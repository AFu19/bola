package main;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Gymnasium;
import model.Peralatan;
import util.Connect;

public class DetailEquipment extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer, gpInput;
	private Label judulLbl, namaLbl, hargaLbl, hargaaLbl, jumlahLbl, lamaLbl;
	private ImageView fotoIV;
	private Button rentBtn;
	private Spinner<Integer> jlhSpinner, lamaSpinner;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, btnHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, deskripsiBold, deskripsiFont;

	private String idBooking, idGymnasium, idPeralatan;
	private Peralatan peralatan;
	private Connect connect = Connect.getInstance();
	
	private void getData() {
		String query = String.format("SELECT * FROM peralatan WHERE idPeralatan = '%s'", idPeralatan);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idPeralatan = connect.rs.getString("idPeralatan");
				String idGymnasium = connect.rs.getString("idGymnasium");
				String namaPeralatan = connect.rs.getString("namaPeralatan");
				int hargaPeralatan = connect.rs.getInt("hargaPeralatan");
				int stockPeralatan = connect.rs.getInt("stockPeralatan");
				Blob fotoPeralatan = connect.rs.getBlob("fotoPeralatan");
				
				peralatan = new Peralatan(idPeralatan, idGymnasium, namaPeralatan, hargaPeralatan, stockPeralatan, fotoPeralatan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int countDurasi() {
		String query = String.format("SELECT COUNT(DISTINCT idShiftLapangan) FROM detailbooking WHERE idBooking = '%s'", idBooking);
		connect.rs = connect.execQuery(query);
		try {
			while (connect.rs.next()) {
				return connect.rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		gpInput = new GridPane();
		
		judulLbl = new Label("Detail Equipment");
		namaLbl = new Label();
		hargaLbl = new Label("Harga");
		hargaaLbl = new Label();
		jumlahLbl = new Label("Jumlah");
		lamaLbl = new Label("Lama(jam)");
		
		fotoIV = new ImageView();
		
		jlhSpinner = new Spinner<>(1, peralatan.getStockPeralatan(), 1);
		lamaSpinner = new Spinner<>(1, countDurasi(), 1);
		
		rentBtn = new Button("Rent");
		
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
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		backImgView = new ImageView(backImg);
		
		judulFont = Font.font("Poppins", FontWeight.BOLD, 30);
		namaFont = Font.font("Poppins", FontWeight.BOLD, 20);
		deskripsiBold = Font.font("Poppins", FontWeight.BOLD, 12);
		deskripsiFont = Font.font("Poppins", 16);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void setData() {
		namaLbl.setText(peralatan.getNamaPeralatan());
		
		InputStream inputStream;
		Image image;
		
		try {
			inputStream = peralatan.getFotoPeralatan().getBinaryStream();
			image = new Image(inputStream, 342, 160, false, false);
			fotoIV.setImage(image);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		hargaaLbl.setText(String.format("Rp%d / jam", peralatan.getHargaPeralatan()));

	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		gpInput.add(jumlahLbl, 0, 0);
		gpInput.add(jlhSpinner, 0, 1);
		gpInput.add(lamaLbl, 1, 0);
		gpInput.add(lamaSpinner, 1, 1);		
		
		btnHB.getChildren().addAll(rentBtn);
		
		gpContainer.add(namaLbl, 0, 0);
		gpContainer.add(fotoIV, 0, 1);
		gpContainer.add(hargaLbl, 0, 2);
		gpContainer.add(hargaaLbl, 0, 3);
		gpContainer.add(gpInput, 0, 4);
		gpContainer.add(btnHB, 0, 5);
		
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
		gpContainer.setMargin(namaLbl, new Insets(0, 0, 12, 0));
		gpContainer.setMargin(fotoIV, new Insets(0, 0, 12, 0));
		gpContainer.setMargin(hargaaLbl, new Insets(0, 0, 12, 0));
		
		namaLbl.setFont(namaFont);
		
		fotoIV.setStyle("-fx-background-radius: 12px;");
		
		hargaLbl.setFont(deskripsiBold);
		hargaLbl.setTextFill(Color.web("#458E5E"));
		hargaaLbl.setFont(deskripsiFont);
		jumlahLbl.setFont(deskripsiBold);
		jumlahLbl.setTextFill(Color.web("#458E5E"));
		lamaLbl.setFont(deskripsiBold);
		lamaLbl.setTextFill(Color.web("#458E5E"));
		
		gpInput.setHgap(40);
		
		rentBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		rentBtn.setPrefSize(160, 48);
		rentBtn.setTextFill(Color.WHITE);
		rentBtn.setFont(namaFont);
		
		btnHB.setAlignment(Pos.BOTTOM_CENTER);
		btnHB.setPadding(new Insets(240, 0, 0, 0));
		btnHB.setSpacing(16);
				
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
			new EquipmentList(stage, idBooking, idGymnasium);
		});
		
		rentBtn.setOnMouseClicked(e -> {
			int jumlah = jlhSpinner.getValue();
			int durasi = lamaSpinner.getValue();
			new  MetodePembayaranPeralatan(stage, idBooking, idGymnasium, idPeralatan, jumlah, durasi);
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
	
	public DetailEquipment(Stage stage, String inputIdBooking, String inputIdGymnasium, String inputIdPeralatan) {
		idBooking = inputIdBooking;
		idGymnasium = inputIdGymnasium;
		idPeralatan = inputIdPeralatan;
		
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage detailLapanganStage) throws Exception {
		getData();
		
		initialize();
		setData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
