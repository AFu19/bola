package main;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import util.Connect;

public class SignUp extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpFooter;
	private FlowPane fpGender, fpQuestion;
	private Label judulLbl, namaLbl, tglLahirLbl, genderLbl, nomorHPLbl, emailLbl, passwordLbl, questionLbl, signInLbl, errorLbl;
	private TextField namaTF, nomorHPTF, emailTF;
	private DatePicker tglLahirDP;
	private PasswordField passwordPF;
	private RadioButton rbLaki, rbPerempuan;
	private ToggleGroup tgGender;
	private HBox hbBtn;
	private Button signUpBtn;
	private Font fontJudul, fontText, fontTextBold, fontBtn;
	
	private Connect connect = Connect.getInstance();
	
	private void registerCustomer(String namaCustomer, String customerDOB, String customerGender, String customerPhone, String customerEmail, String customerPassword) {
		String query = String.format("INSERT INTO customer(namaCustomer, customerDOB, customerGender, customerPhone, customerEmail, customerPassword)"
				+ "VALUES('%s', '%s', '%s', '%s', '%s', '%s')", namaCustomer, customerDOB, customerGender, customerPhone, customerEmail, customerPassword);
		connect.execUpdate(query);
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		gpContainer = new GridPane();
		gpFooter = new GridPane();
		fpGender = new FlowPane();
		fpQuestion = new FlowPane();
		
		judulLbl = new Label("Sign Up");
		namaLbl = new Label("Nama Lengkap");
		tglLahirLbl = new Label("Tanggal Lahir");
		genderLbl = new Label("Gender");
		nomorHPLbl = new Label("Nomor Handphone");
		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		questionLbl = new Label("Sudah punya akun?");
		signInLbl = new Label("Sign In");
		errorLbl = new Label("Tolong isi semua data!");
		
		namaTF = new TextField();
		nomorHPTF = new TextField();
		emailTF = new TextField();
		tglLahirDP = new DatePicker();
		passwordPF = new PasswordField();
		
		rbLaki = new RadioButton("Laki-laki");
		rbPerempuan = new RadioButton("Perempuan");
		tgGender = new ToggleGroup();
		
		hbBtn = new HBox();
		signUpBtn = new Button("Sign Up");
		
		fontJudul = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		fontText = Font.font("Poppins", 15);
		fontTextBold = Font.font("Poppins", FontWeight.BOLD, 15);
		fontBtn = Font.font("Poppins", FontWeight.BOLD, 22);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void positioning(){
		fpGender.getChildren().addAll(rbLaki, rbPerempuan);
		
		gpContainer.add(namaLbl, 0, 0);
		gpContainer.add(namaTF, 0, 1);
		gpContainer.add(tglLahirLbl, 0, 2);
		gpContainer.add(tglLahirDP, 0, 3);
		gpContainer.add(genderLbl, 0, 4);
		gpContainer.add(fpGender, 0, 5);
		gpContainer.add(nomorHPLbl, 0, 6);
		gpContainer.add(nomorHPTF, 0, 7);
		gpContainer.add(emailLbl, 0, 8);
		gpContainer.add(emailTF, 0, 9);
		gpContainer.add(passwordLbl, 0, 10);
		gpContainer.add(passwordPF, 0, 11);
		
		hbBtn.getChildren().add(signUpBtn);
		fpQuestion.getChildren().addAll(questionLbl, signInLbl);
		
		gpFooter.add(hbBtn, 0, 0);
		gpFooter.add(fpQuestion, 0, 1);
		
		bpMain.setTop(judulLbl);
		bpMain.setCenter(gpContainer);
		bpMain.setBottom(gpFooter);
	}
	
	private void style() {
		bpMain.setPrefWidth(390);
		bpMain.setPadding(new Insets(24, 20, 20, 20));
		
		bpMain.setAlignment(judulLbl, Pos.CENTER);
		judulLbl.setFont(fontJudul);
		judulLbl.setTextFill(Color.web("#458E5E"));
		bpMain.setMargin(judulLbl, new Insets(0, 0, 32, 0));
		
		gpContainer.setPrefWidth(390);
		gpContainer.setAlignment(Pos.TOP_CENTER);
		gpContainer.setVgap(4);
		
		namaLbl.setFont(fontText);
		namaTF.setPrefSize(350, 40);
		namaTF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		namaTF.setPromptText("Tidak lebih dari 50 karakter!");
		
		tglLahirLbl.setPadding(new Insets(12, 0, 0, 0));
		tglLahirLbl.setFont(fontText);
		tglLahirDP.setPrefSize(350, 40);
		tglLahirDP.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-text-field-background-radius: 10px; -fx-border-color: #458E5E;");
		
		genderLbl.setFont(fontText);
		genderLbl.setPadding(new Insets(12, 0, 0, 0));
		
		rbLaki.setFont(fontText);
		rbPerempuan.setFont(fontText);
		tgGender.getToggles().addAll(rbLaki, rbPerempuan);
		fpGender.setHgap(40);
		
		nomorHPLbl.setFont(fontText);
		nomorHPLbl.setPadding(new Insets(12, 0, 0, 0));
		nomorHPTF.setPrefSize(350, 40);
		nomorHPTF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		nomorHPTF.setPromptText("08...");
		
		emailLbl.setFont(fontText);
		emailLbl.setPadding(new Insets(12, 0, 0, 0));
		emailTF.setPrefSize(350, 40);
		emailTF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		emailTF.setPromptText("example@gmail.com");
		
		passwordLbl.setFont(fontText);
		passwordLbl.setPadding(new Insets(12, 0, 0, 0));
		passwordPF.setPrefSize(350, 40);
		passwordPF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		passwordPF.setPromptText("8 - 20 karakter");
		
		gpFooter.setVgap(16);
		
		hbBtn.setAlignment(Pos.CENTER);
		signUpBtn.setStyle("-fx-background-color: #458E5E; -fx-background-radius: 8px; -fx-border-radius: 15px;");
		signUpBtn.setFont(fontBtn);
		signUpBtn.setPrefSize(350, 48);
		signUpBtn.setTextFill(Color.WHITE);
		
		questionLbl.setFont(fontText);
		signInLbl.setFont(fontTextBold);
		signInLbl.setTextFill(Color.BLUE);
		fpQuestion.setHgap(4);
		fpQuestion.setAlignment(Pos.CENTER);
		
		errorLbl.setFont(fontText);
		errorLbl.setTextFill(Color.RED);
	}
	
	private void errorHandling(String errorMsg) {
		errorLbl.setText(errorMsg);
		if (!gpContainer.getChildren().contains(errorLbl)) {
			gpContainer.add(errorLbl, 0, 12);
		}
	}

	private void handler(Stage stage){
		signUpBtn.setOnMouseClicked(e -> {
			if (namaTF.getText().isEmpty() || tglLahirDP.getValue() == null || tgGender.getSelectedToggle() == null || nomorHPTF.getText().isEmpty() || emailTF.getText().isEmpty() || passwordPF.getText().isEmpty()) {
				errorHandling("Tolong isi semua data!");
			}else if (namaTF.getText().length() > 50) {
				errorHandling("Nama tidak lebih dari 50 karakter!");
			}else if(tglLahirDP.getValue().isAfter(LocalDate.now())){
				errorHandling("Tanggal Lahir tidak valid!");
			}else if (nomorHPTF.getText().length() > 14) {
				errorHandling("Nomor handphone tidak lebih dari 14 digit!");
			}else if (!emailTF.getText().endsWith("@gmail.com")) {
				errorHandling("Email berakhir dengan '@gmail.com'!");
			}else if (passwordPF.getText().length() > 20 || passwordPF.getText().length() < 8) {
				errorHandling("Password harus memiliki 8 - 20 karakter!");
			}else {
				String namaCustomer = namaTF.getText();
				String customerDOB = tglLahirDP.getValue().toString();
				
				String customerGender;
				if (rbLaki.isSelected()) {
					customerGender = "Male";
				}else {
					customerGender = "Female";
				}
				
				String customerPhone = nomorHPTF.getText();
				String customerEmail = emailTF.getText();
				String customerPassword = passwordPF.getText();
				
				registerCustomer(namaCustomer, customerDOB, customerGender, customerPhone, customerEmail, customerPassword);
				new SignIn(stage);
			}
		});
		
	}
	
	
	public SignUp(Stage stage) {
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage signUpStage) throws Exception {
		initialize();
		positioning();
		style();
		handler(signUpStage);
		
		changePage(signUpStage);
		
		signUpStage.setScene(scene);
	}
	
	private void changePage(Stage stage) {
		signInLbl.setOnMouseClicked(e -> {
			new SignIn(stage);
		});
	}

}
