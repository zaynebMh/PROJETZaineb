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

import cni.projet.projetpfe.model.FacturesDTO;

import cni.projet.projetpfe.service.FacturesService;

@CrossOrigin(origins = "*" )
@RestController
public class FacturesController {
	 private static final Logger logger = LoggerFactory.getLogger(FacturesController.class);

	 @Autowired
	    private FacturesService factureService  ;
	
	 @GetMapping("/factures")
	 public ResponseEntity<List<FacturesDTO>> getAllFactures() {
	     try {
	         List<FacturesDTO> factures = factureService.getAllFactures();
	         logger.info("Liste des factures récupérée avec succès");
	         return ResponseEntity.ok(factures);
	     } catch (Exception ex) {
	         logger.error("Erreur lors de la récupération de la liste des factures", ex);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
	     }
	 }
	    
	    
	 @PostMapping("/createFacture")
	 public ResponseEntity<FacturesDTO> createFacture(@RequestBody FacturesDTO facture) {
		 try {
			    
			    if (facture.getContrat_id() == null || facture.getContrat_id() <= 0) {
			        throw new IllegalArgumentException("ContratId must be specified and greater than zero");
			    }
			   
			    FacturesDTO createdFacture = factureService.createFacture(facture);
			    logger.info("Facture créée avec succès : {}", createdFacture);
			    return ResponseEntity.status(HttpStatus.CREATED).body(createdFacture);
			} catch (HttpMessageNotReadableException ex) {
			    logger.error("Erreur lors de l'analyse de la requête JSON : {}", ex.getMessage());
			    return ResponseEntity.badRequest().build(); 
			} catch (IllegalArgumentException ex) {
			    logger.error("Erreur lors de la création de la facture : {}", ex.getMessage());
			    return ResponseEntity.badRequest().build(); 
			} catch (Exception ex) {
			    logger.error("Erreur lors de la création de la facture", ex);
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

	 }

	 @GetMapping("/facture/{id}")
	 public ResponseEntity<FacturesDTO> getFactureById(@PathVariable Long id) {
	     try {
	         FacturesDTO facture = factureService.getFactureById(id);
	         if (facture != null) {
	             logger.info("Récupération de la facture avec l'ID : {}", id);
	             return ResponseEntity.ok(facture);
	         } else {
	             throw new ResourceNotFoundException("Facture not exist with id :" + id);
	         }
	     } catch (ResourceNotFoundException ex) {
	         logger.error("Facture not found with id : {}", id);
	         return ResponseEntity.notFound().build(); 
	     } catch (Exception ex) {
	         logger.error("Erreur lors de la récupération de la facture avec l'ID : {}", id, ex);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
	     }
	 }

	 @PutMapping("/updateFacture/{id}")
	 public ResponseEntity<FacturesDTO> updateFacture(@RequestBody FacturesDTO factureDetails) {
	     try {
	         FacturesDTO facture = factureService.getFactureById(factureDetails.getId());
	         if (facture != null) {
	             facture.setIntitule(factureDetails.getIntitule());
	             facture.setMontant(factureDetails.getMontant());
	             facture.setRef(factureDetails.getRef());
	             facture.setContrat_id(factureDetails.getContrat_id());
	             FacturesDTO updatedFacture = factureService.updateFacture(facture);
	             logger.info("Facture mise à jour avec succès : {}", factureDetails.getId());
	             return ResponseEntity.ok(updatedFacture);
	         } else {
	             throw new ResourceNotFoundException("Facture not exist with id :" + factureDetails.getId());
	         }
	     } catch (ResourceNotFoundException ex) {
	         logger.error("Facture not found with id : {}", factureDetails.getId());
	         return ResponseEntity.notFound().build(); 
	     } catch (Exception ex) {
	         logger.error("Erreur lors de la mise à jour de la facture avec l'ID : {}", factureDetails.getId(), ex);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
	     }
	 }

	 @DeleteMapping("/deleteFacture/{id}")
	 public ResponseEntity<Map<String, Boolean>> deleteFacture(@PathVariable Long id) {
	     try {
	         FacturesDTO facture = factureService.getFactureById(id);
	         if (facture != null) {
	             factureService.deleteFacture(id);
	             logger.info("Facture supprimée avec succès : {}", id);
	             Map<String, Boolean> response = new HashMap<>();
	             response.put("deleted", Boolean.TRUE);
	             return ResponseEntity.ok(response); 
	         } else {
	             throw new ResourceNotFoundException("Facture not exist with id :" + id);
	         }
	     } catch (ResourceNotFoundException ex) {
	         logger.error("Facture not found with id : {}", id);
	         return ResponseEntity.notFound().build();
	     } catch (Exception ex) {
	         logger.error("Erreur lors de la suppression de la facture avec l'ID : {}", id, ex);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
	     }
	 }
	 @GetMapping("/factures/{contrat_id}")
	 public ResponseEntity<List<FacturesDTO>> getFacturesByContratId(@PathVariable Long contrat_id) {
	     try {
	         List<FacturesDTO> factures = factureService. findByContratId(contrat_id);
	         logger.info("Liste de factures pour contratId {} récupérée avec succès", contrat_id);
	         return ResponseEntity.ok(factures);
	     } catch (Exception ex) {
	         logger.error("Erreur lors de la récupération des factures pour contratId {}",contrat_id, ex);
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Erreur serveur interne
	     }
	 }
	 }
