import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import jxl.*;
import jxl.read.biff.BiffException;

public class PayrollSystemModel {
    private ArrayList<Personnel> personnels;
    private Connection conn;
    private SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    

    public PayrollSystemModel(Connection conn) {
            this.conn=conn;
            personnels = new ArrayList<Personnel>();
    }
    
    public void addPersonnel(String fileDirectory) throws Exception {
        Statement stmt = null; 
        String sql;

            File file = new File(fileDirectory);
            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);

            String name,position,assignment,educationalAttainment,employeeStatus,SSS,TIN,
                PHIC,HDMF,taxStatus;
            float dailyRate, colaRate, monthlyRate;
            Date dateStarted, presentContractStartDate, presentContractEndDate;
            SimpleDateFormat[] possibleFormats = new SimpleDateFormat[] {
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yyyy/MM/dd"),
                new SimpleDateFormat("MM/dd/yyyy"),
                new SimpleDateFormat("MM-dd-yyyy") };

            //getCell(column,row)
            int row = 0;
            assignment = sheet.getCell(1,row).getContents();
            row+=2;

            while(row < sheet.getRows()){

                name = sheet.getCell(0,row).getContents();

                if(name.length() > 0){
                    position = sheet.getCell(1,row).getContents();
                    educationalAttainment = sheet.getCell(2,row).getContents();
                    dateStarted = null;
                    for(SimpleDateFormat format:possibleFormats){
                        try{
                                format.setLenient(false);
                                dateStarted = format.parse(sheet.getCell(3,row).getContents());
                        } catch(Exception e){
                            System.out.println(e);
                            //e1.printStackTrace();
                        }
                    }

                    employeeStatus = sheet.getCell(4,row).getContents();
                    String date = sheet.getCell(5,row).getContents();

                    presentContractStartDate = null;
                    presentContractEndDate = null;

                    for(SimpleDateFormat format:possibleFormats){
                        try{
                            format.setLenient(false);
                            presentContractStartDate = format.parse(date.split("-")[0]);
                            presentContractEndDate = format.parse(date.split("-")[1]);
                        }catch(Exception e){
                            System.out.println(e);
                            //e2.printStackTrace();
                        }
                    }

                    dailyRate = 0;
                    colaRate = 0;
                    monthlyRate = 0;

                    try{
                        dailyRate 	= Float.parseFloat(sheet.getCell(6,row).getContents());
                        colaRate 	= Float.parseFloat(sheet.getCell(7,row).getContents());
                        monthlyRate = Float.parseFloat(sheet.getCell(8,row).getContents());
                    } catch(Exception e) {
                        System.out.println(e);
                        //e3.printStackTrace();
                    }
                    SSS = sheet.getCell(9,row).getContents();
                    TIN = sheet.getCell(10,row).getContents();
                    PHIC = sheet.getCell(11,row).getContents();
                    HDMF = sheet.getCell(12,row).getContents();
                    taxStatus = sheet.getCell(13,row).getContents();
                    
                    personnels.add(new Personnel(name, position, assignment, educationalAttainment,
                         employeeStatus, SSS, TIN, PHIC, HDMF, taxStatus,
                         dailyRate, colaRate, monthlyRate, dateStarted,
                         presentContractStartDate, presentContractEndDate));
                }
                row++;
            }
        
        System.out.println(personnels.size());

