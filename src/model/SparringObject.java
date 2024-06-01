package model;

import java.sql.Date;

public class SparringObject {
	private String idSparring, idBooking, idGymnasium, idJenisLapangan, idCustomer;
	private int minPemain, maxPemain;
	private Date tanggalBooking;
	
	public SparringObject(String idSparring, String idBooking, String idGymnasium, String idJenisLapangan,
			String idCustomer, int minPemain, int maxPemain, Date tanggalBooking) {
		super();
		this.idSparring = idSparring;
		this.idBooking = idBooking;
		this.idGymnasium = idGymnasium;
		this.idJenisLapangan = idJenisLapangan;
		this.idCustomer = idCustomer;
		this.minPemain = minPemain;
		this.maxPemain = maxPemain;
		this.tanggalBooking = tanggalBooking;
	}
	
	public String getIdSparring() {
		return idSparring;
	}
	public void setIdSparring(String idSparring) {
		this.idSparring = idSparring;
	}
	public String getIdBooking() {
		return idBooking;
	}
	public void setIdBooking(String idBooking) {
		this.idBooking = idBooking;
	}
	public String getIdGymnasium() {
		return idGymnasium;
	}
	public void setIdGymnasium(String idGymnasium) {
		this.idGymnasium = idGymnasium;
	}
	public String getIdJenisLapangan() {
		return idJenisLapangan;
	}
	public void setIdJenisLapangan(String idJenisLapangan) {
		this.idJenisLapangan = idJenisLapangan;
	}
	public String getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}
	public int getMinPemain() {
		return minPemain;
	}
	public void setMinPemain(int minPemain) {
		this.minPemain = minPemain;
	}
	public int getMaxPemain() {
		return maxPemain;
	}
	public void setMaxPemain(int maxPemain) {
		this.maxPemain = maxPemain;
	}
	public Date getTanggalBooking() {
		return tanggalBooking;
	}
	public void setTanggalBooking(Date tanggalBooking) {
		this.tanggalBooking = tanggalBooking;
	}
}
