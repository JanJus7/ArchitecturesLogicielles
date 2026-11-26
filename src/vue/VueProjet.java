package vue;

import gestion.Equipe;
import gestion.GestionProjets;
import gestion.Projet;
import gestion.Tache;
import observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class VueProjet extends JFrame implements Observateur {

    private final GestionProjets gestion;
    private Projet projetCourant;

    private final JComboBox<String> comboProjets = new JComboBox<>();
    private final JTextField champNom = new JTextField();
    private final JTextField champDateDebut = new JTextField();
    private final JTextField champDateFin = new JTextField();
    private final JLabel labelEquipes = new JLabel("Equipes : -");

    private final DefaultListModel<String> tachesModel = new DefaultListModel<>();
    private final JList<String> listeTaches = new JList<>(tachesModel);

    private final JTextField champIntitule = new JTextField();
    private final JTextField champDuree = new JTextField("5");
    private final JComboBox<String> comboEquipes = new JComboBox<>();
    private final JCheckBox checkTerminee = new JCheckBox("Terminee");

    public VueProjet(GestionProjets gestion) {
        super("Details du Projet");
        this.gestion = gestion;

        setLayout(new BorderLayout());

        JPanel panelHaut = new JPanel(new GridLayout(5, 3));
        JButton btnVoir = new JButton("Voir");
        btnVoir.addActionListener(e -> actionChoisirProjet());
        panelHaut.add(new JLabel("Projet :"));
        panelHaut.add(comboProjets);
        panelHaut.add(btnVoir);

        JButton btnNom = new JButton("Renommer");
        btnNom.addActionListener(e -> actionModifierNom());
        panelHaut.add(new JLabel("Nom :"));
        panelHaut.add(champNom);
        panelHaut.add(btnNom);

        JButton btnDeb = new JButton("Maj debut");
        btnDeb.addActionListener(e -> actionModifierDateDebut());
        panelHaut.add(new JLabel("Debut :"));
        panelHaut.add(champDateDebut);
        panelHaut.add(btnDeb);

        JButton btnFin = new JButton("Maj fin");
        btnFin.addActionListener(e -> actionModifierDateFin());
        panelHaut.add(new JLabel("Fin :"));
        panelHaut.add(champDateFin);
        panelHaut.add(btnFin);

        panelHaut.add(labelEquipes);
        panelHaut.add(new JLabel(""));
        panelHaut.add(new JLabel(""));
        add(panelHaut, BorderLayout.NORTH);

        JPanel panelCentre = new JPanel(new BorderLayout());
        panelCentre.add(new JScrollPane(listeTaches), BorderLayout.CENTER);
        JButton btnSuppTache = new JButton("Supprimer tache");
        btnSuppTache.addActionListener(e -> actionSupprimerTache());
        panelCentre.add(btnSuppTache, BorderLayout.SOUTH);
        add(panelCentre, BorderLayout.CENTER);

        JPanel panelBas = new JPanel(new GridLayout(3, 3));
        panelBas.add(new JLabel("Tache :"));
        panelBas.add(champIntitule);
        panelBas.add(checkTerminee);
        panelBas.add(new JLabel("Duree :"));
        panelBas.add(champDuree);
        panelBas.add(comboEquipes);
        JButton btnAjoutTache = new JButton("Ajouter tache");
        btnAjoutTache.addActionListener(e -> actionAjouterTache());
        panelBas.add(new JLabel(""));
        panelBas.add(btnAjoutTache);
        add(panelBas, BorderLayout.SOUTH);

        this.gestion.ajouterObservateur(this);
        this.update();

        setSize(520, 460);
        setLocation(520, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void actionChoisirProjet() {
        String nom = (String) comboProjets.getSelectedItem();
        if (nom != null) {
            this.projetCourant = gestion.getProjetParNom(nom);
            afficherProjet();
        }
    }

    private void actionModifierNom() {
        if (projetCourant == null) return;
        String nouveauNom = champNom.getText().trim();
        if (nouveauNom.equals(projetCourant.getNom())) return;
        if (!gestion.updateProjetNom(projetCourant.getNom(), nouveauNom)) {
            JOptionPane.showMessageDialog(this, "Nom invalide ou deja pris.");
            afficherProjet();
        }
    }

    private void actionModifierDateDebut() {
        if (projetCourant == null) return;
        try {
            LocalDate date = LocalDate.parse(champDateDebut.getText().trim());
            if (!gestion.updateProjetDateDeb(projetCourant.getNom(), date)) {
                JOptionPane.showMessageDialog(this, "Date de debut invalide.");
                afficherProjet();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Format attendu : AAAA-MM-JJ");
        }
    }

    private void actionModifierDateFin() {
        if (projetCourant == null) return;
        try {
            LocalDate date = LocalDate.parse(champDateFin.getText().trim());
            if (!gestion.updateProjetDateFin(projetCourant.getNom(), date)) {
                JOptionPane.showMessageDialog(this, "Date de fin invalide.");
                afficherProjet();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Format attendu : AAAA-MM-JJ");
        }
    }

    private void actionAjouterTache() {
        if (projetCourant == null) return;
        try {
            int duree = Integer.parseInt(champDuree.getText().trim());
            String equipe = (String) comboEquipes.getSelectedItem();
            boolean ok = gestion.ajouterTache(
                    projetCourant.getNom(), champIntitule.getText().trim(), duree, checkTerminee.isSelected(), equipe);
            if (ok) {
                champIntitule.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Tache invalide.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Duree invalide.");
        }
    }

    private void actionSupprimerTache() {
        int index = listeTaches.getSelectedIndex();
        if (projetCourant == null || index < 0 || index >= projetCourant.getTaches().size()) {
            return;
        }
        Tache t = projetCourant.getTaches().get(index);
        gestion.supprimerTache(projetCourant.getNom(), t.getIntitule());
    }

    @Override
    public void update() {
        List<Projet> projets = gestion.getListeProjet();
        if (projetCourant == null || !projets.contains(projetCourant)) {
            projetCourant = projets.isEmpty() ? null : projets.get(0);
        }

        comboProjets.removeAllItems();
        for (Projet p : projets) {
            comboProjets.addItem(p.getNom());
        }
        if (projetCourant != null) {
            comboProjets.setSelectedItem(projetCourant.getNom());
        }
        afficherProjet();
    }

    private void afficherProjet() {
        tachesModel.clear();
        comboEquipes.removeAllItems();

        if (projetCourant == null) {
            champNom.setText("");
            champDateDebut.setText("");
            champDateFin.setText("");
            labelEquipes.setText("Aucun projet");
            return;
        }

        champNom.setText(projetCourant.getNom());
        champDateDebut.setText(projetCourant.getDateDebut().toString());
        champDateFin.setText(projetCourant.getDateFin().toString());

        String equipesTxt = "Equipes : ";
        for (Equipe e : projetCourant.getEquipes()) {
            comboEquipes.addItem(e.getNom());
            equipesTxt += e.getNom() + " ";
        }
        labelEquipes.setText(equipesTxt);

        for (Tache t : projetCourant.getTaches()) {
            String eq = (t.getEquipeAffectee() != null) ? t.getEquipeAffectee().getNom() : "-";
            String etat = t.isEstTerminee() ? "OK" : "En cours";
            tachesModel.addElement(t.getIntitule() + " (" + t.getDuree() + "j, " + eq + ", " + etat + ")");
        }
    }
}