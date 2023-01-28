package markiplites.SoyBlock.ItemClasses;

import java.util.HashMap;

import markiplites.SoyBlock.Item;
import markiplites.SoyBlock.attr;
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
	public Sword(String itemID, String itemName, Material mat, HashMap<attr, Double> attributes, String lore)
	{
		super(itemID,itemName,mat,attributes,lore,1.0);
		Bukkit.getLogger().info("Added Sword: " + itemID + " to item dictionary.");
		this.finalizeItem(itemID);
	}
}
