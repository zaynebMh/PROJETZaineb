package cni.projet.projetpfe.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cni.projet.projetpfe.model.ContratDTO;
import cni.projet.projetpfe.service.ContratService;

@WebMvcTest(ContratController.class)
public class ContratControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContratService contratService;

    @Test
    public void testGetAllContrats() throws Exception {
        ContratDTO contrat1 = new ContratDTO();
        contrat1.setId(1L);
        contrat1.setIntitule("Contrat 1");
        contrat1.setEtablissement("Etablissement 1");
        contrat1.setMontant(25000.0);
        contrat1.setRef(123L);
        ContratDTO contrat2 = new ContratDTO();
        contrat2.setId(2L);
        contrat2.setIntitule("Contrat 2");
        contrat2.setEtablissement("Etablissement 2");
        contrat2.setMontant(35000.0);
        contrat2.setRef(456L);
        List<ContratDTO> contrats = Arrays.asList(contrat1, contrat2);

        when(contratService.getAllContrats()).thenReturn(contrats);

        mockMvc.perform(get("/contrats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(contrats.size()));
    }

    @Test
    public void testGetContratById() throws Exception {
        ContratDTO contrat = new ContratDTO();
        contrat.setId(1L);
        contrat.setIntitule("Contrat de prestation de service");
        contrat.setEtablissement("Entreprise ABC");
        contrat.setMontant(5000.0);
        contrat.setRef(12345L);
        when(contratService.getContratById(1L)).thenReturn(contrat);

        mockMvc.perform(get("/contrat/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(contrat.getId()))
                .andExpect(jsonPath("$.intitule").value(contrat.getIntitule()))
                .andExpect(jsonPath("$.etablissement").value(contrat.getEtablissement()))
                .andExpect(jsonPath("$.montant").value(contrat.getMontant()))
                .andExpect(jsonPath("$.ref").value(contrat.getRef()));
    }

    @Test
    public void testUpdateContrat() throws Exception {
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setId(1L);
        contratDTO.setIntitule("Contrat de prestation de service");
        contratDTO.setEtablissement("Entreprise ABC");
        contratDTO.setMontant(5000.0);
        contratDTO.setRef(12345L);

        when(contratService.getContratById(1L)).thenReturn(contratDTO);
        when(contratService.updateContrat(contratDTO)).thenReturn(contratDTO);

        mockMvc.perform(put("/updateContrat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"intitule\":\"Nouvel intitule\", \"montant\":2000.0, \"etablissement\":\"Nouvel Etablissement\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(contratDTO.getId()))
                .andExpect(jsonPath("$.intitule").value("Nouvel intitule"))
                .andExpect(jsonPath("$.etablissement").value("Nouvel Etablissement"))
                .andExpect(jsonPath("$.montant").value(2000.0))
                .andExpect(jsonPath("$.ref").value(contratDTO.getRef()));
    }

    @Test
    public void testDeleteContrat() throws Exception {
        ContratDTO contratDTO = new ContratDTO();
        contratDTO.setId(1L);
        contratDTO.setIntitule("Contrat de prestation de service");
        contratDTO.setEtablissement("Entreprise ABC");
        contratDTO.setMontant(5000.0);
        contratDTO.setRef(12345L);

        when(contratService.getContratById(1L)).thenReturn(contratDTO);

        mockMvc.perform(delete("/deleteContrat/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deleted").value(true));
    }
}
