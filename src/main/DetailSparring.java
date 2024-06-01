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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Gymnasium;
import model.Peralatan;
import model.SparringObject;
import util.Connect;

public class DetailSparring extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader, fpJlhPemain;
	private GridPane gpContainer;
	private Label judulLbl, namaLbl, namaGymnasiumLbl, jadwalLbl, minPemainLbl, maxPemainLbl, jumlahPemainLbl, skLbl, lbl1, lbl2, errorLbl, sk1Lbl, sk2Lbl, sk3Lbl, sk4Lbl;
	private Text alamatText;
	private CheckBox cbSetuju;
	private TextField jlhPemainTF;
	private Button gabungBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, gabungHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg, locationImg, dateImg, peopleImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView, locationIV, dateIV, peopleIV;
	private Font judulFont, namaFont, font18, font14, font12;

	private Connect connect = Connect.getInstance();
	private SparringObject sparringObject;
	private String idCustomer;
	
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
	
	private String getAlamatGymnasium(String idGymnasium) {
		String query = String.format("SELECT alamatGymnasium from gymnasium WHERE idGymnasium = '%s'", idGymnasium);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				return connect.rs.getString("alamatGymnasium");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
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
	
	private String getTelfonCustomer(String inputIdCustomer) {
		String query = String.format("SELECT customerPhone FROM customer WHERE idCustomer = '%s'", inputIdCustomer);
		connect.rs = connect.execQuery(query);

		try {
			while (connect.rs.next()) {
				return connect.rs.getString("customerPhone");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void insertSparring() {
		String query = String.format("INSERT INTO detailsparring VALUES('%s', '%s', '%s')", sparringObject.getIdSparring(), idCustomer, jlhPemainTF.getText().toString());
		connect.execUpdate(query);
	}
	
//	private void updateSparring() {
//		String query = String.format("UPDATE sparring SET ", sparringObject.getIdSparring(), idCustomer, jlhPemainTF.getText().toString());
//		connect.execUpdate(query);
//	}
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		fpJlhPemain = new FlowPane();
		gpContainer = new GridPane();
		
		judulLbl = new Label("Detail Sparring");
		namaLbl = new Label();
		namaGymnasiumLbl = new Label();
		jadwalLbl = new Label();
		minPemainLbl = new Label();
		maxPemainLbl = new Label();
		jumlahPemainLbl = new Label("Jumlah Pemain Anda:");
		skLbl = new Label("Syarat & Ketentuan");
		lbl1 = new Label("1.");
		lbl2 = new Label("2.");
		errorLbl = new Label();
		
		alamatText = new Text();
		sk1Lbl = new Label("Informasi kontak Anda akan dibagikan kepada");
		sk2Lbl = new Label("pembuat kegiatan Sparring yang Anda pilih.");
		sk3Lbl = new Label("Pembagian biaya sparring akan dilakukan secara");
		sk4Lbl = new Label("mandiri sesuai dengan kesepakatan pengguna.");
		
		cbSetuju = new CheckBox("Saya menyetujui syarat & ketentuan yang berlaku.");
		
		jlhPemainTF = new TextField();
		
		gabungBtn = new Button("Gabung");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		backHB = new HBox();
		gabungHB = new HBox();
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("InProfile.png");
		backImg = new Image("back.png");
		locationImg = new Image("iconLocation.png");
		dateImg = new Image("iconLocation.png");
		peopleImg = new Image("iconPeople.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		backImgView = new ImageView(backImg);
		locationIV = new ImageView(locationImg);
		dateIV = new ImageView(dateImg);
		peopleIV = new ImageView(peopleImg);
		
		judulFont = Font.font("Poppins", FontWeight.BOLD, 30);
		namaFont = Font.font("Poppins", FontWeight.BOLD, 20);
		font18 = Font.font("Poppins", 18);
		font14 = Font.font("Poppins", 14);
		font12 = Font.font("Poppins", 12);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void setData() {
		Date date = sparringObject.getTanggalBooking();
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM yyyy");
		String formatDate = outputDateFormat.format(date);
		
		namaLbl.setText("Sparring " + getNamaJenisLapangan(sparringObject.getIdJenisLapangan()) + " " + getNamaCustomer(sparringObject.getIdCustomer()));
		namaGymnasiumLbl.setText(getNamaGymnasium(sparringObject.getIdGymnasium()));
		alamatText.setText(getAlamatGymnasium(sparringObject.getIdGymnasium()));
		jadwalLbl.setText(formatDate + " | " + getJadwal(sparringObject.getIdBooking()));
		minPemainLbl.setText("Jumlah Min. Pemain: " + sparringObject.getMinPemain());
		maxPemainLbl.setText("Jumlah Max. Pemain: " + sparringObject.getMaxPemain());
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		fpJlhPemain.getChildren().addAll(jumlahPemainLbl, jlhPemainTF);
		
		gabungHB.getChildren().add(gabungBtn);
		
		gpContainer.add(namaLbl, 0, 0, 2, 1);
		gpContainer.add(locationIV, 0, 1);
		gpContainer.add(namaGymnasiumLbl, 1, 1);
		gpContainer.add(alamatText, 1, 2);
		gpContainer.add(dateIV, 0, 3);
		gpContainer.add(jadwalLbl, 1, 3);
		gpContainer.add(peopleIV, 0, 4);
		gpContainer.add(minPemainLbl, 1, 4);
		gpContainer.add(maxPemainLbl, 1, 5);
		gpContainer.add(fpJlhPemain, 0, 6, 2, 1);
		gpContainer.add(skLbl, 0, 7, 2, 1);
		gpContainer.add(lbl1, 0, 8);
		gpContainer.add(sk1Lbl, 1, 8);
		gpContainer.add(sk2Lbl, 1, 9);
		gpContainer.add(lbl2, 0, 10);
		gpContainer.add(sk3Lbl, 1, 10);
		gpContainer.add(sk4Lbl, 1, 11);
		gpContainer.add(cbSetuju, 0, 12, 2, 1);
		gpContainer.add(errorLbl, 0, 13, 2, 1);
		gpContainer.add(gabungHB, 0, 14, 2, 1);
		
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

		gpContainer.setHgap(4);
		fpJlhPemain.setHgap(4);
		gpContainer.setMargin(namaLbl, new Insets(0, 0, 12, 0));
		gpContainer.setMargin(alamatText, new Insets(0, 0, 4, 0));
		gpContainer.setMargin(jadwalLbl, new Insets(0, 0, 4, 0));
		gpContainer.setMargin(maxPemainLbl, new Insets(0, 0, 12, 0));
		gpContainer.setMargin(fpJlhPemain, new Insets(0, 0, 12, 0));
		gpContainer.setMargin(sk4Lbl, new Insets(0, 0, 12, 0));
		
		namaGymnasiumLbl.setFont(font14);
		alamatText.setFont(font14);
		jadwalLbl.setFont(font14);
		minPemainLbl.setFont(font14);
		maxPemainLbl.setFont(font14);
		jumlahPemainLbl.setFont(font14);
		skLbl.setFont(font18);
		lbl1.setFont(font14);
		lbl2.setFont(font14);
		sk1Lbl.setFont(font14);
		sk2Lbl.setFont(font14);
		sk3Lbl.setFont(font14);
		sk4Lbl.setFont(font14);
		cbSetuju.setFont(font14);
		
		alamatText.setWrappingWidth(315);
		
		jlhPemainTF.setPrefSize(35, 22);
		
		gpContainer.setPadding(new Insets(24));
		gpContainer.setMargin(namaLbl, new Insets(0, 0, 12, 0));
		
		errorLbl.setFont(font14);
		errorLbl.setTextFill(Color.RED);
		
		gabungHB.setAlignment(Pos.BOTTOM_CENTER);
		gabungHB.setPrefHeight(240);
		
		gabungBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		gabungBtn.setPrefSize(160, 48);
		gabungBtn.setTextFill(Color.WHITE);
		gabungBtn.setFont(namaFont);
		
		namaLbl.setFont(namaFont);
				
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
			new TandingSparring(stage, idCustomer);
		});
		
		gabungBtn.setOnMouseClicked(e -> {
			int inputTF = 0;
			
			try {
				inputTF = Integer.parseInt(jlhPemainTF.getText());
			} catch (Exception e2) {
				errorLbl.setText("Jumlah pemain tidak sesuai!");
			}
			
			if (jlhPemainTF.getText().isEmpty()) {
				errorLbl.setText("Masukkan jumlah pemain!");
			}else if (inputTF < sparringObject.getMinPemain() || inputTF > sparringObject.getMaxPemain()) {
				errorLbl.setText("Jumlah pemain tidak sesuai!");
			}else if (!cbSetuju.isSelected()) {
				errorLbl.setText("Setujui syarat & ketentuan!");
			}else {
				errorLbl.setText("");
				
				Stage popupStage;
				Scene popupScene;
				BorderPane popupPane;
				GridPane labelPane;
				Image tickImg;
				ImageView tickIV;
				Text notifText;
				Label notifLabel;
				HBox buttonBox;
				Button nextButton;
				
				//initialize
				popupStage = new Stage();
				popupPane = new BorderPane();
				labelPane = new GridPane();
				tickImg = new Image("tick.png");
				tickIV = new ImageView(tickImg);
				notifText = new Text("Selamat! Anda telah tergabung dalam kegiatan Sparring ini. Hubungi pembuat kegiatan Sparring di bawah untuk informasi lebih lanjut:");
				notifLabel = new Label(getNamaCustomer(sparringObject.getIdCustomer()) + " - " + getTelfonCustomer(sparringObject.getIdCustomer()));
				buttonBox = new HBox();
				nextButton = new Button("Kembali");
				popupScene = new Scene(popupPane, 294, 242);
				
				//positioning
				labelPane.add(notifText, 0, 0);
				labelPane.add(notifLabel, 0, 1);
				
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
				labelPane.setPadding(new Insets(10, 0, 8, 0));
				
				notifText.setFont(font12);
				notifText.setWrappingWidth(253);
				notifText.setTextAlignment(TextAlignment.CENTER);
				
				notifLabel.setFont(font12);
				notifLabel.setMinWidth(253);
				notifLabel.setAlignment(Pos.CENTER);
				
				nextButton.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 10px;");
				nextButton.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
				nextButton.setMinSize(130, 48);
				nextButton.setTextFill(Color.WHITE);
				
				buttonBox.setPadding(new Insets(0, 0, 24, 0));
				buttonBox.setAlignment(Pos.TOP_CENTER);

				//handler
				popupStage.setOnHidden(event -> {
					new TandingSparring(stage, idCustomer);
				});
				nextButton.setOnMouseClicked(event -> {
					popupStage.hide();
					new TandingSparring(stage, idCustomer);
				});
				
				//main
				popupStage.setScene(popupScene);
				popupStage.showAndWait();
				
				insertSparring();
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
	
	public DetailSparring(Stage stage, SparringObject sparringObject, String idCust) {
		this.sparringObject = sparringObject;
		this.idCustomer = idCust;
		
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage detailLapanganStage) throws Exception {
		initialize();
		setData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
