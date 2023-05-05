package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
@Service
@AllArgsConstructor
public class BoardServiceimpl implements BoardService{

	@Setter(onMethod_= @Autowired)
	private BoardMapper mapper;

	@Override
	public void register(BoardVO board) {
		//등록하기
		log.info("register...." + board);		
		mapper.insertSelectKey(board);
	}

	@Override
	public BoardVO get(Long bno) {
		// 하나만 조회하기
		log.info("get......" + bno);
		return mapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {

		log.info("modify......" + board);

		return mapper.update(board) == 1;
	}

	@Override
	public boolean remove(Long bno) {
		// 삭제하기
		log.info("remove....." + bno);
		return mapper.delete(bno) == 1;
	}

//	@Override
//	public List<BoardVO> getList() {
//		// 전체목록 가져오기
//		log.info("getList....");
//		return mapper.getList();
//	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		// 페이징 사용하며 전체목록 가져오기
		log.info("get List with criteria: " + cri);
		
		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		// 모든 개수를 구합니다.
		log.info("get Total Count");
		return mapper.getTotalCount(cri);
	}
}
