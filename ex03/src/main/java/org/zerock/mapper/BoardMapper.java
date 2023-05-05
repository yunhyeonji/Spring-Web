package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.zerock.domain.Criteria;
import org.zerock.domain.VO.BoardVO;

public interface BoardMapper {

	//@Select("select * from tb1_board where bno > 0")
	// -> 어노테이션사용 Test를 성공했다면, main/resources폴더에 xml파일을 만들어 사용해보도록 한다.
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//insert
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	
	//read
	public BoardVO read(Long bno);
	
	//delete
	public int delete(Long bno);
	
	//update
	public int update(BoardVO board);
	
	//모든 게시물의 수를 구해서 PageDTO를 구성할 때 전달해주기
	public int getTotalCount(Criteria cri);
	
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
}
