package cni.projet.projetpfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cni.projet.projetpfe.model.User;
import cni.projet.projetpfe.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean checkPassword(String plainPassword, String hashedPassword) {
       
        return plainPassword.equals(hashedPassword);
    }

	@Override
	public User findByEmail(String email) {
	
		return userRepository.findByEmail(email);
	}

}
