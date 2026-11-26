import controlleur.ControlleurProjets;
import factory.ProjetFactory;
import gestion.GestionProjets;
import gestion.Projet;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Q1
        ProjetFactory factory = new ProjetFactory();
        Projet pStd = factory.createProjectStandard("StandardDemo", LocalDate.of(2026, 5, 1), "Equipe1", 4);
        System.out.println("Q1 : taches=" + pStd.getTaches().size() + ", projets crees=" + factory.getProjetsCrees());

        // Q4
        GestionProjets demo = new GestionProjets();

        System.out.println("\nQ4");
        System.out.println("doublons.json : " + demo.importerProjetsJSON("test-data/doublons.json"));
        System.out.println("erreurs_metier.json : " + demo.importerProjetsJSON("test-data/erreurs_metier.json"));
        System.out.println("sans_date.json : " + demo.importerProjetsJSON("test-data/sans_date.json"));
        System.out.println("syntaxe_cassee.json : " + demo.importerProjetsJSON("test-data/syntaxe_cassee.json"));
        System.out.println("valide.json : " + demo.importerProjetsJSON("test-data/valide.json"));

        // Q4 (valide) + Q5
        GestionProjets gestion = new GestionProjets();
        List<String> erreursImport = gestion.importerProjetsJSON("test-data/valide.json");
        if (!erreursImport.isEmpty()) {
            System.out.println("Erreurs import valide.json : " + erreursImport);
        }

        System.out.println("\nQ5 : AFFICHAGE HIERARCHIQUE");
        System.out.println(gestion.afficherHierarchique());
        System.out.println("Q5 : AFFICHAGE PLAT");
        System.out.println(gestion.afficherTachesAPlat());

        // Q6 / Q7
        SwingUtilities.invokeLater(() -> {
            ControlleurProjets controleur = new ControlleurProjets(gestion);
            controleur.demarrer();
        });
    }
}