package cni.projet.projetpfe.service;

import cni.projet.projetpfe.model.Ao;
import cni.projet.projetpfe.model.AoDTO;
import cni.projet.projetpfe.model.Projet;
import cni.projet.projetpfe.repository.AoRepository;
import cni.projet.projetpfe.repository.ProjetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AoServiceImpl implements AoService {
    @Autowired
    private AoRepository aoRepository;

    @Autowired
    private ProjetRepository projetRepository;

    @Override
    public List<AoDTO> getAllAOs() {
        List<Ao> aos = aoRepository.findAll();
        return aos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AoDTO createAO(AoDTO aoDTO) {
        if (aoDTO == null) {
            throw new IllegalArgumentException("AoDTO object cannot be null");
        }

        validateAoDTO(aoDTO);

        Ao ao = convertToEntity(aoDTO);
        Ao savedAo = aoRepository.save(ao);

        return convertToDTO(savedAo);
    }

    private void validateAoDTO(AoDTO aoDTO) {
        if (aoDTO.getProjet_id() == null || aoDTO.getProjet_id() <= 0) {
            throw new IllegalArgumentException("Projet_id must be specified and greater than zero for AoDTO");
        }
        if (aoDTO.getIntitule() == null || aoDTO.getIntitule().isBlank()) {
            throw new IllegalArgumentException("Intitule is required for AoDTO");
        }
        if (aoDTO.getMontant() <= 0.0) {
            throw new IllegalArgumentException("Montant must be specified and greater than zero for AoDTO");
        }
        if (aoDTO.getRef() == null || aoDTO.getRef() <= 0) {
            throw new IllegalArgumentException("Ref must be specified and greater than zero for AoDTO");
        }
        if (aoDTO.getEtablissement() == null || aoDTO.getEtablissement().isEmpty()) {
            throw new IllegalArgumentException("Etablissement is required for AoDTO");
        }
    }

    @Override
    public AoDTO updateAO(AoDTO aoDTO) {
        if (aoDTO == null || aoDTO.getId() == null) {
            throw new IllegalArgumentException("AoDTO object or ID cannot be null");
        }

        Ao existingAo = aoRepository.findById(aoDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("L'Appel d'offre avec l'ID " + aoDTO.getId() + " n'existe pas."));
        existingAo.setIntitule(aoDTO.getIntitule());
        existingAo.setMontant(aoDTO.getMontant());
        existingAo.setRef(aoDTO.getRef());
        existingAo.setEtablissement(aoDTO.getEtablissement());

        Ao updatedAo = aoRepository.save(existingAo);

        return convertToDTO(updatedAo);
    }

    @Override
    public void deleteAO(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'identifiant (id) ne peut pas être null.");
        }
        aoRepository.deleteById(id);
    }

    @Override
    public AoDTO getAoById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Ao ao = aoRepository.findById(id).orElse(null);
        if (ao != null) {
            return convertToDTO(ao);
        } else {
            return null;
        }
    }

    private AoDTO convertToDTO(Ao ao) {
        AoDTO aoDTO = new AoDTO();
        aoDTO.setId(ao.getId());
        aoDTO.setIntitule(ao.getIntitule());
        aoDTO.setEtablissement(ao.getEtablissement());
        aoDTO.setMontant(ao.getMontant());
        aoDTO.setRef(ao.getRef());
        if (ao.getProjet() != null) {
            aoDTO.setProjet_id(ao.getProjet().getId());
        }
        return aoDTO;
    }

    private Ao convertToEntity(AoDTO aoDTO) {
        Ao ao = new Ao();
        ao.setIntitule(aoDTO.getIntitule());
        ao.setEtablissement(aoDTO.getEtablissement());
        ao.setMontant(aoDTO.getMontant());
        ao.setRef(aoDTO.getRef());

        if (aoDTO.getProjet_id() != null) {
            Projet projet = projetRepository.findById(aoDTO.getProjet_id())
                    .orElseThrow(() -> new IllegalArgumentException("Projet not found with id: " + aoDTO.getProjet_id()));
            ao.setProjet(projet);
        }

        return ao;
    }

    @Override
    public List<AoDTO> findByProjetId(Long projet_id) {
       
        if (projet_id == null || projet_id <= 0) {
            throw new IllegalArgumentException("ProjetId must be specified and greater than zero");
        }
        List<Ao> aos = aoRepository.findByProjetId(projet_id);
        
        return aos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
