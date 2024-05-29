package model;

import java.sql.Date;

public class BookingLapangan {
	private String idBooking, idCustomer;
	private Date tanggalBooking;
	private String statusBooking, metodePembayaran;
	
	public BookingLapangan(String idBooking, String idCustomer, Date tanggalBooking, String statusBooking,
			String metodePembayaran) {
		super();
		this.idBooking = idBooking;
		this.idCustomer = idCustomer;
		this.tanggalBooking = tanggalBooking;
		this.statusBooking = statusBooking;
		this.metodePembayaran = metodePembayaran;
	}
	
	public String getIdBooking() {
		return idBooking;
	}
	public void setIdBooking(String idBooking) {
		this.idBooking = idBooking;
	}
	public String getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}
	public Date getTanggalBooking() {
		return tanggalBooking;
	}
	public void setTanggalBooking(Date tanggalBooking) {
		this.tanggalBooking = tanggalBooking;
	}
	public String getStatusBooking() {
		return statusBooking;
	}
	public void setStatusBooking(String statusBooking) {
		this.statusBooking = statusBooking;
	}
	public String getMetodePembayaran() {
		return metodePembayaran;
	}
	public void setMetodePembayaran(String metodePembayaran) {
		this.metodePembayaran = metodePembayaran;
	}
	
	
}
