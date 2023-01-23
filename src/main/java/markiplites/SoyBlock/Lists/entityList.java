package markiplites.SoyBlock.Lists;

import java.util.HashMap;

import org.bukkit.entity.EntityType;

import markiplites.SoyBlock.Ent;

public class entityList {
    public entityList() {
        init();
    }

    private void init() {
        HashMap<String, Double> attributes = new HashMap<>();

        attributes.put("Health", 10000.0);
        attributes.put("BaseDamage", 10000.0);
        attributes.put("MaxHealth", 10000.0);
        attributes.put("Absorption", 100000.0);
        Ent ent = new Ent("ZOMBIE_BOSS", EntityType.ZOMBIE, attributes);

        attributes.clear();
   }
}
