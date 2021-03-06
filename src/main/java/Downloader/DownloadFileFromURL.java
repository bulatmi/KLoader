package Downloader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.Map;


public class DownloadFileFromURL {

	/*
	public void download(String url, String save_path) {

		try {
			// ������ ���� � ������� Stream
			downloadUsingStream(url, save_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	// ������ ���� � ������� Stream
	
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
	
	public java.sql.Timestamp getFileDate(URL url)  {
		try {
			URLConnection conn = url.openConnection();
			java.sql.Timestamp date = new java.sql.Timestamp(conn.getLastModified());
		    //System.out.println(sqlDate);
			return date;
		} catch (IOException e) { 
			System.out.println("Connection failed!");
			return null;  
		}
	}

}