package net.vaagen.screensaver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Magnus on 04/10/2014.
 */
public class Screen extends JPanel implements Runnable {

    public static boolean RUNNING;
    public Thread thread;

    public List<Word> wordList = new CopyOnWriteArrayList<Word>();

    public int FPS;
    public int UPS;

    public Screen(){
        RUNNING = true;

        thread = new Thread(this);
        thread.start();
    }

    Font font = new Font("newFont", Font.BOLD, 15);
    public void paintComponent(Graphics g){
        g.clearRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

        for(Word word : wordList){
            g.drawImage(word.image, (int)word.x, (int)word.y, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(font);

        // This is only needed for debugging
        //g.drawString("UPS:" + UPS, 0, 15);
        //g.drawString("FPS:" + FPS, 0, 30);
        //g.drawString("Words:" + wordList.size(), 0, 60);
    }

    Random random = new Random();
    public void update(){
        for(int i = 0; i < 3; i++){
            wordList.add(new Word(References.TEXT, References.COLOR.getRandomColor(), (float)Math.toRadians(random.nextInt(31)-15), 0, random.nextInt(Main.SCREEN_WIDTH+500)-500, -100, 35+random.nextInt(50)));
        }

        for(Word word : wordList){
            word.y+=word.speed;
            word.speed+=0.2F;

            if(word.y > Main.SCREEN_HEIGHT)
                wordList.remove(word);
        }
    }

    long MAX_FRAMESKIP = 5;
    public void run(){
        // The wait time in milliseconds before next update/render
        long GAME_SKIP_TICKS = 1000 / References.UPDATES_PER_SECOND;
        long FRAME_SKIP_TICKS = 1000 / References.FRAMES_PER_SECOND;

        // Schedules the updates and repaints
        long next_game_tick = System.currentTimeMillis();
        long next_frame_tick = System.currentTimeMillis();
        long time = System.currentTimeMillis();

        // Counts the amount of time the game updates without rendering
        int loops;

        // Variables to update the UPS & FPS variables
        int frames = 0;
        int updates = 0;

        while(RUNNING){
            loops = 0;
            while(System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP){
                update();

                next_game_tick += GAME_SKIP_TICKS;
                updates++;
                loops++;
            }

            if(System.currentTimeMillis() > next_frame_tick){
                next_frame_tick += FRAME_SKIP_TICKS;
                repaint();
                frames++;
            }

            if(time+1000 <= System.currentTimeMillis()){
                time+=1000;

                FPS = frames;
                UPS = updates;
                updates = 0;
                frames = 0;
            }
        }
    }

}
