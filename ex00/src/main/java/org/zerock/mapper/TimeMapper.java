package org.zerock.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {

	@Select("select sysdate from dual")
	public String getTime();
	// mapper을 작성해주었다면..? mybatis가 작동할때 mapper가 실행되나봅니다..?
	
	public String getTime2();
	//SQL이 복잡해지고 많아진다면, 어노테이션(Select)보다는 xml을 사용하는것이 좋음
}
