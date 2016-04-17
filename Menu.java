package game;


import java.awt.*;
import java.awt.Graphics;

public class Menu {

    public Rectangle playButton = new Rectangle(500, 150, 100, 100);
    //public Rectangle helpButton = new Rectangle(500, 250, 100, 100);
    public Rectangle quitButton = new Rectangle(500, 350, 100, 100);

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("Space Ship", 500, 100);

        g2d.draw(playButton);
        //g2d.draw(helpButton);
        g2d.draw(quitButton);
    }


}
