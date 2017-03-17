import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class databasecon {
	String userName;
	String password;
	Float Balance;
	int accno;
	int balance;
	int pin;
	
/*	public int rembalance(String statement){
		Connection con;
		try {
			con = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/bankingsystem","root","");
			Statement stmt=con.createStatement();  
			ResultSet rs = stmt.executeQuery(statement); 
			while (rs.next()) {
				  balance = rs.getInt("Balance");
				}
			return balance;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}    
	}*/
		public int getbalance(String statement){
			Connection con;
			try {
				con = DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/bankingsystem","root","");
				Statement stmt=con.createStatement();  
				ResultSet rs = stmt.executeQuery(statement); 
				while (rs.next()) {
					  balance = rs.getInt("Balance");
					}
				return balance;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}    
		}
		public void databasecon(){
			try{
				Class.forName("com.mysql.jdbc.Driver");  
				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/bankingsystem","root","");    
				}
				catch(Exception e){ System.out.println(e);}  
				}
		public void transferbal(String statement){
			Connection con;
			try {
				con = DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/bankingsystem","root","");
				Statement stmt=con.createStatement();  	
				stmt.executeUpdate(statement);
				System.out.println("I am on Mars");
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		}
		public void updatestate(String statement){
			Connection con;
			try {
				con = DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/bankingsystem","root","");
				Statement stmt=con.createStatement();  
				ResultSet rs = stmt.executeQuery(statement); 
				while (rs.next()) {
					  userName = rs.getString("Username");
					  password = rs.getString("Password");
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		}
		public String sendusername(){
			return this.userName;
		}
		public String senduserpass(){
			return this.password;
		}
		public void ministatement(String statement){
				Connection con;
				try {
					con = DriverManager.getConnection(  
							"jdbc:mysql://localhost:3306/bankingsystem","root","");
					Statement stmt=con.createStatement();  
					ResultSet rs = stmt.executeQuery(statement); 
					while (rs.next()) {
						  Balance = rs.getFloat("Balance");
						  accno = rs.getInt("accountno");
						}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    
			}
		public Float getBalance(){
			return Balance;
		}
		public Integer accountno(){
			return accno;
		}
		public Integer pinno(String Statement){
			Connection con;
			try {
				con = DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/bankingsystem","root","");
				Statement stmt=con.createStatement();  
				ResultSet rs = stmt.executeQuery(Statement); 
				while (rs.next()) {
					  pin = rs.getInt("PIN");
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			return pin;
		}
		public void updatepin(String Statement,String uname){
			Connection con;
			try {
				con = DriverManager.getConnection(  
						"jdbc:mysql://localhost:3306/bankingsystem","root","");
				Statement stmt=con.createStatement();  
				stmt.executeUpdate(Statement);
				String sql = "SELECT * FROM accounts WHERE Username='"+uname+"'";
			    ResultSet rs = stmt.executeQuery(sql);
			    while(rs.next()){
			    	Integer bal = rs.getInt("Balance");
			    	Integer accountno = rs.getInt("accountno");
			    	Integer pinn = rs.getInt("PIN");
			    	String unamee = rs.getString("Username");
			    	System.out.println(bal+" "+accountno+" "+pinn+" "+unamee);
			    }
			    rs.close();
			    
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		}
		}
		

