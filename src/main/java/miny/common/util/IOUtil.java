/* @description This class implements reusable methods for input/output processing, 
 *              e.g. reading and writing data structures.
 * 
 * @history 2.Nov.2015 Beta version
 *              
 * @author <ahref="mailto:h.cuayahuitl@gmail.com">Heriberto Cuayahuitl</a>
 */

package miny.common.util;

import java.util.*;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;

public class IOUtil {

	private static Logger logger = LoggerFactory.getLogger(IOUtil.class);
	private static Map<String,Integer> logMap = new HashMap();;
	public static void printBatchLog(String batchName) {
		
		try {
			logger.info("===================================================================>> BatchExec : {}", batchName);
			
//			Integer cnt = logMap.get(batchName);
//			if (cnt == null) cnt = 0;
//			cnt ++;
//			logMap.put(batchName, cnt);
//			if (cnt % 60 == 0){
//				
//				cnt = 0;
//			} 
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void printObject(Object obj) {
		try {
		    logger.debug("=================================================================== {}", obj.getClass().getName());
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				String name = field.getName();
				Object value = field.get(obj);
				if (value != null) logger.info("{} : {}", name, value);
			}
			logger.debug("===================================================================");
			
		} catch (Exception e) {
			logger.error("printObject Error");
			e.printStackTrace();
		}
	}

	public static void printArrayList(ArrayList<String> collection, String title) {
		logger.debug("==============================================================================");
		logger.debug(title);

		for (int i = 0; i < collection.size(); i++) {
			logger.debug("[{}]:[{}]", i, collection.get(i));
		}
	}

	public static void printHashMap(HashMap collection, String title) {

		ArrayList<String> states = new ArrayList<String>();

		logger.debug("==============================================================================");
		logger.debug(title);

		Set entries = collection.entrySet();
		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			logger.debug("[{}]=[{}]", key, value);
		}

	}

	public static void writeArrayList(String file, ArrayList collection, String message) {
		String value = "";
		logger.debug(message + " ... " + file);

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			for (int i = 0; i < collection.size(); i++) {
				if (collection.get(i) instanceof Integer) {
					value = ((Integer) collection.get(i)).toString();

				} else if (collection.get(i) instanceof Float) {
					value = ((Float) collection.get(i)).toString();

				} else if (collection.get(i) instanceof Double) {
					value = ((Double) collection.get(i)).toString();

				} else {
					value = (String) collection.get(i);
				}
				out.println(value);
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeJson(String file, String json) {
		String value = "";
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			out.println(json);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fileToMap(String file, HashMap<String, String> collection, String separator) {
		String line = "";
		String key = "";
		String value = "";
		String comment = "#";
		logger.debug("Reading file " + file);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.startsWith(comment) || line.equals("")) continue;

				StringTokenizer toks = new StringTokenizer(line, separator);
				if (toks.countTokens() > 0) {
					key = toks.nextToken();
					value = toks.nextToken();
					collection.put(key, value);
				}
			}
			reader.close();

		} catch (IOException e) {
			logger.debug("readHashMap : {}", line);
			e.printStackTrace();
		}
	}

	public static void readArrayList(String file, ArrayList<String> collection) {
		String line = "";
		logger.debug("Reading file " + file);

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				collection.add(line);
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void incrementHashTable(HashMap<String, Integer> collection, String key, int update) {
		if (collection.containsKey(key)) {
			Integer items = (Integer) collection.get(key);
			int updated = items.intValue() + update;
			collection.put(key, new Integer(updated));

		} else {
			collection.put(key, new Integer(update));
		}
	}

	public static void printSysTurn(HashMap<String, String> dict, int steps, boolean verbose) {
		if (dict.get("response_sys") != null && verbose) {
			String sys = "[" + dict.get("action_sys_key") + "] [" + dict.get("action_sys_val") + "] " + dict.get("response_sys");
			logger.debug(" SYS:" + "" + sys + " - " + steps);
		}
	}

	public static void printUsrTurn(HashMap<String, String> dict, int steps, boolean verbose) {
		if (dict.get("response_usr") != null && verbose) {
			String usr = "[" + dict.get("action_usr_key") + "] [" + dict.get("action_usr_val") + "] " + dict.get("response_usr");
			String asr = "[" + dict.get("action_usr_key") + "] [" + dict.get("action_usr_val") + "] " + dict.get("response_asr");
			logger.debug("USR:" + usr + " - " + steps);
		}
	}

	public static void printLearningExperience(String state, String actions, String action, String reward, boolean verbose) {
		if (verbose) {
			// Logger.debug("SympleDS", "", "s="+state + " A="+actions + "
			// a="+action + " r="+reward);
		}
	}

	public static void printInteractionLength(int numDialogues, long numTimeSteps, double winRate) {
		if (numDialogues % 100 == 0) {
			double avgTimeSteps = (double) numTimeSteps / numDialogues;
			// Logger.debug("SympleDS", "InteractionManagement",
			// "dialogues="+numDialogues + " turns="+avgTimeSteps + "
			// winRate="+winRate);
		}
	}
}
