package ui;

import model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
Represents the splash screen
 */
public class SplashScreen extends JPanel {

    /*
     * EFFECTS: Constructs a splash screen to be displayed before the game starts
     */
    public SplashScreen() {
        setBackground(new Color(30,30,30));
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/SplashScreen.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel background = new JLabel(new ImageIcon(img));
        add(background);

    }
}
