package vue.page;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

public class Accueil extends JPanel {

    public Accueil() throws SQLException {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/logo.png"));
            Image scaledImg = img.getScaledInstance(600, 300, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
            this.add(imageLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.setVisible(true);
    }

}
