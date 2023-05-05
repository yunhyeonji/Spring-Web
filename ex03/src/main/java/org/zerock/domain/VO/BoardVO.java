package org.zerock.domain.VO;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {

	private Long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updateDate;
	
	// 댓글 수 처리
	private int replyCnt;
	
	//첨부파일 처리
	private List<BoardAttachVO> attachList;
}
