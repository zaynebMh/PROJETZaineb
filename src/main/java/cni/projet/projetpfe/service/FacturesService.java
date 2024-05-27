package cni.projet.projetpfe.service;


import cni.projet.projetpfe.model.FacturesDTO;


import java.util.List;


public interface FacturesService {
	List<FacturesDTO> getAllFactures();
	FacturesDTO createFacture(FacturesDTO factureDTO);
	FacturesDTO updateFacture(FacturesDTO factureDTO);
	void deleteFacture(Long id);
	FacturesDTO getFactureById(Long id);
	List<FacturesDTO> findByContratId(Long contrat_id);

    
  
    
}