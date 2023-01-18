package markiplites.SoyBlock;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;


public class CustomAttributes implements Listener {
    static String ScalingToLetter(double num) {
    	if(num <= 0.4) {
    		return "F";
    	}else if(num <= 0.75) {
    		return "D";
    	}else if(num <= 0.9) {
    		return "C";
    	}else if(num <= 1.1) {
    		return "B";
    	}else if(num <= 1.6) {
    		return "A";
    	}else if(num <= 2.0) {
    		return "S";
    	}else{
    		return "S+";
    	}
    }
	
	static void updateItem(ItemStack item) {
		if(item == null)
			return;
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if(container.has(new NamespacedKey(Main.getInstance(), "UUID"), PersistentDataType.DOUBLE) && !meta.getPersistentDataContainer().has(new NamespacedKey(Main.getInstance(), "itemUUID"), PersistentDataType.STRING))
		{
			String itemUUID = UUID.randomUUID().toString();
			meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), "itemUUID"), PersistentDataType.STRING, itemUUID);
		}
		
		//all da variables!!!!!!!!meme
		double baseDamage = container.has(new NamespacedKey(Main.getInstance(), "baseDamage"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "baseDamage"), PersistentDataType.DOUBLE) : 0.0;
		double baseAttackSpeed = container.has(new NamespacedKey(Main.getInstance(), "baseAttackSpeed"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "baseAttackSpeed"), PersistentDataType.DOUBLE) : 0.0;
		double attackReachBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "attackReachBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "attackReachBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double attackSpeedBaseMult = container.has(new NamespacedKey(Main.getInstance(), "attackSpeedBaseMult"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "attackSpeedBaseMult"), PersistentDataType.DOUBLE) : 0.0;
		double healthBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "healthBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "healthBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double absorptionBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "absorptionBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "absorptionBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double intelligenceBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "intelligenceBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "intelligenceBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double intelligenceScaling = container.has(new NamespacedKey(Main.getInstance(), "intelligenceScaling"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "intelligenceScaling"), PersistentDataType.DOUBLE) : 0.0;
		double strengthBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "strengthBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "strengthBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double strengthScaling = container.has(new NamespacedKey(Main.getInstance(), "strengthScaling"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "strengthScaling"), PersistentDataType.DOUBLE) : 0.0;
		double dexterityBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "dexterityBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "dexterityBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double dexterityScaling = container.has(new NamespacedKey(Main.getInstance(), "dexteritySCaling"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "dexterityScaling"), PersistentDataType.DOUBLE) : 0.0;
		//Independent Stats
		double critChance = container.has(new NamespacedKey(Main.getInstance(), "critChance"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "critChance"), PersistentDataType.DOUBLE) : 0.0;
		double critDamage = container.has(new NamespacedKey(Main.getInstance(), "critDamage"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "critDamage"), PersistentDataType.DOUBLE) : 0.0;
		double regenerationBonus = container.has(new NamespacedKey(Main.getInstance(), "regenerationBonus"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "regenerationBonus"), PersistentDataType.DOUBLE) : 0.0;
		double moveSpeed = container.has(new NamespacedKey(Main.getInstance(), "moveSpeed"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "moveSpeed"), PersistentDataType.DOUBLE) : 0.0;
		//Mining Stats
		double miningSpeed = container.has(new NamespacedKey(Main.getInstance(), "miningSpeed"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "miningSpeed"), PersistentDataType.DOUBLE) : 0.0;
		double miningHardness = container.has(new NamespacedKey(Main.getInstance(), "miningHardness"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "miningHardness"), PersistentDataType.DOUBLE) : 0.0;
		double miningFortune = container.has(new NamespacedKey(Main.getInstance(), "miningFortune"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "miningFortune"), PersistentDataType.DOUBLE) : 0.0;
		
		//Set lore of item
		ArrayList<String> lore = new ArrayList<>();
		//Base Damage
		if(baseDamage > 0) {
			lore.add(String.format("§6Damage: §x§f§f§6§b§0§0%.0f\n",baseDamage));
		}
		//Attackspeeds, do not add base attackspeed and multiplier on same item
		if(baseAttackSpeed > 0) {
			lore.add(String.format("§6Attack Speed: §x§f§f§9§9§0§0%.1f\n",baseAttackSpeed));
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,new AttributeModifier("GENERIC_ATTACK_SPEED",baseAttackSpeed-4.0,AttributeModifier.Operation.ADD_NUMBER));	
		}
		//attackspeed bonus for armors/talismans
		if(attackSpeedBaseMult > 0) {
			lore.add(String.format("§6Attack Speed: §a+§x§f§f§9§9§0§0%.0f%%\n",attackSpeedBaseMult));
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,new AttributeModifier("GENERIC_ATTACK_SPEED",attackSpeedBaseMult,AttributeModifier.Operation.ADD_SCALAR));	
		}else if(attackSpeedBaseMult < 0) {
			lore.add(String.format("§6Attack Speed: §c-§x§f§f§9§9§0§0%.0f%%\n",Math.abs(attackSpeedBaseMult)));
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,new AttributeModifier("GENERIC_ATTACK_SPEED",attackSpeedBaseMult,AttributeModifier.Operation.ADD_SCALAR));	
		}
		//Attack Range
		if(attackReachBonusRaw != 0) {
			lore.add(String.format("§6Attack Range: §x§f§f§9§9§0§0%.1f\n",attackReachBonusRaw));
		}
		//Mining Stats
		//Mining Speed
		if(miningSpeed > 0) {
			lore.add(String.format("§6\u26CF Mining Speed: §a+§x§0§0§a§1§f§b%.0f\n",miningSpeed));
		}else if(miningSpeed < 0) {
			lore.add(String.format("§6\u26CF Mining Speed: §c-§x§0§0§a§1§f§b%.0f\n",Math.abs(miningSpeed)));
		}
		//Mining Hardness
		if(miningHardness > 0) {
			lore.add(String.format("§6\u26CF Mining Tier: §a+§x§0§0§a§1§f§b%.0f\n",miningHardness));
		}else if(miningHardness < 0) {
			lore.add(String.format("§6\u26CF Mining Tier: §c-§x§0§0§a§1§f§b%.0f\n",Math.abs(miningHardness)));
		}
		//Mining Fortune
		if(miningFortune > 0) {
			lore.add(IridiumColorAPI.process(String.format("§6\u2663 Mining Fortune: §a+<GRADIENT:059600>%.0f</GRADIENT:00F794>\n",miningFortune)));
		}else if(miningFortune < 0) {
			lore.add(String.format("§6\u2663 Mining Fortune: §c-<GRADIENT:059600>%.0f</GRADIENT:00F794>f\n",Math.abs(miningFortune)));
		}
		
		//Health
		if(healthBonusRaw > 0) {
			lore.add(String.format("§6\u2764 Maximum Health: §a+§c%.0f\n",healthBonusRaw));
		}else if(healthBonusRaw < 0) {
			lore.add(String.format("§6\u2764 Maximum Health: §c-§c%.0f\n",Math.abs(healthBonusRaw)));
		}
		//Absorption (defense)
		if(absorptionBonusRaw > 0) {
			lore.add(String.format("§6\u26E8 Absorption: §a+§a%.2f%%\n",absorptionBonusRaw));
		}else if(absorptionBonusRaw < 0) {
			lore.add(String.format("§6\u26E8 Absorption: §c-§a%.2f%%\n",Math.abs(absorptionBonusRaw)));
		}
		//Intelligence
		if(intelligenceBonusRaw > 0) {
			lore.add(String.format("§6\u2605 Intelligence: §a+§b%.0f\n",intelligenceBonusRaw));
		}else if(intelligenceBonusRaw < 0) {
			lore.add(String.format("§6\u2605 Intelligence: §c-§b%.0f\n",Math.abs(intelligenceBonusRaw)));
		}
		
		if(intelligenceScaling > 0) {
			lore.add(String.format("§6\u2605 Intelligence Scaling - §b%s (%.2f)\n",ScalingToLetter(intelligenceScaling),intelligenceScaling));
		}
		//Strength
		if(strengthBonusRaw > 0) {
			lore.add(String.format("§6\u01A9 Strength: §a+§x§2§5§8§e§0§0%.0f\n",strengthBonusRaw));
		}else if(strengthBonusRaw < 0) {
			lore.add(String.format("§6\u01A9 Strength: §c-§x§2§5§8§e§0§0%.0f\n",Math.abs(strengthBonusRaw)));
		}
		
		if(strengthScaling > 0) {
			lore.add(String.format("§6\u01A9 Strength Scaling - §x§2§5§8§e§0§0%s (%.2f)\n",ScalingToLetter(strengthScaling),strengthScaling));
		}
		//Dexterity
		if(dexterityBonusRaw > 0) {
			lore.add(String.format("§6\u2620 Dexterity: §a+§x§d§8§0§0§6§8%.0f\n",dexterityBonusRaw));
		}else if(dexterityBonusRaw < 0) {
			lore.add(String.format("§6\u2620 Dexterity: §c-§x§d§8§0§0§6§8%.0f\n",Math.abs(dexterityBonusRaw)));
		}
		
		if(dexterityScaling > 0) {
			lore.add(String.format("§6\u2620 Dexterity Scaling - §x§d§8§0§0§6§8%s (%.2f)\n",ScalingToLetter(dexterityScaling),dexterityScaling));
		}
		
		if(critChance > 0) {
			lore.add(String.format("§6\u2620 Crit Chance: §a+§x§f§b§0§0§d§3%.0f%%\n",critChance*100.0));
		}else if(critChance < 0) {
			lore.add(String.format("§6\u2620 Crit Chance: §c-§x§f§b§0§0§d§3%.0f%%\n",Math.abs(critChance)*100.0));
		}
		
		if(critDamage > 0) {
			lore.add(String.format("§6\u2620 Crit Damage: §a+§x§9§c§0§0§f§b%.0f%%\n",critDamage*100.0));
		}else if(critDamage < 0) {
			lore.add(String.format("§6\u2620 Crit Damage: §c-§x§9§c§0§0§f§b%.0f%%\n",Math.abs(critDamage)*100.0));
		}
		
		if(regenerationBonus > 0) {
			lore.add(String.format("§6\u2764 Regeneration Bonus: §a+§c%.0f%%\n",regenerationBonus));
		}else if(regenerationBonus < 0) {
			lore.add(String.format("§6\u2764 Regeneration Bonus: §c-%.0f%%\n",Math.abs(regenerationBonus)));
		}
		
		if(moveSpeed > 0) {
			lore.add(String.format("§6\u2604 Speed: §a+§c%.0f%%\n",moveSpeed*100.0));
		}else if(moveSpeed < 0) {
			lore.add(String.format("§6\u2604 Speed: §c-%.0f%%\n",Math.abs(moveSpeed)*100.0));
		}
		

		if(container.has(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING)) {	
			lore.addAll(Arrays.asList(container.get(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING).split(", ")));
		}
		
		int rarity = container.has(new NamespacedKey(Main.getInstance(), "rarity"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "rarity"), PersistentDataType.DOUBLE)) : 1;
		int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 0;

		switch (rarity) {
			case 2 -> lore.add(IridiumColorAPI.process(String.format("<SOLID:33cccc>Uncommon %s", getItemTypeIntToString(itemType))));
			case 3 -> lore.add(IridiumColorAPI.process(String.format("<SOLID:9933ff>Rare %s", getItemTypeIntToString(itemType))));
			case 4 -> lore.add(IridiumColorAPI.process(String.format("<SOLID:ff3399>Epic %s", getItemTypeIntToString(itemType))));
			case 5 -> lore.add(IridiumColorAPI.process(String.format("<GRADIENT:cc0000>Famed %s</GRADIENT:ff9933>", getItemTypeIntToString(itemType))));
			case 6 -> lore.add(IridiumColorAPI.process(String.format("<GRADIENT:0099ff>Legendary %s</GRADIENT:cc66ff>", getItemTypeIntToString(itemType))));
			default -> lore.add(IridiumColorAPI.process(String.format("<SOLID:33cc33>Common %s", getItemTypeIntToString(itemType))));
		}
		
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
		
	}
	static void giveItemStats(ItemStack item, HashMap<String, Double> attributes) {
		if(item == null)
			return;
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		//String itemUUID = container.has(new NamespacedKey(Main.getInstance(), "itemUUID"), PersistentDataType.STRING) ? container.get(new NamespacedKey(Main.getInstance(), "itemUUID"), PersistentDataType.STRING) : "";
		
		double baseDamage = container.has(new NamespacedKey(Main.getInstance(), "baseDamage"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "baseDamage"), PersistentDataType.DOUBLE) : 0.0;
		double baseAttackSpeed = container.has(new NamespacedKey(Main.getInstance(), "baseAttackSpeed"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "baseAttackSpeed"), PersistentDataType.DOUBLE) : 0.0;
		double attackSpeedBaseMult = container.has(new NamespacedKey(Main.getInstance(), "attackSpeedBaseMult"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "attackSpeedBaseMult"), PersistentDataType.DOUBLE) : 0.0;
		double attackReachBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "attackReachBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "attackReachBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double healthBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "healthBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "healthBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double absorptionBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "absorptionBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "absorptionBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double intelligenceBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "intelligenceBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "intelligenceBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double intelligenceScaling = container.has(new NamespacedKey(Main.getInstance(), "intelligenceScaling"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "intelligenceScaling"), PersistentDataType.DOUBLE) : 0.0;
		double strengthBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "strengthBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "strengthBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double strengthScaling = container.has(new NamespacedKey(Main.getInstance(), "strengthScaling"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "strengthScaling"), PersistentDataType.DOUBLE) : 0.0;
		double dexterityBonusRaw = container.has(new NamespacedKey(Main.getInstance(), "dexterityBonusRaw"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "dexterityBonusRaw"), PersistentDataType.DOUBLE) : 0.0;
		double dexterityScaling = container.has(new NamespacedKey(Main.getInstance(), "dexterityScaling"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "dexterityScaling"), PersistentDataType.DOUBLE) : 0.0;
		
		double critChance = container.has(new NamespacedKey(Main.getInstance(), "critChance"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "critChance"), PersistentDataType.DOUBLE) : 0.0;
		double critDamage = container.has(new NamespacedKey(Main.getInstance(), "critDamage"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "critDamage"), PersistentDataType.DOUBLE) : 0.0;
		double regenerationBonus = container.has(new NamespacedKey(Main.getInstance(), "regenerationBonus"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "regenerationBonus"), PersistentDataType.DOUBLE) : 0.0;
		double moveSpeed = container.has(new NamespacedKey(Main.getInstance(), "moveSpeed"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "moveSpeed"), PersistentDataType.DOUBLE) : 0.0;
		
		double miningSpeed = container.has(new NamespacedKey(Main.getInstance(), "miningSpeed"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "miningSpeed"), PersistentDataType.DOUBLE) : 0.0;
		double miningHardness = container.has(new NamespacedKey(Main.getInstance(), "miningHardness"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "miningHardness"), PersistentDataType.DOUBLE) : 0.0;
		double toolType = container.has(new NamespacedKey(Main.getInstance(), "toolType"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "toolType"), PersistentDataType.DOUBLE) : 0.0;
		double miningFortune = container.has(new NamespacedKey(Main.getInstance(), "miningFortune"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "miningFortune"), PersistentDataType.DOUBLE) : 0.0;
		
		ArrayList<String> lore = new ArrayList<>();
		//Base Damage
		if(baseDamage > 0) {
			lore.add(String.format("§6Damage: §x§f§f§6§b§0§0%.0f\n",baseDamage));
			attributes.put("BaseDamage",attributes.get("BaseDamage") + baseDamage);
		}
		//Attackspeeds, do not add base attackspeed and multiplier on same item
		if(baseAttackSpeed > 0) {
			lore.add(String.format("§6Attack Speed: §x§f§f§9§9§0§0%.1f\n",baseAttackSpeed));
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,new AttributeModifier("GENERIC_ATTACK_SPEED",baseAttackSpeed-4.0,AttributeModifier.Operation.ADD_NUMBER));
			attributes.put("AttackSpeed", baseAttackSpeed);
		}
		//attackspeed bonus for armors/talismans
		if(attackSpeedBaseMult > 0) {
			lore.add(String.format("§6Attack Speed: §a+§x§f§f§9§9§0§0%.0f%%\n",attackSpeedBaseMult));
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,new AttributeModifier("GENERIC_ATTACK_SPEED",attackSpeedBaseMult,AttributeModifier.Operation.ADD_SCALAR));	
			attributes.put("AttackSpeedBonus", attributes.get("AttackSpeedBonus") + attackSpeedBaseMult);
		}else if(attackSpeedBaseMult < 0) {
			lore.add(String.format("§6Attack Speed: §c+§x§f§f§9§9§0§0%.0f%%\n",Math.abs(attackSpeedBaseMult)));
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,new AttributeModifier("GENERIC_ATTACK_SPEED",attackSpeedBaseMult,AttributeModifier.Operation.ADD_SCALAR));	
			attributes.put("AttackSpeedBonus", attributes.get("AttackSpeedBonus") + attackSpeedBaseMult);
		}
		//Attack Range
		if(attackReachBonusRaw != 0) {
			lore.add(String.format("§6Attack Range: §x§f§f§9§9§0§0%.1f\n",attackReachBonusRaw));
			attributes.put("AttackRange", attackReachBonusRaw);
		}
		//Mining Stats6
		//Mining Speed
		if(miningSpeed > 0) {
			lore.add(String.format("§6\u26CF Mining Speed: §a+§x§0§0§a§1§f§b%.0f\n",miningSpeed));
			attributes.put("MiningSpeed", attributes.get("MiningSpeed") + miningSpeed);
		}else if(miningSpeed < 0) {
			lore.add(String.format("§6\u26CF Mining Speed: §c-§x§0§0§a§1§f§b%.0f\n",Math.abs(miningSpeed)));
			attributes.put("MiningSpeed", attributes.get("MiningSpeed") + miningSpeed);
		}
		//Mining Hardness
		if(miningHardness > 0) {
			lore.add(String.format("§6\u26CF Mining Tier: §a+§x§0§0§a§1§f§b%.0f\n",miningHardness));
			attributes.put("ToolHardness", attributes.get("ToolHardness") + miningHardness);
		}else if(miningHardness < 0) {
			lore.add(String.format("§6\u26CF Mining Tier: §c-§x§0§0§a§1§f§b%.0f\n",Math.abs(miningHardness)));
			attributes.put("ToolHardness", attributes.get("ToolHardness") + miningHardness);
		}
		//Mining Fortune
		if(miningFortune > 0) {
			lore.add(IridiumColorAPI.process(String.format("§6\u2663 Mining Fortune: §a+<GRADIENT:059600>%.0f</GRADIENT:00F794>\n",miningFortune)));
			attributes.put("MiningFortune", attributes.get("MiningFortune") + miningFortune);
		}else if(miningFortune < 0) {
			lore.add(String.format("§6\u2663 Mining Fortune: §c-<GRADIENT:059600>%.0f</GRADIENT:00F794>f\n",Math.abs(miningFortune)));
			attributes.put("MiningFortune", attributes.get("MiningFortune") + miningFortune);
		}
		//Tool Type
		if(toolType > 0) {
			attributes.put("ToolID", toolType);
		}
		//Health
		if(healthBonusRaw > 0) {
			attributes.put("MaxHealth", attributes.get("MaxHealth") + healthBonusRaw);
			lore.add(String.format("§6\u2764 Maximum Health: §a+§c%.0f\n",healthBonusRaw));
		}else if(healthBonusRaw < 0) {
			attributes.put("MaxHealth", attributes.get("MaxHealth") + healthBonusRaw);
			lore.add(String.format("§6\u2764 Maximum Health: §c-§c%.0f\n",Math.abs(healthBonusRaw)));
		}
		//Absorption (defense)
		if(absorptionBonusRaw > 0) {
			attributes.put("Absorption", attributes.get("Absorption") - ((attributes.get("Absorption")*absorptionBonusRaw)/100.0) + absorptionBonusRaw);
			lore.add(String.format("§6\u26E8 Absorption: §a+§a%.2f%%\n",absorptionBonusRaw));
		}else if(absorptionBonusRaw < 0) {
			attributes.put("Absorption", attributes.get("Absorption") - ((attributes.get("Absorption")*absorptionBonusRaw)/100.0) + absorptionBonusRaw);
			lore.add(String.format("§6\u26E8 Absorption: §c-§a%.2f%%\n",Math.abs(absorptionBonusRaw)));
		}
		//Intelligence
		if(intelligenceBonusRaw > 0) {
			attributes.put("Intelligence", attributes.get("Intelligence") + intelligenceBonusRaw);
			lore.add(String.format("§6\u2605 Intelligence: §a+§b%.0f\n",intelligenceBonusRaw));
		}else if(intelligenceBonusRaw < 0) {
			attributes.put("Intelligence", attributes.get("Intelligence") + intelligenceBonusRaw);
			lore.add(String.format("§6\u2605 Intelligence: §c-§b%.0f\n",Math.abs(intelligenceBonusRaw)));
		}
		
		if(intelligenceScaling > 0) {
			attributes.put("IntelligenceScaling", attributes.get("IntelligenceScaling") + intelligenceScaling);
			lore.add(String.format("§6\u2605 Intelligence Scaling - §b%s (%.2f)\n",ScalingToLetter(intelligenceScaling),intelligenceScaling));
		}
		//Strength
		if(strengthBonusRaw > 0) {
			attributes.put("Strength", attributes.get("Strength") + strengthBonusRaw);
			lore.add(String.format("§6\u01A9 Strength: §a+§x§2§5§8§e§0§0%.0f\n",strengthBonusRaw));
		}else if(strengthBonusRaw < 0) {
			attributes.put("Strength", attributes.get("Strength") + strengthBonusRaw);
			lore.add(String.format("§6\u01A9 Strength: §c-§x§2§5§8§e§0§0%.0f\n",Math.abs(strengthBonusRaw)));
		}
		
		if(strengthScaling > 0) {
			attributes.put("StrengthScaling", attributes.get("StrengthScaling") + strengthScaling);
			lore.add(String.format("§6\u01A9 Strength Scaling - §x§2§5§8§e§0§0%s (%.2f)\n",ScalingToLetter(strengthScaling),strengthScaling));
		}
		//Dexterity
		if(dexterityBonusRaw > 0) {
			attributes.put("Dexterity", attributes.get("Dexterity") + dexterityBonusRaw);
			lore.add(String.format("§6\u2620 Dexterity: §a+§x§d§8§0§0§6§8%.0f\n",dexterityBonusRaw));
		}else if(dexterityBonusRaw < 0) {
			attributes.put("Dexterity", attributes.get("Dexterity") + dexterityBonusRaw);
			lore.add(String.format("§6\u2620 Dexterity: §c-§x§d§8§0§0§6§8%.0f\n",Math.abs(dexterityBonusRaw)));
		}
		
		if(dexterityScaling > 0) {
			attributes.put("DexterityScaling", attributes.get("DexterityScaling") + dexterityScaling);
			lore.add(String.format("§6\u2620 Dexterity Scaling - §x§d§8§0§0§6§8%s (%.2f)\n",ScalingToLetter(dexterityScaling),dexterityScaling));
		}
		
		if(critChance > 0) {
			attributes.put("CritChance", attributes.get("CritChance") + critChance);
			lore.add(String.format("§6\u2620 Crit Chance: §a+§x§f§b§0§0§d§3%.0f%%\n",critChance*100.0));
		}else if(critChance < 0) {
			attributes.put("CritChance", attributes.get("CritChance") + critChance);
			lore.add(String.format("§6\u2620 Crit Chance: §c-§x§f§b§0§0§d§3%.0f%%\n",Math.abs(critChance*100.0)));
		}
		
		if(critDamage > 0) {
			attributes.put("CritDamage", attributes.get("CritDamage") + critDamage);
			lore.add(String.format("§6\u2620 Crit Damage: §a+§x§9§c§0§0§f§b%.0f%%\n",critDamage*100.0));
		}else if(critDamage < 0) {
			attributes.put("CritDamage", attributes.get("CritDamage") + critDamage);
			lore.add(String.format("§6\u2620 Crit Damage: §c-§x§9§c§0§0§f§b%.0f%%\n",Math.abs(critDamage*100.0)));
		}

		if(regenerationBonus > 0) {
			attributes.put("RegenerationBonus", attributes.get("RegenerationBonus") + regenerationBonus);
			lore.add(String.format("§6\u2764 Regeneration Bonus: §a+§c%.0f%%\n",regenerationBonus*100.0));
		}else if(regenerationBonus < 0) {
			attributes.put("RegenerationBonus", attributes.get("RegenerationBonus") + regenerationBonus);
			lore.add(String.format("§6\u2764 Regeneration Bonus: §c-%.0f%%\n",Math.abs(regenerationBonus*100.0)));
		}
		
		if(moveSpeed > 0) {
			attributes.put("Speed", attributes.get("Speed") + moveSpeed);
			lore.add(String.format("§6\u2604 Speed: §a+§c%.0f%%\n",moveSpeed*100.0));
		}else if(moveSpeed < 0) {
			attributes.put("Speed", attributes.get("Speed") + moveSpeed);
			lore.add(String.format("§6\u2604 Speed: §c-%.0f%%\n",Math.abs(moveSpeed*100.0)));
		}
		
		if(container.has(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING)) {	
			lore.add(container.get(new NamespacedKey(Main.getInstance(), "additionalLore"), PersistentDataType.STRING));
		}
		
		int rarity = container.has(new NamespacedKey(Main.getInstance(), "rarity"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "rarity"), PersistentDataType.DOUBLE)) : 1;
		int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 0;

		switch (rarity) {
			case 2 -> lore.add(IridiumColorAPI.process(String.format("<SOLID:33cccc>Uncommon %s", getItemTypeIntToString(itemType))));
			case 3 -> lore.add(IridiumColorAPI.process(String.format("<SOLID:9933ff>Rare %s", getItemTypeIntToString(itemType))));
			case 4 -> lore.add(IridiumColorAPI.process(String.format("<SOLID:ff3399>Epic %s", getItemTypeIntToString(itemType))));
			case 5 -> lore.add(IridiumColorAPI.process(String.format("<GRADIENT:cc0000>Famed %s</GRADIENT:ff9933>", getItemTypeIntToString(itemType))));
			case 6 -> lore.add(IridiumColorAPI.process(String.format("<GRADIENT:0099ff>Legendary %s</GRADIENT:cc66ff>", getItemTypeIntToString(itemType))));
			default -> lore.add(IridiumColorAPI.process(String.format("<SOLID:33cc33>Common %s", getItemTypeIntToString(itemType))));
		}
		
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setUnbreakable(true);
		item.setItemMeta(meta);
	}
	public CustomAttributes() {
		init();
	}
	public void init() {
	}
	public static HashMap<String,Double> defaultStats()
	{
		final HashMap<String, Double> defaultAttributes = new HashMap<>();
		defaultAttributes.put("BaseDamage", 5.0);
		defaultAttributes.put("AttackSpeed", 4.0);
		defaultAttributes.put("AttackSpeedBonus", 1.0);
		defaultAttributes.put("Health", 100.0);
		defaultAttributes.put("MaxHealth", 100.0);
		defaultAttributes.put("Absorption", 0.0);
		defaultAttributes.put("Mana", 100.0);
		defaultAttributes.put("MaxMana", 100.0);
		defaultAttributes.put("Intelligence", 0.0);
		defaultAttributes.put("IntelligenceScaling", 0.0);
		defaultAttributes.put("Strength", 0.0);
		defaultAttributes.put("StrengthScaling", 0.0);
		defaultAttributes.put("Dexterity", 0.0);
		defaultAttributes.put("DexterityScaling", 0.0);
		defaultAttributes.put("AttackRange", 3.0);
		defaultAttributes.put("CritChance", 0.0);
		defaultAttributes.put("CritDamage", 0.0);
		defaultAttributes.put("RegenerationBonus", 0.0);
		defaultAttributes.put("Speed", 0.0);
		defaultAttributes.put("MiningSpeed", 1.0);
		defaultAttributes.put("ToolHardness", 0.0);
		defaultAttributes.put("MiningFortune", 0.0);
		return defaultAttributes;
	}
	@EventHandler
	public void onInventoryChange(PlayerItemHeldEvent e) { //works on item, not on armor
		Player p = e.getPlayer();
		HashMap<Player, HashMap<String, Double>> playerAttributes = Main.getAttributes();
		HashMap<String, Double> attributes = CustomAttributes.defaultStats();

		if(p.getInventory().getItem(e.getNewSlot()) != null) {
			p.setCooldown(p.getInventory().getItem(e.getNewSlot()).getType(), 4);
			CustomAttributes.giveItemStats(p.getInventory().getItem(e.getNewSlot()),attributes);
		}
		new BukkitRunnable(){public void run(){//Start of Delay
		getUpdatedPlayerAttributes(p, attributes, false);
		playerAttributes.put(p, attributes);
		}}.runTaskLater(Main.getInstance(), 1);
	}

	@EventHandler
	public void onArmorChange(ArmorEquipEvent e) {
		Player p = e.getPlayer();
		HashMap<Player, HashMap<String, Double>> playerAttributes = Main.getAttributes();
		HashMap<String, Double> attributes = CustomAttributes.defaultStats();
		
		new BukkitRunnable(){public void run(){//Start of Delay
			getUpdatedPlayerAttributes(p, attributes);
		playerAttributes.put(p, attributes);
		}}.runTaskLater(Main.getInstance(), 1);
	}
	@EventHandler
	public void onPickupEvent(EntityPickupItemEvent e) {
		Entity entity = e.getEntity();
		if(!(entity instanceof Player))
			return;
		ItemStack item = e.getItem().getItemStack();
		updateItem(item);
	}
	@EventHandler
	public void onDropItemEvent(PlayerDropItemEvent e)
	{
		Player p = e.getPlayer();
		if(p == null)
			return;
		Item item = e.getItemDrop();
		if(item == null)
			return;

		ItemStack itemStack = item.getItemStack();
		ItemMeta meta = itemStack.getItemMeta();
		if(meta == null)
			return;

		PersistentDataContainer container = meta.getPersistentDataContainer();
		int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 1;
		if(itemType == 200 || itemStack == p.getInventory().getItemInMainHand())
		{
			HashMap<Player, HashMap<String, Double>> playerAttributes = Main.getAttributes();
			HashMap<String, Double> attributes = CustomAttributes.defaultStats();

			new BukkitRunnable(){public void run(){//Start of Delay
				getUpdatedPlayerAttributes(p, attributes);
				playerAttributes.put(p, attributes);
			}}.runTaskLater(Main.getInstance(), 1);
		}
		int itemActionType = container.has(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE)) : -1;
		switch (itemActionType) {
			case 1 -> {
				String[] guiSetup = {
						"    s    ",
						"         ",
						"         "
				};
				InventoryGui gui = new InventoryGui(Main.getInstance(), p, "Soyblock Menu", guiSetup);
				gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS, 1)); // fill the empty slots with this

				gui.addElement(new StaticGuiElement('s',
						new ItemStack(Material.REDSTONE),
						69,
						click -> {
							click.getRawEvent().getWhoClicked().sendMessage(ChatColor.RED + "lel!");
							return true; // returning true will cancel the click event and stop taking the item
						},
						"Soyblock Stats",
						CustomAttributes.getPlayerStatsFormat(p)
				));
				gui.show(p);
				e.setCancelled(true);
			}
		}
	}
	public static String getPlayerStatsFormat(Player player)
	{
		HashMap<Player, HashMap<String, Double>> playerAttributes = Main.getAttributes();
		String statFormat = "";
		
		//if(playerAttributes.get(player).containsKey("BaseDamage"))String.format("%s§6Damage: §x§f§f§6§b§0§0%.0f\n", statFormat,playerAttributes.get(player).get("BaseDamage"));
		if(playerAttributes.get(player).containsKey("AttackSpeedBonus") && playerAttributes.get(player).get("AttackSpeedBonus")!=1.0)statFormat=String.format("%s§6Attack Speed: §a+§x§f§f§9§9§0§0%.0f%%\n", statFormat,playerAttributes.get(player).get("AttackSpeedBonus")*100.0);
		if(playerAttributes.get(player).containsKey("MaxHealth") && playerAttributes.get(player).get("MaxHealth")>0.0)statFormat=String.format("%s§6\u2764 Maximum Health: §a§c%.0f\n", statFormat,playerAttributes.get(player).get("MaxHealth"));
		if(playerAttributes.get(player).containsKey("Absorption") && playerAttributes.get(player).get("Absorption")!=0.0)statFormat=String.format("%s§6\u26E8 Absorption: §a§a%.2f%%\n", statFormat,playerAttributes.get(player).get("Absorption"));
		if(playerAttributes.get(player).containsKey("Intelligence") && playerAttributes.get(player).get("Intelligence")!=0.0)statFormat=String.format("%s§6\u2605 Intelligence: §a§b%.0f\n", statFormat,playerAttributes.get(player).get("Intelligence"));
		if(playerAttributes.get(player).containsKey("IntelligenceScaling") && playerAttributes.get(player).get("IntelligenceScaling")!=0.0)statFormat=String.format("%s§6\u2605 Intelligence Scaling - §b(%.2f)\n", statFormat,playerAttributes.get(player).get("IntelligenceScaling"));
		if(playerAttributes.get(player).containsKey("Strength") && playerAttributes.get(player).get("Strength")!=0.0)statFormat=String.format("%s§6\u01A9 Strength: §a§x§2§5§8§e§0§0%.0f\n", statFormat,playerAttributes.get(player).get("Strength"));
		if(playerAttributes.get(player).containsKey("StrengthScaling") && playerAttributes.get(player).get("StrengthScaling")!=0.0)statFormat=String.format("%s§6\u01A9 Strength Scaling - §x§2§5§8§e§0§0 (%.2f)\n", statFormat,playerAttributes.get(player).get("StrengthScaling"));
		if(playerAttributes.get(player).containsKey("Dexterity") && playerAttributes.get(player).get("Dexterity")!=0.0)statFormat=String.format("%s§6\u2620 Dexterity: §a§x§d§8§0§0§6§8%.0f\n", statFormat,playerAttributes.get(player).get("Dexterity"));
		if(playerAttributes.get(player).containsKey("DexterityScaling") && playerAttributes.get(player).get("DexterityScaling")!=0.0)statFormat=String.format("%s§6\u2620 Dexterity Scaling - §x§d§8§0§0§6§8 (%.2f)\n", statFormat,playerAttributes.get(player).get("DexterityScaling"));
		
		if(playerAttributes.get(player).containsKey("CritChance") && playerAttributes.get(player).get("CritChance")!=0.0)statFormat=String.format("%s§6\u2620 Crit Chance: §a§x§f§b§0§0§d§3%.0f%%\n", statFormat,playerAttributes.get(player).get("CritChance")*100.0);
		if(playerAttributes.get(player).containsKey("CritDamage") && playerAttributes.get(player).get("CritDamage")!=0.0)statFormat=String.format("%s§6\u2620 Crit Damage: §c+§x§9§c§0§0§f§b%.0f%%\n", statFormat,playerAttributes.get(player).get("CritDamage")*100.0);
		if(playerAttributes.get(player).containsKey("RegenerationBonus") && playerAttributes.get(player).get("RegenerationBonus")!=0.0)statFormat=String.format("%s§6\u2764 Regeneration Bonus: §a+§c%.0f%%\n", statFormat,playerAttributes.get(player).get("RegenerationBonus")*100.0);
		if(playerAttributes.get(player).containsKey("Speed") && playerAttributes.get(player).get("Speed")!=0.0)statFormat=String.format("%s§6\u2604 Speed: §a+§c%.0f%%\n", statFormat,playerAttributes.get(player).get("Speed")*100.0);
		
		if(playerAttributes.get(player).containsKey("MiningSpeed") && playerAttributes.get(player).get("MiningSpeed")!=1.0)statFormat=String.format("%s§6\u26CF Mining Speed: §a§x§0§0§a§1§f§b%.0f\n", statFormat,playerAttributes.get(player).get("MiningSpeed"));
		if(playerAttributes.get(player).containsKey("ToolHardness") && playerAttributes.get(player).get("ToolHardness")!=0.0)statFormat=String.format("%s§6\u26CF Mining Tier: §a§x§0§0§a§1§f§b%.0f\n", statFormat,playerAttributes.get(player).get("ToolHardness"));
		if(playerAttributes.get(player).containsKey("MiningFortune") && playerAttributes.get(player).get("MiningFortune")>=10.0)statFormat=IridiumColorAPI.process(String.format("%s§6\u2663 Mining Fortune: §c-<GRADIENT:059600>%.0f</GRADIENT:00F794>f\n", statFormat,playerAttributes.get(player).get("MiningFortune")));
		
		return statFormat;
	}
	public static void getUpdatedPlayerAttributes(Player p, HashMap<String, Double> attributes)
	{
		ArrayList<String> usedTalismanFamily = new ArrayList<>();
		int talismans = 0;
		for(ItemStack checkItem : p.getInventory()) {
			if(checkItem == null)
				continue;
			ItemMeta meta = checkItem.getItemMeta();
			if(meta == null)
				continue;

			PersistentDataContainer container = meta.getPersistentDataContainer();
			int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 1;
			if(itemType < 100)
			{
				CustomAttributes.giveItemStats(checkItem,attributes);
			}
			else if(itemType == 200)
			{
				if(talismans > 4)
					continue;
				if(!container.has(new NamespacedKey(Main.getInstance(), "talismanFamily"), PersistentDataType.STRING))
					continue;
				String family = container.get(new NamespacedKey(Main.getInstance(), "talismanFamily"), PersistentDataType.STRING);
				if(family == null)
					continue;
				if(usedTalismanFamily.contains(family))
					continue;

				CustomAttributes.giveItemStats(checkItem,attributes);
				usedTalismanFamily.add(family);
				talismans++;
			}
		}
		for(ItemStack checkItem: p.getEquipment().getArmorContents()) {
			if(checkItem == null)
				continue;

			ItemMeta meta = checkItem.getItemMeta();
			if(meta == null)
				continue;

			PersistentDataContainer container = meta.getPersistentDataContainer();
			int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 1;
			if(itemType > 100)
				CustomAttributes.giveItemStats(checkItem,attributes);
		}
		attributes.put("MaxMana", attributes.get("MaxMana") + attributes.get("Intelligence"));

		if(attributes.get("Health") > attributes.get("MaxHealth"))
			attributes.put("Health", attributes.get("MaxHealth"));
		if(attributes.get("Mana") > attributes.get("MaxMana"))
			attributes.put("Mana", attributes.get("MaxMana"));

		for(AttributeModifier modifier : p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
			p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(modifier);
		}
		if(attributes.containsKey("Speed")) {//Remove all speed modifiers except the speed stat.
			p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier("GENERIC_MOVEMENT_SPEED",attributes.get("Speed"),
					AttributeModifier.Operation.MULTIPLY_SCALAR_1));
		}
	}
	public static void getUpdatedPlayerAttributes(Player p, HashMap<String, Double> attributes, boolean checkWeapon)
	{
		ArrayList<String> usedTalismanFamily = new ArrayList<>();
		int talismans = 0;
		for(ItemStack checkItem : p.getInventory()) {
			if(checkItem == null)
				continue;
			ItemMeta meta = checkItem.getItemMeta();
			if(meta == null)
				continue;

			PersistentDataContainer container = meta.getPersistentDataContainer();
			int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 1;
			if(checkWeapon && itemType < 100)
			{
				CustomAttributes.giveItemStats(checkItem,attributes);
			}
			else if(itemType == 200)
			{
				if(talismans > 4)
					continue;
				if(!container.has(new NamespacedKey(Main.getInstance(), "talismanFamily"), PersistentDataType.STRING))
					continue;

				String family = container.get(new NamespacedKey(Main.getInstance(), "talismanFamily"), PersistentDataType.STRING);
				if(family == null)
					continue;

				if(usedTalismanFamily.contains(family))
					continue;

				CustomAttributes.giveItemStats(checkItem,attributes);
				usedTalismanFamily.add(family);
				talismans++;
			}
		}
		for(ItemStack checkItem: p.getEquipment().getArmorContents()) {
			if(checkItem == null)
				continue;

			ItemMeta meta = checkItem.getItemMeta();
			if(meta == null)
				continue;

			PersistentDataContainer container = meta.getPersistentDataContainer();
			int itemType = container.has(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(new NamespacedKey(Main.getInstance(), "itemType"), PersistentDataType.DOUBLE)) : 1;
			if(itemType > 100)
				CustomAttributes.giveItemStats(checkItem,attributes);
		}
		attributes.put("Health", Main.getAttributes().get(p).get("Health"));
		attributes.put("Mana", Main.getAttributes().get(p).get("Mana"));
		attributes.put("MaxMana", attributes.get("MaxMana") + attributes.get("Intelligence"));

		if(attributes.get("Health") > attributes.get("MaxHealth"))
			attributes.put("Health", attributes.get("MaxHealth"));
		if(attributes.get("Mana") > attributes.get("MaxMana"))
			attributes.put("Mana", attributes.get("MaxMana"));
		for(AttributeModifier modifier : p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
			p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(modifier);
		}
		if(attributes.containsKey("Speed")) {//Remove all speed modifiers except the speed stat.
			p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier("GENERIC_MOVEMENT_SPEED",attributes.get("Speed"),
					AttributeModifier.Operation.MULTIPLY_SCALAR_1));
		}
	}
	public static String getItemTypeIntToString(int lel)
	{
		switch (lel) {
			case 0 -> {return "Material";}
			case 1 -> {return "Sword";}
			case 2 -> {return "Bow";}
			case 3 -> {return "Hoe";}
			case 4 -> {return "Shovel";}
			case 5 -> {return "Pickaxe";}
			case 6 -> {return "Axe";}
			case 7 -> {return "Fishing Rod";}
			case 8 -> {return "Drill";}
			case 9 -> {return "Longbow";}
			case 10 -> {return "Crossbow";}
			case 11 -> {return "Greatbow";}
			case 12 -> {return "Longsword";}
			case 13 -> {return "Greatsword";}
			case 14 -> {return "Dagger";}
			case 15 -> {return "Throwing Knives";}
			case 16 -> {return "Hammer";}
			case 17 -> {return "Handgun";}
			case 18 -> {return "DMR";}
			case 19 -> {return "Shotgun";}
			case 20 -> {return "Explosives";}
			case 100 -> {return "Helmet";}
			case 101 -> {return "Chestplate";}
			case 102 -> {return "Leggings";}
			case 103 -> {return "Boots";}
			case 200 -> {return "Talisman";}
			default -> {return "";}
		}
	}
}