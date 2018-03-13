package com.filemanager.task;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.filemanager.dto.DuplicateFileDetail;
import com.filemanager.dto.FileDetail;
import com.filemanager.dto.FileGroup;

public class FileConsumer {

	private Logger logger = LoggerFactory.getLogger(FileConsumer.class);

	@Value("${rabbitmq.queue}")
	String queueName;

	@Autowired
	private MongoTemplate mangoTemplate;
	
	
	  @RabbitListener(queues="${rabbitmq.queue}")
	  public void receive(FileDetail message) {
	    logger.info(Thread.currentThread().getName()+" Received message '{}'", message);
	   
	    FileGroup file = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("fileName").is(message.getFileName()));
		query.addCriteria(Criteria.where("size").is(message.getSize()));
		  
		file = mangoTemplate.findOne(query, FileGroup.class);
		
		if(file == null){
			Collection<FileDetail> files = new ArrayList<>();
			files.add(message);
			FileGroup grp = new FileGroup(message.getFileName(), message.getCreated(), message.getModified(), message.getSize(), files, files.size());
			mangoTemplate.save(grp);
		}else{
			
			file.getFiles().add(message);
			file.setLength(file.getFiles().size());
			mangoTemplate.save(file);
		}
	  }
}
