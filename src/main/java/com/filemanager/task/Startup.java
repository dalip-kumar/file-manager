package com.filemanager.task;

import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;


@Configuration
public class Startup {

	
	private Logger log = LoggerFactory.getLogger(Startup.class);
	
	@Value("${rabbitmq.queue}")
	String queueName;

	@Autowired
	private MongoTemplate mangoTemplate;
	
	@Autowired
	private RabbitTemplate jmsTemplate;
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Value("${filePath}")
	private String path;
	

	@Value("${reset}")
	String reset;
	
	@EventListener(ApplicationReadyEvent.class)
	public void startProcess(){
		log.info("Started Jobs with location - "+path);
		rabbitAdmin.purgeQueue(queueName, false);
		
		
		 if(reset.equalsIgnoreCase("Y")){
		    	log.info("### Clear database data ###");
		    	mangoTemplate.remove(new Query(), "file_group_data");
		    }
		
		ForkJoinPool pool = new ForkJoinPool();
		//FolderProcessor system = new FolderProcessor("C:\\Program Files (x86)", "log", jmsTemplate);
		FolderProcessor system = new FolderProcessor(path, jmsTemplate);
		
		pool.execute(system);
	}
}
