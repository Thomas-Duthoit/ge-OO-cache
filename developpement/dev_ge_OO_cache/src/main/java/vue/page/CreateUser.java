package vue.page;

import requete.RequeteGeOOCache;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CreateUser extends JPanel {

    public CreateUser(RequeteGeOOCache requeteGeOOCache) throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Create Utilisateur");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}
