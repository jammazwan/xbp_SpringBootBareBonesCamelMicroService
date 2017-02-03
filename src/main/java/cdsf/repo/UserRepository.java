package cdsf.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import cdsf.service.WriteCampaignToProps;
import cdsf.util.Db;

public class UserRepository {
	DatabaseReference ref;
	Map<String, CampaignRepository> campaignRepositoryMap = new HashMap<String, CampaignRepository>();
	Map<String, EmailContentRepository> emailContentRepositoryMap = new HashMap<String, EmailContentRepository>();
	Map<String, EmailLookupRepository> emailLookupRepositoryMap = new HashMap<String, EmailLookupRepository>();
	String userKey;

	@Autowired
	WriteCampaignToProps writeCampaignToProps;

	public UserRepository(String key) {
		this.userKey = key;
		ref = Db.coRef("users/" + key);
		init();
	}

	private void init() {

		// if(writeCampaignToProps==null){
		// throw new RuntimeException("sorry");
		// }
		ref.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				dispatch(dataSnapshot.getKey());
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				dispatch(dataSnapshot.getKey());
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				throw new RuntimeException(databaseError.getMessage());
			}
		});
	}

	private void dispatch(String key) {
		String path = "users/" + userKey + "/" + key;
		switch (key) {
		case "campaign":
			if (!campaignRepositoryMap.containsKey(path)) {
				campaignRepositoryMap.put(path, new CampaignRepository(path));
			}
			break;
		case "campaignName":
			// not interested
			break;
		case "emailLookup":
			if (!emailLookupRepositoryMap.containsKey(path)) {
				emailLookupRepositoryMap.put(path, new EmailLookupRepository(path));
			}

			break;
		case "emailContent":
			if (!emailContentRepositoryMap.containsKey(path)) {
				emailContentRepositoryMap.put(path, new EmailContentRepository(path));
			}
			break;
		default:
			System.err.println("DIDN'T LOOK FOR " + key + " PLEASE FIX ME");
		}
	}
}
