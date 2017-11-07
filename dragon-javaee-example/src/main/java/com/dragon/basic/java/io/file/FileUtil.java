package com.dragon.basic.java.io.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具类
 * Title:  <br>
 * Description: <br>
 * Copyright: Alex Copyright (C) 2012 <br>
 * @author: Alex <br>
 * @e-mail: yumin_860619@126.com <br>
 * @version 1.0 <br>
 * @creatdate 2012-12-13 上午10:09:51 <br>
 *
 */
public class FileUtil {
	
	/**
	 * 创建单个文件夹
	 * 注：（1）mkdir()只能在已经存在的目录中创建创建文件夹。 
	 *    （2）mkdirs()可以在不存在的目录中创建文件夹。
	 * @param dir
	 * @param ignoreExist：true表示如果文件夹存在就不再创建；false是重新创建
	 * @throws IOException 
	 */
	public static void createDir(String dir, boolean ignoreExist) throws IOException {
		File file = new File(dir);
		if(file.exists() && ignoreExist)
			return;
		if(!file.mkdir())
			throw new IOException("Cannot create the directory=【"+ dir +"】");
	}
	
	/**
	 * 创建多个文件夹
	 * 注：（1）mkdir()只能在已经存在的目录中创建创建文件夹。 
	 *    （2）mkdirs()可以在不存在的目录中创建文件夹。
	 * @param dir
	 * @param ignoreExist：true表示如果文件夹存在就不再创建；false是重新创建
	 * @throws IOException 
	 */
	public static void createDirs(String dir, boolean ignoreExist) throws IOException {
		File file = new File(dir);
		if(file.exists() && ignoreExist)
			return;
		if(!file.mkdirs())
			throw new IOException("Cannot create the directory=【"+ dir +"】.");
	}
	
	/**
	 * 删除文件夹及其下面的子文件夹
	 * @param dir
	 * @throws IOException 
	 */
	public static void deleteDir(File dir) throws IOException {
		if(dir.isFile())
			throw new IOException(dir + " is not a director.");
		File[] listFiles = dir.listFiles();
		if(null != listFiles && listFiles.length > 0) {
			for(File f : listFiles) {
				if(f.isFile())
					f.delete();
				else
					deleteDir(f);
			}
		}
		if(!dir.delete())
			throw new IOException("Cannot delete the directory.");
	}
	
	/**
	 * 根据文件名删除文件
	 * @param fileName
	 * @throws IOException 
	 */
	public static void deleteFile(String fileName) throws IOException {
		File file = new File(fileName);
		if(file.isDirectory())
			throw new IOException(fileName + " is not a file.");
		if(!file.exists())
			throw new IOException(fileName + " file is not exists.");
		if(!file.delete())
			throw new IOException("Cannot delete file. fileName=【"+ fileName +"】.");
	}
	
	/**
	 * 获取指定目录的文件大小，包含子目录
	 * @param dir
	 * @return
	 * @throws IOException 
	 */
	public static long getDirLength(File dir) throws IOException {
		if(dir.isFile())
			throw new IOException(dir + " is not a director.");
		if(!dir.exists())
			throw new IOException(dir + " is not exists.");
		
		long size = 0;
		File[] listFiles = dir.listFiles();
		if(null != listFiles && listFiles.length > 0) {
			for(int i = 0; i < listFiles.length; i++) {
				File tmpFile = listFiles[i];
				if(tmpFile.isDirectory())
					getDirLength(tmpFile);
				else
					size += tmpFile.length();
			}
		}
		
		return size;
	}
	
