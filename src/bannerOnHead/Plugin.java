package bannerOnHead;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
	
	String message_remove_the_hat;
	String message_done;
	String message_take_flag_in_hand;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		FileConfiguration cfg = this.getConfig();
		
		message_remove_the_hat = cfg.getString("message-remove-the-helmet");
		message_done = cfg.getString("message-done");
		message_take_flag_in_hand = cfg.getString("message-take-flag-in-hand");
		
		this.getLogger().info("Enabled");
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		Player author = (Player) s;
		
		if(cmd.getName().equals("wear")) {
			PlayerInventory inventory = author.getInventory();
			if(inventory.getHelmet() != null) {
				author.sendMessage(message_remove_the_hat);
				return true;
			}
			ItemStack item_in_hand = inventory.getItemInMainHand();
			String item_name = item_in_hand.getType().name();
			if(item_name.equals("BANNER") | item_name.equals("STANDING_BANNER")) {
				if(item_in_hand.getAmount() > 1) {
					item_in_hand.setAmount(item_in_hand.getAmount()-1);
				}
				else {
					inventory.remove(item_in_hand);
				}
				ItemStack banner_on_head = new ItemStack(item_in_hand);
				banner_on_head.setAmount(1);
				inventory.setHelmet(banner_on_head);
				author.sendMessage(message_done);
			}
			else {
				author.sendMessage(message_take_flag_in_hand);
			}	
		}
		return true;
	}
	
}
