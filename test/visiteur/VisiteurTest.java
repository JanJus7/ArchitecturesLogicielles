package visiteur;

import gestion.GestionProjets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisiteurTest {

    @Test
    void affichageHierarchiqueContientProjetsEquipesEtTaches() {
        GestionProjets gestion = new GestionProjets();
        gestion.importerProjetsJSON("test-data/valide.json");

        String res = gestion.afficherHierarchique();

        assertTrue(res.contains("Alpha"));
        assertTrue(res.contains("Beta"));
        assertTrue(res.contains("Backend"));
        assertTrue(res.contains("Frontend"));
        assertTrue(res.contains("API"));
        assertTrue(res.contains("UI"));
        assertTrue(res.contains("Terminee"));
        assertTrue(res.contains("En cours"));
    }

    @Test
    void affichagePlatContientTachesAvecProjetEquipeEtEtat() {
        GestionProjets gestion = new GestionProjets();
        gestion.importerProjetsJSON("test-data/valide.json");

        String res = gestion.afficherTachesAPlat();
        String[] lignes = res.trim().split("\n");

        assertEquals(3, lignes.length);
        assertTrue(res.contains("API") && res.contains("Alpha") && res.contains("Backend") && res.contains("En cours"));
        assertTrue(res.contains("UI") && res.contains("Alpha") && res.contains("Frontend") && res.contains("Terminee"));
        assertTrue(res.contains("Setup") && res.contains("Beta") && res.contains("Core"));
    }

    @Test
    void visiteurNullOuCatalogueVideNePlantePas() {
        GestionProjets gestion = new GestionProjets();

        assertEquals("", gestion.afficherHierarchique());
        assertEquals("", gestion.afficherTachesAPlat());
        assertDoesNotThrow(() -> gestion.appliquerVisiteur(null));
    }
}