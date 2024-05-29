package model;

public class DetailBooking {
	String idBooking, idLapangan, idShiftLapangan;

	public DetailBooking(String idBooking, String idLapangan, String idShiftLapangan) {
		super();
		this.idBooking = idBooking;
		this.idLapangan = idLapangan;
		this.idShiftLapangan = idShiftLapangan;
	}

	public String getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(String idBooking) {
		this.idBooking = idBooking;
	}

	public String getIdLapangan() {
		return idLapangan;
	}

	public void setIdLapangan(String idLapangan) {
		this.idLapangan = idLapangan;
	}

	public String getIdShiftLapangan() {
		return idShiftLapangan;
	}

	public void setIdShiftLapangan(String idShiftLapangan) {
		this.idShiftLapangan = idShiftLapangan;
	}
	
}
