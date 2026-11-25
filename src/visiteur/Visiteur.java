package visiteur;

import gestion.Equipe;
import gestion.Projet;
import gestion.Tache;

public interface Visiteur {
    void visiter(Projet projet);
    void visiter(Equipe equipe);
    void visiter(Tache tache);
}