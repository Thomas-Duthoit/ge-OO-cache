package vue.page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ShowStatistic extends JPanel {

    public ShowStatistic() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Show statistic");
        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

}