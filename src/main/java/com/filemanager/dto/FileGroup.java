package com.filemanager.dto;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "file_group_data")
public class FileGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7388449545179411057L;
	
	@Id
	private String id;
	
	private String fileName;
	private long created;
	private long modified;
	private long size;
	private Collection<FileDetail> files;
	
	private long length;

	public FileGroup() {
		super();
	}

	public FileGroup(String fileName, long created, long modified, long size, Collection<FileDetail> files, long length) {
		super();
		this.fileName = fileName;
		this.created = created;
		this.modified = modified;
		this.size = size;
		this.files = files;
		this.length = length;
	}

	public FileGroup(File file){
		this.fileName = file.getName();
		this.created = file.lastModified();
		this.modified = file.lastModified();
		this.size = file.length();
		//this.fullPath = file.getAbsolutePath();
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

	

	@Override
	public String toString() {
		return "FileGroup [id=" + id + ", fileName=" + fileName + ", created=" + created + ", modified=" + modified
				+ ", size=" + size + ", files=" + files + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<FileDetail> getFiles() {
		return files;
	}

	public void setFiles(Collection<FileDetail> files) {
		this.files = files;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

}
