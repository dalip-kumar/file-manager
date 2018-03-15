package com.filemanager.task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
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
	
	
	 @Autowired
	private ApplicationArguments applicationArguments;
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void startProcess(){
        System.out.println(applicationArguments.getNonOptionArgs());
        
		log.info("Started Jobs with location - "+path);
		rabbitAdmin.purgeQueue(queueName, false);
		
		
		 if(reset.equalsIgnoreCase("Y")){
		    	log.info("### Clear database data ###");
		    	mangoTemplate.remove(new Query(), "file_group_data");
		    }
		Set<String> extn = new HashSet<String>();
		
		List<String> fileExtn = applicationArguments.getOptionValues("fileExtn");
		if(fileExtn != null && !fileExtn.isEmpty()){
			String[] extns = fileExtn.get(0).split(",");
			Arrays.stream(extns).forEach(s-> extn.add(s.toUpperCase()));
		}
		ForkJoinPool pool = new ForkJoinPool();
		//FolderProcessor system = new FolderProcessor("C:\\Program Files (x86)", "log", jmsTemplate);
		
		Arrays.stream(path.split(",")).forEach(s-> {
			FolderProcessor system = new FolderProcessor(s, jmsTemplate, extn);
			pool.execute(system);	
		});
		
		log.info("Completed Jobs with location");
	}
}
