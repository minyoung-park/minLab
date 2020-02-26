/* @description This class implements reusable methods for string-based processing.
 * 
 * @history 2.Nov.2015 Beta version
 *              
 * @author <ahref="mailto:h.cuayahuitl@gmail.com">Heriberto Cuayahuitl</a>
 */


package miny.common.util;

import java.util.*;

public class StringUtil {
	public static String getExpandedDialAct(String template, HashMap<String,String> pairs) {		
		if (template == null) return template;
		String newtemplate = template;

		if (template.indexOf("$")>=0) {
			boolean quoted = template.startsWith("\"") && template.endsWith("\"") ? true : false;
			if (quoted) {
				template = template.substring(1, template.length()-1);
			}

			String sequence = "";
			String slotValues = template.substring(template.indexOf("(")+1, template.indexOf(")"));
			ArrayList<String> list = StringUtil.getArrayListFromString(slotValues, ",");

			for (String item : list) {
				String key = item.substring(0, item.indexOf("="));
				String val = item.substring(item.indexOf("=")+1);
				String getVal = pairs.get(val); 
				if (getVal != null){
					String keyVal = key+"="+getVal;
					sequence += (sequence.equals("")) ? keyVal : ","+keyVal;	
				}
			}

			String dialAct = template.substring(0, template.indexOf("("));
			newtemplate = dialAct + "(" + sequence + ")";

			if (quoted) {
				newtemplate = "\""+newtemplate+"\"";
			}
		}

		return newtemplate;
	}

	public static String getExpandedTemplate(String template, HashMap<String,String> pairs) {
		if (template == null) return template;
		String newtemplate = template;

		if (template.indexOf("$")>=0) {
			boolean quoted = template.startsWith("\"") && template.endsWith("\"") ? true : false;
			if (quoted) {
				template = template.substring(1, template.length()-1);
			}

			String prefix = template.substring(0, template.indexOf("$"));
			String suffix = "";
			String variable = "";
			template = template.substring(prefix.length());
			if (template.indexOf(" ")>0) {
				variable = template.substring(0, template.indexOf(" "));
				suffix = template.substring(template.indexOf(" "));

			} else if (template.endsWith(".") || template.endsWith("?") || template.endsWith(")")) {
				suffix += template.substring(template.length()-1);
				variable = template.substring(0, template.length()-1);

			} else {
				variable = template;
			}

			if (!pairs.containsKey(variable)) return null;
				
			String value = (String) pairs.get(variable);
			newtemplate = prefix + value.trim() + suffix;

			if (quoted) {
				newtemplate = "\""+newtemplate+"\"";
			}
		}
		
		if (newtemplate.indexOf("$")>0) {
			newtemplate = getExpandedTemplate(newtemplate, pairs);
		}

		return newtemplate;
	}
	
	public static String getRandomisedTemplate(String templates) {
		ArrayList<String> list = StringUtil.getArrayListFromString(templates, "|");
		String template = null;
		
		if (list.size() == 1) {
			template = list.get(0);
			
		} else {
			double randValue = Math.random();
			for (int i=1; i<=list.size(); i++) {
				String templateInFocus = list.get(i-1);
				double incValue = (double)i/list.size();

				if (incValue>=randValue) {
					template = templateInFocus;
					break;
				}
			}
		}
	
		return template;
	}
	
