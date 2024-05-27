package cni.projet.projetpfe.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cni.projet.projetpfe.model.Projet;
import cni.projet.projetpfe.repository.ProjetRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProjetController.class)
public class ProjetControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjetRepository projetRepository;

    @Test
    public void testGetAllProjets() throws Exception {
        Projet projet1 = new Projet();
        projet1.setId(1L);
        projet1.setIntitule("Projet 1");
        projet1.setEtablissement("etab 1");
        projet1.setMontant(10000.000);
        projet1.setRef(11L);
      
        Projet projet2 = new Projet();
        projet2.setId(2L);
        projet2.setIntitule("Projet 2");
        projet2.setEtablissement("etab 2");
        projet2.setMontant(20000.0000);
        projet2.setRef(12L);

        List<Projet> projets = Arrays.asList(projet1, projet2);

        when(projetRepository.findAll()).thenReturn(projets);

        mockMvc.perform(get("/projets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].intitule").value("Projet 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].intitule").value("Projet 2"));
    }

    @Test
    public void testCreateProjet() throws Exception {
        Projet projet = new Projet();
        projet.setId(1L);
        projet.setIntitule("Projet test");
        projet.setEtablissement("etab test");
        projet.setMontant(28000.000);
        projet.setRef(0L);

        when(projetRepository.save(any(Projet.class))).thenReturn(projet);

        mockMvc.perform(post("/projets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"intitule\": \"Projet test\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.intitule").value("Projet test"));
    }

    @Test
    public void testGetProjetById() throws Exception {
        Projet projet = new Projet();
        projet.setId(1L);
        projet.setIntitule("Projet test");
        projet.setEtablissement("etab test");
        projet.setMontant(4000.000);
        projet.setRef(0L);
        when(projetRepository.findById(1L)).thenReturn(Optional.of(projet));

        mockMvc.perform(get("/projets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.intitule").value("Projet test"));
        
    }

    @Test
    public void testUpdateProjet() throws Exception {
        Projet existingProjet = new Projet();
        existingProjet.setId(1L);
        existingProjet.setIntitule("Projet existant");
        existingProjet.setEtablissement("etab existant");
        existingProjet.setMontant(2000.000);
        existingProjet.setRef(0L);

        Projet updatedProjet = new Projet();
        updatedProjet.setId(1L);
        updatedProjet.setIntitule("Nouveau nom du projet");
        updatedProjet.setEtablissement("new etap");
        updatedProjet.setMontant(800000.000);
        updatedProjet.setRef(3L);

        when(projetRepository.findById(1L)).thenReturn(Optional.of(existingProjet));
        when(projetRepository.save(any(Projet.class))).thenReturn(updatedProjet);

        mockMvc.perform(put("/projets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"intitule\": \"Nouveau nom du projet\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.intitule").value("Nouveau nom du projet"));
    }

    @Test
    public void testDeleteProjet() throws Exception {
        when(projetRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/projets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deleted").value(true));
    }
}
