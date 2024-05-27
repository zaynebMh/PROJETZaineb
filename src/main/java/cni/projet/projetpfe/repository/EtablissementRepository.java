package cni.projet.projetpfe.repository;

import cni.projet.projetpfe.model.Etablissement;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {

}
