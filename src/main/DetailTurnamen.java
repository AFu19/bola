package main;

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
import javafx.stage.Stage;
import model.Gymnasium;
import model.Lomba;
import model.Peralatan;
import util.Connect;

public class DetailTurnamen extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer, gpInfo, gpParticipate;
	private Label judulLbl, lokasiLbl, tanggalLbl, waktuLbl, biayaLbl, prizepoolLbl, cpLbl, namaTimLbl, errorLbl;
	private Text namaText;
	private TextField lokasiTF, tanggalTF, waktuTF, biayaTF, prizepoolTF, cpTF, namaTimTF;
	private Button participateBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, participateHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, font20Bold, font14;

	private Connect connect = Connect.getInstance();
	private Lomba lomba;
	private ArrayList<String> dataNamaTim = new ArrayList<>();
	
	private void getNamaTim() {
		dataNamaTim.clear();
		
		String query = String.format("SELECT namaTim FROM formpendaftaranlomba WHERE idLomba = '%s'", lomba.getIdLomba());
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String namaTim = connect.rs.getString("namaTim");
				
				dataNamaTim.add(namaTim);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		gpInfo = new GridPane();
		gpParticipate = new GridPane();
		
		judulLbl = new Label("Detail Turnamen");
		namaText = new Text();
		lokasiLbl = new Label("Lokasi");
		tanggalLbl = new Label("Tanggal Lomba");
		waktuLbl = new Label("Waktu Lomba");
		biayaLbl = new Label("Biaya Pendaftaran");
		prizepoolLbl = new Label("Prizepool");
		cpLbl = new Label("Contact Person");
		namaTimLbl = new Label("Nama Tim");
		errorLbl = new Label();
		
		lokasiTF = new TextField();
		tanggalTF = new TextField();
		waktuTF = new TextField();
		biayaTF = new TextField();
		prizepoolTF = new TextField();
		cpTF = new TextField();
		namaTimTF = new TextField();
		
		participateBtn = new Button("Participate");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		backHB = new HBox();
		participateHB = new HBox();
		
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
		font20Bold = Font.font("Poppins", FontWeight.BOLD, 20);
		font14 = Font.font("Poppins", 14);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void setData() {
		Date date = lomba.getTanggalLomba();
		SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM yyyy");
		String formatDate = outputDateFormat.format(date);
		
		namaText.setText(lomba.getNamaLomba());
		
		lokasiTF.setText(lomba.getLokasiLomba());
		tanggalTF.setText(formatDate);
		waktuTF.setText(lomba.getWaktuLomba().toString().substring(0,5));
		biayaTF.setText("Rp" + lomba.getHargaPendaftaran());
		prizepoolTF.setText("Rp" + lomba.getPrizepool());
		cpTF.setText(lomba.getNamaContactPerson() + " - " + lomba.getTelfonContactPerson());
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		gpInfo.add(lokasiLbl, 0, 0, 2, 1);
		gpInfo.add(lokasiTF, 0, 1, 2, 1);
		gpInfo.add(tanggalLbl, 0, 2);
		gpInfo.add(tanggalTF, 0, 3);
		gpInfo.add(waktuLbl, 1, 2);
		gpInfo.add(waktuTF, 1, 3);
		gpInfo.add(biayaLbl, 0, 4);
		gpInfo.add(biayaTF, 0, 5);
		gpInfo.add(prizepoolLbl, 1, 4);
		gpInfo.add(prizepoolTF, 1, 5);
		gpInfo.add(cpLbl, 0, 6, 2, 1);
		gpInfo.add(cpTF, 0, 7, 2, 1);
		
		participateHB.getChildren().add(participateBtn);
		
		gpParticipate.add(namaTimLbl, 0, 0);
		gpParticipate.add(namaTimTF, 0, 1);
		gpParticipate.add(errorLbl, 0, 2);
		
		gpContainer.add(namaText, 0, 0);
		gpContainer.add(gpInfo, 0, 1);
		gpContainer.add(gpParticipate, 0, 2);
		gpContainer.add(participateHB, 0, 3);
		
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
		gpContainer.setMargin(namaText, new Insets(0, 0, 12, 0));
		
		namaText.setWrappingWidth(342);
		namaText.setFont(font20Bold);
		lokasiLbl.setFont(font14);
		tanggalLbl.setFont(font14);
		waktuLbl.setFont(font14);
		biayaLbl.setFont(font14);
		prizepoolLbl.setFont(font14);
		cpLbl.setFont(font14);
		namaTimLbl.setFont(font14);
		
		gpInfo.setHgap(22);
		gpInfo.setVgap(4);
		gpInfo.setPadding(new Insets(0, 0, 24, 0));
		gpInfo.setStyle("-fx-border-color: transparent transparent black transparent");
		
		lokasiTF.setMinSize(342, 40);
		lokasiTF.setStyle("-fx-background-color: white; -fx-background-radius: 9; -fx-border-color: #458E5E; -fx-border-radius: 9");
		lokasiTF.setEditable(false);
		tanggalTF.setMinSize(160, 40);
		tanggalTF.setStyle("-fx-background-color: white; -fx-background-radius: 9; -fx-border-color: #458E5E; -fx-border-radius: 9");
		tanggalTF.setEditable(false);
		waktuTF.setMinSize(160, 40);
		waktuTF.setStyle("-fx-background-color: white; -fx-background-radius: 9; -fx-border-color: #458E5E; -fx-border-radius: 9");
		waktuTF.setEditable(false);
		biayaTF.setMinSize(160, 40);
		biayaTF.setStyle("-fx-background-color: white; -fx-background-radius: 9; -fx-border-color: #458E5E; -fx-border-radius: 9");
		biayaTF.setEditable(false);
		prizepoolTF.setMinSize(160, 40);
		prizepoolTF.setStyle("-fx-background-color: white; -fx-background-radius: 9; -fx-border-color: #458E5E; -fx-border-radius: 9");
		prizepoolTF.setEditable(false);
		cpTF.setMinSize(342, 40);
		cpTF.setStyle("-fx-background-color: white; -fx-background-radius: 9; -fx-border-color: #458E5E; -fx-border-radius: 9");
		cpTF.setEditable(false);
		namaTimTF.setMinSize(342, 40);
		namaTimTF.setStyle("-fx-background-color: white; -fx-background-radius: 9; -fx-border-color: #458E5E; -fx-border-radius: 9");
		namaTimTF.requestFocus();
		
		gpParticipate.setVgap(4);
		gpParticipate.setPadding(new Insets(24, 0, 40, 0));
		
		participateHB.setPrefHeight(200);
		participateHB.setAlignment(Pos.BOTTOM_CENTER);
		
		errorLbl.setFont(font14);
		errorLbl.setTextFill(Color.RED);

		participateBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		participateBtn.setPrefSize(160, 48);
		participateBtn.setTextFill(Color.WHITE);
		participateBtn.setFont(font20Bold);
		
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
			new Tanding(stage, Home.idCustomer);
		});
		
		participateBtn.setOnMouseClicked(e -> {
			if (namaTimTF.getText().isEmpty()) {
				errorLbl.setText("Isi nama tim!");
			}else if (dataNamaTim.contains(namaTimTF.getText())) {
				errorLbl.setText("Nama tim sudah digunakan!");
			}else {
				errorLbl.setText("");
				new MetodePembayaranLomba(stage, lomba.getIdLomba(), namaTimTF.getText(), lomba);
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
	
	public DetailTurnamen(Stage stage, Lomba inputLomba) {
		lomba = inputLomba;
		
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage detailLapanganStage) throws Exception {
		getNamaTim();
		
		initialize();
		setData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
