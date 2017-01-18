package Application;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import Downloader.DownloadFileFromURL;

public class Application {
	
    //���� � ������ ����� ������������
    public static final String CFG_PATH = "src/main/resources/config.properties";
    //private static String url;
    private static URL url; 
    private static String save_path; 
	
	public void init() { 
		FileInputStream fileInputStream;
        //�������������� ����������� ������ Properties
        //���� Hashtable ��� ������� ������ � �������
		Properties prop = new Properties();
		
		try {
            //���������� � ����� � �������� ������
            fileInputStream = new FileInputStream(CFG_PATH);
            prop.load(fileInputStream);
 
            url = new URL(prop.getProperty("url"));
            save_path = prop.getProperty("save_path");
            
        } catch (IOException e) {
            System.out.println("���������� ���� � �����������: " + CFG_PATH + "");
            e.printStackTrace();
        }
		
		
		
	}
	
	public void run() throws IOException { 
		init();  
		DownloadFileFromURL downloader = new DownloadFileFromURL(); 
		//downloader.downloadUsingStream(url, save_path);
		/*try {
			downloader.downloadUsingStream(url, save_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		//URL url = new URL("http://www.gnivc.ru/html/gnivcsoft/KLADR/Base.7z"); 
  
		downloader.getFileInfo(url);
			
	}
	
	public static void main(String[] args) throws IOException { 
		Application application = new Application(); 
		application.run();
		
	}
	
}