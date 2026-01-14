package vue.page;

import modele.Log;
import vue.Refreshable;
import vue.SelectionDropdown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

import static java.awt.Color.LIGHT_GRAY;

/**
 * Classe ShowLoggingDetails
 * Une classe correspondant à une page de l'application
 * Elle permet l'affichage des détails d'un log choisit précédemment dans la page affichant la liste des logs
 */
public class ShowLoggingDetails extends JPanel implements Refreshable {
    private SelectionDropdown selectionDropdown;
    private CardLayout cardLayout;
    private JPanel panelFenetre;

    //Constructeur par données
    public ShowLoggingDetails(SelectionDropdown selectionDropdown, CardLayout cardLayout, JPanel panel) throws SQLException {
        super();
        this.selectionDropdown = selectionDropdown;
        this.cardLayout = cardLayout;
        this.panelFenetre = panel;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        //Crée 2 éléments au début : le bouton vide + 1 autre élément qui sera supprimer après pour être remplacé
        this.add(createButtonBack(), BorderLayout.NORTH);
        this.add(new JPanel(), BorderLayout.CENTER);

        this.setVisible(true);
    }

    /**
     *              METHODES DE Creation des différents éléments affichés
     */

    /**
     * Méthode : createPanel
     * ----------
     * créer l'affichage globale en reunissant toutes les parties
     * @return JPanel : l'affichage final de la partie donnée sur le Log
     */
    public JPanel createPanel(){
        /**
         * Création des différentes parties de l'affichage
         * 1. Récupération des données du log
         * 2. Nom du log
         * 3. Affiche trouvé ou non
         * 4. Date et Note
         * 6. Commentaire
         */

        JPanel mainDataPanel = new JPanel();
        JPanel mainPanel = new JPanel();
        Log log = null;

        //Etape 1 : vérifier que l'élément sélectionné est un log avant de récupérer la valeur
        if (selectionDropdown.getElementSelect("Log") instanceof Log){
            log = (Log) selectionDropdown.getElementSelect("Log");
        }
        if  (log != null) {
            //Etape 2
            JLabel labelNom = createLabel("Log " + log.getId());
            labelNom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            //Etape 3
            JLabel labelTrouve = createLabelTrouve(log.isTrouver());

            //Etape 4
            Date date = log.getDate();
            String dateTexte = (date.getDate() < 10 ? "0" : "") + date.getDate() + "/" + (date.getMonth() < 10 ? "0" : "") + date.getMonth() + "/" + date.getYear();

            JLabel labelDate = createLabel(dateTexte);
            JLabel labelNote = createLabel(log.getNote() + "/5");

            labelDate.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
            labelNote.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            //Etape 5
            JLabel commentaire = createLabel(log.getCommentaire());
            commentaire.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

            //Rassemblement des labels
            //Ligne de Date et Note
            JPanel panelDateNote = new JPanel();
            panelDateNote.setLayout(new BoxLayout(panelDateNote, BoxLayout.X_AXIS));

            panelDateNote.setBackground(Color.WHITE);
            panelDateNote.add(labelDate);
            panelDateNote.add(Box.createRigidArea(new Dimension(20, 0)));
            panelDateNote.add(labelNote);
            panelDateNote.setAlignmentX(Component.CENTER_ALIGNMENT);

            //Main panel design
            mainDataPanel.setBackground(Color.WHITE);
            mainDataPanel.setLayout(new BoxLayout(mainDataPanel, BoxLayout.Y_AXIS));

            //Rassemblement de tout
            mainDataPanel.add(labelNom);
            mainDataPanel.add(Box.createRigidArea(new Dimension(0, 20))); //Espace vide
            mainDataPanel.add(labelTrouve);
            mainDataPanel.add(Box.createRigidArea(new Dimension(0, 20))); //Espace vide
            mainDataPanel.add(panelDateNote);
            mainDataPanel.add(Box.createRigidArea(new Dimension(0, 20))); //Espace vide
            mainDataPanel.add(commentaire);

            mainPanel.setBackground(Color.WHITE);
            mainPanel.setLayout(new BorderLayout());

            mainPanel.add(mainDataPanel, BorderLayout.CENTER);
            //Les ajouts suivent permettent de mettre un "cadre" autour de mainDataPanel
            mainPanel.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.NORTH);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 50)), BorderLayout.SOUTH);
            mainPanel.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.EAST);
            mainPanel.add(Box.createRigidArea(new Dimension(50, 0)), BorderLayout.WEST);
        }
        return mainPanel;
    }

    /**
     * Methode : createLabel
     * ---------
     * crée de manière générale le design du Panel
     * @param text : le texte a attribué au Label
     * @return JLabel le label adéquat
     */
    public JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.decode("#dbdbd8"));
        label.setOpaque(true);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        label.setFont(new Font("consolas", Font.BOLD, 20));

        return label;
    }

    /**
     * Methode : createLabelTrouve
     * ---------
     * crée de manière générale pour la cas de Trouvé
     * @param trouve boolean indiquant si le cache a été trouvé ou non à la création du log associé
     * @return JLabel le label pour le cas de trouver
     */
    public JLabel createLabelTrouve(boolean trouve){
        JLabel label = new JLabel(trouve ? "Trouvé" : "Non trouvé");

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true); // permet de définir une couleur de background directement sur le label

        label.setBackground(trouve ? Color.decode("#39741f") : Color.decode("#ff5757"));

        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        label.setFont(new Font("Consolas", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setMaximumSize(new Dimension(200, 80));

        return label;
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
        panelButton.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.SOUTH);

        return panelButton;
    }


    /**
     *          REFRESHDATA
     */

    /**
     * Methode : RefreshData
     * -------
     * Permet de refresh les données quand on revient sur cette vue
     * Crée un nouvel affichage selon le Log choisit
     */
    @Override
    public void refreshData() {
        this.remove(1); //Supprime uniquement l'affichage des données du Log
        this.add(createPanel(),  BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    /**
     *              LISTENER
     */

    // classe interne à la vue car elle y est spécifique
    // Permet de retourner à la page précédente en gardant les valeurs de filtre choisies précédemment
    public class ButtonBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(panelFenetre, "Affichage des logs");
            selectionDropdown.supprElementSelect("Log");
        }
    }


}