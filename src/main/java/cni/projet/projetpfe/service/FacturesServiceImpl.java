package cni.projet.projetpfe.service;

import cni.projet.projetpfe.model.Contrat;
import cni.projet.projetpfe.model.Factures;
import cni.projet.projetpfe.model.FacturesDTO;
import cni.projet.projetpfe.repository.ContratRepository;
import cni.projet.projetpfe.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturesServiceImpl implements FacturesService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ContratRepository contratRepository;

    @Override
    public List<FacturesDTO> getAllFactures() {
        List<Factures> facturesList = factureRepository.findAll();
        return facturesList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FacturesDTO createFacture(FacturesDTO factureDTO) {
    	validateFactureDTO(factureDTO);
        Factures facture = convertToEntity(factureDTO);
        Factures savedFactures = factureRepository.save(facture);
        return convertToDTO(savedFactures);
    }

    private void validateFactureDTO(FacturesDTO factureDTO) {
    	if (factureDTO == null) {
    	    throw new IllegalArgumentException("FactureDTO object cannot be null");
    	}

    	if (factureDTO.getIntitule() == null || factureDTO.getIntitule().isBlank()) {
    	    throw new IllegalArgumentException("Intitule is required for FactureDTO");
    	}

    	if (factureDTO.getEtablissement() == null || factureDTO.getEtablissement().isBlank()) {
    	    throw new IllegalArgumentException("Etablissement is required for FactureDTO");
    	}

    	if (factureDTO.getMontant() <= 0.0) {
    	    throw new IllegalArgumentException("Montant must be specified and greater than zero for FactureDTO");
    	}

    	if (factureDTO.getRef() == null | factureDTO.getRef()<=0) {
    	    throw new IllegalArgumentException("Ref must be specified and greater than zero for FactureDTO");
    	}

    	if (factureDTO.getContrat_id() == null || factureDTO.getContrat_id() <= 0) {
    	    throw new IllegalArgumentException("ContratId must be specified and greater than zero for FactureDTO");
    	}

	}

	@Override
    public FacturesDTO updateFacture(FacturesDTO factureDTO) {
        if (factureDTO == null || factureDTO.getId() == null) {
            throw new IllegalArgumentException("Facture DTO ou ID ne peut pas être null");
        }

        Factures existingFacture = factureRepository.findById(factureDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Facture non trouvée pour l'ID : " + factureDTO.getId()));

        existingFacture.setIntitule(factureDTO.getIntitule());
        existingFacture.setMontant(factureDTO.getMontant());
        existingFacture.setEtablissement(factureDTO.getEtablissement());
        existingFacture.setRef(factureDTO.getRef());
      
        Factures updatedFacture = factureRepository.save(existingFacture);

        return convertToDTO(updatedFacture);
    }

    @Override
    public FacturesDTO getFactureById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID ne peut pas être null");
        }

        Factures facture = factureRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Facture non trouvée pour l'ID : " + id));

        return convertToDTO(facture);
    }

    @Override
    public void deleteFacture(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID ne peut pas être null");
        }

        factureRepository.deleteById(id);
    }

    private FacturesDTO convertToDTO(Factures facture) {
        FacturesDTO factureDTO = new FacturesDTO();
        factureDTO.setId(facture.getId());
        factureDTO.setIntitule(facture.getIntitule());
        factureDTO.setMontant(facture.getMontant());
        factureDTO.setEtablissement(facture.getEtablissement());
        factureDTO.setRef(facture.getRef());
        if (facture.getContrat() != null) {
            factureDTO.setContrat_id(facture.getContrat().getId());
        }
        return factureDTO;
    }

    private Factures convertToEntity(FacturesDTO factureDTO) {
        Factures facture = new Factures();
        facture.setId(factureDTO.getId());
        facture.setIntitule(factureDTO.getIntitule());
        facture.setEtablissement(factureDTO.getEtablissement());
        facture.setMontant(factureDTO.getMontant());
        facture.setRef(factureDTO.getRef());
        
        Long contratId = factureDTO.getContrat_id();
        if (contratId != null) {
            Contrat contrat = contratRepository.findById(contratId)
                    .orElseThrow(() -> new IllegalArgumentException("Contrat not found with ID: " + contratId));
            facture.setContrat(contrat);
        }
        return facture;
    }
    @Override
    public List<FacturesDTO> findByContratId(Long contrat_id) {
        List<Factures> facturesList = factureRepository.findByContratId(contrat_id);
        return facturesList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
