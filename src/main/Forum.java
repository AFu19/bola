package main;

import java.io.InputStream;
import java.sql.Blob;
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
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ForumObject;
import model.HistoryObject;
import model.JenisLapangan;
import util.Connect;

public class Forum extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpJenis;
	private StackPane spHeader;
	private Label judulLbl;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, headerHB;
	private Button plusBtn;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV;
	private Font judulFont, font20Bold, font16Semi, font12, font12Bold;
	private ListView<HBox> forumListView;
	
	private ArrayList<ForumObject> dataForum = new ArrayList<>();
	private Connect connect = Connect.getInstance();
	public String idCustomer;
	
	private void getData() {
		dataForum.clear();
		
		String query = "SELECT * FROM forum ORDER BY idForum DESC";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idForum = connect.rs.getString("idForum");
				String idCustomer = connect.rs.getString("idCustomer");
				String judulForum = connect.rs.getString("judulForum");
				String isiForum = connect.rs.getString("isiForum");
				Date tanggalForum = connect.rs.getDate("tanggalForum");
				
				dataForum.add(new ForumObject(idForum, idCustomer, judulForum, isiForum, tanggalForum));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	private String getNamaPengguna(String inputIdCust) {
		String query = "SELECT namaCustomer FROM customer WHERE idCustomer = '" + inputIdCust + "'";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				return connect.rs.getString("namaCustomer");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;	
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		gpContainer = new GridPane();
		gpJenis = new GridPane();
		spHeader = new StackPane();
		
		judulLbl = new Label("Forum");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		headerHB = new HBox();
		
		plusBtn = new Button("+");
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("AcForum.png");
		profileImg = new Image("InProfile.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		
		forumListView = new ListView<>();
		
		judulFont = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		font20Bold = Font.font("Poppins", FontWeight.EXTRA_BOLD, 20);
		font16Semi = Font.font("Poppins", FontWeight.BOLD, 16);
		font12 = Font.font("Poppins", 12);
		font12Bold = Font.font("Poppins", FontWeight.EXTRA_BOLD, 12);
		
		scene = new Scene(bpMain, 390, 800);
	}

	private void initializeListView() {			
		for (ForumObject forumObject : dataForum) {
			GridPane gp = new GridPane();
			HBox hb = new HBox();
			
			Label judulForum = new Label();
			Text isiForum = new Text();
			Label postedAccountLbl = new Label();
			
			judulForum.setText(forumObject.getJudulForum());
			isiForum.setText(forumObject.getIsiForum());
			postedAccountLbl.setText("- " + getNamaPengguna(forumObject.getIdCustomer()));
			
			//positioning
			gp.add(judulForum, 0, 0);
			gp.add(isiForum, 0, 1);
			gp.add(postedAccountLbl, 0, 2);
			
			hb.getChildren().add(gp);
			
			//style
			hb.setPrefWidth(300);
			
			judulForum.setFont(font16Semi);
			isiForum.setWrappingWidth(300);
			isiForum.setFont(font12);
			postedAccountLbl.setFont(font12Bold);
			
			gp.setVgap(4);
			
			//add to listView
			forumListView.getItems().add(hb);
		}
	}
	
	private void positioning() {
		headerHB.getChildren().add(plusBtn);
		
		spHeader.getChildren().addAll(judulLbl, headerHB);
		
		homeHB.getChildren().add(homeIV);
		tandingHB.getChildren().add(tandingIV);
		historyHB.getChildren().add(historyIV);
		forumHB.getChildren().add(forumIV);
		profileHB.getChildren().add(profileIV);
		
		gpContainer.add(forumListView, 0, 0);
		
		menuHB.getChildren().addAll(homeHB, tandingHB, historyHB, forumHB, profileHB);
		
		bpMain.setTop(spHeader);
		bpMain.setCenter(gpContainer);
		bpMain.setBottom(menuHB);
	}
	
	private void style() {
		bpMain.setPrefWidth(390);
		bpMain.setPadding(new Insets(24, 0, 0, 0));
		
		bpMain.setAlignment(judulLbl, Pos.CENTER);
		judulLbl.setFont(judulFont);
		judulLbl.setTextFill(Color.web("#458E5E"));
		
		plusBtn.setMinSize(43, 43);
		plusBtn.setMaxSize(43, 43);
		plusBtn.setStyle("-fx-background-color: #458E5E; -fx-background-radius: 10;");
		plusBtn.setFont(font20Bold);
		plusBtn.setTextFill(Color.WHITE);
		
		headerHB.setAlignment(Pos.CENTER_RIGHT);
		headerHB.setPadding(new Insets(0, 24, 0, 0));
		
		gpContainer.setPadding(new Insets(8, 24, 0, 24));
		gpContainer.setVgap(8);
		
		gpJenis.setVgap(12);
		gpJenis.setPadding(new Insets(0, 0, 12, 0));
		forumListView.setMinWidth(342);
		forumListView.setMaxWidth(342);
		forumListView.setPrefHeight(700);
		forumListView.getStylesheets().add("style.css");
		forumListView.getStyleClass().add("list-view-1");
		
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
		plusBtn.setOnMouseClicked(e -> {
			new FormForum(stage, Home.idCustomer);
		});
		
		forumListView.setOnMouseClicked(e -> {
			if (!forumListView.getSelectionModel().isEmpty()) {		
				new DetailForum(stage, idCustomer, dataForum.get(forumListView.getSelectionModel().getSelectedIndex()));
			}
		});
		
		//menu
		homeHB.setOnMouseClicked(e -> {
			new Home(stage, Home.idCustomer);
		});
		
		historyHB.setOnMouseClicked(e -> {
			new History(stage, Home.idCustomer);
		});
		
		profileHB.setOnMouseClicked(e -> {
			new Profile(stage, idCustomer);
		});
	}
	
	public Forum(Stage stage, String idCust) {
		idCustomer = idCust;
		
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage homeStage) throws Exception {
		getData();
		
		initialize();
		positioning();
		style();
		initializeListView();
		handler(homeStage);
		
		homeStage.setScene(scene);
	}

}
