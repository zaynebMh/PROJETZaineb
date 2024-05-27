package cni.projet.projetpfe.model;



import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class FacturesDTO {
	@NotNull
    private Long id;
	@NotBlank
	@JsonProperty("intitule")
    private String intitule;
	@NotBlank
	@JsonProperty("etablissement")
	private String etablissement ;
    @NotNull
    @JsonProperty("montant")
    private double montant;
    @NotNull
    @JsonProperty("ref")
    private Long ref ;
    @NotNull
    private Long contrat_id;
    @NotNull
    private Long projet_id;
}