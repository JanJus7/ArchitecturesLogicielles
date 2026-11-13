package verification;
import factory.*;
import gestion.*;

import java.util.HashSet;
import java.util.Set;

public enum ErreursPossibles {
    NULL_PROJET("Projet ne peut pas etre null.") {
        @Override
        public boolean estValide(Projet projet) {
            return projet != null;
        }
    },

    NOM_VIDE("Nom projet ne peut pas etre vide ou null.") {
        @Override
        public boolean estValide(Projet projet) {
            return projet.getNom() != null && !projet.getNom().trim().isEmpty();
        }
    },

    DATE_INVALIDE("La date de fin ne peut pas etre plus petite que la date de debut.") {
        @Override
        public boolean estValide(Projet projet) {
            if (projet.getDateDebut() == null || projet.getDateFin() == null) {
                return false;
            }

            return projet.getDateFin().isBefore(projet.getDateDebut());
        }
    },

    DUREE_TACHE_INVALIDE("Le duree de la tache ne peut pas etre negatif.") {
        @Override
        public boolean estValide(Projet projet) {
            for (Tache t : projet.getTaches()) {
                if (t.getDuree() <= 0) {
                    return false;
                }
            }

            return true;

        }
    },

    DOUBLES_NOMS_EQUIPE("Noms d’équipes distincts dans un projet.") {
        @Override
        public boolean estValide(Projet projet) {
            Set<String> nomsVus = new HashSet<>();
            for (Equipe e : projet.getEquipes()) {
                if (e.getNom() == null || !nomsVus.add(e.getNom().trim().toLowerCase())) {
                    return false;
                }
            }
            return true;
        }
    },

    UNE_PROJET_PAR_EQUIPE("affectation de chaque tache a une equipe du projet") {
        @Override
        public boolean estValide (Projet projet){
            if (projet.getTaches() == null || projet.getTaches().isEmpty()) {
                return true;
            }

            if (projet.getEquipes() == null) {
                return false;
            }

            for (Tache t : projet.getTaches()) {
                Equipe eAf = t.getEquipeAffectee();
                if (eAf == null || !projet.getEquipes().contains(eAf)) {
                    return false;
                }
            }

            return true;
        }
    };




    private final String message;

    ErreursPossibles(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public abstract boolean estValide(Projet projet);

}
