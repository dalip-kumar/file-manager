package com.filemanager.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "duplicate_file_data")
public class DuplicateFileDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7388449545179411057L;
	
	@Id
	private String id;
	
	private String firstFile;
	private String lastFile;

	public DuplicateFileDetail() {
		super();
	}



	public DuplicateFileDetail(String firstFile, String lastFile) {
		super();
		this.firstFile = firstFile;
		this.lastFile = lastFile;
	}



	public String getFirstFile() {
		return firstFile;
	}



	public void setFirstFile(String firstFile) {
		this.firstFile = firstFile;
	}



	public String getLastFile() {
		return lastFile;
	}



	public void setLastFile(String lastFile) {
		this.lastFile = lastFile;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "DuplicateFileDetail [id=" + id + ", firstFile=" + firstFile + ", lastFile=" + lastFile + "]";
	}

}
