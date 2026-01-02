package vue.page;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AssociateUser extends JPanel implements Refreshable {
    private RequeteGeOOCache requeteGeOOCache;
    private Utilisateur utilisateur;
    private JComboBox<Utilisateur> comboBoxUtilisateur;
    private JComboBox<Object> comboBoxReseau;
    private SelectionDropdown selectionDropdown;
    private JLabel reussite;
    private JLabel echec;

    public AssociateUser(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, SelectionDropdown selectionDropdown) throws SQLException {
        super();
        //Attribution des variables
        this.requeteGeOOCache = requeteGeOOCache;
        this.utilisateur = utilisateur;
        this.selectionDropdown = selectionDropdown;
        System.out.println("test");
        System.out.println(selectionDropdown);

        //DONNEES

        //Création des dropdowns pour les réseaux et les utilisateurs
        //Récupération des valeurs
        //Récupération de la liste des réseauxCache dont l'utilisateur est propriétaire
        List<ReseauCache> reseauxCacheProp = this.requeteGeOOCache.getReseauxUtilisateur(utilisateur);

        //Récupération de la liste des utilisateurs
        List<Utilisateur> utilisateurs = this.requeteGeOOCache.getListeUtilisateurs();

        //Création des dropdowns
        //Création de la dropdown : ReseauCache
        comboBoxReseau = new JComboBox<>();
        DefaultComboBoxModel<Object> comboBoxReseauModel = new DefaultComboBoxModel<>();
        comboBoxReseauModel.addElement("Choix du réseau");
        for(ReseauCache rc : reseauxCacheProp){
            comboBoxReseauModel.addElement(rc);
        }

        comboBoxReseau.setModel(comboBoxReseauModel);

        //Création de la dropdown : Utilisateur
        comboBoxUtilisateur = new JComboBox<>();
        DefaultComboBoxModel<Utilisateur> comboBoxUtilisateurModel = new DefaultComboBoxModel<>();
        for(Utilisateur u : utilisateurs){
            if(!(u.equals(this.utilisateur))) { //TODO : vérifier qu'il n'y a pas besoin de pouvoir s'associer soit même
                comboBoxUtilisateurModel.addElement(u);
            }
        }

        comboBoxUtilisateur.setModel(comboBoxUtilisateurModel);

        //DESIGN

        //Mise en forme
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        //Texte en haut de la page
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);

        JPanel topTextPanel = createChampWithoutTextField("Attribution d'accès à un réseau");
        topPanel.add(topTextPanel, BorderLayout.CENTER);

        this.add(topPanel, BorderLayout.NORTH);

        ///Création du panel pour le design
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);

        comboBoxUtilisateur.setMaximumSize(new Dimension(200, 50));
        panel.add(comboBoxUtilisateur);
        JLabel texte = new JLabel(" aura accès à ");
        texte.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(texte);
        comboBoxReseau.setMaximumSize(new Dimension(200, 50));
        panel.add(comboBoxReseau);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100)); // 50px à gauche et droite

        this.add(panel, BorderLayout.CENTER);

        //Création du bouton
        JButton btnCreer = new JButton("> Oui !");
        btnCreer.setBackground(Color.decode("#c8d400"));
        btnCreer.addActionListener(new ActionAssociateButtonListener());

        //Design du bouton
        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));  // bordure en bas et à droite
        panelBtn.setBackground(Color.WHITE);
        panelBtn.add(btnCreer, BorderLayout.EAST);  // à droite
        this.add(panelBtn, BorderLayout.SOUTH);  // le panel du bouton est en bas

        //Message de réussite
        this.reussite = new JLabel("Association réussie");
        this.reussite.setForeground(Color.BLUE);
        this.reussite.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.reussite.setVisible(false);
        //Message d'échec
        this.echec = new JLabel("Association échoué");
        this.echec.setForeground(Color.RED);
        this.echec.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.echec.setVisible(false);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        messagePanel.add(this.reussite);
        messagePanel.add(this.echec);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        center.add(Box.createVerticalStrut(100));
        center.add(messagePanel);
        center.add(Box.createVerticalStrut(20));
        center.add(panel);

        this.add(topTextPanel, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public JPanel createChampWithoutTextField(String texteLabel) {
        JPanel champ = new JPanel(new FlowLayout(FlowLayout.CENTER));
        champ.setBackground(new Color(230, 230, 230));

        // padding gauche / droite / haut / bas
        champ.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel label = new JLabel(texteLabel);
        champ.add(label);

        return champ;
    }

    @Override
    public void refreshData() {
        //Récupère l'utilisateur sélectionné dans la dropdown
        Utilisateur user = (Utilisateur) selectionDropdown.getElementSelect("Utilisateur");
        if(user != null)
            comboBoxUtilisateur.setSelectedItem(user);
        revalidate();
        repaint();
    }

    public class ActionAssociateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (comboBoxReseau.getSelectedItem() instanceof ReseauCache) {
                ReseauCache r = (ReseauCache) comboBoxReseau.getSelectedItem();
                Utilisateur u = (Utilisateur) comboBoxUtilisateur.getSelectedItem();

                //Avant de créer l'association on vérifie qu'elle n'existe pas déjà
                boolean exist = requeteGeOOCache.checkAssociationReseauUtilisateurExist(u, r);
                boolean result;
                if(exist){
                    result = false;
                }else{
                    result = requeteGeOOCache.ajouterAccesReseau(r, u);
                }

                if (result){
                    echec.setVisible(false);
                    reussite.setVisible(true);
                }else{
                    echec.setVisible(true);
                    reussite.setVisible(false);
                }

            }else{
                echec.setVisible(true);
                reussite.setVisible(false);
                return;
            };
        }
    }
}