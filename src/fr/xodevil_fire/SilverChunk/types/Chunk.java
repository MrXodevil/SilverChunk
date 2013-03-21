package fr.xodevil_fire.SilverChunk.types;

import org.bukkit.World;

import fr.xodevil_fire.SilverChunk.Core;

public class Chunk {
	
	private World world;
	private Integer x;
	private Integer z;
	
	public Chunk(World w, Integer x, Integer z) {
		this.x = x;
		this.z = z;
		this.world = w;
	}
	
	public void save() {
		Core.chunk.add(this);
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World w) {
		this.world = w;
	}
	
	public Integer getX() {
		return x;
	}
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public Integer getZ() {
		return z;
	}
	
	public void setZ(Integer z) {
		this.z = z;
	}

}
