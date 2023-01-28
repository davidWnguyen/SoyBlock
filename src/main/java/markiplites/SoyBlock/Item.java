package markiplites.SoyBlock;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class Item implements Listener
{
	private ItemMeta meta;
	private ItemStack stack;
	public Item(String itemID, String itemName, Material mat, HashMap<attr, Double> attributes, String lore, Double itemType)
	{
		if(mat == null)
		{
			Bukkit.getLogger().info("INVALID material for " + itemID);
			return;
		}
		stack = new ItemStack(mat);
		meta = stack.getItemMeta();
		if(meta == null) return;

		attributes.put(attr.itemType, itemType);

		meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "itemID"), PersistentDataType.STRING, itemID);
		meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING, IridiumColorAPI.process(lore));
		
		for(attr attributeName : attributes.keySet()) {
			double attributeValue = attributes.get(attributeName);
			meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), attributeName.name()), PersistentDataType.DOUBLE, attributeValue);
		}
		meta.setDisplayName(IridiumColorAPI.process(itemName));

		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		stack.setItemMeta(meta);
	}
	public ItemMeta getItemMeta()
	{
		return meta;
	}
	public ItemStack getItemStack() { return stack;}
	public void setItemMeta(ItemMeta inputMeta) {meta = inputMeta;}
	public void setItemStack(ItemStack inputStack) { stack = inputStack;}
	public void finalizeItem(String itemID){
		ItemListHandler.putItemMap(itemID,stack);
		CustomAttributes.updateItem(stack);
	}
}
