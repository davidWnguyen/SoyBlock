package markiplites.SoyBlock.Configs;

import markiplites.SoyBlock.Item;
import markiplites.SoyBlock.attr;
import org.bukkit.Material;

import java.util.HashMap;

public class skillsConfig {
    public static HashMap<String, Double[]> skillEXPRequirements = new HashMap<>();
    public static HashMap<String, Double[]> skillEXPRequirementsCumulative = new HashMap<>();
    public skillsConfig() {
        init();
    }
    public void init() {
        //Populate the skill EXP requirements
        //Standard Skill EXP Requirements
        Double[] standardEXPRequirementList = {0.0,
                100.0,200.0,300.0,400.0,500.0,//1-5
                1000.0,1300.0,1600.0,1900.0,2100.0,//6-10
                5000.0,7000.0,9000.0,11000.0,13000.0,//11-15
                20000.0,25000.0,30000.0,35000.0,40000.0,//16-20
                80000.0, 100000.0, 120000.0, 140000.0, 160000.0,//21-25
                500000.0, 700000.0, 900000.0, 1100000.0, 1300000.0,//26-30
        };
        Double[] standardEXPRequirementCumulativeList = new Double[31];
        Double cumulative = 0.0;
        for(int i = 0;i < standardEXPRequirementCumulativeList.length;i++){
            cumulative += standardEXPRequirementList[i];
            standardEXPRequirementCumulativeList[i] = cumulative;
        }

        skillEXPRequirements.put("Combat", standardEXPRequirementList);
        skillEXPRequirements.put("Foraging", standardEXPRequirementList);
        skillEXPRequirements.put("Mining", standardEXPRequirementList);
        skillEXPRequirements.put("Farming", standardEXPRequirementList);
        skillEXPRequirements.put("Alchemy", standardEXPRequirementList);

        skillEXPRequirementsCumulative.put("Combat", standardEXPRequirementCumulativeList);
        skillEXPRequirementsCumulative.put("Foraging", standardEXPRequirementCumulativeList);
        skillEXPRequirementsCumulative.put("Mining", standardEXPRequirementCumulativeList);
        skillEXPRequirementsCumulative.put("Farming", standardEXPRequirementCumulativeList);
        skillEXPRequirementsCumulative.put("Alchemy", standardEXPRequirementCumulativeList);
    }
}
