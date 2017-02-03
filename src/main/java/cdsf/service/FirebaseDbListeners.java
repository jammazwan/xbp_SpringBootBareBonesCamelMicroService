package cdsf.service;

import org.springframework.stereotype.Service;

import cdsf.repo.UsersRepository;

@Service
public class FirebaseDbListeners {
	
	public void go(){
		UsersRepository ur = new UsersRepository();
//		new DbCodeReference().go();
//		new InitializeCampaignType().go();
//		new InitializeProductCategory().go();
	}

}
