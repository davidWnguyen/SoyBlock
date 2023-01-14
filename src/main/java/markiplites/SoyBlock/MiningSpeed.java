package markiplites.SoyBlock;
import java.lang.reflect.Field;
import java.util.ArrayList;

import com.comphenix.protocol.wrappers.BlockPosition;
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
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerDigType;
import com.jeff_media.customblockdata.CustomBlockData;
import com.jeff_media.morepersistentdatatypes.DataType;
import org.bukkit.util.Vector;

public class MiningSpeed implements Listener{
    //Arrays that tell whether a player is mining a block
    ArrayList<Player> playerMiningBlock = new ArrayList<>();
    ArrayList<Block> blockBeingMined = new ArrayList<>();
    private final NamespacedKey blockHardnessKey;
    private final NamespacedKey blockDurabilityKey;
    private final NamespacedKey blockToolKey;
    private final NamespacedKey blockExpKey;
    private final NamespacedKey blockLootKey;
    private final ProtocolManager protocolManager;
    Main mainPlugin;
    public MiningSpeed(Main main) {
        mainPlugin = main;
        protocolManager = ProtocolLibrary.getProtocolManager();
        interceptPackets();
        
        blockHardnessKey = new NamespacedKey(mainPlugin, "blockHardness");
        blockDurabilityKey = new NamespacedKey(mainPlugin, "blockDurability");
        blockToolKey = new NamespacedKey(mainPlugin, "blockTool");
        blockExpKey = new NamespacedKey(mainPlugin, "blockExp");
        blockLootKey = new NamespacedKey(mainPlugin, "blockLoot");
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
                
                if(digType.equals(PlayerDigType.START_DESTROY_BLOCK)) {
                    playerMiningBlock.add(p);
                    blockBeingMined.add(null);
                } 
                else 
                {
                	int temp = 0;
                    for(Player player : playerMiningBlock) 
                    {
                        if(player == p)
                        {
                            playerMiningBlock.remove(player);
                            blockBeingMined.remove(temp);
                           
            				if(p.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
            					p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
            				}
                            break;
                        }
                        temp++;
                    }
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
		
		if(container.has(new NamespacedKey(Main.getInstance(), "baseDamage"), PersistentDataType.DOUBLE) || container.has(new NamespacedKey(Main.getInstance(), "toolType"), PersistentDataType.DOUBLE))
			event.setCancelled(true);
		
		if(container.has(new NamespacedKey(Main.getInstance(), "blockDurability"), PersistentDataType.DOUBLE))
		{
			double blockDurability = container.has(new NamespacedKey(Main.getInstance(), "blockDurability"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "blockDurability"), PersistentDataType.DOUBLE) : 20.0;
			double blockHardness = container.has(new NamespacedKey(Main.getInstance(), "blockHardness"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "blockHardness"), PersistentDataType.DOUBLE) : 0.0;
			double blockTool = container.has(new NamespacedKey(Main.getInstance(), "blockTool"), PersistentDataType.DOUBLE) ? container.get(new NamespacedKey(Main.getInstance(), "blockTool"), PersistentDataType.DOUBLE) : -1.0;
			int blockExp = container.has(new NamespacedKey(Main.getInstance(), "blockExp"), PersistentDataType.DOUBLE) ? (int)Math.round(container.get(new NamespacedKey(Main.getInstance(), "blockExp"), PersistentDataType.DOUBLE)) : 0;
			PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), Main.getInstance());
			customBlockData.set(blockDurabilityKey, PersistentDataType.DOUBLE, blockDurability);
			customBlockData.set(blockHardnessKey, PersistentDataType.DOUBLE, blockHardness);
			customBlockData.set(blockToolKey, PersistentDataType.DOUBLE, blockTool);
			customBlockData.set(blockExpKey, PersistentDataType.INTEGER, blockExp);
			if(container.has(new NamespacedKey(Main.getInstance(), "blockLoot"), DataType.asArrayList(DataType.ITEM_STACK)))
			{
				//PersistentDataType<byte[],ItemStack[]> ITEM_STACK_ARRAY; get to this later
				//Premise is that you will have a persistent item stack array give you info for block loot
				customBlockData.set(blockLootKey, DataType.asArrayList(DataType.ITEM_STACK), container.get(new NamespacedKey(Main.getInstance(), "blockLoot"), DataType.asArrayList(DataType.ITEM_STACK)));
			}
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
    	int temp = 0;
    	Player eventPlayer = event.getPlayer();
        for(Player player : playerMiningBlock) {
            if(player == eventPlayer) {
                playerMiningBlock.remove(player);
                blockBeingMined.remove(temp);
                break;
            }
            temp++;
        }
        if(!event.getInstaBreak()) {
            playerMiningBlock.add(eventPlayer);
            blockBeingMined.add(event.getBlock());
            PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), Main.getInstance());
            if(customBlockData.has(blockDurabilityKey, PersistentDataType.DOUBLE))
            {
            	double toolToUse = customBlockData.has(blockToolKey, PersistentDataType.DOUBLE) ? customBlockData.get(blockToolKey, PersistentDataType.DOUBLE) : -1.0;
            	double toolUsed = Main.getAttributes().get(eventPlayer).getOrDefault("ToolID", -1.0);

            	if(toolToUse == -1.0 || toolToUse == toolUsed)
            	{
                	double blockHardness = customBlockData.has(blockHardnessKey, PersistentDataType.DOUBLE) ? customBlockData.get(blockHardnessKey, PersistentDataType.DOUBLE) : 0.0;
                	double toolHardness = Main.getAttributes().get(eventPlayer).getOrDefault("ToolHardness", 0.0);
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
        }
    }
    public void MiningBlock(BlockDamageEvent event, Block block) {
        Player p = event.getPlayer();
        for(Player player : playerMiningBlock) {
              if(player == event.getPlayer()) {
                  p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 1000, 255, true));
                  
                  /*PlayerConnection connection = p.getHandle().playerConnection;
                  @SuppressWarnings("deprecation")
                  PacketPlayOutEntityEffect entityEffect = new PacketPlayOutEntityEffect(p.getEntityId(), new MobEffect(MobEffectList.fromId(PotionEffectType.SLOW_DIGGING.getId()), Integer.MAX_VALUE, 255, true, false));
                  connection.sendPacket(entityEffect);

				  PacketContainer potionPacket = new PacketContainer(PacketType.Play.Server.ENTITY_EFFECT);
				  potionPacket.getIntegers().write(0, p.getEntityId())
						  .write(1, Integer.MAX_VALUE);
				  potionPacket.getBytes().write(0, (byte) PotionEffectType.SLOW_DIGGING.getId())
						  .write(1, (byte) );
				  try {
					  protocolManager.sendServerPacket(p, potionPacket);
				  }catch(Exception e){

				  }
					*/
                  blockBreakingStages(event, p, 0, block);
              }
        }
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

    public void blockBreakingStages(BlockDamageEvent event, Player p, int stage, Block block)
    {
		final PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), Main.getInstance());
		double toolSpeed = Main.getAttributes().get(p).getOrDefault("MiningSpeed", 1.0);
		long temporaryBreakTime = Math.round(customBlockData.get(blockDurabilityKey, PersistentDataType.DOUBLE) / 9.0 / toolSpeed);
		BukkitScheduler scheduler = mainPlugin.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(mainPlugin, () -> {
			boolean foundPlayer = false;
			for(int i = 0; i < playerMiningBlock.size(); i++)
			{
				if(playerMiningBlock.get(i) == event.getPlayer() )
				{
					foundPlayer = true;
					if(blockBeingMined.get(i) == block)
					{
						blockBreakEffect(p, event.getBlock().getLocation().toVector(), stage);
						if(stage < 9)
						{
							blockBreakingStages(event, p, stage + 1, block);
						}
						else
						{
							mainPlugin.getServer().getPluginManager().callEvent(new BlockBreakEvent(event.getBlock(), event.getPlayer()));
							//Block Breaking shit
							block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation(), 1, 0.1, 0.1, 0.1, block.getType().createBlockData());
							event.getBlock().breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
							//da epic sphee
							PersistentDataContainer customBlockData1 = new CustomBlockData(event.getBlock(), Main.getInstance());
							if(customBlockData1.has(blockLootKey, DataType.asArrayList(DataType.ITEM_STACK)))
							{
								double blockTool = customBlockData1.has(new NamespacedKey(Main.getInstance(), "blockTool"), PersistentDataType.DOUBLE) ? customBlockData1.get(new NamespacedKey(Main.getInstance(), "blockTool"), PersistentDataType.DOUBLE) : -1.0;
								ArrayList<ItemStack> blockLootID = customBlockData1.get(blockLootKey, DataType.asArrayList(DataType.ITEM_STACK));
								if(blockLootID == null) return;
								double miningFortune = 0.0;
								if(blockTool == 1.0)
									miningFortune = Main.getAttributes().get(p).getOrDefault("MiningFortune", 0.0);

								int blockExp = customBlockData1.has(blockExpKey, PersistentDataType.INTEGER) ? customBlockData1.get(blockExpKey, PersistentDataType.INTEGER) : 0;
								p.giveExp(blockExp);
								for(ItemStack item: blockLootID)
								{
									//Mining fortune for items that can be stacked
									if(item.getMaxStackSize() != 1)
									{
										int miningMult = (int)Math.floor((miningFortune/100.0)+1.0);
										if(Math.random() < (miningFortune/100.0) - Math.floor(miningFortune/100.0))
											miningMult+=1;

										item.setAmount(item.getAmount()*miningMult);
									}
									if(p.getInventory().firstEmpty() != -1)//If player's inventory is not full
									{
										p.getInventory().addItem(item);
									}
									else
									{//Drop the item where the block was broken
										event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation().add(0.5,0.5,0.5), item);
									}
								}
							}
							if(p.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
							p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
							}
						}
					}
					break;
				}
			}
			if(p.hasPotionEffect(PotionEffectType.SLOW_DIGGING) && !foundPlayer) {
			p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
			}
		}, temporaryBreakTime);
	}
}