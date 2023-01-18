package markiplites.SoyBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

public class ItemListHandler {
	private final static HashMap<String, ItemStack> itemMap = new HashMap<>();
	public ItemListHandler() {
		init();
	}
	public void init() {
		LoadConfigs();
	}
	@SuppressWarnings("deprecation")
	public void LoadConfigs() {
		//Load weapons
		for (String itemID : Main.getInstance().weaponConfig.getKeys(false)) {
			ConfigurationSection itemConfig = Main.getInstance().weaponConfig.getConfigurationSection(itemID);
			ConfigurationSection attributeList = Main.getInstance().weaponConfig.getConfigurationSection(itemID + ".attributes");
			Material mat = (Material.getMaterial(Objects.requireNonNull(itemConfig.getString("material"))));
			Bukkit.getLogger().info("Added " + itemID + " to weapon dictionary.");
			if (mat == null) {
				Bukkit.getLogger().info("INVALID weapon material for " + itemID);
				continue;
			}
			ItemStack item = new ItemStack(mat);
			ItemMeta meta = item.getItemMeta();

			ArrayList<String> lore = new ArrayList<>(itemConfig.getStringList("lore"));
			if (meta == null) return;
			meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING, IridiumColorAPI.process(String.join(", ", lore)));

			for (String attributeName : attributeList.getKeys(false)) {
				double attributeValue = attributeList.getDouble(attributeName);
				meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), attributeName), PersistentDataType.DOUBLE, attributeValue);
			}
			meta.setDisplayName(IridiumColorAPI.process(itemConfig.getString("itemName")));

			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			item.setItemMeta(meta);
			itemMap.put(itemID, item);
			CustomAttributes.updateItem(item);
		}
		//Load tools
		for (String itemID : Main.getInstance().toolConfig.getKeys(false)) {
			ConfigurationSection itemConfig = Main.getInstance().toolConfig.getConfigurationSection(itemID);
			ConfigurationSection attributeList = Main.getInstance().toolConfig.getConfigurationSection(itemID + ".attributes");
			Material mat = (Material.getMaterial(itemConfig.getString("material")));
			Bukkit.getLogger().info("Added " + itemID + " to tool dictionary.");
			if (mat == null) {
				Bukkit.getLogger().info("INVALID tool material for " + itemID);
				continue;
			}
			ItemStack item = new ItemStack(mat);
			ItemMeta meta = item.getItemMeta();

			ArrayList<String> lore = new ArrayList<>(itemConfig.getStringList("lore"));
			if (meta == null) return;
			meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING, IridiumColorAPI.process(String.join(", ", lore)));

			for (String attributeName : attributeList.getKeys(false)) {
				double attributeValue = attributeList.getDouble(attributeName);
				meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), attributeName), PersistentDataType.DOUBLE, attributeValue);
			}

			meta.setDisplayName(IridiumColorAPI.process(itemConfig.getString("itemName")));

			meta.setUnbreakable(true);
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			item.setItemMeta(meta);
			itemMap.put(itemID, item);
			CustomAttributes.updateItem(item);
		}
		//Load Armor
		for (String itemID : Main.getInstance().armorConfig.getKeys(false)) {
			ConfigurationSection itemConfig = Main.getInstance().armorConfig.getConfigurationSection(itemID);
			ConfigurationSection attributeList = Main.getInstance().armorConfig.getConfigurationSection(itemID + ".attributes");
			Material mat = (Material.getMaterial(itemConfig.getString("material")));
			Bukkit.getLogger().info("Added " + itemID + " to armor dictionary.");
			if (mat == null) {
				Bukkit.getLogger().info("INVALID armor material for " + itemID);
				continue;
			}
			ItemStack item = new ItemStack(mat);
			if (mat == Material.PLAYER_HEAD) {
				String head = itemConfig.getString("head");
				if (head != null) {
					UUID hashAsId = new UUID(head.hashCode(), head.hashCode());
					Bukkit.getUnsafe().modifyItemStack(item,
							"{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + head + "\"}]}}}"
					);
					Bukkit.getLogger().info("Set " + itemID + " head value to " + head);
				}
			}
			ItemMeta meta = item.getItemMeta();

			ArrayList<String> lore = new ArrayList<>(itemConfig.getStringList("lore"));
			meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING, IridiumColorAPI.process(String.join(", ", lore)));

			for (String attributeName : attributeList.getKeys(false)) {
				double attributeValue = attributeList.getDouble(attributeName);
				meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), attributeName), PersistentDataType.DOUBLE, attributeValue);
			}

			String rgbValue = itemConfig.getString("rgb");
			if (rgbValue != null) {
				int it = 0;
				int red = 0;
				int green = 0;
				int blue = 0;
				for (String color : rgbValue.split(" ")) {
					if (it == 0) {
						red = Integer.parseInt(color);
					} else if (it == 1) {
						green = Integer.parseInt(color);
					} else if (it == 2) {
						blue = Integer.parseInt(color);
					}
					it++;
				}

				Color rgb = Color.fromRGB(red, green, blue);
				Bukkit.getLogger().info("Set " + itemID + " RGB value to " + rgb.asRGB());
				((LeatherArmorMeta) meta).setColor(rgb);
			}

			meta.setDisplayName(IridiumColorAPI.process(itemConfig.getString("itemName")));

			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			item.setItemMeta(meta);
			itemMap.put(itemID, item);
			CustomAttributes.updateItem(item);
		}
	}
	public static ItemStack generateItem(String itemName)
	{
		ItemStack item = itemMap.get(itemName).clone();
		if(item == null)
			return null;
		ItemMeta meta = item.getItemMeta();

		boolean isStackable = meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), "stackable"), PersistentDataType.STRING) &&
				(meta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "stackable"), PersistentDataType.DOUBLE) == 1.0);
		if(isStackable) {
			String itemUUID = UUID.randomUUID().toString();
			meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "itemUUID"), PersistentDataType.STRING, itemUUID);
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
}