	/**
	 * 获取指定文件目录下的文件信息
	 * @param dir
	 * @return
	 * @throws IOException 
	 */
	public static List<FileInfo> getFileInfo(String dir) throws IOException {
		File file = new File(dir);
		if(file.isFile())
			throw new IOException(dir + " is not a director.");
		if(!file.exists())
			throw new IOException(dir + " is not exists.");
		
		List<FileInfo> rtList = null;
		File[] files = file.listFiles();
		if(null != files && files.length > 0) {
			rtList = new ArrayList<FileInfo>();
			for(int i = 0; i < files.length; i++) {
				FileInfo fileInfo = new FileInfo();
				File f = files[i];
				fileInfo.setFileName(f.getName());
				fileInfo.setDir(f.isDirectory());
				if(f.isFile() && f.getName().lastIndexOf(".") != -1) {
					fileInfo.setFileType(f.getName().substring(f.getName().lastIndexOf(".")));
				} else {
					fileInfo.setFileType("文件夹");
				}
				long tmpFLen = f.length();
				fileInfo.setFileLen(tmpFLen);
				if(tmpFLen/1024/1024/1024 > 0) {
					fileInfo.setFileLength(tmpFLen/1024/1024/1024 + "G");
				} else if(tmpFLen/1024/1024 > 0) {
					fileInfo.setFileLength(tmpFLen/1024/1024 + "M");
				} else if(tmpFLen/1024 > 0) {
					fileInfo.setFileLength(tmpFLen/1024 + "K");
				} else {
					fileInfo.setFileLength(tmpFLen + "byte");
				}
				fileInfo.setFilePath(f.getAbsolutePath().replaceAll("[\\\\]", "/"));
				fileInfo.setLastModifiedTime(new Date(f.lastModified()));
				fileInfo.setHidden(f.isHidden());
				fileInfo.setAuthor(null);
				fileInfo.setVersion(null);
				fileInfo.setRemark(null);
				
				rtList.add(fileInfo);
			}
		}
		
		return rtList;
	}
	
