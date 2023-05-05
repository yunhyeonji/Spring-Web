package org.zerock.service;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {

	@Override
	public Integer doAdd(String str1, String str2) throws Exception {
		// 문자열을 입력받아 변환하여 더하기 연산을 진행함
		return Integer.parseInt(str1) + Integer.parseInt(str2);
	}

}
