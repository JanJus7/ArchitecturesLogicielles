package gestion;

import visiteur.*;

public class Equipe implements Visitable {
    private String nom;
    private int nbMembres;

    public Equipe(String nom, int nbMembres) {
        this.nom = nom;
        this.nbMembres = nbMembres;
    }

    //
    // getters
    //
    public String getNom() {
        return nom;
    }

    public int getNbMembres() {
        return nbMembres;
    }

    //
    // setters
    //
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbMembres(int nbMembres) {
        this.nbMembres = nbMembres;
    }

    @Override
    public void accepter(Visiteur visiteur) {
        if (visiteur != null) {
            visiteur.visiter(this);
        }
    }
}
