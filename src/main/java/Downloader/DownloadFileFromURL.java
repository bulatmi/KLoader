package Downloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;


public class DownloadFileFromURL {

	/*
	public void download(String url, String save_path) {

		try {
			// ךאקאול פאיכ ס ןמלמשü‏ Stream
			downloadUsingStream(url, save_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	// ךאקאול פאיכ ס ןמלמשü‏ Stream
	
	public void downloadUsingStream(URL urlStr, String file) throws IOException {
		URL url = urlStr;
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = bis.read(buffer, 0, 1024)) != -1) {
			fos.write(buffer, 0, count);
		}
		fos.close();
		bis.close();
	}
	
	public java.sql.Date getFileInfo(URL url)  { 
		try {
			URLConnection conn = url.openConnection(); 
			Date sqlDate = new Date(conn.getLastModified());
		    //System.out.println(sqlDate);
			return sqlDate;
		} catch (IOException e) { 
			System.out.println("Connection failed!");
			return null;  
		}
	}
	

}