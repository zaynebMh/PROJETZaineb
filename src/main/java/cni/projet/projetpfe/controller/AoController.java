package cni.projet.projetpfe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cni.projet.projetpfe.Exception.ResourceNotFoundException;
import cni.projet.projetpfe.model.AoDTO;
import cni.projet.projetpfe.service.AoService;


@RestController
@CrossOrigin(origins = "*")
public class AoController {
	
	 private static final Logger logger = LoggerFactory.getLogger(AoController.class);

	@Autowired
	private AoService aoservice;

	// Get all AO
	@GetMapping("/aos")
	public ResponseEntity<List<AoDTO>> getAllAOs() {
	    logger.info("Endpoint /aos appelé pour récupérer tous les AO.");
	    List<AoDTO> aoList = aoservice.getAllAOs();
	    return ResponseEntity.ok(aoList);
	}
	@PostMapping("/createAO")
	 public ResponseEntity<AoDTO> createAO(@RequestBody AoDTO ao) {
        logger.info("Endpoint /createAO appelé avec AO : {}", ao);

        if (ao == null) {
            logger.error("Objet AO est null.");
            throw new IllegalArgumentException("Ao object cannot be null");
        }

        try {
            AoDTO createdAo = aoservice.createAO(ao);
            logger.info("Nouveau AO créé avec succès : {}", createdAo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAo);
        } catch (IllegalArgumentException e) {
            logger.error("Erreur de validation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Erreur lors de la création du AO: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

	@GetMapping("/ao/{id}")
	public ResponseEntity<AoDTO> getAOById(@PathVariable @NonNull Long id) {
	    logger.info("Recherche de l'AO avec l'ID : {}", id);

	    AoDTO ao = aoservice.getAoById(id);
	    if (ao != null) {
	        logger.info("AO trouvé avec l'ID {} : {}", id, ao);
	        return ResponseEntity.ok(ao);
	    } else {
	        logger.error("Aucun AO trouvé avec l'ID : {}", id);
	        throw new ResourceNotFoundException("l'Appel d'offre n'existe pas avec l'ID : " + id);
	    }
	}
	@PutMapping("/updateAO")
	public ResponseEntity<AoDTO> updateAO(@RequestBody AoDTO aoDetails) {
	    logger.info("Mise à jour de l'AO avec l'ID : {}", aoDetails.getId());

	    AoDTO a = aoservice.getAoById(aoDetails.getId());
	    if (a != null) {
	        
	        a.setIntitule(aoDetails.getIntitule());
	        a.setEtablissement(aoDetails.getEtablissement());
	        a.setMontant(aoDetails.getMontant());
	        a.setRef(aoDetails.getRef());

	        AoDTO updatedAo = aoservice.updateAO(a);

	        logger.info("AO mis à jour avec succès : {}", updatedAo);
	        return ResponseEntity.ok(updatedAo);
	    } else {
	        logger.error("Aucun AO trouvé avec l'ID : {}", aoDetails.getId());
	        throw new ResourceNotFoundException("Appel d'Offre n'existe pas avec l'ID : " + aoDetails.getId());
	    }
	}


	@DeleteMapping("/deleteAo/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteAo(@PathVariable Long id) {
	    logger.info("Tentative de suppression de l'AO avec l'ID : {}", id);

	    AoDTO ao = aoservice.getAoById(id);
	    if (ao != null) {
	       
	        aoservice.deleteAO(id);

	        logger.info("AO supprimé avec succès : {}", id);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return ResponseEntity.ok(response);
	    } else {
	        logger.error("Aucun AO trouvé avec l'ID : {}", id);
	        throw new ResourceNotFoundException("Appel d'Offre n'existe pas avec l'ID : " + id);
	    }
	}
	@GetMapping("/aos/{projet_id}")
	public ResponseEntity<List<AoDTO>> getAosByProjetId(@PathVariable Long projet_id) {
	    logger.info("Recherche des AO pour le projet avec l'ID : {}", projet_id);

	    List<AoDTO> aos = aoservice.findByProjetId(projet_id);
	    if (!aos.isEmpty()) {
	        logger.info("AO trouvés pour le projet avec l'ID {} : {}", projet_id, aos);
	        return ResponseEntity.ok(aos);
	    } else {
	        logger.error("Aucun AO trouvé pour le projet avec l'ID : {}", projet_id);
	        throw new ResourceNotFoundException("Aucun AO trouvé pour le projet avec l'ID : " + projet_id);
	    }
	}
}
