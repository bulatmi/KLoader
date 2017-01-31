package Application;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import DB.StoredProcedures;
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
        //типа Hashtable дл€ удобной работы с данными
		Properties prop = new Properties();
		
		try {
            //обращаемс€ к файлу и получаем данные
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
            System.out.println("ќтсуствует файл с настройками: " + CFG_PATH + "");
            e.printStackTrace();
        }
		
	}
	
	public void run() throws Exception { 
		
		init();  
		
		DownloadFileFromURL downloader = new DownloadFileFromURL();

		OraConnecting oc = new OraConnecting();
		Connection con = oc.connecting(oraurl, user, password);

		StoredProcedures sp = new StoredProcedures();

		System.out.println(downloader.getFileDate(url));
		System.out.println(sp.getMaxKDate(con));

		Long maxKDate = new Long(sp.getMaxKDate(con).getTime()); // получаем дату создани€ последнего залитого в базу кладр-а
		Long kladrDate = new Long(downloader.getFileDate(url).getTime());      // получаем дату ныне лежащего на ресурсе архива с кладром

		System.out.println(maxKDate);
		System.out.println(kladrDate);
		con.commit();

		if (kladrDate > maxKDate) {  // если на ресурсе лежит файл более новый, чем уже загружен в базу, то выполн€ем весь процесс

			try { 					// качаем архив с новым кладр
				downloader.downloadUsingStream(url, save_path);
				System.out.println("Download completed!");
			} catch (IOException e) {
				e.printStackTrace();
			}

			SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
			SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.mm.yyyy", Locale.UK);

			Date sd = oldDateFormat.parse(downloader.getFileDate(url).toString());
			String fd = newDateFormat.format(sd);
			//System.out.println(fd);

			sp.saveKladrDate(con, fd, "Base.7z");  // сохран€ем дату создани€ нового кладр в Oracle
			con.commit();

			Un7zh un = new Un7zh();

			try {					// распаковываем архив
				un.onUnzip7Zip(file);
				System.out.println("Un7z Completed!");
			} catch (IOException e) {
				e.printStackTrace();
			}


			for (int i = 0; i < oratables.length; ++i) {   // заливаем таблицы в Oracle
				sp.loadTable2Ora(con, "KLADR", dbftables[i], oratables[i]);
				con.commit();
				System.out.println(dbftables[i] + " is loaded!");
			}

			con.commit();
			oc.close(con);

			System.out.println("Complete!");
		}
	}
	
	public static void main(String[] args) throws Exception { 
		Application application = new Application(); 
		application.run();
		
	}
	
}

