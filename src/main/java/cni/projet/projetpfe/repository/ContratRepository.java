package cni.projet.projetpfe.repository;

import cni.projet.projetpfe.model.Contrat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ContratRepository extends JpaRepository<Contrat ,Long> {
	List<Contrat> findByAoProjetId(Long projet_id);
	List<Contrat> findByAoId(Long ao_id);
}
