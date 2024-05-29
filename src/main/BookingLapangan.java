package main;

import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
import javafx.stage.Stage;
import model.DetailBooking;
import model.Gymnasium;
import model.ShiftLapangan;
import util.Connect;

public class BookingLapangan extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer, gpShift;
	private Label judulLbl, namaLbl, tanggalLbl, shiftLbl, wrongDateLbl;
	private DatePicker tanggalDP;
	private ListView<model.DetailLapangan> lapanganListView;
	private ListView<ShiftLapangan> shiftLV;
	private ListView<String> detailBookLV;
	private Button sparringBtn, submitBtn, cancelBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB;
	private VBox btnVB, cancelVB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, textFont, btnFont;
	
	private Gymnasium gymnasium;
	private Connect connect = Connect.getInstance();
	private ObservableList<model.DetailLapangan> dataDetailLapangan;
	private ObservableList<ShiftLapangan> dataShiftLapangan;
	private ArrayList<DetailBooking> dataDetailBooking = new ArrayList<>();
	private ArrayList<String> dataShiftAdded = new ArrayList<>();
	
	private void getDetailLapangan() {
		dataDetailLapangan.clear();
		
		String query = String.format("SELECT * FROM detaillapangan WHERE idGymnasium = '%s'", gymnasium.getIdGymnasium());
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idLapangan = connect.rs.getString("idLapangan");
				String idGymnasium = connect.rs.getString("idGymnasium");
				String namaLapangan = connect.rs.getString("namaLapangan");
				int kapasitas = connect.rs.getInt("kapasitas");
				
				dataDetailLapangan.add(new model.DetailLapangan(idLapangan, idGymnasium, namaLapangan, kapasitas));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getShift(Time jamBuka, Time jamTutup, String idLapangan, LocalDate tanggalBooking) {
		dataShiftLapangan.clear();
		
		String query = "SELECT * FROM `shiftlapangan` \r\n" + 
				"WHERE shiftlapangan.jamMulai >= '" + jamBuka + "'\r\n" + 
				"AND shiftlapangan.jamSelesai <= '" + jamTutup + "'\r\n" + 
				"AND NOT EXISTS(\r\n" + 
				"	SELECT detailbooking.idShiftLapangan FROM detailbooking, bookinglapangan \r\n" + 
				"    WHERE detailbooking.idShiftLapangan = shiftlapangan.idShiftLapangan\r\n" + 
				"    AND detailbooking.idBooking = bookinglapangan.idBooking\r\n" + 
				"    AND detailbooking.idLapangan = '" + idLapangan + "'\r\n" + 
				"    AND bookinglapangan.statusBooking != 'Failed'\r\n" +
				"    AND bookinglapangan.statusBooking != 'Cancelled'\r\n" +
				"    AND bookinglapangan.tanggalBooking = '" + tanggalBooking + "'" + 
				")";

		if (tanggalBooking.equals(LocalDate.now())) {
			query += " AND shiftlapangan.jamMulai >= CURRENT_TIMESTAMP";
		}
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idShiftLapangan = connect.rs.getString("idShiftLapangan");
				Time jamMulai = connect.rs.getTime("jamMulai");
				Time jamSelesai = connect.rs.getTime("jamSelesai");
				Time durasi = connect.rs.getTime("durasi");
				
				dataShiftLapangan.add(new ShiftLapangan(idShiftLapangan, jamMulai, jamSelesai, durasi));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		dataDetailLapangan = FXCollections.observableArrayList();
		dataShiftLapangan = FXCollections.observableArrayList();
		
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		gpShift = new GridPane();
		
		judulLbl = new Label("Booking Lapangan");
		namaLbl = new Label();
		tanggalLbl = new Label("Tanggal");
		shiftLbl = new Label("Shift");
		wrongDateLbl = new Label();
		
		tanggalDP = new DatePicker();
		
		lapanganListView = new ListView<>();
		shiftLV = new ListView<>();
		detailBookLV = new ListView<>();
		
		sparringBtn = new Button("Open Sparring");
		submitBtn = new Button("Submit");
		cancelBtn = new Button("Remove");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		backHB = new HBox();
		btnVB = new VBox();
		cancelVB = new VBox();
		
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
		btnFont = Font.font("Poppins", FontWeight.BOLD, 18);
		textFont = Font.font("Poppins", 15);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void setLapanganData() {
		namaLbl.setText(gymnasium.getNamaGymnasium());
		
		lapanganListView.setItems(dataDetailLapangan);
	}
	
	private void setShiftData(LocalDate date, String idLapangan) {
		getShift(gymnasium.getJamBuka(), gymnasium.getJamTutup(), idLapangan, date);
		
		shiftLV.setItems(dataShiftLapangan);
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		cancelVB.getChildren().add(cancelBtn);
		
		gpShift.add(lapanganListView, 0, 0);
		gpShift.add(shiftLV, 1, 0);
		gpShift.add(detailBookLV, 0, 1, 2, 1);
		gpShift.add(cancelVB, 0, 2, 2, 1);
		
		btnVB.getChildren().addAll(sparringBtn, submitBtn);
		
		gpContainer.add(namaLbl, 0, 0);
		gpContainer.add(tanggalLbl, 0, 1);
		gpContainer.add(tanggalDP, 0, 2);
		gpContainer.add(wrongDateLbl, 0, 3);
		gpContainer.add(shiftLbl, 0, 4);
		gpContainer.add(gpShift, 0, 5);
		gpContainer.add(btnVB, 0, 6);
		
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
		gpContainer.setVgap(4);
		
		namaLbl.setFont(namaFont);
		
		tanggalLbl.setFont(textFont);
		
		tanggalDP.setPrefSize(350, 40);
		
		gpContainer.setMargin(wrongDateLbl, new Insets(0, 0, 4, 0));
		wrongDateLbl.setFont(textFont);
		wrongDateLbl.setTextFill(Color.RED);
		
		shiftLbl.setFont(textFont);
		lapanganListView.setDisable(true);
		lapanganListView.setMinHeight(150);
		lapanganListView.setMaxHeight(150);
		
		shiftLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		shiftLV.setMinHeight(150);
		shiftLV.setMaxHeight(150);
		
		gpShift.setPadding(new Insets(0, 0, 80, 0));
		
		cancelVB.setAlignment(Pos.CENTER);
		cancelVB.setMargin(cancelBtn, new Insets(12, 0, 0, 0));
		cancelBtn.setPrefSize(120, 35);
		cancelBtn.setStyle("-fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10; -fx-border-width: 2;");
		cancelBtn.setTextFill(Color.RED);
		cancelBtn.setFont(btnFont);
		cancelBtn.setDisable(true);
				
		sparringBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		sparringBtn.setPrefSize(160, 40);
		sparringBtn.setTextFill(Color.WHITE);
		sparringBtn.setFont(btnFont);
		sparringBtn.setDisable(true);
		
		submitBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		submitBtn.setPrefSize(160, 40);
		submitBtn.setTextFill(Color.WHITE);
		submitBtn.setFont(btnFont);
		submitBtn.setDisable(true);
		
		btnVB.setAlignment(Pos.BOTTOM_CENTER);
		btnVB.setMargin(sparringBtn, new Insets(0, 0, 8, 0));
				
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
			new DetailLapangan(stage, gymnasium);
		});
		
		tanggalDP.setOnAction(e -> {
			LocalDate today = LocalDate.now();
			if (tanggalDP.getValue().isBefore(today)) {
				wrongDateLbl.setText("Wrong date!");
				lapanganListView.setDisable(true);
			}else {
				lapanganListView.setDisable(false);
				wrongDateLbl.setText("");
			}
		});
		
		tanggalDP.setOnMouseClicked(e -> {
			shiftLV.getItems().clear();
			cancelBtn.setDisable(true);
			sparringBtn.setDisable(true);
			submitBtn.setDisable(true);
			dataDetailBooking.clear();
			detailBookLV.getItems().clear();
			dataShiftAdded.clear();
		});
		
		lapanganListView.setOnMouseClicked(e -> {
			shiftLV.getItems().clear();
			
			if (!lapanganListView.getSelectionModel().isEmpty()) {
				setShiftData(tanggalDP.getValue(), lapanganListView.getSelectionModel().getSelectedItem().getIdLapangan());				
			}
		});
		
		shiftLV.setOnMouseClicked(e -> {
			if (!shiftLV.getSelectionModel().isEmpty()) {
				String idLapangan = lapanganListView.getSelectionModel().getSelectedItem().getIdLapangan();
				String idShiftLapangan = shiftLV.getSelectionModel().getSelectedItem().getIdShiftLapangan();
				String strDetail = lapanganListView.getSelectionModel().getSelectedItem().getNamaLapangan() + " : "
						+ shiftLV.getSelectionModel().getSelectedItem().getJamMulai() + " - "
						+ shiftLV.getSelectionModel().getSelectedItem().getJamSelesai();
				
				
				dataDetailBooking.add(new DetailBooking(null, idLapangan, idShiftLapangan));
				detailBookLV.getItems().add(strDetail);
				dataShiftAdded.add(idShiftLapangan);
				
				dataShiftLapangan.remove(shiftLV.getSelectionModel().getSelectedIndex());
				
				sparringBtn.setDisable(false);
				sparringBtn.setOnMouseClicked(event -> {
					new OpenSparring(stage, gymnasium, tanggalDP.getValue(), dataDetailBooking);
				});
				submitBtn.setDisable(false);
				submitBtn.setOnMouseClicked(event -> {
					new MetodePembayaran(stage, gymnasium, tanggalDP.getValue(), dataDetailBooking, false);
				});
			}
		});
		
		detailBookLV.setOnMouseClicked(e -> {
			if (!detailBookLV.getSelectionModel().isEmpty()) {
				cancelBtn.setDisable(false);
				cancelBtn.setOnMouseClicked(event -> {
					dataDetailBooking.remove(detailBookLV.getSelectionModel().getSelectedIndex());
					detailBookLV.getItems().remove(detailBookLV.getSelectionModel().getSelectedIndex());
					
					if (dataDetailBooking.isEmpty()) {
						cancelBtn.setDisable(true);
						submitBtn.setDisable(true);
						sparringBtn.setDisable(true);
					}
				});
			}
		});
	}
	
	public BookingLapangan(Stage stage, Gymnasium inputGymnasium) {
		gymnasium = inputGymnasium;
				
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage detailLapanganStage) throws Exception {
		initialize();
		
		getDetailLapangan();
		setLapanganData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