	public static void expandAbstractKeyValuePairs(HashMap<String,String> collection) {
		try {
		for (String key : collection.keySet()) {
			String line = collection.get(key);
			if (line.indexOf("%")>0) {
				String prefix = line.substring(0, line.indexOf("%"));
				String rest = line.substring(line.indexOf("%")+1);
				String value = rest.substring(0, rest.indexOf("%"));
				String suffix = rest.substring(rest.indexOf("%")+1);
				String newValue = prefix + collection.get(value) + suffix;
				collection.put(key, newValue);
			}
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getArrayListFromString(String wordSequence, String separator) {
		ArrayList<String> array = new ArrayList<String>();

		if (wordSequence != null) {
			StringTokenizer toks = new StringTokenizer(wordSequence, separator);
			while (toks.hasMoreTokens()) array.add(toks.nextToken());
		}

		return array;
	}
	
	public static HashMap<String,Double> getWordDistributionFromRawText(String observations) {
		HashMap<String,Integer> counts = new HashMap<String,Integer>();
		HashMap<String,Double> probs = new HashMap<String,Double>();
		int topCount = 0;

		if (observations == null || observations.equals("")) return probs;
		
		observations = observations.toLowerCase();
		ArrayList<String> tokens = StringUtil.getArrayListFromString(observations, " :");
		
		for (String token : tokens) {
			IOUtil.incrementHashTable(counts, token, 1);
			int count = counts.get(token);
			if (count>topCount) {
				topCount = count;
			}
		}

		for (String token : tokens) {
			int count = counts.get(token);
			double prob = (double) count/topCount;
			probs.put(token, prob);
		}
		
		return probs;
	}

	@SuppressWarnings("resource")
	public static String getTypedInput() {
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	
	public static String returnAreaName(String area) {
		String return_area = area;
		if (area.equals("충청남도")) {
			return_area = "충남";
		} else if (area.equals("충청북도")) {
			return_area = "충북";
		} else if (area.equals("전라남도")) {
			return_area = "전남";
		} else if (area.equals("전라북도")) {
			return_area = "전북";
		} else if (area.equals("경상남도")) {
			return_area = "경남";
		} else if (area.equals("경상북도")) {
			return_area = "경북";
		} else if (area.equals("서울특별시") || area.equals("서울시")) {
			return_area = "서울";
		} else if (area.equals("경기도")) {
			return_area = "경기";
		} else if (area.equals("강원도")) {
			return_area = "강원";
		} else if (area.equals("광주광역시") || area.equals("광주시")) {
			return_area = "광주";
		} else if (area.equals("부산광역시") || area.equals("부산시")) {
			return_area = "부산";
		} else if (area.equals("대구광역시") || area.equals("대구시")) {
			return_area = "대구";
		} else if (area.equals("인천광역시") || area.equals("인천시")) {
			return_area = "인천";
		} else if (area.equals("울산광역시") || area.equals("울산시")) {
			return_area = "울산";
		} else if (area.equals("대전광역시") || area.equals("대전시")) {
			return_area = "대전";
		} else if (area.equals("세종특별자치시") || area.equals("세종시")) {
			return_area = "세종";
		} else if (area.equals("제주특별자치도") || area.equals("제주도")) {
			return_area = "제주";
		}
		
		return return_area;
	}
	
	public static String returnGyeonggiAreaName(String area) {
		String return_area = "경기";
		if(area.equals("고양시")) {
			return_area = "경기북부";
		} else if(area.equals("구리시")) {
			return_area = "경기북부";
		} else if(area.equals("김포시")) {
			return_area = "경기북부";
		} else if(area.equals("남양주시")) {
			return_area = "경기북부";
		} else if(area.equals("동두천시")) {
			return_area = "경기북부";
		} else if(area.equals("양주시")) {
			return_area = "경기북부";
		} else if(area.equals("의정부시")) {
			return_area = "경기북부";
		} else if(area.equals("파주시")) {
			return_area = "경기북부";
		} else if(area.equals("포천시")) {
			return_area = "경기북부";
		} else if(area.equals("연천군")) {
			return_area = "경기북부";
		} else if(area.equals("가평군")) {
			return_area = "경기북부";
		} else if(area.equals("양평군")) {
			return_area = "경기북부";
		} else {
			return_area = "경기남부";
		}
		return return_area;
	}
	
	public static String returnGangwonAreaName(String area) {
		String return_area = "강원";
		if(area.equals("고성군")) {
			return_area = "영동";
		} else if(area.equals("속초시")) {
			return_area = "영동";
		} else if(area.equals("양양군")) {
			return_area = "영동";
		} else if(area.equals("강릉시")) {
			return_area = "영동";
		} else if(area.equals("동해시")) {
			return_area = "영동";
		} else if(area.equals("삼척시")) {
			return_area = "영동";
		} else if(area.equals("태백시")) {
			return_area = "영동";
		} else {
			return_area = "영서";
		}
		return return_area;
	}
	
	public static String returnCommaNum(int num) {
		return String.format("%,d", num);
	}
}
