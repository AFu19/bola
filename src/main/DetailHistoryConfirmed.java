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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DetailSewaPeralatan;
import model.Gymnasium;
import util.Connect;

public class DetailHistoryConfirmed extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer, gpPeralatan;
	private Label judulLbl, namaLbl, alamatLbl, tanggalLbl, tanggallLbl, jadwalLbl, jadwallLbl, equipmentLbl;
	private TextArea alamatTA;
	private ImageView fotoIV;
	private Button equipmentBtn, cancelBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, btnHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, deskripsiBold, deskripsiFont;
	private ListView<String> equipmentListView;
	
	private String idBooking, idGymnasium, jamMulaiBook, jamSelesaiBook;;
	private Date tglBook;
	private Gymnasium gymnasium;
	private Connect connect = Connect.getInstance();
	private ArrayList<DetailSewaPeralatan> dataSewaPeralatan = new ArrayList<>();
	
	private void getGymnasium() {
		String query = String.format("SELECT * FROM gymnasium where idGymnasium = '%s'", idGymnasium);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idGymnasium = connect.rs.getString("idGymnasium");
				String idJenisLapangan = connect.rs.getString("idJenisLapangan");
				String idPemilikGymnasium = connect.rs.getString("idPemilikGymnasium");
				String namaGymnasium = connect.rs.getString("namaGymnasium");
				Blob fotoGymnasium = connect.rs.getBlob("fotoGymnasium");
				String alamatGymnasium = connect.rs.getString("alamatGymnasium");
				String deskripsiFasilitas = connect.rs.getString("deskripsiFasilitas");
				int hargaGymnasium = connect.rs.getInt("hargaGymnasium");
				Time jamBuka = connect.rs.getTime("jamBuka");
				Time jamTutup = connect.rs.getTime("jamTutup");
				int jumlahLapangan = connect.rs.getInt("jumlahLapangan");
				String statusGymnasium = connect.rs.getString("statusGymnasium");
				
				gymnasium = new Gymnasium(idGymnasium, idJenisLapangan, idPemilikGymnasium, namaGymnasium, fotoGymnasium, alamatGymnasium, deskripsiFasilitas, hargaGymnasium, jamBuka, jamTutup, jumlahLapangan, statusGymnasium);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getAdditionalData() {
		String query = String.format("SELECT bl.tanggalBooking, MIN(sl.jamMulai) as jamMulai, MAX(sl.jamSelesai) as jamSelesai FROM bookinglapangan bl, detailbooking db, shiftlapangan sl WHERE bl.idBooking = db.idBooking AND db.idShiftLapangan = sl.idShiftLapangan AND bl.idBooking = '%s'", idBooking);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				tglBook = connect.rs.getDate("tanggalBooking");
				jamMulaiBook = connect.rs.getString("jamMulai");
				jamSelesaiBook = connect.rs.getString("jamSelesai");
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void cancelBooking() {
		String query = String.format("UPDATE bookinglapangan SET statusBooking = 'Cancelled' WHERE idBooking = '%s'", idBooking);
		connect.execUpdate(query);
	}
	
	private void getSewaPeralatan() {
		String query = String.format("SELECT * FROM detailsewaperalatan WHERE idBooking = '%s'", idBooking);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idBooking = connect.rs.getString("idBooking");
				String idPeralatan = connect.rs.getString("idPeralatan");
				int jumlah = connect.rs.getInt("jumlah");
				Time durasi = connect.rs.getTime("durasi");
				String metodePembayaran = connect.rs.getString("metodePembayaran");
				
				dataSewaPeralatan.add(new DetailSewaPeralatan(idBooking, idPeralatan, jumlah, durasi, metodePembayaran));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getNamaPeralatan(String idPeralatan) {
		String query = String.format("SELECT namaPeralatan FROM peralatan WHERE idPeralatan = '%s'", idPeralatan);
		connect.rs = connect.execQuery(query);
		
		try {
			if (connect.rs.next()) {
				return connect.rs.getString("namaPeralatan");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		gpPeralatan = new GridPane();
		
		judulLbl = new Label("Confirmed");
		namaLbl = new Label();
		alamatLbl = new Label("Alamat");
		tanggalLbl = new Label("Tanggal");
		tanggallLbl = new Label();
		jadwalLbl = new Label("Jadwal");
		jadwallLbl = new Label();
		equipmentLbl = new Label();
		
		fotoIV = new ImageView();
		
		alamatTA = new TextArea();
		
		equipmentBtn = new Button("Equipments");
		cancelBtn = new Button("Cancel");
		
		equipmentListView = new ListView<>();
		
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
		namaLbl.setText(gymnasium.getNamaGymnasium());
		
		InputStream inputStream;
		Image image;
		
		try {
			inputStream = gymnasium.getFotoGymnasium().getBinaryStream();
			image = new Image(inputStream, 342, 160, false, false);
			fotoIV.setImage(image);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		alamatTA.setText(gymnasium.getAlamatGymnasium());
		
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM yyyy");
		String formatDate = outputDateFormat.format(tglBook);
		tanggallLbl.setText(formatDate);
		
		jadwallLbl.setText(jamMulaiBook + " - " + jamSelesaiBook);
		
		if (!dataSewaPeralatan.isEmpty()) {
			equipmentLbl.setText("Equipment");
			for (DetailSewaPeralatan detailSewaPeralatan : dataSewaPeralatan) {
				String namaPeralatan = getNamaPeralatan(detailSewaPeralatan.getIdPeralatan());
				String jumlahStr = detailSewaPeralatan.getJumlah() + " Pc(s)";
				String durasiStr = detailSewaPeralatan.getDurasi().toString();
						
				if (durasiStr.charAt(0) == '0') {
					durasiStr = durasiStr.substring(1, 2) + " Jam";
				}else {
					durasiStr = durasiStr.substring(0, 2) + " Jam";
				}
					
				
				equipmentListView.getItems().add(String.format("%s - %s - %s", namaPeralatan, jumlahStr, durasiStr));
			}
		}
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		if (!dataSewaPeralatan.isEmpty()) {
			gpPeralatan.add(equipmentLbl, 0, 0);
			gpPeralatan.add(equipmentListView, 0, 1);			
		}
		
		btnHB.getChildren().addAll(equipmentBtn, cancelBtn);
		
		gpContainer.add(namaLbl, 0, 0);
		gpContainer.add(fotoIV, 0, 1);
		gpContainer.add(alamatLbl, 0, 2);
		gpContainer.add(alamatTA, 0, 3);
		gpContainer.add(tanggalLbl, 0, 4);
		gpContainer.add(tanggallLbl, 0, 5);
		gpContainer.add(jadwalLbl, 0, 6);
		gpContainer.add(jadwallLbl, 0, 7);
		gpContainer.add(gpPeralatan, 0, 8);
		gpContainer.add(btnHB, 0, 9);
		
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
		gpContainer.setMargin(alamatTA, new Insets(0, 0, 8, 0));
		gpContainer.setMargin(tanggallLbl, new Insets(0, 0, 8, 0));
		
		namaLbl.setFont(namaFont);
		
		fotoIV.setStyle("-fx-background-radius: 12px;");
		
		alamatTA.setWrapText(true);
		alamatTA.setEditable(false);
		alamatTA.setMinHeight(70);
		alamatTA.setMaxHeight(70);
		alamatTA.setStyle("-fx-text-fill: black;");
		
		gpPeralatan.setPadding(new Insets(8, 0, 0, 0));
		gpPeralatan.setMinHeight(160);
		gpPeralatan.setMaxHeight(160);
		
		equipmentListView.setMinSize(346, 108);
		equipmentListView.setMaxSize(346, 108);
		
		alamatLbl.setFont(deskripsiBold);
		alamatLbl.setTextFill(Color.web("#458E5E"));
		tanggalLbl.setFont(deskripsiBold);
		tanggalLbl.setTextFill(Color.web("#458E5E"));
		jadwalLbl.setFont(deskripsiBold);
		jadwalLbl.setTextFill(Color.web("#458E5E"));
		equipmentLbl.setFont(deskripsiBold);
		equipmentLbl.setTextFill(Color.web("#458E5E"));
		tanggallLbl.setFont(deskripsiFont);
		jadwallLbl.setFont(deskripsiFont);
		
		equipmentBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		equipmentBtn.setPrefSize(160, 48);
		equipmentBtn.setTextFill(Color.WHITE);
		equipmentBtn.setFont(namaFont);
		
		cancelBtn.setStyle("-fx-background-color: #F93A3A; -fx-background-radius: 8px;");
		cancelBtn.setPrefSize(160, 48);
		cancelBtn.setTextFill(Color.WHITE);
		cancelBtn.setFont(namaFont);
		
		btnHB.setAlignment(Pos.BOTTOM_CENTER);
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
			new History(stage, Home.idCustomer);
		});
		
		equipmentBtn.setOnMouseClicked(e -> {
			new  EquipmentList(stage, idBooking, idGymnasium);
		});
		
		cancelBtn.setOnMouseClicked(e -> {
			Stage popupStage;
			Scene popupScene;
			BorderPane popupPane;
			GridPane labelPane;
			Label notifLabel1, notifLabel2;
			VBox buttonBox;
			Button tidakButton, yaButton;
			
			//initialize
			popupStage = new Stage();
			popupPane = new BorderPane();
			labelPane = new GridPane();
			notifLabel1 = new Label("Batalkan");
			notifLabel2 = new Label("Booking?");
			buttonBox = new VBox();
			tidakButton = new Button("Tidak");
			yaButton = new Button("Ya, batalkan");
			popupScene = new Scene(popupPane, 222, 254);
			
			//positioning
			labelPane.add(notifLabel1, 0, 0);
			labelPane.add(notifLabel2, 0, 1);
			
			buttonBox.getChildren().addAll(tidakButton, yaButton);

			popupPane.setCenter(labelPane);
			popupPane.setBottom(buttonBox);
			
			//style
			popupStage.initModality(Modality.APPLICATION_MODAL);
			popupStage.setTitle("Notification");
			popupStage.setResizable(false);
			popupStage.setMaximized(false);
			
			popupPane.setPadding(new Insets(8, 0, 0, 0));
			popupPane.setPrefSize(166, 206);
			
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
			
			tidakButton.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-border-color: #F93A3A; -fx-border-radius: 10px; -fx-border-width: 2;");
			tidakButton.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
			tidakButton.setMinSize(160, 48);
			tidakButton.setTextFill(Color.web("#F93A3A"));
			
			yaButton.setStyle("-fx-background-color: #F93A3A; -fx-background-radius: 10px; ");
			yaButton.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
			yaButton.setMinSize(160, 48);
			yaButton.setTextFill(Color.WHITE);
			
			buttonBox.setPadding(new Insets(0, 0, 24, 0));
			buttonBox.setSpacing(12);
			buttonBox.setAlignment(Pos.TOP_CENTER);

			//handler
			tidakButton.setOnMouseClicked(event -> {
				popupStage.hide();
			});
			yaButton.setOnMouseClicked(event -> {
				popupStage.hide();
				cancelBooking();
				new History(stage, Home.idCustomer);
				
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
	
	public DetailHistoryConfirmed(Stage stage, String inputIdBooking, String inputIdGymnasium) {
		idBooking = inputIdBooking;
		idGymnasium = inputIdGymnasium;
		
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage detailLapanganStage) throws Exception {
		getGymnasium();
		getAdditionalData();
		getSewaPeralatan();
		
		initialize();
		setData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
