package tuomas.rain.level.tile.spawn_level;

import tuomas.rain.graphics.Screen;
import tuomas.rain.graphics.Sprite;
import tuomas.rain.level.tile.Tile;

public class SpawnWaterTile extends Tile {
	public SpawnWaterTile(Sprite sprite) {
		super(sprite);
	}
	public void render(int x, int y, Screen screen) {
		// Render here -->
		screen.renderTile(x << 4, y << 4, sprite.spawn_water);
	}

}
