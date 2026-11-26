package controlleur;

import gestion.GestionProjets;
import vue.VueListeProjets;
import vue.VueProjet;

import java.time.LocalDate;
import java.util.List;

public class ControlleurProjets {
    private final GestionProjets gestion;
    private VueListeProjets vueListe;
    private VueProjet vueProjet;

    public ControlleurProjets(GestionProjets gestion) {
        this.gestion = gestion;
    }

    public void demarrer() {
        this.vueListe = new VueListeProjets(gestion, this);
        vueListe.setVisible(true);
    }

    public void ouvrirProjet(String nomProjet) {
        if (nomProjet == null) {
            return;
        }

        if (this.vueProjet == null) {
            this.vueProjet = new VueProjet(gestion, this);
        }

        vueProjet.setProjetCourant(nomProjet);
        vueProjet.setVisible(true);
        vueProjet.toFront();
    }

    public String ajouterProjet(String nom, String dateStr, String equipe, String nbMembresStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr.trim());
            int nb = Integer.parseInt(nbMembresStr.trim());

            boolean ok = gestion.createProjet(nom.trim(), date, equipe.trim(), nb);
            if (ok) {
                return null;
            }
            return "Erreur : projet invalide ou doublon.";
        } catch (Exception e) {
            return "Format de date (AAAA-MM-JJ) ou de nombre invalide.";
        }
    }

    public boolean supprimerProjet(String nom) {
        if (nom == null) {
            return false;
        }

        return gestion.supprimeProjet(nom);
    }

    public List<String> verifierProjet(String nom) {
        return gestion.verifierProjet(nom);
    }

    public String modifierNom(String ancienNom, String nouveauNom) {
        if (ancienNom == null || nouveauNom == null) {
            return "Projet null.";
        }
        nouveauNom = nouveauNom.trim();

        if (nouveauNom.equals(ancienNom)) {
            return "Meme nom.";
        }

        if (!gestion.updateProjetNom(ancienNom, nouveauNom)) {
            return "Nom invalide ou deja pris.";
        }

        return null;
    }

    public String modifierDateDebut(String nomProjet, String dateStr) {
        if (nomProjet == null) return "Projet null.";
        try {
            LocalDate date = LocalDate.parse(dateStr.trim());
            if (!gestion.updateProjetDateDeb(nomProjet, date)) {
                return "Date de debut invalide.";
            }
        } catch (Exception ex) {
            return "Format attendu : AAAA-MM-JJ";
        }

        return null;
    }

    public String modifierDateFin(String nomProjet, String dateStr) {
        if (nomProjet == null) return "Projet null.";
        try {
            LocalDate date = LocalDate.parse(dateStr.trim());
            if (!gestion.updateProjetDateFin(nomProjet, date)) {
                return "Date de fin invalide.";
            }
        } catch (Exception ex) {
            return "Format attendu : AAAA-MM-JJ";
        }

        return null;
    }

    public String ajouterTache(String nomProjet, String intitule, String dureeStr, boolean terminee, String nomEquipe) {
        if (nomProjet == null) return "Projet null.";
        try {
            int duree = Integer.parseInt(dureeStr.trim());
            boolean ok = gestion.ajouterTache(nomProjet, intitule.trim(), duree, terminee, nomEquipe);
            if (ok) {
                return null;
            } else {
                return "Tache invalide.";
            }
        } catch (Exception ex) {
            return "Duree invalide.";
        }
    }

    public boolean supprimerTache(String nomProjet, String intitule) {
        if (nomProjet == null || intitule == null) {
            return false;
        }
        return gestion.supprimerTache(nomProjet, intitule);
    }

    public String getAffichageHierarchique() {
        return gestion.afficherHierarchique();
    }

    public String getAffichagePlat() {
        return gestion.afficherTachesAPlat();
    }

}
