package markiplites.SoyBlock;

import java.util.HashMap;
import java.util.UUID;

import markiplites.SoyBlock.Configs.skillsConfig;
import org.bukkit.Bukkit;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

public class Skills {
    public static final HashMap<UUID, HashMap<String, Double>> skillExperience = new HashMap<>();
    public static final HashMap<UUID, HashMap<String, Integer>> skillLevels = new HashMap<>();
    public Skills() {

    }

    public static void addSkillExp(UUID id, String skill, double exp) {
        //curse java
        if(!skillExperience.containsKey(id)) {
            HashMap<String, Double> map = new HashMap<>();
            map.put("Combat", 0.0);
            map.put("Foraging", 0.0);
            map.put("Mining", 0.0);
            map.put("Alchemy", 0.0);
            skillExperience.put(id, map);
        }
        if(!skillExperience.get(id).containsKey(skill)) {skillExperience.get(id).put(skill, exp); return;}
        skillExperience.get(id).replace(skill, skillExperience.get(id).get(skill)+exp);
        if(!skillLevels.containsKey(id)) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("Combat", 1);
            map.put("Foraging", 1);
            map.put("Mining", 1);
            map.put("Alchemy", 1);
            skillLevels.put(id, map);
        }
        if(!skillLevels.get(id).containsKey(skill)) {skillLevels.get(id).put(skill, 1);return;}

        int levels = skillLevels.get(id).get(skill);
        String bonus = "";
        //When you level up.
        if(skillExperience.get(id).get(skill) >= skillsConfig.skillEXPRequirementsCumulative.get(skill)[levels]) {
            switch (skill) {
                case "Combat" -> bonus = "+1 Combat Skill Point";
                case "Foraging" -> bonus = "+1 Foraging Skill Point";
                case "Mining" -> bonus = "+1 Mining Skill Point";
                case "Farming" -> bonus = "+1 Horticulture Skill Point";
                default -> Bukkit.getLogger().info("SOYBLOCK SKILLS: Cannot find associated skill to get level formula.");
            }
            skillLevels.get(id).replace(skill, levels+1);
            Bukkit.getServer().getPlayer(id).sendMessage(IridiumColorAPI.process(String.format("You leveled up %s. You are now level <SOLID:09AABD>%d<SOLID:FFFFFF>!", skill, levels)));
            Bukkit.getServer().getPlayer(id).sendMessage(IridiumColorAPI.process(String.format("You get <SOLID:D5DD19>%s", bonus)));
        }
    }

    public static int getLevel(UUID id, String skill) {
        if(!skillLevels.containsKey(id)) return 1;
        if(!skillLevels.get(id).containsKey(skill)) return 1;
        return skillLevels.get(id).get(skill);
    }
    public static double getPlayerEXP(UUID id, String skill){
        if(!skillExperience.containsKey(id)) return 0.0;
        if(!skillExperience.get(id).containsKey(skill)) return 0.0;
        return skillExperience.get(id).get(skill);
    }
}
