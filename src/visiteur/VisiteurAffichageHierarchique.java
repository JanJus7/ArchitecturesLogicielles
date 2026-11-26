package visiteur;

import gestion.Equipe;
import gestion.Projet;
import gestion.Tache;

public class VisiteurAffichageHierarchique implements Visiteur {
    private final StringBuilder res = new StringBuilder();

    @Override
    public void visiter(Projet projet) {
        if (projet == null) {
            return;
        }

        res.append("Projet : ").append(projet.getNom())
                .append(" (").append(projet.getDateDebut())
                .append(" -> ").append(projet.getDateFin()).append(")\n");

        res.append("  Equipes :\n");
        if (projet.getEquipes() != null) {
            for (Equipe e : projet.getEquipes()) {
                if (e != null) {
                    e.accepter(this);
                }
            }
        }

        res.append("  Taches :\n");
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
        if (equipe == null) {
            return;
        }
        res.append("    - ").append(equipe.getNom())
                .append(" (").append(equipe.getNbMembres()).append(" membres)\n");
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

        res.append("    - ").append(tache.getIntitule())
                .append(" [").append(tache.getDuree()).append("j, ")
                .append(etat).append(", equipe: ").append(nomEquipe).append("]\n");
    }

    public String getRes() {
        return res.toString();
    }
}
