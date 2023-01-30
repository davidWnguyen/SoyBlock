package markiplites.SoyBlock;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.LivingEntity;
import com.iridium.iridiumcolorapi.IridiumColorAPI;

public class Ent {
    static HashMap<String, HashMap<String, Double>> entityAttributes = new HashMap<>();
    static HashMap<String, EntityType> models = new HashMap<>();
    static HashMap<String, String> names = new HashMap<>();
    public Ent(String entityID, String entityName, EntityType model, HashMap<String, Double> attributes) {
        entityAttributes.put(entityID, attributes);
        models.put(entityID, model);
        names.put(entityID, entityName);
        Bukkit.getLogger().info(entityID + " has been added to entity dictionary.");
    }

    public static Entity spawnCustomEntity(String entityID, Location loc) {
        if(entityAttributes.containsKey(entityID) && models.containsKey(entityID) && names.containsKey(entityID)) {
            Entity ent = loc.getWorld().spawnEntity(loc, models.get(entityID));
            EntityHandling.spawned.put(ent.getEntityId(), true);
            EntityHandling.putEntityAttributes(ent.getUniqueId(), entityAttributes.get(entityID), names.get(entityID));
            EntityHandling.setNameHealth(ent);
            return ent;
        }
        else return null;
    }
}
