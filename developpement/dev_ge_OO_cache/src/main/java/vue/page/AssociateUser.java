package vue.page;

import jdk.jshell.execution.Util;
import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;
import vue.dropdown.ComboBoxGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe AssociateUser
 * Une classe correspondant à une page de l'application
 * Elle permet l'affichage de la page sur l'association d'un utilisateur à une vue
 */
public class AssociateUser extends JPanel implements Refreshable {
    private Utilisateur utilisateurConnecte;
    private RequeteGeOOCache requeteGeOOCache;
    private JComboBox<Utilisateur> comboBoxUtilisateur;
    private JComboBox<Object> comboBoxReseau;
    private SelectionDropdown selectionDropdown;
    private ComboBoxGeneral comboBoxGeneral;
    private JLabel reussite;
    private JLabel echec;
    private Utilisateur utilisateur;

    //Constructeur par données
    public AssociateUser(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, SelectionDropdown selectionDropdown, ComboBoxGeneral comboBoxGeneral) throws SQLException {
        super();
        //Attribution des variables
        this.requeteGeOOCache = requeteGeOOCache;
        this.selectionDropdown = selectionDropdown;
        this.comboBoxGeneral = comboBoxGeneral;
        this.utilisateurConnecte = utilisateur;

        Font font = new Font("Consolas", Font.PLAIN, 24);

        //DONNEES
        //Création des JComboBox pour les réseaux et les utilisateurs
        this.comboBoxReseau = new JComboBox<>();
        this.comboBoxReseau.setMaximumSize(new Dimension(300, 50));
        this.comboBoxReseau.setFont(font);
        this.comboBoxReseau.setBackground(Color.decode("#e6e6e6"));

        this.comboBoxUtilisateur = new JComboBox<>();
        this.comboBoxUtilisateur.setMaximumSize(new Dimension(200, 50));
        this.comboBoxUtilisateur.addActionListener(new ActionChangeListener());
        this.comboBoxUtilisateur.setFont(font);
        this.comboBoxUtilisateur.setBackground(Color.decode("#e6e6e6"));

        //DESIGN
        //Mise en forme
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        //Mise en forme centre panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(createTextTop("Attribution d'accès à un réseau"), BorderLayout.NORTH);
        centerPanel.add(createCenterPanel(), BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
        this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        this.add(Box.createRigidArea(new Dimension(0, 300)), BorderLayout.NORTH);
        this.add(createButtonValidate("> OUI !"), BorderLayout.SOUTH); // le panel du bouton est en bas

        this.setVisible(true);
    }

    /**
     *              METHODES : creation des différents éléments
     */

    /**
     * Méthode getDefaultComboBoxModelReseau
     * -------
     * permet de créer le modèle de la comboBox pour la partie ReseauCache
     * @return DefaultComboBoxModel<Object> correspondant à la partie ReseauCache des utilisateurs du reseau
     */
    public DefaultComboBoxModel<Object> getDefaultComboBoxModelReseau(Utilisateur utilisateurProprietaire, Utilisateur utilisateurChoisi) {
        // Liste des réseaux du propriétaire
        List<ReseauCache> reseauxCacheProp = this.requeteGeOOCache.getReseauxUtilisateur(utilisateurProprietaire);
        // Liste des réseaux où l'utilisateur est déjà associé
        List<ReseauCache> reseauCachesAssocie = this.requeteGeOOCache.getReseauxAvecAccesUtilisateur(utilisateurChoisi);

        DefaultComboBoxModel<Object> comboBoxReseauModel = new DefaultComboBoxModel<>();
        comboBoxReseauModel.addElement("Choix du réseau");
        for(ReseauCache rc : reseauxCacheProp){
            if(!(reseauCachesAssocie.contains(rc))){
                comboBoxReseauModel.addElement(rc);
            }
        }
        return comboBoxReseauModel;
    }

    /**
     * Méthode getDefaultModelComboBoxUtilisateur
     * -------
     * permet de créer le modèle de la comboBox pour la partie Utilisateur
     * @return DefaultComboBoxModel<Utilisateur> correspondant à la partie Utilisateur (hors utilisateur courant/connecté)
     */
    public DefaultComboBoxModel<Utilisateur> getDefaultModelComboBoxUtilisateur(Utilisateur utilisateur) {
        List<Utilisateur> utilisateurs = this.requeteGeOOCache.getListeUtilisateurs();
        DefaultComboBoxModel<Utilisateur> comboBoxUtilisateurModel = new DefaultComboBoxModel<>();
        for(Utilisateur u : utilisateurs){
            if(!(u.equals(utilisateur))) {
                comboBoxUtilisateurModel.addElement(u);
            }
        }
        return comboBoxUtilisateurModel;
    }

    /**
     * Méthode createTextTop
     * -------
     * permet de créer la JPanel de la partie haute de l'affichage
     * @param texte : le texte à attribuer à la partie haute
     * @return JPanel correspondant à l'affichage de la partie haute
     */
    public JPanel createTextTop(String texte){
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        //Création du champ avec le texte
        JPanel topTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topTextPanel.setBackground(new Color(230, 230, 230));
        // padding gauche / droite / haut / bas
        topTextPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JLabel label = new JLabel(texte);
        label.setFont(new Font("Consolas", Font.PLAIN, 24));
        topTextPanel.add(label);

        topPanel.add(topTextPanel, BorderLayout.CENTER);

        return topPanel;
    }

    /**
     * Méthode createMessagePanel
     * -------
     * permet de créer la JPanel correspondant au message d'erreur et de réussite
     * @return JPanel correspondant au message d'erreur et de réussite
     */
    public JPanel createMessagePanel(){
        //Message de réussite
        this.reussite = new JLabel("Association réussie");
        this.reussite.setForeground(Color.BLUE);
        this.reussite.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.reussite.setVisible(false);
        this.reussite.setFont(new Font("Consolas", Font.PLAIN, 24));
        //Message d'échec
        this.echec = new JLabel("Association échoué");
        this.echec.setForeground(Color.RED);
        this.echec.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.echec.setVisible(false);
        this.reussite.setFont(new Font("Consolas", Font.PLAIN, 24));

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);

        messagePanel.add(this.reussite);
        messagePanel.add(this.echec);
        return messagePanel;
    }

