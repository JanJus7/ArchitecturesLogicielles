package gestion;

import factory.*;
import observer.Observateur;
import observer.Sujet;
import verification.*;
import visiteur.Visiteur;
import visiteur.VisiteurAffichageHierarchique;
import visiteur.VisiteurAffichagePlat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionProjets implements Sujet {
    private final ProjetFactory factory;
    private final CatalogProjets catalogProjets;
    private final ProjetJSONFactory projectJSONFactory;
    private final VerificationProjet verificationProjet;

    public GestionProjets() {
        this.factory = new ProjetFactory();
        this.catalogProjets = new CatalogProjets();
        this.projectJSONFactory = new ProjetJSONFactory();
        this.verificationProjet = new VerificationProjet();
    }

    public boolean createProjet(String nomProj, LocalDate deb, String nomEquipe, int nbMembres) {
        Projet projet = this.factory.createProjectStandard(nomProj, deb, nomEquipe, nbMembres);
        return this.catalogProjets.ajouterProjet(projet);
    }

    public List<String> importerProjetsJSON(String filename) {
        List<Projet> importes = this.projectJSONFactory.createProjetsFromJSON(filename);
        List<String> rapport = new ArrayList<>(this.projectJSONFactory.getErreurs());
        rapport.addAll(this.catalogProjets.ajouterProjets(importes));
        return rapport;
    }

    public List<String> verifierProjet(String nomProj) {
        return this.verificationProjet.getErreurs(this.catalogProjets.getProjetParNom(nomProj));
    }

    public Projet getProjetParNom(String nomProj) {
        return this.catalogProjets.getProjetParNom(nomProj);
    }

    public boolean updateProjetNom(String nomProj, String newNomProj) {
        return this.catalogProjets.updateProjetNom(nomProj, newNomProj);
    }

    public boolean updateProjetDateDeb(String nomProj, LocalDate newDateDeb) {
        return this.catalogProjets.updateProjetDateDeb(nomProj, newDateDeb);
    }

    public boolean updateProjetDateFin(String nomProj, LocalDate newDateFin) {
        return this.catalogProjets.updateProjetDateFin(nomProj, newDateFin);
    }

    public boolean updateProjetEquipes(String nomProj, List<Equipe> newEquipes) {
        return this.catalogProjets.updateProjetEquipes(nomProj, newEquipes);
    }

    public boolean updateProjetTaches(String nomProj, List<Tache> newTaches) {
        return this.catalogProjets.updateProjetTaches(nomProj, newTaches);
    }

    public boolean supprimeProjet(String nomProjet) {
        return this.catalogProjets.supprimeProjet(nomProjet);
    }

    public List<Projet> getListeProjet() {
        return this.catalogProjets.getListeProjet();
    }

    /*
     * VISITEURS
     */
    public void appliquerVisiteur(Visiteur visiteur) {
        this.catalogProjets.appliquerVisiteur(visiteur);
    }

    public String afficherHierarchique() {
        VisiteurAffichageHierarchique visiteur = new VisiteurAffichageHierarchique();
        this.catalogProjets.appliquerVisiteur(visiteur);
        return visiteur.getRes();
    }

    public String afficherTachesAPlat() {
        VisiteurAffichagePlat visiteur = new VisiteurAffichagePlat();
        this.catalogProjets.appliquerVisiteur(visiteur);
        return visiteur.getRes();
    }

    /*
    * SUJETS / OVSERVERS
     */

    public boolean ajouterTache(String nomProj, String intitule, int duree, boolean estTerminee, String nomEquipe) {
        return this.catalogProjets.ajouterTache(nomProj, intitule, duree, estTerminee, nomEquipe);
    }

    public boolean supprimerTache(String nomProj, String intitule) {
        return this.catalogProjets.supprimerTache(nomProj, intitule);
    }

    @Override
    public void ajouterObservateur(Observateur o) {
        this.catalogProjets.ajouterObservateur(o);
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        this.catalogProjets.supprimerObservateur(o);
    }

    @Override
    public void notifierObservateurs() {
        this.catalogProjets.notifierObservateurs();
    }
}