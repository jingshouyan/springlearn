package com.jing.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jing.web.bean.Err;
import com.jing.web.dao.DbDao;
import com.jing.web.model.User;
import com.jing.web.util.Constants;
import com.jing.web.util.http.HttpUtil;
import com.jing.web.util.http.IDownloadHandler;
import com.jing.web.util.http.Response;
import com.jing.web.util.redis.ObjectOps;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ObjectOps opt;
	@Autowired
	private DbDao<User> dbDaoUser;

	public HomeController() {

	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String index() {
		logger.info("HomeController.index");
		return "HELLO WORLD!!";
	}

	@RequestMapping("home")
	public String home(HttpServletRequest request) {
		String userid = UUID.randomUUID().toString();
		request.getSession().setAttribute(Constants.SESSION_USER_ID, userid);
		return "home";
	}

	@RequestMapping(value = "user")
	@ResponseBody
	public User getUser() {
		dbDaoUser.setClass(User.class);
		User user = dbDaoUser.find(3031);
		user.setCreatedAt(new Date());
		return user;
	}

	@RequestMapping(value = "redis")
	@ResponseBody
	public User jredis() {
		User user = new User();
		user.setId(123123l);
		user.setName("张三");
		user.setAge(23);
		user.setGender("male");
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		opt.save(user);

		User u = opt.get("123123", User.class);
		// opt.delete(user);
		return u;
	}
	
	/*
     * 通过流的方式上传文件
     * @RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String  fileUpload(@RequestParam("file") CommonsMultipartFile file) throws IOException {
         
         
        //用来检测程序运行时间
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename());         
        try {
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            String url = "http://127.0.0.1:8080/springlearn/upload";
			Response response = HttpUtil.upload(url , "file", "句話.jpg", is);
			System.out.println(JSON.toJSONString(response));
			is.close();
         
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法一的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "/success"; 
    }
	

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> upload(MultipartFile file, String data) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		String originalFileName = "NAN";
		System.out.println(data);
		try {
			if (!file.isEmpty()) {
				originalFileName = file.getOriginalFilename();
				// byte[] bytes = originalFileName.getBytes("utf-8");
				// String convert = new String(bytes,"ISO-8859-1");
				// System.out.println(bytes);
				// System.out.println(convert);
				System.out.println(originalFileName);
				System.out.println(file.getName());
				System.out.println(file.getSize());
				System.out.println(file.getContentType());
				String realPath = "d:/zip";
				String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
				FileUtils.copyInputStreamToFile(file.getInputStream(),
						new File(realPath, UUID.randomUUID().toString() + suffix));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("filename", originalFileName);
		return map;
	}

	@RequestMapping(value = "test", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject test(@RequestBody String body) {

		return JSON.parseObject(body);
	}

	@RequestMapping(value = "test2", method = RequestMethod.POST)
	@ResponseBody
	public String test2(@RequestBody JSONObject body) {

		return body.toJSONString();
	}

	@RequestMapping(value = "test3", method = RequestMethod.POST)
	@ResponseBody
	public User test3(@RequestBody User body) {

		return body;
	}

	@RequestMapping(value = "cache")
	@ResponseBody
	public String cache() {

		return "cache";
	}
	
	@RequestMapping(value = "down/{fileName:.*}")
	@ResponseBody
	public ResponseEntity<byte[]> down(@PathVariable String fileName) throws IOException{
//		String fileName = "图片下载测试.jpg";
		File file = new File("d:\\"+fileName);
		return download(fileName,file);
	}

	@RequestMapping(value = "downBig/{fileName:.*}")
	@ResponseBody
	public void downBig(@PathVariable String fileName,HttpServletResponse response) throws IOException, InterruptedException{
//		String fileName = "图片下载测试.jpg";
//		File file = new File("d:\\"+fileName);
		String filepath = "d:\\"+fileName;
		String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");//URLEncoder.encode(fileName.trim(),"UTF-8");
		InputStream is = new FileInputStream(filepath);
		response.setHeader("Content-Disposition","attachment;filename="+dfileName);
		int read =0;
		byte[] bytes = new byte[2048];
		OutputStream os = response.getOutputStream();
		while((read = is.read(bytes))!=-1){
		os.write(bytes, 0, read);
		Thread.sleep(100);
		}
		os.flush();
		os.close();
		is.close();

	}
	
	
	@RequestMapping(value = "downTest")
	@ResponseBody
	public void downTest(final HttpServletResponse response){
		
		String url = "http://127.0.0.1:8080/springlearn/downBig/linkdood%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F1.0%E7%89%88%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8C.docx";
		
		IDownloadHandler handler = new IDownloadHandler() {

			@Override
			public void handle(InputStream in) throws IOException {

				String dfileName = new String("测试.docx".getBytes("gb2312"), "iso8859-1");//URLEncoder.encode(fileName.trim(),"UTF-8");
				
				response.setHeader("Content-Disposition","attachment;filename="+dfileName);
				int read =0;
				byte[] bytes = new byte[2048];
				OutputStream os = response.getOutputStream();
				while((read = in.read(bytes))!=-1){
					System.out.println("数据接受中>>>>"+read);
					os.write(bytes, 0, read);
//				Thread.sleep(100);
				}
				os.flush();
				os.close();
				in.close();

			}
		};
		HttpUtil.download(url, handler);
		
	}
	
	public ResponseEntity<byte[]> download(String fileName, File file) throws IOException {
		String dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", dfileName);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}

	@ExceptionHandler
	@ResponseBody
	public Err error(Exception e) {
		logger.error("", e);
		return new Err(e);
	}
}
