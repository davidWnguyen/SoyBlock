package markiplites.SoyBlock;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

public class Sword extends Item implements Listener
{
	public Sword(String itemID, String itemName, Material mat, HashMap<String, Double> attributes, String lore)
	{
		super(itemID,itemName,mat,attributes,lore);
		Bukkit.getLogger().info("Added Sword: " + itemID + " to item dictionary.");
	}
}
