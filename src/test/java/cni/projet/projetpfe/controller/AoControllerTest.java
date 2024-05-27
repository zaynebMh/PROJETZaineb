package cni.projet.projetpfe.controller;

import static org.mockito.Mockito.*;
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

import cni.projet.projetpfe.model.AoDTO;
import cni.projet.projetpfe.service.AoService;

@WebMvcTest(AoController.class)
public class AoControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private AoService aoService;

    @Test
    public void testGetAllAOs() throws Exception {
        AoDTO ao1 = new AoDTO();
        ao1.setId(1L);
        ao1.setIntitule("Appel d'offre 1");
        ao1.setMontant(250000.0);
        ao1.setRef(null);
        ao1.setProjet_id(1L);
        
        AoDTO ao2 = new AoDTO();
        ao2.setId(2L);
        ao2.setIntitule("Appel d'offre 2");
        ao2.setMontant(350000.0);
        ao2.setMontant(55500.000);
        ao2.setRef(null);
        ao2.setProjet_id(2L);
        
        List<AoDTO> aoList = Arrays.asList(ao1, ao2);

        when(aoService.getAllAOs()).thenReturn(aoList);

        mockMvc.perform(get("/aos"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreateAO() throws Exception {
        AoDTO ao = new AoDTO();
        ao.setId(5L);
        ao.setIntitule(" new ao ");
        ao.setEtablissement(" new etab");
        ao.setMontant(25000.9000);
        ao.setRef(null);
        ao.setProjet_id(2L);

        when(aoService.createAO(any(AoDTO.class))).thenReturn(ao);

        mockMvc.perform(post("/createAO")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testUpdateAO() throws Exception {
        AoDTO ao = new AoDTO();
        ao.setId(1L);
        ao.setIntitule("ao 1");
        ao.setEtablissement("etab 1");
        ao.setMontant(25000.9000);
        ao.setRef(null);
        ao.setProjet_id(2L);

        when(aoService.getAoById(1L)).thenReturn(ao);
        when(aoService.updateAO(any(AoDTO.class))).thenReturn(ao);

        mockMvc.perform(put("/updateAO")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"intitule\": \"Nouveau intitule\", \"montant\": 100.0, \"ref\": \"REF123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.intitule").value("Nouveau intitule"))
                .andExpect(jsonPath("$.montant").value(100.0))
                .andExpect(jsonPath("$.ref").value("REF123"));
    }

    @Test
    public void testDeleteAO() throws Exception {
        AoDTO ao = new AoDTO();
        ao.setId(3L);
        ao.setIntitule("updated projet");
        ao.setEtablissement("updated etab");
        ao.setMontant(25000.9000);
        ao.setRef(null);
        ao.setProjet_id(2L);

        when(aoService.getAoById(1L)).thenReturn(ao);

        mockMvc.perform(delete("/deleteAo/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deleted").value(true));
    }
}
