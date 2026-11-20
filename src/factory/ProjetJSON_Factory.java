package factory;

import gestion.Equipe;
import gestion.Projet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;

public class ProjetJSON_Factory {
    private int projetsCrees = 0;

    // TODO: need to process json into array.
//    public List<?> processJSON(String[] args) {
//        return
//    }

    /*
    * helper function
    * @return Projet
    */
    public Projet createOneProjetFromJSON() {
        Projet JSONprojet = new Projet(nomProjet, dateDebut, dateDebut.plusWeeks(3));

        for (int i = 0; i < equipeTab.length; i++) {
                Equipe equipe = new Equipe(equipeTab[i][0], equipeTab[i][1]);
                JSONprojet.ajouterEquipe(equipe);
        }

        // TODO: skończyc to bo trochę zaczynam od złej strony. niech na razie przeworzy json.



    }
}
