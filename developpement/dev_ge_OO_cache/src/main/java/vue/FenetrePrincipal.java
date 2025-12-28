package vue;

import requete.RequeteGeOOCache;
import vue.dropdown.DropdownTopChoix;
import vue.page.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class FenetrePrincipal extends JFrame {
    private CardLayout cl;
    private JPanel mainPanel;
    private RequeteGeOOCache requeteGeOOCache;
    private DropdownTopChoix dropdownTopChoix;

    public FenetrePrincipal() throws SQLException {
        super();
        //Initialisation des attributs
        this.cl = new CardLayout();
        this.mainPanel = new JPanel(this.cl);
        this.requeteGeOOCache = new RequeteGeOOCache();

        //Configuration de la fenêtre
        this.setTitle("gé-OO-cache");
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Création du dropdownTop qui restera fixe
        this.dropdownTopChoix = new DropdownTopChoix(this.requeteGeOOCache, mainPanel, cl);
        //Vue de connexion
        mainPanel.add(new Login(this), "Login");

        //Les différentes vues possibles
        mainPanel.add(new Accueil(), "Choix de l'interface");
        mainPanel.add(new CreateReseau(), "Créer un réseau");
        mainPanel.add(new ShowReseau(), "Affichage des réseaux");
        mainPanel.add(new AssociateUser(), "Associer un utilisateur");
        mainPanel.add(new ShowCaches(), "Affichage de la liste des caches");
        mainPanel.add(new ShowStatistic(), "Afficher les statistiques");
        mainPanel.add(new ShowLoggings(), "Afficher les loggins");
        mainPanel.add(new ShowLoggingDetails(), "Afficher les logging détails");
        mainPanel.add(new CreateCache() , "Créer une cache");
        mainPanel.add(new CreateUser(), "Créer un utilisateur");
        mainPanel.add(new UpdateStatutCache(), "Modifier le statut d'une cache");
        mainPanel.add(new ShowListCache(), "Liste des caches");
        mainPanel.add(new CreateType(), "Créer un type");

        //Mise en page sur la fenêtre principale
        this.setLayout(new BorderLayout());
        this.add(this.dropdownTopChoix, BorderLayout.NORTH); // Le menu reste toujours en haut
        this.add(mainPanel, BorderLayout.CENTER); // Le contenu change au milieu
        this.dropdownTopChoix.setVisible(false);
        this.setVisible(true);
    }

    public void loginValider(){
        this.dropdownTopChoix.setVisible(true);
        this.cl.show(this.mainPanel, "Choix de l'interface");
        this.revalidate();
        this.repaint();
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
