package gestion;

import factory.*;
import verification.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GestionProjets {
    private final ProjetFactory factory;
    private final List<Projet> projets;
    private final VerificationProjet validator;

    public GestionProjets() {
        this.factory = new ProjetFactory();
        this.projets = new ArrayList<>();
        this.validator = new VerificationProjet();

    }

    public boolean createProjet(String nomProj, LocalDate deb, String nomEquipe, int nbMembres) {

        if (this.getProjetParNom(nomProj) != null) {
            return false;
        }

        Projet projet = this.factory.createProjectStandard(nomProj, deb, nomEquipe, nbMembres);

        boolean isValide = validator.estValide(projet);

        if (isValide) {
            this.projets.add(projet);
            return true;
        }

        return false;
    }

    public Projet getProjetParNom(String nomProj) {
        for (Projet p : this.projets) {
            if (Objects.equals(p.getNom(), nomProj)) {
                return p;
            }
        }

        return null;
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

        if (newNomProj != null && !newNomProj.isEmpty()) {
            p.setNom(newNomProj);
        }

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

        if (newDateDeb != null) {
            p.setDateDebut(newDateDeb);
        }

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

        if (newDateFin != null) {
            p.setDateFin(newDateFin);
        }

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

        if (newEquipes != null) {
            p.setEquipes(newEquipes);
        }

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

        if (newTaches != null) {
            p.setTaches(newTaches);
        }

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

    public ArrayList<Projet> getListeProjet() {
        return new ArrayList<>(this.projets);
    }

}