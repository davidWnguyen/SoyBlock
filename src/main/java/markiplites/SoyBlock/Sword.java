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

public class Sword implements Listener 
{
	public void createItem(String itemID, String itemName, Material mat, HashMap<String, Double> attributes, String lore)
	{
		if(mat == null)
		{
			Bukkit.getLogger().info("INVALID material for " + itemID);
			return;
		}
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		if(meta == null) return;
		meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING, IridiumColorAPI.process(lore));
		
		for(String attributeName : attributes.keySet()) {
			double attributeValue = attributes.get(attributeName);
			meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), attributeName), PersistentDataType.DOUBLE, attributeValue);
		}
		meta.setDisplayName(IridiumColorAPI.process(itemName));

		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		item.setItemMeta(meta);
		ItemList.putItemMap(itemID,item);
		CustomAttributes.updateItem(item);	
		Bukkit.getLogger().info("Added " + itemID + " to item dictionary.");
	}
}
