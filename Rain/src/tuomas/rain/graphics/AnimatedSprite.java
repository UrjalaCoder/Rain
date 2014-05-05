package tuomas.rain.graphics;

public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private int rate = 5;
	private int time = 0;
	private int length = -1;

	private Sprite sprite;

	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.getSprites()[0];
		if (length > sheet.getSprites().length) {
			System.err.println("[ERROR] Length of animation is too short" + this);
		}

		System.out.println("Frame: " + frame + " Sprite " + sprite);
	}

	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= length - 1) {
				frame = 0;
			} else {
				frame++;
			}

			sprite = sheet.getSprites()[frame];
		}

	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int frames) {
		rate = frames;
	}

}