package model;

import java.sql.Date;

public class ReplyForum {
	private String idReplyForum, idForum, idCustomer, isiReplyForum;
	private Date tanggalReplyForum;
	
	public ReplyForum(String idReplyForum, String idForum, String idCustomer, String isiReplyForum,
			Date tanggalReplyForum) {
		super();
		this.idReplyForum = idReplyForum;
		this.idForum = idForum;
		this.idCustomer = idCustomer;
		this.isiReplyForum = isiReplyForum;
		this.tanggalReplyForum = tanggalReplyForum;
	}
	
	public String getIdReplyForum() {
		return idReplyForum;
	}
	public void setIdReplyForum(String idReplyForum) {
		this.idReplyForum = idReplyForum;
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
	public String getIsiReplyForum() {
		return isiReplyForum;
	}
	public void setIsiReplyForum(String isiReplyForum) {
		this.isiReplyForum = isiReplyForum;
	}
	public Date getTanggalReplyForum() {
		return tanggalReplyForum;
	}
	public void setTanggalReplyForum(Date tanggalReplyForum) {
		this.tanggalReplyForum = tanggalReplyForum;
	}
}
