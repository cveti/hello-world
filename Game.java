package game;

import display.Display;
import game.entities.*;
import gfx.Assets;
import gfx.ImageLoader;
import gfx.SpriteSheet;

import java.awt.*;
import java.awt.Menu;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageConsumer;
import java.util.LinkedList;

import static gfx.Assets.background;
import static gfx.Assets.enemy;

/**
 * za da si naprawim nishka na koqto da ni wurvi igrata
 * pishem - implements Runnable
 */

public class Game implements Runnable {
    private String name;
    private int width;
    private int height;
    public Rectangle playButton = new Rectangle(650, 150, 200, 100);
    //public Rectangle helpButton = new Rectangle(650, 300, 200, 100);
    public Rectangle quitButton = new Rectangle(650, 300, 200, 100);

    private Thread thread;
    private boolean isRunning;

    private Display display;
    // BufferStrategy - nachina po koito nie kontrolilrame vizualiziraneto na obektite
    // na nashiq canvas
    private BufferStrategy bs;
    // Graphics - towa koeto gi izrisuva na nashiq canvas
    private Graphics g;
    private InputHandler ih;

    private int enemyCount = 10;
    private int enemyKilled = 0;
    // testing
    private Player monster;
    private Controller c;
    private Enemy enemy;
    private Menu menu;
    private Graphics2D g2d;

    public LinkedList<EntityA> ea;
    public LinkedList<EntityB> eb;
    // end

    public Game(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public static enum STATE{
        MENU,
        GAME
    };

    public static STATE States = STATE.MENU;

    /**
     * metod v koito si inicializiram
     */
    public void init() {
        Assets.init();
        this.display = new Display(this.name, this.width, this.height);
        monster = new Player(700, 700, 100, this);
        c = new Controller(this);
        menu = new Menu();
        enemy = new Enemy(500, 500, this, c);
        c.createEnemys(enemyCount);
        this.ih = new InputHandler(this.display.getCanvas(), c, monster);
        ea = c.getEntityA();
        eb = c.getEntityB();


    }

    /**
     * tick obnowqwa sustoqnieto na igrata na vseki tick
     * 1 sec = 1_000_000_000ns
     * render - printira sustoqnieto na igrata na wseki tick
     */
    public void tick() {
        if (States == STATE.GAME) {
            this.monster.tick();
            this.c.tick();
        }
        if (enemyKilled >= enemyCount) {
            enemyCount += 2;
            enemyKilled = 0;
            c.createEnemys(enemyCount);
        }
    }

    public void render() {
        /**
         * purvo suzdawame buferstrategiqta
         * i posle po neq grafik obekta
         */

        // canvasa ima nqkakwa strategiq za buferirane i q iniciali
        //ziram na bs
        this.bs = this.display.getCanvas().getBufferStrategy();

        // ako nqma bs q szdavam
        if (this.bs == null) {
            // (2) imame dva bufera s koito mojem da rabotim
            this.display.getCanvas().createBufferStrategy(2);
            // da vzemem strategiqta koqto toku shto suzdadohme
            this.bs = this.display.getCanvas().getBufferStrategy();
        }

        // grafikata se vzimame ot buferstrategiqta
        this.g = this.bs.getDrawGraphics();
        // izchistwame freima predi da rendnem
        this.g.clearRect(0, 0, this.width, this.height);
//        // zapulwame kwadrat
//        this.g.setColor(Color.BLUE);
//        this.g.fillRect(this.x, 200, 100, 50);
//
//        this.g.setColor(Color.green);
//        this.g.drawRect(100, 100, 100, 100);
//
//        //this.g.setFont(Font.getFont("consolas")); - ne stawa- da go vidq kak
//        this.g.drawString("Hello", 50, 50);
//
//        // izrisuvane na kartinki
//        this.g.drawImage()

        // start drawing

        this.g.drawImage(background, 0, 0, 1600, 900, null);
        if (States == STATE.GAME) {
            this.monster.render(this.g);
            this.c.render(this.g);
        }else if (States == STATE.MENU){
            Graphics2D g2d = (Graphics2D) g;
            Font fnt0 = new Font("arial", Font.BOLD, 100);
            g.setFont(fnt0);
            g.setColor(Color.WHITE);
            g.drawString("Space Ship", 500, 100);

            Font fnt1 = new Font("arial", Font.BOLD, 70);
            g.setFont(fnt1);
            g.drawString("Play", 670, 220);
            g2d.draw(playButton);
            g.drawString("Quit", 670, 375);
            g2d.draw(quitButton);
        }
        // end drawing
        // kazvame na nashiq canvas da vizualizira informaciqta
        this.g.dispose();
        this.bs.show();
    }

    /**
     * za da moje klasa Game da nasledi interfeisa Runnable
     * zaduljitelno se pishe metoda run, zashtoto wsichki ot runable
     * imat metod run
     * Tozo metod se puska avtomatichno kogato pusna nova nishka
     * run metoda e main metoda na vsichko koeto e runnable
     */
    // kogato startna igrata ot start() shte se suzdade nova nishka
    // koqto avtomatichno shte pusne run metoda
    @Override
    public void run() {
        // iskame da inicializirame samo kogato trugne igrata
        this.init();
        // moje da e s true no taka nqmame kontrol nad cikula
        // po dobre e edna promenliva koeto da setvame na true
        // kogato startnem i na false kogato sprem igrata

        int fps = 60;
        double timePerTick = 1_000_000_000 / fps;

        double delta = 0;
        long now;
        long lastTimeTicked = System.nanoTime();

        while (isRunning) {
            now = System.nanoTime();

            delta += (now - lastTimeTicked) / timePerTick;
            lastTimeTicked = now;

            if (delta >= 1) {
                // na vsqka iteraciq nishkata spira swoqta rabota za
                // edi kolko si ms
                // slaga se 1 ms za optimizaciq na procesora pri
                // presmqtaneto na wremet oza zabavqne
            try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tick();
                render();
                delta--;
            }
        }

        // v kraq na run metoda vikame stop() za da sprem nishkata
        this.stop();
    }

    /**za da pusna nova nishka pisha metoda start
     * za da spra nishkata - za da q wurna/sleq w
     * purvonachalnata nishka, ot koqto sme q pusnali - pisha metoda stoop
     * towa mi puska i spira igra
     */

    /**
     * synchronized - sinhronizira dvete nishki pri puskane i spirane
     * toest ednata izchakwa drugata da se pusne i sled towa q izchakwa
     * da se joine
     * ako wsichki metodi w dvete nishki ni sa sinhronizirani nqma
     * smisul ot dve nishki, zashtoto te postoqnno shte se izchakwat
     */
    public synchronized void start() {
        this.isRunning = true;
        // iskame nova nishka koqto da raboti vurhu klasa Game
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        this.isRunning = false;
        // ne q prekusvam, a q slivam kum predishnata
        try {
            thread.join(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getEnemyKilled() {
        return enemyKilled;
    }
    public void setEnemyKilled(int enemyKilled) {
        this.enemyKilled = enemyKilled;
    }
    public int getEnemyCount() {
        return enemyCount;
    }
    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public STATE getState() {
        return States;
    }
}
