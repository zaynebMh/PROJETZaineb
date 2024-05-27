package cni.projet.projetpfe.repository;

import cni.projet.projetpfe.model.Factures;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FactureRepository extends JpaRepository<Factures,Long> {
	  List<Factures> findByContratAoProjetId(Long projet_id);
	  List<Factures> findByContratId(Long contrat_id); 
}
