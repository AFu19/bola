package model;

import java.sql.Blob;
import java.sql.Date;

public class HistoryObject {
	String idBooking;
	Date tanggalBooking;
	String idGymnasium;
	String namaGymnasium;
	Blob fotoGymnasium;
	
	public HistoryObject(String idBooking, Date tanggalBooking, String idGymnasium, String namaGymnasium,
			Blob fotoGymnasium) {
		super();
		this.idBooking = idBooking;
		this.tanggalBooking = tanggalBooking;
		this.idGymnasium = idGymnasium;
		this.namaGymnasium = namaGymnasium;
		this.fotoGymnasium = fotoGymnasium;
	}

	public String getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(String idBooking) {
		this.idBooking = idBooking;
	}

	public Date getTanggalBooking() {
		return tanggalBooking;
	}

	public void setTanggalBooking(Date tanggalBooking) {
		this.tanggalBooking = tanggalBooking;
	}

	public String getIdGymnasium() {
		return idGymnasium;
	}

	public void setIdGymnasium(String idGymnasium) {
		this.idGymnasium = idGymnasium;
	}

	public String getNamaGymnasium() {
		return namaGymnasium;
	}

	public void setNamaGymnasium(String namaGymnasium) {
		this.namaGymnasium = namaGymnasium;
	}

	public Blob getFotoGymnasium() {
		return fotoGymnasium;
	}

	public void setFotoGymnasium(Blob fotoGymnasium) {
		this.fotoGymnasium = fotoGymnasium;
	}
	
	
}
