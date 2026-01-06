package vue.page;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


public class ManageUser extends JPanel implements Refreshable {
    private JList<Utilisateur> listUtilisateur;
    private SelectionDropdown selectionDropdown;
    private RequeteGeOOCache requeteGeOOCache;

    public ManageUser(SelectionDropdown selectionDropdown, RequeteGeOOCache requeteGeOOCache) {
        JPanel panelManageUser = new JPanel();
        this.selectionDropdown = selectionDropdown;
        this.requeteGeOOCache = requeteGeOOCache;
        this.listUtilisateur = new JList<>();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(listUtilisateur);
        scrollPane.setFont(new Font("consolas", Font.BOLD, 15));
        listUtilisateur.setCellRenderer(new ListUtilisateurCellRenderer());
        listUtilisateur.addMouseListener(new MouseListenerActionButton());

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        panelManageUser.setLayout(new BoxLayout(panelManageUser, BoxLayout.Y_AXIS));
        panelManageUser.setBackground(Color.WHITE);
        panelManageUser.add(createPanelTop());
        panelManageUser.add(scrollPane);

        this.add(panelManageUser, BorderLayout.CENTER);
        this.add(Box.createRigidArea(new Dimension(0, 50)),  BorderLayout.NORTH);
        this.add(Box.createRigidArea(new Dimension(0, 50)),  BorderLayout.SOUTH);
        this.add(Box.createRigidArea(new Dimension(100, 0)),  BorderLayout.EAST);
        this.add(Box.createRigidArea(new Dimension(100, 0)),  BorderLayout.WEST);
        this.setVisible(true);
    }

    public JPanel createPanelTop(){
        JLabel labelUtilisateur = new JLabel("Utilisateur");
        JLabel labelSuppr = new JLabel("Supprimer");
        JPanel panelTop = new JPanel();
        panelTop.setBackground(Color.decode("#dbdbd8"));
        panelTop.setLayout(new BorderLayout());

        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(Color.decode("#dbdbd8"));
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.X_AXIS));
        panelLeft.add(Box.createRigidArea(new Dimension(10, 0)));
        panelLeft.add(labelUtilisateur);

        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.decode("#dbdbd8"));
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.X_AXIS));
        panelRight.add(labelSuppr);
        panelRight.add(Box.createRigidArea(new Dimension(10, 0)));

        panelTop.add(panelLeft, BorderLayout.WEST);
        panelTop.add(panelRight, BorderLayout.EAST);

        panelTop.setSize(new Dimension(Integer.MAX_VALUE, 80));
        panelTop.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        return panelTop;
    }

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


    @Override
    public void refreshData() {
        //Récupère le réseau choisit
        ReseauCache reseauCache = (ReseauCache) this.selectionDropdown.getElementSelect("Reseau");

        listUtilisateur.setModel(createDefaultModelUtilisateur(reseauCache));
    }

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

    public JPanel createPanelRenderer(Utilisateur utilisateur, boolean proprietaire){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        JLabel labelUtilisateur = new JLabel("Utilisateur : " + utilisateur.getPseudo());
        labelUtilisateur.setFont(new Font("consolas", Font.BOLD, 15));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        leftPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        leftPanel.add(labelUtilisateur);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));

        if (proprietaire){
            JLabel statutUtilisateur = new JLabel("( Propriétaire )");
            statutUtilisateur.setFont(new Font("consolas", Font.PLAIN, 15));
            rightPanel.add(statutUtilisateur);
        }else{
            JButton statutUtilisateur = new JButton("X");
            statutUtilisateur.setBackground(Color.WHITE);
            rightPanel.add(statutUtilisateur);
        }

        rightPanel.add(Box.createRigidArea( new Dimension(10, 0)));

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        panel.setOpaque(true);
        leftPanel.setOpaque(true);
        rightPanel.setOpaque(true);

        return panel;
    }

    public void supprimerUtilisateur(Utilisateur utilisateur){
        ReseauCache reseauCache = (ReseauCache) selectionDropdown.getElementSelect("Reseau");
        System.out.println("Action Suppression Utilisateur de l'association avec " + utilisateur.getPseudo());

        boolean result = requeteGeOOCache.deleteAccessReseau(utilisateur, reseauCache);
        System.out.println(result);

        listUtilisateur.setModel(createDefaultModelUtilisateur(reseauCache));
    }

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

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
