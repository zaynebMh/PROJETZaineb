package cni.projet.projetpfe.service;

import java.util.List;

import cni.projet.projetpfe.model.EtablissementDTO;


public interface EtablissementService {
	List<EtablissementDTO> getAllEtablissements();

    EtablissementDTO createEtablissement(EtablissementDTO etablissementDTO);

    EtablissementDTO updateEtablissement(EtablissementDTO etablissementDTO);

    EtablissementDTO getEtablissementById(Long id);

    void deleteEtablissement(Long id);

}
