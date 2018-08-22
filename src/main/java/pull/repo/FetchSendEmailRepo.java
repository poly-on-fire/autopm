package pull.repo;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import pull.domain.EmailContent;
import pull.domain.EmailOut;
import pull.domain.EmailStart;
import pull.util.Db;
import pull.util.SendEmail;

public class FetchSendEmailRepo {

    private DatabaseReference emailContentRef;
    private EmailStart emailStart;
    private SendEmail sendEmail;
    private String emailFrom;

    @SuppressWarnings("unused")
    private FetchSendEmailRepo() {
        super();
    }

    public FetchSendEmailRepo(String emailFrom, EmailOut emailOut, EmailStart emailStart, SendEmail sendEmail) {
        super();
        this.emailFrom = emailFrom;
        this.sendEmail = sendEmail;
        this.emailStart = emailStart;
        emailContentRef = Db.coRef(cleanPath(emailOut));
        init();
    }

    private String cleanPath(EmailOut emailOut) {
        String path = emailOut.getEmailKey();
        if (path.contains("emailMeta")) {
            path = path.replaceAll("emailMeta", "emailContent");
        }
        return path;
    }

    private void init() {
        emailContentRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError arg0) {
            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EmailContent emailContent = dataSnapshot.getValue(EmailContent.class);
                try {
                    sendEmail.go(emailFrom, System.getenv("EMAIL_FROM_NAME"), emailStart.getEmailAddress(),
                            emailContent.getEmailBody(), emailContent.getSubject());
                } catch (Exception e) {
                    //TODO real error handling
                    System.err.println("WOOPS");
                    e.printStackTrace();
                }

            }
        });
    }

}
