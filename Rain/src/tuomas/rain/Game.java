package tuomas.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import tuomas.rain.entity.mob.Player;
import tuomas.rain.graphics.Screen;
import tuomas.rain.graphics.SpriteSheet;
import tuomas.rain.input.Keyboard;
import tuomas.rain.input.Mouse;
import tuomas.rain.level.Level;
import tuomas.rain.level.TileCoordinate;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// Resolution variables -->
	private static int width = 480;
	private static int height = width / 16 * 9;
	private static int scale = 3;
	// --------------

	private Thread thread;
	private JFrame frame;
	private String NAME = "Codename Rain";
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;

	private Screen screen;

	// Image variables -->
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);

		frame = new JFrame();

		key = new Keyboard();

		level = Level.spawn;

		TileCoordinate playerSpawn = new TileCoordinate(19, 62);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		player.init(level);
		
		
		Mouse mouse = new Mouse();
		
		
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addKeyListener(key);
	}
	
	public static int getWindowWidth(){
		return width * scale;
	}
	
	
	public static int getWindowHeight(){
		return height * scale;
	}


	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();

	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void run() {

		requestFocus();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;

		int fps = 0;
		int ups = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				update();
				ups++;
				delta--;

			}

			render();
			fps++;

			if ((System.currentTimeMillis() - timer) > 1000) {
				timer += 1000;
				String updateInfo = "FPS: " + fps + " UPS: " + ups + ".";
				frame.setTitle(NAME + "  |  " + updateInfo);
				fps = 0;
				ups = 0;
			}
		}

		stop();
	}

	public void update() {
		key.update();
		player.update();
		level.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		int xScroll = player.x - screen.width / 2;
		int yScroll = player.y - screen.height / 2;
		level.render(xScroll, yScroll, screen);
		player.render(screen);
		screen.renderSheet(40, 40, SpriteSheet.player_mage_down, false);
		
		/*Sprite sprite = new Sprite(2, 2, 0xffffff);
		
		Random random = new Random();
		for (int i = 0; i< 100; i++){
		 	int x = random.nextInt(30);
			int y = random.nextInt(30);
			screen.renderSprite(width - 60 + x, 50 + y, sprite, true);
		}*/
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		// --------------->

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(new Color(0xFFFF00EE));
		g.setFont(new Font("Verdana", 0, 30));
		
		// Debug työkaluja -->
		
		//g.fillRect(Mouse.getX() - 8, Mouse.getY() - 8, 16, 16);
		//if(Mouse.getButton() != -1) g.drawString("Button: " + Mouse.getButton(), 80, 80);
		//g.drawString("AAAAMouseX: " + Mouse.getX() + " MouseY: " +Mouse.getY(), 80, 120);
		//AAAA
		
		g.dispose();
		bs.show();
	}

	public static void main(String args[]) {
		Game game = new Game();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setResizable(false);
		game.frame.setTitle(game.NAME);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.requestFocus();
		game.frame.setVisible(true);
		
		BufferedImage logo = null;
		
		try {
			logo = ImageIO.read(SpriteSheet.class.getResource("/textures/sheets/Knight_game_icon1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		game.frame.setIconImage(logo);

		game.start();
	}
}
