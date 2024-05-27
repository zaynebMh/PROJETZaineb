package cni.projet.projetpfe.service;

import cni.projet.projetpfe.model.Ao;
import cni.projet.projetpfe.model.Contrat;
import cni.projet.projetpfe.model.ContratDTO;
import cni.projet.projetpfe.repository.AoRepository;
import cni.projet.projetpfe.repository.ContratRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContratServiceImpl implements ContratService {

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private AoRepository aoRepository;

    @Override
    public List<ContratDTO> getAllContrats() {
        List<Contrat> contrats = contratRepository.findAll();
        return contrats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContratDTO createContrat(ContratDTO contratDTO) {
        validateContratDTO(contratDTO);
        Contrat contrat = convertToEntity(contratDTO);
        Contrat savedContrat = contratRepository.save(contrat);
        return convertToDTO(savedContrat);
    }
    
    @Override
    public ContratDTO updateContrat(ContratDTO contratDTO) {
        if (contratDTO.getId() == null) {
            throw new IllegalArgumentException("ContratDTO ID cannot be null");
        }

        Contrat existingContrat = contratRepository.findById(contratDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Contrat not found with ID: " + contratDTO.getId()));

        // Update existing contrat with new details
        existingContrat.setIntitule(contratDTO.getIntitule());
        existingContrat.setEtablissement(contratDTO.getEtablissement());
        existingContrat.setMontant(contratDTO.getMontant());
        existingContrat.setRef(contratDTO.getRef());
      
        // Update ao_id if necessary
        if (!existingContrat.getAo().getId().equals(contratDTO.getAo_id())) {
            Long aoId = contratDTO.getAo_id();
            Optional<Ao> optionalAo = aoRepository.findById(aoId);
            if (optionalAo.isPresent()) {
                Ao ao = optionalAo.get();
                existingContrat.setAo(ao);
            } else {
                throw new IllegalArgumentException("Appel d'offres not found with id: " + aoId);
            }
        }

        // Save updated contrat
        Contrat updatedContrat = contratRepository.save(existingContrat);
        return convertToDTO(updatedContrat);
    }

    @Override
    public void deleteContrat(Long id) {
        contratRepository.deleteById(id);
    }

    @Override
    public ContratDTO getContratById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Contrat ID cannot be null");
        }

        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contrat not found with ID: " + id));

        return convertToDTO(contrat);
    }

    private void validateContratDTO(ContratDTO contratDTO) {
        if (contratDTO == null) {
            throw new IllegalArgumentException("ContratDTO object cannot be null");
        }

        if (contratDTO.getIntitule() == null || contratDTO.getIntitule().isBlank()) {
            throw new IllegalArgumentException("Intitule is required for ContratDTO");
        }

        if (contratDTO.getEtablissement() == null || contratDTO.getEtablissement().isBlank()) {
            throw new IllegalArgumentException("Etablissement is required for ContratDTO");
        }

        if (contratDTO.getMontant() <= 0.0) {
            throw new IllegalArgumentException("Montant must be specified and greater than zero for ContratDTO");
        }

        if (contratDTO.getRef() == null || contratDTO.getRef() <= 0) {
            throw new IllegalArgumentException("Ref must be specified and greater than zero for ContratDTO");
        }
        if (contratDTO.getAo_id() == null || contratDTO.getAo_id() <= 0) {
            throw new IllegalArgumentException("ProjetId must be specified and greater than zero for AoDTO");
        }
    }

    private ContratDTO convertToDTO(Contrat contrat) {
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setId(contrat.getId());
        contratDTO.setIntitule(contrat.getIntitule());
        contratDTO.setEtablissement(contrat.getEtablissement());
        contratDTO.setMontant(contrat.getMontant());
        contratDTO.setRef(contrat.getRef());

        if (contrat.getAo() != null) {
            contratDTO.setAo_id(null);
        }

        return contratDTO;
    }

    private Contrat convertToEntity(ContratDTO contratDTO) {
        Contrat contrat = new Contrat();
        contrat.setIntitule(contratDTO.getIntitule());
        contrat.setEtablissement(contratDTO.getEtablissement());
        contrat.setMontant(contratDTO.getMontant());
        contrat.setRef(contratDTO.getRef());

        if (contratDTO.getAo_id() != null) {
            Long aoId = contratDTO.getAo_id();
            Optional<Ao> optionalAo = aoRepository.findById(aoId);
            if (optionalAo.isPresent()) {
                Ao ao = optionalAo.get();
                contrat.setAo(ao);
            } else {
                throw new IllegalArgumentException("Appel d'offres not found with id: " + aoId);
            }
        }

        return contrat;
    }

    @Override
    public List<ContratDTO> findByAoId(Long ao_id) {
        if (ao_id == null || ao_id <= 0) {
            throw new IllegalArgumentException("Ao ID must be specified and greater than zero");
        }

        List<Contrat> contrats = contratRepository.findByAoId(ao_id);
        return contrats.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}

