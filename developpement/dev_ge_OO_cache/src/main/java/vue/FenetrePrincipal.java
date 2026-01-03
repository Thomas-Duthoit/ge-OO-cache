package vue;

import modele.Utilisateur;
import requete.RequeteGeOOCache;
import vue.dropdown.ComboBoxGeneral;
import vue.page.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Classe FenetrePrincipal
 * Fenêtre générale : permet de gérer les pages
 */
public class FenetrePrincipal extends JFrame {
    //Attributs
    private CardLayout cl;
    private JPanel mainPanel;
    private RequeteGeOOCache requeteGeOOCache;
    private Utilisateur user;
    private SelectionDropdown selectionDropdown;
    private ComboBoxGeneral comboBoxGeneral;

    //Constructeur par défaut
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
     *              METHODES
     */

    /**
     * méthode : loginValide
     * ---------------------
     * permet de changer la page si le login de connexion a été validé dans la classe login
     */
    public void loginValider(Utilisateur user) {
        //Enregistrement de l'utilisateur connecté
        this.user = user;
        //Création du dropdownTop qui restera fixe
        //Besoin de la mettre ici pour avoir un user fixe
        try{
            this.comboBoxGeneral = new ComboBoxGeneral(this.requeteGeOOCache, mainPanel, cl, this.user, this.selectionDropdown);
            addAllView();
        }catch (SQLException e){
            System.out.println("Erreur de créer de la dropDown à la connexion");
        }

        this.add(this.comboBoxGeneral, BorderLayout.NORTH);
        this.cl.show(this.mainPanel, "Choix de l'interface");
        this.revalidate();
        this.repaint();
    }

    /**
     * Méthode : addAllView
     * ------
     * permet d'ajouter la liste de toutes les vues dans le panel
     */
    public void addAllView(){
        try {
            mainPanel.add(new Accueil(), "Choix de l'interface");
            mainPanel.add(new CreateReseau(this.requeteGeOOCache, this.user), "Créer un réseau");
            mainPanel.add(new ShowReseau(this.requeteGeOOCache, this.user, this.mainPanel, this.cl, this.selectionDropdown, this.comboBoxGeneral), "Affichage des réseaux");
            mainPanel.add(new AssociateUser(this.requeteGeOOCache, this.user, this.selectionDropdown, this.comboBoxGeneral), "Associer un utilisateur");
            mainPanel.add(new ShowCaches(this.requeteGeOOCache, this.selectionDropdown), "Affichage de la liste des caches");
            mainPanel.add(new ShowStatistic(this.requeteGeOOCache, this.selectionDropdown), "Afficher les statistiques");
            mainPanel.add(new ShowLoggings(this.requeteGeOOCache, this.user, this.selectionDropdown, this.cl, this.mainPanel), "Afficher les loggins");
            mainPanel.add(new ShowLoggingDetails(this.selectionDropdown, this.cl, this.mainPanel), "Afficher les logging détails");
            mainPanel.add(new CreateCache(this.requeteGeOOCache, this.selectionDropdown), "Créer une cache");
            mainPanel.add(new CreateUser(this.requeteGeOOCache), "Créer un utilisateur");
            mainPanel.add(new UpdateStatutCache(this.requeteGeOOCache, this.selectionDropdown), "Modifier le statut d'une cache");
            mainPanel.add(new ShowListCache(this.requeteGeOOCache, this.selectionDropdown), "Liste des caches");
            mainPanel.add(new CreateType(this.requeteGeOOCache), "Créer un type");
        }catch(SQLException e){
            System.out.println("Erreur d'ajout des vues de l'application");
        }
    }

    /**
     *              REFRESH
     */

    /**
     * Permet de refresh la vue après la modification d'une dropdown
     */
    //Méthode pour refresh la vue
    public void refreshMainPanel() {
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    /**
     *                      GETTER
     */

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
}
