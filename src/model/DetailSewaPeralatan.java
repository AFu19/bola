package model;

import java.sql.Time;

public class DetailSewaPeralatan {
	private String idBooking, idPeralatan;
	private int jumlah;
	private Time durasi;
	private String metodePembayaran;
	
	public DetailSewaPeralatan(String idBooking, String idPeralatan, int jumlah, Time durasi, String metodePembayaran) {
		super();
		this.idBooking = idBooking;
		this.idPeralatan = idPeralatan;
		this.jumlah = jumlah;
		this.durasi = durasi;
		this.metodePembayaran = metodePembayaran;
	}

	public String getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(String idBooking) {
		this.idBooking = idBooking;
	}

	public String getIdPeralatan() {
		return idPeralatan;
	}

	public void setIdPeralatan(String idPeralatan) {
		this.idPeralatan = idPeralatan;
	}

	public int getJumlah() {
		return jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}

	public Time getDurasi() {
		return durasi;
	}

	public void setDurasi(Time durasi) {
		this.durasi = durasi;
	}

	public String getMetodePembayaran() {
		return metodePembayaran;
	}

	public void setMetodePembayaran(String metodePembayaran) {
		this.metodePembayaran = metodePembayaran;
	}
	
}
