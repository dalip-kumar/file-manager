package com.filemanager.dto;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

//@Document(collection = "file_data")
public class FileDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7388449545179411057L;
	
	/*@Id
	private String id;*/
	
	private String fileName;
	private long created;
	private long modified;
	private long size;
	private String fullPath;
	BasicFileAttributes basicFileAttributes;
	public FileDetail() {
		super();
	}

	public FileDetail(String fileName, long created, long modified, long size, String fullPath) {
		super();
		this.fileName = fileName;
		this.created = created;
		this.modified = modified;
		this.size = size;
		this.fullPath = fullPath;
	}

	public FileDetail(File file) throws IOException{
		
		basicFileAttributes = Files.readAttributes(file.toPath(),
				BasicFileAttributes.class);
		this.fileName = file.getName();
		this.created = basicFileAttributes.creationTime().toMillis();
		this.modified = basicFileAttributes.lastModifiedTime().toMillis();
		this.size = file.length();
		this.fullPath = file.getAbsolutePath();
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	@Override
	public String toString() {
		return "FileDetail [fileName=" + fileName + ", created=" + created + ", modified=" + modified + ", size=" + size
				+ ", fullPath=" + fullPath + "]";
	}

	/*public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/

}
