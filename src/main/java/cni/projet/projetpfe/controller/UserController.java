package cni.projet.projetpfe.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cni.projet.projetpfe.model.User;
import cni.projet.projetpfe.model.UserDTO;
import cni.projet.projetpfe.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*" )
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getEmail() == null) {
                return ResponseEntity.badRequest().body("Tous les champs doivent être remplis.");
            }

            User newUser = new User();
            newUser.setUsername(userDTO.getUsername());
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(userDTO.getPassword());
            newUser.setRole(2); 

            userService.save(newUser);

            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            logger.error("Failed to sign up user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to sign up user: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> handleLoginRequest(HttpServletRequest request, HttpSession session, @RequestBody UserDTO userDTO) {
        String method = request.getMethod();
        logger.info("Méthode HTTP de la requête : {}", method);
        logger.info("Données reçues : username={}, password={}", userDTO.getUsername(), userDTO.getPassword());

        if (!HttpMethod.POST.matches(method)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Méthode HTTP non autorisée pour /login");
        }

        logger.info("Requête de connexion reçue avec les paramètres : username={}, password={}", 
                    userDTO.getUsername(), userDTO.getPassword());

        if (userDTO.getUsername() == null || userDTO.getPassword() == null) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur ou mot de passe manquant.");
        }

        User user = userService.findByUsername(userDTO.getUsername());

        if (user != null && userService.checkPassword(userDTO.getPassword(), user.getPassword())) {
            // Invalidation de la session existante
            session.invalidate();
            // Création d'une nouvelle session
            HttpSession newSession = request.getSession(true);
            // Enregistrement des informations de l'utilisateur dans la nouvelle session
            newSession.setAttribute("user", user);
            logger.info("Connexion réussie pour l'utilisateur : {}", userDTO.getUsername());
            return ResponseEntity.ok(user);
        } else {
            logger.warn("Échec de connexion pour l'utilisateur : {}", userDTO.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> handleLogoutRequest(HttpServletRequest request, HttpSession session) {
        try {
            // Invalidation de la session pour déconnecter l'utilisateur
            session.invalidate();
            logger.info("Utilisateur déconnecté avec succès.");
            return ResponseEntity.ok("Utilisateur déconnecté avec succès.");
        } catch (Exception e) {
            logger.error("Erreur lors de la déconnexion : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Erreur lors de la déconnexion : " + e.getMessage());
        }
    }
}
