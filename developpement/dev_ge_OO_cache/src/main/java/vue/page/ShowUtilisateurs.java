package vue.page;

import modele.Log;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;
import vue.dropdown.ComboBoxGeneral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class ShowUtilisateurs extends JPanel implements Refreshable {
    private RequeteGeOOCache requeteGeOOCache;
    private Utilisateur utilisateur;

    private JList<Utilisateur> utilisateurs;

    //Pour modifier la vue
    private CardLayout cl;
    private JPanel mainPanel;
    private ComboBoxGeneral comboBoxGeneral;


    public ShowUtilisateurs(RequeteGeOOCache requeteGeOOCache, Utilisateur utilisateur, SelectionDropdown selectionDropdown, CardLayout cl, JPanel mainPanel, ComboBoxGeneral comboBoxGeneral){
        super();
        this.requeteGeOOCache = requeteGeOOCache;
        this.utilisateur = utilisateur;
        this.cl = cl;
        this.mainPanel = mainPanel;
        this.comboBoxGeneral = comboBoxGeneral;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        this.utilisateurs = new JList<>();
        this.utilisateurs.addMouseListener(new MouseUtilisateurListener(selectionDropdown));

        //Ajout d'un Renderer pour le design sur celui qui a le focus
        this.utilisateurs.setCellRenderer(new rendererJListUtilisateur());

        this.utilisateurs.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.utilisateurs.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        //Ajout de la barre de défilement de la JList
        JScrollPane scrollPaneListLog = new JScrollPane(utilisateurs);
        scrollPaneListLog.setFont(new Font("consolas", Font.BOLD, 20));

        JPanel panelList = new JPanel();
        panelList.setLayout(new BorderLayout());
        panelList.add(this.utilisateurs, BorderLayout.CENTER);

        //Mise en place d'un cadre vide autour de panelList
        this.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.NORTH);
        this.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.SOUTH);
        this.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.EAST);
        this.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.WEST);
        this.add(panelList, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public DefaultListModel<Utilisateur> createModelJListUtilisateur(Utilisateur utilisateurConnecte){
        DefaultListModel<Utilisateur> modelUtilisateur = new DefaultListModel<>();

        java.util.List<Utilisateur> utilisateurs = this.requeteGeOOCache.getListeUtilisateurs();

        for(Utilisateur user : utilisateurs){
            if(!user.equals(utilisateurConnecte)) {
                modelUtilisateur.addElement(user);
            }
        }

        return modelUtilisateur;
    }

    @Override
    public void refreshData() {
        //Récupère l'utilisateur sélectionné dans la dropdown
        this.utilisateurs.setModel(createModelJListUtilisateur(this.utilisateur));
        revalidate();
        repaint();
    }

    /**
     * Methode : RefreshDataView
     * -------
     * Cette méthode a été proposé par l'IA
     * -------
     * permet de trouver quelle vue est actuellement la vue courante du cardLayout et d'activer la méthode refreshData si implémentée dans la classe
     */
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
    }

    // classe interne à la vue car elle y est spécifique
    // Permet de réagir à un double clic sur un élément de la JList des logs : change de vue pour celle du détails de la log
    public class MouseUtilisateurListener implements MouseListener {
        private SelectionDropdown selectionDropdown;

        public MouseUtilisateurListener(SelectionDropdown selectionDropdown){
            this.selectionDropdown = selectionDropdown;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2){
                System.out.println("Affichage des détails d'un login");
                JList log = (JList) e.getSource();
                Utilisateur selected =  (Utilisateur) log.getSelectedValue();
                System.out.println(selected);
                this.selectionDropdown.addElementSelect("Utilisateur", selected);

                System.out.println(this.selectionDropdown.getElementSelect("Utilisateur"));

                cl.show(mainPanel, "Associer un utilisateur");
                refreshDataView(); //Permet d'activer la méthode refreshData de la vue d'affichage des détails d'un log

                comboBoxGeneral.refreshComboBoxUtilisateur();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //Non utilisé
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //Non utilisé
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //Non utilisé
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //Non utilisé
        }
    }

    /**
     *              RENDERER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de modifier le rendu de la JList selon si l'élément subit le focus ou non
    public class rendererJListUtilisateur implements ListCellRenderer<Utilisateur>{


        @Override
        public Component getListCellRendererComponent(JList<? extends Utilisateur> list, Utilisateur value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = new JLabel();
            label.setText(value.getPseudo());

            if (cellHasFocus) {
                label.setForeground(Color.BLACK);
                label.setOpaque(true);
                label.setBackground(Color.decode("#dbdbd8"));
            } else {
                label.setForeground(Color.BLACK);
            }
            label.setFont(new Font("consolas", Font.BOLD, 15));

            return label;
        }
    }




}
