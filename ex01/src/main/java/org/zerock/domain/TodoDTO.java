package org.zerock.domain;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoDTO {
	
	private String title;
	
	// 형식이 맞다면 날짜타입으로 자동으로 변환됨 
	// 이런 형식으로 변환해주는것이 아님!!!!
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date dueDate;
	
	/* 제대로 실행되지 않음 -> 왜 그런건지는 모르겠다..... */
}
