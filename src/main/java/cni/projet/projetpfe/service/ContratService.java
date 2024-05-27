package cni.projet.projetpfe.service;


import cni.projet.projetpfe.model.ContratDTO;


import java.util.List;


public interface ContratService {
	List<ContratDTO> getAllContrats();
    ContratDTO createContrat(ContratDTO contrat);
    ContratDTO updateContrat( ContratDTO contratDTO);
    void deleteContrat(Long id);
    ContratDTO getContratById(Long id);
	List<ContratDTO> findByAoId(Long ao_id);
}
