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

import cni.projet.projetpfe.model.EtablissementDTO;
import cni.projet.projetpfe.service.EtablissementService;

@WebMvcTest(EtablissementController.class)
public class EtablissementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EtablissementService etablissementService;

    @Test
    public void testGetAllEtablissements() throws Exception {
        EtablissementDTO etablissement1 = new EtablissementDTO();
        etablissement1.setId(1L);
        etablissement1.setNom("Etablissement 1");
        etablissement1.setAdresse("Adresse 1");
        etablissement1.setProjetIds(Arrays.asList(1L, 2L, 3L));
        etablissement1.setUser_id(123L);
        
        EtablissementDTO etablissement2 = new EtablissementDTO();
        etablissement2.setId(2L);
        etablissement2.setNom("Etablissement 2");
        etablissement2.setAdresse("Adresse 2");
        etablissement2.setProjetIds(Arrays.asList(4L, 5L));
        etablissement2.setUser_id(456L);
        
        List<EtablissementDTO> etablissements = Arrays.asList(etablissement1, etablissement2);

        when(etablissementService.getAllEtablissements()).thenReturn(etablissements);

        mockMvc.perform(get("/etablissements/getAllEtablissements"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(etablissements.size()));
    }

    @Test
    public void testGetEtablissementById() throws Exception {
        EtablissementDTO etablissement = new EtablissementDTO();
        etablissement.setId(1L);
        etablissement.setNom("Etablissement test");
        etablissement.setAdresse("123 Rue de Test");
        etablissement.setProjetIds(Arrays.asList(1L, 2L, 3L));
        etablissement.setUser_id(123L);

        when(etablissementService.getEtablissementById(1L)).thenReturn(etablissement);

        mockMvc.perform(get("/etablissements/Etablissement/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(etablissement.getId()))
                .andExpect(jsonPath("$.nom").value(etablissement.getNom()))
                .andExpect(jsonPath("$.adresse").value(etablissement.getAdresse()))
                .andExpect(jsonPath("$.projetIds").isArray())
                .andExpect(jsonPath("$.projetIds.length()").value(3))
                .andExpect(jsonPath("$.projetIds[0]").value(1))
                .andExpect(jsonPath("$.user_id").value(etablissement.getUser_id()));
    }

    @Test
    public void testCreateEtablissement() throws Exception {
        EtablissementDTO etablissementDTO = new EtablissementDTO();
        etablissementDTO.setId(1L);
        etablissementDTO.setNom("Etablissement test");
        etablissementDTO.setAdresse("123 Rue de Test");
        etablissementDTO.setProjetIds(Arrays.asList(1L, 2L, 3L));
        etablissementDTO.setUser_id(123L);

        when(etablissementService.createEtablissement(etablissementDTO)).thenReturn(etablissementDTO);

        mockMvc.perform(post("/etablissements/createEtablissement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"nom\":\"Etablissement test\",\"adresse\":\"123 Rue de Test\",\"projetIds\":[1,2,3],\"user_id\":123}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(etablissementDTO.getId()))
                .andExpect(jsonPath("$.nom").value(etablissementDTO.getNom()))
                .andExpect(jsonPath("$.adresse").value(etablissementDTO.getAdresse()))
                .andExpect(jsonPath("$.projetIds").isArray())
                .andExpect(jsonPath("$.projetIds.length()").value(3))
                .andExpect(jsonPath("$.projetIds[0]").value(1))
                .andExpect(jsonPath("$.user_id").value(etablissementDTO.getUser_id()));
    }

    @Test
    public void testUpdateEtablissement() throws Exception {
        EtablissementDTO etablissementDTO = new EtablissementDTO();
        etablissementDTO.setId(1L);
        etablissementDTO.setNom("Etablissement test");
        etablissementDTO.setAdresse("123 Rue de Test");
        etablissementDTO.setProjetIds(Arrays.asList(1L, 2L, 3L));
        etablissementDTO.setUser_id(123L);

        when(etablissementService.updateEtablissement(etablissementDTO)).thenReturn(etablissementDTO);

        mockMvc.perform(put("/etablissements/updateEtablissement/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"nom\":\"Etablissement test\",\"adresse\":\"123 Rue de Test\",\"projetIds\":[1,2,3],\"user_id\":123}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(etablissementDTO.getId()))
                .andExpect(jsonPath("$.nom").value(etablissementDTO.getNom()))
                .andExpect(jsonPath("$.adresse").value(etablissementDTO.getAdresse()))
                .andExpect(jsonPath("$.projetIds").isArray())
                .andExpect(jsonPath("$.projetIds.length()").value(3))
                .andExpect(jsonPath("$.projetIds[0]").value(1))
                .andExpect(jsonPath("$.user_id").value(etablissementDTO.getUser_id()));
    }

    @Test
    public void testDeleteEtablissement() throws Exception {
        mockMvc.perform(delete("/etablissements/deleteEtablissement/1"))
                .andExpect(status().isOk());
    }
}