    /**
     * Méthode createButtonValidate
     * -------
     * permet de créer le JButton permettant de valider l'association
     * @param texte : le texte à attribuer au bouton
     * @return JButton de validation
     */
    public JPanel createButtonValidate(String texte){
        //Création du bouton
        JButton btnCreer = new JButton(texte);
        btnCreer.setBackground(Color.decode("#c8d400"));

        btnCreer.setPreferredSize(new Dimension(250, 60));
        btnCreer.setMaximumSize(new Dimension(250, 60));
        btnCreer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCreer.setFont(new Font("Consolas", Font.BOLD, 24));

        btnCreer.addActionListener(new ActionAssociateButtonListener());

        //Design du bouton
        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 30));  // bordure en bas et à droite
        panelBtn.setBackground(Color.WHITE);
        panelBtn.add(btnCreer, BorderLayout.EAST);  // à droite
        return panelBtn;
    }

    /**
     * Méthode createCenterPanel
     * -------
     * permet de créer le JPanel du centre avec le choix de l'utilisateur et du reseauCache + message
     * @return JPanel de l'affichage du centre
     */
    public JPanel createCenterPanel(){
        ///Création du panel pour le design
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);

        //Création du texte
        JLabel texte = new JLabel(" aura accès à ");
        texte.setHorizontalAlignment(SwingConstants.CENTER);
        texte.setFont(new Font("Consolas", Font.PLAIN, 25));

        //Rassemblement de la ligne
        panel.add(this.comboBoxUtilisateur);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(texte);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(this.comboBoxReseau);

        panel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100)); // 50px à gauche et droite

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        //Ajout des messages d'erreur
        center.add(Box.createVerticalStrut(100));
        center.add(createMessagePanel());
        center.add(Box.createVerticalStrut(20));
        center.add(panel);

        return center;
    }

    /**
     *          REFRESHDATA
     */

    /**
     * Methode : RefreshData
     * -------
     * Permet de refresh les données quand on revient sur cette vue ou quand l'utilisateur est modifié dans la dropdown
     * Modifie la comboBox avec l'utilisateur choisit
     */
    @Override
    public void refreshData() {
        //Récupère l'utilisateur sélectionné dans la dropdown
        Utilisateur user = (Utilisateur) selectionDropdown.getElementSelect("Utilisateur");
        if(user != null) {
            comboBoxUtilisateur.setModel(getDefaultModelComboBoxUtilisateur(utilisateurConnecte));
            comboBoxUtilisateur.setSelectedItem(user);
            comboBoxReseau.setModel(getDefaultComboBoxModelReseau(utilisateurConnecte, user));
            reussite.setVisible(false);
            echec.setVisible(false);
        }
        revalidate();
        repaint();
    }

    /**
     *                       LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir au bouton : vient créer l'association si c'est possible
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

                //Création de l'association
                if (result){
                    echec.setVisible(false);
                    reussite.setVisible(true);
                    comboBoxReseau.setModel(getDefaultComboBoxModelReseau(utilisateurConnecte, u));
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

    // classe interne à la vue car elle y est spécifique
    // Permet de changer la comboBox utilisateur en haut selon la valeur choisie dans la comboBox de la vue
    public class ActionChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectionDropdown.addElementSelect("Utilisateur", comboBoxUtilisateur.getSelectedItem());
            comboBoxGeneral.refreshComboBoxUtilisateur();
            reussite.setVisible(false);
            echec.setVisible(false);
        }
    }
}