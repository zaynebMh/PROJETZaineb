package cni.projet.projetpfe.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cni.projet.projetpfe.model.User;
import cni.projet.projetpfe.service.CompteService;


@CrossOrigin("*")
@RestController
public class CompteController {

	private static final Logger logger = LoggerFactory.getLogger(CompteController.class);

    @Autowired
    private CompteService compteService;

    @GetMapping("/Comptes")
    public List<User> getAllComptes() {
        logger.info("Fetching all comptes");
        return compteService.getAllComptes();
    }

    @GetMapping("/Compte/{id}")
    public ResponseEntity<User> getCompteById(@PathVariable Long id) {
        logger.info("Fetching compte with id {}", id);
        Optional<User> compte = compteService.getCompteById(id);
        return compte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/createCompte")
    public ResponseEntity<User> createCompte(@RequestBody User user) {
        logger.info("Creating new compte with username {}", user.getUsername());
        try {
            User createdCompte = compteService.createCompte(user);
            return ResponseEntity.ok(createdCompte);
        } catch (RuntimeException e) {
            logger.error("Error creating compte: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/updateCompte/{id}")
    public ResponseEntity<User> updateCompte(@PathVariable Long id, @RequestBody User userDetails) {
        logger.info("Updating compte with id {}", id);
        try {
            User updatedCompte = compteService.updateCompte(id, userDetails);
            return ResponseEntity.ok(updatedCompte);
        } catch (RuntimeException e) {
            logger.error("Compte or User not found with id {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteCompte/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        logger.info("Deleting compte with id {}", id);
        try {
            compteService.deleteCompte(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Compte not found with id {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
