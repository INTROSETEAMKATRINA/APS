import java.util.Date;

public class Personnel {
	private String name;
	private String position;
	private String assignment;
	private String educationalAttainment;
	private String employeeStatus;
	private String SSS;
	private String TIN;
	private String PHIC;
	private String HDMF;
	private String taxStatus;
	private float dailyRate;
	private float colaRate;
	private float monthlyRate;
	private Date dateStarted;
	private Date presentContractStartDate;
	private Date presentContractEndDate;
	private DTR dtr;

    public Personnel(String name, String position, String assignment,
    				 String educationalAttainment, String employeeStatus,
    				 String SSS, String TIN, String PHIC, String HDMF,
    				 String taxStatus, float dailyRate, float colaRate,
    				 float monthlyRate, Date dateStarted, Date presentContractStartDate,
    				 Date presentContractEndDate) {
		this.name = name;
		this.position = position;
		this.assignment = assignment;
		this.educationalAttainment = educationalAttainment;
		this.employeeStatus = employeeStatus;
		this.SSS = SSS;
		this.TIN = TIN;
		this.PHIC = PHIC;
		this.HDMF = HDMF;
		this.taxStatus = taxStatus;
		this.dailyRate = dailyRate;
		this.colaRate = colaRate;
		this.monthlyRate = monthlyRate;
		this.dateStarted = dateStarted;
		this.presentContractStartDate = presentContractStartDate;
		this.presentContractEndDate = presentContractEndDate;
		this.dtr = null;
    }
    public String getName(){
            return name;
    }
    public String getPosition(){
            return position;
    }
    public void setPosition(String position){
            this.position = position;
    }
    public float getDailyRate(){
            return dailyRate;
    }
    public void setDailyRate(float dailyRate){
            this.dailyRate = dailyRate;
    }
    public float getMonthlyRate(){
        return monthlyRate;
    }
    public String getAssignment(){
            return assignment;
    }
    public void setAssignment(String assignment){
            this.assignment = assignment;
    }
    public String getEducationalAttainment(){
            return educationalAttainment;
    }
    public String getEmployeeStatus(){
            return employeeStatus;
    }  
    public String getSSS(){
            return SSS;
    }        
    public String getTIN(){
            return TIN;
    }        
    public String getPHIC(){
            return PHIC;
    }        
    public String getHDMF(){
            return HDMF;
    }                       
    public float getColaRate(){
            return colaRate;
    }
    public void setColaRate(float colaRate){
            this.colaRate = colaRate;
    }
    public String getTaxStatus(){
            return taxStatus;
    }
    public void setTaxStatus(String taxStatus){
            this.taxStatus = taxStatus;
    }
    public DTR getDTR() {
            return dtr;
    }        
    public void setDTR(DTR dtr){
            this.dtr = dtr;
    }
    public Date getDateStarted(){
        return dateStarted;
    }
    public Date getPresentContractStartDate(){
        return presentContractStartDate;
    }        
    public Date getPresentContractEndDate(){
        return presentContractStartDate;
    }        
}