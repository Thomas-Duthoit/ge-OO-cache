package vue.dropdown;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.SelectionDropdown;

import javax.print.attribute.standard.JobKOctets;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DropdownUtilisateur extends JPanel {
    private RequeteGeOOCache requeteGeOOCache;
    private CardLayout cl;
    private JPanel dynamicArea;
    private JPanel mainPanel;
    private JComboBox<Object> cb;
    private String actionPrec;
    private SelectionDropdown selectionDropdown;

    public DropdownUtilisateur(RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, String actionPrec, SelectionDropdown selectionDropdown) throws SQLException {
        this.requeteGeOOCache = requeteGeOOCache;
        this.mainPanel = mainPanel;
        this.cl = cl;
        this.actionPrec = actionPrec;
        this.selectionDropdown = selectionDropdown;

        //Mise en forme
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Les différentes valeurs a attribué pour le dropdown
        //Création du dropdown
        this.cb = new JComboBox<>();

        //Création du modèle pour la comboBox
        DefaultComboBoxModel<Object> cbModel = new DefaultComboBoxModel<>();
        cbModel.addElement("Choix de l'utilisateur");
        //Récupère dans une requête de la liste des Utilisateurs
        List<Utilisateur> utilisateurs = this.requeteGeOOCache.getListeUtilisateurs();
        for(Utilisateur utilisateur : utilisateurs){
            cbModel.addElement(utilisateur);
        }
        this.cb.setModel(cbModel);

        cb.setSelectedIndex(0); //Valeur par défaut au niveau des choix
        cb.setBackground(Color.LIGHT_GRAY);

        //Au niveau des changements de vue par rapport au choix
        cb.addActionListener(new ChoixActionListener(this.mainPanel, this.cl, this.requeteGeOOCache));

        this.add(cb);
        this.dynamicArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add(dynamicArea);
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
        private SelectionDropdown selectionDropdown;

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

            //Ajout d'autres dropdowns si nécessaire selon le choix
            dynamicArea.removeAll();

            refresh();
            if (!choixSelectionne.equals("Choix d'un utilisateur")){
                selectionDropdown.addElementSelect("Utilisateur", cb.getSelectedItem());
                cl.show(mainPanel, actionPrec);
            }else{
                selectionDropdown.supprElementSelect("Utilisateur");
                cl.show(mainPanel, "Choix de l'interface");
            }
        }
    }

    public Utilisateur getUtilisateurSelectionne(){
        return (Utilisateur) cb.getSelectedItem();
    }
}
