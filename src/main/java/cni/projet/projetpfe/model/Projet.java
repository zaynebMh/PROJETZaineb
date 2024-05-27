package cni.projet.projetpfe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projet")
public class Projet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String intitule;

    @NotBlank
    @Column(nullable = false)
    private String etablissement;

    @NotNull
    @Column(nullable = false)
    private Double montant;

    @NotNull(message = "{jakarta.validation.constraints.NotNull.message}")
    @Column(nullable = false)
    private Long ref;
	
	 @ManyToOne
	 @JoinColumn(name = "etablissement_id")
	 private Etablissement Etablissement;
   
}
