package org.zerock.domain.DTO;

import org.zerock.domain.Criteria;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

	private int startpage;
	private int endpage;
	private boolean prev,next;
	
	private int total;
	private Criteria cri; //pageNum & amount 
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
			// Math.ceil(숫자) -> 숫자를 무조건 올림해버리는 함수 
			// 1~10 -> 0.1~1의 숫자로 바뀌고 올림하여 1~1의 숫자로 바뀌게 됨 -> endpage = 10으로 계산완료
		this.endpage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		this.startpage = endpage - 9;
		
			// 실제로 사용할 페이지 개수 => 전체 데이터 / 한 페이지당 데이터 개수 = 사용할 페이지 개수
			// 만약 실제로 사용할 페이지 개수가 지정한 페이지수보다 작으면, 지정한 페이지수를 변경해야 한다.
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		if(realEnd < this.endpage) {
			this.endpage = realEnd;
		}
		
			// 이전이 있나요? 다음이 있나요? 
		this.prev = this.startpage > 1;
		this.next = this.endpage < realEnd;
	}
}
