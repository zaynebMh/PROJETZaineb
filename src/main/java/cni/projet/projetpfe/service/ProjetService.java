package cni.projet.projetpfe.service;

import cni.projet.projetpfe.model.ProjetDTO;


import java.util.List;


public interface ProjetService {
    List<ProjetDTO> getAllProjets();
    ProjetDTO createProjet(ProjetDTO projetDTO);
    ProjetDTO updateProjet(ProjetDTO projetDTO);
    void deleteProjet(Long id);
	ProjetDTO getProjetById(Long id);


}
