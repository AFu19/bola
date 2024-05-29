package main;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DetailBooking;
import model.Gymnasium;
import util.Connect;

public class OpenSparring extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer, gpSyarat, gpJlhPemain;
	private Label judulLbl, textLbl, lbl1, lbl2, lbl3, minLbl, maxLbl, checkSyaratLbl, checkMinMaxLbl;
	private TextArea ta1, ta2, ta3;
	private CheckBox cbSyarat;
	private TextField minTF, maxTF;
	private Button lanjutBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, btnHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, btnFont, font15, font12;

	private String metodePembayaranStr;
	private Gymnasium gymnasium;
	private LocalDate tanggalBooking;
	private ArrayList<DetailBooking> dataDetail = new ArrayList<>();
	private int idBooking;
	public static Integer minPemain, maxPemain;
	
	private Connect connect = Connect.getInstance();
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		gpSyarat = new  GridPane();
		gpJlhPemain = new GridPane();
		
		judulLbl = new Label("Syarat & Ketentuan");
		textLbl = new Label("Informasi Jumlah Pemain");
		lbl1 = new Label("1.");
		lbl2 = new Label("2.");
		lbl3 = new Label("3.");
		minLbl = new Label("Jumlah Min. Pemain:");
		maxLbl = new Label("Jumlah Max. Pemain:");
		checkSyaratLbl = new Label();
		checkMinMaxLbl = new Label();
		
		ta1 = new TextArea();
		ta2 = new TextArea();
		ta3 = new TextArea();
		
		cbSyarat = new CheckBox("Saya menyetujui syarat & ketentuan yang berlaku.");
		
		minTF = new TextField();
		maxTF = new TextField();
		
		lanjutBtn = new Button("Lanjut");
		
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
		namaFont = Font.font("Poppins", 18);
		btnFont = Font.font("Poppins", FontWeight.BOLD, 22);
		font15 = Font.font("Poppins", 15);
		font12 = Font.font("Poppins", 12);
		
		scene = new Scene(bpMain, 390, 800);
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		gpSyarat.add(lbl1, 0, 0);
		gpSyarat.add(ta1, 1, 0);
		gpSyarat.add(lbl2, 0, 1);
		gpSyarat.add(ta2, 1, 1);
		gpSyarat.add(lbl3, 0, 2);
		gpSyarat.add(ta3, 1, 2);
		gpSyarat.add(cbSyarat, 0, 3, 2, 1);
		gpSyarat.add(checkSyaratLbl, 0, 4, 2, 1);
		
		gpJlhPemain.add(textLbl, 0, 0, 2, 1);
		gpJlhPemain.add(minLbl, 0, 1);
		gpJlhPemain.add(minTF, 1, 1);
		gpJlhPemain.add(maxLbl, 0, 2);
		gpJlhPemain.add(maxTF, 1, 2);
		gpJlhPemain.add(checkMinMaxLbl, 0, 3, 2, 1);
		
		btnHB.getChildren().add(lanjutBtn);
		
		gpContainer.add(gpSyarat, 0, 0);
		gpContainer.add(gpJlhPemain, 0, 1);
		gpContainer.add(btnHB, 0, 2);
		
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
		
		ta1.setText("Informasi kontak Anda akan dibagikan kepada pengguna yang bergabung pada kegiatan Sparring Anda.");
		ta1.setWrapText(true);
		ta1.setPrefSize(320, 70);
		ta1.setEditable(false);
		ta1.setStyle("-fx-background-color: transparent; -fx-border-color:transparent; -fx-text-fill: black");
		
		ta2.setText("Sparring akan dibatalkan secara otomatis apabila tidak menemukan lawan tanding dalam waktu 1 x 24 jam sebelum jadwal sparring yang didaftarkan.");
		ta2.setWrapText(true);
		ta2.setPrefSize(320, 70);
		ta2.setEditable(false);
		ta2.setStyle("-fx-background-color: transparent; -fx-border-color:transparent; -fx-text-fill: black");

		ta3.setText("Pembagian biaya sparring akan dilakukan secara mandiri sesuai dengan kesepakatan pengguna.");
		ta3.setWrapText(true);
		ta3.setPrefSize(320, 70);
		ta3.setEditable(false);
		ta3.setStyle("-fx-background-color: transparent; -fx-border-color:transparent; -fx-text-fill: black");
		
		textLbl.setFont(namaFont);
		lbl1.setPrefHeight(70);
		lbl1.setAlignment(Pos.TOP_CENTER);
		lbl1.setPadding(new Insets(5, 0, 0, 0));
		
		lbl2.setPrefHeight(70);
		lbl2.setAlignment(Pos.TOP_CENTER);
		lbl2.setPadding(new Insets(5, 0, 0, 0));
		
		lbl3.setPrefHeight(70);
		lbl3.setAlignment(Pos.TOP_CENTER);
		lbl3.setPadding(new Insets(5, 0, 0, 0));
		
		cbSyarat.setFont(font12);
		
		checkSyaratLbl.setFont(font12);
		checkSyaratLbl.setTextFill(Color.RED);
		checkMinMaxLbl.setFont(font12);
		checkMinMaxLbl.setTextFill(Color.RED);
		
		gpSyarat.setVgap(4);
		
		gpJlhPemain.setVgap(4);
		gpJlhPemain.setHgap(4);
		minLbl.setFont(font15);
		maxLbl.setFont(font15);
		
		minTF.setPrefWidth(35);
		maxTF.setPrefWidth(35);
		
		lanjutBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		lanjutBtn.setPrefSize(160, 48);
		lanjutBtn.setTextFill(Color.WHITE);
		lanjutBtn.setFont(btnFont);
		
		btnHB.setAlignment(Pos.BOTTOM_CENTER);
		btnHB.setMargin(lanjutBtn, new Insets(0, 0, 12, 0));
				
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
			new BookingLapangan(stage, gymnasium);
		});
		
		lanjutBtn.setOnMouseClicked(e -> {
			Boolean checkSyarat = false, checkPemain = false;
			
			if (cbSyarat.isSelected()) {
				checkSyaratLbl.setText("");
				checkSyarat = true;
			}else {
				checkSyaratLbl.setText("Setujui persyaratan!");
				checkSyarat = false;
			}
			
			try {
				minPemain = Integer.parseInt(minTF.getText());
				maxPemain = Integer.parseInt(maxTF.getText());
				checkMinMaxLbl.setText("");
				checkPemain = true;
				
			} catch (Exception e2) {
				checkMinMaxLbl.setText("incorrect value!");
				checkPemain = false;
			}
			
			if (checkPemain && checkSyarat) {
				new MetodePembayaran(stage, gymnasium, tanggalBooking, dataDetail, true);
			}
		});
	}
	
	public OpenSparring(Stage stage, Gymnasium inputGymnasium, LocalDate inputTanggalBooking, ArrayList<DetailBooking> arrDetail) {
		gymnasium = inputGymnasium;
		this.tanggalBooking = inputTanggalBooking;
		for (DetailBooking detailBooking : arrDetail) {
			dataDetail.add(detailBooking);			
		}
		
				
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void start(Stage detailLapanganStage) throws Exception {
		initialize();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
