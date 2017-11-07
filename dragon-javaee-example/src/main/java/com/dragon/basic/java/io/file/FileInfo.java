package com.dragon.basic.java.io.file;

import java.io.Serializable;
import java.util.Date;

public class FileInfo implements Serializable {

	private static final long serialVersionUID = -8138134506156686125L;

	private String fileName; // 文件名
	private boolean isDir; // 是否是目录
	private String fileType; // 文件类型
	private long fileLen; // 文件长度
	private String fileLength; // 转化后的文件长度
	private String filePath; // 文件路径
	private Date lastModifiedTime; // 文件最后修改日期
	private boolean isHidden; // 是否隐藏文件
	private String author; // 作者
	private String version; // 版本
	private String remark; // 备注

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getFileLen() {
		return fileLen;
	}

	public void setFileLen(long fileLen) {
		this.fileLen = fileLen;
	}

	public String getFileLength() {
		return fileLength;
	}

	public void setFileLength(String fileLength) {
		this.fileLength = fileLength;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}