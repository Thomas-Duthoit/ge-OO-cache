package vue;

import modele.ReseauCache;
import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.dropdown.DropdownTopChoix;
import vue.page.*;

import vue.Refreshable;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class FenetrePrincipal extends JFrame {
    private CardLayout cl;
    private JPanel mainPanel;
    private RequeteGeOOCache requeteGeOOCache;
    private DropdownTopChoix dropdownTopChoix;
    private Utilisateur user;
    private SelectionDropdown selectionDropdown;

    public FenetrePrincipal() throws SQLException {
        super();

        //Initialisation des attributs
        this.cl = new CardLayout();
        this.mainPanel = new JPanel(this.cl);
        this.requeteGeOOCache = new RequeteGeOOCache();
        this.user = null;
        this.selectionDropdown = new SelectionDropdown(this);

        //Configuration de la fenêtre
        this.setTitle("gé-OO-cache");
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Vue de connexion
        mainPanel.add(new Login(this), "Login");

        //Mise en page sur la fenêtre principale
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER); // Le contenu change au milieu
        this.setVisible(true);
    }

    /**
     * méthode : loginValide
     * ---------------------
     * permet de changer la page si le login de connexion a été validé dans la classe longin
     */
    public void loginValider(Utilisateur user) {
        //Enregistrement de l'utilisateur connecté
        this.user = user;
        //Création du dropdownTop qui restera fixe
        //Besoin de la mettre ici pour avoir un user fixe
        try{
            this.dropdownTopChoix = new DropdownTopChoix(this.requeteGeOOCache, mainPanel, cl, this.user, this.selectionDropdown);
            //Les différentes vues possibles
            mainPanel.add(new Accueil(), "Choix de l'interface");
            mainPanel.add(new CreateReseau(this.requeteGeOOCache, this.user), "Créer un réseau");
            mainPanel.add(new ShowReseau(this.requeteGeOOCache, this.user, this.mainPanel, this.cl, this.selectionDropdown), "Affichage des réseaux");
            mainPanel.add(new AssociateUser(this.requeteGeOOCache, this.user, this.selectionDropdown), "Associer un utilisateur");
            mainPanel.add(new ShowCaches(this.requeteGeOOCache, this.selectionDropdown), "Affichage de la liste des caches");
            mainPanel.add(new ShowStatistic(this.requeteGeOOCache, this.selectionDropdown), "Afficher les statistiques");
            mainPanel.add(new ShowLoggings(this.requeteGeOOCache, this.selectionDropdown), "Afficher les loggins");
            mainPanel.add(new ShowLoggingDetails(this.requeteGeOOCache, this.selectionDropdown), "Afficher les logging détails");
            mainPanel.add(new CreateCache(this.requeteGeOOCache, this.selectionDropdown) , "Créer une cache");
            mainPanel.add(new CreateUser(this.requeteGeOOCache), "Créer un utilisateur");
            mainPanel.add(new UpdateStatutCache(this.requeteGeOOCache, this.selectionDropdown), "Modifier le statut d'une cache");
            mainPanel.add(new ShowListCache(this.requeteGeOOCache, this.selectionDropdown), "Liste des caches");
            mainPanel.add(new CreateType(this.requeteGeOOCache), "Créer un type");
        }catch (SQLException e){
            System.out.println("Erreur de créer de la dropDown à la connexion");
        }
        this.dropdownTopChoix.setVisible(true);
        this.add(this.dropdownTopChoix, BorderLayout.NORTH); // Le menu reste toujours en haut
        this.cl.show(this.mainPanel, "Choix de l'interface");
        this.revalidate();
        this.repaint();
    }

    public RequeteGeOOCache getRequeteGeOOCache() {
        return requeteGeOOCache;
    }

    public static void main(String[] args) {
        try{
            FenetrePrincipal fp = new FenetrePrincipal();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erreur de connexion à la base de données");
            System.out.println(e.getMessage());
        }
    }

    //Méthode pour refresh la vue
    public void refreshMainPanel() {
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }
}
