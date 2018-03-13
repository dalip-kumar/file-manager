package com.filemanager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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

@RestController
public class FileController {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@RequestMapping(value="/files/duplicate", method = RequestMethod.GET)
	public Collection<FileGroup> getDuplicateFiles(){
		
		List<FileDetail> result = new LinkedList<>();
		Query query = new Query();
		query.addCriteria(Criteria.where("length").gt(1));
		  
		Collection<FileGroup> files = mongoTemplate.find(query, FileGroup.class);
		files.parallelStream().forEach(elm -> result.addAll(elm.getFiles()));
		return files;
	}
	
	@ResponseBody
	@RequestMapping(value="/files/removefile", method = RequestMethod.GET)
	public ResponseEntity<String> deleteFile(@RequestParam("file") String file){
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
		
		return responseEntity;
	}
	
	@ResponseBody
	@RequestMapping(value="/files/loadfile", method = RequestMethod.GET)
	public ResponseEntity<String> loadFile(@RequestParam("file") String fileName){
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
		return responseEntity;
	}
	
	
}
