package nx.util;


import java.util.HashMap;
import java.util.List;

import io.github.fvarrui.globalstats.GlobalStats;
import io.github.fvarrui.globalstats.model.Rank;
import io.github.fvarrui.globalstats.model.Stats;

public class Global_stats {
	
	public static GlobalStats CLIENT = new GlobalStats("e1JpQ3fJ8y2z5jEdDIxMkF2Uss4ewe6LgQa6TpQ1", "SA3bfhOXOAiuDdW5haBwhnM7xT0XJ2WBDZNSgCQ5");
	
	public static void CreateNewUserStatistics(int value) {
		try {
			Stats stats = CLIENT.createStats("username", new HashMap<String, Object>() {{
			    put("Time", value);
			}});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Stats getUserStatistics(String id) {
		try {
			return CLIENT.getStats("63d8f9d59f5e8817248b4577");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Rank> getLeaderboard(){
		try {
			return CLIENT.getLeaderboard("Time", 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
