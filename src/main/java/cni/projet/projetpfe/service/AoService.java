package cni.projet.projetpfe.service;


import cni.projet.projetpfe.model.AoDTO;

import java.util.List;


public interface AoService {
	List<AoDTO> getAllAOs();
    AoDTO createAO(AoDTO aoDTO);
    AoDTO updateAO(AoDTO aoDTO);
    void deleteAO(Long id);
    AoDTO getAoById(Long id);
	List<AoDTO> findByProjetId(Long projet_id);
    
   
}
