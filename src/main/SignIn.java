package main;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Customer;
import util.Connect;

public class SignIn extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpFooter;
	private Label judulLabel, emailLabel, passwordLabel, questionLabel, signUpLabel, emptyFieldLbl;
	private Font fontJudul, fontText, fontTextBold, fontBtn;
	private TextField emailTF;
	private PasswordField passwordPF;
	private HBox hbBtn;
	private Button signInBtn;
	private FlowPane fpQuestion;
	
	private ArrayList<Customer> dataCustomer = new ArrayList<>();
	private Connect connect = Connect.getInstance();
	
	private void getLoginData(String inputEmail, String inputPassword) {
		dataCustomer.clear();
		
		String query = String.format("SELECT * FROM Customer WHERE customerEmail = '%s' AND customerPassword = '%s'", inputEmail, inputPassword);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idCustomer = connect.rs.getString("idCustomer");
				String namaCustomer = connect.rs.getString("namaCustomer");
				String customerDOB = connect.rs.getString("customerDOB");
				String customerGender = connect.rs.getString("customerGender");
				String customerPhone = connect.rs.getString("customerPhone");
				String customerEmail = connect.rs.getString("customerEmail");
				String customerPassword = connect.rs.getString("customerPassword");
				
				dataCustomer.add(new Customer(idCustomer, namaCustomer, customerDOB, customerGender, customerPhone, customerEmail, customerPassword));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		gpContainer = new GridPane();
		gpFooter = new GridPane();
		
		judulLabel = new Label("Sign In");
		emailLabel = new Label("Email");
		passwordLabel = new Label("Password");
		questionLabel = new Label("Tidak punya akun?");
		signUpLabel = new Label("Sign Up");
		emptyFieldLbl = new Label("Tolong isi semua data!");
		
		fontJudul = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		fontText = Font.font("Poppins", 15);
		fontTextBold = Font.font("Poppins", FontWeight.BOLD, 15);
		fontBtn = Font.font("Poppins", FontWeight.BOLD, 22);
		
		emailTF = new TextField();
		passwordPF = new PasswordField();
		
		hbBtn = new HBox();
		signInBtn = new Button("Sign In");
		
		fpQuestion = new FlowPane();
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void positioning() {
		fpQuestion.getChildren().addAll(questionLabel, signUpLabel);
		
		hbBtn.getChildren().add(signInBtn);
		
		gpContainer.add(emailLabel, 0, 0);
		gpContainer.add(emailTF, 0, 1);
		gpContainer.add(passwordLabel, 0, 2);
		gpContainer.add(passwordPF, 0, 3);
		
		gpFooter.add(hbBtn, 0, 0);
		gpFooter.add(fpQuestion, 0, 1);
		
		bpMain.setTop(judulLabel);
		bpMain.setCenter(gpContainer);
		bpMain.setBottom(gpFooter);
	}
	
	private void style() {		
		bpMain.setPrefWidth(390);
		bpMain.setPadding(new Insets(24, 20, 20, 20));
		
		bpMain.setAlignment(judulLabel, Pos.CENTER);
		
		gpContainer.setPrefWidth(390);
		gpContainer.setAlignment(Pos.TOP_CENTER);
		gpContainer.setVgap(4);
		bpMain.setMargin(judulLabel, new Insets(0, 0, 32, 0));
		gpContainer.setMargin(passwordLabel, new Insets(12, 0, 0, 0));
		
		gpFooter.setPrefWidth(390);
		gpFooter.setAlignment(Pos.CENTER);
		
		hbBtn.setAlignment(Pos.CENTER);
		fpQuestion.setAlignment(Pos.CENTER);
		
		//style
		judulLabel.setFont(fontJudul);
		judulLabel.setTextFill(Color.web("#458E5E"));
		
		emailLabel.setFont(fontText);
		passwordLabel.setFont(fontText);
		
		emailTF.setPrefSize(350, 40);
		emailTF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		
		passwordPF.setPrefSize(350, 40);
		passwordPF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		
		gpFooter.setVgap(16);
		
		signInBtn.setPrefSize(350, 48);
		signInBtn.setFont(fontBtn);
		signInBtn.setStyle("-fx-background-color: #458E5E; -fx-background-radius: 8px; -fx-border-radius: 15px;");
		signInBtn.setTextFill(Color.WHITE);
		
		questionLabel.setFont(fontText);
		signUpLabel.setFont(fontTextBold);
		signUpLabel.setTextFill(Color.BLUE);
		fpQuestion.setHgap(4);
		
		emptyFieldLbl.setFont(fontText);
		emptyFieldLbl.setTextFill(Color.RED);
	}
	
	private void handler(Stage stage) {
		signInBtn.setOnMouseClicked(e -> {
			if (emailTF.getText().isEmpty() || passwordPF.getText().isEmpty()) {
				if (!gpContainer.getChildren().contains(emptyFieldLbl)) {
					gpContainer.add(emptyFieldLbl, 0, 4);
				}else {
					emptyFieldLbl.setText("Tolong isi semua data!");
				}
				
			}else {
				String inputEmail = emailTF.getText();
				String inputPassword = passwordPF.getText();
				
				getLoginData(inputEmail, inputPassword);
				
				if (!dataCustomer.isEmpty()) {
					new Home(stage);
				}else {
					emptyFieldLbl.setText("Email atau password salah!");
					if (!gpContainer.getChildren().contains(emptyFieldLbl)) {
						gpContainer.add(emptyFieldLbl, 0, 4);
					}
				}
			}
			
		});
	}
	
	public SignIn(Stage stage) {
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage signInStage) throws Exception {
		initialize();
		positioning();
		style();
		handler(signInStage);
		
		changePage(signInStage);
		
		signInStage.setScene(scene);
	}
	
	private void changePage(Stage stage) {
		signUpLabel.setOnMouseClicked(e -> {
			new SignUp(stage);
		});
	}
	
}
