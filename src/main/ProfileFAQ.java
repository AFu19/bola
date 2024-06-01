package main;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Faq;
import util.Connect;

public class ProfileFAQ extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private GridPane gpContainer, gpFaq;
	private Label judulLbl;
	private Text answerText;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, subMenuHB;
	private Button informationBtn, faqBtn;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV;
	private Font judulFont, font14, font20Semi;
	private ListView<HBox> questionList;
	
	private Connect connect = Connect.getInstance();
	private String idCustomer;
	private ArrayList<Faq> dataFaq = new ArrayList<>();
	
	private void getData() {
		dataFaq.clear();
		
		String query = "SELECT * FROM faq";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idFaq = connect.rs.getString("idFaq");
				String question = connect.rs.getString("question");
				String answer = connect.rs.getString("answer");
				String idAdmin = connect.rs.getString("idAdmin");
				
				dataFaq.add(new Faq(idFaq, question, answer, idAdmin));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		bpMain = new BorderPane();
		gpContainer = new GridPane();
		gpFaq = new GridPane();
		
		judulLbl = new Label("Profile");
		
		answerText = new Text();

		menuHB = new HBox();
		homeHB = new HBox();
		tandingHB = new HBox();
		historyHB = new HBox();
		forumHB = new HBox();
		profileHB = new HBox();
		subMenuHB = new HBox();
		
		informationBtn = new Button("Information");
		faqBtn = new Button("FAQ");
		
		questionList = new ListView<>();
		
		homeImg = new Image("InHome.png");
		tandingImg = new Image("InTanding.png");
		historyImg = new Image("InHistory.png");
		forumImg = new Image("InForum.png");
		profileImg = new Image("AcProfile.png");
		
		homeIV = new ImageView(homeImg);
		tandingIV = new ImageView(tandingImg);
		historyIV = new ImageView(historyImg);
		forumIV = new ImageView(forumImg);
		profileIV = new ImageView(profileImg);
		
		judulFont = Font.font("Poppins", FontWeight.EXTRA_BOLD, 30);
		font14 = Font.font("Poppins", 14);
		font20Semi = Font.font("Poppins", FontWeight.BOLD, 20);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void setData() {
		for (Faq faq : dataFaq) {
			HBox hb = new HBox();
			TextArea ta = new TextArea();
			
			hb.getChildren().add(ta);
			ta.setText(faq.getQuestion());
			ta.setMaxSize(315, 50);
			ta.setMinSize(315, 50);
			ta.setDisable(true);
			ta.setWrapText(true);
			ta.setStyle("-fx-opacity: 1.0;");

			questionList.getItems().add(hb);			
		}
	}
	
	private void positioning() {
		subMenuHB.getChildren().addAll(informationBtn, faqBtn);

		gpFaq.add(questionList, 0, 0);
		gpFaq.add(answerText, 0, 1);
		
		homeHB.getChildren().add(homeIV);
		tandingHB.getChildren().add(tandingIV);
		historyHB.getChildren().add(historyIV);
		forumHB.getChildren().add(forumIV);
		profileHB.getChildren().add(profileIV);
		
		gpContainer.add(subMenuHB, 0, 0);
		gpContainer.add(gpFaq, 0, 1);
		
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

		subMenuHB.setMinWidth(342);
		subMenuHB.setMaxWidth(342);
		subMenuHB.setAlignment(Pos.CENTER);
		subMenuHB.setSpacing(70);
		subMenuHB.setPadding(new Insets(0, 0, 16, 0));
		
		informationBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		informationBtn.setPadding(new Insets(0));
		informationBtn.setMinSize(120, 40);
		informationBtn.setMaxSize(120, 40);
		informationBtn.setTextFill(Color.web("#A3A3A3"));
		informationBtn.setFont(font20Semi);
		
		faqBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent transparent #FF7E46 transparent; -fx-border-width: 3;");
		faqBtn.setPadding(new Insets(0));
		faqBtn.setMinSize(120, 40);
		faqBtn.setMaxSize(120, 40);
		faqBtn.setTextFill(Color.web("#FF7E46"));
		faqBtn.setFont(font20Semi);
		
		gpContainer.setPadding(new Insets(8, 24, 0, 24));
		gpContainer.setVgap(8);
		
		gpFaq.setVgap(8);
		gpFaq.setPrefHeight(520);
		
		questionList.setMinSize(342, 180);
		questionList.setMaxSize(342, 180);
		
		answerText.setFont(font14);
		answerText.wrappingWidthProperty().set(342);
		
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
		informationBtn.setOnMouseClicked(e -> {
			new Profile(stage, Home.idCustomer);
		});
		
		questionList.setOnMouseClicked(e -> {
			if (!questionList.getSelectionModel().isEmpty()) {
				int index = questionList.getSelectionModel().getSelectedIndex();
				String tempAnswer = dataFaq.get(index).getAnswer();
				
				answerText.setText(tempAnswer);
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
	
	public ProfileFAQ(Stage stage, String idCust) {
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
		setData();
		positioning();
		style();
		handler(homeStage);
		
		homeStage.setScene(scene);
	}

}
