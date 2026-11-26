package vue;

import gestion.GestionProjets;
import gestion.Projet;
import observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class VueListeProjets extends JFrame implements Observateur {

    private final GestionProjets gestion;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> listeProjets = new JList<>(listModel);

    private final JTextField champNom = new JTextField();
    private final JTextField champDate = new JTextField("2026-01-01");
    private final JTextField champEquipe = new JTextField("Equipe1");
    private final JTextField champNbMembres = new JTextField("3");

    public VueListeProjets(GestionProjets gestion) {
        super("Liste des Projets");
        this.gestion = gestion;

        setLayout(new BorderLayout());

        JPanel panelAjout = new JPanel(new GridLayout(5, 2));
        panelAjout.add(new JLabel("Nom :"));
        panelAjout.add(champNom);
        panelAjout.add(new JLabel("Date debut (AAAA-MM-JJ) :"));
        panelAjout.add(champDate);
        panelAjout.add(new JLabel("Equipe :"));
        panelAjout.add(champEquipe);
        panelAjout.add(new JLabel("Nb membres :"));
        panelAjout.add(champNbMembres);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(e -> actionAjouter());
        panelAjout.add(new JLabel(""));
        panelAjout.add(btnAjouter);
        add(panelAjout, BorderLayout.NORTH);

        add(new JScrollPane(listeProjets), BorderLayout.CENTER);

        JPanel panelBas = new JPanel(new FlowLayout());
        JButton btnVerifier = new JButton("Verifier");
        JButton btnSupprimer = new JButton("Supprimer");
        btnVerifier.addActionListener(e -> actionVerifier());
        btnSupprimer.addActionListener(e -> actionSupprimer());
        panelBas.add(btnVerifier);
        panelBas.add(btnSupprimer);
        add(panelBas, BorderLayout.SOUTH);

        this.gestion.ajouterObservateur(this);
        this.update();

        setSize(400, 380);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void actionAjouter() {
        try {
            LocalDate date = LocalDate.parse(champDate.getText().trim());
            int nb = Integer.parseInt(champNbMembres.getText().trim());
            boolean ok = gestion.createProjet(champNom.getText().trim(), date, champEquipe.getText().trim(), nb);
            if (ok) {
                champNom.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur : projet invalide ou doublon.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Format de date ou de nombre invalide.");
        }
    }

    private void actionSupprimer() {
        String nom = listeProjets.getSelectedValue();
        if (nom == null) {
            return;
        }
        gestion.supprimeProjet(nom);
    }

    private void actionVerifier() {
        String nom = listeProjets.getSelectedValue();
        if (nom == null) {
            return;
        }
        List<String> erreurs = gestion.verifierProjet(nom);
        if (erreurs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Projet valide !");
        } else {
            JOptionPane.showMessageDialog(this, String.join("\n", erreurs));
        }
    }

    @Override
    public void update() {
        listModel.clear();
        for (Projet p : gestion.getListeProjet()) {
            listModel.addElement(p.getNom());
        }
    }
}