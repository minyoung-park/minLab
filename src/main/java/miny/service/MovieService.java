package miny.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miny.common.CValue;
import miny.common.apis.CallApi;
import miny.common.exception.MyException;
import miny.common.exception.MyException.MyError;
import miny.common.util.CUtil;
import miny.common.util.StringUtil;
import miny.common.util.TimeUtil;
import miny.vo.ActionResultForMessage;
import miny.vo.ResultBoxoffice;
import miny.vo.Template;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class MovieService {
	
	@Autowired
	private CallApi api;

	public Map<String, Object> getWeeklyBoxoffice(Map<String, Object> map) {
		Map<String, Object> returnData = new HashMap<>();
		ResultBoxoffice result = new ResultBoxoffice();
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("itemPerPage", 5);
			param.put("targetDt", map.get("period"));
			Call<ResultBoxoffice> call = api.getCall().getWeeklyBoxoffice(param);
			result = call.execute().body();

			Map<String, Object> movie = result.getBoxOfficeResult();
			//			logger.info("{}", new Gson().toJson(movie));

			StringBuffer sb = new StringBuffer();
			sb.append("최근 한 주 동안 인기 영화!\n설마...이것도 안 본건 아니죠?\n");
			List<Map> list = (List<Map>) movie.get("weeklyBoxOfficeList");
			for (Map vo : list) {
				sb.append(vo.get("rank")).append("위 ").append(vo.get("movieNm"));
				int audiAcc = Integer.parseInt((String) vo.get("audiAcc"));
				sb.append(" (총 ").append(StringUtil.returnCommaNum(audiAcc)).append("명)\n");
			}

			String daterange = (String) movie.get("showRange");
			String[] date = daterange.split("~");
			TimeUtil tu = new TimeUtil();
			sb.append("\n(집계기간: ").append(tu.toStr(tu.toDate(date[0]), "yy/MM/dd")).append("~").append(tu.toStr(tu.toDate(date[1]), "yy/MM/dd")).append(", 자료제공: 영화진흥위원회)");
			returnData.put("message", sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	public ActionResultForMessage getWeeklyBoxoffice_new(Map<String, Object> param) {
		ActionResultForMessage returnData = new ActionResultForMessage();
		ResultBoxoffice result = new ResultBoxoffice();
		
		try {
			param.put("itemPerPage", 5);
			param.put("targetDt", param.get("period"));
			Call<ResultBoxoffice> call = api.getCall().getWeeklyBoxoffice(param);
			result = call.execute().body();

			Map<String, Object> movie = result.getBoxOfficeResult();

			List<Map> list = (List<Map>) movie.get("weeklyBoxOfficeList");
			for (Map vo : list) {
				Template template = getMovieInfo(vo);
				if(template == null) throw new MyException(MyError.NO_DATA_FOUND);
				returnData.addTemplate(template);
			}
			
			String daterange = (String) movie.get("showRange");
			String[] date = daterange.split("~");
			TimeUtil tu = new TimeUtil();
			
			StringBuffer sb = new StringBuffer();
			sb.append("집계기간: ").append(tu.toStr(tu.toDate(date[0]), "yy/MM/dd")).append("~").append(tu.toStr(tu.toDate(date[1]), "yy/MM/dd"));
			sb.append(CValue.newlineStr).append("자료제공: 영화진흥위원회");
			returnData.setMessage(sb.toString());

			throw new MyException(MyError.SUCCESS);
		} catch (MyException e) {
			returnData.setMyError(e.getMyError());
		} catch (Exception e) {
			e.printStackTrace();
			returnData = null;
		}
		
		return returnData;
	}
	
	public ActionResultForMessage getTicketingMovieChart() {
		ActionResultForMessage returnData = new ActionResultForMessage();
		
		try {
			Call<Map<String, Object>> call = api.getCall().getTicketingMovieChart();
			Response<Map<String, Object>> body = call.execute();
			
			if(body.code() != 200) throw new MyException(MyError.EXTERNAL_SERVER_ERROR);
			
			Map<String, Object> result = body.body();
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) ((Map)result.get("movieChartList")).get("RESERVE");
			int rank = 1;
			StringBuffer sb = new StringBuffer();
			for(Map<String,Object> data : list) {
				Template template = new Template();
				
				String img = "https://movie-phinf.pstatic.net" + data.get("posterImageUrl");
				template.setImage_url(img);
				String title = rank+"위 " + data.get("movieTitle");
				template.setTitle(title);
				String description = "예매율 " + data.get("reserveRatio") + "%";
				description += CValue.newlineStr + ((Map)data.get("lastKoreanGrade")).get("name");
				template.setContents(description);
				String link = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=" + CUtil.objectToInteger(data.get("movieCode"));
				template.setImage_action(link);
				template.addButton("web_url", "상세보기", link);
				
				sb.append(title).append(CValue.newlineStr);
				sb.append("예매율 ").append(data.get("reserveRatio")).append("%").append(CValue.newlineStr);
				sb.append(link).append(CValue.newlineStr).append(CValue.newlineStr);
				template.setText(sb.toString());
				
				returnData.addTemplate(template);
				rank++;
			}
			returnData.setMessage("현재 예매율 순위!");
			throw new MyException(MyError.SUCCESS);
		} catch (MyException e) {
			returnData.setMyError(e.getMyError());
		} catch (Exception e) {
			e.printStackTrace();
			returnData = null;
		}
		
		return returnData;
	}
	
	public Template getMovieInfo(Map data) {
		Template template = new Template();
		
		Map<String, Object> param = new HashMap<>();
		Map<String, String> header = new HashMap<>();
		header.put("X-Naver-Client-Id", "FT8pY2Ghyo6olzPxn9k5");
		header.put("X-Naver-Client-Secret", "1wJ_AHuiKX");
		
		try {
			param.put("query", data.get("movieNm"));
			
			Call<HashMap<String, Object>> call = api.getCall().getNaverMovie(param, header);
			Response<HashMap<String, Object>> body = call.execute();
			HashMap<String, Object> result = body.body();
			
			if(result.get("items") == null) return null;
			
			Map movie = (((List<Map>)result.get("items")).get(0));
			
			template.setImage_url((String)movie.get("image"));
			template.setImage_action((String)movie.get("link"));
			String title = data.get("rank") + "위 " + data.get("movieNm");
			template.setTitle(title);
			
			StringBuffer sb = new StringBuffer();
			String actor = ((String)movie.get("actor")).replaceAll("\\|", ",");
			actor = actor.substring(0, actor.length()-1);
			sb.append("주연: ").append(actor).append(CValue.newlineStr);
			sb.append("관객 평점: ").append(movie.get("userRating")).append(CValue.newlineStr);
			int audiAcc = Integer.parseInt((String) data.get("audiAcc"));
			sb.append("누적 관객 수: ").append(StringUtil.returnCommaNum(audiAcc)).append("명");
			template.setContents(sb.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return template;
	}
}
