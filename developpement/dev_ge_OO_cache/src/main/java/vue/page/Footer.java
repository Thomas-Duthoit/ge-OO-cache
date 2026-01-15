package vue.page;

import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.FenetrePrincipal;
import vue.Refreshable;
import vue.SelectionDropdown;
import vue.dropdown.ComboBoxGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Footer extends JPanel {

    private CardLayout cl;
    private JPanel mainPanel;
    private RequeteGeOOCache requeteGeOOCache;
    private SelectionDropdown selectionDropdown;
    private FenetrePrincipal fenetrePrincipal;
    private ComboBoxGeneral cb;



    public Footer(FenetrePrincipal fenetrePrincipal, RequeteGeOOCache requeteGeOOCache, JPanel mainPanel, CardLayout cl, SelectionDropdown selectionDropdown, ComboBoxGeneral cb) {

        //Initialisation des attributs
        this.requeteGeOOCache = requeteGeOOCache;
        this.cl = cl;
        this.mainPanel = mainPanel;
        this.selectionDropdown = selectionDropdown;
        this.fenetrePrincipal = fenetrePrincipal;
        this.cb = cb;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton buttonDeconnexion = new JButton("> Deconnexion !");
        buttonDeconnexion.setBackground(Color.decode("#c8d400"));
        buttonDeconnexion.setPreferredSize(new Dimension(250, 45));
        buttonDeconnexion.setMaximumSize(new Dimension(250, 45));
        buttonDeconnexion.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonDeconnexion.setFont(new Font("Consolas", Font.BOLD, 20));

        buttonDeconnexion.addActionListener(new ActionDeconnexionListener());

        this.add(buttonDeconnexion, BorderLayout.EAST);

        JButton buttonAccueil = new JButton("Retour à l'Accueil");
        buttonAccueil.setBackground(Color.decode("#c8d400"));
        buttonAccueil.setPreferredSize(new Dimension(250, 45));
        buttonAccueil.setMaximumSize(new Dimension(250, 45));
        buttonAccueil.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonAccueil.setFont(new Font("Consolas", Font.BOLD, 20));

        buttonAccueil.addActionListener(new ActionHomeListener());


        this.add(buttonAccueil, BorderLayout.WEST);
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

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    //Listener qui s'activer au click sur le bouton déconnexion.
    //Effectue la déconnexion de l'utilisateur et le retour à l'affichage de connexion
    public class ActionDeconnexionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fenetrePrincipal.deconnexionMainPanel();
        }
    }

    public class ActionHomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cl.show(mainPanel, "Accueil");
            refreshDataView();
            selectionDropdown.addElementSelect("Action", "Accueil");
            cb.refreshComboBoxAction();
        }
    }

}
