package fr.xodevil_fire.SilverChunk.listeners;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import fr.xodevil_fire.SilverChunk.Core;
import fr.xodevil_fire.SilverChunk.managers.Manager;

public class WorldListener implements Listener {
	
	private Core plugin;
	public WorldListener(Core core) {
		this.plugin = core;
	}

	public void onChunkUnload(ChunkUnloadEvent event){
		Chunk c = event.getChunk();
		Integer x = c.getX();
		Integer z = c.getZ();
		World w = c.getWorld();
		if(Manager.hasChunk(w, x, z) && !Core.forceUnload.contains(c)){
			event.setCancelled(true);
			if (Core.debug) {
				plugin.getLogger().info("Le chunk a la position" + x + ":" + z + " vient d'etre mis a jour.");
			}
		} else if (Core.forceUnload.contains(c)) {
			event.setCancelled(false);
			Core.forceUnload.remove(c);
		}
	}

}
