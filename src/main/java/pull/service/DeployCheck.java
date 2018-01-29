package pull.service;

import org.springframework.stereotype.Service;

@Service
public class DeployCheck {
	
	private boolean hasPageToDeploy = false;

	public DeployCheck() {
		super();
	}
	
	public void setDeploymentFlag(boolean flag){
		hasPageToDeploy = flag;
	}
	
	public boolean hasPageToDeploy(){
		return hasPageToDeploy;
	}

}
