package markiplites.SoyBlock.Lists;

import java.util.HashMap;

import org.bukkit.entity.EntityType;

import markiplites.SoyBlock.Ent;

public class entityList {
    HashMap<String, Double> attributes = new HashMap<>();
    public entityList() {
        init();
    }

    private void init() {
        attributes.put("Health", 250.0);
        attributes.put("BaseDamage", 75.0);
        attributes.put("MaxHealth", 250.0);
        attributes.put("Absorption", 75.0);
        attributes.put("Intelligence", 15.0);
        attributes.put("IntelligenceScaling", 0.9);
        attributes.put("Strength", 25.0);
        attributes.put("StrengthScaling", 1.1);
        attributes.put("Dexterity", 15.0);
        attributes.put("DexterityScaling", 1.0);
        new Ent("ZOMBIE_KING", "Zombie King", EntityType.ZOMBIE, attributes);
    }
}
