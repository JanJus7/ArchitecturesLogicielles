package gestion;

public class Tache {
    private String intitule;
    private int duree;
    private boolean estTerminee;
    private Equipe equipeAffectee;

    public Tache(String intitule, int duree, boolean estTerminee, Equipe equipeAffectee) {
        this.intitule = intitule;
        this.duree = duree;
        this.estTerminee = estTerminee;
        this.equipeAffectee = equipeAffectee;
    }

    //
    // getters
    //
    public String getIntitule() {
        return intitule;
    }

    public int getDuree() {
        return duree;
    }

    public boolean isEstTerminee() {
        return estTerminee;
    }

    public Equipe getEquipeAffectee() {
        return equipeAffectee;
    }

    //
    // setters
    //
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setEstTerminee(boolean estTerminee) {
        this.estTerminee = estTerminee;
    }

    public void setEquipeAffectee(Equipe equipeAffectee) {
        this.equipeAffectee = equipeAffectee;
    }
}

