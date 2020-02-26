package miny.vo;

import java.util.List;
import java.util.Map;

public class ResultBoxoffice {
	private Map<String, Object> boxOfficeResult;
	
	public class BoxOfficeResult {
		private String showRange;
		private List<WeeklyBoxOfficeList> weeklyBoxOfficeList;
		public String getShowRange() {
			return showRange;
		}
		public void setShowRange(String showRange) {
			this.showRange = showRange;
		}
		public List<WeeklyBoxOfficeList> getWeeklyBoxOfficeList() {
			return weeklyBoxOfficeList;
		}
		public void setWeeklyBoxOfficeList(List<WeeklyBoxOfficeList> weeklyBoxOfficeList) {
			this.weeklyBoxOfficeList = weeklyBoxOfficeList;
		}
	}
	
	public class WeeklyBoxOfficeList {
		private String rank;
		private String movieNm;
		private String openDt;
		private String audiAcc;
		public String getRank() {
			return rank;
		}
		public void setRank(String rank) {
			this.rank = rank;
		}
		public String getMovieNm() {
			return movieNm;
		}
		public void setMovieNm(String movieNm) {
			this.movieNm = movieNm;
		}
		public String getOpenDt() {
			return openDt;
		}
		public void setOpenDt(String openDt) {
			this.openDt = openDt;
		}
		public String getAudiAcc() {
			return audiAcc;
		}
		public void setAudiAcc(String audiAcc) {
			this.audiAcc = audiAcc;
		}
	}

	public Map<String, Object> getBoxOfficeResult() {
		return boxOfficeResult;
	}

	public void setBoxOfficeResult(Map<String, Object> boxOfficeResult) {
		this.boxOfficeResult = boxOfficeResult;
	}
}
