package com.sourcetrace.eses.service;

import com.sourcetrace.eses.entity.DocumentUpload;
import com.sourcetrace.eses.txn.schema.Response;
import com.sourcetrace.eses.txn.schema.Status;
import com.sourcetrace.eses.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	private IUtilService farmerService;

	  @RequestMapping("/")
	  String welcome(HttpServletResponse response) throws IOException {
	    return "It Works";
	  }



	@RequestMapping(value = "/Documents/{id}", method = RequestMethod.GET)
	public HttpEntity<byte[]> createPdf(
			@PathVariable("id") String id) throws IOException {
		DocumentUpload f = farmerService.findDocumentById(Long.valueOf(id));
		byte[] document =f.getContent();
		File file = new File(FileUtil.storeXls(String.valueOf("ss")), f.getDocFileFileName() + ".png");
		FileUtils.writeByteArrayToFile(file, f.getContent());
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentLength(document.length);
		respHeaders.setContentType(MediaType.IMAGE_PNG);
		respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName() );
		return new ResponseEntity<byte[]>(document, respHeaders, HttpStatus.OK);

	}

	@RequestMapping(value="/upload", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public @ResponseBody Response uploadSingleFile (@RequestParam("file") MultipartFile file,@RequestParam("reference") String reference) {
		Status status = new Status();
		status.setCode("00");
		status.setMessage("SUCCESS");
		Response res = new Response();
		try {
			byte[] content =file.getBytes();
			DocumentUpload dc =new DocumentUpload();
			dc.setContent(content);
			dc.setRefCode(reference);
			dc.setDocFileFileName(file.getOriginalFilename());
			dc.setDocFileContentType(FilenameUtils.getExtension(file.getOriginalFilename()));
			dc.setName(file.getOriginalFilename());
			if(Arrays.asList("png","PNG","JPG","jpg","JPEG","jpeg").contains(dc.getDocFileContentType())){
				dc.setFileType(DocumentUpload.fileType.IMAGE.ordinal());
			}else if(Arrays.asList("pdf","PDF").contains(dc.getDocFileContentType())){
				dc.setFileType(DocumentUpload.fileType.PDF.ordinal());
			}else if(Arrays.asList("mp3","MP3").contains(dc.getDocFileContentType())){
				dc.setFileType(DocumentUpload.fileType.AUDIO.ordinal());
			}else if(Arrays.asList("mp4","MP4").contains(dc.getDocFileContentType())){
				dc.setFileType(DocumentUpload.fileType.VIDEO.ordinal());
			}
			dc.setCreatedDate(new Date());
			dc.setCreatedUser("FIleUpload");
			dc.setType(dc.getFileType());
			farmerService.save(dc);

		} catch (Exception e) {
			status.setCode("44");
			status.setMessage("SERVER ERROR");
			e.printStackTrace();
		}


		res.setStatus(status);
		return res;

	}

}
