package Downloader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
	public static void downloadUsingStream(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		FileOutputStream fis = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = bis.read(buffer, 0, 1024)) != -1) {
			fis.write(buffer, 0, count);
		}
		fis.close();
		bis.close();
	}

	
	private int getFileSize(URL url) {
	    HttpURLConnection conn = null;
	    try {
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.getInputStream();
	        return conn.getContentLength();
	    } catch (IOException e) {
	        return -1;
	    } finally {
	        conn.disconnect();
	    }
	}

}