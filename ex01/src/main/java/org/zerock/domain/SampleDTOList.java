package org.zerock.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/* 전달하는 데이터가 객체타입이고 여러개를 처리해야 한다? */
// 객체를 포함하는 List클래스를 설계함

@Data
public class SampleDTOList {
	
	private List<SampleDTO> list;
	
	public SampleDTOList() {
		list = new ArrayList<>();
	}
}
