package miny.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/*
<response>
<comMsgHeader>
   <RequestMsgID></RequestMsgID>
   <ResponseTime>2017-11-21 12:14:18.1418</ResponseTime>
   <ResponseMsgID></ResponseMsgID>
   <SuccessYN>Y</SuccessYN>
   <ReturnCode>00</ReturnCode>
   <ErrMsg>NORMAL SERVICE.</ErrMsg>
</comMsgHeader>
<msgBody>
   <totalCount>3</totalCount>
   <cPage>1</cPage>
   <rows>10</rows>
   <sido>경남</sido>
   <from>20171101</from>
   <to>20171231</to>
   <keyword>뮤지컬</keyword>
   <sortStdr>2</sortStdr>
   <perforList>
       <seq>122117</seq>
       <title>가족뮤지컬 핑크퐁과 상어가족 - 창원</title>
       <startDate>20171202</startDate>
       <endDate>20171203</endDate>
       <place>3.15아트센터</place>
       <realmName>연극</realmName>
       <area>경남</area>
       <thumbnail>http://www.culture.go.kr/upload/rdf/17/11/show_201711101465699967.JPG</thumbnail>
       <gpsX>128.577108277</gpsX>
       <gpsY>35.225817064</gpsY>
   </perforList>
</msgBody>
</response>
*/

@Root(name="response", strict=false)
public class Culture {
	@Element(name="comMsgHeader")
	private Header comMsgHeader;
	
	@Element(name="msgBody", required=false)
	private Body msgBody;
	
	@Root(name="comMsgHeader", strict=false)
	private static class Header {
		@Element(required=false)
		private String ResponseTime;
		@Element(required=false)
		private String ResponseMsgID;
		@Element(required=false)
		private String SuccessYN;
		@Element(required=false)
		private String ReturnCode;
		@Element(required=false)
		private String ErrMsg;
	}
	
	@Root(name="msgBody", strict=false)
	private static class Body {
		@Element(required=false)
		private String totalCount;
		@Element(required=false)
		private String cPage;
		@Element(required=false)
		private String rows;
		@Element(required=false)
		private String sido;
		@Element(required=false)
		private String from;
		@Element(required=false)
		private String to;
		@Element(required=false)
		private String keyword;
		@Element(required=false)
		private String sortStdr;
		@ElementList (required=false, inline=true)
		private List<Performance> list;
	}
	
	@Root(name="perforList", strict=false)
	private static class Performance {
		@Element(required=false)
		private String seq;
		@Element(required=false)
		private String title;
		@Element(required=false)
		private String startDate;
		@Element(required=false)
		private String endDate;
		@Element(required=false)
		private String place;
		@Element(required=false)
		private String realmName;
		@Element(required=false)
		private String area;
		@Element(required=false)
		private String thumbnail;
		@Element(required=false)
		private String gpsY;
		@Element(required=false)
		private String gpsX;
	}
	
	public String getErrCode() {
		return comMsgHeader.ReturnCode;
	}
	
	public String getErrMsg() {
		return comMsgHeader.ErrMsg;
	}
	
	public String getTotalCount() {
		return msgBody.totalCount;
	}
	
	public Map<String, Object> getPerformance(int i) {
		Map<String, Object> map = new HashMap<>();
		map.put("area", msgBody.list.get(i).area);
		map.put("title", msgBody.list.get(i).title);
		map.put("seq", msgBody.list.get(i).seq);
		map.put("startDate", msgBody.list.get(i).startDate);
		map.put("endDate", msgBody.list.get(i).endDate);
		map.put("place", msgBody.list.get(i).place);
		map.put("realmName", msgBody.list.get(i).realmName);
		return map;
	}
	
}
