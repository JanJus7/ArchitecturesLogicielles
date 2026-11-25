package verification;

import java.util.ArrayList;
import java.util.List;

import gestion.*;

public class VerificationProjet {
    public List<ErreursPossibles> validation(Projet projet) {
        List<ErreursPossibles> erreurs = new ArrayList<>();

        if (!ErreursPossibles.NULL_PROJET.estValide(projet)) {
            erreurs.add(ErreursPossibles.NULL_PROJET);
            return erreurs;
        }

        for (ErreursPossibles regle : ErreursPossibles.values()) {
            if (regle != ErreursPossibles.NULL_PROJET && !regle.estValide(projet)) {
                erreurs.add(regle);
            }
        }

        return erreurs;
    }

    public List<String> getErreurs(Projet projet) {
        List<ErreursPossibles> erreurs = validation(projet);
        List<String> listErreurs = new ArrayList<>();
        erreurs.forEach((erreur) -> {
            listErreurs.add(erreur.getMessage());
        });



        return listErreurs;
    }

    public boolean estValide(Projet projet) {
        return validation(projet).isEmpty();
    }

}

