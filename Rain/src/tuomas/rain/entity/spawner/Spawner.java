package tuomas.rain.entity.spawner;

import java.util.ArrayList;
import java.util.List;

import tuomas.rain.entity.Entity;
import tuomas.rain.entity.particle.Particle;
import tuomas.rain.level.Level;

public class Spawner extends Entity{
	
	public enum Type{
		MOB, PARTICLE;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level){
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;

	}
}
