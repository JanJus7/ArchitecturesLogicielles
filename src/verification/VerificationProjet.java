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

    public boolean estValide(Projet projet) {
        return validation(projet).isEmpty();
    }
    
    // TODO: add getStatus??? podobno operowanie na booleanach nie jest najlepsze...
}

