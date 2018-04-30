package pull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pull.domain.Profile;
//import pull.repo.SubmitRoleRepo;
import pull.repo.ServiceActionRepo;
import pull.util.SendEmail;
// TODO: this class is used now only to notify admin when a profile needs promotion and is thus both mis named and also the params need to be 12factored because specific to me
@Service
public class NewServiceRequest {
	@Autowired
	ServiceActionRepo serviceActionRepo;
	@Autowired
	SendEmail sendEmail;
	@Value("${run.naked}")
	private boolean runNaked;
	@Value("${EMAIL_FROM}")
	private String emailFrom;


	public void make(Profile profile, String path) {
		if (profile.promotion && !runNaked) {
			try {
				sendEmail.go(emailFrom, System.getenv("EMAIL_FROM_NAME") , System.getenv("EMAIL_ADMIN") ,
						"A user has requested a promotion \nhttps://console.firebase.google.com/project/pullmodel-5d998/database/pullmodel-5d998/data/"+ path ,"User Promotion");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
//			TODO: something below, but not this
		}
	}
}
