package pull.domain;

public class AbuseLog {
    private String abuseDescription;
    private String emailAddress;
    private long timeStamp;

    public AbuseLog() {
        super();
    }

    public AbuseLog(String abuseDescription, String emailAddress, long timeStamp) {
        this.abuseDescription = abuseDescription;
        this.emailAddress = emailAddress;
        this.timeStamp = timeStamp;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
