package main;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import util.Connect;

public class DaftarGymnasium extends Application{

	private Scene scene;
	private BorderPane bpMain;
	private FlowPane fpHeader;
	private Label judulLbl;
	private HBox menuHB, homeHB, tandingHB, historyHB, forumHB, profileHB, backHB;
	private Image homeImg, tandingImg, historyImg, forumImg, profileImg, backImg;
	private ImageView homeIV, tandingIV, historyIV, forumIV, profileIV, backImgView;
	private Font judulFont, namaFont, alamatFont;
	private ListView<HBox> gymnasiumListView;
	
	private Connect connect = Connect.getInstance();
	private ArrayList<Gymnasium> dataGymnasium = new ArrayList<>();
	
	static int idJenis;
	
	private void getDaftar() {
		dataGymnasium.clear();
		
		String query = String.format("SELECT * FROM gymnasium WHERE idJenisLapangan = %d", idJenis);
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
				
				dataGymnasium.add(new Gymnasium(idGymnasium, idJenisLapangan, idPemilikGymnasium, namaGymnasium, fotoGymnasium, alamatGymnasium, deskripsiFasilitas, hargaGymnasium, jamBuka, jamTutup, jumlahLapangan, statusGymnasium));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		bpMain = new BorderPane();
		fpHeader = new FlowPane();
		
		judulLbl = new Label("Daftar Gymnasium");
		
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
		
		gymnasiumListView = new ListView<>();
		
		judulFont = Font.font("Poppins", FontWeight.BOLD, 30);
		namaFont = Font.font("Poppins", FontWeight.BOLD, 16);
		alamatFont = Font.font("Poppins", 12);
		
		scene = new Scene(bpMain, 390, 800);
	}
	
	private void initializeListView() {
		getDaftar();			
		
		for (Gymnasium gymnasium : dataGymnasium) {
			FlowPane fp = new FlowPane();
			GridPane gp = new GridPane();
			HBox hb = new HBox();
			
			InputStream inputStream;
			Image image;
			ImageView imageView = new ImageView();
			
			Label namaGymnasium = new Label();
			javafx.scene.control.TextArea alamat = new javafx.scene.control.TextArea();
			
			try {
				inputStream = gymnasium.getFotoGymnasium().getBinaryStream();
				image = new Image(inputStream, 80, 80, false, false);
				imageView.setImage(image);
				
				namaGymnasium.setText(gymnasium.getNamaGymnasium());
				alamat.setText(gymnasium.getAlamatGymnasium());
				
				//positioning
				gp.add(namaGymnasium, 0, 0);
				gp.add(alamat, 0, 1);
				
				fp.getChildren().addAll(imageView, gp);
				
				hb.getChildren().add(fp);
				
				//style
				hb.setPrefWidth(310);
				
				imageView.setStyle("-fx-border-radius: 6px;");
				
				fp.setAlignment(Pos.TOP_LEFT);
				fp.setHgap(16);
				fp.setPadding(new Insets(5));
				
				namaGymnasium.setFont(namaFont);
				alamat.setFont(alamatFont);
				alamat.setDisable(true);
				alamat.setPrefSize(190, 80);
				alamat.setWrapText(true);
				
				//add to listview
				gymnasiumListView.getItems().add(hb);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void positioning() {
		backHB.getChildren().add(backImgView);
		
		fpHeader.getChildren().addAll(backHB, judulLbl);
		
		homeHB.getChildren().add(homeIV);
		tandingHB.getChildren().add(tandingIV);
		historyHB.getChildren().add(historyIV);
		forumHB.getChildren().add(forumIV);
		profileHB.getChildren().add(profileIV);
		
		menuHB.getChildren().addAll(homeHB, tandingHB, historyHB, forumHB, profileHB);
		
		bpMain.setTop(fpHeader);
		bpMain.setCenter(gymnasiumListView);
		bpMain.setBottom(menuHB);
	}
	
	private void style() {
		bpMain.setPrefWidth(390);
		bpMain.setPadding(new Insets(24, 0, 0, 0));
		bpMain.setMargin(gymnasiumListView, new Insets(24, 0, 0, 0));
		
		fpHeader.setAlignment(Pos.CENTER_LEFT);
		fpHeader.setPadding(new Insets(0, 0, 0, 24));
		fpHeader.setHgap(24);
		
		backHB.setAlignment(Pos.CENTER_LEFT);
				
		bpMain.setAlignment(judulLbl, Pos.CENTER);
		judulLbl.setFont(judulFont);
		judulLbl.setTextFill(Color.web("#458E5E"));
		
		gymnasiumListView.setMaxWidth(342);
		gymnasiumListView.getStylesheets().add("style.css");
		gymnasiumListView.getStyleClass().add("list-view-1");

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
			new Home(stage);
		});
		
		gymnasiumListView.setOnMouseClicked(e -> {
			new DetailLapangan(stage, dataGymnasium.get(gymnasiumListView.getSelectionModel().getSelectedIndex()));
		});
	}
	
	public DaftarGymnasium(Stage stage, int inputIdJenis) {
		idJenis = inputIdJenis;
		try {
			this.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage daftarGymansiumStage) throws Exception {		
		initialize();
		positioning();
		style();

		initializeListView();
		
		handler(daftarGymansiumStage);
		
		daftarGymansiumStage.setScene(scene);
	}

}
