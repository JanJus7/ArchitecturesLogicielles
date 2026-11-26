package vue;

import controlleur.ControlleurProjets;
import gestion.GestionProjets;
import gestion.Projet;
import observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VueListeProjets extends JFrame implements Observateur {

    private final GestionProjets gestion;
    private final ControlleurProjets controleur;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> listeProjets = new JList<>(listModel);

    private final JTextField champNom = new JTextField();
    private final JTextField champDate = new JTextField("2026-01-01");
    private final JTextField champEquipe = new JTextField("Equipe1");
    private final JTextField champNbMembres = new JTextField("3");

    public VueListeProjets(GestionProjets gestion, ControlleurProjets controleur) {
        super("Liste des Projets");
        this.gestion = gestion;
        this.controleur = controleur;

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

        JPanel panelBas = createPanelBas(controleur);
        add(panelBas, BorderLayout.SOUTH);

        this.gestion.ajouterObservateur(this);
        this.update();

        setSize(400, 380);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createPanelBas(ControlleurProjets controleur) {
        JPanel panelBas = new JPanel(new GridLayout(2, 3));
        JButton btnDetails = new JButton("Details");
        JButton btnVerifier = new JButton("Verifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnHier = new JButton("Hierarchique");
        JButton btnPlat = new JButton("A plat");
        btnDetails.addActionListener(e -> controleur.ouvrirProjet(listeProjets.getSelectedValue()));
        btnVerifier.addActionListener(e -> actionVerifier());
        btnSupprimer.addActionListener(e -> actionSupprimer());
        btnHier.addActionListener(e -> JOptionPane.showMessageDialog(
                this, new JScrollPane(new JTextArea(controleur.getAffichageHierarchique(), 15, 40))));
        btnPlat.addActionListener(e -> JOptionPane.showMessageDialog(
                this, new JScrollPane(new JTextArea(controleur.getAffichagePlat(), 15, 40))));

        panelBas.add(btnDetails);
        panelBas.add(btnVerifier);
        panelBas.add(btnSupprimer);
        panelBas.add(btnHier);
        panelBas.add(btnPlat);
        return panelBas;
    }

    private void actionAjouter() {
        String err = controleur.ajouterProjet(
                champNom.getText(),
                champDate.getText(),
                champEquipe.getText(),
                champNbMembres.getText()
        );
        if (err == null) {
            champNom.setText("");
        } else {
            JOptionPane.showMessageDialog(this, err);
        }
    }

    private void actionSupprimer() {
        controleur.supprimerProjet(listeProjets.getSelectedValue());
    }

    private void actionVerifier() {
        String nom = listeProjets.getSelectedValue();
        if (nom == null) {
            return;
        }
        List<String> erreurs = controleur.verifierProjet(nom);
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