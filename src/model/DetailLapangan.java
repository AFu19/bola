package model;

public class DetailLapangan {
	private String idLapangan, idGymnasium, namaLapangan;
	private int kapasitas;
	
	public DetailLapangan(String idLapangan, String idGymnasium, String namaLapangan, int kapasitas) {
		super();
		this.idLapangan = idLapangan;
		this.idGymnasium = idGymnasium;
		this.namaLapangan = namaLapangan;
		this.kapasitas = kapasitas;
	}

	public String getIdLapangan() {
		return idLapangan;
	}

	public void setIdLapangan(String idLapangan) {
		this.idLapangan = idLapangan;
	}

	public String getIdGymnasium() {
		return idGymnasium;
	}

	public void setIdGymnasium(String idGymnasium) {
		this.idGymnasium = idGymnasium;
	}

	public String getNamaLapangan() {
		return namaLapangan;
	}

	public void setNamaLapangan(String namaLapangan) {
		this.namaLapangan = namaLapangan;
	}

	public int getKapasitas() {
		return kapasitas;
	}

	public void setKapasitas(int kapasitas) {
		this.kapasitas = kapasitas;
	}
	
	@Override
	public String toString() {
		return namaLapangan;
	}
	
}
