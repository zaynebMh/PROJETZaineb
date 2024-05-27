package cni.projet.projetpfe.controller;

import cni.projet.projetpfe.Exception.ResourceNotFoundException;

import cni.projet.projetpfe.model.ProjetDTO;
import cni.projet.projetpfe.service.ProjetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin(origins = "*" )
public class ProjetController {
	private static final Logger logger = LoggerFactory.getLogger(ProjetController.class);


    @Autowired
    private ProjetService projetservice ;
    
    @GetMapping("/projet")
    public ResponseEntity<List<ProjetDTO>> getAllProjets() {
        logger.info("Tentative de récupération de tous les projets.");

        List<ProjetDTO> projets = projetservice.getAllProjets();
        if (projets.isEmpty()) {
            logger.warn("Aucun projet trouvé.");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Récupération réussie de tous les projets.");
            return ResponseEntity.ok(projets);
        }
    }

    @PostMapping("/createProjet")
    public ResponseEntity<ProjetDTO> createProjet(@RequestBody ProjetDTO projet) {
        
        try {
            
            ProjetDTO createdProjet = projetservice.createProjet(projet);

            if (createdProjet == null) {
                logger.error("La création du projet a échoué pour l'objet ProjetDTO : {}", projet);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Retourner une réponse InternalServerError
            }

            logger.info("Projet créé avec succès : {}", createdProjet);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProjet);
        } catch (Exception e) {
          
            logger.error("Une erreur est survenue lors de la création du projet.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/projet/{id}")
    public ResponseEntity<ProjetDTO> getProjetById(@PathVariable Long id) {
        try {
            ProjetDTO projet = projetservice.getProjetById(id);
            if (projet != null) {
                logger.info("Récupération du projet avec ID : {}", id);
                return ResponseEntity.ok(projet);
            } else {
                logger.warn("Aucun projet trouvé avec l'ID : {}", id);
                throw new ResourceNotFoundException("Projet not exist with id :" + id);
            }
        } catch (ResourceNotFoundException ex) {
            logger.error("Erreur lors de la récupération du projet avec l'ID : {}", id, ex);
            throw ex; 
        } catch (Exception ex) {
            logger.error("Une erreur s'est produite lors de la récupération du projet avec l'ID : {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
        }
    }
    @PutMapping("/updateProjet/{id}")
    public ResponseEntity<ProjetDTO> updateProjet(@PathVariable  String id, @RequestBody ProjetDTO projetDetails) {
    	 try {
             Long projetId = Long.parseLong(id);
             ProjetDTO existingProjet = projetservice.getProjetById(projetId);
             if (existingProjet != null) {
 
                 existingProjet.setIntitule(projetDetails.getIntitule());
                 existingProjet.setEtablissement(projetDetails.getEtablissement());
                 existingProjet.setMontant(projetDetails.getMontant());
                 existingProjet.setRef(projetDetails.getRef());
                 ProjetDTO updatedProjet = projetservice.updateProjet(existingProjet);
                 return ResponseEntity.ok(updatedProjet); 
             } else {
                 return ResponseEntity.notFound().build();
             }
         } catch (NumberFormatException e) {

             return ResponseEntity.badRequest().build(); 
         } catch (Exception ex) {
         
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
         }
     }
    

 
    @DeleteMapping("/deleteProjet/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProjet(@PathVariable Long id) {
        try {
            ProjetDTO projet = projetservice.getProjetById(id);
            if (projet != null) {
                projetservice.deleteProjet(id);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                logger.info("Projet avec ID {} supprimé avec succès", id);
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Aucun projet trouvé avec l'ID : {}", id);
                throw new ResourceNotFoundException("Projet not exist with id :" + id);
            }
        } catch (ResourceNotFoundException ex) {
            logger.error("Erreur lors de la suppression du projet avec l'ID : {}", id, ex);
            throw ex; 
        } catch (Exception ex) {
            logger.error("Une erreur s'est produite lors de la suppression du projet avec l'ID : {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
