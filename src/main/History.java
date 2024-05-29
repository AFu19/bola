package main;

import java.io.InputStream;
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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.HistoryObject;
import model.JenisLapangan;
import util.Connect;

public class History extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpJenis;
	private Label judulLbl;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, subMenuHB;
	private Button confirmBtn, completeBtn;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV;
	private Font judulFont, font16Semi, font20Semi, font12;
	private ListView<HBox> bookingListView;
	
	private ArrayList<HistoryObject> dataHistory = new ArrayList<>();
	private Connect connect = Connect.getInstance();
	public String idCustomer;
	
	
	private void getData() {
		dataHistory.clear();
		
		String query = "SELECT DISTINCT bl.idBooking, bl.tanggalBooking, g.idGymnasium, g.namaGymnasium, g.fotoGymnasium \r\n" + 
				"FROM bookinglapangan bl JOIN detailbooking db ON bl.idBooking = db.idBooking\r\n" + 
				"JOIN detaillapangan dl ON db.idLapangan = dl.idLapangan\r\n" + 
				"JOIN gymnasium g ON dl.idGymnasium = g.idGymnasium\r\n" + 
				"WHERE bl.idCustomer = '" + idCustomer + "'\r\n" + 
				"AND bl.statusBooking = 'Confirmed'\r\n" +
				"ORDER BY bl.tanggalBooking";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idBooking = connect.rs.getString("idBooking");
				Date tanggalBooking = connect.rs.getDate("tanggalBooking");
				String idGymnasium = connect.rs.getString("idGymnasium");
				String namaGymnasium = connect.rs.getString("namaGymnasium");
				Blob fotoGymnasium = connect.rs.getBlob("fotoGymnasium");
				
				dataHistory.add(new HistoryObject(idBooking, tanggalBooking, idGymnasium, namaGymnasium, fotoGymnasium));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		gpContainer = new GridPane();
		gpJenis = new GridPane();
		
		judulLbl = new Label("History");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		subMenuHB = new HBox();
		
		confirmBtn = new Button("Confirmed");
		completeBtn = new Button("Completed");
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("AcHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("InProfile.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		
		bookingListView = new ListView<>();
		
		judulFont = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		font16Semi = Font.font("Poppins", FontWeight.BOLD, 16);
		font20Semi = Font.font("Poppins", FontWeight.BOLD, 20);
		font12 = Font.font("Poppins", 12);
		
		scene = new Scene(bpMain, 390, 800);
	}

	private void initializeListView() {
		getData();
			
		for (HistoryObject historyObject : dataHistory) {
			FlowPane fp = new FlowPane();
			GridPane gp = new GridPane();
			HBox hb = new HBox();
			
			InputStream inputStream;
			Image image, iconImg = new Image("iconDate.png");
			ImageView imageView = new ImageView(), iconImgView = new ImageView(iconImg);
			
			Label namaGymnasium = new Label(), tanggalLabel = new Label();
			
			tanggalLabel.setFont(font12);
			namaGymnasium.setFont(font16Semi);
			try {
				inputStream = historyObject.getFotoGymnasium().getBinaryStream();
				image = new Image(inputStream, 80, 80, false, false);
				imageView.setImage(image);
				
				namaGymnasium.setText(historyObject.getNamaGymnasium());
				
				Date date = historyObject.getTanggalBooking();
				SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM yyyy");
				String formatDate = outputDateFormat.format(date);
				
				tanggalLabel.setText(formatDate);
				
				//positioning
				gp.add(namaGymnasium, 0, 0, 2, 1);
				gp.add(iconImgView, 0, 1);
				gp.add(tanggalLabel, 1, 1);
				
				fp.getChildren().addAll(imageView, gp);
				
				hb.getChildren().add(fp);
				
				//style
				hb.setPrefWidth(300);
				
				imageView.setStyle("-fx-border-radius: 6px;");
				
				fp.setAlignment(Pos.TOP_LEFT);
				fp.setHgap(16);
				fp.setPadding(new Insets(5));

				gp.setHgap(4);
				
				//add to listView
				bookingListView.getItems().add(hb);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void positioning() {
		subMenuHB.getChildren().addAll(confirmBtn, completeBtn);
		
		homeHB.getChildren().add(homeIV);
		tandingHB.getChildren().add(tandingIV);
		historyHB.getChildren().add(historyIV);
		forumHB.getChildren().add(forumIV);
		profileHB.getChildren().add(profileIV);
		
		gpContainer.add(subMenuHB, 0, 0);
		gpContainer.add(bookingListView, 0, 1);
		
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
		subMenuHB.setSpacing(80);
		subMenuHB.setPadding(new Insets(0, 0, 16, 0));
		
		confirmBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent transparent #FF7E46 transparent; -fx-border-width: 3;");
		confirmBtn.setPadding(new Insets(0));
		confirmBtn.setMinSize(110, 40);
		confirmBtn.setMaxSize(110, 40);
		confirmBtn.setTextFill(Color.web("#FF7E46"));
		confirmBtn.setFont(font20Semi);
		
		completeBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		completeBtn.setPadding(new Insets(0));
		completeBtn.setMinSize(110, 40);
		completeBtn.setMaxSize(110, 40);
		completeBtn.setTextFill(Color.web("#A3A3A3"));
		completeBtn.setFont(font20Semi);
		
		gpContainer.setPadding(new Insets(8, 24, 0, 24));
		gpContainer.setVgap(8);
		
		gpJenis.setVgap(12);
		gpJenis.setPadding(new Insets(0, 0, 12, 0));
		bookingListView.setMaxWidth(342);
		bookingListView.setPrefHeight(700);
		bookingListView.getStylesheets().add("style.css");
		bookingListView.getStyleClass().add("list-view-1");
		
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
		completeBtn.setOnMouseClicked(e -> {
			new HistoryCompleted(stage, Home.idCustomer);
		});
		
		bookingListView.setOnMouseClicked(e -> {
			if (!bookingListView.getSelectionModel().isEmpty()) {		
				String idBooking = dataHistory.get(bookingListView.getSelectionModel().getSelectedIndex()).getIdBooking();
				String idGymnasium = dataHistory.get(bookingListView.getSelectionModel().getSelectedIndex()).getIdGymnasium();

				new DetailHistoryConfirmed(stage, idBooking, idGymnasium);
			}
		});
		
		//menu
		homeHB.setOnMouseClicked(e -> {
			new Home(stage, Home.idCustomer);
		});
		
		forumHB.setOnMouseClicked(e -> {
			new Forum(stage, idCustomer);
		});
		
		profileHB.setOnMouseClicked(e -> {
			new Profile(stage, idCustomer);
		});
	}
	
	public History(Stage stage, String idCust) {
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
		positioning();
		style();
		initializeListView();
		handler(homeStage);
		
		homeStage.setScene(scene);
	}

}
