package miny.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
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
        <seq>132686</seq>
        <perforInfo>
            <seq>132686</seq>
            <title>종로 페인터즈 히어로</title>
            <startDate>20131101</startDate>
            <endDate>20181031</endDate>
            <place>서울극장</place>
            <realmName>연극</realmName>
            <area>서울</area>
            <subTitle></subTitle>
            <price>VIP석:60,000원 S석:50,000원 A석:40,000원   </price>
            <contents1>&lt;p&gt;&lt;img title=&quot;페인트히어로즈&quot; style=&quot;border: 0px solid rgb(0, 0, 0); border-image: none; width: 780px; vertical-align: baseline;&quot; alt=&quot;전세계가 극찬한 환상의 미술 공연! PAINTERS 페인터즈 히어로 HERO 2010 문화체육관광부 장관표창 수상 2012 한국관광공사 Korea In Motion Award 우수공연상 수상 2014 외신기자단 올해의 공연상 수상 2015 대한민국 한류대상 대중문화대상 뮤지컬 부문 수상 연간 관객 100만명 돌파! 1320만명 3년 연속 | 관람객 수 | 관람 누적 1위! 공연횟수 10,000회 전세계 17개국 103개 도시에서 입증된 공연! 종로, 서대문, 제주까지 전용관 오픈! 광저우 항저우 상하이 대한민국 러시아 베이징/시안 텐진 / 난징 1 서울/여수/경주/부산 블라디보스톡 프랑스 칭다오/안후이 /충칭/우한 선전 제주/평창/대전/인천/고양 옌타이 / 이창/징저우 창사 대구/구리/전남광주/정선 니스 창춘/타이위안 / 하얼빈 경기광주/거제 / 전주 19개 도시 200회 20개 도시 1400회 이상 이란 미국 아랍 테헤란 태국 워싱턴/LA 에미레이트 일본 3 회 방콕 15회 두바이 도쿄 후쿠오카 20회 베트남 나고야 도야마 오사카 호치민 4회 (마카오 133 회 싱가포르 인도네시아 17회 대만 자카르타 발리 말레이시아 족자카르타 타이페이/가오슝 30회 쿠알라룸푸르 43회 코타키나발루 필리핀 30회 마닐라 5회 세계는 지금! 마술같은 미술 퍼포먼스, 페인터즈 히어로에 열광한다! TOKYO WE ), 넌버벌 공연 최초 일본 도쿄 장기 투어 공연 총 38회, 20,000명 동원 롯본기 블루씨어터 A SINGAPORE 싱가폴 투어 공연 리조트 월드 센토사 극장 PAINTERS 홍콩 투어 공연 KITEC 스타홀 페인터즈 히어로 IERO 중국 6개 도시 투어 공연 북경, 상해, 항주, 광주, 남경, 천진 미국 워싱턴 스미소니언 박물관 Asia After Dark 메인 이벤트 공연 인도네시아 자카르타, 필리핀 마닐라, 이란 테헤란, 카자흐스탄 알마티 | K-Festival 한국 대표 공연 12 Media 슈퍼주니어의 Sorry Soint) KBS1 090 L6 0 MAMESE 2014 MBC Every | 우리 결혼했어요 세계판 시즌2김희철 곽설부 출연 7:50 HCI( 지 CANTATUEST 이 페인터즈 히어로 인기몰이 2016 KBSI NEWS KBS 9시 뉴스 손끝에서 영웅이?... 관광객 홀린 페인터즈 히어로 2016 FUJI TV 메자마시TV 출연 Preview 캔버스를 벗어난 그림!  춤을 추는 드로잉! 4명의 페인터즈가 그려내는 환상적인 아트 퍼포먼스 어린시절 꿈꿔오던 히어로가 드로잉으로 살아나며, 코미디 마임과 함께 즐겁게 표현한 여태껏 본 적 없는 새로운 형식의 아트 퍼포먼스! Light Scratching 그림을 조각한다! 더 빠르게 더 강렬하게!! Action Painting 뛰고, 흔들고, 뿌리고 4개의 화판으로 완성되는 놀라운 작품! Dust Drawing 먼지처럼 사라진 영웅이 되살아난다... 신기루 같은 더스트 드로잉! Speed Drawing 단 한순간도 놓칠 수 없다. 눈 깜짝 할 사이 완성되는 대형 한국화! 333313 RAM 23 330DPID Light Drawing 별빛을 담은 브러쉬! 빛으로 그려내는 밤하늘의 하모니! Marbling Art 물 위에서 화려하게 그려지는 색채의 향연! Battle Drawing 어둠 속 배우들이 보여주는 카리스마 넘치는 터치감!! Seat The Painters HERO STAGE 다 VIP Isl IA Information 공연명 공연장 공연시간 러닝타임 관람가능연령 페인터즈 히어로 종로 전용관 (서울극장 5층) 오후 5시, 8시 (365일 연중무휴) 75 분 문의 및 예약 전 연령 관람 가능 (36개월 미만 무료 입장/좌석없음) (주)펜타토닉 02.766.7848 ticket@pentatonic.kr www.thepainters.co.kr 이메일 홈페이지 공연시작 1시간 전부터 티켓 발권이 가능합니다. 공연시작 30분 전부터 공연장 입장이 가능합니다. 페인터즈 히어로를 더 알고 싶으시다면? f @ThePaintersHERO O @Painters_official Location 탑골공원 유니클로 1,3,5호선 종로3가역 종묘 BUS 신한은행 BUS 세운상가 광장시장 즈 페인터즈 히어로 | 종로 전용관 HERO 페인터즈 히어로 종로전용관 서울시 종로구 돈화문로 13 서울극장 5층 지하철 이용 안내 지하철 1,3,5호선 종로3가역 14번출구 신한은행에서 우회전 후 도보 약 1분 주차 안내 공연티켓을 제시하면 서울극장 주차장 3시간에 3,000원으로 할인 2000cc 초과 및 SUV 차량 주차 불가, 가급적 대중교통 이용바람) pentatonic &quot; src=&quot;http://culture.go.kr/upload/editor_upload/20180725141147640_D99GHQPF.jpg&quot; /&gt;&lt;br /&gt;&lt;/p&gt;</contents1>
            <contents2></contents2>
            <url>http://ticket1.auction.co.kr/VIP/Item?IdPerf=20756</url>
            <phone>02-766-7848 </phone>
            <imgUrl>http://www.culture.go.kr/upload/rdf/18/07/show_2018072514115302317.png</imgUrl>
            <gpsX>126.991664327</gpsX>
            <gpsY>37.5696928067</gpsY>
            <placeUrl>http://www.seoulcinema.com/</placeUrl>
            <placeAddr>서울특별시 종로구 돈화문로 13 (관수동) 서울극장</placeAddr>
            <placeSeq>1369</placeSeq>
        </perforInfo>
    </msgBody>
</response>
*/

@Root(name="response", strict=false)
public class CultureInfo {
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
		private String seq;
		@Element (required=false, name="perforInfo")
		private Performance info;
	}
	
	@Root(name="perforInfo", strict=false)
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
		@Element(required=false)
		private String imgUrl;
		@Element(required=false)
		private String price;
		@Element(required=false)
		private String url;
	}
	
	public String getErrCode() {
		return comMsgHeader.ReturnCode;
	}
	
	public String getErrMsg() {
		return comMsgHeader.ErrMsg;
	}
	
	public Map<String, Object> getPerformance() {
		Map<String, Object> map = new HashMap<>();
		map.put("area", msgBody.info.area);
		map.put("title", msgBody.info.title);
		map.put("seq", msgBody.info.seq);
		map.put("startDate", msgBody.info.startDate);
		map.put("endDate", msgBody.info.endDate);
		map.put("place", msgBody.info.place);
		map.put("realmName", msgBody.info.realmName);
		map.put("imgUrl", msgBody.info.imgUrl);
		map.put("price", msgBody.info.price);
		map.put("url", msgBody.info.url);
		return map;
	}
	
}
