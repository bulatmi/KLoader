package DB;

/**
 * Created by Bulat on 29.01.2017.
 */

import java.sql.*;

public class SpLoadTable {

    public static void LoadTable2Ora(Connection con, String p_dir, String p_file, String p_tname) {
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
}


