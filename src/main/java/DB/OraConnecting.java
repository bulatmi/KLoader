package DB;
import java.sql.*;
import java.util.Locale;
import oracle.jdbc.driver.*;

public class OraConnecting {


    public Connection connecting(String oraurl, String user, String password) {

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            Connection con = DriverManager.getConnection(oraurl,user,password);
            con.setAutoCommit(false);
            System.out.println("Connected Successfully to Oracle!");
            //con.close();
            return con;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void close(Connection con){
        try {
            con.close();
            System.out.println("Connection close.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}
