package cni.projet.projetpfe.repository;

import cni.projet.projetpfe.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProjetRepository  extends JpaRepository<Projet , Long> {
}
