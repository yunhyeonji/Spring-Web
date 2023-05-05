package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
/* Form을 이용해서 파일 업로드 */
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
		
	@PostMapping("/uploadFormAction")
	@ResponseBody
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "C:\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			 log.info("-------------------------------------");
			 log.info("Upload File Name: " +multipartFile.getOriginalFilename());
			 log.info("Upload File Size: " +multipartFile.getSize());
			 
			 File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			 
			 try {
				 multipartFile.transferTo(saveFile);
			 }catch (Exception e) {
				log.error(e.getMessage());
			}
			 
		}
	}

/* Ajax를 이용하여 파일 업로드 */
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	// 업로드 함
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {

		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();
		String uploadFolder = "C:\\upload";
		
		String uploadFolderPath = getFolder();
		//make folder...
		File uploadPath = new File(uploadFolder,uploadFolderPath);
		log.info("upload path: "+uploadPath);
		
		if(uploadPath.exists() == false) {
			//make yyyy/MM/dd folder
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			AttachFileDTO attachFileDTO = new AttachFileDTO();
			
			log.info("-------------------------------------");
			log.info("Upload File Name: " +multipartFile.getOriginalFilename());
			log.info("Upload File Size: " +multipartFile.getSize());
			 
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName =  uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			 	// 파일명에 경로가 있다면 다 자르고 실제 이름만 가져오겠다!
			log.info("only file name: " + uploadFileName);
			attachFileDTO.setFileName(uploadFileName);
			 
			 	// 파일이름이 겹치는것을 방지하고자 시간을 넣음
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			 
			try {
				//File saveFile = new File(uploadFolder, uploadFileName);
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				 
				attachFileDTO.setUuid(uuid.toString());
				attachFileDTO.setUploadPath(uploadFolderPath);
				
				 	// 파일타입이 이미지인지 확인 후 맞다면 섬네일을 제작함
				if(checkImageType(saveFile)) {
					
					attachFileDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				
				list.add(attachFileDTO);
				 
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		} // end for문
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 한 폴더 내에 너무 많은 파일의 생성 문제해결
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}
	
	// 특정한 파일이 이미지 타입인지 검사하는 메소드
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//이미지 데이터를 전송하는 코드(화면에 보여줌)
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName: "+fileName);
		
		File file = new File("C:\\upload\\" + fileName);
		log.info("file: "+ file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//다운로드하기
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		
		log.info("download file: "+ fileName);		
		Resource resource = new FileSystemResource("C:\\upload\\"+fileName);
		log.info("resource: "+resource);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		
		HttpHeaders headers = new HttpHeaders();
		try {
			String downloadName = null;
			if(userAgent.contains("Trident")) {
				
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replace("\\+", " ");
				
			}else if(userAgent.contains("Edg")) {
				
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
				log.info("Edge name: "+downloadName);
			}else {
				
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
				
			}
			// attachment 와 filename= 이 같이 오면 그 파일을 다운받으라는 의미
			headers.add("content-Disposition", "attachment; filename=" + downloadName);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile: " + fileName);
		
		File file;
		try {
			file = new File("C:\\upload\\" + URLDecoder.decode(fileName,"UTF-8"));
			file.delete();
			
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("largeFileName: "+largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	
}
