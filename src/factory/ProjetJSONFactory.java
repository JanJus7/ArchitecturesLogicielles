package factory;

import gestion.Equipe;
import gestion.Projet;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;

public class ProjetJSONFactory {
    private int projetsCrees = 0;

    private JSONArray processJSON(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            return new JSONArray(new JSONTokener(reader));
        }
    }

    /*
    * helper function
    * @return Projet
    */
    private Projet createProjetsFromJSON() {
        Projet projetJson = new Projet(nomProjet, dateDebut, dateDebut.plusWeeks(3));

        for (int i = 0; i < equipeTab.length; i++) {
                Equipe equipe = new Equipe(equipeTab[i][0], equipeTab[i][1]);
                projetJson.ajouterEquipe(equipe);
        }

        // TODO: skończyc to bo trochę zaczynam od złej strony. niech na razie przeworzy json.



    }
}
