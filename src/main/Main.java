package main;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{

	private Scene scene;
	private StackPane sp;
	private Image img;
	private ImageView imgView;
	
	private void initialize() {
		sp = new StackPane();
		img = new Image("LOGO_BOLA.png");
		imgView = new ImageView(img);
		
		scene = new Scene(sp, 390, 800);
	}
	
	private void positioning() {
		sp.getChildren().add(imgView);
	}
	
	private void style() {
		sp.setStyle("-fx-background-color: #4FCA79");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage initialStage) throws Exception {
//		new DaftarGymnasium(initialStage, 1);
		new SignUp(initialStage);
		
//		initialize();
//		positioning();
//		style();
//		changePage(initialStage);
//		
//		initialStage.setResizable(false);
//		initialStage.setMaximized(false);
//		initialStage.setScene(scene);
		initialStage.show();
	}
	
	private void changePage(Stage stage) {
		Timeline delayTimeline = new Timeline(new javafx.animation.KeyFrame(Duration.seconds(3), event -> {
            new Landing(stage);
        }));
        delayTimeline.play();
	}

}
