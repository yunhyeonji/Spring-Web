package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Ticket;
import org.zerock.domain.VO.SampleVO;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {

	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE );
		return "안녕하세요";
	}
	
	@GetMapping(value = "/getSample",
			produces = { MediaType.APPLICATION_JSON_UTF8_VALUE,
						 MediaType.APPLICATION_XML_VALUE })
	public SampleVO getSample() {
		return new SampleVO(112,"스타","로드");
	}
	
	/* produces는 생략가능합니다. url 뒤에 .xml 또는 .json을 입력하면 관련페이지로 나타남 */
	@GetMapping(value = "/getSample2")
	public SampleVO getSample2() {
		return new SampleVO(113,"로켓","라쿤");
	}
	
	/* =======LIST======== */
	@GetMapping(value = "/getList")
	public List<SampleVO> getList() {
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i+"First",i+"Last")).collect(Collectors.toList());
			// 내부적으로 1 ~ 10미만까지의 루프를 처리하면서 SampleVO객체를 만들어서 List<SampleVO>로 만들어냄
	}
	
	/* =======MAP======== */
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap() {
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First",new SampleVO(111,"그루트","주니어"));
		
		return map;
	}
	
	@GetMapping(value="/check", params= {"height","weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		SampleVO vo = new SampleVO(0, ""+height, ""+weight);
		
		ResponseEntity<SampleVO> result = null;
		if(height < 150) { //height가 150보다 작으면 502 상태코드와 데이터를 전송합니다.
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	/* ========@PathVariable======== */
	@GetMapping("/product/{cat}/{pid}")
	public String[]	getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		return new String[] {"category: "+cat, "productid: "+pid};
	}
	
/*Ticket=========================================================================*/
	
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert.....ticket " + ticket);
		return ticket;
	}
}
