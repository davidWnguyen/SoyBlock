package markiplites.SoyBlock;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

public class Skills {
    private static HashMap<UUID, HashMap<String, Double>> skillExperience = new HashMap<>();
    private static HashMap<UUID, HashMap<String, Integer>> skillLevels = new HashMap<>();
    public Skills() {

    }

    public static void addSkillExp(UUID id, String skill, double exp) {
        if(!skillExperience.containsKey(id)) {
            HashMap<String, Double> map = new HashMap<>();
            map.put("Combat", 0.0);
            map.put("Foraging", 0.0);
            map.put("Alchemy", 0.0);
            skillExperience.put(id, map);
            return;
        }
        if(!skillExperience.get(id).containsKey(skill)) {skillExperience.get(id).put(skill, exp); return;}
        skillExperience.get(id).replace(skill, skillExperience.get(id).get(skill)+exp);
        if(!skillLevels.containsKey(id)) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("Combat", 1);
            map.put("Foraging", 1);
            map.put("Alchemy", 1);
            skillLevels.put(id, map);
            return;
        }
        if(!skillLevels.get(id).containsKey(skill)) {skillLevels.get(id).put(skill, 1);return;}
        int levels = 0;
        String bonus = "";
        switch(skill) {
            case "Combat" -> {
                levels = (int)((Math.log(1 + ((skillExperience.get(id).get(skill)*2)/20) - (skillExperience.get(id).get(skill))/20))/(Math.log(2)) + 1);
                bonus = "+5 Base Damage";
            }

            default -> {Bukkit.getLogger().info("Nigga you crazyyy");}
        };
        int previous = skillLevels.get(id).replace(skill, levels);
        if(previous != levels) {
            Bukkit.getServer().getPlayer(id).sendMessage(IridiumColorAPI.process(String.format("You leveled up %s. You are now level <SOLID:09AABD>%d<SOLID:FFFFFF>!", skill, levels)));
            Bukkit.getServer().getPlayer(id).sendMessage(IridiumColorAPI.process(String.format("You get <SOLID:D5DD19>%s", bonus)));
        }
    }

    public static int getLevel(UUID id, String skill) {
        if(!skillLevels.containsKey(id)) return 1;
        if(!skillLevels.get(id).containsKey(skill)) return 1;
        return skillLevels.get(id).get(skill);
    }
}
