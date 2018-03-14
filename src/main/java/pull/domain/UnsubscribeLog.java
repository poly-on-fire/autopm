package pull.domain;

public class UnsubscribeLog {
    private String emailAddress;
    private long timeStamp;

    public UnsubscribeLog() {
        super();
    }

    public UnsubscribeLog(String emailAddress, long timeStamp) {
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
