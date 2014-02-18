
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @(#)PayrollSystem.java
 *
 * PayrollSystem application
 *
 * @author
 * @version 1.00 2014/2/11
 */

public class PayrollSystem {
    
        private Connection conn= null;
        private String username= "root";
        private String password= "";
    
    public Connection getConnection() throws SQLException{
        conn = DriverManager.getConnection("jdbc:mysql://localhost/Payroll System?" + 
        "user="+username + "&password="+password); 
        return conn;
    }    
        

    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
    	PayrollSystemModel model;
        
        try {
            payrollSystem.getConnection();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        if (payrollSystem.conn!=null){
            model = new PayrollSystemModel(payrollSystem.conn);
            try {
                model.addPersonnel("/Users/macbookpro/Dropbox/introse stuff/PayrollSystem/sample personnel data.xls");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

//            try {
//                model.addAdjustment("Holiday", (float)200.99, "190-957-277", "2013-02-22");
//            } catch (SQLException ex) {
//                PayrollSystem.printError(ex);
//            }
//            
//            try {
//                model.removeAdjustment("190-957-277", "2013-02-22");
//            } catch (SQLException ex) {
//                PayrollSystem.printError(ex);
//            }
            try {
                model.addDTR("/Users/macbookpro/Dropbox/introse stuff/PayrollSystem/sample dtr.xls");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            
            
            
            
        }
    
        
    
    }
}
