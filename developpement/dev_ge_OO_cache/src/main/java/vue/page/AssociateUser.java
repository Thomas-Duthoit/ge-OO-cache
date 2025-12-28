package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AssociateUser extends JPanel {

    public AssociateUser() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Associate Users");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}