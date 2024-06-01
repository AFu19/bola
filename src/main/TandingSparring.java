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
import model.SparringObject;
import util.Connect;

public class TandingSparring extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpJenis;
	private Label judulLbl;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, subMenuHB;
	private Button turnamenBtn, sparringBtn;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV;
	private Font judulFont, font16Semi, font20Semi, font12;
	private ListView<HBox> sparringListView;
	
	private ArrayList<SparringObject> dataSparring = new ArrayList<>();
	private Connect connect = Connect.getInstance();
	public String idCustomer;
	
	private void getData() {
		dataSparring.clear();
		
		String query = "SELECT DISTINCT s.idSparring, s.idBooking, g.idGymnasium, jl.idJenisLapangan, c.idCustomer , s.minPemain, s.maxPemain, bl.tanggalBooking\r\n" + 
				"FROM sparring s, bookinglapangan bl, customer c, detailbooking db, detaillapangan dl, gymnasium g, jenislapangan jl\r\n" + 
				"WHERE s.idBooking = bl.idBooking\r\n" + 
				"AND bl.idBooking = db.idBooking\r\n" + 
				"AND bl.idCustomer = c.idCustomer\r\n" + 
				"AND db.idLapangan = dl.idLapangan\r\n" + 
				"AND dl.idGymnasium = g.idGymnasium\r\n" + 
				"AND g.idJenisLapangan = jl.idJenisLapangan\r\n" + 
				"AND bl.statusBooking = 'Confirmed'\r\n" + 
				"AND c.idCustomer != '" + idCustomer + "'\r\n" + 
				"AND NOT EXISTS(\r\n" + 
				"	SELECT idSparring FROM detailsparring ds\r\n" + 
				"    WHERE s.idSparring = ds.idSparring\r\n" + 
				")\r\n" + 
				"ORDER BY bl.tanggalBooking";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idSparring = connect.rs.getString("idSparring");
				String idBooking = connect.rs.getString("idBooking");
				String idGymnasium = connect.rs.getString("idGymnasium");
				String idJenisLapangan = connect.rs.getString("idJenisLapangan");
				String idCustomer = connect.rs.getString("idCustomer");
				int minPemain = connect.rs.getInt("minPemain");
				int maxPemain = connect.rs.getInt("maxPemain");
				Date tanggalBooking = connect.rs.getDate("tanggalBooking");
				
				dataSparring.add(new SparringObject(idSparring, idBooking, idGymnasium, idJenisLapangan, idCustomer, minPemain, maxPemain, tanggalBooking));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getNamaJenisLapangan(String idJenisLapangan) {
		String query = String.format("SELECT namaJenisLapangan from jenislapangan WHERE idJenisLapangan = '%s'", idJenisLapangan);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				return connect.rs.getString("namaJenisLapangan");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String getNamaCustomer(String inputIdCustomer) {
		String query = String.format("SELECT namaCustomer FROM customer WHERE idCustomer = '%s'", inputIdCustomer);
		connect.rs = connect.execQuery(query);

		String namaLengkap = "";
		try {
			while (connect.rs.next()) {
				namaLengkap = connect.rs.getString("namaCustomer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return namaLengkap;
	}
	
	private String getNamaGymnasium(String idGymnasium) {
		String query = String.format("SELECT namaGymnasium from gymnasium WHERE idGymnasium = '%s'", idGymnasium);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				return connect.rs.getString("namaGymnasium");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String getJadwal(String idBooking) {
		String query = String.format("SELECT MIN(sl.jamMulai) as jamMulai, MAX(sl.jamSelesai) as jamSelesai FROM bookinglapangan bl, detailbooking db, shiftlapangan sl WHERE bl.idBooking = db.idBooking AND db.idShiftLapangan = sl.idShiftLapangan AND bl.idBooking = '%s'", idBooking);
		connect.rs = connect.execQuery(query);
		
		
		String jamMulaiBook, jamSelesaiBook, jadwalTemp = "";
		try {
			while (connect.rs.next()) {
				jamMulaiBook = connect.rs.getString("jamMulai").substring(0, 5);
				jamSelesaiBook = connect.rs.getString("jamSelesai").substring(0, 5);
			
				jadwalTemp = jamMulaiBook + "-" + jamSelesaiBook;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jadwalTemp;
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
		
		sparringListView = new ListView<>();
		
		judulFont = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		font16Semi = Font.font("Poppins", FontWeight.BOLD, 16);
		font20Semi = Font.font("Poppins", FontWeight.BOLD, 20);
		font12 = Font.font("Poppins", 12);
		
		scene = new Scene(bpMain, 390, 800);
	}

	private void initializeListView() {
		getData();
			
		for (SparringObject sparringObject : dataSparring) {
			GridPane gp = new GridPane();
			HBox hb = new HBox();
			
			Image locationImg = new Image("iconLocation.png");
			Image peopleImg = new Image("iconPeople.png");
			Image dateImg = new Image("iconDate.png");
			
			ImageView locationIV = new ImageView(locationImg);
			ImageView peopleIV = new ImageView(peopleImg);
			ImageView dateIV = new ImageView(dateImg);
			
			Label namaSparringLbl = new Label();
			Label locationLbl = new Label();
			Label peopleLbl = new Label();
			Label tanggalLabel = new Label();
			
			//setData
			int minPemain = sparringObject.getMinPemain();
			int maxPemain = sparringObject.getMaxPemain();
			String people = "";
			if (minPemain == maxPemain) {
				people = minPemain + " pemain";
			}else {
				people = minPemain + "-" + maxPemain + " pemain";
			}
			
			Date date = sparringObject.getTanggalBooking();
			SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM yyyy");
			String formatDate = outputDateFormat.format(date);
			
			namaSparringLbl.setText("Sparring " + getNamaJenisLapangan(sparringObject.getIdJenisLapangan()) + " " + getNamaCustomer(sparringObject.getIdCustomer()));
			locationLbl.setText(getNamaGymnasium(sparringObject.getIdGymnasium()));
			peopleLbl.setText(people);
			tanggalLabel.setText(formatDate + " | " + getJadwal(sparringObject.getIdBooking()));
			
			//positioning
			gp.add(namaSparringLbl, 0, 0, 2, 1);
			gp.add(locationIV, 0, 1);
			gp.add(locationLbl, 1, 1);
			gp.add(peopleIV, 0, 2);
			gp.add(peopleLbl, 1, 2);
			gp.add(dateIV, 0, 3);
			gp.add(tanggalLabel, 1, 3);
						
			hb.getChildren().add(gp);
			
			//style
			hb.setPrefWidth(300);

			namaSparringLbl.setFont(font16Semi);
			locationLbl.setFont(font12);
			peopleLbl.setFont(font12);
			tanggalLabel.setFont(font12);
			
			gp.setPrefWidth(300);;
			gp.setHgap(4);
			
			//add to listView
			sparringListView.getItems().add(hb);
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
		gpContainer.add(sparringListView, 0, 1);
		
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
		
		turnamenBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		turnamenBtn.setPadding(new Insets(0));
		turnamenBtn.setMinSize(110, 40);
		turnamenBtn.setMaxSize(110, 40);
		turnamenBtn.setTextFill(Color.web("#A3A3A3"));
		turnamenBtn.setFont(font20Semi);
		
		sparringBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent transparent #FF7E46 transparent; -fx-border-width: 3;");
		sparringBtn.setPadding(new Insets(0));
		sparringBtn.setMinSize(110, 40);
		sparringBtn.setMaxSize(110, 40);
		sparringBtn.setTextFill(Color.web("#FF7E46"));
		sparringBtn.setFont(font20Semi);
		
		gpContainer.setPadding(new Insets(8, 24, 0, 24));
		gpContainer.setVgap(8);
		
		gpJenis.setVgap(12);
		gpJenis.setPadding(new Insets(0, 0, 12, 0));
		sparringListView.setMaxWidth(342);
		sparringListView.setPrefHeight(700);
		sparringListView.getStylesheets().add("style.css");
		sparringListView.getStyleClass().add("list-view-1");
		
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
		turnamenBtn.setOnMouseClicked(e -> {
			new Tanding(stage, idCustomer);
		});
		
		sparringListView.setOnMouseClicked(e -> {
			if (!sparringListView.getSelectionModel().isEmpty()) {		
				new DetailSparring(stage, dataSparring.get(sparringListView.getSelectionModel().getSelectedIndex()), idCustomer);
			}
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
	
	public TandingSparring(Stage stage, String idCust) {
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