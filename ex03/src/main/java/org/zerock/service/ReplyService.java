package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.DTO.ReplyPageDTO;
import org.zerock.domain.VO.ReplyVO;

public interface ReplyService {

	public int register(ReplyVO vo); //등록하기
	public ReplyVO get(Long rno); //하나만 가져오기
	public int modify(ReplyVO vo); //수정하기
	public int remove(Long rno); //삭제하기
	
	public List<ReplyVO> getList(Criteria cri, Long bno); //페이징 사용하기
	
	public ReplyPageDTO getListpage(Criteria cri, Long bno);
}
