package markiplites.SoyBlock;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;


public class ItemListHandler {
	private final static HashMap<String, ItemStack> itemMap = new HashMap<>();
	public ItemListHandler() {
		init();
	}
	public void init() {}

	public static ItemStack generateItem(String itemName)
	{
		if(!itemMap.containsKey(itemName))
			{Bukkit.getLogger().info("Failure to clone "+itemName);return null;}

		ItemStack item = itemMap.get(itemName).clone();
		if(item == null)
			return null;
		ItemMeta meta = item.getItemMeta();

		boolean isStackable = meta.getPersistentDataContainer().has(Main.attributeKeys.get( "stackable"), PersistentDataType.DOUBLE) &&
				(meta.getPersistentDataContainer().get(Main.attributeKeys.get( "stackable"), PersistentDataType.DOUBLE) == 1.0);
		if(!isStackable) {
			String itemUUID = UUID.randomUUID().toString();
			meta.getPersistentDataContainer().set(Main.attributeKeys.get( "itemUUID"), PersistentDataType.STRING, itemUUID);
		}
		item.setItemMeta(meta);
		return item;
	}
	public static HashMap<String, ItemStack> getItemMap()
	{
		return itemMap;
	}
	public static void putItemMap(String itemID, ItemStack item)
	{
		itemMap.put(itemID, item);
	}
	public static ItemStack getPlayerHead(Player p)
	{
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta)head.getItemMeta();
		meta.setOwnerProfile(p.getPlayerProfile());
		head.setItemMeta(meta);
		return head;
	}
	public static ItemStack getItemForDisplay(Material mat)
	{
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		item.setItemMeta(meta);
		return item;
	}
}