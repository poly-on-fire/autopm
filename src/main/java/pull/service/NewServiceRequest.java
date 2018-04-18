package pull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pull.domain.Profile;
import pull.repo.NewUserRepo;
import pull.repo.ServiceActionRepo;
import pull.util.SendEmail;

@Service
public class NewServiceRequest {
	@Autowired
	ServiceActionRepo serviceActionRepo;
	@Autowired
	NewUserRepo newUserRepo;
	@Autowired
	SendEmail sendEmail;
	@Value("${run.naked}")
	private boolean runNaked;
	@Value("${EMAIL_FROM}")
	private String emailFrom;


	public void make(Profile profile) {
		if (profile.promotion && !runNaked) {
			try {
				sendEmail.go(emailFrom, "Support at pullModel.com" ,  "mary@appwriter.com" ,
						"You have a new user to approve" ,"New User");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			newUserRepo.addUser(profile);
		}
	}
}
