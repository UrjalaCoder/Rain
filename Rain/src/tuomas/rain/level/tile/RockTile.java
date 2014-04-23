package tuomas.rain.level.tile;

import tuomas.rain.graphics.Screen;
import tuomas.rain.graphics.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		// Render here -->
		screen.renderTile(x << 4, y << 4, sprite.rock);
	}
	
	public boolean solid() {
		return true;
	}

}
