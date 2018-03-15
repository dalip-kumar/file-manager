package com.filemanager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.filemanager.dto.FileDetail;
import com.filemanager.dto.FileGroup;
import com.filemanager.task.Startup;

@RestController
public class FileController {

	private Logger log = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/files/duplicate", method = RequestMethod.GET)
	public Collection<FileGroup> getDuplicateFiles(){
		log.info("Start FileController.getDuplicateFiles");
		List<FileDetail> result = new LinkedList<>();
		Query query = new Query();
		query.addCriteria(Criteria.where("length").gt(1));
		  
		Collection<FileGroup> files = mongoTemplate.find(query, FileGroup.class);
		files.stream().forEach(elm -> result.addAll(elm.getFiles()));
		log.info("End FileController.getDuplicateFiles");
		return files;
	}
	
	@ResponseBody
	@RequestMapping(value="/files/removefile", method = RequestMethod.GET)
	public ResponseEntity<String> deleteFile(@RequestParam("file") String file){
		log.info("Start FileController.deleteFile");
		File file1 = new File(file);
		String result= "";
		if(file1.exists()){
			file1.delete();
			result =  "File Delected";
		}else{
			result = "Not Found "+file1.getAbsolutePath();
		}
		ResponseEntity<String> responseEntity = new ResponseEntity<>(result,
                HttpStatus.OK);
		log.info("End FileController.deleteFile");
		return responseEntity;
	}
	
	@ResponseBody
	@RequestMapping(value="/files/loadfile", method = RequestMethod.GET)
	public ResponseEntity<String> loadFile(@RequestParam("file") String fileName){
		log.info("Start FileController.loadFile");
		String base64Image = "";
		File file = new File(fileName);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
			base64Image="Not found";
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
			base64Image="Error found";
		}
		 ResponseEntity<String> responseEntity = new ResponseEntity<>(base64Image,
                 HttpStatus.OK);
		 log.info("End FileController.loadFile");
		return responseEntity;
	}
	
	
}
