package Application;

import java.io.*;
import java.net.URL;
import java.util.Properties;

import Downloader.DownloadFileFromURL;
import Un7zipper.Un7zh;
import Un7zipper.testUn7z;

public class Application {
	
    //путь к нашему файлу конфигураций
    public static final String CFG_PATH = "src/main/resources/config.properties";
    //private static String url;
    private static URL url; 
    private static String save_path; 
    private static String extractPath; 
	
	public void init() { 
		FileInputStream fileInputStream;
        //инициализируем специальный объект Properties
        //типа Hashtable для удобной работы с данными
		Properties prop = new Properties();
		
		try {
            //обращаемся к файлу и получаем данные
            fileInputStream = new FileInputStream(CFG_PATH);
            prop.load(fileInputStream);
            
            url = new URL(prop.getProperty("url"));
            save_path = prop.getProperty("save_path");
            extractPath = prop.getProperty("extractPath");
            
            
            
        } catch (IOException e) {
            System.out.println("Отсуствует файл с настройками: " + CFG_PATH + "");
            e.printStackTrace();
        }
		
		
		
	}
	
	public void run() throws Exception { 
		
		init();  
		
		DownloadFileFromURL downloader = new DownloadFileFromURL(); 
		downloader.getFileInfo(url);
		
		try {
			downloader.downloadUsingStream(url, save_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		File f = new File("C:/KLADR/Base.7z"); 
		testUn7z unzip = new testUn7z();  
		//unzip.extract(f, extractPath); 
		//int extractedFileNum = testUn7z.extract(f, extractPath);
		Un7zh un = new Un7zh();
		un.onUnzip7Zip();
		
	}
	
	public static void main(String[] args) throws Exception { 
		Application application = new Application(); 
		application.run();
		
	}
	
}