package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AirHockey extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800, HEIGHT = 500;
    private static final int PADDLE_WIDTH = 20, PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 20;

    private int paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int paddleSpeed = 10;

    private int ballX = WIDTH / 2 - BALL_SIZE / 2;
    private int ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballVelocityX = 5, ballVelocityY = 3;

    private int score1 = 0, score2 = 0;

    private Timer timer;

    public AirHockey() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(1000 / 60, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Chiziq
        g.setColor(Color.WHITE);
        g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

        // O'yinchilarning ballari
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(String.valueOf(score1), WIDTH / 4, 50);
        g.drawString(String.valueOf(score2), WIDTH * 3 / 4, 50);

        // Raketkalar
        g.fillRect(50, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - 70, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);

        // Shayba
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballVelocityX;
        ballY += ballVelocityY;

        // Devorlarga urilganda yo'nalishni o'zgartirish
        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballVelocityY *= -1;
        }


        if (ballX <= 70 && ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballVelocityX *= -1;
        }
        if (ballX >= WIDTH - 90 && ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballVelocityX *= -1;
        }


        if (ballX <= 0) {
            score2++;
            resetBall();
        }
        if (ballX >= WIDTH - BALL_SIZE) {
            score1++;
            resetBall();
        }

        repaint();
    }

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballVelocityX = -ballVelocityX;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W && paddle1Y > 0) {
            paddle1Y -= paddleSpeed;
        }
        if (e.getKeyCode() == KeyEvent.VK_S && paddle1Y < HEIGHT - PADDLE_HEIGHT) {
            paddle1Y += paddleSpeed;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && paddle2Y > 0) {
            paddle2Y -= paddleSpeed;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && paddle2Y < HEIGHT - PADDLE_HEIGHT) {
            paddle2Y += paddleSpeed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    //main
    public static void main(String[] args) {
        JFrame frame = new JFrame("Air Hockey");
        AirHockey game = new AirHockey();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}