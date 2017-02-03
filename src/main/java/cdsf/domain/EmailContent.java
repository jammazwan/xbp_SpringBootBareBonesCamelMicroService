package cdsf.domain;

public class EmailContent {
	public String campaign;
	public String day;
	public String subject;
	public String emailtext;
	
	public EmailContent(){
		super();
	}

	@Override
	public String toString() {
		return "EmailContent [campaign=" + campaign + ", day=" + day + ", subject=" + subject + ", emailtext="
				+ emailtext + "]";
	}
	
	

}