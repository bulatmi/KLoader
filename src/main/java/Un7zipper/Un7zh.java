package Un7zipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.swing.text.View;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class Un7zh {
	
	public void onUnzip7Zip() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss.SSS");
        
        File db_path = new File("C:/KLADR/Base1.dbf");
        
        File f = new File("C:/KLADR/Base.7z");

        SevenZFile sevenZFile = new SevenZFile(f);
        SevenZArchiveEntry entry = sevenZFile.getNextEntry();
        OutputStream os = new FileOutputStream(db_path);
        while (entry != null) {
            byte[] buffer = new byte[8192];//
            int count;
            while ((count = sevenZFile.read(buffer, 0, buffer.length)) > -1) {

                os.write(buffer, 0, count);
            }
            entry = sevenZFile.getNextEntry();
        }
        sevenZFile.close();
        os.close();

    }
	

}
