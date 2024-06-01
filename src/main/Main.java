package main;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BookingLapangan;
import model.Gymnasium;
import model.Lomba;
import util.Connect;

public class Main extends Application{

	private Scene scene;
	private StackPane sp;
	private Image img;
	private ImageView imgView;
	
	private Connect connect = Connect.getInstance();
	private ArrayList<model.BookingLapangan> dataBooking = new ArrayList<>();
	private ArrayList<Lomba> dataLomba = new ArrayList<>();
	
	private void getBooking() {
		String query = "SELECT * FROM bookinglapangan WHERE statusBooking = 'Confirmed'";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idBooking = connect.rs.getString("idBooking");
				String idCustomer = connect.rs.getString("idCustomer");
				Date tanggalBooking = connect.rs.getDate("tanggalBooking");
				String statusBooking = connect.rs.getString("statusBooking");
				String metodePembayaran = connect.rs.getString("metodePembayaran");
				
				dataBooking.add(new BookingLapangan(idBooking, idCustomer, tanggalBooking, statusBooking, metodePembayaran));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Time getjamSelesai(String idBooking) {
		String query = String.format("SELECT MAX(sl.jamSelesai) as jamSelesai FROM shiftlapangan sl, detailbooking db WHERE sl.idShiftLapangan = db.idShiftLapangan AND db.idBooking = '%s'", idBooking);
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				return connect.rs.getTime("jamSelesai");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void updateBooking(String idBooking) {
		String query = String.format("UPDATE bookinglapangan SET statusBooking = 'Completed' WHERE idBooking = '%s'", idBooking);
		connect.execUpdate(query);
	}
	
	private void getLomba() {
		dataLomba.clear();
		
		String query = "SELECT * FROM lomba WHERE status = 'Open'";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String idLomba = connect.rs.getString("idLomba");
				String idAdmin = connect.rs.getString("idAdmin");
				String namaLomba = connect.rs.getString("namaLomba");
				Date tanggalLomba = connect.rs.getDate("tanggalLomba");
				Time waktuLomba = connect.rs.getTime("waktuLomba");
				String lokasiLomba = connect.rs.getString("lokasiLomba");
				int prizepool = connect.rs.getInt("prizepool");
				int hargaPendaftaran = connect.rs.getInt("hargaPendaftaran");
				String namaContactPerson = connect.rs.getString("namaContactPerson");
				String telfonContactPerson = connect.rs.getString("telfonContactPerson");
				int jumlahMaxPartis = connect.rs.getInt("jumlahMaxPartis");
				String status = connect.rs.getString("status");
				
				dataLomba.add(new Lomba(idLomba, idAdmin, namaLomba, tanggalLomba, waktuLomba, lokasiLomba, prizepool, hargaPendaftaran, namaContactPerson, telfonContactPerson, jumlahMaxPartis, status));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateLomba(String idLomba) {
		String query = String.format("UPDATE lomba SET status = 'Closed' WHERE idLomba = '%s'", idLomba);
		connect.execUpdate(query);
	}
	
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
		getBooking();
		getLomba();
		
		for (BookingLapangan bookingLapangan : dataBooking) {
			Date date = Date.valueOf(LocalDate.now());
			LocalTime time = LocalTime.now();
			
			if (bookingLapangan.getTanggalBooking().before(date)) {
				updateBooking(bookingLapangan.getIdBooking());
			}else if (bookingLapangan.getTanggalBooking().equals(date) && getjamSelesai(bookingLapangan.getIdBooking()).toLocalTime().isBefore(time)) {
				updateBooking(bookingLapangan.getIdBooking());
			}
		}
		
		for (Lomba lomba : dataLomba) {
			Date date = Date.valueOf(LocalDate.now());
			LocalTime time = LocalTime.now();
			
			if (lomba.getTanggalLomba().before(date)) {
				updateLomba(lomba.getIdLomba());
			}else if (lomba.getTanggalLomba().equals(date) && lomba.getWaktuLomba().toLocalTime().isBefore(time)) {
				updateLomba(lomba.getIdLomba());
			}
		}
		
		initialize();
		positioning();
		style();
		changePage(initialStage);
		
		initialStage.setResizable(false);
		initialStage.setMaximized(false);
		initialStage.setScene(scene);
		initialStage.show();
	}
	
	private void changePage(Stage stage) {
		Timeline delayTimeline = new Timeline(new javafx.animation.KeyFrame(Duration.seconds(3), event -> {
            new Landing(stage);
        }));
        delayTimeline.play();
	}

}
