package cni.projet.projetpfe.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "Etablissement") 
public class Etablissement {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String nom;
	    private String adresse;
	    private Long cout_projet ;
	    private Long cout_contrat ;
	    private Long cout_facture ;
	   
	    @OneToMany(mappedBy = "etablissement", cascade = CascadeType.ALL)
	    private List<Projet> projets = new ArrayList<>();

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
}