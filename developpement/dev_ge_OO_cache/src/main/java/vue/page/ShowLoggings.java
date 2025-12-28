package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowLoggings extends JPanel {

    public ShowLoggings() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Show Loggings");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}