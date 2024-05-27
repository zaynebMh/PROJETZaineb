package cni.projet.projetpfe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class AoDTO {
	@NotNull
    private Long id;
	@NotBlank
	@JsonProperty("intitule")
    private String intitule;
	@NotBlank
	@JsonProperty("etablissement")
	private String etablissement;
	@NotNull
	@JsonProperty("montant")
    private double montant;
	@NotNull
	@JsonProperty("ref")
    private Long ref ;
	@NotNull
	@Positive(message = "ProjetId must be specified and greater than zero")
    private Long projet_id;
	@NotNull
    private Set<Long> contratIds= Collections.emptySet(); 
	@NotNull
    private Set<Long> facturesIds= Collections.emptySet();
	
	

}
