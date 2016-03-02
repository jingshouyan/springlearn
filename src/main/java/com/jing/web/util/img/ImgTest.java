/**
 * Project Name:springlearn
 * File Name:ImgTest.java
 * Package Name:com.jing.web.util.img
 * Date:2016年3月1日下午2:50:15
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.img;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ImgTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月1日 下午2:50:15 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ImgTest {
	public static void main(String[] args) throws IOException {
//		String filePath="D:/VRV/images/hongminote3/1.jpg";
//		String toPath="D:/112/";
//		ImgCompress imgCompress = new ImgCompress(filePath, toPath);
//		imgCompress.resizeByWidth(540);
		
		String dir="D:/images/";
		String toDir="D:/VRV/images/";
		//		File dir = new File("D:/VRV/images/");
//		List<File> files = getAllFile(dir);
//		for(File file:files){
//			System.out.println(file);
//		}
		ImgC( dir, toDir);
	}
	
	public static void ImgC(String dir,String toDir) throws IOException{
		File root = new File(dir);
		List<File> files = getAllFile(root);
		for(File file:files){
			String filePath = file.getPath();
			if(filePath.endsWith(".jpg")){
				System.out.println(filePath);
				String toPath = toDir+filePath.substring(dir.length());
				
				System.out.println(toPath);
				ImgCompress imgCompress = new ImgCompress(filePath, toPath);
				imgCompress.resizeByWidth(1080);
			}			
		}
	}
	
	public static List<File> getAllFile(File dir){
		List<File> files = new ArrayList<File>();
		File[] fs = dir.listFiles();
		for(int i = 0;i<fs.length;i++){
			if(fs[i].isDirectory()){
				files.addAll(getAllFile(fs[i]));
			}else{
				files.add(fs[i]);
			}
		}
		return files;
	}
}

