package markiplites.SoyBlock;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Ent {
    static HashMap<String, HashMap<String, Double>> entityAttributes = new HashMap<>();
    static HashMap<String, EntityType> models = new HashMap<>();
    public static HashMap<String, String> names = new HashMap<>();
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
            
            EntityHandling.setNameHealth(ent);
            return ent;
        }
        else return null;
    }
}
