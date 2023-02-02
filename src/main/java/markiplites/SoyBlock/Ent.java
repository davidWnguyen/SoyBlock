package markiplites.SoyBlock;

import java.util.HashMap;
import java.util.UUID;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;

public class Ent {
    static HashMap<String, HashMap<String, Double>> entityAttributes = new HashMap<>();
    static HashMap<String, EntityType> models = new HashMap<>();
    public static HashMap<String, String> names = new HashMap<>();
    public static HashMap<UUID, ArmorStand> nameTags = new HashMap<>();
    public Ent(String entityID, String entityName, EntityType model, HashMap<String, Double> attributes) {
        
        entityAttributes.put(entityID, (HashMap<String, Double>)attributes.clone());
        models.put(entityID, model);
        names.put(entityID, entityName);
        Bukkit.getLogger().info(entityID + " has been added to entity dictionary.");
    }

    public static Entity spawnCustomEntity(String entityID, Location loc) {
        if(entityAttributes.containsKey(entityID) && models.containsKey(entityID) && names.containsKey(entityID)) {
            Entity ent = loc.getWorld().spawnEntity(loc, models.get(entityID));

            EntityHandling.putEntityAttributes(ent.getUniqueId(), entityAttributes.get(entityID), names.get(entityID));

            EntityHandling.entityAttributes.get(ent.getUniqueId()).replace("Health", EntityHandling.entityAttributes.get(ent.getUniqueId()).get("MaxHealth")); //im mad at myself

            ArmorStand hologram = (ArmorStand) ent.getWorld().spawnEntity(new Location(ent.getWorld(), 0, 0, 0), EntityType.ARMOR_STAND);
            hologram.setVisible(false);
            hologram.setBasePlate(false);
            hologram.setCollidable(false);
            hologram.setArms(false);
            hologram.setSmall(true);
            hologram.setSilent(true);
            hologram.setCanPickupItems(false);
            hologram.setGliding(true);
            hologram.setLeftLegPose(EulerAngle.ZERO.add(180, 0, 0));
            hologram.setRightLegPose(EulerAngle.ZERO.add(180, 0, 0));
            hologram.setInvulnerable(true);
            hologram.setCustomNameVisible(true);
            hologram.setCustomName(names.get(entityID) + ": " + IridiumColorAPI.process(String.format("<SOLID:44E90B> %.0f/%.0f", entityAttributes.get(entityID).get("MaxHealth"), entityAttributes.get(entityID).get("MaxHealth"))));
            hologram.setGravity(false);
            ent.addPassenger(hologram);
            nameTags.put(ent.getUniqueId(), hologram);
            return ent;
        }
        else return null;
    }
}
