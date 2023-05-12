package markiplites.SoyBlock;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
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
	
	public static void updateItem(ItemStack item) {
		if(item == null)
			return;
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if(container.has(Main.attributeKeys.get( "UUID"), PersistentDataType.DOUBLE) && !meta.getPersistentDataContainer().has(Main.attributeKeys.get( "itemUUID"), PersistentDataType.STRING))
		{
			String itemUUID = UUID.randomUUID().toString();
			container.set(Main.attributeKeys.get( "itemUUID"), PersistentDataType.STRING, itemUUID);
		}
		
		//all da variables!!!!!!!!meme
		double baseDamage = container.getOrDefault(Main.attributeKeys.get( "baseDamage"), PersistentDataType.DOUBLE, 0.0);
		double baseAttackSpeed = container.getOrDefault(Main.attributeKeys.get( "baseAttackSpeed"), PersistentDataType.DOUBLE, 0.0);
		double attackReachBonusRaw = container.getOrDefault(Main.attributeKeys.get( "attackReachBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double attackSpeedBaseMult = container.getOrDefault(Main.attributeKeys.get( "attackSpeedBaseMult"), PersistentDataType.DOUBLE, 0.0);
		double healthBonusRaw = container.getOrDefault(Main.attributeKeys.get( "healthBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double absorptionBonusRaw = container.getOrDefault(Main.attributeKeys.get( "absorptionBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double intelligenceBonusRaw = container.getOrDefault(Main.attributeKeys.get( "intelligenceBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double intelligenceScaling = container.getOrDefault(Main.attributeKeys.get( "intelligenceScaling"), PersistentDataType.DOUBLE, 0.0);
		double strengthBonusRaw = container.getOrDefault(Main.attributeKeys.get( "strengthBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double strengthScaling = container.getOrDefault(Main.attributeKeys.get( "strengthScaling"), PersistentDataType.DOUBLE, 0.0);
		double dexterityBonusRaw = container.getOrDefault(Main.attributeKeys.get( "dexterityBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double dexterityScaling = container.getOrDefault(Main.attributeKeys.get( "dexterityScaling"), PersistentDataType.DOUBLE, 0.0);
		//Independent Stats
		double critChance = container.getOrDefault(Main.attributeKeys.get( "critChance"), PersistentDataType.DOUBLE, 0.0);
		double critDamage = container.getOrDefault(Main.attributeKeys.get( "critDamage"), PersistentDataType.DOUBLE, 0.0);
		double regenerationBonus = container.getOrDefault(Main.attributeKeys.get( "regenerationBonus"), PersistentDataType.DOUBLE, 0.0);
		double moveSpeed = container.getOrDefault(Main.attributeKeys.get( "moveSpeed"), PersistentDataType.DOUBLE, 0.0);
		//Mining Stats
		double miningSpeed = container.getOrDefault(Main.attributeKeys.get( "miningSpeed"), PersistentDataType.DOUBLE, 0.0);
		double miningHardness = container.getOrDefault(Main.attributeKeys.get( "miningHardness"), PersistentDataType.DOUBLE, 0.0);
		double miningFortune = container.getOrDefault(Main.attributeKeys.get( "miningFortune"), PersistentDataType.DOUBLE, 0.0);
		//Projectile Stats
		double projectileSpeed = container.getOrDefault(Main.attributeKeys.get( "projectileSpeed"), PersistentDataType.DOUBLE, 1.0);
		double blastRadius = container.getOrDefault(Main.attributeKeys.get( "blastRadius"), PersistentDataType.DOUBLE, 0.0);
		double blastFalloff = container.getOrDefault(Main.attributeKeys.get( "blastFalloff"), PersistentDataType.DOUBLE, 0.0);

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
			lore.add(String.format("§6⛏ Mining Speed: §a+§x§0§0§a§1§f§b%.0f\n",miningSpeed));
		}else if(miningSpeed < 0) {
			lore.add(String.format("§6⛏ Mining Speed: §c-§x§0§0§a§1§f§b%.0f\n",Math.abs(miningSpeed)));
		}
		//Mining Hardness
		if(miningHardness > 0) {
			lore.add(String.format("§6⛏ Mining Tier: §a+§x§0§0§a§1§f§b%.0f\n",miningHardness));
		}else if(miningHardness < 0) {
			lore.add(String.format("§6⛏ Mining Tier: §c-§x§0§0§a§1§f§b%.0f\n",Math.abs(miningHardness)));
		}
		//Mining Fortune
		if(miningFortune > 0) {
			lore.add(IridiumColorAPI.process(String.format("§6♣ Mining Fortune: §a+<GRADIENT:059600>%.0f</GRADIENT:00F794>\n",miningFortune)));
		}else if(miningFortune < 0) {
			lore.add(String.format("§6♣ Mining Fortune: §c-<GRADIENT:059600>%.0f</GRADIENT:00F794>f\n",Math.abs(miningFortune)));
		}
		
		//Health
		if(healthBonusRaw > 0) {
			lore.add(String.format("§6❤ Maximum Health: §a+§c%.0f\n",healthBonusRaw));
		}else if(healthBonusRaw < 0) {
			lore.add(String.format("§6❤ Maximum Health: §c-§c%.0f\n",Math.abs(healthBonusRaw)));
		}
		//Absorption (defense)
		if(absorptionBonusRaw > 0) {
			lore.add(String.format("§6⛨ Absorption: §a+§a%.2f%%\n",absorptionBonusRaw));
		}else if(absorptionBonusRaw < 0) {
			lore.add(String.format("§6⛨ Absorption: §c-§a%.2f%%\n",Math.abs(absorptionBonusRaw)));
		}
		//Intelligence
		if(intelligenceBonusRaw > 0) {
			lore.add(String.format("§6★ Intelligence: §a+§b%.0f\n",intelligenceBonusRaw));
		}else if(intelligenceBonusRaw < 0) {
			lore.add(String.format("§6★ Intelligence: §c-§b%.0f\n",Math.abs(intelligenceBonusRaw)));
		}
		
		if(intelligenceScaling > 0) {
			lore.add(String.format("§6★ Intelligence Scaling - §b%s (%.2f)\n",ScalingToLetter(intelligenceScaling),intelligenceScaling));
		}
		//Strength
		if(strengthBonusRaw > 0) {
			lore.add(String.format("§6Ʃ Strength: §a+§x§2§5§8§e§0§0%.0f\n",strengthBonusRaw));
		}else if(strengthBonusRaw < 0) {
			lore.add(String.format("§6Ʃ Strength: §c-§x§2§5§8§e§0§0%.0f\n",Math.abs(strengthBonusRaw)));
		}
		
		if(strengthScaling > 0) {
			lore.add(String.format("§6Ʃ Strength Scaling - §x§2§5§8§e§0§0%s (%.2f)\n",ScalingToLetter(strengthScaling),strengthScaling));
		}
		//Dexterity
		if(dexterityBonusRaw > 0) {
			lore.add(String.format("§6☠ Dexterity: §a+§x§d§8§0§0§6§8%.0f\n",dexterityBonusRaw));
		}else if(dexterityBonusRaw < 0) {
			lore.add(String.format("§6☠ Dexterity: §c-§x§d§8§0§0§6§8%.0f\n",Math.abs(dexterityBonusRaw)));
		}
		
		if(dexterityScaling > 0) {
			lore.add(String.format("§6☠ Dexterity Scaling - §x§d§8§0§0§6§8%s (%.2f)\n",ScalingToLetter(dexterityScaling),dexterityScaling));
		}
		
		if(critChance > 0) {
			lore.add(String.format("§6☠ Crit Chance: §a+§x§f§b§0§0§d§3%.0f%%\n",critChance*100.0));
		}else if(critChance < 0) {
			lore.add(String.format("§6☠ Crit Chance: §c-§x§f§b§0§0§d§3%.0f%%\n",Math.abs(critChance)*100.0));
		}
		
		if(critDamage > 0) {
			lore.add(String.format("§6☠ Crit Damage: §a+§x§9§c§0§0§f§b%.0f%%\n",critDamage*100.0));
		}else if(critDamage < 0) {
			lore.add(String.format("§6☠ Crit Damage: §c-§x§9§c§0§0§f§b%.0f%%\n",Math.abs(critDamage)*100.0));
		}
		
		if(regenerationBonus > 0) {
			lore.add(String.format("§6❤ Regeneration Bonus: §a+§c%.0f%%\n",regenerationBonus));
		}else if(regenerationBonus < 0) {
			lore.add(String.format("§6❤ Regeneration Bonus: §c-%.0f%%\n",Math.abs(regenerationBonus)));
		}
		
		if(moveSpeed > 0) {
			lore.add(String.format("§6☄ Speed: §a+§c%.0f%%\n",moveSpeed*100.0));
		}else if(moveSpeed < 0) {
			lore.add(String.format("§6☄ Speed: §c-%.0f%%\n",Math.abs(moveSpeed)*100.0));
		}

		if(projectileSpeed > 1)
			lore.add(String.format("§6Projectile Speed: §x§f§f§9§9§0§0+%.2f%%\n",(projectileSpeed-1)*100.0));
		else if(projectileSpeed < 1)
			lore.add(String.format("§6Projectile Speed: §x§f§f§9§9§0§0-%.2f%%\n",Math.abs((projectileSpeed-1)*100.0)));

		if(blastRadius > 0)
			lore.add(String.format("§6Blast Radius: §x§f§f§9§9§0§0%.2f §6Blocks\n",blastRadius));

		if(blastFalloff > 0.0)
			lore.add(String.format("§6Blast Falloff: §x§f§f§9§9§0§0%.2f%%\n", blastFalloff*100.0));
		
		/* Modifiers */
		int clockworkValue = container.getOrDefault(Main.modifierKeys.get( "clockwork"), PersistentDataType.INTEGER, 0);
		if(clockworkValue > 0)
			lore.add(String.format("§6Clockwork %s: -%.2f%% cooldown\n", Main.getRomanNumber(clockworkValue), (clockworkValue*15.0) ));

		if(container.has(Main.attributeKeys.get( "additionalLore"), PersistentDataType.STRING)) {	
			lore.addAll(Arrays.asList(container.get(Main.attributeKeys.get( "additionalLore"), PersistentDataType.STRING).split(", ")));
		}
		
		int rarity = container.getOrDefault(Main.attributeKeys.get( "rarity"), PersistentDataType.DOUBLE, 1.0).intValue();
		int itemType = container.getOrDefault(Main.attributeKeys.get( "itemType"), PersistentDataType.DOUBLE, 0.0).intValue();

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

		double baseDamage = container.getOrDefault(Main.attributeKeys.get( "baseDamage"), PersistentDataType.DOUBLE, 0.0);
		double baseAttackSpeed = container.getOrDefault(Main.attributeKeys.get( "baseAttackSpeed"), PersistentDataType.DOUBLE, 0.0);
		double attackReachBonusRaw = container.getOrDefault(Main.attributeKeys.get( "attackReachBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double attackSpeedBaseMult = container.getOrDefault(Main.attributeKeys.get( "attackSpeedBaseMult"), PersistentDataType.DOUBLE, 0.0);
		double healthBonusRaw = container.getOrDefault(Main.attributeKeys.get( "healthBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double absorptionBonusRaw = container.getOrDefault(Main.attributeKeys.get( "absorptionBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double intelligenceBonusRaw = container.getOrDefault(Main.attributeKeys.get( "intelligenceBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double intelligenceScaling = container.getOrDefault(Main.attributeKeys.get( "intelligenceScaling"), PersistentDataType.DOUBLE, 0.0);
		double strengthBonusRaw = container.getOrDefault(Main.attributeKeys.get( "strengthBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double strengthScaling = container.getOrDefault(Main.attributeKeys.get( "strengthScaling"), PersistentDataType.DOUBLE, 0.0);
		double dexterityBonusRaw = container.getOrDefault(Main.attributeKeys.get( "dexterityBonusRaw"), PersistentDataType.DOUBLE, 0.0);
		double dexterityScaling = container.getOrDefault(Main.attributeKeys.get( "dexterityScaling"), PersistentDataType.DOUBLE, 0.0);
		//Independent Stats
		double critChance = container.getOrDefault(Main.attributeKeys.get( "critChance"), PersistentDataType.DOUBLE, 0.0);
		double critDamage = container.getOrDefault(Main.attributeKeys.get( "critDamage"), PersistentDataType.DOUBLE, 0.0);
		double regenerationBonus = container.getOrDefault(Main.attributeKeys.get( "regenerationBonus"), PersistentDataType.DOUBLE, 0.0);
		double moveSpeed = container.getOrDefault(Main.attributeKeys.get( "moveSpeed"), PersistentDataType.DOUBLE, 0.0);
		//Mining Stats
		double miningSpeed = container.getOrDefault(Main.attributeKeys.get( "miningSpeed"), PersistentDataType.DOUBLE, 0.0);
		double miningHardness = container.getOrDefault(Main.attributeKeys.get( "miningHardness"), PersistentDataType.DOUBLE, 0.0);
		double miningFortune = container.getOrDefault(Main.attributeKeys.get( "miningFortune"), PersistentDataType.DOUBLE, 0.0);
		double toolType = container.getOrDefault(Main.attributeKeys.get( "toolType"), PersistentDataType.DOUBLE, 0.0);
		//Projectile Stats
		double projectileSpeed = container.getOrDefault(Main.attributeKeys.get( "projectileSpeed"), PersistentDataType.DOUBLE, 1.0);
		double blastRadius = container.getOrDefault(Main.attributeKeys.get( "blastRadius"), PersistentDataType.DOUBLE, 0.0);
		double blastFalloff = container.getOrDefault(Main.attributeKeys.get( "blastFalloff"), PersistentDataType.DOUBLE, 0.0);

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
			lore.add(String.format("§6⛏ Mining Speed: §a+§x§0§0§a§1§f§b%.0f\n",miningSpeed));
			attributes.put("MiningSpeed", attributes.get("MiningSpeed") + miningSpeed);
		}else if(miningSpeed < 0) {
			lore.add(String.format("§6⛏ Mining Speed: §c-§x§0§0§a§1§f§b%.0f\n",Math.abs(miningSpeed)));
			attributes.put("MiningSpeed", attributes.get("MiningSpeed") + miningSpeed);
		}
		//Mining Hardness
		if(miningHardness > 0) {
			lore.add(String.format("§6⛏ Mining Tier: §a+§x§0§0§a§1§f§b%.0f\n",miningHardness));
			attributes.put("ToolHardness", attributes.get("ToolHardness") + miningHardness);
		}else if(miningHardness < 0) {
			lore.add(String.format("§6⛏ Mining Tier: §c-§x§0§0§a§1§f§b%.0f\n",Math.abs(miningHardness)));
			attributes.put("ToolHardness", attributes.get("ToolHardness") + miningHardness);
		}
		//Mining Fortune
		if(miningFortune > 0) {
			lore.add(IridiumColorAPI.process(String.format("§6♣ Mining Fortune: §a+<GRADIENT:059600>%.0f</GRADIENT:00F794>\n",miningFortune)));
			attributes.put("MiningFortune", attributes.get("MiningFortune") + miningFortune);
		}else if(miningFortune < 0) {
			lore.add(String.format("§6♣ Mining Fortune: §c-<GRADIENT:059600>%.0f</GRADIENT:00F794>f\n",Math.abs(miningFortune)));
			attributes.put("MiningFortune", attributes.get("MiningFortune") + miningFortune);
		}
		//Tool Type
		if(toolType > 0) {
			attributes.put("ToolID", toolType);
		}
		//Health
		if(healthBonusRaw > 0) {
			attributes.put("MaxHealth", attributes.get("MaxHealth") + healthBonusRaw);
			lore.add(String.format("§6❤ Maximum Health: §a+§c%.0f\n",healthBonusRaw));
		}else if(healthBonusRaw < 0) {
			attributes.put("MaxHealth", attributes.get("MaxHealth") + healthBonusRaw);
			lore.add(String.format("§6❤ Maximum Health: §c-§c%.0f\n",Math.abs(healthBonusRaw)));
		}
		//Absorption (defense)
		if(absorptionBonusRaw > 0) {
			attributes.put("Absorption", attributes.get("Absorption") - ((attributes.get("Absorption")*absorptionBonusRaw)/100.0) + absorptionBonusRaw);
			lore.add(String.format("§6⛨ Absorption: §a+§a%.2f%%\n",absorptionBonusRaw));
		}else if(absorptionBonusRaw < 0) {
			attributes.put("Absorption", attributes.get("Absorption") - ((attributes.get("Absorption")*absorptionBonusRaw)/100.0) + absorptionBonusRaw);
			lore.add(String.format("§6⛨ Absorption: §c-§a%.2f%%\n",Math.abs(absorptionBonusRaw)));
		}
		//Intelligence
		if(intelligenceBonusRaw > 0) {
			attributes.put("Intelligence", attributes.get("Intelligence") + intelligenceBonusRaw);
			lore.add(String.format("§6★ Intelligence: §a+§b%.0f\n",intelligenceBonusRaw));
		}else if(intelligenceBonusRaw < 0) {
			attributes.put("Intelligence", attributes.get("Intelligence") + intelligenceBonusRaw);
			lore.add(String.format("§6★ Intelligence: §c-§b%.0f\n",Math.abs(intelligenceBonusRaw)));
		}
		
		if(intelligenceScaling > 0) {
			attributes.put("IntelligenceScaling", attributes.get("IntelligenceScaling") + intelligenceScaling);
			lore.add(String.format("§6★ Intelligence Scaling - §b%s (%.2f)\n",ScalingToLetter(intelligenceScaling),intelligenceScaling));
		}
		//Strength
		if(strengthBonusRaw > 0) {
			attributes.put("Strength", attributes.get("Strength") + strengthBonusRaw);
			lore.add(String.format("§6Ʃ Strength: §a+§x§2§5§8§e§0§0%.0f\n",strengthBonusRaw));
		}else if(strengthBonusRaw < 0) {
			attributes.put("Strength", attributes.get("Strength") + strengthBonusRaw);
			lore.add(String.format("§6Ʃ Strength: §c-§x§2§5§8§e§0§0%.0f\n",Math.abs(strengthBonusRaw)));
		}
		
		if(strengthScaling > 0) {
			attributes.put("StrengthScaling", attributes.get("StrengthScaling") + strengthScaling);
			lore.add(String.format("§6Ʃ Strength Scaling - §x§2§5§8§e§0§0%s (%.2f)\n",ScalingToLetter(strengthScaling),strengthScaling));
		}
		//Dexterity
		if(dexterityBonusRaw > 0) {
			attributes.put("Dexterity", attributes.get("Dexterity") + dexterityBonusRaw);
			lore.add(String.format("§6☠ Dexterity: §a+§x§d§8§0§0§6§8%.0f\n",dexterityBonusRaw));
		}else if(dexterityBonusRaw < 0) {
			attributes.put("Dexterity", attributes.get("Dexterity") + dexterityBonusRaw);
			lore.add(String.format("§6☠ Dexterity: §c-§x§d§8§0§0§6§8%.0f\n",Math.abs(dexterityBonusRaw)));
		}
		
		if(dexterityScaling > 0) {
			attributes.put("DexterityScaling", attributes.get("DexterityScaling") + dexterityScaling);
			lore.add(String.format("§6☠ Dexterity Scaling - §x§d§8§0§0§6§8%s (%.2f)\n",ScalingToLetter(dexterityScaling),dexterityScaling));
		}
		
		if(critChance > 0) {
			attributes.put("CritChance", attributes.get("CritChance") + critChance);
			lore.add(String.format("§6☠ Crit Chance: §a+§x§f§b§0§0§d§3%.0f%%\n",critChance*100.0));
		}else if(critChance < 0) {
			attributes.put("CritChance", attributes.get("CritChance") + critChance);
			lore.add(String.format("§6☠ Crit Chance: §c-§x§f§b§0§0§d§3%.0f%%\n",Math.abs(critChance*100.0)));
		}
		
		if(critDamage > 0) {
			attributes.put("CritDamage", attributes.get("CritDamage") + critDamage);
			lore.add(String.format("§6☠ Crit Damage: §a+§x§9§c§0§0§f§b%.0f%%\n",critDamage*100.0));
		}else if(critDamage < 0) {
			attributes.put("CritDamage", attributes.get("CritDamage") + critDamage);
			lore.add(String.format("§6☠ Crit Damage: §c-§x§9§c§0§0§f§b%.0f%%\n",Math.abs(critDamage*100.0)));
		}

		if(regenerationBonus > 0) {
			attributes.put("RegenerationBonus", attributes.get("RegenerationBonus") + regenerationBonus);
			lore.add(String.format("§6❤ Regeneration Bonus: §a+§c%.0f%%\n",regenerationBonus*100.0));
		}else if(regenerationBonus < 0) {
			attributes.put("RegenerationBonus", attributes.get("RegenerationBonus") + regenerationBonus);
			lore.add(String.format("§6❤ Regeneration Bonus: §c-%.0f%%\n",Math.abs(regenerationBonus*100.0)));
		}
		
		if(moveSpeed > 0) {
			attributes.put("Speed", attributes.get("Speed") + moveSpeed);
			lore.add(String.format("§6☄ Speed: §a+§c%.0f%%\n",moveSpeed*100.0));
		}else if(moveSpeed < 0) {
			attributes.put("Speed", attributes.get("Speed") + moveSpeed);
			lore.add(String.format("§6☄ Speed: §c-%.0f%%\n",Math.abs(moveSpeed*100.0)));
		}

		if(projectileSpeed > 1)
			lore.add(String.format("§6Projectile Speed: §x§f§f§9§9§0§0+%.2f%%\n",(projectileSpeed-1)*100.0));
		else if(projectileSpeed < 1)
			lore.add(String.format("§6Projectile Speed: §x§f§f§9§9§0§0-%.2f%%\n",Math.abs((projectileSpeed-1)*100.0)));

		if(blastRadius > 0)
			lore.add(String.format("§6Blast Radius: §x§f§f§9§9§0§0%.2f §6Blocks\n",blastRadius));

		if(blastFalloff > 0.0)
			lore.add(String.format("§6Blast Falloff: §x§f§f§9§9§0§0%.2f%%\n", blastFalloff*100.0));

		/* Modifiers */
		int clockworkValue = container.getOrDefault(Main.modifierKeys.get( "clockwork"), PersistentDataType.INTEGER, 0);
		if(clockworkValue > 0)
			lore.add(String.format("§6Clockwork %s: -%.2f%% cooldown\n", Main.getRomanNumber(clockworkValue), (clockworkValue*15.0) ));

		if(container.has(Main.attributeKeys.get( "additionalLore"), PersistentDataType.STRING)) {	
			lore.addAll(Arrays.asList(container.get(Main.attributeKeys.get( "additionalLore"), PersistentDataType.STRING).split(", ")));
		}
		
		int rarity = container.getOrDefault(Main.attributeKeys.get( "rarity"), PersistentDataType.DOUBLE, 1.0).intValue();
		int itemType = container.getOrDefault(Main.attributeKeys.get( "itemType"), PersistentDataType.DOUBLE, 0.0).intValue();

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
		HashMap<UUID, HashMap<String, Double>> playerAttributes = Main.getAttributes();
		HashMap<String, Double> attributes = CustomAttributes.defaultStats();

		if(p.getInventory().getItem(e.getNewSlot()) != null) {
			p.setCooldown(p.getInventory().getItem(e.getNewSlot()).getType(), 4);
			CustomAttributes.giveItemStats(p.getInventory().getItem(e.getNewSlot()),attributes);
		}
		new BukkitRunnable(){public void run(){//Start of Delay
		getUpdatedPlayerAttributes(p, attributes, false);
		playerAttributes.put(p.getUniqueId(), attributes);
		}}.runTaskLater(Main.getInstance(), 1);
	}
	@EventHandler
	public void onInventoryMoved(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player p))
			return;
		if(e.getAction() == InventoryAction.NOTHING)
			return;
		if(e.getSlot() == 40)
		{
			ItemStack item = e.getCursor();
			if(item != null) {
				ItemMeta meta = item.getItemMeta();
				if (meta != null) {
					PersistentDataContainer container = meta.getPersistentDataContainer();
					if (!(container.has(Main.attributeKeys.get( "canOffhand"), PersistentDataType.DOUBLE))) {
						p.getWorld().dropItem(p.getLocation(),item);
						e.setCancelled(true);
						return;
					}
				}
			}
		}


		HashMap<UUID, HashMap<String, Double>> playerAttributes = Main.getAttributes();
		HashMap<String, Double> attributes = CustomAttributes.defaultStats();
		new BukkitRunnable(){public void run(){//Start of Delay
			getUpdatedPlayerAttributes(p, attributes, true);
			playerAttributes.put(p.getUniqueId(), attributes);
		}}.runTaskLater(Main.getInstance(), 1);
	}
	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClose(InventoryCloseEvent e) {
		ItemStack offhand = e.getPlayer().getInventory().getItemInOffHand();
		if(offhand.getType()== Material.AIR || offhand == null) {
			return;
		}
		ItemMeta meta = offhand.getItemMeta();
		if (meta == null)
			return;

		PersistentDataContainer container = meta.getPersistentDataContainer();
		if (!(container.has(Main.attributeKeys.get( "canOffhand"), PersistentDataType.DOUBLE))) {
			e.getPlayer().getInventory().setItemInOffHand(null);
			e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), offhand);
		}
	}
	@EventHandler
	public void onPlayerOffhand(PlayerSwapHandItemsEvent e){
		ItemStack offhand = e.getOffHandItem();

		if(offhand == null)
			return;
		ItemMeta meta = offhand.getItemMeta();
		if(meta == null)
			return;

		PersistentDataContainer container = meta.getPersistentDataContainer();
		if (!(container.has(Main.attributeKeys.get( "canOffhand"), PersistentDataType.DOUBLE))) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onArmorChange(ArmorEquipEvent e) {
		Player p = e.getPlayer();
		HashMap<UUID, HashMap<String, Double>> playerAttributes = Main.getAttributes();
		HashMap<String, Double> attributes = CustomAttributes.defaultStats();
		
		new BukkitRunnable(){public void run(){//Start of Delay
			getUpdatedPlayerAttributes(p, attributes, true);
		playerAttributes.put(p.getUniqueId(), attributes);
		}}.runTaskLater(Main.getInstance(), 1);
	}
	@EventHandler
	public void onPickupEvent(EntityPickupItemEvent e) {
		if(!(e.getEntity() instanceof Player p))
			return;
		ItemStack item = e.getItem().getItemStack();
		updateItem(item);
		new BukkitRunnable(){public void run(){
			if(p.getInventory().getItemInMainHand().equals(item)) {
				HashMap<UUID, HashMap<String, Double>> playerAttributes = Main.getAttributes();
				HashMap<String, Double> attributes = CustomAttributes.defaultStats();

				getUpdatedPlayerAttributes(p, attributes, true);
				playerAttributes.put(p.getUniqueId(), attributes);
			}
		}}.runTaskLater(Main.getInstance(), 1);
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
		if(container.has(Main.attributeKeys.get( "itemAction"), PersistentDataType.DOUBLE))
			{e.setCancelled(true);return;}

		int itemType = container.has(Main.attributeKeys.get( "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(Main.attributeKeys.get( "itemType"), PersistentDataType.DOUBLE)) : 1;
		if(itemType == 200 || (itemType > 0 && itemType < 100))
		{
			HashMap<UUID, HashMap<String, Double>> playerAttributes = Main.getAttributes();
			HashMap<String, Double> attributes = CustomAttributes.defaultStats();

			new BukkitRunnable(){public void run(){//Start of Delay
				getUpdatedPlayerAttributes(p, attributes, false);
				playerAttributes.put(p.getUniqueId(), attributes);
			}}.runTaskLater(Main.getInstance(), 1);
		}
	}
	public static String[] getPlayerStatsFormat(Player player)
	{
		HashMap<UUID, HashMap<String, Double>> playerAttributes = Main.getAttributes();
		UUID uuid = player.getUniqueId();

		ArrayList<String> output = new ArrayList<String>();
		
		if(playerAttributes.get(uuid).containsKey("BaseDamage"))
			output.add(String.format("§6Damage: §x§f§f§6§b§0§0%.0f", playerAttributes.get(uuid).get("BaseDamage")));
		
		if(playerAttributes.get(uuid).containsKey("AttackSpeedBonus") && playerAttributes.get(uuid).get("AttackSpeedBonus")!=1.0)
			output.add(String.format("§6Attack Speed: §a+§x§f§f§9§9§0§0%.0f%%", playerAttributes.get(uuid).get("AttackSpeedBonus")*100.0));
		
		if(playerAttributes.get(uuid).containsKey("MaxHealth") && playerAttributes.get(uuid).get("MaxHealth")>0.0)
			output.add(String.format("§6❤ Maximum Health: §a§c%.0f", playerAttributes.get(uuid).get("MaxHealth")));
		if(playerAttributes.get(uuid).containsKey("Absorption") && playerAttributes.get(uuid).get("Absorption")!=0.0)
			output.add(String.format("§6⛨ Absorption: §a§a%.2f%%", playerAttributes.get(uuid).get("Absorption")));
		if(playerAttributes.get(uuid).containsKey("Intelligence") && playerAttributes.get(uuid).get("Intelligence")!=0.0)
			output.add(String.format("§6★ Intelligence: §a§b%.0f", playerAttributes.get(uuid).get("Intelligence")));
		if(playerAttributes.get(uuid).containsKey("IntelligenceScaling") && playerAttributes.get(uuid).get("IntelligenceScaling")!=0.0)
			output.add(String.format("§6★ Intelligence Scaling - §b(%.2f)", playerAttributes.get(uuid).get("IntelligenceScaling")));
		if(playerAttributes.get(uuid).containsKey("Strength") && playerAttributes.get(uuid).get("Strength")!=0.0)
			output.add(String.format("§6Ʃ Strength: §a§x§2§5§8§e§0§0%.0f", playerAttributes.get(uuid).get("Strength")));
		if(playerAttributes.get(uuid).containsKey("StrengthScaling") && playerAttributes.get(uuid).get("StrengthScaling")!=0.0)
			output.add(String.format("§6Ʃ Strength Scaling - §x§2§5§8§e§0§0 (%.2f)", playerAttributes.get(uuid).get("StrengthScaling")));
		if(playerAttributes.get(uuid).containsKey("Dexterity") && playerAttributes.get(uuid).get("Dexterity")!=0.0)
			output.add(String.format("§6☠ Dexterity: §a§x§d§8§0§0§6§8%.0f", playerAttributes.get(uuid).get("Dexterity")));
		if(playerAttributes.get(uuid).containsKey("DexterityScaling") && playerAttributes.get(uuid).get("DexterityScaling")!=0.0)
			output.add(String.format("§6☠ Dexterity Scaling - §x§d§8§0§0§6§8 (%.2f)", playerAttributes.get(uuid).get("DexterityScaling")));
		
		if(playerAttributes.get(uuid).containsKey("CritChance") && playerAttributes.get(uuid).get("CritChance")!=0.0)
			output.add(String.format("§6☠ Crit Chance: §a§x§f§b§0§0§d§3%.0f%%", playerAttributes.get(uuid).get("CritChance")*100.0));
		if(playerAttributes.get(uuid).containsKey("CritDamage") && playerAttributes.get(uuid).get("CritDamage")!=0.0)
			output.add(String.format("§6☠ Crit Damage: §c+§x§9§c§0§0§f§b%.0f%%", playerAttributes.get(uuid).get("CritDamage")*100.0));
		if(playerAttributes.get(uuid).containsKey("RegenerationBonus") && playerAttributes.get(uuid).get("RegenerationBonus")!=0.0)
			output.add(String.format("§6❤ Regeneration Bonus: §a+§c%.0f%%", playerAttributes.get(uuid).get("RegenerationBonus")*100.0));
		if(playerAttributes.get(uuid).containsKey("Speed") && playerAttributes.get(uuid).get("Speed")!=0.0)
			output.add(String.format("§6☄ Speed: §a+§c%.0f%%", playerAttributes.get(uuid).get("Speed")*100.0));
		
		if(playerAttributes.get(uuid).containsKey("MiningSpeed") && playerAttributes.get(uuid).get("MiningSpeed")!=1.0)
			output.add(String.format("§6⛏ Mining Speed: §a§x§0§0§a§1§f§b%.0f", playerAttributes.get(uuid).get("MiningSpeed")));
		if(playerAttributes.get(uuid).containsKey("ToolHardness") && playerAttributes.get(uuid).get("ToolHardness")!=0.0)
			output.add(String.format("§6⛏ Mining Tier: §a§x§0§0§a§1§f§b%.0f", playerAttributes.get(uuid).get("ToolHardness")));
		if(playerAttributes.get(uuid).containsKey("MiningFortune") && playerAttributes.get(uuid).get("MiningFortune")>=10.0)
			output.add(IridiumColorAPI.process(String.format("§6♣ Mining Fortune: §c-<GRADIENT:059600>%.0f</GRADIENT:00F794>f", playerAttributes.get(uuid).get("MiningFortune"))));
		
		return output.toArray(new String[0]);
	}
	
	public static void getUpdatedPlayerAttributes(Player p, HashMap<String, Double> attributes, boolean checkWeapon)
	{
		ArrayList<String> usedTalismanFamily = new ArrayList<>();
		UUID uuid = p.getUniqueId();
		int talismans = 0;
		for(ItemStack checkItem : p.getInventory()) {
			if(checkItem == null)
				continue;
			ItemMeta meta = checkItem.getItemMeta();
			if(meta == null)
				continue;

			PersistentDataContainer container = meta.getPersistentDataContainer();
			int itemType = container.has(Main.attributeKeys.get( "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(Main.attributeKeys.get( "itemType"), PersistentDataType.DOUBLE)) : 1;
			if(checkWeapon && p.getInventory().getItemInMainHand().equals(checkItem) && itemType < 100)
			{
				CustomAttributes.giveItemStats(checkItem,attributes);
			}
			else if(itemType == 200)
			{
				if(talismans > 4)
					continue;
				if(!container.has(Main.attributeKeys.get( "talismanFamily"), PersistentDataType.STRING))
					continue;

				String family = container.get(Main.attributeKeys.get( "talismanFamily"), PersistentDataType.STRING);
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
			int itemType = container.has(Main.attributeKeys.get( "itemType"), PersistentDataType.DOUBLE) ? (int) Math.round(container.get(Main.attributeKeys.get( "itemType"), PersistentDataType.DOUBLE)) : 1;
			if(itemType > 100)
				CustomAttributes.giveItemStats(checkItem,attributes);
		}

		attributes.put("MaxMana", attributes.get("MaxMana") + attributes.get("Intelligence"));

		for(AttributeModifier modifier : p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getModifiers()) {
			p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(modifier);
		}
		if(attributes.containsKey("Speed")) {//Remove all speed modifiers except the speed stat.
			p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(new AttributeModifier("GENERIC_MOVEMENT_SPEED",attributes.get("Speed"),
					AttributeModifier.Operation.MULTIPLY_SCALAR_1));
		}
		
		//Skill shittoids
		if(attributes.containsKey("BaseDamage"))
			attributes.replace("BaseDamage", (5.0 * (Skills.getLevel(uuid, "Combat"))-1) + attributes.get("BaseDamage"));
		if(attributes.containsKey("Strength"))
			attributes.replace("Strength", (5.0 * (Skills.getLevel(uuid, "Foraging"))-1) + attributes.get("Strength"));
		if(attributes.containsKey("Absorption"))
			attributes.replace("Absorption", (1.0 * (Skills.getLevel(uuid, "Mining"))-1) + attributes.get("Absorption"));
		
		
			//Check for maximums
		HashMap<String, Double> attr = Main.getAttributes().get(uuid);

		if(attr == null) return;

		if(attr.containsKey("Health") && attr.get("Health") > attributes.get("MaxHealth"))
			attributes.put("Health", attributes.get("MaxHealth"));
		else
			attributes.put("Health", attr.get("Health"));

		if(attr.containsKey("Mana") && attr.get("Mana") > attributes.get("MaxMana"))
			attributes.put("Mana", attributes.get("MaxMana"));
		else
			attributes.put("Mana", attr.get("Mana"));
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
			case 21 -> {return "Spell";}
			case 100 -> {return "Helmet";}
			case 101 -> {return "Chestplate";}
			case 102 -> {return "Leggings";}
			case 103 -> {return "Boots";}
			case 200 -> {return "Talisman";}
			default -> {return "";}
		}
	}
	public static double getDamageModified(UUID uuid, boolean isCrit)
	{
		double customDamage;
		if(Bukkit.getPlayer(uuid) != null) { //if uuid belongs to a player
			double baseDMG = Main.getAttributes().get(uuid).getOrDefault("BaseDamage", 5.0);
			double dex = Main.getAttributes().get(uuid).containsKey("Dexterity") ? Math.max(Main.getAttributes().get(uuid).get("Dexterity"), 0.0) : 0.0;
			double dexScaling = Main.getAttributes().get(uuid).getOrDefault("DexterityScaling", 0.0);

			double str = Main.getAttributes().get(uuid).containsKey("Strength") ? Math.max(Main.getAttributes().get(uuid).get("Strength"), 0.0) : 0.0;
			double strScaling = Main.getAttributes().get(uuid).getOrDefault("StrengthScaling", 0.0);

			double intel = Main.getAttributes().get(uuid).containsKey("Intelligence") ? Math.max(Main.getAttributes().get(uuid).get("Intelligence"), 0.0) : 0.0;
			double intelScaling = Main.getAttributes().get(uuid).getOrDefault("IntelligenceScaling", 0.0);
			//damage formula
			customDamage = baseDMG * Math.pow((1 + (dex) / 100.0), dexScaling) *
					Math.pow((1 + (str) / 100.0), strScaling) * Math.pow((1 + (intel) / 100.0), intelScaling);

			if (isCrit)
				customDamage *= (Main.getAttributes().get(uuid).getOrDefault("CritDamage", 0.0)) + 1.35;
		}
		else {
			double baseDMG = EntityHandling.entityAttributes.get(uuid).getOrDefault("BaseDamage", 5.0);
			double dex = EntityHandling.entityAttributes.get(uuid).getOrDefault("Dexterity", 0.0);
			double dexScaling = EntityHandling.entityAttributes.get(uuid).getOrDefault("DexterityScaling", 0.0);

			double str = EntityHandling.entityAttributes.get(uuid).getOrDefault("Strength", 0.0);
			double strScaling = EntityHandling.entityAttributes.get(uuid).getOrDefault("StrengthScaling", 0.0);

			double intel = EntityHandling.entityAttributes.get(uuid).getOrDefault("Intelligence", 0.0);
			double intelScaling = EntityHandling.entityAttributes.get(uuid).getOrDefault("IntelligenceScaling", 0.0);
			//damage formula
			customDamage = baseDMG * Math.pow((1 + (dex) / 100.0), dexScaling) *
					Math.pow((1 + (str) / 100.0), strScaling) * Math.pow((1 + (intel) / 100.0), intelScaling);

			if (isCrit)
				customDamage *= (EntityHandling.entityAttributes.get(uuid).getOrDefault("CritDamage", 0.0)) + 1.35;
		}
		return customDamage;
	}
}