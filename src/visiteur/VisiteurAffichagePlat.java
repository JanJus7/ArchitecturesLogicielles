package visiteur;

import gestion.Equipe;
import gestion.Projet;
import gestion.Tache;

public class VisiteurAffichagePlat implements Visiteur {
    private final StringBuilder res = new StringBuilder();
    private String nomProjetCourant = "Inconnu";

    @Override
    public void visiter(Projet projet) {
        if (projet == null) {
            return;
        }
        this.nomProjetCourant = (projet.getNom() != null) ? projet.getNom() : "Sans nom";

        if (projet.getTaches() != null) {
            for (Tache t : projet.getTaches()) {
                if (t != null) {
                    t.accepter(this);
                }
            }
        }
    }

    @Override
    public void visiter(Equipe equipe) {
    }

    @Override
    public void visiter(Tache tache) {
        if (tache == null) {
            return;
        }
        String nomEquipe = (tache.getEquipeAffectee() != null)
                ? tache.getEquipeAffectee().getNom()
                : "Aucune";
        String etat = tache.isEstTerminee() ? "Terminee" : "En cours";

        res.append("Tache : ").append(tache.getIntitule())
                .append(" | Projet : ").append(this.nomProjetCourant)
                .append(" | Equipe : ").append(nomEquipe)
                .append(" | Etat : ").append(etat)
                .append("\n");
    }

    public String getRes() {
        return res.toString();
    }
}