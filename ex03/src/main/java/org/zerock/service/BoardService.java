package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.VO.BoardAttachVO;
import org.zerock.domain.VO.BoardVO;

public interface BoardService {

	public void register(BoardVO board); //등록하기
	public BoardVO get(Long bno); //하나만 가져오기
	public boolean modify(BoardVO board); //수정하기
	public boolean remove(Long bno); //삭제하기
	
	//public List<BoardVO> getList(); //전체목록 가져오기
	public List<BoardVO> getList(Criteria cri); //페이징 사용하기
	
	//굳이 파라미터가 필요없지만 목록과 전체 데이터 개수는 항상 같이 동작하기 때문에 파라미터로 지정합니다.
	public int getTotal(Criteria cri);
	
	//첨부파일  조회
	public List<BoardAttachVO> getAttachList(Long bno);
}
