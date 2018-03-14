package pull.domain;

public class ReturningLog {
    private String emailAddress;
    private long timeStamp;

    public ReturningLog() {
        super();
    }

    public ReturningLog(String emailAddress, long timeStamp) {
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
