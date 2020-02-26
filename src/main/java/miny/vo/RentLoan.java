package miny.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/*
 <response>
    <header>
        <resultCode>00</resultCode>
        <resultMsg>NORMAL SERVICE.</resultMsg>
    </header>
    <body>
        <items>
            <item>
                <avgLoanRat>1.84</avgLoanRat>
                <avgLoanRat_2>1.84</avgLoanRat_2>
                <bnkNm>부산은행</bnkNm>
                <cnt>1</cnt>
                <loanAmt>24000000</loanAmt>
                <maxLoanRat>1.84</maxLoanRat>
                <minLoanRat>1.84</minLoanRat>
            </item>
            <item>
                <avgLoanRat>2.8</avgLoanRat>
                <avgLoanRat_2>2.79</avgLoanRat_2>
                <bnkNm>우리은행</bnkNm>
                <cnt>4</cnt>
                <loanAmt>286000000</loanAmt>
                <maxLoanRat>2.89</maxLoanRat>
                <minLoanRat>2.74</minLoanRat>
            </item>
        </items>
        <numOfRows>10</numOfRows>
        <pageNo>1</pageNo>
        <totalCount>7</totalCount>
    </body>
</response>
 
 * */

@Root(name="response", strict=false)
public class RentLoan {
	@Element
	private Header header;
	
	@Element(name="body", required=false)
	private Body body;
	
	@Root(name="header", strict=false)
	private static class Header {
		@Element(required=false)
		private String resultCode;
		
		@Element(required=false)
		private String resultMsg;
	}
	
	@Root(name="body", strict=false)
	private static class Body {
		@ElementList (required=false, inline=true)
		private List<Item> list;
		
		@Element(required=false)
		private String totalCount;
		
		@Element(required=false)
		private String items;
	}
	
	@Root(name="item", strict=false)
	private static class Item {
		@Element(name="avgLoanRat", required=false)
		private String avgLoanRat; //산술평균
		@Element(name="avgLoanRat_2", required=false)
		private String avgLoanRat_2; //가중평균
		@Element(name="bnkNm", required=false)
		private String bnkNm; //은행명
		@Element(name="cnt", required=false)
		private String cnt; //건수
		@Element(name="loanAmt", required=false)
		private String loanAmt; //대출 총액
		@Element(name="maxLoanRat", required=false)
		private String maxLoanRat; //최대 금리
		@Element(name="minLoanRat", required=false)
		private String minLoanRat; //최저 금리
	}
	
	public String getTotalCount() {
		return body.totalCount;
	}
	public String getHeaderCode() {
		return header.resultCode;
	}
	public String getHeaderMsg() {
		return header.resultMsg;
	}
	public String getAvgLoanRat(int i) {
		return body.list.get(i).avgLoanRat;
	}
	public Map<String, Object> getItem(int i) {
		Map<String, Object> map = new HashMap<>();
		map.put("bank", body.list.get(i).bnkNm);
		map.put("loanrat", body.list.get(i).avgLoanRat);
		map.put("cnt", body.list.get(i).cnt);
		map.put("amt", body.list.get(i).loanAmt);
		map.put("max", body.list.get(i).maxLoanRat);
		map.put("min", body.list.get(i).minLoanRat);
		return map;
	}
}
