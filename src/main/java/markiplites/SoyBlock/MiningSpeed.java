package markiplites.SoyBlock;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerDigType;
import com.jeff_media.customblockdata.CustomBlockData;
import markiplites.SoyBlock.Lists.blockList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class MiningSpeed implements Listener{
    private final NamespacedKey blockIDKey;
    private final ProtocolManager protocolManager;
    Main mainPlugin;
    public MiningSpeed(Main main) {
        mainPlugin = main;
        protocolManager = ProtocolLibrary.getProtocolManager();
        interceptPackets();

		blockIDKey = new NamespacedKey(mainPlugin, "blockID");
    }
    private void interceptPackets()
    {
        protocolManager.addPacketListener(new PacketAdapter(mainPlugin, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event){
                PacketContainer packet = event.getPacket();
                EnumWrappers.PlayerDigType digType = packet.getPlayerDigTypes().getValues().get(0);
                //System.out.println("DigType: "+digType.name());
                Player p = event.getPlayer();
                
                if(!digType.equals(PlayerDigType.START_DESTROY_BLOCK)) {
					setFatigue(p, 0,-1);
                }
            }
        });
    }
    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent event)
    {
    	ItemStack itemPlaced = event.getItemInHand();
		ItemMeta meta = itemPlaced.getItemMeta();
		if(meta == null) return;
		PersistentDataContainer container = meta.getPersistentDataContainer();
		
		if(container.has(new NamespacedKey(Main.getInstance(), "baseDamage"), PersistentDataType.DOUBLE)
				|| container.has(new NamespacedKey(Main.getInstance(), "toolType"), PersistentDataType.DOUBLE)
				|| container.has(new NamespacedKey(Main.getInstance(), "itemAction"), PersistentDataType.DOUBLE))
			event.setCancelled(true);
		
		if(container.has(new NamespacedKey(Main.getInstance(), "blockID"), PersistentDataType.STRING))
		{
			PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), Main.getInstance());
			String id = container.get(new NamespacedKey(Main.getInstance(), "blockID"), PersistentDataType.STRING);
			customBlockData.set(blockIDKey, PersistentDataType.STRING, id);
		}
    }
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event)
    {
		event.setCancelled(true);
		event.setExpToDrop(0);
    }
    @EventHandler
    public void OnBlockHit(BlockDamageEvent event) {
    	Player eventPlayer = event.getPlayer();
		event.setInstaBreak(false);

		PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), Main.getInstance());
		String blockID;
		if(customBlockData.has(blockIDKey, PersistentDataType.STRING))
			blockID = customBlockData.get(blockIDKey, PersistentDataType.STRING);
		else
			blockID = event.getBlock().getType().toString();

		if(!blockList.block_attributes.containsKey(blockID))
			return;

		UUID uuid = eventPlayer.getUniqueId();
		double toolToUse = blockList.block_attributes.get(blockID).getOrDefault(attr.blockTool, -1.0);
		double toolUsed = Main.getAttributes().get(uuid).getOrDefault("ToolID", -1.0);

		if(toolToUse == -1.0 || toolToUse == toolUsed)
		{
			double blockHardness = blockList.block_attributes.get(blockID).getOrDefault(attr.blockHardness, 0.0);
			double toolHardness = Main.getAttributes().get(uuid).getOrDefault("ToolHardness", 0.0);
			if(toolHardness >= blockHardness)
			{
				MiningBlock(event, event.getBlock());
			}
			else
			{
				eventPlayer.sendMessage("§x§d§3§0§0§0§0This block is too hard to mine.");
				event.setCancelled(true);
			}
		}
		else
		{
			eventPlayer.sendMessage("§x§d§3§0§0§0§0You are using the wrong tool for this block.");
			event.setCancelled(true);
		}
    }

    public void MiningBlock(BlockDamageEvent event, Block block) {
        Player p = event.getPlayer();
		  //p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 4, true));
		  setFatigue(p, 200,4);
		  //Instead of getting the hash every time the breaking process is done, get it when it starts.
		  PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), Main.getInstance());
		  String blockID;
		  if(customBlockData.has(blockIDKey, PersistentDataType.STRING))
			  blockID = customBlockData.get(blockIDKey, PersistentDataType.STRING);
		  else
			  blockID = event.getBlock().getType().toString();

		  if(!blockList.block_attributes.containsKey(blockID))
			  return;

		  double toolSpeed = Main.getAttributes().get(p.getUniqueId()).getOrDefault("MiningSpeed", 1.0);
		  long temporaryBreakTime = Math.round(blockList.block_attributes.get(blockID).get(attr.blockDurability) / 9.0 / toolSpeed);
		  blockBreakingStages(event, p, 0, block,temporaryBreakTime, blockID);
		  blockBreakEffect(p, event.getBlock().getLocation().toVector(), 1);
    }

	public void blockBreakEffect(Player player, Vector vector, int step) {
		PacketContainer container = new PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
		container.getBlockPositionModifier().write(0, new BlockPosition(vector));
		container.getIntegers()
				.write(0, player.getEntityId())
				.write(1, step);
		execute(player, container);
	}
	void execute(Player receiver, PacketContainer container) {
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, container);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setFatigue(Player p, int duration, int amplitude)
	{
		if(amplitude == -1)
		{
			PacketContainer potionPacket = new PacketContainer(PacketType.Play.Server.REMOVE_ENTITY_EFFECT);
			potionPacket.getIntegers().write(0, p.getEntityId());
			potionPacket.getEffectTypes().write(0, PotionEffectType.SLOW_DIGGING);
			try {
				protocolManager.sendServerPacket(p, potionPacket);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		PacketContainer potionPacket = new PacketContainer(PacketType.Play.Server.ENTITY_EFFECT);
		potionPacket.getIntegers().write(0, p.getEntityId())
				.write(1, duration);
		potionPacket.getBytes().write(0, (byte) amplitude)
				.write(1, (byte) ((byte) 0x1 | ((byte) 0x0)));
		potionPacket.getEffectTypes().write(0, PotionEffectType.SLOW_DIGGING);
		try {
			protocolManager.sendServerPacket(p, potionPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public void blockBreakingStages(BlockDamageEvent event, Player p, int stage, Block block, long temporaryBreakTime, String blockID)
    {
		UUID uuid = p.getUniqueId();
		BukkitScheduler scheduler = mainPlugin.getServer().getScheduler();
		blockBreakEffect(p, event.getBlock().getLocation().toVector(), stage);
		scheduler.scheduleSyncDelayedTask(mainPlugin, () -> {
			if (stage < 9) {
				blockBreakEffect(p, event.getBlock().getLocation().toVector(), stage);
				blockBreakingStages(event, p, stage + 1, block, temporaryBreakTime, blockID);
			} else {
				mainPlugin.getServer().getPluginManager().callEvent(new BlockBreakEvent(event.getBlock(), event.getPlayer()));
				//Block Breaking shit
				block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation(), 20, 0.3, 0.1, 0.3, block.getType().createBlockData());
				block.setType(Material.AIR);

				if (blockList.block_loot.containsKey(blockID)) {
					double miningFortune = Main.getAttributes().get(uuid).getOrDefault("MiningFortune", 0.0);
					int blockExp = (int) Math.round(blockList.block_attributes.get(blockID).getOrDefault(attr.blockExp, 0.0));
					p.giveExp(blockExp);
					for (String itemName : blockList.block_loot.get(blockID)) {
						ItemStack item = ItemListHandler.generateItem(itemName);
						if (item == null) continue;

						if (item.getMaxStackSize() != 1) {
							int miningMult = (int) Math.floor((miningFortune / 100.0) + 1.0);
							if (Math.random() < (miningFortune / 100.0) - Math.floor(miningFortune / 100.0))
								miningMult += 1;

							item.setAmount(item.getAmount() * miningMult);
						}
						if (p.getInventory().firstEmpty() != -1)//If player's inventory is not full
						{
							p.getInventory().addItem(item);
						} else {//Drop the item where the block was broken
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5, 0.5, 0.5), item);
						}
					}
				}
				setFatigue(p, 0, -1);
			}
		}, temporaryBreakTime);
	}
}