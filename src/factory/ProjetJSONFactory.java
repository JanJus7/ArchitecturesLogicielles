package factory;

import gestion.Equipe;
import gestion.Projet;
import gestion.Tache;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjetJSONFactory {
    private int projetsCrees = 0;

    /*
     * helper function
     * @return JSONArray
     */
    private JSONArray processJSON(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            return new JSONArray(new JSONTokener(reader));
        }
    }

    /*
     * helper function
     * @return Projet
     */
    private Projet createOneProjet(JSONObject projetJson) {
        String projetNom = projetJson.getString("nom");
        LocalDate dateDeb = LocalDate.parse(projetJson.getString("dateDebut"));
        LocalDate dateFin = LocalDate.parse(projetJson.getString("dateFin"));

        Projet projet = new Projet(projetNom, dateDeb, dateFin);

        JSONArray equipesJson = projetJson.getJSONArray("equipes");

        for (int i = 0; i < equipesJson.length(); i++) {
            JSONObject equipeJson = equipesJson.getJSONObject(i);
            String nom = equipeJson.getString("nom");
            int nbMembres = equipeJson.getInt("nbMembres");
            Equipe equipe = new Equipe(nom, nbMembres);
            projet.ajouterEquipe(equipe);
        }

        JSONArray tachesJson = projetJson.getJSONArray("taches");

        for (int i = 0; i < tachesJson.length(); i++) {
            JSONObject tacheJson = tachesJson.getJSONObject(i);
            String intitule = tacheJson.getString("intitule");
            int duree = tacheJson.getInt("duree");
            boolean estTerminee = tacheJson.getBoolean("estTerminee");
            String eqAffectee = tacheJson.optString("equipeAffectee", null);
            Equipe equipeAffectee = null;

            List<Equipe> equipes = projet.getEquipes();
            for (Equipe e : equipes) {
                if (Objects.equals(e.getNom(), eqAffectee)) {
                    equipeAffectee = e;
                    break;
                }
            }

            Tache tache = new Tache(intitule, duree, estTerminee, equipeAffectee);
            projet.ajouterTache(tache);
        }

        return projet;
    }

    public List<Projet> createProjetsFromJSON(String filename) throws IOException {
        List<Projet> projets = new ArrayList<>();
        JSONArray projetsJson = this.processJSON(filename);

        for (int i = 0; i < projetsJson.length(); i++) {
            JSONObject projetObj = projetsJson.getJSONObject(i);
            Projet compProjet = this.createOneProjet(projetObj);
            projets.add(compProjet);
            this.projetsCrees++;
        }

        return projets;
    }

    public int getProjetsCrees() {
        return projetsCrees;
    }
}
