package fr.xodevil_fire.SilverChunk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.xodevil_fire.SilverChunk.commands.ChunkCommand;
import fr.xodevil_fire.SilverChunk.listeners.WorldListener;
import fr.xodevil_fire.SilverChunk.types.Chunk;

public class Core extends JavaPlugin {
	
	public static ArrayList<Chunk> chunk = new ArrayList<Chunk>();
	public static ArrayList<org.bukkit.Chunk> forceUnload = new ArrayList<org.bukkit.Chunk>();
	public static String prefix = new String(ChatColor.GRAY + "[SilverChunk] ");
	public static String publicPrefix = new String(ChatColor.GRAY + "[Chunk] ");
	public static Boolean debug = new Boolean(false);
	public File chunksFile;
	public FileConfiguration chunks;
	
	public void onDisable() {
		saveChunks();
	}
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new WorldListener(this), this);
		this.getCommand("chunk").setExecutor(new ChunkCommand(this));
		
		chunksFile = new File(getDataFolder(), "chunks.yml");
		try {
	        firstRun();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		chunks = new YamlConfiguration();
		if (this.getChunks().getConfigurationSection("Chunks") == null) {
	    	this.getChunks().createSection("Chunks");
	    	saveChunks();
		}
	    loadChunks();
	}
	
	private void firstRun() {
		if(!chunksFile.exists()){
	        chunksFile.getParentFile().mkdirs();
	        copy(getResource("chunks.yml"), chunksFile);
	    }
	}
	
	private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void saveChunks() {
		Integer i = 0;
	    for (Chunk chunk : Core.chunk) {
	    	String path = "Chunks." + i.toString();
	    	if (this.getChunks().getConfigurationSection(path) == null) {
	    		this.getChunks().createSection(path); }
	    	this.getChunks().set(path + ".world", chunk.getWorld().getName());
	    	this.getChunks().set(path + ".x", chunk.getX());
	    	this.getChunks().set(path + ".z", chunk.getZ());
		    try {
		    	this.getChunks().save(chunksFile);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    i++;
	    }
	}
	
	public void loadChunks() {
		try {
			this.getChunks().load(chunksFile);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (this.getChunks().getConfigurationSection("Chunks") != null) {
			Set<String> allChunks = this.getChunks().getConfigurationSection("Chunks").getKeys(false);
	    	for (String i : allChunks) {
				Chunk chunk = new Chunk(Bukkit.getWorld(this.getChunks().getConfigurationSection("Chunks." + i).getString("world")), this.getChunks().getConfigurationSection("Chunks." + i).getInt("x"), this.getChunks().getConfigurationSection("Chunks." + i).getInt("z"));
				chunk.save();
	    	}
		}
	}
	
	public FileConfiguration getChunks() {
	    if (chunks == null) {
	        this.loadChunks();
	    }
	    return chunks;
	}
}
