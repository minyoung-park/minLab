package miny.common.util;

import java.util.HashMap;
import java.util.Random;

/*
 * 
	case 1: emoji = "☀(맑음)"; break;
	case 2: emoji = "☁(구름조금)"; break;
	case 3: emoji = "☁(구름많음)"; break;
	case 4: emoji = "⛅(흐림)"; break;
	case 5: emoji = "☔(비)"; break;
	case 6: emoji = "❄(눈)"; break;
	case 7: emoji = "☔->☀(비온후갬)"; break;
	case 8: emoji = "☔(소나기)"; break;
	case 9: emoji = "☔/❄(비/눈)"; break;
	case 10: emoji = "❄/☔(눈/비)"; break;
	case 11: emoji = "⚡(낙뢰)"; break;
	case 12: emoji = "🌁(안개)"; break;
 * 
 * */
public class WeatherUtil {
	public static String getSkyState(int code) {
		String state = "";
		switch (code) {
		case 1:
			state = "맑음";
			break;
		case 2:
			state = "구름조금";
			break;
		case 3:
			state = "구름많음";
			break;
		case 4:
			state = "흐림";
			break;
		default:
			break;
		}
		return state;
	}
	
	public static String getPtyState(int code) {
		String state = "";
		switch (code) {
		case 1:
			state = "(비)";
			break;
		case 2:
			state = "(진눈개비)";
			break;
		case 3:
			state = "(눈)";
			break;
		default:
			break;
		}
		return state;
	}
	
	public static String getPm10State(int num) {
		String state = "-";
		if(num >= 0 && num < 31)
			state="좋음";
		else if(num >=31 && num <81)
			state="보통";
		else if(num >=81 && num <151)
			state="나쁨";
		else if(num >=151)
			state="매우 나쁨";
		return state;
	}
	
	public static String getPm25State(int num) {
		String state = "-";
		if(num >= 0 && num < 16)
			state="좋음";
		else if(num >=16 && num <51)
			state="보통";
		else if(num >=51 && num <101)
			state="나쁨";
		else if(num >=101)
			state="매우 나쁨";
		return state;
	}
	
	public static String getO3State(double num) {
		String state = "-";
		if(num >= 0 && num < 0.031)
			state="좋음";
		else if(num >=0.031 && num <0.091)
			state="보통";
		else if(num >=0.091 && num <0.151)
			state="나쁨";
		else if(num >=0.151)
			state="매우 나쁨";
		return state;
	}
	
	//체감온도 산출식
	public static String getSensorTemp(String wind, String temp) {
		double sensorTemp = 0;
		
		double w = Double.parseDouble(wind);
		double t = Double.parseDouble(temp);
		
		w = w * 3.6;
		if(w > 4.8) {
			w = Math.pow(w, 0.16);
			sensorTemp = 13.12 + 0.6215 * t - 11.37 * w + 0.3965 * w * t;
			sensorTemp = Math.round(sensorTemp);
			if(sensorTemp > t) {
				sensorTemp = t;
			}
			
		} else {
			sensorTemp = t;
		}
		
		return String.valueOf(sensorTemp);
	}
	
