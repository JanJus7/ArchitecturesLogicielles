import factory.ProjetFactory;
import gestion.*;
import factory.*;
import verification.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProjetFactory factory = new ProjetFactory();
        LocalDate debut = LocalDate.now();
        System.out.println("Initial projects number: " + factory.getProjetsCrees());
        Projet pStd = factory.createProjectStandard("standard", debut, "e1", 7);
        System.out.println("Projects number: " + factory.getProjetsCrees());
        System.out.println("les equipes dedans: " + pStd.getEquipes());
        System.out.println("Les taches dans le projet: " + pStd.getTaches());

        VerificationProjet verif = new VerificationProjet();
        List<ErreursPossibles> erreurs = verif.validation(pStd);

        if (erreurs.isEmpty()) {
            System.out.println("Tout valide!");
        } else {
            for (ErreursPossibles err : erreurs) {
                System.out.println(err);
            }
        }
    }
}