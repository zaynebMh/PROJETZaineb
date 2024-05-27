package cni.projet.projetpfe.service;

import cni.projet.projetpfe.model.User;


public interface UserService {
	  void save(User user);
	    User findByUsername(String username);
	    boolean checkPassword(String plainPassword, String hashedPassword);
	    User findByEmail(String email);
	 
}
