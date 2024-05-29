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
import model.DetailSewaPeralatan;
import model.Gymnasium;
import model.Review;
import util.Connect;

public class DetailHistoryCompleted extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader, fpStar;
	private GridPane gpContainer, gpJadwal;
	private Label judulLbl, namaLbl, alamatLbl, tanggalLbl, tanggallLbl, jadwalLbl, jadwallLbl, reviewLbl, deskripsiLbl, errorLbl;
	private TextArea alamatTA, deskripsiTA;
	private ImageView fotoIV;
	private Button submitBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, btnHB, starHB1, starHB2, starHB3, starHB4, starHB5;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg, inStar, acStar;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView, starIV1, starIV2, starIV3, starIV4, starIV5;
	private Font judulFont, namaFont, deskripsiBold, deskripsiFont;
	
	private String idBooking, idGymnasium, jamMulaiBook, jamSelesaiBook;;
	private Date tglBook;
	private Gymnasium gymnasium;
	private Connect connect = Connect.getInstance();
	private int reviewInt = 0;
	private boolean isReviewed = false;
	private Review review;
	
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
	
	private void getReview() {
		String query = String.format("SELECT * FROM review WHERE idBooking = '%s'", idBooking);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idReview = connect.rs.getString("idReview");
				String idBooking = connect.rs.getString("idBooking");
				int rating = connect.rs.getInt("rating");
				String deskripsiReview = connect.rs.getString("deskripsiReview");
				
				review = new Review(idReview, query, rating, deskripsiReview);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void insertReview() {
		String query = String.format("INSERT INTO review VALUES(null, '%s', '%d', '%s')", idBooking, reviewInt, deskripsiTA.getText());
		connect.execUpdate(query);
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		fpStar = new FlowPane();
		gpContainer = new GridPane();
		gpJadwal = new GridPane();
		
		judulLbl = new Label("Review");
		namaLbl = new Label();
		alamatLbl = new Label("Alamat");
		tanggalLbl = new Label("Tanggal");
		tanggallLbl = new Label();
		jadwalLbl = new Label("Jadwal");
		jadwallLbl = new Label();
		reviewLbl = new Label("Review");
		deskripsiLbl = new Label("Deskripsi Review");
		errorLbl = new Label();
		
		fotoIV = new ImageView();
		
		alamatTA = new TextArea();
		deskripsiTA = new TextArea();
		
		submitBtn = new Button("Submit");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		backHB = new HBox();
		btnHB = new HBox();
		starHB1 = new HBox();
		starHB2 = new HBox();
		starHB3 = new HBox();
		starHB4 = new HBox();
		starHB5 = new HBox();
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("InProfile.png");
		backImg = new Image("back.png");
		inStar = new Image("inReviewStar.png");
		acStar = new Image("acReviewStar.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		backImgView = new ImageView(backImg);
		starIV1 = new ImageView(inStar);
		starIV2 = new ImageView(inStar);
		starIV3 = new ImageView(inStar);
		starIV4 = new ImageView(inStar);
		starIV5 = new ImageView(inStar);
		
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
		
		if (isReviewed) {
			switch (review.getRating()) {
			case 5:
				starIV5.setImage(acStar);
				
			case 4:
				starIV4.setImage(acStar);
				
			case 3:
				starIV3.setImage(acStar);
				
			case 2:
				starIV2.setImage(acStar);
				
			case 1:
				starIV1.setImage(acStar);
			}
			
			deskripsiTA.setEditable(false);
			deskripsiTA.setText(review.getDeskripsiReview());
		}
	}

	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		gpJadwal.add(tanggalLbl, 0, 0);
		gpJadwal.add(tanggallLbl, 0, 1);
		gpJadwal.add(jadwalLbl, 1, 0);
		gpJadwal.add(jadwallLbl, 1, 1);
		
		starHB1.getChildren().add(starIV1);
		starHB2.getChildren().add(starIV2);
		starHB3.getChildren().add(starIV3);
		starHB4.getChildren().add(starIV4);
		starHB5.getChildren().add(starIV5);
		
		fpStar.getChildren().addAll(starHB1, starHB2, starHB3, starHB4, starHB5);
		
		btnHB.getChildren().addAll(submitBtn);
		
		gpContainer.add(namaLbl, 0, 0);
		gpContainer.add(fotoIV, 0, 1);
		gpContainer.add(alamatLbl, 0, 2);
		gpContainer.add(alamatTA, 0, 3);
		gpContainer.add(gpJadwal, 0, 4);
		gpContainer.add(reviewLbl, 0, 5);
		gpContainer.add(fpStar, 0, 6);
		gpContainer.add(deskripsiLbl, 0, 7);
		gpContainer.add(deskripsiTA, 0, 8);
		gpContainer.add(errorLbl, 0, 9);
		if (!isReviewed) {
			gpContainer.add(btnHB, 0, 10);			
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
		
		gpJadwal.setHgap(100);

		gpContainer.setPadding(new Insets(24));
		gpContainer.setMargin(namaLbl, new Insets(0, 0, 12, 0));
		gpContainer.setMargin(fotoIV, new Insets(0, 0, 8, 0));
		gpContainer.setMargin(alamatTA, new Insets(0, 0, 8, 0));
		gpContainer.setMargin(gpJadwal, new Insets(0, 0, 8, 0));
		gpContainer.setMargin(fpStar, new Insets(0, 0, 8, 0));
		gpContainer.setMargin(deskripsiTA, new Insets(0, 0, 8, 0));
		gpContainer.setMargin(errorLbl, new Insets(0, 0, 30, 0));
		
		namaLbl.setFont(namaFont);
		
		fotoIV.setStyle("-fx-background-radius: 12px;");
		
		alamatTA.setWrapText(true);
		alamatTA.setEditable(false);
		alamatTA.setMinHeight(70);
		alamatTA.setMaxHeight(70);
		alamatTA.setStyle("-fx-text-fill: black;");
		
		deskripsiTA.setWrapText(true);
		deskripsiTA.setMinHeight(80);
		deskripsiTA.setMaxHeight(80);
		
		alamatLbl.setFont(deskripsiBold);
		alamatLbl.setTextFill(Color.web("#458E5E"));
		tanggalLbl.setFont(deskripsiBold);
		tanggalLbl.setTextFill(Color.web("#458E5E"));
		jadwalLbl.setFont(deskripsiBold);
		jadwalLbl.setTextFill(Color.web("#458E5E"));
		reviewLbl.setFont(deskripsiBold);
		reviewLbl.setTextFill(Color.web("#458E5E"));
		deskripsiLbl.setFont(deskripsiBold);
		deskripsiLbl.setTextFill(Color.web("#458E5E"));
		errorLbl.setFont(deskripsiBold);
		errorLbl.setTextFill(Color.RED);
		tanggallLbl.setFont(deskripsiFont);
		jadwallLbl.setFont(deskripsiFont);
		
		submitBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		submitBtn.setPrefSize(160, 48);
		submitBtn.setTextFill(Color.WHITE);
		submitBtn.setFont(namaFont);
		
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
			new HistoryCompleted(stage, Home.idCustomer);
		});
		
		if (!isReviewed) {
			starHB1.setOnMouseClicked(e -> {
				reviewInt = 1;
				
				starIV1.setImage(acStar);
				starIV2.setImage(inStar);
				starIV3.setImage(inStar);
				starIV4.setImage(inStar);
				starIV5.setImage(inStar);
			});
			
			starHB2.setOnMouseClicked(e -> {
				reviewInt = 2;
				
				starIV1.setImage(acStar);
				starIV2.setImage(acStar);
				starIV3.setImage(inStar);
				starIV4.setImage(inStar);
				starIV5.setImage(inStar);
			});
			
			starHB3.setOnMouseClicked(e -> {
				reviewInt = 3;
				
				starIV1.setImage(acStar);
				starIV2.setImage(acStar);
				starIV3.setImage(acStar);
				starIV4.setImage(inStar);
				starIV5.setImage(inStar);
			});
			
			starHB4.setOnMouseClicked(e -> {
				reviewInt = 4;
				
				starIV1.setImage(acStar);
				starIV2.setImage(acStar);
				starIV3.setImage(acStar);
				starIV4.setImage(acStar);
				starIV5.setImage(inStar);
			});
			
			starHB5.setOnMouseClicked(e -> {
				reviewInt = 5;
				
				starIV1.setImage(acStar);
				starIV2.setImage(acStar);
				starIV3.setImage(acStar);
				starIV4.setImage(acStar);
				starIV5.setImage(acStar);
			});
		}
		
		submitBtn.setOnMouseClicked(e -> {
			if (reviewInt == 0) {
				errorLbl.setText("Masukkan rating!");
			}else if (deskripsiTA.getText().isEmpty()) {
				errorLbl.setText("Masukkan deskripsi!");
			}else {
				errorLbl.setText("");
				insertReview();
				new HistoryCompleted(stage, Home.idCustomer);
			}
		});
	}
	
	public DetailHistoryCompleted(Stage stage, String inputIdBooking, String inputIdGymnasium, Boolean isReviewed) {
		idBooking = inputIdBooking;
		idGymnasium = inputIdGymnasium;
		this.isReviewed = isReviewed;
		
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
		getReview();
		
		initialize();
		setData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
