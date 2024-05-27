package cni.projet.projetpfe.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cni.projet.projetpfe.model.EtablissementDTO;
import cni.projet.projetpfe.service.EtablissementService;

@RestController
@CrossOrigin(origins = "*" )
public class EtablissementController {

    private static final Logger logger = LoggerFactory.getLogger(EtablissementController.class);

    @Autowired
    private EtablissementService etablissementService;

    @GetMapping("/getAllEtablissements")
    public List<EtablissementDTO> getAllEtablissements() {
     
        List<EtablissementDTO> etablissements = etablissementService.getAllEtablissements();
       
        return etablissements;
    }

    @GetMapping("/Etablissement/{id}")
    public ResponseEntity<EtablissementDTO> getEtablissementById(@PathVariable Long id) {
        logger.info("Fetching etablissement with id: {}", id);
        EtablissementDTO etablissementDTO = etablissementService.getEtablissementById(id);
        if (etablissementDTO != null) {
            logger.info("Etablissement found: {}", etablissementDTO);
            return ResponseEntity.ok(etablissementDTO);
        } else {
            logger.warn("Etablissement with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createEtablissement")
    public ResponseEntity<EtablissementDTO> createEtablissement(@RequestBody EtablissementDTO etablissementDTO) {
        logger.info("Creating new etablissement with data: {}", etablissementDTO);
        EtablissementDTO savedEtablissementDTO = etablissementService.createEtablissement(etablissementDTO);
        logger.info("Created etablissement with id: {}", savedEtablissementDTO.getId());
        return ResponseEntity.ok(savedEtablissementDTO);
    }

    @PutMapping("/updateEtablissement/{id}")
    public ResponseEntity<EtablissementDTO> updateEtablissement(@PathVariable Long id, @RequestBody EtablissementDTO etablissementDTO) {
        logger.info("Updating etablissement with id: {}", id);
        etablissementDTO.setId(id);
        EtablissementDTO updatedEtablissementDTO = etablissementService.updateEtablissement(etablissementDTO);
        if (updatedEtablissementDTO != null) {
            logger.info("Updated etablissement with id: {}", id);
            return ResponseEntity.ok(updatedEtablissementDTO);
        } else {
            logger.warn("Etablissement with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteEtablissement/{id}")
    public ResponseEntity<Void> deleteEtablissement(@PathVariable Long id) {
        logger.info("Deleting etablissement with id: {}", id);
        etablissementService.deleteEtablissement(id);
        logger.info("Deleted etablissement with id: {}", id);
        return ResponseEntity.ok().build();
    }
}
