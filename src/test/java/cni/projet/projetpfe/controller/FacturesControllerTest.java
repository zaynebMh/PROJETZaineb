package cni.projet.projetpfe.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cni.projet.projetpfe.model.FacturesDTO;
import cni.projet.projetpfe.service.FacturesService;

@WebMvcTest(FacturesController.class)
public class FacturesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturesService facturesService;

    @Test
    public void testGetAllFactures() throws Exception {
    	FacturesDTO facture1 = new FacturesDTO();
    	facture1.setId(1L);
    	facture1.setIntitule("Facture 1");
    	facture1.setEtablissement("Etab 1");
    	facture1.setMontant(1000.0);
    	facture1.setRef(123L);
    	facture1.setContrat_id(1L);
    	facture1.setProjet_id(1L);

    	FacturesDTO facture2 = new FacturesDTO();
    	facture2.setId(2L);
    	facture2.setIntitule("Facture 2");
    	facture2.setEtablissement("Etab 2");
    	facture2.setMontant(1500.0);
    	facture2.setRef(456L);
    	facture2.setContrat_id(2L);
    	facture2.setProjet_id(2L);

        List<FacturesDTO> factures = Arrays.asList(facture1, facture2);

        when(facturesService.getAllFactures()).thenReturn(factures);

        mockMvc.perform(get("/factures"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreateFacture() throws Exception {
    	FacturesDTO factureToSave = new FacturesDTO();
    	factureToSave.setId(1L);
    	factureToSave.setIntitule("Nouvelle facture");
    	factureToSave.setEtablissement("Nouvel établissement");
    	factureToSave.setMontant(2000.0);
    	factureToSave.setRef(789L);
    	factureToSave.setContrat_id(3L);
    	factureToSave.setProjet_id(3L);

    	
    	FacturesDTO savedFacture = new FacturesDTO();
    	savedFacture.setId(factureToSave.getId());
    	savedFacture.setIntitule(factureToSave.getIntitule());
    	savedFacture.setEtablissement(factureToSave.getEtablissement());
    	savedFacture.setMontant(factureToSave.getMontant());
    	savedFacture.setRef(factureToSave.getRef());
    	savedFacture.setContrat_id(factureToSave.getContrat_id());
    	savedFacture.setProjet_id(factureToSave.getProjet_id());

        when(facturesService.createFacture(factureToSave)).thenReturn(savedFacture);

        mockMvc.perform(post("/createFacture")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedFacture.getId()));
    }

    @Test
    public void testGetFactureById() throws Exception {
        Long factureId = 1L;
        FacturesDTO facture = new FacturesDTO();
        facture.setId(1L);
        facture.setIntitule("Facture A");
        facture.setEtablissement("Etablissement X");
        facture.setMontant(1500.0);
        facture.setRef(123L);
        facture.setContrat_id(5L);
        facture.setProjet_id(10L);
        when(facturesService.getFactureById(factureId)).thenReturn(facture);

        mockMvc.perform(get("/facture/{id}", factureId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(facture.getId()));
    }

    @Test
    public void testUpdateFacture() throws Exception {
        Long factureId = 1L;
        FacturesDTO existingFacture = new FacturesDTO();
        existingFacture.setId(1L);
        existingFacture.setIntitule("Facture existante");
        existingFacture.setEtablissement("Etablissement Y");
        existingFacture.setMontant(2500.0);
        existingFacture.setRef(456L);
        existingFacture.setContrat_id(7L);
        existingFacture.setProjet_id(20L);

        when(facturesService.getFactureById(factureId)).thenReturn(existingFacture);
        FacturesDTO updatedFacture = new FacturesDTO();
        updatedFacture.setId(1L);
        updatedFacture.setIntitule("Facture mise à jour");
        updatedFacture.setEtablissement("Nouvel établissement");
        updatedFacture.setMontant(3000.0);
        updatedFacture.setRef(789L);
        updatedFacture.setContrat_id(8L);
        updatedFacture.setProjet_id(25L);

        when(facturesService.updateFacture(existingFacture)).thenReturn(updatedFacture);

        mockMvc.perform(put("/updateFacture/{id}", factureId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedFacture.getId()));
    }

    @Test
    public void testDeleteFacture() throws Exception {
        Long factureId = 1L;
        FacturesDTO existingFacture = new FacturesDTO();
        existingFacture.setId(1L);
        existingFacture.setIntitule("Facture existante");
        existingFacture.setEtablissement("Etablissement Y");
        existingFacture.setMontant(2500.0);
        existingFacture.setRef(456L);
        existingFacture.setContrat_id(7L);
        existingFacture.setProjet_id(20L);
        when(facturesService.getFactureById(factureId)).thenReturn(existingFacture);

        mockMvc.perform(delete("/deleteFacture/{id}", factureId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deleted").value(true));
    }

}
