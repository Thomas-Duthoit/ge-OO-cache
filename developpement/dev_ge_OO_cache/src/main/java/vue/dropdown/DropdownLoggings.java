package vue.dropdown;

import modele.Log;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DropdownLoggings extends JPanel {
    private RequeteGeOOCache requeteGeOOCache;
    private CardLayout cl;
    private JPanel mainPanel;
    private JComboBox<Object> cb;
    private String actionPrec;
    private SelectionDropdown selectionDropdown;

    public DropdownLoggings(RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, String actionPrec, Utilisateur user, SelectionDropdown selectionDropdown) throws SQLException {
        this.requeteGeOOCache = requeteGeOOCache;
        this.mainPanel = mainPanel;
        this.cl = cl;
        this.actionPrec = actionPrec;
        this.selectionDropdown = selectionDropdown;
        System.out.println(user.toString());

        //Mise en forme
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Les différentes valeurs a attribué pour le dropdown
        //Récupération de la liste des logs de l'utilisateur
        List<Log> logsUtilisateur = this.requeteGeOOCache.getLogs(user, null, null);
        System.out.println(logsUtilisateur.toString());
        //Création du dropdown
        this.cb = new JComboBox<>();

        DefaultComboBoxModel<Object>  cbModel = new DefaultComboBoxModel<>();
        cbModel.addElement("Choix du logging");
        if  (logsUtilisateur != null) {
            //Création du modèle de la JComboBox
            for (Log log : logsUtilisateur) {
                cbModel.addElement(log);
            }
        }
        this.cb.setModel(cbModel);


        cb.setSelectedIndex(0); //Valeur par défaut au niveau des choix
        cb.setBackground(Color.LIGHT_GRAY);
        cb.addActionListener(new ChoixActionListener(this.mainPanel, this.cl, this.requeteGeOOCache));


        this.add(cb);
        this.setVisible(true);

    }

    public void refresh() {
        revalidate();
        repaint();
    }

    public class  ChoixActionListener implements ActionListener {
        private JPanel mainPanel;
        private CardLayout cl;
        private RequeteGeOOCache requeteGeOOCache;

        public ChoixActionListener(JPanel mainPanel, CardLayout cl, RequeteGeOOCache requeteGeOOCache) {
            this.mainPanel = mainPanel;
            this.cl = cl;
            this.requeteGeOOCache = requeteGeOOCache;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String choixSelectionne = cb.getSelectedItem().toString();
            System.out.println("Action selectionné : " + choixSelectionne);

            if (!choixSelectionne.equals("Choix des loggings")) {
                selectionDropdown.addElementSelect("Log", cb.getSelectedItem());
                System.out.println("test");
                cl.show(mainPanel, "Afficher les logging détails");

                refreshDataView();
            }else {
                selectionDropdown.supprElementSelect("Log");
                cl.show(mainPanel, "Choix de l'interface");
            }
            refresh();
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
    }
}
