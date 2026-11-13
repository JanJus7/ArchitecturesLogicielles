package gestion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Projet {
    private String nom;
    private LocalDate dateFin;
    private LocalDate dateDebut;
    private List<Equipe> equipes;
    private List<Tache> taches;

    public Projet(String nom, LocalDate dateDebut, LocalDate dateFin) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.equipes = new ArrayList<>();
        this.taches = new ArrayList<>();
    }

    //
    // getters
    //
    public String getNom() {
        return nom;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public List<Equipe> getEquipes() {
        return equipes;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    //
    // setters
    //
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setEquipes(List<Equipe> equipe) {
        this.equipes = equipe;
    }

    public void setTaches(List<Tache> tache) {
        this.taches = tache;
    }

    //
    // Array element operations
    //

    public void ajouterEquipe(Equipe equipe) {
        this.equipes.add(equipe);
    }

    public void ajouterTache(Tache tache) {
        this.taches.add(tache);
    }
}
