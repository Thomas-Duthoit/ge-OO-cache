package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CreateCache extends JPanel {

    public CreateCache() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Create Cache");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}
