package task1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class PointMovement extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private int x = 50; // початкова позиція точки по осі X
    private int y = 10; // початкова позиція точки по осі Y

    private JLabel speedLabel;
    private int speed = 5; // швидкість руху точки
    int kx = 1;
    private Timer timer;

    public PointMovement() {
        setLayout(null);
        JButton increaseSpeedButton = new JButton("Збільшити швидкість");
        increaseSpeedButton.setBounds(10, 200, 200, 30);
        increaseSpeedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                speed += 1; // збільшити швидкість на 1
                speedLabel.setText("Швидкість: " + speed);
            }
        });
        add(increaseSpeedButton);

        JButton decreaseSpeedButton = new JButton("Зменшити швидкість");
        decreaseSpeedButton.setBounds(220, 200, 200, 30);
        decreaseSpeedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                speed -= 1; // зменшити швидкість на 1
                speedLabel.setText("Швидкість: " + speed);
            }
        });
        add(decreaseSpeedButton);

        speedLabel = new JLabel("Швидкість: " + speed);
        speedLabel.setBounds(175, 150, 200, 30);
        add(speedLabel);

        timer = new Timer(10, this); // частота оновлення екрану - 10 мілісекунд
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        x += speed * kx; // змінити позицію точки по осі X залежно від швидкості
        if (x > getWidth() - 25) {
            kx = -kx;
        }
        if (x < 0) {
            kx = - kx;
        }
        repaint(); // перемалювати екран
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(x, y, 20, 20); // намалювати червону точку
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Point Movement");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 280);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new PointMovement());
        frame.setVisible(true);
    }
}