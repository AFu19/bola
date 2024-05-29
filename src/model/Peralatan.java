package model;

import java.sql.Blob;

public class Peralatan {
	private String idPeralatan, idGymnasium, namaPeralatan;
	private int hargaPeralatan, stockPeralatan;
	private Blob fotoPeralatan;
	
	public Peralatan(String idPeralatan, String idGymnasium, String namaPeralatan, int hargaPeralatan,
			int stockPeralatan, Blob fotoPeralatan) {
		super();
		this.idPeralatan = idPeralatan;
		this.idGymnasium = idGymnasium;
		this.namaPeralatan = namaPeralatan;
		this.hargaPeralatan = hargaPeralatan;
		this.stockPeralatan = stockPeralatan;
		this.fotoPeralatan = fotoPeralatan;
	}
	
	public String getIdPeralatan() {
		return idPeralatan;
	}
	public void setIdPeralatan(String idPeralatan) {
		this.idPeralatan = idPeralatan;
	}
	public String getIdGymnasium() {
		return idGymnasium;
	}
	public void setIdGymnasium(String idGymnasium) {
		this.idGymnasium = idGymnasium;
	}
	public String getNamaPeralatan() {
		return namaPeralatan;
	}
	public void setNamaPeralatan(String namaPeralatan) {
		this.namaPeralatan = namaPeralatan;
	}
	public int getHargaPeralatan() {
		return hargaPeralatan;
	}
	public void setHargaPeralatan(int hargaPeralatan) {
		this.hargaPeralatan = hargaPeralatan;
	}
	public int getStockPeralatan() {
		return stockPeralatan;
	}
	public void setStockPeralatan(int stockPeralatan) {
		this.stockPeralatan = stockPeralatan;
	}
	public Blob getFotoPeralatan() {
		return fotoPeralatan;
	}
	public void setFotoPeralatan(Blob fotoPeralatan) {
		this.fotoPeralatan = fotoPeralatan;
	}
	
	
}
