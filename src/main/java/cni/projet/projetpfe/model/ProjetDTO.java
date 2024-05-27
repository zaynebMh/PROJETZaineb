package cni.projet.projetpfe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class ProjetDTO {
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
    private Long etablissement_id;
    
    private Set<Long> aoIds = Collections.emptySet(); 

    private Set<Long> contratIds = Collections.emptySet(); 
   
    private Set<Long> facturesIds = Collections.emptySet(); 
}