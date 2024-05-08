import java.awt.*;
import javax.swing.*;

import Controller.ColorController;

public class HomePage extends JFrame{
    HomePage(){
        initComponents();
    }
    private void initComponents(){
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);
        //---------BackGround-----------------
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = ColorController.firstColor(); 
                Color color2 = ColorController.secoundColor();
                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);
        //------------------------------------
        JLabel titleApp=new JLabel("ITE Nodes Of Tree");
        




        add(backgroundPanel);
        setVisible(true);
    }
}