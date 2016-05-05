import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginSystem {
	
	private String Username="";
	private String Password="";
	
	public LoginSystem(String Username, String Password )
	{
		this.Username = Username;
		this.Password = Password;
	}

	public boolean CheckLogin()
	{
		Connection cnt = null;
		Statement stmt = null;
		ResultSet rst = null;
		try{
		cnt = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hrs","root","password");
		stmt = cnt.createStatement();
		rst = stmt.executeQuery("select * from login");
		while (rst.next()){
			if(rst.getString("username").equals(this.Username) && rst.getString("password").equals(this.Password))
			{
				rst.close();
				stmt.close();
				cnt.close();
				return true;
			}
		}
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		finally{
			try {
				rst.close();
				stmt.close();
				cnt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return false;
	}
}
