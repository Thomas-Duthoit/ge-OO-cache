package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowReseau extends JPanel {

    public ShowReseau() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label = new JLabel();
        label.setText("Show Reseau");
        this.add(label, BorderLayout.NORTH);
        this.setVisible(true);
    }
}