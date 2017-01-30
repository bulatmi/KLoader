package Un7zipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class Un7zh {
	
	public void onUnzip7Zip(File file) throws IOException {

        SevenZFile sevenZFile = new SevenZFile(file);
        SevenZArchiveEntry entry = sevenZFile.getNextEntry();
        //OutputStream os = new FileOutputStream(db_path);
        while (entry != null) {
        	System.out.println(entry.getName());
        	OutputStream os = new FileOutputStream("C:/KLADR/" + entry.getName());
            byte[] buffer = new byte[1024];//new byte[8192];
            int count;
            while ((count = sevenZFile.read(buffer, 0, buffer.length)) > -1) {

                os.write(buffer, 0, count);    
            }
            os.close();
            entry = sevenZFile.getNextEntry();
        }
        sevenZFile.close();

    }
	

}
