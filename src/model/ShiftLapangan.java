package model;

import java.sql.Time;

public class ShiftLapangan {
	String idShiftLapangan;
	Time jamMulai, jamSelesai, durasi;
	
	public ShiftLapangan(String idShiftLapangan, Time jamMulai, Time jamSelesai, Time durasi) {
		super();
		this.idShiftLapangan = idShiftLapangan;
		this.jamMulai = jamMulai;
		this.jamSelesai = jamSelesai;
		this.durasi = durasi;
	}

	public String getIdShiftLapangan() {
		return idShiftLapangan;
	}

	public void setIdShiftLapangan(String idShiftLapangan) {
		this.idShiftLapangan = idShiftLapangan;
	}

	public Time getJamMulai() {
		return jamMulai;
	}

	public void setJamMulai(Time jamMulai) {
		this.jamMulai = jamMulai;
	}

	public Time getJamSelesai() {
		return jamSelesai;
	}

	public void setJamSelesai(Time jamSelesai) {
		this.jamSelesai = jamSelesai;
	}

	public Time getDurasi() {
		return durasi;
	}

	public void setDurasi(Time durasi) {
		this.durasi = durasi;
	}
	
	@Override
	public String toString() {
		return jamMulai + " - " + jamSelesai;
	}
}
