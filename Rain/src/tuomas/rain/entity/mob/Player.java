package tuomas.rain.entity.mob;

import tuomas.rain.Game;
import tuomas.rain.entity.projectile.Projectile;
import tuomas.rain.entity.projectile.WizardProjectile;
import tuomas.rain.graphics.AnimatedSprite;
import tuomas.rain.graphics.Screen;
import tuomas.rain.graphics.Sprite;
import tuomas.rain.graphics.SpriteSheet;
import tuomas.rain.input.Keyboard;
import tuomas.rain.input.Mouse;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_mage_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_mage_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_mage_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_mage_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private int fireRate = 0;
	Projectile p;

	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player_forward;
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_forward;
		fireRate = WizardProjectile.FIRE_RATE;
	}

	public void update() {
		animSprite.update();

		if (fireRate > 0)
			fireRate--;

		int xa = 0, ya = 0;

		if (anim < 7500)
			anim++;
		else
			anim = 0;

		if (input.up) {
			animSprite = up;
			ya--;
		}
		if (input.down) {
			animSprite = down;
			ya++;
		}

		if (input.left) {
			animSprite = left;
			xa--;
		}

		if (input.right) {
			animSprite = right;
			xa++;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		clear();
		updateShooting();

	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved())
				level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {

		if (Mouse.getButton() == 1 && fireRate <= 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);

			shoot(x, y, dir);

			fireRate = WizardProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {
		int flip = 0;

		if (dir == 0) {
			sprite = Sprite.player_forward;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_forward_1;
				} else {
					sprite = Sprite.player_forward_2;
				}
			}
		}

		if (dir == 1) {
			sprite = Sprite.player_side;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_side_1;
				} else {
					sprite = Sprite.player_side_2;
				}
			}
		}
		if (dir == 2) {
			sprite = Sprite.player_back;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_back_1;
				} else {
					sprite = Sprite.player_back_2;
				}
			}
		}

		if (dir == 3) {
			sprite = Sprite.player_side;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_side_1;
				} else {
					sprite = Sprite.player_side_2;

				}
			}
			flip = 1;
		}
		//sprite = animSprite.getSprite();
		screen.renderPlayer(x - 16, y - 16, sprite, flip);
	}

}
