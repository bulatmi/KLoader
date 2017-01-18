package Application;

import java.io.*;
import java.util.Properties;

import Downloader.DownloadFileFromURL;

public class Application {
	
    //путь к нашему файлу конфигураций
    public static final String CFG_PATH = "src/main/resources/config.properties";
    private static String url; 
    private static String save_path; 
	
	public void init() { 
		FileInputStream fileInputStream;
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными
		Properties prop = new Properties();
		
		try {
            //обращаемся к файлу и получаем данные
            fileInputStream = new FileInputStream(CFG_PATH);
            prop.load(fileInputStream);
 
            url = prop.getProperty("url");
            save_path = prop.getProperty("save_path");
            
        } catch (IOException e) {
            System.out.println("Отсуствует файл с настройками: " + CFG_PATH + "");
            e.printStackTrace();
        }
		
		
		
	}
	
	public void run() { 
		init();  
		DownloadFileFromURL downloader = new DownloadFileFromURL(); 
		//downloader.downloadUsingStream(url, save_path);
		try {
			// качаем файл с помощью Stream
			downloader.downloadUsingStream(url, save_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Complete");
			
	}
	
	public static void main(String[] args) { 
		Application application = new Application(); 
		application.run();
		
	}
	
}