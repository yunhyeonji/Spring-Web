<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Upload with Ajax</h1>
	
	<div class="bigPictureWrapper">
		<div class="bigPicture">
		</div>
	</div>
<style>
.uploadResult {
	width: 100%;
	background-color: gray;
}

.uploadResult ul {
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li {
	list-style: none;
	padding: 10px;
	align-content: center;
	text-align: center;
}

.uploadResult ul li img {
	width: 100px;
}
.uploadResult ul li span{
	color: white;
}

.bigPictureWrapper {
  position: absolute;
  display: none;
  justify-content: center;
  align-items: center;
  top:0%;
  width:100%;
  height:100%;
  background-color: gray; 
  z-index: 100;
  background: rgba(225, 225, 225, 0.5);
}

.bigPicture {
  position: relative;
  display:flex;
  justify-content: center;
  align-items: center;
}

.bigPicture img{
	width: 600px;
}
</style>	
	<div class='uploadDiv'>
		<input type='file' name='uploadFile' multiple>
	</div>
	
	<div class='uploadResult'>
		<ul>
			
		</ul>
	</div>
	
	<button id='uploadBtn'>Upload</button>
	
<script src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous">
</script>
<script>
/* 이미지를 크게 볼 수 있습니다. */
function showImage(fileCallPath){
	  
	 //alert(fileCallPath);
	
	 $(".bigPictureWrapper").css("display","flex").show();
	  
	 $(".bigPicture")
	 .html("<img src='/display?fileName="+ encodeURI(fileCallPath)+"'>")
	 .animate({width:'100%', height: '100%'}, 1000);

}
$(document).ready(function(){
	
/* 크게 본 이미지를 다시 사라지게 만들어 줍니다. */
$(".bigPictureWrapper").on("click",function(e){
	$(".bigPicture").animate({width:'0%',height:'0%'}, 1000);
	setTimeout(() => {
		$(this).hide();
	}, 1000);
});

/* 파일의 확장자나 크기의 사전처리 */
	var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var maxSize = 5242880; //5MB

	function checkExtension(fileName, fileSize) {

		if (fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}

		if (regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		return true;
	}
	
/* uploadResult에 업로드된 파일 나열하기 & 다운로드받기 */
	var uploadResult = $(".uploadResult ul");
	
	function showUploadedFile(uploadResultArr){
		var str = "";
		
		$(uploadResultArr).each(function(i,obj){
			if(!obj.image){
				var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
				
				var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
		           // 이미지가 아니면 클릭시 다운로드 되도록 한다.
		          str += "<li><div><a href='/download?fileName="+fileCallPath+"'>"+
		          "<img src='/resources/img/attach.png'>"+obj.fileName+"</a>"+
		          "<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>"+
		          "</div></li>";
		          
			}else{
				//str += "<li>"+ obj.fileName +"</li>";
				
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);							
				
				var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
				originPath = originPath.replace(new RegExp(/\\/g),"/");
				
				str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName="+fileCallPath+"'></a>"+
						"<span data-file=\'"+fileCallPath+"\' data-type='image'>x</span>"+
						"<li>";
			}
		});
		
		uploadResult.append(str);
	}
/* 삭제합니다. */
$(".uploadResult").on("click","span",function(e){
	var targetFile = $(this).data("file");
	var type = $(this).data("type");
	console.log(targetFile);
	
	$.ajax({
		url: '/deleteFile',
		data: {fileName: targetFile, type:type},
		dataType:'text',
		type:'POST',
		success: function(result){
			alert(result);
		}
	}); //end ajax
});
	
/* upload 버튼 */
	var cloneObj = $(".uploadDiv").clone();
	
	$('#uploadBtn').on('click',function(e){
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;

		console.log(files);
		
		for(var i=0;i<files.length;i++){
			if(!checkExtension(files[i].name,files[i].size)){
				return false;
			}
			// name이 uploadFile이고  value가 files[i]인 input을 추가합니다.
			formData.append("uploadFile",files[i]);
		}
		
		/* 버튼을 누르면 post방식으로 url로 이동합니다. */
		$.ajax({
			url:'/uploadAjaxAction',
			processData: false,
			contentType: false,
			data:formData,
			type:'POST',
			dataType:'json',
			success:function(result){
				console.log(result);
				showUploadedFile(result);
				$(".uploadDiv").html(cloneObj.html());
			}
		}); //end ajax
		
	});

});
</script>
</body>
</html>