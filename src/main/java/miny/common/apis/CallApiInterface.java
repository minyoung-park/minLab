package miny.common.apis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import miny.vo.Culture;
import miny.vo.CultureInfo;
import miny.vo.RentLoan;
import miny.vo.ResultBoxoffice;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface CallApiInterface {
	
	@GET("https://openapi.naver.com/v1/search/news.json")
	Call<HashMap<String, Object>> getNaverNews(@QueryMap Map<String,Object> params, @HeaderMap Map<String, String> headers);
	
	@GET("http://tts.news.naver.com/article/{oid}/{aid}/summary")
	Call<HashMap<String, Object>> getSummaryNews(@Path("oid") String oid, @Path("aid") String aid);
	
	@GET("http://news.naver.com/main/read.nhn")
	//http://news.naver.com/main/read.nhn?oid=001&aid=0009761108&m_view=1
	Call<String> getNewsComments(@QueryMap Map<String,Object> params);
	
	// url shortner
	@POST("http://textfactory.ai/actions/getShortUrls")
	Call<HashMap<String, Object>> urlShortner(@Query("urls") List<String> params);
	
	@GET("http://www.diningcode.com/list.php")//isearch.php
	Call<String> getRestaurantDC(@QueryMap Map<String,Object> params);
	@GET("https://www.instagram.com/explore/tags/{tag}")
	Call<String> getInstagramTag(@Path("tag") String tag);
	@GET("http://cafecurator.com/")
	Call<String> getCafe();
	//페이스북 장소검색 categories=["FOOD_BEVERAGE"]&
	@GET("https://graph.facebook.com/v2.11/search?type=place&access_token=EAAVS44dv6zIBACVwr3pSXbqXMihk8BUXkljy5gUvY6He6lx4sx3lASHNZCBk0TgK54gSyHwshyi3gRzZBgRW9eZBg0W6e569W4fzLCIcHKrh93JshNWkqM2duep91RAN5Y806moMuSdfmAqnuuFKCY6Co7FwNRHDnF9ytptxwZDZD")
	Call<HashMap<String,Object>> getFBlocationID(@QueryMap Map<String,Object> params);
	//슬랙 메시지 보내기
	@POST("https://slack.com/api/chat.postMessage?channel=prj_takobot&token=xoxb-236933293655-oiy96kVC82J7uK91bY9EHlqg")
	Call<Map<String, Object>> tacoBotSend(@QueryMap Map<String,Object> params);
	
	@POST("https://graph.facebook.com/v2.6/me/messages?access_token=EAAVS44dv6zIBACVwr3pSXbqXMihk8BUXkljy5gUvY6He6lx4sx3lASHNZCBk0TgK54gSyHwshyi3gRzZBgRW9eZBg0W6e569W4fzLCIcHKrh93JshNWkqM2duep91RAN5Y806moMuSdfmAqnuuFKCY6Co7FwNRHDnF9ytptxwZDZD")
	Call<HashMap<String,Object>> sendFBog(@QueryMap Map<String,Object> params);
	
	///////로또 관련
	@GET("http://nlotto.co.kr/common.do?method=getLottoNumber") //회차 당첨번호 조회
	Call<HashMap<String, Object>> getWonNum(@QueryMap Map<String, Object> params);
	@GET("http://nlotto.co.kr/gameResult.do?method=myWinSearch") //내 번호 당첨 조회
	Call<HashMap<String, Object>> chkMyNum(@QueryMap Map<String, Object> params);
	@GET("http://nlotto.co.kr/gameResult.do?method=byWin") //최신 당첨번호 조회
	Call<String> lastgetWonNum();
	
	//공연,전시회 정보
	//ServiceKey=1o73aOvW%2BnZRNXOMp3hz3rdQ7FWiyoynNuPYvsTw8X76SDT752EapwOQy2WdFmAODdh13u4Qm52zIiq70HNKEw%3D%3D
	@GET("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/area?ServiceKey=1o73aOvW%2BnZRNXOMp3hz3rdQ7FWiyoynNuPYvsTw8X76SDT752EapwOQy2WdFmAODdh13u4Qm52zIiq70HNKEw%3D%3D&rows=50")
	Call<Culture> getCultureInfoArea(@QueryMap Map<String, Object> params);
	@GET("http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?ServiceKey=1o73aOvW%2BnZRNXOMp3hz3rdQ7FWiyoynNuPYvsTw8X76SDT752EapwOQy2WdFmAODdh13u4Qm52zIiq70HNKEw%3D%3D")
	Call<CultureInfo> getCultureInfo(@QueryMap Map<String, Object> params);
	
	//영화 박스오피스
	@GET("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=570b70acb660c92d9c8a4b0f4e33810d&weekGb=0")
	Call<ResultBoxoffice> getWeeklyBoxoffice(@QueryMap Map<String,Object> params);
	@GET("https://openapi.naver.com/v1/search/movie.json")
	Call<HashMap<String, Object>> getNaverMovie(@QueryMap Map<String,Object> params, @HeaderMap Map<String, String> headers);
	@Headers("referer: https://movie.naver.com/")
	@GET("https://movie.naver.com/movieChartJson.nhn?type=RESERVE")
	Call<Map<String, Object>> getTicketingMovieChart();
	
	@GET("http://api.hf.go.kr:8090/service/rest/rentloanratmultidim/getRentLoanRatMultiDim?serviceKey=1o73aOvW%2BnZRNXOMp3hz3rdQ7FWiyoynNuPYvsTw8X76SDT752EapwOQy2WdFmAODdh13u4Qm52zIiq70HNKEw%3D%3D")
	Call<RentLoan> getLoanInfo(@QueryMap Map<String, Object> params); //전세자금대출 금리 정보
}
