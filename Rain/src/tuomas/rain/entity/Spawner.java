package tuomas.rain.entity;

import java.util.ArrayList;
import java.util.List;

public class Spawner extends Entity{
	private List<Entity> entities = new ArrayList<Entity>();
	
	public enum Type{
		MOB, PARTICLE;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount){
		this.x = x;
		this.y = y;
		
		for(int i = 0; i < amount; i++){
			
		}
	}
}
