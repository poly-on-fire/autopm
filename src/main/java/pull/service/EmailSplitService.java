package pull.service;

import java.util.HashMap;
import java.util.Map;

public class EmailSplitService {
	public static Map<String, String> splitCanonicalEmailList(String cannonicalList) {
		String[] pairs = cannonicalList.split(",");
		return emailMap(pairs);
	}

	private static Map<String, String> emailMap(String[] pairs) {
		Map<String, String> emailMap = new HashMap<String, String>();
		for (String pair : pairs) {
			// check for " <" and ">" else log non compliant to screen
			pair = pair.trim();
			pair = pair.substring(0, pair.length() - 1);
			String[] tuple = pair.split("<");
			if (tuple.length == 2) {
				emailMap.put(tuple[0].trim(), tuple[1].trim());
			}
		}
		return emailMap;
	}

}
