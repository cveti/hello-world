package game;

        import game.entities.Bullet;
        import game.entities.Controller;
        import game.entities.Player;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.KeyEvent;
        import java.awt.event.KeyListener;
        import java.awt.event.MouseEvent;
        import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {

    private Controller c;
    private Player p;

    private enum STATE{
        MENU,
        GAME
    }

    private STATE States = STATE.MENU;

    public InputHandler (Canvas canvas, Controller c, Player p) {
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        this.c = c;
        this.p = p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

        //public Rectangle playButton = new Rectangle(500, 150, 100, 100);
        //public Rectangle quitButton = new Rectangle(500, 350, 100, 100);

        //Play Button
        if(mx >= 650 && mx <= 850){
            if(my >= 150 && my <= 250){
                //Pressed Play Button
                Game.States = Game.STATE.GAME;

            }
        }
        //Quit Button
        if(mx >= 650 && mx <= 850){
            if(my >= 300 && my <= 400){
                //Pressed Exit Button
                System.exit(1);

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (States == STATE.GAME) {
            if (code == KeyEvent.VK_RIGHT) {
                Player.isMovingRight = true;
                Player.isMovingLeft = false;
            } else if (code == KeyEvent.VK_LEFT) {
                Player.isMovingRight = false;
                Player.isMovingLeft = true;
            } else if (code == KeyEvent.VK_UP) {
                Player.isMovingUp = true;
                Player.isMovingDown = false;
            } else if (code == KeyEvent.VK_DOWN) {
                Player.isMovingUp = false;
                Player.isMovingDown = true;
            } else if (code == KeyEvent.VK_SPACE) {
                c.addEntity(new Bullet(p.getX() + 65, p.getY() - 35, c.getGame(), c));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_RIGHT) {
            Player.isMovingRight = false;
            Player.isMovingLeft = false;
        } else if (code == KeyEvent.VK_LEFT) {
            Player.isMovingRight = false;
            Player.isMovingLeft = false;
        } else if (code == KeyEvent.VK_UP) {
            Player.isMovingUp = false;
            Player.isMovingDown = false;
        } else if (code == KeyEvent.VK_DOWN) {
            Player.isMovingUp = false;
            Player.isMovingDown = false;
        }
    }
}
