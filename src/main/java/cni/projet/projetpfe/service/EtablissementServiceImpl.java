package cni.projet.projetpfe.service;

import cni.projet.projetpfe.model.Etablissement;
import cni.projet.projetpfe.model.EtablissementDTO;

import cni.projet.projetpfe.repository.EtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtablissementServiceImpl implements EtablissementService {

    @Autowired
    private EtablissementRepository etablissementRepository;

    @Override
    public List<EtablissementDTO> getAllEtablissements() {
        List<Etablissement> etablissements = etablissementRepository.findAll();
        return etablissements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EtablissementDTO createEtablissement(EtablissementDTO etablissementDTO) {
        validateEtablissementDTO(etablissementDTO);
        Etablissement etablissement = convertToEntity(etablissementDTO);
        Etablissement savedEtablissement = etablissementRepository.save(etablissement);
        return convertToDTO(savedEtablissement);
    }

    @Override
    public EtablissementDTO updateEtablissement(EtablissementDTO etablissementDTO) {
        if (etablissementDTO == null || etablissementDTO.getId() == null) {
            throw new IllegalArgumentException("Etablissement DTO or ID cannot be null");
        }

        Etablissement existingEtablissement = etablissementRepository.findById(etablissementDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Etablissement not found for ID: " + etablissementDTO.getId()));

        existingEtablissement.setNom(etablissementDTO.getNom());
        existingEtablissement.setAdresse(etablissementDTO.getAdresse());


        Etablissement updatedEtablissement = etablissementRepository.save(existingEtablissement);

        return convertToDTO(updatedEtablissement);
    }

    @Override
    public EtablissementDTO getEtablissementById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Etablissement etablissement = etablissementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Etablissement not found for ID: " + id));

        return convertToDTO(etablissement);
    }

    @Override
    public void deleteEtablissement(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        etablissementRepository.deleteById(id);
    }

    private EtablissementDTO convertToDTO(Etablissement etablissement) {
        EtablissementDTO dto = new EtablissementDTO();
        dto.setId(etablissement.getId());
        dto.setNom(etablissement.getNom());
        dto.setAdresse(etablissement.getAdresse());
        dto.setUser_id(etablissement.getUser().getId());
        
        dto.setCout_projet(etablissement.getCout_projet());
        dto.setCout_contrat(etablissement.getCout_contrat());
        dto.setCout_facture(etablissement.getCout_facture());
        
        return dto;
    }


    private Etablissement convertToEntity(EtablissementDTO etablissementDTO) {
        Etablissement etablissement = new Etablissement();
        etablissement.setId(etablissementDTO.getId());
        etablissement.setNom(etablissementDTO.getNom());
        etablissement.setAdresse(etablissementDTO.getAdresse());
 
        etablissement.setCout_projet(etablissement.getCout_projet());
        etablissement.setCout_contrat(etablissement.getCout_contrat());
        etablissement.setCout_facture(etablissement.getCout_facture());
        return etablissement;
    }

    private void validateEtablissementDTO(EtablissementDTO etablissementDTO) {
        if (etablissementDTO == null) {
            throw new IllegalArgumentException("Etablissement DTO object cannot be null");
        }

    }
}
