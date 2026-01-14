package vue.page;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


/**
 * Classe ManageUser
 * Une classe correspondant à une page de l'application
 * Elle permet de connaître la liste des utilisateurs d'un réseau et de les retirer de l'association ou non
 */
public class ManageUser extends JPanel implements Refreshable {
    private JList<Utilisateur> listUtilisateur;
    private SelectionDropdown selectionDropdown;
    private RequeteGeOOCache requeteGeOOCache;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    //Constructeur par données
    public ManageUser(SelectionDropdown selectionDropdown, RequeteGeOOCache requeteGeOOCache, CardLayout cardLayout, JPanel mainPanel) {
        JPanel panelManageUser = new JPanel();
        this.selectionDropdown = selectionDropdown;
        this.requeteGeOOCache = requeteGeOOCache;
        this.listUtilisateur = new JList<>();
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(listUtilisateur);
        scrollPane.setFont(new Font("consolas", Font.BOLD, 24));
        listUtilisateur.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        listUtilisateur.setCellRenderer(new ListUtilisateurCellRenderer());
        listUtilisateur.addMouseListener(new MouseListenerActionButton());

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        panelManageUser.setLayout(new BoxLayout(panelManageUser, BoxLayout.Y_AXIS));
        panelManageUser.setBackground(Color.WHITE);
        panelManageUser.add(createPanelTop());
        panelManageUser.add(scrollPane);

        this.add(panelManageUser, BorderLayout.CENTER);
        this.add(createButtonBack(), BorderLayout.NORTH);
        this.add(Box.createRigidArea(new Dimension(0, 50)),  BorderLayout.SOUTH);
        this.add(Box.createRigidArea(new Dimension(100, 0)),  BorderLayout.EAST);
        this.add(Box.createRigidArea(new Dimension(100, 0)),  BorderLayout.WEST);
        this.setVisible(true);
    }

    /**
     *                  METHODES : création des éléments de l'affichage
     */

