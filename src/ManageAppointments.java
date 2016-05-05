import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ManageAppointments {


	List<String> rowValues;
	private String[] doctors = {"Who","McNugget","Johnson","Acula","Ive"};
	private String[] appointmentTimes = {"8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30","12:00","12:30",
			"13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30",
			"20:00","20:30"};
	
	public ManageAppointments()
	{
		//viewAppointments();
	}
	public void printList()
	{
		for(String rowValue: rowValues)
		{
			System.out.println(rowValue);
		}

	}
	public List<String> returnList()
	{
		return rowValues;
	}
	public String[] returnDoctors()
	{
		return doctors;
	}
	public String[] returnTimes()
	{
		return appointmentTimes;
	}
	public String[] parseString(String aptInfo)
	{
		String[] split = aptInfo.split("\\s+(?![^\\[]*\\])");
		String[] trueInfo = new String[(split.length/2)];
		for(int i = 0,j=0; i<(split.length);i++)
		{
			if(i%2==1)
			{	
			trueInfo[j] = split[i];
			j++;
			}
		}
		return trueInfo;
	}
	
	public void changeAppointment(String doctor,String patient, String time, String date, String[] baseInfo)
	{
		Connection cnt = null;
		Statement stmt = null;
		
		rowValues = new ArrayList<String>();
		try{
		cnt = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hrs","root","password");
		stmt = cnt.createStatement();
		stmt.executeUpdate("UPDATE appointments SET `doctor_name`=\'"+doctor+"\',`patient_name`=\'"+patient+
				"\',`apt_time`=\'"+time+"\',`apt_date`=\'"+date+"\' WHERE `doctor_name`=\'"+baseInfo[0]+"\' AND `patient_name`=\'"+baseInfo[1]+"\'");
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		finally{
			try {
				stmt.close();
				cnt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	public void viewAppointments()
	{
		Connection cnt = null;
		Statement stmt = null;
		ResultSet rst = null;
		rowValues = new ArrayList<String>();
		try{
		cnt = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hrs","root","password");
		stmt = cnt.createStatement();
		rst = stmt.executeQuery("select * from appointments");
		
		while (rst.next()){
			rowValues.add("[Doctor:] "+rst.getString(1)+" [Patient:] "+rst.getString(2)+" [Apt Time:] "+rst.getString(3)+" [AptDate:] "+rst.getString(4));
		}
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		finally{
			try {
				rst.close();
				//rst2.close();
				stmt.close();
				cnt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	public void newApt(String doctor,String patient, String time, String date)
	{
		Connection cnt = null;
		Statement stmt = null;
		
		rowValues = new ArrayList<String>();
		try{
		cnt = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hrs","root","password");
		stmt = cnt.createStatement();
		String insert = "insert into appointments"
				+"(doctor_name,patient_name,apt_time,apt_date)"
				+"values(\'"+doctor+"\',\'"+patient+"\',\'"+time+"\',\'"+date+"\')";
		System.out.println(insert);
		stmt.executeUpdate(insert);
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		finally{
			try {
				stmt.close();
				cnt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	public void deleteAppointment(String[] parsedInfo)
	{
		Connection cnt = null;
		Statement stmt = null;
		
		rowValues = new ArrayList<String>();
		try{
		cnt = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hrs","root","password");
		stmt = cnt.createStatement();
		String delete = "delete from appointments"
				+" where `doctor_name`='"+parsedInfo[0]+"' and `patient_name`='"+parsedInfo[1]+"' and apt_time='"+parsedInfo[2]+"'";
		stmt.executeUpdate(delete);
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		finally{
			try {
				stmt.close();
				cnt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
}