        //ADD TO DATABASE
        for( Personnel personnel: personnels ){
            try{
                sql="INSERT INTO `Payroll System`.`Personnel`\n" +
                "(`Name`,\n" +
                "`Assignment`,\n" +
                "`Position`,\n" +
                "`EducationalAttainment`,\n" +
                "`EmployeeStatus`,\n" +
                "`DailyRate`,\n" +
                "`ColaRate`,\n" +
                "`MonthlyRate`,\n" +
                "`SSS`,\n" +
                "`TIN`,\n" +
                "`PHIC`,\n" +
                "`HDMF`,\n" +
                "`TaxStatus`)\n" +
                "VALUES ('"+ personnel.getName() +"',"
                        + " '"+personnel.getAssignment()+"',"
                        + " '"+personnel.getPosition()+"',"
                        + " '"+personnel.getEducationalAttainment()+"',"
                        + " '"+personnel.getEmployeeStatus()+"',"
                        + " '"+personnel.getDailyRate()+"',"
                        + " '"+personnel.getColaRate()+"',"
                        + " '"+personnel.getMonthlyRate()+"',"
                        + " '"+personnel.getSSS()+"',"
                        + " '"+personnel.getTIN()+"',"
                        + " '"+personnel.getPHIC()+"',"
                        + " '"+personnel.getHDMF()+"',"
                        + " '"+personnel.getTaxStatus()+"');";
                stmt=conn.prepareStatement(sql);
                stmt.execute(sql);
            } catch ( SQLException ex ){
                sql ="UPDATE `Payroll System`.`Personnel`\n" +
                "SET\n" +
                "`Name` = '"+ personnel.getName() +"',\n" +
                "`Assignment` = '"+personnel.getAssignment()+"',\n" +
                "`Position` = '"+personnel.getPosition()+"',\n" +
                "`EducationalAttainment` = '"+personnel.getEducationalAttainment()+"',\n" +
                "`EmployeeStatus` = '"+personnel.getEmployeeStatus()+"',\n" +
                "`DailyRate` = '"+personnel.getDailyRate()+"',\n" +
                "`ColaRate` = '"+personnel.getColaRate()+"',\n" +
                "`MonthlyRate` = '"+personnel.getMonthlyRate()+"',\n" +
                "`SSS` = '"+personnel.getSSS()+"',\n" +
                "`PHIC` = '"+personnel.getPHIC()+"',\n" +
                "`HDMF` = '"+personnel.getHDMF()+"',\n" +
                "`TaxStatus` = '"+personnel.getTaxStatus()+"'\n" +
                "WHERE `TIN` = \""+personnel.getTIN()+"\";";
                stmt=conn.prepareStatement(sql);
                stmt.execute(sql);
            }
        }
    }
    
    public void addDTR(String fileDirectory) throws Exception {
        Statement stmt = null; 
        String sql;
        
            File file = new File(fileDirectory);
            Workbook workbook = Workbook.getWorkbook(file);
            Sheet sheet = workbook.getSheet(0);

            String name,client;
            float regularHoursWorks, regularOvertime, regularNightShiftDifferential,
                specialHoliday, specialHolidayOvertime, specialHolidayNightShiftDifferential,
                legalHoliday, legalHolidayOvertime, legalHolidayNightShiftDifferential;
            Date  periodStartDate;
            SimpleDateFormat[] possibleFormats = new SimpleDateFormat[] {
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yyyy/MM/dd"),
                new SimpleDateFormat("MM/dd/yyyy"),
                new SimpleDateFormat("MM-dd-yyyy") };
            int row = 0;

            client = sheet.getCell(1,row).getContents();

            row++;

            periodStartDate = null;
            for(SimpleDateFormat format : possibleFormats){

                try{
                        format.setLenient(false);
                        periodStartDate = format.parse(sheet.getCell(1,row).getContents());
                }catch(Exception e){
                        System.out.println(e);
                        //e1.printStackTrace();
                }
            }
            
            if(periodStartDate==null)
                throw new InvalidDateException("B","2");
                
                
            row+=2;
            while(row < sheet.getRows()){

                name = sheet.getCell(0,row).getContents();

                
                if(name.length() > 0){

                    regularHoursWorks = 0;
                    try{
                        regularHoursWorks = Float.parseFloat(sheet.getCell(1,row).getContents());
                    } catch(Exception e) {
                    }

                    regularOvertime = 0;
                    try{
                        regularOvertime = Float.parseFloat(sheet.getCell(2,row).getContents());
                    }catch(Exception e){
                    }

                    regularNightShiftDifferential = 0;
                    try{
                        regularNightShiftDifferential = Float.parseFloat(sheet.getCell(3,row).getContents());
                    }catch(Exception e){
                    }

                    specialHoliday = 0;
                    try{
                        specialHoliday = Float.parseFloat(sheet.getCell(4,row).getContents());
                    }catch(Exception e){
                    }

                    specialHolidayOvertime = 0;
                    try{
                        specialHolidayOvertime = Float.parseFloat(sheet.getCell(5,row).getContents());
                    }catch(Exception e){
                    }

                    specialHolidayNightShiftDifferential = 0;
                    try{
                        specialHolidayNightShiftDifferential = Float.parseFloat(sheet.getCell(6,row).getContents());
                    }catch(Exception e){
                    }

                    legalHoliday = 0;
                    try{
                        legalHoliday = Float.parseFloat(sheet.getCell(7,row).getContents());
                    }catch(Exception e){
                    }

                    legalHolidayOvertime = 0;
                    try{
                        legalHolidayOvertime = Float.parseFloat(sheet.getCell(8,row).getContents());
                    }catch(Exception e){
                    }

                    legalHolidayNightShiftDifferential = 0;
                    try{
                        legalHolidayNightShiftDifferential = Float.parseFloat(sheet.getCell(9,row).getContents());
                    }catch(Exception e){
                    }

                    for(Personnel personnel : personnels){
                        if(personnel.getName().equalsIgnoreCase(name)){
                            if(personnel.getAssignment().equalsIgnoreCase(client)){
                                 personnel.setDTR(new DTR(regularHoursWorks, regularOvertime, regularNightShiftDifferential,
                                 specialHoliday, specialHolidayOvertime,specialHolidayNightShiftDifferential,
                                 legalHoliday, legalHolidayOvertime, legalHolidayNightShiftDifferential,
                                 periodStartDate));
                            }
                        }
                    }
                }
                row++;
            }

        //ADD TO DATABASE

        for(Personnel personnel: personnels){
            if(personnel.getDTR()!=null){
                
                try{
                    sql= "INSERT INTO `Payroll System`.`DTR`\n" +
                    "(`RHW`,\n" +
                    "`ROT`,\n" +
                    "`RNSD`,\n" +
                    "`SH`,\n" +
                    "`SHOT`,\n" +
                    "`SHNSD`,\n" +
                    "`LH`,\n" +
                    "`LHOT`,\n" +
                    "`LHNSD`,\n" +
                    "`PeriodStartDate`,\n" +
                    "`Personnel`)\n" +
                    "VALUES\n" +
                    "('"+ personnel.getDTR().getRegularHoursWorks() +"',\n" +
                    "'"+ personnel.getDTR().getRegularOvertime() +"',\n" +
                    "'"+ personnel.getDTR().getRegularNightShiftDifferential() +"',\n" +
                    "'"+ personnel.getDTR().getSpecialHoliday() +"',\n" +
                    "'"+ personnel.getDTR().getSpecialHolidayOvertime() +"',\n" +
                    "'"+ personnel.getDTR().getSpecialHolidayNightShiftDifferential() +"',\n" +
                    "'"+ personnel.getDTR().getLegalHoliday() +"',\n" +
                    "'"+ personnel.getDTR().getLegalHolidayOvertime() +"',\n" +
                    "'"+ personnel.getDTR().getLegalHolidayNightShiftDifferential() +"',\n" +
                    "'"+ sdf.format(personnel.getDTR().getPeriodStartDate()) +"',\n" +
                    "'"+ personnel.getTIN() +"');";
                    stmt=conn.prepareStatement(sql);
                    stmt.execute(sql);                

                } catch(SQLException ex) {
                    sql="UPDATE `Payroll System`.`DTR`\n" +
                    "SET\n" +
                    "`RHW` = " + personnel.getDTR().getRegularHoursWorks() + ",\n" +
                    "`ROT` = "+personnel.getDTR().getRegularOvertime()+",\n" +
                    "`RNSD` = "+personnel.getDTR().getRegularNightShiftDifferential()+",\n" +
                    "`SH` = "+personnel.getDTR().getSpecialHoliday()+",\n" +
                    "`SHOT` = "+personnel.getDTR().getSpecialHolidayOvertime()+",\n" +
                    "`SHNSD` = "+personnel.getDTR().getSpecialHolidayNightShiftDifferential()+",\n" +
                    "`LH` = "+personnel.getDTR().getLegalHoliday()+",\n" +
                    "`LHOT` = "+personnel.getDTR().getLegalHolidayOvertime()+",\n" +
                    "`LHNSD` = "+personnel.getDTR().getLegalHolidayNightShiftDifferential()+"\n" +
                    "WHERE `PeriodStartDate` = \"" + sdf.format(personnel.getDTR().getPeriodStartDate()) + "\" AND"
                            + " `Personnel` = \""+personnel.getTIN()+"\";";
                    stmt=conn.prepareStatement(sql);
                    stmt.execute(sql);
                
                }
            }
        }
    }
        
    public void addAdjustment(String reason, float adjustment, String TIN, String periodStartDate) throws SQLException {
        Statement stmt = null; 
        String sql="INSERT INTO `Payroll System`.`AdjustmentsAndDeductions`\n" +
            "(`amount`,\n" +
            "`type`,\n" +
            "`PeriodStartDate`,\n" +
            "`Personnel`)\n" +
            "VALUES\n" +
            "('"+ adjustment +"',\n" +
            "'"+ reason +"',\n" +
            "'"+ periodStartDate +"',\n" +
            "'"+ TIN +"');";
        
        stmt=conn.prepareStatement(sql);
        stmt.execute(sql);
    }
        
    public void removeAdjustment(String TIN, String periodStartDate) throws SQLException {
        Statement stmt = null;         
        
        String sql="DELETE FROM `Payroll System`.`AdjustmentsAndDeductions`\n" +
            "WHERE Personnel = \""+ TIN+"\" AND PeriodStartDate =\""+ periodStartDate+"\"";
        stmt=conn.prepareStatement(sql);
        stmt.execute(sql);
    }
        
}
