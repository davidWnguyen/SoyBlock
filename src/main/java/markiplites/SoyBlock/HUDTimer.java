package markiplites.SoyBlock;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.UUID;

public class HUDTimer implements Listener {
	public static void run(final Plugin plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
				() -> {
					for(Player p : plugin.getServer().getOnlinePlayers()) {
						UUID uuid = p.getUniqueId();
						if(!Main.getAttributes().containsKey(uuid))
							continue;

						double currentHealth = Main.getAttributes().get(uuid).get("Health");
						double maxHealth = Main.getAttributes().get(uuid).get("MaxHealth");
						double currentMana = Main.getAttributes().get(uuid).get("Mana");
						double maxMana = Main.getAttributes().get(uuid).get("MaxMana");
						double regeneration = maxHealth * 0.0003 * (1.0 + (Main.getAttributes().get(uuid).getOrDefault("RegenerationBonus", 0.0)));

						currentMana += maxMana * 0.0005;//regen 1% mana per second

						currentHealth += regeneration;//regen .6% health per second (times those bonuses or smth)

						if(currentHealth > maxHealth)
							currentHealth = maxHealth;
						if(currentMana > maxMana)
							currentMana = maxMana;

						Main.getAttributes().get(uuid).put("Mana", currentMana);
						Main.getAttributes().get(uuid).put("Health", currentHealth);

						if(Main.getAttributes().get(uuid).get("Health") > 0.0) {
							p.setHealth(Main.getAttributes().get(uuid).get("Health")/Main.getAttributes().get(uuid).get("MaxHealth") * p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						}else {
							p.setHealth(0.0);
						}

						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("§c❤ " + String.format("%.0f", currentHealth) + "/" + String.format("%.0f", maxHealth) +"       §b★ " + String.format("%.0f", currentMana) + "/" + String.format("%.0f", maxMana)).create() );
					}
				}, 0, 1);
	}

}