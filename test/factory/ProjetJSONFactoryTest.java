package factory;

import gestion.Projet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjetJSONFactoryTest {

    private ProjetJSONFactory factory;

    @BeforeEach
    void setUp() {
        factory = new ProjetJSONFactory();
    }

    @Test
    void importFichierValideCreeTousLesProjetsSansErreur() {
        List<Projet> projets = factory.createProjetsFromJSON("test-data/valide.json");

        assertEquals(2, projets.size());
        assertEquals(2, factory.getProjetsCrees());
        assertTrue(factory.getErreurs().isEmpty());
        assertEquals("Alpha", projets.get(0).getNom());
        assertEquals(2, projets.get(0).getEquipes().size());
        assertEquals(2, projets.get(0).getTaches().size());
    }

    @Test
    void importFichierSansDateNePlantePasEtGardeLeProjetValide() {
        List<Projet> projets = assertDoesNotThrow(
                () -> factory.createProjetsFromJSON("test-data/sans_date.json")
        );

        assertEquals(1, projets.size());
        assertEquals("ProjetValideApresErreur", projets.get(0).getNom());
        assertEquals(1, factory.getProjetsCrees());
        assertFalse(factory.getErreurs().isEmpty());
    }

    @Test
    void importErreursMetierRejetteToutEtRemplitRapport() {
        List<Projet> projets = assertDoesNotThrow(
                () -> factory.createProjetsFromJSON("test-data/erreurs_metier.json")
        );

        assertTrue(projets.isEmpty());
        assertEquals(0, factory.getProjetsCrees());
        assertTrue(factory.getErreurs().size() >= 4);
    }

    @Test
    void importSyntaxeCasseeNeLancePasException() {
        List<Projet> projets = assertDoesNotThrow(
                () -> factory.createProjetsFromJSON("test-data/syntaxe_cassee.json")
        );

        assertTrue(projets.isEmpty());
        assertFalse(factory.getErreurs().isEmpty());
    }

    @Test
    void fichierInexistantOuNullGeresProprement() {
        List<Projet> p1 = assertDoesNotThrow(
                () -> factory.createProjetsFromJSON("test-data/introuvable.json")
        );
        assertTrue(p1.isEmpty());
        assertFalse(factory.getErreurs().isEmpty());

        List<Projet> p2 = assertDoesNotThrow(
                () -> factory.createProjetsFromJSON(null)
        );
        assertTrue(p2.isEmpty());
        assertFalse(factory.getErreurs().isEmpty());
    }
}