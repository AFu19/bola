package main;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Peralatan;
import util.Connect;

public class FormForum extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer;
	private Label judulLbl, judulForumLbl, isiForumLbl, errorLbl;
	private TextField judulTF;
	private TextArea isiTA;
	private Button submitBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, submitHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, font16;

	private Connect connect = Connect.getInstance();
	private String idCustomer;
	private ArrayList<Peralatan> dataEquipment = new ArrayList<>();
	
	private void insertForum() {
		LocalDate date = LocalDate.now();
		
		String query = String.format("INSERT INTO forum VALUES(null, '%s', '%s', '%s', '%s')", idCustomer, judulTF.getText(), isiTA.getText(), date.toString());
		connect.execUpdate(query);
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		
		judulLbl = new Label("Post Forum");
		judulForumLbl = new Label("Judul Forum");
		isiForumLbl = new Label("Isi Forum");
		errorLbl = new Label();
		
		judulTF = new TextField();
		isiTA = new TextArea();
		
		submitBtn = new Button("Submit");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		backHB = new HBox();
		submitHB = new HBox();
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("InProfile.png");
		backImg = new Image("back.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		backImgView = new ImageView(backImg);
		
		judulFont = Font.font("Poppins", FontWeight.BOLD, 30);
		namaFont = Font.font("Poppins", FontWeight.BOLD, 20);
		font16 = Font.font("Poppins", 16);
		
		scene = new Scene(bpMain, 390, 800);
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		submitHB.getChildren().add(submitBtn);
		
		gpContainer.add(judulForumLbl, 0, 0);
		gpContainer.add(judulTF, 0, 1);
		gpContainer.add(isiForumLbl, 0, 2);
		gpContainer.add(isiTA, 0, 3);
		gpContainer.add(errorLbl, 0, 4);
		gpContainer.add(submitHB, 0, 5);
		
		homeHB.getChildren().add(homeIV);
		tandingHB.getChildren().add(tandingIV);
		historyHB.getChildren().add(historyIV);
		forumHB.getChildren().add(forumIV);
		profileHB.getChildren().add(profileIV);
		
		menuHB.getChildren().addAll(homeHB, tandingHB, historyHB, forumHB, profileHB);
		
		bpMain.setTop(fpHeader);
		bpMain.setCenter(gpContainer);
		bpMain.setBottom(menuHB);
	}
	
	private void style() {
		bpMain.setPrefWidth(390);
		bpMain.setPadding(new Insets(24, 0, 0, 0));
		
		fpHeader.setAlignment(Pos.CENTER_LEFT);
		fpHeader.setPadding(new Insets(0, 0, 0, 24));
		judulLbl.setMinWidth(294);
		judulLbl.setAlignment(Pos.CENTER);
		
		backHB.setAlignment(Pos.CENTER_LEFT);
		
		bpMain.setAlignment(judulLbl, Pos.CENTER);
		judulLbl.setFont(judulFont);
		judulLbl.setTextFill(Color.web("#458E5E"));

		gpContainer.setPadding(new Insets(24));
		gpContainer.setVgap(4);
		gpContainer.setMargin(judulTF, new Insets(0, 0, 8, 0));
		
		judulForumLbl.setFont(font16);
		isiForumLbl.setFont(font16);
		
		judulTF.setPrefSize(342, 40);
		judulTF.setStyle("-fx-border-color: #000000; -fx-border-width: 2; -fx-border-radius: 6; -fx-background-radius: 6;");
		isiTA.setMinSize(342, 90);
		isiTA.setMaxSize(342, 90);
		isiTA.setStyle("-fx-border-color: #000000; -fx-border-width: 2; -fx-border-radius: 6; -fx-background-radius: 6;");
		isiTA.setWrapText(true);
		
		errorLbl.setFont(font16);
		errorLbl.setTextFill(Color.RED);
		
		submitBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		submitBtn.setPrefSize(160, 48);
		submitBtn.setTextFill(Color.WHITE);
		submitBtn.setFont(namaFont);
		
		submitHB.setPrefSize(390, 500);
		submitHB.setAlignment(Pos.BOTTOM_CENTER);
		submitHB.setPadding(new Insets(0, 0, 12, 0));
		
		menuHB.setPrefWidth(390);
		menuHB.setStyle("-fx-background-color: #F4F4F4; -fx-border-color: black transparent transparent transparent");
		menuHB.setPadding(new Insets(4, 26, 4, 26));
		
		homeHB.setPadding(new Insets(0, 16, 0, 11));
		tandingHB.setPadding(new Insets(0, 16, 0, 16));
		historyHB.setPadding(new Insets(0, 16, 0, 16));
		forumHB.setPadding(new Insets(0, 16, 0, 16));
		profileHB.setPadding(new Insets(0, 11, 0, 16));
	}
	
	private void handler(Stage stage) {
		backHB.setOnMouseClicked(e -> {
			new Forum(stage, Home.idCustomer);
		});
		
		submitBtn.setOnMouseClicked(e -> {
			if (judulTF.getText().isEmpty() || isiTA.getText().isEmpty()) {
				errorLbl.setText("Isi semua field!");
			}else if (isiTA.getText().length() > 255) {
				errorLbl.setText("Isi forum tidak melebihi 255 karakter!");
			}else {
				errorLbl.setText("");
				insertForum();
				new Forum(stage, Home.idCustomer);
			}
		});
		
	}
	
	public FormForum(Stage stage, String inputIdCustomer) {
		idCustomer = inputIdCustomer;
		
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage detailLapanganStage) throws Exception {
		initialize();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
