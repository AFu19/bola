package model;

public class Review {
	private String idReview, idBooking;
	private int rating;
	private String deskripsiReview;
	
	public Review(String idReview, String idBooking, int rating, String deskripsiReview) {
		super();
		this.idReview = idReview;
		this.idBooking = idBooking;
		this.rating = rating;
		this.deskripsiReview = deskripsiReview;
	}
	
	public String getIdReview() {
		return idReview;
	}
	public void setIdReview(String idReview) {
		this.idReview = idReview;
	}
	public String getIdBooking() {
		return idBooking;
	}
	public void setIdBooking(String idBooking) {
		this.idBooking = idBooking;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getDeskripsiReview() {
		return deskripsiReview;
	}
	public void setDeskripsiReview(String deskripsiReview) {
		this.deskripsiReview = deskripsiReview;
	}
	
}
