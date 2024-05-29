package model;

public class Faq {
	private String idFaq, question, answer, idAdmin;

	public Faq(String idFaq, String question, String answer, String idAdmin) {
		super();
		this.idFaq = idFaq;
		this.question = question;
		this.answer = answer;
		this.idAdmin = idAdmin;
	}

	public String getIdFaq() {
		return idFaq;
	}

	public void setIdFaq(String idFaq) {
		this.idFaq = idFaq;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(String idAdmin) {
		this.idAdmin = idAdmin;
	}
}
