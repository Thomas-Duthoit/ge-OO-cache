package vue.page;

import vue.FenetrePrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JPanel{
    private FenetrePrincipal fenetrePrincipal;

    public Login(FenetrePrincipal fenetrePrincipal) throws SQLException {
        super();
        this.fenetrePrincipal = fenetrePrincipal;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JLabel label =  new JLabel();
        label.setText("Login");

        JButton button = new JButton("Login");

        button.addActionListener(new MyButtonListener());

        this.add(button, BorderLayout.SOUTH);

        this.add(label,BorderLayout.NORTH);
        this.setVisible(true);
    }

    public class  MyButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            fenetrePrincipal.loginValider();
        }
    }
}
