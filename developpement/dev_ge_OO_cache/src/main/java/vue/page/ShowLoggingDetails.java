package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowLoggingDetails extends JPanel {

    public ShowLoggingDetails() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Show Logging Details");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}