package tuomas.rain.level.tile;

import tuomas.rain.graphics.Screen;
import tuomas.rain.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		// Render here -->
		screen.renderTile(x << 4, y << 4, sprite.voidSprite);
	}
}