	/**
	 * Write content to a fileName with the destEncoding 写文件.如果此文件不存在就创建一个.
	 * @param content
	 * @param fileName
	 * @param destEncoding
	 */
	public static void writeFile(String content, String fileName, String destEncoding) {
		File file = null;
		try {
			file = new File(fileName);
			//如果文件不存在，则创建文件
			if(!file.exists()) {
				if(!file.createNewFile())
					throw new IOException("create "+ fileName +" file failure.");
			}
			//不是文件的情况
			if(!file.isFile())
				throw new IOException(fileName + " is not a file.");
			//是文件，但是文件不可写的情况
			if(!file.canWrite())
				throw new IOException(fileName + " is only-read file.");
		} catch(IOException e) {
		}
		
		BufferedWriter out = null;
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			out = new BufferedWriter(new OutputStreamWriter(fos, destEncoding));
			
			out.write(content);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 压缩文件
	 * @param srcFileName
	 * @param destFileName
	 * @param overwrite
	 */
	public static void zipFile(String srcFileName, String destFileName, boolean overwrite) {
		try {
			File srcFile = new File(srcFileName);
			//首先判断源文件是否存在
			if(!srcFile.exists())
				throw new FileNotFoundException("Cannot find the source file "+ srcFile.getAbsolutePath());
			//判断源文件是否可读
			if(!srcFile.canRead())
				throw new IOException("Cannot read the source file "+ srcFile.getAbsolutePath());
			
			if(null == destFileName || "".equals(destFileName.trim())) {
				destFileName = srcFileName + ".zip";
			} else {
				destFileName += ".zip";
			}
			File destFile = new File(destFileName);
			//如果目标文件存在就不覆盖
			if(!overwrite && destFile.exists()) {
				return;
			} else {
				//如果要覆盖已经存在的目标文件，首先判断目标文件是否可写
				if(destFile.exists()) {
					if(!destFile.canWrite())
						throw new IOException("Cannot write the destination file "+ destFile.getAbsolutePath());
				} else {
					//不存在则创建一个新文件
					if(!destFile.createNewFile())
						throw new IOException("Cannot create the destination file "+ destFile.getAbsolutePath());
				}
			}
				
			BufferedInputStream inputStream = null;
			BufferedOutputStream outputStream = null;
			ZipOutputStream zipOutputStream = null;
			byte[] block = new byte[1024];
			try { 
	            inputStream = new BufferedInputStream(new FileInputStream(srcFile)); 
	            outputStream = new BufferedOutputStream(new FileOutputStream(destFile)); 
	            zipOutputStream = new ZipOutputStream(outputStream); 
	             
	            zipOutputStream.setComment("通过java程序压缩的"); 
	            ZipEntry zipEntry = new ZipEntry(srcFile.getName()); 
	            zipEntry.setComment(" zipEntry通过java程序压缩的"); 
	            zipOutputStream.putNextEntry(zipEntry); 
	            while(true) { 
	                int readLength = inputStream.read(block); 
	                if(readLength == -1) 
	                    break;// end of file 
	                zipOutputStream.write(block, 0, readLength); 
	            } 
	            zipOutputStream.flush(); 
	            zipOutputStream.finish(); 
	        } finally { 
                try { 
                	if(inputStream != null) { 
                		inputStream.close();
                		inputStream = null;
                	}
                	if(outputStream != null) {
                		outputStream.close();
                		outputStream = null;
                	}
                	if(zipOutputStream != null) {
                		zipOutputStream.close();
                		zipOutputStream = null;
                	}
                } catch (IOException ex) { 
                } 
	        } 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * 压缩文件。注意：中文文件名称和中文的评论会乱码。 
     */ 
	
	public static void main(String[] args) {
		try {
			//测试创建多个文件夹
//			createDirs("D:/FileWork/FileUtil2/Dir/Test", true);
			//测试删除文件夹及其下面的子文件夹
//			deleteDir(new File("D:/FileWork/FileUtil2"));
			
			//获取指定目录的文件大小，包含子目录
//			long dirLength = getDirLength(new File("F:/FunshionMedia"));
//			System.out.println("F:/FunshionMedia 大小=【"+ dirLength +"】");
//			String fileLength = "";
//			java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("0.####");
//			if(dirLength/1024/1024/1024 > 0) {
//				fileLength = decimalFormat.format(dirLength/1024.0/1024.0/1024.0) + "G";
//			} else if(dirLength/1024/1024 > 0) {
//				fileLength = decimalFormat.format(dirLength/1024.0/1024.0) + "M";
//			} else if(dirLength/1024 > 0) {
//				fileLength = decimalFormat.format(dirLength/1024.0) + "K";
//			} else {
//				fileLength = dirLength + "byte";
//			}
//			System.out.println("F:/FunshionMedia 大小=【"+ fileLength +"】");
			
			//测试获取指定文件目录下的文件信息
//			List<FileInfo> list = getFileInfo("F:/FunshionMedia");
//			if(null != list && list.size() > 0) {
//				for(int i = 0; i < list.size(); i++) {
//					FileInfo fileInfo = list.get(i);
//					System.out.println("文件名:"+ fileInfo.getFileName() +";" +
//						"\n是否是目录:"+ fileInfo.isDir() +";" +
//						"\n文件类型:"+ fileInfo.getFileType() +";" +
//						"\n文件大小:"+ fileInfo.getFileLen() +";" +
//						"\n文件容量:"+ fileInfo.getFileLength() +";" +
//						"\n文件路径:"+ fileInfo.getFilePath() +";" +
//						"\n最后修改时间:"+ fileInfo.getLastModifiedTime() +";" +
//						"\n是否是隐藏文件:"+ fileInfo.isHidden());
//					
//					System.out.println();
//				}
//			}
			
			//测试写文件.如果此文件不存在就创建一个
//			createDirs("E:/FileWork/FileUtil", true);
			//PS：写文件，一定写需要存在目录才可以操作
//			writeFile("文件内容111234567811111111", "E:/FileWork/FileUtil/test.txt", "utf-8");
			
			//测试压缩文件
//			zipFile("E:/FileWork/FileUtil/test.txt", "", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}