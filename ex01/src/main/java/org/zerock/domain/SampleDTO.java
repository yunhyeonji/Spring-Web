package org.zerock.domain;

import lombok.Data;

/* Controller에서 파라미터 수집은 어떻게 하는걸까? */
// 객체 형식으로 파라미터를 받아서 출력할 수 있음
// SampleController -> ex01

@Data
public class SampleDTO {
	
	private String name;
	private int age;
}
