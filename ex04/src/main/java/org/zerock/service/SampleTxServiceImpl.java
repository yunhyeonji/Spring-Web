package org.zerock.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService {
	
	@Setter(onMethod_ = { @Autowired })
	private Sample1Mapper mapper1;
	  
	@Setter(onMethod_ = { @Autowired })
	private Sample2Mapper mapper2;
	
	/*	@Transactional
	 *  두 개중에 하나라도 실패라면 두개 모두 insert가 되지않도록 한다.
	 *  insertCol1이 실행이 되더라도 2가 실패하면 rollback함수를 실행하여 되돌아 온다.
	 */
	@Transactional
	@Override
	public void addData(String value) {
		
	    log.info("mapper1....................");
	    mapper1.insertCol1(value);
	    
	    log.info("mapper2.....................");
	    mapper2.insertCol2(value);
	    
	    log.info("end..........................");
		
	}

}
