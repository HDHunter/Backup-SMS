package com.steam.android.androidsteam;

/**
 * 描述：文件信息类
 * @author yzh
 *
 */
public class FileInfo {
	
	//文件名称
	private String fileName;
	//文件大小
	private Long fileSize;
	//文件路径
	private String filePath;
	
	public FileInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FileInfo(String fileName, Long fileSize, String filePath) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.filePath = filePath;
	}
	
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
