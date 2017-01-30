package Application;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.sql.*;

import DB.SpLoadTable;
import Downloader.DownloadFileFromURL;
import Un7zipper.Un7zh;
import DB.OraConnecting ;

public class Application {
	
    //путь к нашему файлу конфигураций
    public static final String CFG_PATH = "src/main/resources/config.properties";
    private static URL url; 
    private static String save_path; 
    private static String extractPath; 
    private static File file;
    private static String oraurl;
	private static String user;
	private static String password;
	private static String[] oratables;
	private static String[] dbftables;

	
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
            file = new File(prop.getProperty("file_path")); 
            
            oraurl = prop.getProperty("oraurl");
            user = prop.getProperty("user");
            password = prop.getProperty("password");

            dbftables = prop.getProperty("dbftables").split(";");
			oratables = prop.getProperty("oratables").split(";");

        } catch (IOException e) {
            System.out.println("Отсуствует файл с настройками: " + CFG_PATH + "");
            e.printStackTrace();
        }
		
		
		
	}
	
	public void run() throws Exception { 
		
		init();  
		
		DownloadFileFromURL downloader = new DownloadFileFromURL(); 
		downloader.getFileInfo(url);
/*
		try {
			downloader.downloadUsingStream(url, save_path);
			System.out.println("Download completed!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Un7zh un = new Un7zh();

		try {
			un.onUnzip7Zip(file);
			System.out.println("Un7z Completed!");
		} catch (IOException e) {
			e.printStackTrace();
		}
*/
		for (int i = 0; i < oratables.length; ++i)
		{
			System.out.println(oratables[i]);
		}

		for (int i = 0; i < dbftables.length; ++i)
		{
			System.out.println(dbftables[i]);
		}


		OraConnecting oc = new OraConnecting();
		Connection con = oc.connecting(oraurl, user, password);

		SpLoadTable spload = new SpLoadTable();

		for (int i = 0; i < oratables.length; ++i){
			spload.LoadTable2Ora(con, "KLADR", dbftables[i], oratables[i]);
			con.commit();
			System.out.println(dbftables[i]+" is loaded!");
		}

		oc.close(con);

		System.out.println("Complete!");
	}
	
	public static void main(String[] args) throws Exception { 
		Application application = new Application(); 
		application.run();
		
	}
	
}

