package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Landing extends Application{

	private Scene scene;
	private GridPane gp;
	private Image img;
	private ImageView imgView;
	private HBox hb;
	private Font fontBtn;
	private Button btnSignIn, btnSignUp, btnSignUpCourtOwner;
	
	private void initialize() {
		gp = new GridPane();
		img = new Image("LOGO_BOLA.png");
		imgView = new ImageView(img);
		hb = new HBox();
		btnSignIn = new Button("Sign In");
		btnSignUp = new Button("Sign Up");
		btnSignUpCourtOwner = new Button("Sign Up as Court Owner");
		
		fontBtn = Font.font("Poppins", FontWeight.BOLD, 22);
		
		scene = new Scene(gp, 390, 800);
	}
	
	private void positioning() {
		hb.getChildren().add(btnSignIn);
		hb.getChildren().add(btnSignUp);
		
		gp.add(imgView, 0, 0);
		gp.add(hb, 0, 1);
		gp.add(btnSignUpCourtOwner, 0, 2);
	}
	
	private void style() {		
		gp.setPrefWidth(390);
		gp.setAlignment(Pos.CENTER);
		gp.setMargin(imgView, new Insets(120, 0, 330, 73));
		
		hb.setPrefWidth(388);
		hb.setAlignment(Pos.CENTER);
		hb.setMargin(btnSignIn, new Insets(0, 16, 0, 0));
		
		gp.setMargin(btnSignUpCourtOwner, new Insets(12, 0, 0, 26));
		
		btnSignIn.setPrefSize(160, 48);
		btnSignIn.setFont(fontBtn);
		btnSignIn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px; -fx-border-radius: 15px;");
		btnSignIn.setTextFill(Color.WHITE);
		
		btnSignUp.setPrefSize(160, 48);
		btnSignUp.setFont(fontBtn);
		btnSignUp.setStyle("-fx-background-color: #458E5E; -fx-background-radius: 8px; -fx-border-radius: 15px;");
		btnSignUp.setTextFill(Color.WHITE);

		btnSignUpCourtOwner.setPrefSize(336, 48);
		btnSignUpCourtOwner.setFont(fontBtn);
		btnSignUpCourtOwner.setStyle("-fx-background-color: #458E5E; -fx-background-radius: 8px; -fx-border-radius: 15px;");
		btnSignUpCourtOwner.setTextFill(Color.WHITE);
	}
	
	public Landing(Stage stage) {
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage landingStage) throws Exception {
		initialize();
		positioning();
		style();
		
		changePage(landingStage);
		
		landingStage.setScene(scene);
	}
	
	private void changePage(Stage stage) {
		btnSignIn.setOnMouseClicked(e -> {
			 new SignIn(stage);
		});
		
		btnSignUp.setOnMouseClicked(e -> {
			new SignUp(stage);
		});
	}
	
}
