package Un7zipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Logger;
import net.sf.sevenzipjbinding.ExtractAskMode;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IArchiveExtractCallback;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.PropID;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import org.apache.commons.io.FilenameUtils;


public class testUn7z {
private static Logger logger =     Logger.getLogger(testUn7z.class.getCanonicalName());
//private static String archiveName;
private static String TERR;
private static int extractedNum = 0;

public static int extract(File file, String extractPath) throws Exception {
    logger.info("public static int extract START");
    extractedNum = 0;
    IInArchive inArchive = null;
    RandomAccessFile randomAccessFile = null;

    TERR = ((file.getName()).split("_"))[0];

    randomAccessFile = new RandomAccessFile(file, "r");
    inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
    inArchive.extract(null, false, new MyExtractCallback(inArchive, extractPath));
    if (inArchive != null) {
        inArchive.close();
    }
    if (randomAccessFile != null) {
        randomAccessFile.close();
    }
    logger.info("public static int extract END with extractedNum = " +  extractedNum);
    return extractedNum;
}
public static class MyExtractCallback implements IArchiveExtractCallback {

private final IInArchive inArchive;
private final String extractPath;

public MyExtractCallback(IInArchive inArchive, String extractPath) {
    this.inArchive = inArchive;
    this.extractPath = extractPath;
}

//@Override
public ISequentialOutStream getStream(final int index, ExtractAskMode extractAskMode) throws SevenZipException {
    return new ISequentialOutStream() {
        //@Override
        public int write(byte[] data) throws SevenZipException {
            String filePath = inArchive.getStringProperty(index, PropID.PATH);
            FileOutputStream fos = null;
            try {
            //File dir = new File(extractPath);

                    String onlyFileName = FilenameUtils.getBaseName(filePath);
                    String ext = FilenameUtils.getExtension(filePath);
                    String res = onlyFileName + "_" + TERR +"." + ext;

                if(ext.equals("xml") && !onlyFileName.startsWith("LIST")){
                        //logger.info("extracting file " + filePath);
                    File path = new File(extractPath + File.separator + res);
        //            if (!dir.exists()) {
        //                dir.mkdirs();
        //            }
                    if (!path.exists()) {
                        path.createNewFile();
                    }
                    fos = new FileOutputStream(path, true);
                    fos.write(data);
                    extractedNum++;
                }
            } catch (IOException e) {
                logger.severe(e.getLocalizedMessage());
            } finally {
                try {
                        if (fos != null) {
                            fos.flush();
                            fos.close();
                        }
                } catch (IOException e) {
                    logger.severe(e.getLocalizedMessage());
                }
            }
            return data.length;
        }
    };
}

//@Override
public void prepareOperation(ExtractAskMode extractAskMode) throws SevenZipException {
}

//@Override
public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
}

//@Override
public void setCompleted(long completeValue) throws SevenZipException {
}

//@Override
public void setTotal(long total) throws SevenZipException {
}
}
}