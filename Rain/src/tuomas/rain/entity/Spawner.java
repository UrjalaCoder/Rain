package tuomas.rain.entity;

import java.util.ArrayList;
import java.util.List;

import tuomas.rain.entity.particle.Particle;

public class Spawner extends Entity{
	private List<Entity> entities = new ArrayList<Entity>();
	
	public enum Type{
		MOB, PARTICLE;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount){
		this.x = x;
		this.y = y;
		this.type = type;
		
		for(int i = 0; i < amount; i++){
			if (type == Type.PARTICLE){
				level.add(new Particle (x, y, 50));
			}
		}
	}
}
