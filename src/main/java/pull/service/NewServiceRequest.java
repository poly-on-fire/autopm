package pull.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pull.domain.Profile;
//import pull.repo.SubmitRoleRepo;
import pull.repo.ServiceActionRepo;
import pull.util.SendEmail;

@Service
public class NewServiceRequest {
	@Autowired
	ServiceActionRepo serviceActionRepo;
//	@Autowired
//	SubmitRoleRepo submitRoleRepo;
	@Autowired
	SendEmail sendEmail;
	@Value("${run.naked}")
	private boolean runNaked;
	@Value("${EMAIL_FROM}")
	private String emailFrom;


	public void make(Profile profile, String path) {
		if (profile.promotion && !runNaked) {
			try {
				sendEmail.go(emailFrom, "Support at pullModel.com" ,  "pete@datafundamentals.com" ,
						"A user has requested a promotion \nhttps://console.firebase.google.com/project/pullmodel-5d998/database/pullmodel-5d998/data/"+ path ,"User Promotion");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
//			TODO: something below, but not this
//			submitRoleRepo.addUser(profile);
		}
	}
}
