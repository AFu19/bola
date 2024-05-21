package model;

import java.sql.Blob;
import java.sql.Time;

public class Gymnasium {
	private String idGymnasium, idJenisLapangan, idPemilikGymnasium, namaGymnasium;
	private Blob fotoGymnasium;
	private String alamatGymnasium, deskripsiFasilitas;
	private int hargaGymnasium;
	private Time jamBuka, jamTutup;
	private int jumlahLapangan;
	private String statusGymnasium;
	
	public Gymnasium(String idGymnasium, String idJenisLapangan, String idPemilikGymnasium, String namaGymnasium,
			Blob fotoGymnasium, String alamatGymnasium, String deskrpsiFasilitas, int hargaGymnasium, Time jamBuka,
			Time jamTutup, int jumlahLapangan, String statusGymnasium) {
		super();
		this.idGymnasium = idGymnasium;
		this.idJenisLapangan = idJenisLapangan;
		this.idPemilikGymnasium = idPemilikGymnasium;
		this.namaGymnasium = namaGymnasium;
		this.fotoGymnasium = fotoGymnasium;
		this.alamatGymnasium = alamatGymnasium;
		this.deskripsiFasilitas = deskrpsiFasilitas;
		this.hargaGymnasium = hargaGymnasium;
		this.jamBuka = jamBuka;
		this.jamTutup = jamTutup;
		this.jumlahLapangan = jumlahLapangan;
		this.statusGymnasium = statusGymnasium;
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

	public String getIdPemilikGymnasium() {
		return idPemilikGymnasium;
	}

	public void setIdPemilikGymnasium(String idPemilikGymnasium) {
		this.idPemilikGymnasium = idPemilikGymnasium;
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

	public String getAlamatGymnasium() {
		return alamatGymnasium;
	}

	public void setAlamatGymnasium(String alamatGymnasium) {
		this.alamatGymnasium = alamatGymnasium;
	}

	public String getDeskrpsiFasilitas() {
		return deskripsiFasilitas;
	}

	public void setDeskrpsiFasilitas(String deskrpsiFasilitas) {
		this.deskripsiFasilitas = deskrpsiFasilitas;
	}

	public int getHargaGymnasium() {
		return hargaGymnasium;
	}

	public void setHargaGymnasium(int hargaGymnasium) {
		this.hargaGymnasium = hargaGymnasium;
	}

	public Time getJamBuka() {
		return jamBuka;
	}

	public void setJamBuka(Time jamBuka) {
		this.jamBuka = jamBuka;
	}

	public Time getJamTutup() {
		return jamTutup;
	}

	public void setJamTutup(Time jamTutup) {
		this.jamTutup = jamTutup;
	}

	public int getJumlahLapangan() {
		return jumlahLapangan;
	}

	public void setJumlahLapangan(int jumlahLapangan) {
		this.jumlahLapangan = jumlahLapangan;
	}

	public String getStatusGymnasium() {
		return statusGymnasium;
	}

	public void setStatusGymnasium(String statusGymnasium) {
		this.statusGymnasium = statusGymnasium;
	}
	
	
}
