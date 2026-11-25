package gestion;

import verification.VerificationProjet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CatalogProjets {
    private final List<Projet> projets;
    private final VerificationProjet validator;

    public CatalogProjets() {
        this.projets = new ArrayList<>();
        this.validator = new VerificationProjet();
    }

    public Projet getProjetParNom(String nomProj) {
        for (Projet p : this.projets) {
            if (Objects.equals(p.getNom(), nomProj)) {
                return p;
            }
        }

        return null;
    }

    public boolean ajouterProjet(Projet projet) {

        boolean isValide = validator.estValide(projet);

        if (!isValide) {
            return false;
        }

        if (this.getProjetParNom(projet.getNom()) != null) {
            return false;
        }

        this.projets.add(projet);
        return true;
    }

    public List<String> ajouterProjets(List<Projet> nouveauxProjets) {
        List<String> erreurs = new ArrayList<>();
        if (nouveauxProjets == null) {
            return erreurs;
        }

        for (Projet elem : nouveauxProjets) {
            if (elem == null) {
                erreurs.add("Projet null ignore");
                continue;
            }
            boolean status = this.ajouterProjet(elem);
            if (!status) {
                erreurs.add("Projet: " + elem.getNom() + " non ajoute (doublon ou invalide)");
            }
        }

        return erreurs;
    }

    /*
     * UPDATES
     */

    public boolean updateProjetNom(String nomProj, String newNomProj) {
        Projet p = this.getProjetParNom(nomProj);

        if (p == null) {
            return false;
        }

        if (this.getProjetParNom(newNomProj) != null) {
            return false;
        }

        p.setNom(newNomProj);
        boolean isValide = validator.estValide(p);

        if (!isValide) {
            p.setNom(nomProj);
            return false;
        }
        return true;
    }

    public boolean updateProjetDateDeb(String nomProj, LocalDate newDateDeb) {
        Projet p = this.getProjetParNom(nomProj);

        if (p == null) {
            return false;
        }

        LocalDate prevDateDeb = p.getDateDebut();

        p.setDateDebut(newDateDeb);

        boolean isValide = validator.estValide(p);

        if (!isValide) {
            p.setDateDebut(prevDateDeb);
            return false;
        }

        return true;
    }

    public boolean updateProjetDateFin(String nomProj, LocalDate newDateFin) {
        Projet p = this.getProjetParNom(nomProj);

        if (p == null) {
            return false;
        }

        LocalDate prevDateFin = p.getDateFin();

        p.setDateFin(newDateFin);

        boolean isValide = validator.estValide(p);

        if (!isValide) {
            p.setDateFin(prevDateFin);
            return false;
        }

        return true;
    }

    public boolean updateProjetEquipes(String nomProj, List<Equipe> newEquipes) {
        Projet p = this.getProjetParNom(nomProj);

        if (p == null) {
            return false;
        }

        List<Equipe> prevEquipes = p.getEquipes();

        p.setEquipes(newEquipes);

        boolean isValide = validator.estValide(p);

        if (!isValide) {
            p.setEquipes(prevEquipes);
            return false;
        }

        return true;
    }

    public boolean updateProjetTaches(String nomProj, List<Tache> newTaches) {
        Projet p = this.getProjetParNom(nomProj);

        if (p == null) {
            return false;
        }

        List<Tache> prevTaches = p.getTaches();

        p.setTaches(newTaches);

        boolean isValide = validator.estValide(p);

        if (!isValide) {
            p.setTaches(prevTaches);
            return false;
        }

        return true;
    }

    public boolean supprimeProjet(String nomProjet) {
        Projet p = this.getProjetParNom(nomProjet);

        if (p != null) {
            this.projets.remove(p);
            return true;
        }

        return false;
    }

    public List<Projet> getListeProjet() {
        return new ArrayList<>(this.projets);
    }

}
