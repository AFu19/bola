package model;

import java.sql.Date;

public class ForumObject {
	private String idForum, idCustomer, judulForum, isiForum;
	private Date tanggalForum;
	
	public ForumObject(String idForum, String idCustomer, String judulForum, String isiForum, Date tanggalForum) {
		super();
		this.idForum = idForum;
		this.idCustomer = idCustomer;
		this.judulForum = judulForum;
		this.isiForum = isiForum;
		this.tanggalForum = tanggalForum;
	}

	public String getIdForum() {
		return idForum;
	}

	public void setIdForum(String idForum) {
		this.idForum = idForum;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getJudulForum() {
		return judulForum;
	}

	public void setJudulForum(String judulForum) {
		this.judulForum = judulForum;
	}

	public String getIsiForum() {
		return isiForum;
	}

	public void setIsiForum(String isiForum) {
		this.isiForum = isiForum;
	}

	public Date getTanggalForum() {
		return tanggalForum;
	}

	public void setTanggalForum(Date tanggalForum) {
		this.tanggalForum = tanggalForum;
	}
}