    /**
     * Méthode : createPanelTop
     * ----------------
     * crée l'affichage du panel au dessus de la JList
     * @return JPanel
     */
    public JPanel createPanelTop(){
        JLabel labelUtilisateur = new JLabel("Utilisateur");
        JLabel labelSuppr = new JLabel("Supprimer");

        Font font =  new Font("Consolas", Font.PLAIN, 20);

        labelUtilisateur.setFont(font);
        labelSuppr.setFont(font);

        JPanel panelTop = new JPanel();
        panelTop.setBackground(Color.decode("#dbdbd8"));
        panelTop.setLayout(new BorderLayout());

        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(Color.decode("#dbdbd8"));
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.X_AXIS));
        panelLeft.add(Box.createRigidArea(new Dimension(80, 0)));
        panelLeft.add(labelUtilisateur);

        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.decode("#dbdbd8"));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.X_AXIS));
        panelRight.add(labelSuppr);
        panelRight.add(Box.createRigidArea(new Dimension(80, 0)));

        panelTop.add(panelLeft, BorderLayout.WEST);
        panelTop.add(panelRight, BorderLayout.EAST);

        panelTop.setPreferredSize(new Dimension(Integer.MAX_VALUE, 100));
        panelTop.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        return panelTop;
    }

    /**
     * Methode : createButtonBack
     * ---------
     * crée le button de Retour
     * @return JPanel permettant le Retour
     */
    public JPanel createButtonBack(){
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BorderLayout());

        JButton buttonLog = new JButton("< Retour");
        buttonLog.setBackground(Color.WHITE);
        buttonLog.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        buttonLog.setFont(new Font("Consolas", Font.BOLD, 24));
        buttonLog.setMaximumSize(new Dimension(200, 60));
        buttonLog.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

        buttonLog.addActionListener(new ButtonBackListener());

        panelButton.setBackground(Color.WHITE);
        panelButton.add(buttonLog, BorderLayout.WEST);
        panelButton.add(Box.createRigidArea(new Dimension(0, 30)),  BorderLayout.SOUTH);

        return panelButton;
    }

    /**
     * Méthode createDefaultModelUtilisateur
     * -------
     * permet de créer le modèle de la comboBox pour la partie Utilisateur
     * @return DefaultComboBoxModel<Utilisateur> correspondant à la partie Utilisateur
     */
    public DefaultListModel<Utilisateur> createDefaultModelUtilisateur(ReseauCache reseauCache){
        //Recupère la liste des utilisateurs du réseau choisi
        List<Utilisateur> utilisateursReseau = this.requeteGeOOCache.getListeUtilisateursFromReseau(reseauCache);

        DefaultListModel<Utilisateur> model = new DefaultListModel<>();
        //Modèle de la JList
        for(Utilisateur utilisateur : utilisateursReseau){
            model.addElement(utilisateur);
        }

        return model;
    }

    /**
     * Méthode : createPanelRenderer
     * -------------------
     * permet de créer le JPanel du design de chaque ligne dans la JList
     * @param utilisateur : Utilisateur de la ligne
     * @param proprietaire : indique si l'utilisateur est propriétaire ou non
     * @return JPanel de la ligne
     */
    public JPanel createPanelRenderer(Utilisateur utilisateur, boolean proprietaire){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        JLabel labelUtilisateur = new JLabel(" > " + utilisateur.getPseudo());
        labelUtilisateur.setFont(new Font("consolas", Font.BOLD, 25));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(Box.createRigidArea(new Dimension(60, 0)), BorderLayout.WEST);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
        leftPanel.add(labelUtilisateur, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BorderLayout());

        if (proprietaire){
            JLabel statutUtilisateur = new JLabel("( Propriétaire )");
            statutUtilisateur.setFont(new Font("consolas", Font.PLAIN, 25));
            rightPanel.add(statutUtilisateur, BorderLayout.CENTER);
            rightPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
        }else{
            JButton statutUtilisateur = new JButton("X");
            statutUtilisateur.setBackground(Color.WHITE);
            statutUtilisateur.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            statutUtilisateur.setForeground(Color.RED);
            statutUtilisateur.setFont(new Font("consolas", Font.BOLD, 25));
            rightPanel.add(statutUtilisateur, BorderLayout.CENTER);
            rightPanel.add(Box.createRigidArea(new Dimension(120, 0)), BorderLayout.EAST);
        }

        leftPanel.add(Box.createRigidArea(new Dimension(60, 0)), BorderLayout.EAST);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        panel.setOpaque(true);
        leftPanel.setOpaque(true);
        rightPanel.setOpaque(true);

        return panel;
    }

    /**
     *              METHODE : gestion Utilisateur
     */

    /**
     * Méthode : supprimerUtilisateur
     * --------
     * permet d'effectuer la suppression de l'association de l'utilisateur choisit avec le réseau de cache courant
     * @param utilisateur : utilisateur à retirer de l'association
     */
    public void supprimerUtilisateur(Utilisateur utilisateur){
        ReseauCache reseauCache = (ReseauCache) selectionDropdown.getElementSelect("Reseau");
        System.out.println("Action Suppression Utilisateur de l'association avec " + utilisateur.getPseudo());

        boolean result = requeteGeOOCache.deleteAccessReseau(utilisateur, reseauCache);
        System.out.println(result);

        listUtilisateur.setModel(createDefaultModelUtilisateur(reseauCache));
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
        //Récupère le réseau choisit
        ReseauCache reseauCache = (ReseauCache) this.selectionDropdown.getElementSelect("Reseau");

        listUtilisateur.setModel(createDefaultModelUtilisateur(reseauCache));
    }


    /**
     *                  RENDERER
     */

    // classe interne à la vue car elle y est spécifique
    // Gère l'affichage dans la liste + mettre bouton de suppression ou non selon si il est propriétaire ou non
    public class ListUtilisateurCellRenderer implements ListCellRenderer<Utilisateur>{
        @Override
        public Component getListCellRendererComponent(JList<? extends Utilisateur> list, Utilisateur value, int index, boolean isSelected, boolean cellHasFocus) {
            boolean proprietaire = false;
            if (requeteGeOOCache.getStatProprietaire((ReseauCache) selectionDropdown.getElementSelect("Reseau")).equals(value)){
                System.out.println("Propriétaire !!");
                proprietaire = true;
            };

            JPanel panel = createPanelRenderer(value, proprietaire);

            Color bg = isSelected
                    ? Color.decode("#dbdbd8")
                    : Color.WHITE;

            panel.setBackground(bg);

            // La boucle a été créé à l'aide de l'IA
            // Elle permet de faire en sorte que tous les éléments aient la couleur approprié selon si sélectionné ou non
            // Sans les éléments gardent leur background personnel
            for (Component c : panel.getComponents()) {
                c.setBackground(bg);
                if (c instanceof JPanel) {
                    for (Component cc : ((JPanel) c).getComponents()) {
                        cc.setBackground(bg);
                    }
                }
            }
            return panel;
        }
    }

    /**
     *                  LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    // Gère l'action sur le bouton
    //Sachant que le bouton est dans une JList l'action sur le bouton ne sera pas prise en compte
    // Le code pour prendre en compte l'action dans le cas où on clique sur le bouton a été généré à l'aide de l'IA
    public class MouseListenerActionButton implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int index = listUtilisateur.locationToIndex(e.getPoint());
            if (index < 0) return;

            Rectangle cellBounds = listUtilisateur.getCellBounds(index, index);
            Utilisateur utilisateur = listUtilisateur.getModel().getElementAt(index);

            // Largeur réservée au bouton X (ex: 40px)
            int buttonWidth = 40;
            int xRelative = e.getX() - cellBounds.x;

            if (xRelative > cellBounds.width - buttonWidth) {
                // Clic sur le "bouton"
                supprimerUtilisateur(utilisateur);
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // Non utilisé
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Non utilisé
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // Non utilisé
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // Non utilisé
        }
    }

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de retourner à la page précédente
    public class ButtonBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("RETOUR !!");
            cardLayout.show(mainPanel, "Affichage des réseaux");
            selectionDropdown.supprElementSelect("Reseau");
        }
    }
}
