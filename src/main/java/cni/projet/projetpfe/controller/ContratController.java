package cni.projet.projetpfe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cni.projet.projetpfe.Exception.ResourceNotFoundException;
import cni.projet.projetpfe.model.Contrat;
import cni.projet.projetpfe.model.ContratDTO;
import cni.projet.projetpfe.service.ContratService;



@RestController
@CrossOrigin(origins = "*" )
public class ContratController {
	 private static final Logger logger = LoggerFactory.getLogger(ContratController.class);

	 @Autowired
	 private ContratService contratService ;

	 @GetMapping("/contrats")
	 public ResponseEntity<List<ContratDTO>> getAllContrats() {
	     try {
	         List<ContratDTO> contrats = contratService.getAllContrats();
	         logger.info("Liste de tous les contrats récupérée avec succès");
	         return ResponseEntity.ok(contrats); 
	     } catch (Exception ex) {
	         logger.error("Erreur lors de la récupération de la liste des contrats", ex);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur serveur interne
	     }
	 }
	
	    
	 @PostMapping("/createContrat")
	 public ResponseEntity<ContratDTO> createContrat(@RequestBody ContratDTO contrat) {
	     try {
	         // Validate required fields
	         if (contrat.getAo_id() == null || contrat.getAo_id() <= 0) {
	             throw new IllegalArgumentException("ProjetId must be specified and greater than zero");
	         }

	         // Continue with creating the contrat
	         ContratDTO createdContrat = contratService.createContrat(contrat);
	         logger.info("Contrat créé avec succès : {}", createdContrat);
	         return ResponseEntity.status(HttpStatus.CREATED).body(createdContrat);
	     } catch (HttpMessageNotReadableException ex) {
	         logger.error("Error parsing JSON request: {}", ex.getMessage());
	         return ResponseEntity.badRequest().build(); // Bad request due to JSON parsing error
	     } catch (IllegalArgumentException ex) {
	         logger.error("Erreur lors de la création du contrat : {}", ex.getMessage());
	         return ResponseEntity.badRequest().build(); // Bad request due to invalid input
	     } catch (Exception ex) {
	         logger.error("Erreur lors de la création du contrat", ex);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Internal server error
	     }
	 }

	 
	    
	 
	 @GetMapping("/contrat/{id}")
	    public ResponseEntity<ContratDTO> getContratById(@PathVariable Long id) {
	        try {
	            ContratDTO contrat = contratService.getContratById(id);
	            if (contrat != null) {
	                logger.info("Contrat trouvé avec succès : {}", contrat);
	                return ResponseEntity.ok(contrat);
	            } else {
	                throw new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + id);
	            }
	        } catch (ResourceNotFoundException ex) {
	            logger.error("Contrat non trouvé pour l'identifiant : {}", id);
	            return ResponseEntity.notFound().build(); 
	        } catch (Exception ex) {
	            logger.error("Erreur lors de la récupération du contrat avec l'identifiant : {}", id, ex);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur serveur interne
	        }
	    }
		
	 @PutMapping("/updateContrat")
	    public ResponseEntity<ContratDTO> updateContrat(@RequestBody Contrat contratDetails) {
	        try {
	            ContratDTO contrat = contratService.getContratById(contratDetails.getId());
	            if (contrat != null) {
	                contrat.setIntitule(contratDetails.getIntitule());
	                contrat.setMontant(contratDetails.getMontant());
	                contrat.setEtablissement(contratDetails.getEtablissement());
	                contrat.setRef(contratDetails.getRef());
	                ContratDTO updatedContrat = contratService.updateContrat(contrat);
	                logger.info("Contrat mis à jour avec succès : {}", updatedContrat);
	                return ResponseEntity.ok(updatedContrat);
	            } else {
	                throw new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + contratDetails.getId());
	            }
	        } catch (ResourceNotFoundException ex) {
	            logger.error("Contrat non trouvé pour l'identifiant : {}", contratDetails.getId());
	            return ResponseEntity.notFound().build();
	        } catch (Exception ex) {
	            logger.error("Erreur lors de la mise à jour du contrat avec l'identifiant : {}", contratDetails.getId(), ex);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur serveur interne
	        }
	    }

	 
	 @DeleteMapping("/deleteContrat/{id}")
	    public ResponseEntity<Map<String, Boolean>> deleteContrat(@PathVariable Long id) {
	        try {
	            ContratDTO contrat = contratService.getContratById(id);
	            if (contrat != null) {
	                contratService.deleteContrat(id);
	                Map<String, Boolean> response = new HashMap<>();
	                response.put("deleted", Boolean.TRUE);
	                logger.info("Contrat supprimé avec succès : {}", id);
	                return ResponseEntity.ok(response);
	            } else {
	                throw new ResourceNotFoundException("Contrat non trouvé avec l'identifiant : " + id);
	            }
	        } catch (ResourceNotFoundException ex) {
	            logger.error("Contrat non trouvé pour l'identifiant : {}", id);
	            return ResponseEntity.notFound().build();
	        } catch (Exception ex) {
	            logger.error("Erreur lors de la suppression du contrat avec l'identifiant : {}", id, ex);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur serveur interne
	        }
	 }
	 @GetMapping("/Contrats/{ao_id}")
	    public ResponseEntity<List<ContratDTO>> findByAoId(@PathVariable Long ao_id) {
	        try {
	            List<ContratDTO> contrats = contratService.findByAoId(ao_id);
	            logger.info("Liste de contrats pour ao_id {} récupérée avec succès", ao_id);
	            return ResponseEntity.ok(contrats);
	        } catch (Exception ex) {
	            logger.error("Erreur lors de la récupération des contrats pour ao_id {}", ao_id, ex);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur serveur interne
	        }
	    }
}
	 


