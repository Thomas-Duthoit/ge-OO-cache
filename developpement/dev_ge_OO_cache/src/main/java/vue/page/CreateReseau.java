package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CreateReseau extends JPanel {

    public CreateReseau() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Create Reseau");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}