/**
 * Project Name:springlearn
 * File Name:ImgUtil.java
 * Package Name:com.jing.web.util.img
 * Date:2016年3月1日下午2:03:23
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.jing.web.util.img;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.*;  

/**
 * ClassName:ImgCompress <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月1日 下午2:03:23 <br/>
 * @author   bxy-jing
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ImgCompress {
	
	private Image img;
	private int width;
	private int height;
	private String toPath;
	
	public ImgCompress(String filePath,String toPath)throws IOException{
		File file = new File(filePath);
		img = ImageIO.read(file);
		width = img.getWidth(null);
		height = img.getHeight(null);
		toPath = toPath.replace('\\', '/');
		if(toPath.endsWith("/")){
			String fileName = file.getName();
			this.toPath = toPath+fileName;
		}else{
			this.toPath = toPath;
		}
	}
	
	public void resizePercent(double percent) throws IOException{
		int w = (int) (width*percent/100);
		int h = (int) (height*percent/100);
		resize(w, h);
	}
	
	/** 
     * 按照宽度还是高度进行压缩 
     * @param w int 最大宽度 
     * @param h int 最大高度 
     */  
    public void resizeFix(int w, int h) throws IOException {  
        if (width / height > w / h) {  
            resizeByWidth(w);  
        } else {  
            resizeByHeight(h);  
        }  
    }  
    /** 
     * 以宽度为基准，等比例放缩图片 
     * @param w int 新宽度 
     */  
    public void resizeByWidth(int w) throws IOException {  
        int h = (int) (height * w / width);  
        resize(w, h);  
    }  
    /** 
     * 以高度为基准，等比例缩放图片 
     * @param h int 新高度 
     */  
    public void resizeByHeight(int h) throws IOException {  
        int w = (int) (width * h / height);  
        resize(w, h);  
    }  
    /** 
     * 强制压缩/放大图片到固定的大小 
     * @param w int 新宽度 
     * @param h int 新高度 
     */  
    @SuppressWarnings("restriction")
	public void resize(int w, int h) throws IOException {  
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
        File destFile = new File(toPath);
        File folder = destFile.getParentFile();
        if(!folder.exists()){
        	folder.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
        // 可以正常实现bmp、png、gif转jpg  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
        encoder.encode(image); // JPEG编码  
        out.close();  
    }
    


}

