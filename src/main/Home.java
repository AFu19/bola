package main;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.JenisLapangan;
import util.Connect;

public class Home extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpJenis;
	private Label judulLbl, subJudulLbl;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, banner1Img, banner2Img, banner3Img;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, bannerIV;
	private Font judulFont, subJudulFont, jenisLabelFont;
	private ListView<StackPane> jenisListView;
	
	private ArrayList<JenisLapangan> dataJenisLapangan = new ArrayList<>();
	private Connect connect = Connect.getInstance();
	
	int idJenis = 0;
	
	private void getJenisData() {
		dataJenisLapangan.clear();
		
		String query = "SELECT * FROM jenislapangan";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String namaJenisLapangan = connect.rs.getString("namaJenisLapangan");
				Blob foto = connect.rs.getBlob("foto");
				
				dataJenisLapangan.add(new JenisLapangan(namaJenisLapangan, foto));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		gpContainer = new GridPane();
		gpJenis = new GridPane();
		
		judulLbl = new Label("Home");
		subJudulLbl = new Label("Jenis Lapangan");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		
		homeImg = new Image("AcHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("InProfile.png");
		banner1Img = new Image("banner1.png");
		banner2Img = new Image("banner2.png");
		banner3Img = new Image("banner3.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		bannerIV = new ImageView(banner1Img);
		
		jenisListView = new ListView<>();
		
		judulFont = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		subJudulFont = Font.font("Poppins", FontWeight.BOLD, 24);
		jenisLabelFont = Font.font("Poppins", FontWeight.EXTRA_BOLD, 24);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void positioning() {
		homeHB.getChildren().add(homeIV);
		tandingHB.getChildren().add(tandingIV);
		historyHB.getChildren().add(historyIV);
		forumHB.getChildren().add(forumIV);
		profileHB.getChildren().add(profileIV);
		
		gpContainer.add(bannerIV, 0, 0);
		gpContainer.add(subJudulLbl, 0, 1);
		gpContainer.add(jenisListView, 0, 2);
		
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

		gpContainer.setPadding(new Insets(8, 24, 0, 24));
		gpContainer.setVgap(8);
		
		subJudulLbl.setFont(subJudulFont);
		subJudulLbl.setTextFill(Color.web("#458E5E"));
		gpContainer.setMargin(subJudulLbl, new Insets(8, 0, 0, 0));
		
		gpJenis.setVgap(12);
		gpJenis.setPadding(new Insets(0, 0, 12, 0));
		jenisListView.setMinHeight(414);
		jenisListView.getStylesheets().add("style.css");
		jenisListView.setPadding(new Insets(0, 0, 0, 10));
//		jenisListView.getStyleClass().add("list-view-1");
		
		menuHB.setPrefWidth(390);
		menuHB.setStyle("-fx-background-color: #F4F4F4; -fx-border-color: black transparent transparent transparent");
		menuHB.setPadding(new Insets(4, 26, 4, 26));
		
		homeHB.setPadding(new Insets(0, 16, 0, 11));
		tandingHB.setPadding(new Insets(0, 16, 0, 16));
		historyHB.setPadding(new Insets(0, 16, 0, 16));
		forumHB.setPadding(new Insets(0, 16, 0, 16));
		profileHB.setPadding(new Insets(0, 11, 0, 16));
	}
	
	private void initializeJenisLapangan() {
		for (JenisLapangan jenisLapangan : dataJenisLapangan) {
			StackPane stack = new StackPane();
			
			InputStream inputStream;
			Image image;
			ImageView imageView = new ImageView();
			
			Label namaJenisLapanganLabel = new Label();
			
			try {
				inputStream = jenisLapangan.getFoto().getBinaryStream();
				image = new Image(inputStream);
				imageView.setImage(image);
				
				namaJenisLapanganLabel.setText(jenisLapangan.getNamaJenisLapangan());
				
				stack.getChildren().addAll(imageView, namaJenisLapanganLabel);

				//style
				imageView.setFitWidth(300);
				
				namaJenisLapanganLabel.setFont(jenisLabelFont);
				namaJenisLapanganLabel.setTextFill(Color.WHITE);
				
				stack.setMaxWidth(300);
				
				jenisListView.getItems().add(stack);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handler(Stage stage) {
		
		bannerIV.setOnMouseClicked(e -> {
			if (bannerIV.getImage().equals(banner1Img)) {
				bannerIV.setImage(banner2Img);
			}else if (bannerIV.getImage().equals(banner2Img)) {
				bannerIV.setImage(banner3Img);
			}else if (bannerIV.getImage().equals(banner3Img)) {
				bannerIV.setImage(banner1Img);
			}
		});
		
		jenisListView.setOnMouseClicked(e -> {
			idJenis = jenisListView.getSelectionModel().getSelectedIndex() + 1;
			
			new DaftarGymnasium(stage, idJenis);
		});
		
	}
	
	public Home(Stage stage) {
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage homeStage) throws Exception {
		getJenisData();
				
		initialize();
		positioning();
		style();
		initializeJenisLapangan();
		handler(homeStage);
		
		homeStage.setScene(scene);
	}

}
