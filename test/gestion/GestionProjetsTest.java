package gestion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

class GestionProjetsTest {

    GestionProjets gp;

    @BeforeEach
    void setUp() {
        gp = new GestionProjets();
        gp.createProjet("p1", LocalDate.now(), "eq1", 7);
    }

    @Test
    void createProjetValide() {
        assertTrue(gp.createProjet("p2", LocalDate.now(), "eq2", 6));
    }

    @Test
    void createProjetNonValide() {
        assertFalse(gp.createProjet("", LocalDate.of(-3,12,30), "eq1", 7));
        assertFalse(gp.createProjet(null, LocalDate.now(), "eq1", 7));
    }

    @Test
    void createProjetExistant() {
        assertFalse(gp.createProjet("p1", LocalDate.now(), "eq1", 7));
    }


//    TODO: Jak będzie mi się nudziło...
//    @Test
//    void getProjetParNomValide() {
//
//    }
//
//    @Test
//    void getProjetParNomNonValide() {
//    }
//
//    @Test
//    void updateProjetNom() {
//    }
//
//    @Test
//    void updateProjetDateDeb() {
//    }
//
//    @Test
//    void updateProjetDateFin() {
//    }
//
//    @Test
//    void updateProjetEquipes() {
//    }
//
//    @Test
//    void updateProjetTaches() {
//    }
//
//    @Test
//    void supprimeProjet() {
//    }
//
//    @Test
//    void getListeProjet() {
//    }
}