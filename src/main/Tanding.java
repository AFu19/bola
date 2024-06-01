package main;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Lomba;
import util.Connect;

public class Tanding extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpJenis;
	private Label judulLbl;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, subMenuHB;
	private Button turnamenBtn, sparringBtn;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV;
	private Font judulFont, font16Semi, font20Semi, font12;
	private ListView<HBox> turnamenListView;
	
	private ArrayList<Lomba> dataLomba = new ArrayList<>();
	private Connect connect = Connect.getInstance();
	public String idCustomer;
	
	
	private void getData() {
		dataLomba.clear();
		
		String query = "SELECT * FROM lomba WHERE status = 'Open'";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idLomba = connect.rs.getString("idLomba");
				String idAdmin = connect.rs.getString("idAdmin");
				String namaLomba = connect.rs.getString("namaLomba");
				Date tanggalLomba = connect.rs.getDate("tanggalLomba");
				Time waktuLomba = connect.rs.getTime("waktuLomba");
				String lokasiLomba = connect.rs.getString("lokasiLomba");
				int prizepool = connect.rs.getInt("prizepool");
				int hargaPendaftaran = connect.rs.getInt("hargaPendaftaran");
				String namaContactPerson = connect.rs.getString("namaContactPerson");
				String telfonContactPerson = connect.rs.getString("telfonContactPerson");
				int jumlahMaxPartis = connect.rs.getInt("jumlahMaxPartis");
				String status = connect.rs.getString("status");
				
				dataLomba.add(new Lomba(idLomba, idAdmin, namaLomba, tanggalLomba, waktuLomba, lokasiLomba, prizepool, hargaPendaftaran, namaContactPerson, telfonContactPerson, jumlahMaxPartis, status));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		gpContainer = new GridPane();
		gpJenis = new GridPane();
		
		judulLbl = new Label("Tanding");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		subMenuHB = new HBox();
		
		turnamenBtn = new Button("Turnamen");
		sparringBtn = new Button("Sparring");
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("AcTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("InProfile.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		
		turnamenListView = new ListView<>();
		
		judulFont = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		font16Semi = Font.font("Poppins", FontWeight.BOLD, 16);
		font20Semi = Font.font("Poppins", FontWeight.BOLD, 20);
		font12 = Font.font("Poppins", 12);
		
		scene = new Scene(bpMain, 390, 800);
	}

	private void initializeListView() {
		getData();
			
		for (Lomba lomba : dataLomba) {
			GridPane gp = new GridPane();
			HBox hb = new HBox();
			
			Image locationImg = new Image("iconLocation.png");
			Image prizepoolImg = new Image("iconPrize.png");
			Image dateImg = new Image("iconDate.png");
			
			ImageView locationIV = new ImageView(locationImg);
			ImageView prizepoolIV = new ImageView(prizepoolImg);
			ImageView dateIV = new ImageView(dateImg);
			
			Label namaTurnamenLbl = new Label();
			Label locationLbl = new Label();
			Label prizepoolLbl = new Label();
			Label tanggalLabel = new Label();
			
			//setData
			Date date = lomba.getTanggalLomba();
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM yyyy");
			String formatDate = outputDateFormat.format(date);
			
			namaTurnamenLbl.setText(lomba.getNamaLomba());
			locationLbl.setText(lomba.getLokasiLomba());
			prizepoolLbl.setText("Rp" + lomba.getPrizepool());
			tanggalLabel.setText(formatDate);
			
			//positioning
			gp.add(namaTurnamenLbl, 0, 0, 2, 1);
			gp.add(locationIV, 0, 1);
			gp.add(locationLbl, 1, 1);
			gp.add(prizepoolIV, 0, 2);
			gp.add(prizepoolLbl, 1, 2);
			gp.add(dateIV, 0, 3);
			gp.add(tanggalLabel, 1, 3);
						
			hb.getChildren().add(gp);
			
			//style
			hb.setPrefWidth(300);

			namaTurnamenLbl.setFont(font16Semi);
			locationLbl.setFont(font12);
			prizepoolLbl.setFont(font12);
			tanggalLabel.setFont(font12);
			
			gp.setPrefWidth(300);;
			gp.setHgap(4);
			
			//add to listView
			turnamenListView.getItems().add(hb);
		}
	}
	
	private void positioning() {
		subMenuHB.getChildren().addAll(turnamenBtn, sparringBtn);
		
		homeHB.getChildren().add(homeIV);
		tandingHB.getChildren().add(tandingIV);
		historyHB.getChildren().add(historyIV);
		forumHB.getChildren().add(forumIV);
		profileHB.getChildren().add(profileIV);
		
		gpContainer.add(subMenuHB, 0, 0);
		gpContainer.add(turnamenListView, 0, 1);
		
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
		
		turnamenBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent transparent #FF7E46 transparent; -fx-border-width: 3;");
		turnamenBtn.setPadding(new Insets(0));
		turnamenBtn.setMinSize(110, 40);
		turnamenBtn.setMaxSize(110, 40);
		turnamenBtn.setTextFill(Color.web("#FF7E46"));
		turnamenBtn.setFont(font20Semi);
		
		sparringBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		sparringBtn.setPadding(new Insets(0));
		sparringBtn.setMinSize(110, 40);
		sparringBtn.setMaxSize(110, 40);
		sparringBtn.setTextFill(Color.web("#A3A3A3"));
		sparringBtn.setFont(font20Semi);
		
		gpContainer.setPadding(new Insets(8, 24, 0, 24));
		gpContainer.setVgap(8);
		
		gpJenis.setVgap(12);
		gpJenis.setPadding(new Insets(0, 0, 12, 0));
		turnamenListView.setMaxWidth(342);
		turnamenListView.setPrefHeight(700);
		turnamenListView.getStylesheets().add("style.css");
		turnamenListView.getStyleClass().add("list-view-1");
		
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
		sparringBtn.setOnMouseClicked(e -> {
			new TandingSparring(stage, idCustomer);
		});
		
		turnamenListView.setOnMouseClicked(e -> {
			if (!turnamenListView.getSelectionModel().isEmpty()) {		
				new DetailTurnamen(stage, dataLomba.get(turnamenListView.getSelectionModel().getSelectedIndex()));
			}
		});
		
		//menu
		homeHB.setOnMouseClicked(e -> {
			new Home(stage, idCustomer);
		});
		
		historyHB.setOnMouseClicked(e -> {
			new History(stage, idCustomer);
		});
		
		forumHB.setOnMouseClicked(e -> {
			new Forum(stage, idCustomer);
		});
		
		profileHB.setOnMouseClicked(e -> {
			new Profile(stage, idCustomer);
		});
	}
	
	public Tanding(Stage stage, String idCust) {
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
