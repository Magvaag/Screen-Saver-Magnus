package net.vaagen.screensaver;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * Created by Magnus on 04/10/2014.
 */
public class Word {

    public BufferedImage image;
    public float speed;
    public float x;
    public float y;
    public int size;

    // Text, color, speed <=> rotation?
    public Word(String text, Color color, float rotation, float speed, int x, int y, int size){
        //this.text = text;
        //this.color = color;
        //this.rotation = rotation;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.size = size;

        image = new BufferedImage(500, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(color);
        g2d.rotate(rotation, 0, 0);
        g2d.setFont(new Font("font", Font.BOLD, size));
        g2d.drawString(text, 100, g2d.getFontMetrics().getHeight()+50);
        g2d.rotate(-rotation, 0, 0);
        g2d.dispose();
    }

}
