package fr.xodevil_fire.SilverChunk.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.xodevil_fire.SilverChunk.Core;
import fr.xodevil_fire.SilverChunk.API.command.LightCommand;
import fr.xodevil_fire.SilverChunk.API.command.LightCommandExecutor;
import fr.xodevil_fire.SilverChunk.managers.Manager;
import fr.xodevil_fire.SilverChunk.types.Chunk;

public class ChunkCommand extends LightCommandExecutor {

	private Core plugin;
	public ChunkCommand(Core core) {
		this.plugin = core;
	}

	@Override
	public boolean onCommand(LightCommand cmd) {
		if (cmd.getArgs().length == 0) {
			cmd.sendPrefixedMessage(ChatColor.AQUA + "SilverChunk est un plugin codé par xodevil_fire, et est actuellement dans sa version 1.0.");
			return true;
		} else if (cmd.getArg(0).equalsIgnoreCase("save")) {
			if (cmd.isPlayer() && cmd.getArgs().length < 4) {
				if (cmd.getArgs().length == 1) {
					if (cmd.checkPermission("silverchunk.save", cmd)) {
						Player player = cmd.getPlayer();
						org.bukkit.Chunk c = cmd.getPlayer().getWorld().getChunkAt(player.getLocation());
						if (!Manager.hasChunk(c.getWorld(), c.getX(), c.getZ())) {
							Chunk chunk = new Chunk(c.getWorld(), c.getX(), c.getZ());
							chunk.save();
							plugin.saveChunks();
							cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de sauvegarder le chunk " + ChatColor.AQUA + c.getX() + ChatColor.GREEN + ":" + ChatColor.AQUA + c.getZ() + ChatColor.GREEN + " du monde " + ChatColor.AQUA + c.getWorld().getName() + ChatColor.GREEN + " avec succès !");
							return true;
						}
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "Le chunk que vous souhaitez sauvegarder existe déjà !");
						return true;
					}
					
				} else if (cmd.getArgs().length == 2) {
					if (cmd.checkPermission("silverchunk.save.radius", cmd)) {
						if (!Manager.isNumericInteger(cmd.getArg(1))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [RADIUS] doit obligatoirement être un entier.");
							return true;
						}
						Integer radius = Integer.parseInt(cmd.getArg(1)) * 16;
			            for (int x = (int) (cmd.getPlayer().getLocation().getX() - radius); x < (int) (cmd.getPlayer().getLocation().getX() + radius); x++) {
			                for (int y = (int) (cmd.getPlayer().getLocation().getY() - radius); y < (int) (cmd.getPlayer().getLocation().getY() + radius); y++) {
			                    for (int z = (int) (cmd.getPlayer().getLocation().getZ() - radius); z < (int) (cmd.getPlayer().getLocation().getZ() + radius); z++) {
			                    	if (!Manager.hasChunk(cmd.getPlayer().getLocation().getWorld(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ())) {
			                    		Chunk chunk = new Chunk(cmd.getPlayer().getLocation().getWorld(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ());
			                    		chunk.save();
			                    		plugin.saveChunks();
			                    	}
			                    }
			                }
			            }
			            cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de sauvegarder tous les chunks dans un rayon de " + cmd.getArg(1) + " avec succès !");
			            return true;
					}
					
				} else if (cmd.getArgs().length == 3) {
					if (cmd.checkPermission("silverchunk.save.other", cmd)) {
						Integer x = 0;
						Integer z = 0;
						if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
							return true;
						}
						if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
							return true;
						}
						if (cmd.getArg(1).startsWith("-")) {
							x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
						} else {
							x = Integer.parseInt(cmd.getArg(1));
						}
						if (cmd.getArg(2).startsWith("-")) {
							z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
						} else {
							z = Integer.parseInt(cmd.getArg(2));
						}
						if (!Manager.hasChunk(cmd.getPlayer().getWorld(), x, z)) {
							Chunk chunk = new Chunk(cmd.getPlayer().getWorld(), x, z);
							chunk.save();
							plugin.saveChunks();
							cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de sauvegarder le chunk " + ChatColor.AQUA + x + ChatColor.GREEN + ":" + ChatColor.AQUA + z + ChatColor.GREEN + " du monde " + ChatColor.AQUA + cmd.getPlayer().getWorld().getName() + ChatColor.GREEN + " avec succès !");
							return true;
						}
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "Le chunk que vous souhaitez sauvegarder existe déjà !");
						return true;
					}
				}
			} else if (cmd.getArgs().length == 4) {
				if (cmd.checkPermission("silverchunk.save.other", cmd)) {
					Integer x = 0;
					Integer z = 0;
					if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
						return true;
					}
					if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
						return true;
					}
					if (cmd.getArg(1).startsWith("-")) {
						x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
					} else {
						x = Integer.parseInt(cmd.getArg(1));
					}
					if (cmd.getArg(2).startsWith("-")) {
						z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
					} else {
						z = Integer.parseInt(cmd.getArg(2));
					}
					if (plugin.getServer().getWorld(cmd.getArg(3)) == null) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 4 [WORLD] n'est pas un monde valide !");
						return true;
					}
					if (!Manager.hasChunk(plugin.getServer().getWorld(cmd.getArg(3)), x, z)) {
						Chunk chunk = new Chunk(plugin.getServer().getWorld(cmd.getArg(3)), x, z);
						chunk.save();
						plugin.saveChunks();
						cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de sauvegarder le chunk " + ChatColor.AQUA + x + ChatColor.GREEN + ":" + ChatColor.AQUA + z + ChatColor.GREEN + " du monde " + ChatColor.AQUA + cmd.getArg(3) + ChatColor.GREEN + " avec succès !");
						return true;
					}
					cmd.sendPublicPrefixedMessage(ChatColor.RED + "Le chunk que vous souhaitez sauvegarder existe déjà !");
					return true;
				}
			} else if (!cmd.isPlayer()) {
				cmd.sendPrefixedMessage(ChatColor.RED + "Seuls les joueurs en jeu peuvent effecuter cette commande !");
				return true;
			} else {
				
			}
		} else if (cmd.getArg(0).equalsIgnoreCase("remove")) {
			if (cmd.isPlayer() && cmd.getArgs().length < 4) {
				if (cmd.getArgs().length == 1) {
					if (cmd.checkPermission("silverchunk.remove", cmd)) {
						Player player = cmd.getPlayer();
						org.bukkit.Chunk c = cmd.getPlayer().getWorld().getChunkAt(player.getLocation());
						if (Manager.hasChunk(c.getWorld(), c.getX(), c.getZ())) {
							if (plugin.getChunks().getConfigurationSection("Chunks") != null) {
								Set<String> allChunks = plugin.getChunks().getConfigurationSection("Chunks").getKeys(false);
						    	for (String i : allChunks) {
						    		ConfigurationSection cs = plugin.getChunks().getConfigurationSection("Chunks." + i);
						    		if (plugin.getServer().getWorld(cs.getString("world")).equals(c.getWorld()) && cs.getInt("x") == c.getX() && cs.getInt("z") == c.getZ()) {
						    			plugin.getChunks().set("Chunks." + i, null);
						    		}
						    	}
							}
							Core.chunk.remove(Manager.getChunk(c.getWorld(), c.getX(), c.getZ()));
							cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de retirer des chunks à sauvegarder le chunk " + ChatColor.AQUA + c.getX() + ChatColor.GREEN + ":" + ChatColor.AQUA + c.getZ() + ChatColor.GREEN + " du monde " + ChatColor.AQUA + c.getWorld().getName() + ChatColor.GREEN + " avec succès !");
							plugin.saveChunks();
							return true;
						}
						cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Le chunk que vous souhaitez retirer n'existe pas !");
						return true;
					}
				} else if (cmd.getArgs().length == 2) {
					if (cmd.checkPermission("silverchunk.remove.radius", cmd)) {
						if (!Manager.isNumericInteger(cmd.getArg(1))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [RADIUS] doit obligatoirement être un entier.");
							return true;
						}
						Integer radius = Integer.parseInt(cmd.getArg(1)) * 16;
			            for (int x = (int) (cmd.getPlayer().getLocation().getX() - radius); x < (int) (cmd.getPlayer().getLocation().getX() + radius); x++) {
			                for (int y = (int) (cmd.getPlayer().getLocation().getY() - radius); y < (int) (cmd.getPlayer().getLocation().getY() + radius); y++) {
			                    for (int z = (int) (cmd.getPlayer().getLocation().getZ() - radius); z < (int) (cmd.getPlayer().getLocation().getZ() + radius); z++) {
			                    	if (Manager.hasChunk(cmd.getPlayer().getLocation().getWorld(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ())) {
			                    		if (plugin.getChunks().getConfigurationSection("Chunks") != null) {
											Set<String> allChunks = plugin.getChunks().getConfigurationSection("Chunks").getKeys(false);
									    	for (String i : allChunks) {
									    		ConfigurationSection cs = plugin.getChunks().getConfigurationSection("Chunks." + i);
									    		if (plugin.getServer().getWorld(cs.getString("world")).equals(cmd.getPlayer().getLocation().getWorld()) && cs.getInt("x") == cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX() && cs.getInt("z") == cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ()) {
									    			plugin.getChunks().set("Chunks." + i, null);
									    			Core.chunk.remove(Manager.getChunk(cmd.getPlayer().getLocation().getWorld(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ()));
									    		}
									    	}
										}
										plugin.saveChunks();
			                    	}
			                    }
			                }
			            }
			            cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de retirer tous les chunks dans un rayon de " + cmd.getArg(1) + " avec succès !");
			            return true;
					}
					
				} else if (cmd.getArgs().length == 3) {
					if (cmd.checkPermission("silverchunk.remove.other", cmd)) {
						Integer x = 0;
						Integer z = 0;
						if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
							return true;
						}
						if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
							return true;
						}
						if (cmd.getArg(1).startsWith("-")) {
							x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
						} else {
							x = Integer.parseInt(cmd.getArg(1));
						}
						if (cmd.getArg(2).startsWith("-")) {
							z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
						} else {
							z = Integer.parseInt(cmd.getArg(2));
						}
						if (Manager.hasChunk(cmd.getPlayer().getWorld(), x, z)) {
							if (plugin.getChunks().getConfigurationSection("Chunks") != null) {
								Set<String> allChunks = plugin.getChunks().getConfigurationSection("Chunks").getKeys(false);
						    	for (String i : allChunks) {
						    		ConfigurationSection cs = plugin.getChunks().getConfigurationSection("Chunks." + i);
									if (plugin.getServer().getWorld(cs.getString("world")).equals(cmd.getPlayer().getWorld()) && cs.getInt("x") == x && cs.getInt("z") == z) {
								    	plugin.getChunks().set("Chunks." + i, null);
									}
						    	}
							}
							Core.chunk.remove(Manager.getChunk(cmd.getPlayer().getWorld(), x, z));
							cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de retirer des chunks à sauvegarder le chunk " + ChatColor.AQUA + x + ChatColor.GREEN + ":" + ChatColor.AQUA + z + ChatColor.GREEN + " du monde " + ChatColor.AQUA + cmd.getPlayer().getWorld().getName() + ChatColor.GREEN + " avec succès !");
							plugin.saveChunks();
							return true;
						}
						cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Le chunk que vous souhaitez retirer n'existe pas !");
						return true;
					}
				}
			} else if (cmd.getArgs().length == 4) {
				if (cmd.checkPermission("silverchunk.remove.other", cmd)) {
					Integer x = 0;
					Integer z = 0;
					if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
						return true;
					}
					if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
						return true;
					}
					if (cmd.getArg(1).startsWith("-")) {
						x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
					} else {
						x = Integer.parseInt(cmd.getArg(1));
					}
					if (cmd.getArg(2).startsWith("-")) {
						z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
					} else {
						z = Integer.parseInt(cmd.getArg(2));
					}
					if (plugin.getServer().getWorld(cmd.getArg(3)) == null) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 4 [WORLD] n'est pas un monde valide !");
						return true;
					}
					World w = plugin.getServer().getWorld(cmd.getArg(3));
					if (Manager.hasChunk(w, x, z)) {
						if (plugin.getChunks().getConfigurationSection("Chunks") != null) {
							Set<String> allChunks = plugin.getChunks().getConfigurationSection("Chunks").getKeys(false);
					    	for (String i : allChunks) {
					    		ConfigurationSection cs = plugin.getChunks().getConfigurationSection("Chunks." + i);
								if (plugin.getServer().getWorld(cs.getString("world")).equals(w) && cs.getInt("x") == x && cs.getInt("z") == z) {
							    	plugin.getChunks().set("Chunks." + i, null);
								}
					    	}
						}
						Core.chunk.remove(Manager.getChunk(w, x, z));
						cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de retirer des chunks à sauvegarder le chunk " + ChatColor.AQUA + x + ChatColor.GREEN + ":" + ChatColor.AQUA + z + ChatColor.GREEN + " du monde " + ChatColor.AQUA + cmd.getPlayer().getWorld().getName() + ChatColor.GREEN + " avec succès !");
						plugin.saveChunks();
						return true;
					}
					cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Le chunk que vous souhaitez retirer n'existe pas !");
					return true;
				}
			} else if (!cmd.isPlayer()) {
				cmd.sendPrefixedMessage(ChatColor.RED + "Seuls les joueurs en jeu peuvent effecuter cette commande !");
				return true;
			} else {
				
			}
		} else if (cmd.getArg(0).equalsIgnoreCase("info")) {
			if (cmd.isPlayer() && cmd.getArgs().length < 4) {
				if (cmd.getArgs().length == 1) {
					if (cmd.checkPermission("silverchunk.info", cmd)) {
						Player player = cmd.getPlayer();
						org.bukkit.Chunk c = cmd.getPlayer().getWorld().getChunkAt(player.getLocation());
						Integer nbFound = 0;
						String register = (Manager.hasChunk(c.getWorld(), c.getX(), c.getZ())) ? "Oui" : "Non";
						for (@SuppressWarnings("unused") Entity entity : c.getEntities()) {
							nbFound++;
						}
						cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Voici les informations concernant le chunk où vous vous trouvez actuellement :");
						cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Position : " + ChatColor.GRAY + " X = " + ChatColor.WHITE + c.getX() + ChatColor.GRAY + "; Z = " + ChatColor.WHITE + c.getZ() + ChatColor.GRAY + "; World = " + ChatColor.WHITE + c.getWorld().getName() + ChatColor.GRAY + ".");
						cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Entités : " + ChatColor.GRAY + nbFound + ".");
						cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Enregistré : " + ChatColor.GRAY + register + ".");
						return true;
					}
				} else if (cmd.getArgs().length == 3) {
					if (cmd.checkPermission("silverchunk.info.other", cmd)) {
						Integer x = 0;
						Integer z = 0;
						if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
							return true;
						}
						if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
							return true;
						}
						if (cmd.getArg(1).startsWith("-")) {
							x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
						} else {
							x = Integer.parseInt(cmd.getArg(1));
						}
						if (cmd.getArg(2).startsWith("-")) {
							z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
						} else {
							z = Integer.parseInt(cmd.getArg(2));
						}
						Integer nbFound = 0;
						String register = (Manager.hasChunk(cmd.getPlayer().getWorld(), x, z)) ? "Oui" : "Non";
						for (@SuppressWarnings("unused") Entity entity : cmd.getPlayer().getWorld().getChunkAt(x, z).getEntities()) {
							nbFound++;
						}
						cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Voici les informations concernant le chunk où vous vous trouvez actuellement :");
						cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Position : " + ChatColor.GRAY + " X = " + ChatColor.WHITE + x + ChatColor.GRAY + "; Z = " + ChatColor.WHITE + z + ChatColor.GRAY + "; World = " + ChatColor.WHITE + cmd.getPlayer().getWorld().getName() + ChatColor.GRAY + ".");
						cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Entités : " + ChatColor.GRAY + nbFound + ".");
						cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Enregistré : " + ChatColor.GRAY + register + ".");
						return true;
					}
				}
			} else if (cmd.getArgs().length == 4) {
				if (cmd.checkPermission("silverchunk.info.other", cmd)) {
					Integer x = 0;
					Integer z = 0;
					if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
						return true;
					}
					if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
						return true;
					}
					if (cmd.getArg(1).startsWith("-")) {
						x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
					} else {
						x = Integer.parseInt(cmd.getArg(1));
					}
					if (cmd.getArg(2).startsWith("-")) {
						z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
					} else {
						z = Integer.parseInt(cmd.getArg(2));
					}
					if (plugin.getServer().getWorld(cmd.getArg(3)) == null) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 4 [WORLD] n'est pas un monde valide !");
						return true;
					}
					World w = plugin.getServer().getWorld(cmd.getArg(3));
					Integer nbFound = 0;
					String register = (Manager.hasChunk(w, x, z)) ? "Oui" : "Non";
					for (@SuppressWarnings("unused") Entity entity : w.getChunkAt(x, z).getEntities()) {
						nbFound++;
					}
					cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Voici les informations concernant le chunk où vous vous trouvez actuellement :");
					cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Position : " + ChatColor.GRAY + " X = " + ChatColor.WHITE + x + ChatColor.GRAY + "; Z = " + ChatColor.WHITE + z + ChatColor.GRAY + "; World = " + ChatColor.WHITE + w.getName() + ChatColor.GRAY + ".");
					cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Entités : " + ChatColor.GRAY + nbFound + ".");
					cmd.getSender().sendMessage(ChatColor.GRAY + " - " + ChatColor.GOLD + "Enregistré : " + ChatColor.GRAY + register + ".");
					return true;
				}
			} else if (!cmd.isPlayer()) {
				cmd.sendPrefixedMessage(ChatColor.RED + "Seuls les joueurs en jeu peuvent effecuter cette commande !");
				return true;
			} else {
				
			}
		} else if (cmd.getArg(0).equalsIgnoreCase("load")) {
			if (cmd.isPlayer() && cmd.getArgs().length < 4) {
				if (cmd.getArgs().length == 1) {
					if (cmd.checkPermission("silverchunk.load", cmd)) {
						org.bukkit.Chunk c = cmd.getPlayer().getWorld().getChunkAt(cmd.getPlayer().getLocation());
						if (c.load()) {
							cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Vous venez de charger le chunk " + ChatColor.AQUA + c.getX() + ChatColor.GREEN + ":" + ChatColor.AQUA + c.getZ() + ChatColor.GREEN + " du monde " + ChatColor.AQUA + c.getWorld().getName() + ChatColor.GREEN + " avec succès !");
						}
						return true;
					}
				} else if (cmd.getArgs().length == 2) {
					if (cmd.checkPermission("silverchunk.load.radius", cmd)) {
						if (!Manager.isNumericInteger(cmd.getArg(1))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [RADIUS] doit obligatoirement être un entier.");
							return true;
						}
						Integer radius = Integer.parseInt(cmd.getArg(1)) * 16;
			            for (int x = (int) (cmd.getPlayer().getLocation().getX() - radius); x < (int) (cmd.getPlayer().getLocation().getX() + radius); x++) {
			                for (int y = (int) (cmd.getPlayer().getLocation().getY() - radius); y < (int) (cmd.getPlayer().getLocation().getY() + radius); y++) {
			                    for (int z = (int) (cmd.getPlayer().getLocation().getZ() - radius); z < (int) (cmd.getPlayer().getLocation().getZ() + radius); z++) {
			                    	if (!Manager.hasChunk(cmd.getPlayer().getLocation().getWorld(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ())) {
			                    		org.bukkit.Chunk c = cmd.getPlayer().getWorld().getChunkAt(cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ());
			        					c.load();
			                    	}
			                    }
			                }
			            }
			            cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de charger tous les chunks dans un rayon de " + cmd.getArg(1) + " avec succès !");
			            return true;
					}
					
				} else if (cmd.getArgs().length == 3) {
					if (cmd.checkPermission("silverchunk.load.other", cmd)) {
						Integer x = 0;
						Integer z = 0;
						if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
							return true;
						}
						if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
							return true;
						}
						if (cmd.getArg(1).startsWith("-")) {
							x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
						} else {
							x = Integer.parseInt(cmd.getArg(1));
						}
						if (cmd.getArg(2).startsWith("-")) {
							z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
						} else {
							z = Integer.parseInt(cmd.getArg(2));
						}
						World w = cmd.getPlayer().getWorld();
						if (w.getChunkAt(x, z).load()) {
							cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Vous venez de charger le chunk " + ChatColor.AQUA + x + ChatColor.GREEN + ":" + ChatColor.AQUA + z + ChatColor.GREEN + " du monde " + ChatColor.AQUA + cmd.getPlayer().getWorld().getName() + ChatColor.GREEN + " avec succès !");
						}
						return true;
					}
				}
			} else if (cmd.getArgs().length == 4) {
				if (cmd.checkPermission("silverchunk.load.other", cmd)) {
					Integer x = 0;
					Integer z = 0;
					if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
						return true;
					}
					if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
						return true;
					}
					if (cmd.getArg(1).startsWith("-")) {
						x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
					} else {
						x = Integer.parseInt(cmd.getArg(1));
					}
					if (cmd.getArg(2).startsWith("-")) {
						z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
					} else {
						z = Integer.parseInt(cmd.getArg(2));
					}
					if (plugin.getServer().getWorld(cmd.getArg(3)) == null) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 4 [WORLD] n'est pas un monde valide !");
						return true;
					}
					World w = plugin.getServer().getWorld(cmd.getArg(3));
					if (w.getChunkAt(x, z).load()) {
						cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Vous venez de charger le chunk " + ChatColor.AQUA + x + ChatColor.GREEN + ":" + ChatColor.AQUA + z + ChatColor.GREEN + " du monde " + ChatColor.AQUA + cmd.getPlayer().getWorld().getName() + ChatColor.GREEN + " avec succès !");
					}
					return true;
				}
			} else if (!cmd.isPlayer()) {
				cmd.sendPrefixedMessage(ChatColor.RED + "Seuls les joueurs en jeu peuvent effecuter cette commande !");
				return true;
			} else {
				
			}
		} else if (cmd.getArg(0).equalsIgnoreCase("unload")) {
			if (cmd.isPlayer() && cmd.getArgs().length < 4) {
				if (cmd.getArgs().length == 1) {
					if (cmd.checkPermission("silverchunk.unload", cmd)) {
						org.bukkit.Chunk c = cmd.getPlayer().getWorld().getChunkAt(cmd.getPlayer().getLocation());
						Core.forceUnload.add(c);
						if (c.unload(true)) {
							cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Vous venez de décharger le chunk " + ChatColor.AQUA + c.getX() + ChatColor.GREEN + ":" + ChatColor.AQUA + c.getZ() + ChatColor.GREEN + " du monde " + ChatColor.AQUA + c.getWorld().getName() + ChatColor.GREEN + " avec succès !");
						}
						return true;
					}
				} else if (cmd.getArgs().length == 2) {
					if (cmd.checkPermission("silverchunk.unload.radius", cmd)) {
						if (!Manager.isNumericInteger(cmd.getArg(1))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [RADIUS] doit obligatoirement être un entier.");
							return true;
						}
						Integer radius = Integer.parseInt(cmd.getArg(1)) * 16;
			            for (int x = (int) (cmd.getPlayer().getLocation().getX() - radius); x < (int) (cmd.getPlayer().getLocation().getX() + radius); x++) {
			                for (int y = (int) (cmd.getPlayer().getLocation().getY() - radius); y < (int) (cmd.getPlayer().getLocation().getY() + radius); y++) {
			                    for (int z = (int) (cmd.getPlayer().getLocation().getZ() - radius); z < (int) (cmd.getPlayer().getLocation().getZ() + radius); z++) {
			                    	if (!Manager.hasChunk(cmd.getPlayer().getLocation().getWorld(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ())) {
			                    		org.bukkit.Chunk c = cmd.getPlayer().getWorld().getChunkAt(cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getX(), cmd.getPlayer().getLocation().getWorld().getBlockAt(x,y,z).getChunk().getZ());
			                    		Core.forceUnload.add(c);
			                    		c.unload();
			                    	}
			                    }
			                }
			            }
			            cmd.sendPublicPrefixedMessage(ChatColor.GREEN + "Vous venez de décharger tous les chunks dans un rayon de " + cmd.getArg(1) + " avec succès !");
			            return true;
					}
					
				} else if (cmd.getArgs().length == 3) {
					if (cmd.checkPermission("silverchunk.unload.other", cmd)) {
						Integer x = 0;
						Integer z = 0;
						if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
							return true;
						}
						if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
							return true;
						}
						if (cmd.getArg(1).startsWith("-")) {
							x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
						} else {
							x = Integer.parseInt(cmd.getArg(1));
						}
						if (cmd.getArg(2).startsWith("-")) {
							z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
						} else {
							z = Integer.parseInt(cmd.getArg(2));
						}
						World w = cmd.getPlayer().getWorld();
						Core.forceUnload.add(w.getChunkAt(x, z));
						if (w.getChunkAt(x, z).unload(true)) {
							cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Vous venez de décharger le chunk " + ChatColor.AQUA + x + ChatColor.GREEN + ":" + ChatColor.AQUA + z + ChatColor.GREEN + " du monde " + ChatColor.AQUA + cmd.getPlayer().getWorld().getName() + ChatColor.GREEN + " avec succès !");
						}
						return true;
					}
				}
			} else if (cmd.getArgs().length == 4) {
				if (cmd.checkPermission("silverchunk.unload.other", cmd)) {
					Integer x = 0;
					Integer z = 0;
					if (!Manager.isNumericInteger(cmd.getArg(1).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 2 [LOCATION_X] doit obligatoirement être un entier.");
						return true;
					}
					if (!Manager.isNumericInteger(cmd.getArg(2).replaceAll("-", ""))) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 3 [LOCATION_Y] doit obligatoirement être un entier.");
						return true;
					}
					if (cmd.getArg(1).startsWith("-")) {
						x -= Integer.parseInt(cmd.getArg(1).replaceAll("-", ""));
					} else {
						x = Integer.parseInt(cmd.getArg(1));
					}
					if (cmd.getArg(2).startsWith("-")) {
						z -= Integer.parseInt(cmd.getArg(2).replaceAll("-", ""));
					} else {
						z = Integer.parseInt(cmd.getArg(2));
					}
					if (plugin.getServer().getWorld(cmd.getArg(3)) == null) {
						cmd.sendPublicPrefixedMessage(ChatColor.RED + "L'argument numéro 4 [WORLD] n'est pas un monde valide !");
						return true;
					}
					World w = plugin.getServer().getWorld(cmd.getArg(3));
					Core.forceUnload.add(w.getChunkAt(x, z));
					if (w.getChunkAt(x, z).unload(true)) {
						cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Vous venez de décharger le chunk " + ChatColor.AQUA + x + ChatColor.GREEN + ":" + ChatColor.AQUA + z + ChatColor.GREEN + " du monde " + ChatColor.AQUA + cmd.getPlayer().getWorld().getName() + ChatColor.GREEN + " avec succès !");
					}
					return true;
				}
			} else if (!cmd.isPlayer()) {
				cmd.sendPrefixedMessage(ChatColor.RED + "Seuls les joueurs en jeu peuvent effecuter cette commande !");
				return true;
			} else {
				
			}
		} else if (cmd.getArg(0).equalsIgnoreCase("debug")) {
			if (cmd.getArgs().length == 1) {
				if (cmd.checkPermission("silverchunk.debug", cmd)) {
					if (!Core.debug) {
						Core.debug = true;
						cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Vous venez d'activer le mode de débogage avec succès !");
					} else if (Core.debug) {
						Core.debug = false;
						cmd.sendPublicPrefixedMessage(ChatColor.AQUA + "Vous venez de désactiver le mode de débogage avec succès !");
					}
					return true;
				}
			}
		} else if (cmd.getArg(0).equalsIgnoreCase("list")) {
			if (cmd.getArgs().length == 1) {
				if (cmd.checkPermission("silverchunk.list", cmd)) {
					Integer send = 0;
					for (World world : plugin.getServer().getWorlds()) {
						Integer nbFound = 0;
						StringBuffer sb = new StringBuffer(ChatColor.GRAY + " - " + ChatColor.AQUA + world.getName() + ": ");
						for (Chunk chunk : Core.chunk) {
							if (chunk.getWorld().equals(world)) {
								sb.append(ChatColor.GREEN.toString() + chunk.getX() + ":" + chunk.getZ() + ChatColor.GRAY + ", ");
								nbFound++;
							}
						}
						if (nbFound > 0) {
							if (!(send > 0)) {
								cmd.sendPublicPrefixedMessage(ChatColor.DARK_AQUA + "Voici la liste des chunks actuellement sauvegardés, classés par monde : " + ChatColor.DARK_GRAY + "{" + ChatColor.DARK_GREEN + "Légende: " + ChatColor.GRAY + "Position_X:Position_Z" + ChatColor.DARK_GRAY + "}");
							}
							cmd.getSender().sendMessage(sb.substring(0, sb.length() - 2).toString() + ".");
						} else {
							cmd.sendPublicPrefixedMessage(ChatColor.RED + "Aucun chunk n'a actuellement été sauvegardé !");
						}
						send = 1;
					}
					return true;
				}
			}
		} else if (cmd.getArg(0).equalsIgnoreCase("help")) {
			if (cmd.getArgs().length == 1) {
				if (cmd.checkPermission("silverchunk.help", cmd)) {
					String[] help = new String[15];
					help[0] = ChatColor.AQUA + "---------" + ChatColor.WHITE + " Aide : SilverChunk " + ChatColor.AQUA + "---------------------";
					help[1] = ChatColor.GRAY + "Voici ci-dessous la liste des arguments de la commande /chunk :";
					help[2] = ChatColor.GREEN + "/chunk: " + ChatColor.WHITE + "Afficher les informations concernant le plugin.";
					help[3] = ChatColor.GREEN + "/chunk help: " + ChatColor.WHITE + "Afficher l'aide (Cette page).";
					help[4] = ChatColor.GREEN + "/chunk debug: " + ChatColor.WHITE + "Activer le mode de débogage.";
					help[5] = ChatColor.GREEN + "/chunk list: " + ChatColor.WHITE + "Lister des chunks à sauvegarder.";
					help[6] = ChatColor.GREEN + "/chunk info [x] [y] [Monde]: " + ChatColor.WHITE + "Obtenir des informations sur un chunk.";
					help[7] = ChatColor.GREEN + "/chunk save [x] [y] [Monde]: " + ChatColor.WHITE + "Sauvegarder un chunk.";
					help[8] = ChatColor.GREEN + "/chunk save [Rayon]: " + ChatColor.WHITE + "Sauvegarder les chunks d'un rayon.";
					help[9] = ChatColor.GREEN + "/chunk remove [x] [y] [Monde]: " + ChatColor.WHITE + "Ne plus sauvegarder un chunk.";
					help[10] = ChatColor.GREEN + "/chunk remove [Rayon]: " + ChatColor.WHITE + "Ne plus sauvegarder les chunks d'un rayon.";
					help[11] = ChatColor.GREEN + "/chunk load [x] [y] [Monde]: " + ChatColor.WHITE + "Charger un chunk.";
					help[12] = ChatColor.GREEN + "/chunk load [Rayon]: " + ChatColor.WHITE + "Charger les chunks d'un rayon.";
					help[13] = ChatColor.GREEN + "/chunk unload [x] [y] [Monde]: " + ChatColor.WHITE + "Décharger un chunk.";
					help[14] = ChatColor.GREEN + "/chunk unload [Rayon]: " + ChatColor.WHITE + "Décharger les chunks d'un rayon.";
					for (String l : help) {
						cmd.getSender().sendMessage(l);
					}
					return true;
				}
			}
		}
		cmd.sendPublicPrefixedMessage(ChatColor.RED + "Un des arguments est invalide. Afin de consulter la liste des arguments valide, tapez /chunk help.");
		return true;
	}

}
