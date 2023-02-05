package markiplites.SoyBlock.ItemClasses;

import com.jeff_media.morepersistentdatatypes.DataType;
import markiplites.SoyBlock.Item;
import markiplites.SoyBlock.Lists.blockList;
import markiplites.SoyBlock.Main;
import markiplites.SoyBlock.attr;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class Block extends Item
{
	public Block(String itemID, String itemName, Material mat, HashMap<attr, Double> attributes, String lore, String[] itemArray)
	{
		super(itemID,itemName,mat,attributes,lore,0.0);
		ItemStack stack = super.getItemStack();
		ItemMeta meta = super.getItemMeta();
		meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "blockID"), PersistentDataType.STRING, itemID);
		stack.setItemMeta(meta);
		super.setItemMeta(meta);
		super.setItemStack(stack);
		Bukkit.getLogger().info("Added Block: " + itemID + " to item dictionary.");
		blockList.block_attributes.put(itemID, (HashMap<attr, Double>) attributes.clone());
		blockList.block_loot.put(itemID,itemArray);
		this.finalizeItem(itemID);
	}
}
