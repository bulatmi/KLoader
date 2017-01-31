package DB;

/**
 * Created by Bulat on 29.01.2017.
 */

import oracle.sql.DATE;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class StoredProcedures {

    public static void loadTable2Ora(Connection con, String p_dir, String p_file, String p_tname) {
        try {
            CallableStatement cstmt = con.prepareCall("{call BULAT2.DBASE_FOX.LOAD_TABLE2(:p_dir, :p_file, :p_tname, :result)}");
            cstmt.setString("p_dir", p_dir);
            cstmt.setString("p_file", p_file);
            cstmt.setString("p_tname", p_tname);
            cstmt.registerOutParameter("result", Types.INTEGER);
            cstmt.execute();
            System.out.println(cstmt.getInt("result"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveKladrDate(Connection con, String kdate, String filename) {
        try {
            CallableStatement cstmt = con.prepareCall("{call BULAT2.SAVE_KDATE(:kcreated, :filename, :result)}");
            cstmt.setString("kcreated", kdate);
            cstmt.setString("filename", filename);
            cstmt.registerOutParameter("result", Types.INTEGER);
            cstmt.execute();
            System.out.println(cstmt.getInt("result"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Timestamp getMaxKDate(Connection con) throws ParseException {
        try {
            CallableStatement cstmt = con.prepareCall("{call BULAT2.GET_MAX_KDATE(:result)}");
            cstmt.registerOutParameter("result", Types.DATE);
            cstmt.execute();
            return cstmt.getTimestamp("result");
        }
        catch (Exception e) {
            //java.util.Date dt = new  java.util.Date("01.01.1990");
            //Date sd = new Date(dt.getTime());
            //e.printStackTrace();
            return null;//sd;
        }

    }


}


