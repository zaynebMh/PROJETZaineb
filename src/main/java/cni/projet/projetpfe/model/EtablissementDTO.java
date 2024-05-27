package cni.projet.projetpfe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EtablissementDTO {
	@NotNull
	private Long id;
	@NotBlank
	@JsonProperty("nom")
    private String nom;
	@NotBlank
	@JsonProperty("adresse")
    private String adresse;
	private Long cout_projet ;
    private Long cout_contrat ;
    private Long cout_facture ;
   
	@NotNull
    private List<Long> projetIds;
    @NotNull
    private Long user_id;
}
