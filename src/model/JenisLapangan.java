package model;

import java.sql.Blob;

public class JenisLapangan {
	private String namaJenisLapangan;
	private Blob foto;
	
	public JenisLapangan(String namaJenisLapangan, Blob foto) {
		this.namaJenisLapangan = namaJenisLapangan;
		this.foto = foto;
	}

	public String getNamaJenisLapangan() {
		return namaJenisLapangan;
	}

	public void setNamaJenisLapangan(String namaJenisLapangan) {
		this.namaJenisLapangan = namaJenisLapangan;
	}

	public Blob getFoto() {
		return foto;
	}

	public void setFoto(Blob foto) {
		this.foto = foto;
	}
	
	
}
