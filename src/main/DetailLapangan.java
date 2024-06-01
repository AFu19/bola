package main;

import java.io.InputStream;
import java.sql.SQLException;

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
import javafx.stage.Stage;
import model.Gymnasium;

public class DetailLapangan extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer, gpDeskripsi;
	private Label judulLbl, namaLbl, fasilitasLbl, hargaLbl, hargaaLbl, jamOperasionalLbl, jamOperasionallLbl;
	private TextArea alamatTA;
	private ImageView fotoIV;
	private Button bookBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, btnHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, deskripsiBold, deskripsiFont;
	
	private Gymnasium gymnasium;

	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		gpDeskripsi = new GridPane();
		
		judulLbl = new Label("Detail Lapangan");
		namaLbl = new Label();
		fasilitasLbl = new Label("Fasilitas");
		hargaLbl = new Label("Harga");
		hargaaLbl = new Label();
		jamOperasionalLbl = new Label("Jam Operasional");
		jamOperasionallLbl = new Label();
		
		fotoIV = new ImageView();
		
		alamatTA = new TextArea();
		
		bookBtn = new Button("Book");
		
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
		deskripsiBold = Font.font("Poppins", FontWeight.BOLD, 17);
		deskripsiFont = Font.font("Poppins", 15);
		
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
		
		int rowIndex = 1;
		String[] fasilitasStr = gymnasium.getDeskrpsiFasilitas().split(",");
		for (String string : fasilitasStr) {
			Label label = new Label("- " + string);
			gpDeskripsi.add(label, 0, rowIndex);
			label.setFont(deskripsiFont);
			rowIndex++;
		}
		
		hargaaLbl.setText("Rp" + gymnasium.getHargaGymnasium() + "/jam");
		jamOperasionallLbl.setText(gymnasium.getJamBuka() + " - " + gymnasium.getJamTutup());
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		gpDeskripsi.add(fasilitasLbl, 0, 0);
		gpDeskripsi.add(hargaLbl, 1, 0);
		gpDeskripsi.add(hargaaLbl, 1, 1);
		gpDeskripsi.add(jamOperasionalLbl, 1, 3);
		gpDeskripsi.add(jamOperasionallLbl, 1, 4);
		
		btnHB.getChildren().add(bookBtn);
		
		gpContainer.add(namaLbl, 0, 0);
		gpContainer.add(fotoIV, 0, 1);
		gpContainer.add(alamatTA, 0, 2);
		gpContainer.add(gpDeskripsi, 0, 3);
		gpContainer.add(btnHB, 0, 4);
		
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
		gpContainer.setVgap(12);
		
		namaLbl.setFont(namaFont);
		
		fotoIV.setStyle("-fx-background-radius: 12px;");
		
		alamatTA.setWrapText(true);
		alamatTA.setEditable(false);
		alamatTA.setMinHeight(100);
		alamatTA.setMaxHeight(100);
		alamatTA.setStyle("-fx-text-fill: black;");
		
		gpDeskripsi.setPrefHeight(400);
		
		fasilitasLbl.setPadding(new Insets(0, 120, 0, 0));
		fasilitasLbl.setFont(deskripsiBold);
		hargaLbl.setFont(deskripsiBold);
		jamOperasionalLbl.setFont(deskripsiBold);
		hargaaLbl.setFont(deskripsiFont);
		jamOperasionallLbl.setFont(deskripsiFont);
		
		bookBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		bookBtn.setPrefSize(160, 48);
		bookBtn.setTextFill(Color.WHITE);
		bookBtn.setFont(namaFont);
		
		btnHB.setAlignment(Pos.BOTTOM_CENTER);
		btnHB.setMargin(bookBtn, new Insets(0, 0, 12, 0));
				
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
			new DaftarGymnasium(stage, DaftarGymnasium.idJenis);
		});
		
		bookBtn.setOnMouseClicked(e -> {
			new BookingLapangan(stage, gymnasium);
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
	
	public DetailLapangan(Stage stage, Gymnasium inputGymnasium) {
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
		setData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
