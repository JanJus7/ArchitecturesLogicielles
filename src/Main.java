import controlleur.ControlleurProjets;
import gestion.GestionProjets;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GestionProjets gestion = new GestionProjets();
        List<String> erreursImport = gestion.importerProjetsJSON("test-data/valide.json");

        if (!erreursImport.isEmpty()) {
            System.out.println("Erreurs import JSON : " + erreursImport);
        }

        /*
        * Q5
         */
        System.out.println("AFFICHAGE HIERARCHIQUE");
        System.out.println(gestion.afficherHierarchique());
        System.out.println("AFFICHAGE PLAT");
        System.out.println(gestion.afficherTachesAPlat());

        /*
        * Q6/Q7
         */
        SwingUtilities.invokeLater(() -> {
            ControlleurProjets controleur = new ControlleurProjets(gestion);
            controleur.demarrer();
        });
    }
}