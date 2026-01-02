package vue.page;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ShowReseau extends JPanel {
    private RequeteGeOOCache requeteGeOOCache;
    private Utilisateur utilisateur;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private SelectionDropdown selectionDropdown;

    public ShowReseau(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, JPanel mainPanel, CardLayout cl, SelectionDropdown selectionDropdown) throws SQLException {
        super();
        //Attributs
        this.requeteGeOOCache = requeteGeOOCache;
        this.utilisateur = utilisateur;
        this.mainPanel = mainPanel;
        this.cardLayout = cl;
        this.selectionDropdown = selectionDropdown;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        //Récupération des valeurs pour la liste
        List<ReseauCache> reseaux = this.requeteGeOOCache.getReseauxUtilisateur(utilisateur);
        System.out.println(reseaux);

        JList<ReseauCache> listReseaux = new JList<>();
        DefaultListModel<ReseauCache> listReseauxModel = new DefaultListModel<>();
        for (ReseauCache reseau : reseaux){
            listReseauxModel.addElement(reseau);
        }
        listReseaux.setModel(listReseauxModel);

        listReseaux.setFont(new Font("consolas", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(listReseaux);
        scrollPane.setFont(new Font("consolas", Font.BOLD, 20));

        MyRenderer rendu = new MyRenderer();
        listReseaux.setCellRenderer(rendu);

        //listReseaux.addMouseListener(new MouseReseauListener());

        //Design
        JPanel listPanel = new JPanel();
        listPanel.setBackground(Color.WHITE);
        listPanel.setLayout(new BorderLayout());
        listPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        listPanel.add(listReseaux, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(50), BorderLayout.WEST);  // espace à gauche
        this.add(Box.createHorizontalStrut(50), BorderLayout.EAST);  // espace à droite
        this.add(Box.createVerticalStrut(50), BorderLayout.NORTH);  // espace en haut
        this.add(Box.createVerticalStrut(50), BorderLayout.SOUTH);  // espace en bas
        listPanel.setMinimumSize(new Dimension(500, 500));

        this.add(listPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public class MyRenderer implements ListCellRenderer<ReseauCache>{
        @Override
        public Component getListCellRendererComponent(JList<? extends ReseauCache> list, ReseauCache value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = new JLabel();
            int emp = 0;
            label.setText(value.getNom());

            if (cellHasFocus) {
                label.setForeground(Color.BLACK);
                label.setOpaque(true);
                label.setBackground(Color.LIGHT_GRAY);
            } else {
                label.setForeground(Color.BLACK);
            }
            return label;
        }
    }
    /*
    public class MouseReseauListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { // double-clic, tu peux mettre 1 pour simple clic
                JList list = (JList)e.getSource();
                ReseauCache selected = (ReseauCache) list.getSelectedValue();
                selectionDropdown.addElementSelect("Reseau", selected);
                cardLayout.show(mainPanel, "Affichage de la liste des caches");
                refreshDataView();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //System.out.println(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //System.out.println(e);
        }
    }


    //Cette méthode a été proposé par l'IA
    public void refreshDataView(){
        //On refresh les valeurs pour la vue courante
        //Dans le cas où il s'agit d'une vue nécessitant des valeurs dans les dropdowns
        Component c = Arrays.stream(mainPanel.getComponents())
                .filter(comp -> comp.isVisible())
                .findFirst()
                .orElse(null);

        if (c instanceof Refreshable) {
            ((Refreshable) c).refreshData();
        }
    }*/
}