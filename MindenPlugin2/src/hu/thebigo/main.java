package hu.thebigo;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;



public class main extends JavaPlugin implements Listener, CommandExecutor{

	HashMap<Player,Entity> target = new HashMap<Player, Entity>();
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	/*
	private void getKozeliBlockok(Location loc) {
		Location temploc = loc;
		temploc.add(1, 0, 0).getBlock().getType() == Material.leave
	}
	
	public void BrekoBrekoGyerekekittNosika(BlockBreakEvent e) {
		if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getAmount() > 0) {
			if(e.getPlayer().getInventory().getItemInMainHand() == new ItemStack(Material.DIAMOND_AXE)) {
				Location loc = e.getBlock().getLocation();
			}
		}
	} 
	*/
	
	@EventHandler
	public void onPlayerAttak(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player user = (Player) event.getDamager();
			if(user.getItemInHand() != null) {
				if(user.getItemInHand().getType() != Material.AIR) {
					if(user.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c-- §6MOBBOT §c--")) {
						if(target.get(user) != null) {
							event.setCancelled(true);
							event.getEntity().setPassenger(target.get(user));
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	  public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
			Player user = event.getPlayer();
			if(user.getItemInHand() != null) {
				if(user.getItemInHand().getType() != Material.AIR) {
					if(user.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c-- §6MOBBOT §c--")) {
						target.put(event.getPlayer(), event.getRightClicked());
						user.sendMessage("§aMob Kiválasztva!");
					}
				}
			}
	  }
	
	@EventHandler
	public void onPlayerClicks(PlayerInteractEvent event) {
	if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		if(event.getPlayer().getItemInHand() != null) {
			if(event.getPlayer().getItemInHand().getType() != Material.AIR) {
				if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c-- §6SZUPER KAJA§c--")){
					ItemStack newitem = event.getPlayer().getItemInHand();
					newitem.setAmount(newitem.getAmount()-1);
					event.getPlayer().setItemInHand(newitem);
					event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
					event.getPlayer().setFoodLevel(20);
				}
				}
			}
		}
	}
	
	@EventHandler
	public void Pdeath(PlayerDeathEvent e) {
		ItemStack bot = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = bot.getItemMeta();
		meta.setDisplayName("§c-- §6Mentőrúd§c--");
		bot.setItemMeta(meta);
		for(ItemStack stack : e.getEntity().getInventory().getContents()) {
			if(stack.getItemMeta().getDisplayName().equalsIgnoreCase("§c-- §6Mentőrúd§c--")) {
				e.getDrops().clear();
				e.setKeepInventory(true);
				stack.setAmount(stack.getAmount()-1);
			}
		}
	}
	
	@Override
	 public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("mentorud")) {
			ItemStack bot = new ItemStack(Material.BLAZE_ROD,1);
			ItemMeta meta = bot.getItemMeta();
			meta.setDisplayName("§c-- §6Mentőrúd§c--");
			bot.setItemMeta(meta);
			((Player) sender).getInventory().addItem(bot);
			sender.sendMessage("§aOtt a trinyóóóóó!");
		}
		if(label.equalsIgnoreCase("szuperkaja")) {
			ItemStack bot = new ItemStack(Material.BEETROOT_SOUP,1);
			ItemMeta meta = bot.getItemMeta();
			meta.setDisplayName("§c-- §6SZUPER KAJA§c--");
			bot.setItemMeta(meta);
			((Player) sender).getInventory().addItem(bot);
			sender.sendMessage("§aOtt a trinyóóóóó!");
		}
		if(label.equalsIgnoreCase("mobbot")) {
			ItemStack bot = new ItemStack(Material.STICK,1);
			ItemMeta meta = bot.getItemMeta();
			meta.setDisplayName("§c-- §6MOBBOT §c--");
			bot.setItemMeta(meta);
			((Player) sender).getInventory().addItem(bot);
			sender.sendMessage("§aOtt a trinyóóóóó!");
		}
		if(label.equalsIgnoreCase("troll")) {
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("tnt")) {
					Player target = Bukkit.getPlayer(args[1]);
					target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
					sender.sendMessage("§cMegidézve.. :O "+target.getDisplayName());
				}else if(args[0].equalsIgnoreCase("lava")) {
					Player target = Bukkit.getPlayer(args[1]);
					Location loc = target.getLocation();
					int x = -1;
					int z = -1;
					for(int i = 0;i<9; i++) {
						if(i%3 == 0) {
							x++;
							z=-1;
						}else {
						z++;
						}
					loc.setX(target.getLocation().getBlockX()+x);
					loc.setZ(target.getLocation().getBlockZ()+z);
					loc.getBlock().setType(Material.LAVA);
					sender.sendMessage("§cMegidézve.. :O "+target.getDisplayName());
					}
				}
			}else {
				sender.sendMessage("§c------\nParancsok: \n/troll lava <név>\n/troll tnt <név>\n------");
			}
		}
		return false;
	}
	
}
