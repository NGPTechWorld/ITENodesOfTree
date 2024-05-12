package Module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import Controller.ColorController;



public class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            // تحديد لون الخلفية لشريط التمرير
            trackColor = Color.decode("#E3E3E3");
            setThumbBounds(0, 0, 10, 10);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        // إنشاء زر بحجم صفر
        private JButton createZeroButton() {
            JButton button = new JButton();
            Dimension zeroDim = new Dimension(0, 0);
            button.setPreferredSize(zeroDim);
            button.setMinimumSize(zeroDim);
            button.setMaximumSize(zeroDim);
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            // تخصيص لون الشريط المتحرك (Thumb)
            g.setColor(Color.decode("#7F7F7F")); // يمكنك تغيير هذا اللون حسب احتياجاتك
            ((Graphics2D) g).fill(thumbBounds);
        }
    }