package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowListCache extends JPanel {

    public ShowListCache() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Show List Cache");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}