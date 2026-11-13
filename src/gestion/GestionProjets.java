package gestion;

import factory.*;
import verification.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GestionProjets {
    ProjetFactory factory;
    List<Projet> projets;

    public GestionProjets() {
        this.factory = new ProjetFactory();
        this.projets = new ArrayList<>();

    }

    public void createProjet(String nomProj, LocalDate deb, String nomEquipe, int nbMembres) {

        if (deb == null) {
            deb = LocalDate.now();
        }
        this.projets.add(this.factory.createProjectStandard(nomProj, deb, nomEquipe, nbMembres));
    }

    public Projet getProjetParNom(String nomProj) {
        for (Projet p : this.projets) {
            if (Objects.equals(p.getNom(), nomProj)) {
                return p;
            }
        }

        return null;
    }

    //TODO: przepisać to żeby było zgodne z metodami pisania: mięcej mniejszych metod niż jedna taka jeśli dobrze rozumiem. ale to potem.
    public void updateProjet(String nomProj, String newNomProj, LocalDate deb, LocalDate fin, List<Equipe> equipes, List<Tache> taches) {
        Projet p = this.getProjetParNom(nomProj);

        // TODO: do tego projet może przecież nie istnieć - get zwróci null wtedy nic nie robimy. nooo tu trzeba modyfikacji

        if (newNomProj != null) {
            p.setNom(newNomProj);
        }

        if (deb != null) {
            p.setDateDebut(deb);
        }

        if (fin != null) {
            p.setDateFin(fin);
        }

        if (equipes != null) {
            p.setEquipes(equipes);
        }

        if (taches != null) {
            p.setTaches(taches);
        }

    }

    public void supprimeProjet(String nomProjet) {
        Projet p = this.getProjetParNom(nomProjet);

        if (p != null) {
            this.projets.remove(p);
        }
    }

}