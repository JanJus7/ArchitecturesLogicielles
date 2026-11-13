package factory;

import gestion.*;

import java.time.LocalDate;

public class ProjetFactory {
    private int projetsCrees = 0;

    public Projet createProjectStandard(String nomProjet, LocalDate dateDebut, String nomEquipe, int nbMembres) {
        Projet pStd = new Projet(nomProjet, dateDebut, dateDebut.plusWeeks(3));

        Equipe equipe = new Equipe(nomEquipe, nbMembres);
        pStd.ajouterEquipe(equipe);

        Tache t1 = new Tache("spec", 1, false, null);
        Tache t2 = new Tache("dev", 1, false, null);
        Tache t3 = new Tache("tests", 1, false, null);
        t1.setEquipeAffectee(equipe);
        t2.setEquipeAffectee(equipe);
        t3.setEquipeAffectee(equipe);

        pStd.ajouterTache(t1);
        pStd.ajouterTache(t2);
        pStd.ajouterTache(t3);

        this.projetsCrees++;

        return pStd;
    }

    public int getProjetsCrees() {
        return projetsCrees;
    }
}
