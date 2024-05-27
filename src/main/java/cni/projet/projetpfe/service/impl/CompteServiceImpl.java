package cni.projet.projetpfe.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import cni.projet.projetpfe.model.User;

import cni.projet.projetpfe.repository.UserRepository;
import cni.projet.projetpfe.service.CompteService;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {
	@Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllComptes() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getCompteById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createCompte(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateCompte(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    @Override
    public void deleteCompte(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
