package pull.domain;

public class EmailContent {
	private String emailBody;
	private String subject;

	public EmailContent() {
		super();
	}

	public EmailContent(String emailBody) {
		super();
		this.emailBody = emailBody;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


}