package markiplites.SoyBlock;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class HUDTimer implements Listener {
	public static HashMap<UUID, Double> playerStatusCooldown = new HashMap<>();
	public static HashMap<UUID, Object[]> playerStatusAbility = new HashMap<>();
	public static void run(final Plugin plugin) {
		new BukkitRunnable(){
			@Override
			public void run()
			{
				for (Player p : plugin.getServer().getOnlinePlayers()) {
					UUID uuid = p.getUniqueId();
					if (!Main.getAttributes().containsKey(uuid))
						continue;
					long time = System.currentTimeMillis();
					double currentHealth = Main.getAttributes().get(uuid).get("Health");
					double maxHealth = Main.getAttributes().get(uuid).get("MaxHealth");
					double currentMana = Main.getAttributes().get(uuid).get("Mana");
					double maxMana = Main.getAttributes().get(uuid).get("MaxMana");
					double regeneration = maxHealth * 0.0003 * (1.0 + (Main.getAttributes().get(uuid).getOrDefault("RegenerationBonus", 0.0)));

					currentMana += maxMana * 0.00075;//regen 1.5% mana per second

					currentHealth += regeneration;//regen .6% health per second (times those bonuses or smth)

					if (currentHealth > maxHealth)
						currentHealth = maxHealth;
					if (currentMana > maxMana)
						currentMana = maxMana;

					Main.getAttributes().get(uuid).put("Mana", currentMana);
					Main.getAttributes().get(uuid).put("Health", currentHealth);

					if (Main.getAttributes().get(uuid).get("Health") > 0.0) {
						p.setHealth(Main.getAttributes().get(uuid).get("Health") / Main.getAttributes().get(uuid).get("MaxHealth") * p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					} else {
						p.setHealth(0.0);
					}

					StringBuilder sb = new StringBuilder();

					sb.append("§c❤ ").append(String.format("%.0f", currentHealth)).append("/").append(String.format("%.0f", maxHealth));
					sb.append("    §b★ ").append(String.format("%.0f", currentMana)).append("/").append(String.format("%.0f", maxMana));

					if (playerStatusCooldown.containsKey(uuid)) {
						if (playerStatusCooldown.get(uuid) > time) {
							sb.append("    ").append(String.format("§4Cooldown: %.1fs",
									(playerStatusCooldown.get(uuid) - time) / 1000.0));
						} else {
							playerStatusCooldown.remove(uuid);
						}
					}
					if(playerStatusAbility.containsKey(uuid)){
						if ((Long)playerStatusAbility.get(uuid)[1] > time) {
							sb.append("    ").append(playerStatusAbility.get(uuid)[0]);
						} else {
							playerStatusAbility.remove(uuid);
						}
					}
					BaseComponent[] message = new ComponentBuilder(sb.toString()).create();
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
				}
			}
		}.runTaskTimerAsynchronously(plugin, 20, 1);
	}

}