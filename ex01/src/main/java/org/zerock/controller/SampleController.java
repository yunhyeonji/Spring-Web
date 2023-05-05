package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//형변환을 자동으로 해주는 메소드
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@RequestMapping("")
	public void basic() {
		log.info("basic.....");
	}
	
	@RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
	public void basicGet() {
		log.info("basic get......................");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get......................");
	}
	
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("==============");
		log.info(""+ dto);
		log.info("==============");
		return "ex01";
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("==============");
		log.info("name : " + name);
		log.info("age : " + age);
		log.info("==============");
		return "ex02";
	}
	
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("==============");
		log.info("ids : " + ids);
		log.info("==============");
		return "ex02List";
	}
	
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") ArrayList[] ids) {
		log.info("==============");
		log.info("array ids : " + Arrays.toString(ids));
		log.info("==============");
		return "ex02Array";
	}
	
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto,@ModelAttribute("page") int page) {
		log.info("dto : " + dto);
		log.info("page : " + page);
		
		return "/sample/ex04";
	}
	
	/////////////////// SampleDTOList클래스를 만들어 사용해봅시다. /////////////////////////
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("==============");
		log.info("list data : " + list);
		log.info("==============");
		return "ex02Bean";
	}
	
	////////////////////// TodoDTO클래스를 만들어 사용해봅시다. ////////////////////////////
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("==============");
		log.info("todo : " + todo);
		log.info("==============");
		return "ex03";
	}
	
	///////////////////////////////////////////////////////////////////////////////
	
	//객체 타입
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06..............");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		return dto;
	}
	
	//ResponseEntity 타입
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("/ex07.......");
		
		// {"name" : "홍길동"}
		String msg = "{\"name\": \"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK);
	}
	
	//파일 업로드 controller
	// -> 나타나긴했음, 실제로 업로드를 하기위해선 byte[]타입으로 저장해야함!
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload.......");
	}
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> {
		log.info("---------------------------");
		log.info("name: " + file.getOriginalFilename());
		log.info("size: " + file.getSize());
		});
	}
	
}
