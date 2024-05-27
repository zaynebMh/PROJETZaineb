package cni.projet.projetpfe.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ao")
public class Ao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String intitule;
    @Column(nullable = false)
    private String etablissement;
    @Column(nullable = false)
    private double montant;
    @Column(nullable = false)
    private Long ref ;
    

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
}
