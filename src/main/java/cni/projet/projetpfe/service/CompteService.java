package cni.projet.projetpfe.service;

import java.util.List;
import java.util.Optional;

import cni.projet.projetpfe.model.User;

public interface CompteService {
	List<User> getAllComptes();

    Optional<User> getCompteById(Long id);

    User createCompte(User user);

    User updateCompte(Long id, User userDetails);

    void deleteCompte(Long id);
}
