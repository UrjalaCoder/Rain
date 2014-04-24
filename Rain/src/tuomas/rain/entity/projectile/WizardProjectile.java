package tuomas.rain.entity.projectile;

import tuomas.rain.entity.particle.Particle;
import tuomas.rain.graphics.Screen;
import tuomas.rain.graphics.Sprite;

public class WizardProjectile extends Projectile {

	public static final int FIRE_RATE = 50; // Korkeampi = hitammin ampuu!

	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);

		range = 25 * 16;
		speed = 4;
		damage = 20;

		sprite = Sprite.projectile_wizard;

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);

	}

	public void update() {
		if (level.tileCollision(x, y, nx, ny, 9)) {
			Particle p = new Particle((int) x, (int) y, 50);
			level.add(p);
			remove();
		}
		move();
	}

	protected void move() {

		x += nx;
		y += ny;
		if (distance() > range)
			remove();

	}

	private double distance() {
		double dist = 0;

		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y)
				* (yOrigin - y)));

		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - 12, (int) y - 2, this);
	}

}
