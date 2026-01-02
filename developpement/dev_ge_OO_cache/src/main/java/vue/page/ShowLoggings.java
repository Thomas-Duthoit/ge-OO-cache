package vue.page;

import requete.RequeteGeOOCache;
import vue.SelectionDropdown;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowLoggings extends JPanel {

    public ShowLoggings(RequeteGeOOCache requeteGeOOCache, SelectionDropdown selectionDropdown) throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Show Loggings");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}