package verification;

import gestion.Equipe;
import gestion.Projet;
import gestion.Tache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerificationProjetTest {

    private VerificationProjet verifier;

    @BeforeEach
    void setUp() {
        verifier = new VerificationProjet();
    }

    private Projet creerProjetValide() {
        Projet p = new Projet("Valide", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 2, 1));
        Equipe e = new Equipe("Dev", 3);
        p.ajouterEquipe(e);
        p.ajouterTache(new Tache("Code", 5, false, e));
        return p;
    }

    @Test
    void projetValideNeRetourneAucuneErreur() {
        Projet p = creerProjetValide();
        assertTrue(verifier.estValide(p));
        assertTrue(verifier.validation(p).isEmpty());
        assertTrue(verifier.getErreurs(p).isEmpty());
    }

    @Test
    void projetNullDetecteSansException() {
        List<ErreursPossibles> erreurs = verifier.validation(null);
        assertEquals(1, erreurs.size());
        assertEquals(ErreursPossibles.NULL_PROJET, erreurs.get(0));
        assertFalse(verifier.getErreurs(null).isEmpty());
    }

    @Test
    void nomVideOuNullInvalide() {
        Projet p = creerProjetValide();
        p.setNom("   ");
        assertTrue(verifier.validation(p).contains(ErreursPossibles.NOM_VIDE));

        p.setNom(null);
        assertTrue(verifier.validation(p).contains(ErreursPossibles.NOM_VIDE));
    }

    @Test
    void datesInvalidesOuNullDetectees() {
        Projet p = creerProjetValide();
        p.setDateFin(LocalDate.of(2025, 12, 31));
        assertTrue(verifier.validation(p).contains(ErreursPossibles.DATE_INVALIDE));

        p.setDateDebut(null);
        assertTrue(verifier.validation(p).contains(ErreursPossibles.DATE_INVALIDE));
    }

    @Test
    void dateFinEgaleDateDebutEstValide() {
        Projet p = creerProjetValide();
        p.setDateFin(p.getDateDebut());
        assertTrue(verifier.estValide(p));
    }

    @Test
    void dureeTacheZeroOuNegativeInvalide() {
        Projet p = creerProjetValide();
        Equipe e = p.getEquipes().get(0);
        p.ajouterTache(new Tache("Zero", 0, false, e));
        assertTrue(verifier.validation(p).contains(ErreursPossibles.DUREE_TACHE_INVALIDE));
    }

    @Test
    void nomsEquipesDoublesOuNullInvalides() {
        Projet p = creerProjetValide();
        p.ajouterEquipe(new Equipe(" dev ", 2));
        assertTrue(verifier.validation(p).contains(ErreursPossibles.DOUBLES_NOMS_EQUIPE));

        Projet p2 = creerProjetValide();
        p2.ajouterEquipe(new Equipe(null, 2));
        assertTrue(verifier.validation(p2).contains(ErreursPossibles.DOUBLES_NOMS_EQUIPE));
    }

    @Test
    void tacheAffecteeEquipeExterneOuNullInvalide() {
        Projet p = creerProjetValide();
        Equipe externe = new Equipe("Externe", 2);
        p.ajouterTache(new Tache("HorsProjet", 4, false, externe));
        assertTrue(verifier.validation(p).contains(ErreursPossibles.UNE_PROJET_PAR_EQUIPE));

        Projet p2 = creerProjetValide();
        p2.ajouterTache(new Tache("SansEquipe", 4, false, null));
        assertTrue(verifier.validation(p2).contains(ErreursPossibles.UNE_PROJET_PAR_EQUIPE));
    }

    @Test
    void listesNullEtElementsNullNeProvoquentPasNPE() {
        Projet p = creerProjetValide();
        p.setEquipes(null);
        p.setTaches(null);
        assertDoesNotThrow(() -> verifier.validation(p));
        assertFalse(verifier.estValide(p));

        Projet p2 = creerProjetValide();
        List<Tache> tachesAvecNull = new ArrayList<>();
        tachesAvecNull.add(null);
        p2.setTaches(tachesAvecNull);
        assertDoesNotThrow(() -> verifier.validation(p2));
        assertFalse(verifier.estValide(p2));
    }
}