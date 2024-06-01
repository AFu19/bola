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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Gymnasium;
import model.Peralatan;
import util.Connect;

public class EquipmentList extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer;
	private Label judulLbl, namaLbl, sorryLbl, sorryLbl2;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, font20, font12;
	private ListView<HBox> equipmentListView;

	private Connect connect = Connect.getInstance();
	private String idBooking, idGymnasium;
	private Gymnasium gymnasium;
	private ArrayList<Peralatan> dataEquipment = new ArrayList<>();
	
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
	
	private void getData() {
		dataEquipment.clear();
		
		String query = String.format("SELECT * FROM peralatan p WHERE p.idGymnasium = '%s' AND NOT EXISTS (SELECT * FROM detailsewaperalatan dsp WHERE p.idPeralatan = dsp.idPeralatan AND dsp.idBooking = '%s')", idGymnasium, idBooking);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idPeralatan = connect.rs.getString("idPeralatan");
				String idGymnasium = connect.rs.getString("idGymnasium");
				String namaPeralatan = connect.rs.getString("namaPeralatan");
				int hargaPeralatan = connect.rs.getInt("hargaPeralatan");
				int stockPeralatan = connect.rs.getInt("stockPeralatan");
				Blob fotoPeralatan = connect.rs.getBlob("fotoPeralatan");
				
				dataEquipment.add(new Peralatan(idPeralatan, idGymnasium, namaPeralatan, hargaPeralatan, stockPeralatan, fotoPeralatan));		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		
		judulLbl = new Label("Equipments");
		namaLbl = new Label();
		sorryLbl = new Label("Maaf gymnasium ini tidak");
		sorryLbl2 = new Label("menyewakan peralatan </3");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		backHB = new HBox();
		
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
		
		equipmentListView = new ListView<>();
		
		judulFont = Font.font("Poppins", FontWeight.BOLD, 30);
		namaFont = Font.font("Poppins", FontWeight.BOLD, 20);
		font20 = Font.font("Poppins", 20);
		font12 = Font.font("Poppins", 12);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void setData() {
		namaLbl.setText(gymnasium.getNamaGymnasium());
		
		getData();
			
		if (!dataEquipment.isEmpty()) {
			for (Peralatan peralatan : dataEquipment) {
				FlowPane fp = new FlowPane();
				GridPane gp = new GridPane();
				HBox hb = new HBox();
				
				InputStream inputStream;
				Image image;
				ImageView imageView = new ImageView();
				
				Label namaEquipment = new Label(), hargaLabel = new Label();
				
				namaEquipment.setFont(font20);
				hargaLabel.setFont(font12);
				
				try {
					inputStream = peralatan.getFotoPeralatan().getBinaryStream();
					image = new Image(inputStream, 80, 80, false, false);
					imageView.setImage(image);
					
					namaEquipment.setText(peralatan.getNamaPeralatan());
					hargaLabel.setText(String.format("Rp%d/jam", peralatan.getHargaPeralatan()));
					
					//positioning
					gp.add(namaEquipment, 0, 0);
					gp.add(hargaLabel, 0, 1);
					
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
					equipmentListView.getItems().add(hb);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		gpContainer.add(namaLbl, 0, 0);
		if (dataEquipment.isEmpty()) {
			gpContainer.add(sorryLbl, 0, 1);
			gpContainer.add(sorryLbl2, 0, 2);
		}else {
			gpContainer.add(equipmentListView, 0, 1);			
		}
		
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
		
		equipmentListView.setMinWidth(342);
		equipmentListView.setMaxWidth(342);
		equipmentListView.setPrefHeight(800);
		equipmentListView.getStylesheets().add("style.css");
		equipmentListView.getStyleClass().add("list-view-1");
		
		sorryLbl.setFont(font20);
		sorryLbl.setTextFill(Color.RED);
		sorryLbl2.setFont(font20);
		sorryLbl2.setTextFill(Color.RED);
		
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
			new DetailHistoryConfirmed(stage, idBooking, idGymnasium);
		});
		equipmentListView.setOnMouseClicked(e -> {
			if (!equipmentListView.getSelectionModel().isEmpty()) {
				String idPeralatan = dataEquipment.get(equipmentListView.getSelectionModel().getSelectedIndex()).getIdPeralatan();
				new DetailEquipment(stage, idBooking, idGymnasium, idPeralatan);
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
	
	public EquipmentList(Stage stage, String inputIdBooking, String inputIdGymnasium) {
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
		
		initialize();
		setData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
