package main;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;
import model.ForumObject;
import model.Peralatan;
import model.ReplyForum;
import util.Connect;

public class DetailForum extends Application{
	
	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private GridPane gpContainer;
	private Label judulLbl, judulForumLbl, forumPosterLbl, repliesLbl;
	private Text isiForumText;
	private TextField replyTF;
	private Button postBtn;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB, replyHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, font16, font12Bold, font12;
	private ListView<HBox> listReply;

	private Connect connect = Connect.getInstance();
	private ForumObject forumObject;
	private String idCustomer;
	private ArrayList<ReplyForum> dataReplyForum = new ArrayList<>();
	
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
	
	private void getData() {
		dataReplyForum.clear();
		
		String query = String.format("SELECT * FROM replyforum WHERE idForum = '%s' ORDER BY idReplyForum DESC", forumObject.getIdForum());
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idReplyForum = connect.rs.getString("idReplyForum");
				String idForum = connect.rs.getString("idForum");
				String idCustomer = connect.rs.getString("idCustomer");
				String isiReplyForum = connect.rs.getString("isiReplyForum");
				Date tanggalReplyForum = connect.rs.getDate("tanggalReplyForum");
				
				dataReplyForum.add(new ReplyForum(idReplyForum, idForum, idCustomer, isiReplyForum, tanggalReplyForum));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertReply(String inputReply) {
		String query = String.format("INSERT INTO replyforum VALUES (null,'%s','%s','%s','%s')", forumObject.getIdForum(), idCustomer, inputReply, LocalDate.now().toString());
		connect.execUpdate(query);
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		gpContainer = new GridPane();
		
		judulLbl = new Label("Detail Forum");
		judulForumLbl = new Label();
		forumPosterLbl = new Label();
		isiForumText = new Text();
		repliesLbl = new Label("Replies: ");
		
		replyTF = new TextField();
		
		listReply = new ListView<>();
		
		postBtn = new Button("Post");
		
		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		backHB = new HBox();
		replyHB = new HBox();
		
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
		font16 = Font.font("Poppins", 16);
		font12Bold = Font.font("Poppins", FontWeight.BOLD, 12);
		font12 = Font.font("Poppins", 12);
		
		scene = new Scene(bpMain, 390, 800);
	}

	private void setData() {
		judulForumLbl.setText(forumObject.getJudulForum());
		forumPosterLbl.setText(getNamaPengguna(forumObject.getIdCustomer()));
		isiForumText.setText(forumObject.getIsiForum());
	}
	
	private void setListViewData() {
		listReply.getItems().clear();
		
		for (ReplyForum replyForum : dataReplyForum) {
			GridPane gp = new GridPane();
			HBox hb = new HBox();
			
			Label namaPenggunaLbl = new Label();
			Text isiReplyText = new Text();
			
			namaPenggunaLbl.setText(getNamaPengguna(replyForum.getIdCustomer()));
			isiReplyText.setText(replyForum.getIsiReplyForum());
			
			//positioning
			gp.add(namaPenggunaLbl, 0, 0);
			gp.add(isiReplyText, 0, 1);
			
			hb.getChildren().add(gp);
			
			//style
			hb.setPrefWidth(300);
			
			namaPenggunaLbl.setFont(font12Bold);
			isiReplyText.setWrappingWidth(300);
			isiReplyText.setFont(font12);
			
			gp.setVgap(4);
			
			//add to listView
			listReply.getItems().add(hb);
		}
	}
	
	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		replyHB.getChildren().addAll(replyTF, postBtn);
				
		gpContainer.add(judulForumLbl, 0, 0);
		gpContainer.add(forumPosterLbl, 0, 1);
		gpContainer.add(isiForumText, 0, 2);
		gpContainer.add(repliesLbl, 0, 3);
		gpContainer.add(listReply, 0, 4);
		gpContainer.add(replyHB, 0, 5);
		
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

		gpContainer.setPadding(new Insets(24, 24, 8, 24));
		gpContainer.setVgap(0);
		gpContainer.setMargin(isiForumText, new Insets(12, 0, 16, 0));
		
		judulForumLbl.setFont(namaFont);
		forumPosterLbl.setFont(font16);
		isiForumText.setFont(font16);
		isiForumText.setWrappingWidth(342);
		
		repliesLbl.setFont(font12);
		
		listReply.setMinHeight(200);
		listReply.setMaxHeight(200);
		listReply.getStylesheets().add("style.css");
		listReply.getStyleClass().add("list-view-1");
		
		replyTF.setPrefSize(282, 44);
		replyTF.setStyle("-fx-border-color: #000000; -fx-border-width: 2; -fx-border-radius: 6; -fx-background-radius: 6;");
		
		postBtn.setStyle("-fx-background-color: #FF7E46; -fx-background-radius: 8px;");
		postBtn.setPrefSize(80, 36);
		postBtn.setTextFill(Color.WHITE);
		postBtn.setFont(namaFont);
		
		replyHB.setPrefSize(390, 600);
		replyHB.setSpacing(4);
		replyHB.setAlignment(Pos.BOTTOM_CENTER);
		
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
			new Forum(stage, Home.idCustomer);
		});
		
		postBtn.setOnMouseClicked(e -> {
			String replyStr = replyTF.getText();
			
			insertReply(replyStr);
			getData();
			setListViewData();
			
			replyTF.clear();
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
	
	public DetailForum(Stage stage, String inputIdCustomer, ForumObject selectedForum) {
		idCustomer = inputIdCustomer;
		forumObject = selectedForum;
		
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage detailLapanganStage) throws Exception {
		getData();
		
		initialize();
		setData();
		setListViewData();
		positioning();
		style();
		
		handler(detailLapanganStage);
		
		detailLapanganStage.setScene(scene);
	}

}
