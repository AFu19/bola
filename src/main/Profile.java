package main;

import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Customer;
import util.Connect;

public class Profile extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpInfo;
	private Label judulLbl, namaLbl, tglLahirLbl, noHPLbl, emailLbl;
	private TextField namaTF, tglLahirTF, noHPTF, emailTF;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, subMenuHB;
	private Button informationBtn, faqBtn, logOutBtn;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV;
	private Font judulFont, font14, font20Semi;
	
	private Connect connect = Connect.getInstance();
	private String idCustomer;
	private Customer customer;
	
	private void initialize() {
		bpMain = new BorderPane();
		gpContainer = new GridPane();
		gpInfo = new GridPane();
		
		judulLbl = new Label("Profile");
		namaLbl = new Label("Nama Lengkap");
		tglLahirLbl = new Label("Tanggal Lahir");
		noHPLbl = new Label("Nomor Handphone");
		emailLbl = new Label("Email");
		
		namaTF = new TextField();
		tglLahirTF = new TextField();
		noHPTF = new TextField();
		emailTF = new TextField();
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		subMenuHB = new HBox();
		
		informationBtn = new Button("Information");
		faqBtn = new Button("FAQ");
		logOutBtn = new Button("Log Out");
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("AcProfile.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		
		judulFont = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		font14 = Font.font("Poppins", 14);
		font20Semi = Font.font("Poppins", FontWeight.BOLD, 20);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void setData() {
		String query = String.format("SELECT * FROM customer WHERE idCustomer = '%s'", idCustomer);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				namaTF.setText(connect.rs.getString("namaCustomer"));
				tglLahirTF.setText(connect.rs.getString("customerDOB"));
				noHPTF.setText(connect.rs.getString("customerPhone"));
				emailTF.setText(connect.rs.getString("customerEmail"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void positioning() {
		subMenuHB.getChildren().addAll(informationBtn, faqBtn);

		gpInfo.add(namaLbl, 0, 0);
		gpInfo.add(namaTF, 0, 1);
		gpInfo.add(tglLahirLbl, 0, 2);
		gpInfo.add(tglLahirTF, 0, 3);
		gpInfo.add(noHPLbl, 0, 4);
		gpInfo.add(noHPTF, 0, 5);
		gpInfo.add(emailLbl, 0, 6);
		gpInfo.add(emailTF, 0, 7);
		
		homeHB.getChildren().add(homeIV);
		tandingHB.getChildren().add(tandingIV);
		historyHB.getChildren().add(historyIV);
		forumHB.getChildren().add(forumIV);
		profileHB.getChildren().add(profileIV);
		
		gpContainer.add(subMenuHB, 0, 0);
		gpContainer.add(gpInfo, 0, 1);
		gpContainer.add(logOutBtn, 0, 2);
		
		menuHB.getChildren().addAll(homeHB, tandingHB, historyHB, forumHB, profileHB);
		
		bpMain.setTop(judulLbl);
		bpMain.setCenter(gpContainer);
		bpMain.setBottom(menuHB);
	}
	
	private void style() {
		bpMain.setPrefWidth(390);
		bpMain.setPadding(new Insets(24, 0, 0, 0));
		
		bpMain.setAlignment(judulLbl, Pos.CENTER);
		judulLbl.setFont(judulFont);
		judulLbl.setTextFill(Color.web("#458E5E"));

		subMenuHB.setMinWidth(342);
		subMenuHB.setMaxWidth(342);
		subMenuHB.setAlignment(Pos.CENTER);
		subMenuHB.setSpacing(70);
		subMenuHB.setPadding(new Insets(0, 0, 16, 0));
		
		informationBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent transparent #FF7E46 transparent; -fx-border-width: 3;");
		informationBtn.setPadding(new Insets(0));
		informationBtn.setMinSize(120, 40);
		informationBtn.setMaxSize(120, 40);
		informationBtn.setTextFill(Color.web("#FF7E46"));
		informationBtn.setFont(font20Semi);
		
		faqBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		faqBtn.setPadding(new Insets(0));
		faqBtn.setMinSize(120, 40);
		faqBtn.setMaxSize(120, 40);
		faqBtn.setTextFill(Color.web("#A3A3A3"));
		faqBtn.setFont(font20Semi);
		
		gpContainer.setPadding(new Insets(8, 24, 0, 24));
		gpContainer.setVgap(8);
		
		gpInfo.setVgap(4);
		gpInfo.setMargin(namaTF, new Insets(0, 0, 8, 0));
		gpInfo.setMargin(tglLahirTF, new Insets(0, 0, 8, 0));
		gpInfo.setMargin(noHPTF, new Insets(0, 0, 8, 0));
		gpInfo.setMargin(emailTF, new Insets(0, 0, 8, 0));
		gpInfo.setPrefHeight(520);
		
		namaLbl.setFont(font14);
		tglLahirLbl.setFont(font14);
		noHPLbl.setFont(font14);
		emailLbl.setFont(font14);
		
		namaTF.setPrefSize(342, 40);
		namaTF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		namaTF.setEditable(false);
		tglLahirTF.setPrefSize(342, 40);
		tglLahirTF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		tglLahirTF.setEditable(false);
		noHPTF.setPrefSize(342, 40);
		noHPTF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		noHPTF.setEditable(false);
		emailTF.setPrefSize(342, 40);
		emailTF.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 9px; -fx-border-radius: 9px; -fx-border-color: #458E5E;");
		emailTF.setEditable(false);
		
		logOutBtn.setPrefSize(342, 40);
		logOutBtn.setStyle("-fx-background-color: #F93A3A; -fx-background-radius: 9px;");
		logOutBtn.setFont(font20Semi);
		logOutBtn.setTextFill(Color.WHITE);
		
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
		faqBtn.setOnMouseClicked(e -> {
			new ProfileFAQ(stage, Home.idCustomer);
		});
		
		logOutBtn.setOnMouseClicked(e -> {
			new Landing(stage);
		});
		
		//menu
		homeHB.setOnMouseClicked(e -> {
			new Home(stage, Home.idCustomer);
		});
		
		historyHB.setOnMouseClicked(e -> {
			new History(stage, idCustomer);
		});
		
		forumHB.setOnMouseClicked(e -> {
			new Forum(stage, idCustomer);
		});
	}
	
	public Profile(Stage stage, String idCust) {
		idCustomer = idCust;
		
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage homeStage) throws Exception {
		initialize();
		setData();
		positioning();
		style();
		handler(homeStage);
		
		homeStage.setScene(scene);
	}

}
