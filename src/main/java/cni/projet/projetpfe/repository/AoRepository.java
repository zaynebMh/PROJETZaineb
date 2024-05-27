package cni.projet.projetpfe.repository;

import cni.projet.projetpfe.model.Ao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AoRepository extends JpaRepository<Ao ,Long> {
	 List<Ao> findByProjetId(Long projet_id);
}
