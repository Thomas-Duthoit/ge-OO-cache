package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowCaches extends JPanel {

    public ShowCaches() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Show Caches");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}