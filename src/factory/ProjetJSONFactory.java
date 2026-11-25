package factory;

import gestion.Equipe;
import gestion.Projet;
import gestion.Tache;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import verification.VerificationProjet;

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
        String projetNom = projetJson.optString("nom", null);
        LocalDate dateDeb = LocalDate.parse(projetJson.optString("dateDebut", null));
        LocalDate dateFin = LocalDate.parse(projetJson.optString("dateFin", null));

        Projet projet = new Projet(projetNom, dateDeb, dateFin);

        JSONArray equipesJson = projetJson.optJSONArray("equipes");

        for (int i = 0; i < equipesJson.length(); i++) {
            JSONObject equipeJson = equipesJson.optJSONObject(i);
            String nom = equipeJson.optString("nom", null);
            int nbMembres = equipeJson.optInt("nbMembres", 0);
            Equipe equipe = new Equipe(nom, nbMembres);
            projet.ajouterEquipe(equipe);
        }

        JSONArray tachesJson = projetJson.optJSONArray("taches");

        for (int i = 0; i < tachesJson.length(); i++) {
            JSONObject tacheJson = tachesJson.optJSONObject(i);
            String intitule = tacheJson.optString("intitule", null);
            int duree = tacheJson.optInt("duree", 0);
            boolean estTerminee = tacheJson.optBoolean("estTerminee", false);
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

        VerificationProjet verification = new VerificationProjet();

        boolean estValide = verification.estValide(projet);

        if (estValide) {
            return projet;
        }

        return null;
    }

    public List<Projet> createProjetsFromJSON(String filename) throws IOException {
        List<Projet> projets = new ArrayList<>();
        JSONArray projetsJson = this.processJSON(filename);

        for (int i = 0; i < projetsJson.length(); i++) {
            JSONObject projetObj = projetsJson.optJSONObject(i);
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
