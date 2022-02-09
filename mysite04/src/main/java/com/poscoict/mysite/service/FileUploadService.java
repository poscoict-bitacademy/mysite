package com.poscoict.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poscoict.mysite.exception.FileUploadException;

@Service
public class FileUploadService {
	private static String SAVE_PATH = "/upload-images";
	private static String URL_BASE = "/images";
	
	public String restore(MultipartFile multipartFile) {
		String url = null;
		
		try {
			if(multipartFile.isEmpty()) {
				return url;
			}
			
			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf('.')+1);
			String saveFilename = genearteSaveFilename(extName);
			long fileSize = multipartFile.getSize();
			
			System.out.println("##############" + originFilename);
			System.out.println("##############" + saveFilename);
			System.out.println("##############" + fileSize);
			
			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();
			
			url = URL_BASE + "/" + saveFilename;
			
		} catch(IOException ex) {
			throw new FileUploadException("file upload error:" + ex);
		}
		
		return url;
	}

	private String genearteSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}

}
