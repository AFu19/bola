package model;

import java.sql.Date;
import java.sql.Time;

public class Lomba {
	private String idLomba, idAdmin, namaLomba;
	private Date tanggalLomba;
	private Time waktuLomba;
	private String lokasiLomba;
	private int prizepool, hargaPendaftaran;
	private String namaContactPerson, telfonContactPerson;
	private int jumlahMaxPartis;
	private String status;
	
	public Lomba(String idLomba, String idAdmin, String namaLomba, Date tanggalLomba, Time waktuLomba,
			String lokasiLomba, int prizepool, int hargaPendaftaran, String namaContactPerson,
			String telfonContactPerson, int jumlahMaxPartis, String status) {
		super();
		this.idLomba = idLomba;
		this.idAdmin = idAdmin;
		this.namaLomba = namaLomba;
		this.tanggalLomba = tanggalLomba;
		this.waktuLomba = waktuLomba;
		this.lokasiLomba = lokasiLomba;
		this.prizepool = prizepool;
		this.hargaPendaftaran = hargaPendaftaran;
		this.namaContactPerson = namaContactPerson;
		this.telfonContactPerson = telfonContactPerson;
		this.jumlahMaxPartis = jumlahMaxPartis;
		this.status = status;
	}

	public String getIdLomba() {
		return idLomba;
	}

	public void setIdLomba(String idLomba) {
		this.idLomba = idLomba;
	}

	public String getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(String idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getNamaLomba() {
		return namaLomba;
	}

	public void setNamaLomba(String namaLomba) {
		this.namaLomba = namaLomba;
	}

	public Date getTanggalLomba() {
		return tanggalLomba;
	}

	public void setTanggalLomba(Date tanggalLomba) {
		this.tanggalLomba = tanggalLomba;
	}

	public Time getWaktuLomba() {
		return waktuLomba;
	}

	public void setWaktuLomba(Time waktuLomba) {
		this.waktuLomba = waktuLomba;
	}

	public String getLokasiLomba() {
		return lokasiLomba;
	}

	public void setLokasiLomba(String lokasiLomba) {
		this.lokasiLomba = lokasiLomba;
	}

	public int getPrizepool() {
		return prizepool;
	}

	public void setPrizepool(int prizepool) {
		this.prizepool = prizepool;
	}

	public int getHargaPendaftaran() {
		return hargaPendaftaran;
	}

	public void setHargaPendaftaran(int hargaPendaftaran) {
		this.hargaPendaftaran = hargaPendaftaran;
	}

	public String getNamaContactPerson() {
		return namaContactPerson;
	}

	public void setNamaContactPerson(String namaContactPerson) {
		this.namaContactPerson = namaContactPerson;
	}

	public String getTelfonContactPerson() {
		return telfonContactPerson;
	}

	public void setTelfonContactPerson(String telfonContactPerson) {
		this.telfonContactPerson = telfonContactPerson;
	}

	public int getJumlahMaxPartis() {
		return jumlahMaxPartis;
	}

	public void setJumlahMaxPartis(int jumlahMaxPartis) {
		this.jumlahMaxPartis = jumlahMaxPartis;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
