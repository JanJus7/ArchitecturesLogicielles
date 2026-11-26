package gestion;

import observer.Observateur;
import observer.Sujet;
import verification.VerificationProjet;
import visiteur.Visiteur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CatalogProjets implements Sujet {
    private final List<Projet> projets;
    private final VerificationProjet validator;
    private final List<Observateur> observateurs = new ArrayList<>();

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
        this.notifierObservateurs();
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

    public boolean ajouterTache(String nomProj, String intitule, int duree, boolean estTerminee, String nomEquipe) {
        Projet p = this.getProjetParNom(nomProj);

        if (p == null || intitule == null || intitule.isBlank()) {
            return false;
        }

        Equipe eq = null;

        if (p.getEquipes() != null) {
            for (Equipe elem : p.getEquipes()) {
                if (elem != null && Objects.equals(elem.getNom(), nomEquipe)) {
                    eq = elem;
                    break;
                }
            }
        }

        Tache nouvelle = new Tache(intitule, duree, estTerminee, eq);
        p.ajouterTache(nouvelle);

        if (!validator.estValide(p)) {
            p.getTaches().remove(nouvelle);
            return false;
        }

        this.notifierObservateurs();
        return true;
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

        this.notifierObservateurs();
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

        this.notifierObservateurs();
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

        this.notifierObservateurs();
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

        this.notifierObservateurs();
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

        this.notifierObservateurs();
        return true;
    }

    public boolean supprimeProjet(String nomProjet) {
        Projet p = this.getProjetParNom(nomProjet);

        if (p != null) {
            this.projets.remove(p);
            this.notifierObservateurs();
            return true;
        }

        return false;
    }

    public boolean supprimerTache(String nomProj, String intitule) {
        Projet projet = this.getProjetParNom(nomProj);

        if (projet == null || projet.getTaches() == null) {
            return false;
        }

        Tache aSupprimer = null;
        for (Tache item : projet.getTaches()) {
            if (item != null && Objects.equals(item.getIntitule(), intitule)) {
                aSupprimer = item;
                break;
            }
        }

        if (aSupprimer == null) {
            return false;
        }

        projet.getTaches().remove(aSupprimer);
        this.notifierObservateurs();
        return true;
    }

    public List<Projet> getListeProjet() {
        return new ArrayList<>(this.projets);
    }

    /*
     * VISITEURS
     */
    public void appliquerVisiteur(Visiteur visiteur) {
        if (visiteur == null) {
            return;
        }
        for (Projet p : this.projets) {
            p.accepter(visiteur);
        }
    }

    /*
    * SUJET
     */

    @Override
    public void ajouterObservateur(Observateur o) {
        if ( o!= null) {
            if (!observateurs.contains(o)) {
                observateurs.add(o);
            }
        }
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifierObservateurs() {
        for (Observateur o : new ArrayList<>(this.observateurs)) {
            o.update();
        }
    }

}
