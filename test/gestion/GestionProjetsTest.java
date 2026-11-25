package gestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionProjetsTest {

    private GestionProjets gestion;

    @BeforeEach
    void setUp() {
        gestion = new GestionProjets();
    }

    @Test
    void creationStandardEtUniciteNom() {
        LocalDate debut = LocalDate.of(2026, 1, 10);

        assertTrue(gestion.createProjet("P1", debut, "TeamA", 4));
        assertFalse(gestion.createProjet("P1", debut, "TeamB", 2));
        assertEquals(1, gestion.getListeProjet().size());
        assertNotNull(gestion.getProjetParNom("P1"));
    }

    @Test
    void creationAvecParametresInvalidesEchoueSansNPE() {
        assertFalse(gestion.createProjet("", LocalDate.of(2026, 1, 1), "TeamA", 3));
        assertFalse(gestion.createProjet(null, LocalDate.of(2026, 1, 1), "TeamA", 3));
        // Jesli poniższy test rzuci NPE, zabezpiecz deb == null w ProjetFactory.createProjectStandard
        assertDoesNotThrow(() -> assertFalse(gestion.createProjet("PNullDate", null, "TeamA", 3)));
    }

    @Test
    void updateNomValideEtRollbackSiInvalideOuDoublon() {
        gestion.createProjet("P1", LocalDate.of(2026, 1, 1), "T1", 2);
        gestion.createProjet("P2", LocalDate.of(2026, 1, 1), "T2", 2);

        assertFalse(gestion.updateProjetNom("P1", "P2"));
        assertFalse(gestion.updateProjetNom("P1", null));
        assertFalse(gestion.updateProjetNom("P1", "   "));
        assertFalse(gestion.updateProjetNom("Inexistant", "P3"));
        assertNotNull(gestion.getProjetParNom("P1"));

        assertTrue(gestion.updateProjetNom("P1", "P1_Renomme"));
        assertNull(gestion.getProjetParNom("P1"));
        assertNotNull(gestion.getProjetParNom("P1_Renomme"));
    }

    @Test
    void updatesDatesEquipesTachesRejettentNullEtFontRollback() {
        LocalDate debut = LocalDate.of(2026, 2, 1);
        gestion.createProjet("P1", debut, "TeamA", 3);
        Projet p = gestion.getProjetParNom("P1");
        LocalDate finInit = p.getDateFin();

        assertFalse(gestion.updateProjetDateDeb("P1", null));
        assertEquals(debut, p.getDateDebut());

        assertFalse(gestion.updateProjetDateFin("P1", null));
        assertFalse(gestion.updateProjetDateFin("P1", LocalDate.of(2025, 1, 1)));
        assertEquals(finInit, p.getDateFin());

        assertFalse(gestion.updateProjetEquipes("P1", null));
        assertFalse(p.getEquipes().isEmpty());

        assertFalse(gestion.updateProjetTaches("P1", null));
        assertFalse(p.getTaches().isEmpty());

        assertFalse(gestion.updateProjetDateDeb("Inexistant", debut));
        assertTrue(gestion.updateProjetDateFin("P1", LocalDate.of(2026, 6, 1)));
        assertEquals(LocalDate.of(2026, 6, 1), p.getDateFin());
    }

    @Test
    void suppressionEtVerification() {
        gestion.createProjet("P1", LocalDate.of(2026, 1, 1), "TeamA", 3);

        assertTrue(gestion.verifierProjet("P1").isEmpty());
        assertFalse(gestion.verifierProjet("Inexistant").isEmpty());

        assertTrue(gestion.supprimeProjet("P1"));
        assertFalse(gestion.supprimeProjet("P1"));
        assertTrue(gestion.getListeProjet().isEmpty());
    }

    @Test
    void importJSONGereDoublonsDansFichierEtAvecExistants() {
        List<String> rapportDoublons = gestion.importerProjetsJSON("test-data/doublons.json");
        assertEquals(1, gestion.getListeProjet().size());
        assertEquals(1, rapportDoublons.size());

        List<String> rapportValide = gestion.importerProjetsJSON("test-data/valide.json");
        assertTrue(rapportValide.isEmpty());
        assertEquals(3, gestion.getListeProjet().size());

        // Ponowny import tego samego pliku -> oba projekty odrzucone jako duplikaty
        List<String> rapportReimport = gestion.importerProjetsJSON("test-data/valide.json");
        assertEquals(2, rapportReimport.size());
        assertEquals(3, gestion.getListeProjet().size());
    }

    @Test
    void importJSONCasseRetourneRapportSansException() {
        List<String> rapport = assertDoesNotThrow(
                () -> gestion.importerProjetsJSON("test-data/syntaxe_cassee.json")
        );
        assertFalse(rapport.isEmpty());
        assertTrue(gestion.getListeProjet().isEmpty());
    }
}