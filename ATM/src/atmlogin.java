import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class atmlogin extends Application {
	private int temp1,temp2;
	private String rembalance,withbalance;
	private int balance;
	private Float Balance;
	private int accno;
	private TextField username;
	private PasswordField password;
	private Button login;
	private Label captcha;
	private int random = 0;
	private int captchaentry;
	private TextField captchaenter;
	private String balance2;
	private void captcha(){
		Random rand = new Random();
		random = rand.nextInt(100) + 5;
	}
	databasecon d = new databasecon();
	public static void main(String[] args) {
			launch(args);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage arg0) throws Exception {
		captcha();
		arg0.setTitle("LOGIN SCREEN");
		captcha = new Label("What is "+random+" +1");
		captchaenter = new TextField();
		Label userlabel = new Label("Username: ");
		Label passlabel = new Label("Password: ");
		username = new TextField();
		password = new PasswordField();
		login = new Button("Login");
		username.setPromptText("Username");
		password.setPromptText("Password");
		captchaenter.setPromptText("Enter the Captcha");
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		grid.setConstraints(username, 5, 1);
		grid.setConstraints(password, 5, 2);
		grid.setConstraints(userlabel, 4, 1);
		grid.setConstraints(passlabel, 4, 2);
		grid.setConstraints(captcha, 4, 3);
		grid.setConstraints(captchaenter, 5,3);
		grid.setConstraints(login, 5, 5);
		grid.setMaxHeight(300);
		grid.setMaxWidth(300);
		grid.getChildren().addAll(username,password,userlabel,passlabel,captcha,captchaenter,login);
		Scene scene = new Scene(grid,300,300);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		arg0.setResizable(false);
		userlabel.requestFocus();
		arg0.setScene(scene);
		arg0.show();
		grid.addEventFilter(KeyEvent.KEY_PRESSED,e->{
			if(e.getCode()==KeyCode.ENTER){
				login(arg0);
			}
		});
		login.setOnAction(e->{login(arg0);});
	}
	
	private void login(Stage arg0){
		captchaentry = Integer.parseInt(captchaenter.getText());
		String user = username.getText();
		String pass = password.getText();
		d.databasecon();
		String statement = "SELECT * FROM custpass WHERE Username='"+user+"'";
		d.updatestate(statement);
		String checkuser = d.sendusername();
		String checkpass = d.senduserpass();
		if(captchaentry==(random+1)){
			if((checkuser.equals(user))&&(checkpass.equals(pass))){
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login Success");
				alert.setHeaderText("Congrats! You have logged in successfully");
				alert.setContentText("");
				alert.showAndWait();
				mainpage(arg0);
				
			}}
			else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Login Unsuccessful");
				alert.setHeaderText("Please check your login info");
				alert.setContentText("");
				alert.showAndWait();
			}
	}
	private void mainpage(Stage arg0){
		Button pinchange = new Button("PIN Change");
		Button benquiry = new Button("Balance Enquiry");
		Button mstatement = new Button("Mini Statement");
		Button bwithdrawal = new Button("Balance Withdrawal");
		Button transfer = new Button("Transfer");
		GridPane mainp = new GridPane();
		mainp.setVgap(10);
		mainp.setHgap(10);
		mainp.setConstraints(pinchange, 2, 2);
		mainp.setConstraints(bwithdrawal, 8,2);
		mainp.setConstraints(mstatement, 8, 3);
		mainp.setConstraints(benquiry, 2, 5);
		mainp.setConstraints(transfer, 8, 5);
		mainp.getChildren().addAll(pinchange,bwithdrawal,benquiry,transfer,mstatement);
		Scene scene = new Scene(mainp,350,300);
		arg0.requestFocus();
		arg0.setScene(scene);
		mstatement.setOnAction(e->{ministatement(arg0);});
		pinchange.setOnAction(e->{
			pinchange(arg0);
		});
		transfer.setOnAction(e->{
			transfer(arg0);
		});
		benquiry.setOnAction(e->{balanceenquiry(arg0);});
		bwithdrawal.setOnAction(e->{
			withdraw(arg0);
		});
	}
	@SuppressWarnings("unused")
	private void withdraw(Stage arg0){
		TextField withdrawalamount = new TextField();
		PasswordField pin = new PasswordField();
		Button withdraw = new Button("Withdraw");
		withdrawalamount.setPromptText("Amount to Withdraw");
		pin.setPromptText("Enter the PIN");
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		grid.setConstraints(withdrawalamount, 5, 1);
		grid.setConstraints(pin, 5,2);
		grid.setConstraints(withdraw, 5, 3);
		grid.getChildren().addAll(withdrawalamount,pin,withdraw);
		databasecon db = new databasecon();
		withdraw.setOnAction(e->{
			String withdrawalamount1 = withdrawalamount.getText();
			String pin1 = pin.getText();
			String uname = username.getText();
			String statement = "SELECT Balance FROM accounts WHERE Username='"+uname+"'";
			balance = d.getbalance(statement);
			temp1 = Integer.parseInt(withdrawalamount1);
			temp2 = Integer.parseInt(pin1);
			temp1 = balance - temp1;
			String statement1 = "UPDATE accounts SET Balance = '"+String.valueOf(temp1)+"' WHERE Username='"+uname+"'";
			db.transferbal(statement1);
		});
		Scene scene = new Scene(grid,400,400);
		arg0.setScene(scene);
		arg0.show();
	}
	private void transfer(Stage arg0){
		TextField withdrawalamount = new TextField();
		TextField accountno = new TextField();
		TextArea transferdetais = new TextArea();
		Button Transfer = new Button("Transfer");
		Label l = new Label();
		Button checkdetails = new Button("Check details");
		withdrawalamount.setPromptText("Enter the Withdrawal Amount");
		accountno.setPromptText("Enter the Account no. of Reciever");
		transferdetais.setPromptText("Payee Account Details");
		PasswordField pin = new PasswordField();
		pin.setPromptText("Enter the PIN");
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(10,10,10,10));
		grid.setConstraints(withdrawalamount, 5, 1);
		grid.setConstraints(accountno, 5, 2);
		grid.setConstraints(transferdetais, 5, 3);
		grid.setConstraints(pin, 5, 8);
		transferdetais.setMaxWidth(250);
		withdrawalamount.setMinWidth(250);
		accountno.setMinWidth(250);
		transferdetais.setMaxHeight(50);
		grid.setConstraints(Transfer, 2, 10);
		grid.setConstraints(checkdetails, 5, 10);
		grid.getChildren().addAll(withdrawalamount,accountno,transferdetais,Transfer,checkdetails,pin);
		l.requestFocus();
		Scene scene = new Scene(grid);
		Transfer.setOnAction(e->{
			String withdrawal = withdrawalamount.getText(); 
			String account = accountno.getText();
			String uname = username.getText();
			String statement = "SELECT Balance FROM accounts WHERE Username='"+uname+"'";
			balance = d.getbalance(statement);
			String statement3 = "SELECT Balance FROM accounts WHERE accountno='"+account+"'";
			int balance2 = d.getbalance(statement3);
			String balll = ""+balance;
			System.out.println(balance);
			balancecalc(balll,withdrawal);
			//balance = balance - Integer.parseInt(withdrawal);
			System.out.println(rembalance);
			System.out.println(uname);
			System.out.println(withbalance);
			System.out.println(account);
			String statement1 = "UPDATE accounts SET Balance='"+rembalance+"' WHERE Username='"+uname+"'";
			d.transferbal(statement1);
			int withbalance1 = Integer.parseInt(withbalance);
			withbalance1 = withbalance1 + balance2;
			withbalance = ""+withbalance1;
			String statement2 = "UPDATE accounts SET Balance='"+withbalance+"' WHERE accountno='"+account+"'";
			d.transferbal(statement2);
		});
		arg0.setScene(scene);
		arg0.show();
	}
	private void balancecalc(String Balance,String withdrawal){
		int balan = Integer.parseInt(Balance);
		int withdrawamount = Integer.parseInt(withdrawal);
		int rembalance = balan - withdrawamount;
		this.rembalance = ""+rembalance;
		this.withbalance = ""+withdrawamount;
		
	}
	private void pinchange(Stage arg0){
		TextField oldpin = new TextField();
		TextField newpin = new TextField();
		TextField confnewpin = new TextField();
		Button set = new Button("Set New PIN");
		Label oldpinlabel = new Label("Old PIN");
		Label newpinlabel = new Label("New PIN");
		Label confnewpinlabel = new Label("Confirm New PIN");
		GridPane pinpage = new GridPane();
		pinpage.setHgap(10);
		pinpage.setVgap(10);
		pinpage.setPadding(new Insets(10,10,10,10));
		pinpage.setConstraints(oldpin, 3, 2);
		pinpage.setConstraints(oldpinlabel, 2, 2);
		pinpage.setConstraints(newpinlabel,2,3);
		pinpage.setConstraints(newpin, 3, 3);
		pinpage.setConstraints(confnewpinlabel, 2, 4);
		pinpage.setConstraints(confnewpin, 3, 4);
		pinpage.setConstraints(set, 3, 6);
		pinpage.getChildren().addAll(oldpin,oldpinlabel,newpinlabel,newpin,confnewpin,confnewpinlabel,set);
		Scene scene = new Scene(pinpage,350,350);
		arg0.setScene(scene);
		set.setOnAction(e->{int oldpinget=0;
		oldpinget = Integer.parseInt(oldpin.getText());
		int newpinget=0;
		newpinget = Integer.parseInt(newpin.getText());
		int confpinget=0;
		confpinget = Integer.parseInt(confnewpin.getText());
		String uname = username.getText();
		String statement = "SELECT PIN FROM accounts WHERE Username='"+uname+"'";
		int pin = d.pinno(statement);
			if((oldpinget!=0)&&(newpinget!=0)&&(confpinget!=0)&&(newpinget==confpinget)&&(oldpinget==pin)){
				String statem = "UPDATE accounts SET PIN='"+newpinget+"' WHERE Username='"+uname+"'";
				System.out.println(statem);
				d.updatepin(statem,uname);
				System.out.println("Updated Successfully");
			}
		});
	}
	private void ministatement(Stage arg0){
		GridPane MiniStatement = new GridPane();
		MiniStatement.setPadding(new Insets(10,10,10,10));
		MiniStatement.setHgap(10);
		MiniStatement.setVgap(10);
		Button getministatement = new Button("Get Mini Statement");
		TextArea statement = new TextArea();
		Button back = new Button("Back");
		statement.setEditable(false);
		MiniStatement.setConstraints(getministatement, 5, 1);
		MiniStatement.setConstraints(statement, 5, 5);
		MiniStatement.getChildren().addAll(getministatement,statement);
		Scene scene2 = new Scene(MiniStatement,500,500);
		arg0.setScene(scene2);
		getministatement.setOnAction(e->{getMinistatement(statement);});
	}
	private void getMinistatement(TextArea ta){
		String uname = username.getText();
		String statement = "SELECT * FROM accounts WHERE Username='"+uname+"'";
		d.ministatement(statement);
		Balance = d.getBalance();
		accno =  d.accountno();
		ta.setText("Your Balance for Account no. :"+accno+"\n is "+Balance);
	}
	private void balanceenquiry(Stage arg0){
		GridPane Balanceenquiry = new GridPane();
		Balanceenquiry.setPadding(new Insets(10,10,10,10));
		Balanceenquiry.setHgap(10);
		Balanceenquiry.setVgap(10);
		TextArea ta = new TextArea();
		Button populate = new Button("Detailed Enquiry");
		Balanceenquiry.setConstraints(ta, 2, 2);
		Balanceenquiry.setConstraints(populate, 5, 8);
		Balanceenquiry.getChildren().addAll(ta,populate);
		Scene scene = new Scene(Balanceenquiry,500,500);
		arg0.setScene(scene);
		populate.setOnAction(e->{});
	}
}
