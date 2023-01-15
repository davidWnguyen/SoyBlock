package markiplites.SoyBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandsTabCompletion implements TabCompleter {
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> list = new ArrayList<>(ItemListHandler.getItemMap().keySet());
		List<String> completions = null;
		if(args.length == 2)
		{
			String input = args[1].toUpperCase();
			
			for(String s : list) {
				if(s.startsWith(input)) {
					if(completions == null) {
						completions = new ArrayList<>();
					}
					completions.add(s);
				}
			}
		}
		if(completions != null)
			Collections.sort(completions);
		return completions;
		
		
	}
}