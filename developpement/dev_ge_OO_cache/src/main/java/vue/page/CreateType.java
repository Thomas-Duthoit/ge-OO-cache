package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CreateType extends JPanel {

    public CreateType() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Create Type");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}