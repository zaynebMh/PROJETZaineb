package cni.projet.projetpfe.service;

import cni.projet.projetpfe.model.Projet;
import cni.projet.projetpfe.model.ProjetDTO;
import cni.projet.projetpfe.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProjetServiceImpl implements ProjetService{
    @Autowired
    ProjetRepository projetRepository ;
   
    @Override
    public List<ProjetDTO> getAllProjets() {
        List<Projet> p= projetRepository.findAll(); 
        List<ProjetDTO> ps = p.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ps;
    }
    @Override
    public ProjetDTO createProjet(ProjetDTO projetDTO) {
        // Vérifier que le ProjetDTO n'est pas null
        if (projetDTO == null) {
            throw new IllegalArgumentException("ProjetDTO object cannot be null");
        }

        // Valider les champs obligatoires de ProjetDTO
        validateProjetDTO(projetDTO);

        // Convertir ProjetDTO en entité Projet
        Projet projet = convertToEntity(projetDTO);

        // Sauvegarder l'entité Projet dans le dépôt
        Projet savedProjet = projetRepository.save(projet);

        // Convertir l'entité Projet sauvegardée en ProjetDTO
        ProjetDTO savedProjetDTO = convertToDTO(savedProjet);

        // Vérifier que la conversion en ProjetDTO est réussie
        if (savedProjetDTO == null) {
            throw new RuntimeException("Failed to convert saved Projet object to DTO");
        }

        return savedProjetDTO;
    }

    
    private void validateProjetDTO(ProjetDTO projetDTO) {
        if (projetDTO.getIntitule() == null || projetDTO.getIntitule().isBlank()) {
            throw new IllegalArgumentException("Intitule is required for ProjetDTO");
        }
        if (projetDTO.getEtablissement() == null || projetDTO.getEtablissement().isBlank()) {
            throw new IllegalArgumentException("Etablissement is required for ProjetDTO");
        }
        
        if (projetDTO.getMontant()<= 0.0) {
            throw new IllegalArgumentException("Montant must be specified and greater than zero for ProjetDTO");
        }
        
        if (projetDTO.getRef() == null || projetDTO.getRef() <= 0.0) {
            throw new IllegalArgumentException("Ref must be specified and greater than zero for ProjetDTO");
        }
    }

	@Override
public ProjetDTO updateProjet( ProjetDTO projetDTO) {
	Projet p = projetRepository.findById(projetDTO.getId()).orElse(null);
    
        if (p != null) {
            Projet updatedProjet = projetRepository.save(p);
            ProjetDTO updatedProjetDTO = convertToDTO(updatedProjet);
            
            return updatedProjetDTO;
        } else {
			throw new IllegalArgumentException("Le projet trouvé avec l'ID : est null");
        }
    
}
 
    @Override
    public void deleteProjet(Long id) {
    if (id != null) {
        projetRepository.deleteById(id);
    } else {
        throw new IllegalArgumentException("L'identifiant (id) ne peut pas être null.");
    }
    }


	
	@Override
	public ProjetDTO getProjetById(Long id) {
	    if (id == null) {
	        throw new IllegalArgumentException("ID cannot be null");
	    }

	    Projet p = projetRepository.findById(id).orElse(null);
	    if (p != null) {
	        ProjetDTO projetDTO = new ProjetDTO();
	        projetDTO.setId(p.getId());
	        projetDTO.setIntitule(p.getIntitule());
	        projetDTO.setMontant(p.getMontant());
	        projetDTO.setEtablissement(p.getEtablissement());
	        return projetDTO;
	    } else {
	        return null;
	    }
	}

	private ProjetDTO convertToDTO(Projet projet) {
	    if (projet == null) {
	        return null;
	    }

	    ProjetDTO projetDTO = new ProjetDTO();
	    projetDTO.setId(projet.getId());
	    projetDTO.setIntitule(projet.getIntitule());
	    projetDTO.setEtablissement(projet.getEtablissement());
	    projetDTO.setMontant(projet.getMontant());
	    projetDTO.setRef(projet.getRef()); 

	    return projetDTO;
	}
 
    
    private Projet convertToEntity(ProjetDTO projetDTO) {
    	Projet projet = new Projet();
        projet.setIntitule(projetDTO.getIntitule());
        projet.setEtablissement(projetDTO.getEtablissement());
        projet.setMontant(projetDTO.getMontant());
        projet.setRef(projetDTO.getRef());
        return projet;
    }


}
