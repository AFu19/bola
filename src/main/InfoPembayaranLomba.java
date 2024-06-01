package main;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DetailBooking;
import model.Gymnasium;
import model.Lomba;
import util.Connect;

public class InfoPembayaranLomba extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer;
	private Label judulLbl;
	private Button okeBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, btnHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg, petunjukImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView, petunjukIV;
	private Font judulFont, btnFont;
	
	private Lomba lomba;
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		
		judulLbl = new Label("Info Pembayaran");
		
		okeBtn = new Button("Oke");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		backHB = new HBox();
		btnHB = new HBox();
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("InProfile.png");
		backImg = new Image("back.png");
		petunjukImg = new Image("VirtualAccount.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		backImgView = new ImageView(backImg);
		petunjukIV = new ImageView(petunjukImg);
		
		judulFont = Font.font("Poppins", FontWeight.BOLD, 30);
		btnFont = Font.font("Poppins", FontWeight.BOLD, 22);
		
		scene = new Scene(bpMain, 390, 800);
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		btnHB.getChildren().add(okeBtn);
		
		gpContainer.add(petunjukIV, 0, 0);
		gpContainer.add(btnHB, 0, 1);
		
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
		fpHeader.setHgap(24);
		
		backHB.setAlignment(Pos.CENTER_LEFT);
		
		bpMain.setAlignment(judulLbl, Pos.CENTER);
		judulLbl.setFont(judulFont);
		judulLbl.setTextFill(Color.web("#458E5E"));

		gpContainer.setPadding(new Insets(24));
		gpContainer.setPrefWidth(390);
		gpContainer.setVgap(48);
		
		okeBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		okeBtn.setPrefSize(160, 48);
		okeBtn.setTextFill(Color.WHITE);
		okeBtn.setFont(btnFont);
		
		btnHB.setAlignment(Pos.BOTTOM_CENTER);
		btnHB.setMargin(okeBtn, new Insets(0, 0, 12, 0));
				
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
			new DetailTurnamen(stage, lomba);
		});
		
		okeBtn.setOnMouseClicked(e -> {
			Stage popupStage;
			Scene popupScene;
			BorderPane popupPane;
			GridPane labelPane;
			Image tickImg;
			ImageView tickIV;
			Label notifLabel1, notifLabel2;
			HBox buttonBox;
			Button nextButton;
			
			//initialize
			popupStage = new Stage();
			popupPane = new BorderPane();
			labelPane = new GridPane();
			tickImg = new Image("tick.png");
			tickIV = new ImageView(tickImg);
			notifLabel1 = new Label("Pembayaran");
			notifLabel2 = new Label("Sukses!");
			buttonBox = new HBox();
			nextButton = new Button("Kembali");
			popupScene = new Scene(popupPane, 222, 254);
			
			//positioning
			labelPane.add(notifLabel1, 0, 0);
			labelPane.add(notifLabel2, 0, 1);
			
			buttonBox.getChildren().add(nextButton);
			
			popupPane.setTop(tickIV);
			popupPane.setCenter(labelPane);
			popupPane.setBottom(buttonBox);
			
			//style
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setTitle("Notification");
			popupStage.setResizable(false);
			popupStage.setMaximized(false);
			
			popupPane.setPadding(new Insets(24, 0, 0, 0));
			popupPane.setPrefSize(166, 206);
			popupPane.setAlignment(tickIV, Pos.CENTER);
			
			labelPane.setPrefSize(166, 90);
			labelPane.setAlignment(Pos.CENTER);
			notifLabel1.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 24));
			notifLabel1.setTextFill(Color.web("#458E5E"));
			notifLabel1.setMinWidth(166);
			notifLabel1.setAlignment(Pos.TOP_CENTER);
			
			notifLabel2.setFont(Font.font("Poppins", FontWeight.EXTRA_BOLD, 24));
			notifLabel2.setTextFill(Color.web("#458E5E"));
			notifLabel2.setMinWidth(166);
			notifLabel2.setAlignment(Pos.CENTER);
			
			nextButton.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 10px;");
			nextButton.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
			nextButton.setMinSize(160, 48);
			nextButton.setTextFill(Color.WHITE);
			
			buttonBox.setPadding(new Insets(0, 0, 24, 0));
			buttonBox.setAlignment(Pos.TOP_CENTER);
			
			//handler
			popupStage.setOnHidden(event -> {
				new Tanding(stage, Home.idCustomer);
			});
			nextButton.setOnMouseClicked(event -> {
				popupStage.hide();
				new Tanding(stage, Home.idCustomer);
			});
			
			//main
			popupStage.setScene(popupScene);
			popupStage.showAndWait();
			
		});

		//menu
		homeHB.setOnMouseClicked(e -> {
			new Home(stage, Home.idCustomer);
		});
		
		tandingHB.setOnMouseClicked(e -> {
			new Tanding(stage, Home.idCustomer);
		});
		
		historyHB.setOnMouseClicked(e -> {
			new History(stage, Home.idCustomer);
		});
		
		forumHB.setOnMouseClicked(e -> {
			new Forum(stage, Home.idCustomer);
		});
		
		profileHB.setOnMouseClicked(e -> {
			new Profile(stage, Home.idCustomer);
		});
	}
	
	public InfoPembayaranLomba(Stage stage, Lomba lomba) {
		this.lomba = lomba;
		
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