	//위경도를 기상청 좌표축에 맞추기 위한 함수
	public static HashMap<String, Integer> transferXY(double lat, double lon) {
		double PI = Math.asin(1.0) * 2.0;
		double DEGRAD = PI / 180.0;
		double Re = 6371.00877; // 지도반경
		double grid = 5.0; // 격자간격 (km)
		double re;
		double olon = 126.0; // 기준점 경도
		double olat = 38.0; // 기준점 위도
		double sn, sf, ro;
		double slat1 = 30.0; // 표준위도 1
		double slat2 = 60.0; // 표준위도 2
		double ra, theta;
		double xo = 210 / grid; // 기준점 X좌표
		double yo = 675 / grid; // 기준점 Y좌표

		re = Re / grid;
		slat1 = slat1 * DEGRAD;
		slat2 = slat2 * DEGRAD;
		olon = olon * DEGRAD;
		olat = olat * DEGRAD;

		sn = Math.tan(PI * 0.25 + slat2 * 0.5) / Math.tan(PI * 0.25 + slat1 * 0.5);
		sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
		sf = Math.tan(PI * 0.25 + slat1 * 0.5);
		sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
		ro = Math.tan(PI * 0.25 + olat * 0.5);
		ro = re * sf / Math.pow(ro, sn);

		ra = Math.tan(PI * 0.25 + (lat) * DEGRAD * 0.5);
		ra = re * sf / Math.pow(ra, sn);
		theta = (lon) * DEGRAD - olon;
		if (theta > PI)
			theta -= 2.0 * PI;
		if (theta < -PI)
			theta += 2.0 * PI;
		theta *= sn;

		double x = (ra * Math.sin(theta)) + xo + 1.5;
		double y = (ro - ra * Math.cos(theta)) + yo + 1.5;

		HashMap<String, Integer> map = new HashMap<>();
		map.put("X", (int) x);
		map.put("Y", (int) y);
		return map;
	}
	/*
	//중기 육상 예보 구역 코드
	public static String getMidSkyArea(String area) {
		if(area.equals("서울") || area.equals("인천") || area.contains("경기")) {
			return "11B00000";
		} else if(area.equals("영서")) {
			return "11D10000";
		} else if(area.equals("영동")) {
			return "11D20000";
		} else if(area.equals("대전") || area.equals("세종") || area.equals("충남")) {
			return "11C20000";
		} else if(area.equals("충북")) {
			return "11C10000";
		} else if(area.equals("광주") || area.equals("전남")) {
			return "11F20000";
		} else if(area.equals("전북")) {
			return "11F10000";
		} else if(area.equals("대구") || area.equals("경북")) {
			return "11H10000";
		} else if(area.equals("부산") || area.equals("울산") || area.equals("경남")) {
			return "11H20000";
		} else if(area.equals("제주")) {
			return "11G0000";
		}
		return null;
	}
	
	//중기 기온 예보 구역 코드
	public static String getMidTempArea(String area) {
		if(area.equals("서울")) {
			return "11B10101";
		} else if(area.equals("인천")) {
			return "11B20201";
		} else if(area.equals("경기남부")) {
			return "11B20601";
		} else if(area.equals("경기북부")) {
			return "11B20305";
		} else if(area.equals("영서")) {
			return "11D10301";
		} else if(area.equals("영동")) {
			return "11D20501";
		} else if(area.equals("대전")) {
			return "11C20401";
		} else if(area.equals("세종")) {
			return "11C20404";
		} else if(area.equals("충남")) {
			return "11C20101";
		} else if(area.equals("충북")) {
			return "11C10301";
		} else if(area.equals("광주")) {
			return "11F20501";
		} else if(area.equals("전남")) {
			return "21F20801";
		}else if(area.equals("전북")) {
			return "11F10201";
		} else if(area.equals("대구")) {
			return "11H10701";
		} else if(area.equals("부산")) {
			return "11H20201";
		} else if(area.equals("울산")) {
			return "11H20101";
		} else if(area.equals("경남")) {
			return "11H20301";
		} else if(area.equals("경북")) {
			return "11H10201";
		} else if(area.equals("제주")) {
			return "11G00201";
		}
		return null;
	}
	*/
	//기온별 옷차림 + 기타 txt
	private static String[][] clothes = {
			{"반팔","얇은 셔츠","얇은 긴팔","반바지","면바지"},
			{"긴팔티","가디건","후드티","면바지","얇은 슬랙스","스키니진"},
			{"니트","가디건","후드티","맨투맨","청바지","면바지","슬랙스"},
			{"자켓","셔츠","가디건","얇은 야상","슬랙스","청바지"},
			{"트렌치코트","청자켓","봄/가을 외투"},
			{"코트","가죽자켓", "니트", "레깅스", "무스탕"},
			{"목도리", "털모자", "장갑", "패딩"}
	};
	
	public static String recommText(String temp_min, String temp_max) {
		String txt = "";
		Random rnd = new Random();
		int rand = 0;
		
		int min = CUtil.objectToInteger(temp_min);
		int max = CUtil.objectToInteger(temp_max);
		
		double temp_avg = (min+max)/2;
		
		if(temp_avg >= 27) {
			txt += "기온이 높으니 민소매나 반바지를 입는게 어떨까요?";
		} else if(temp_avg < 27 && temp_avg >= 23) {
			rand = rnd.nextInt(clothes[0].length);
			txt += "오늘 옷차림은 " + clothes[0][rand] + " 추천!";
		} else if(temp_avg < 23 && temp_avg >= 20) {
			rand = rnd.nextInt(clothes[1].length);
			txt += "오늘 날씨는 " + clothes[1][rand] + " 입는게 좋겠어요^^";
		} else if(temp_avg < 20 && temp_avg >= 17) {
			rand = rnd.nextInt(clothes[2].length);
			txt += "오늘 " + clothes[2][rand] + " 입는게 어때요?";
		} else if(temp_avg < 17 && temp_avg >= 12) {
			rand = rnd.nextInt(clothes[3].length);
			txt += "오늘 패션 아이템은 " + clothes[3][rand] + " 추천!";
		} else if(temp_avg < 12 && temp_avg >= 10) {
			rand = rnd.nextInt(clothes[4].length);
			txt += "쌀쌀한 날씨엔 " + clothes[4][rand] + " 어때요?";
		} else if(temp_avg < 10 && temp_avg >= 6) {
			rand = rnd.nextInt(clothes[5].length);
			txt += "오늘 " + clothes[5][rand] + " 입는게 어때요?";
		} else {
			rand = rnd.nextInt(clothes[6].length);
			txt += "따뜻하게 입으세요! " + clothes[6][rand] + " 필수!";
		}
		return txt;
	}
}
