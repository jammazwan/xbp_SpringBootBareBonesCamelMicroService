package cdsf.repo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import cdsf.domain.Campaign;
import cdsf.service.WriteCampaignToProps;
import cdsf.util.AppCtxt;
import cdsf.util.Db;

public class CampaignRepository {
	DatabaseReference campaignRef;
	DatabaseReference campaignTypeFaqRef = Db.coRef("campaignTypeFaq");
	String path;
	Map<String, Campaign> campaignMap = new HashMap<String, Campaign>();

	/*
	 * Don't know why I couldn't get autowire to work but something to fix on
	 * another day
	 */
	@Autowired
	private WriteCampaignToProps writeCampaignToProps;

	public CampaignRepository(String path) {
		super();
		campaignRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		if (writeCampaignToProps == null) {
			writeCampaignToProps = (WriteCampaignToProps) AppCtxt.getInstance().get("writeCampaignToProps");
			System.err.println("AUTOWIRE FAILED BUT NOW " + writeCampaignToProps);
		}
		campaignRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				System.err.println("ADDING "+ dataSnapshot.getKey());
				if (!campaignMap.containsKey(pathKey(dataSnapshot))) {
					Campaign campaign = dataSnapshot.getValue(Campaign.class);
					campaignMap.put(pathKey(dataSnapshot), campaign);
					writeCampaignToProps.go(dataSnapshot.getKey(), campaign);
				}
				System.err.println("NOW "+ campaignMap.size());
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				Campaign campaign = dataSnapshot.getValue(Campaign.class);
				campaignMap.put(pathKey(dataSnapshot), campaign);
				System.err.println("CHANGING "+ dataSnapshot.getKey());
				writeCampaignToProps.go(dataSnapshot.getKey(), campaign);
				System.err.println("NOW "+ campaignMap.size());
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				campaignMap.remove(pathKey(dataSnapshot));
				System.err.println("NOW "+ campaignMap.size());
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
				//TODO no idea what use case is here
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				throw new RuntimeException(databaseError.getMessage());
			}
		});
	}
	
	private String pathKey(DataSnapshot dataSnapshot){
		return path + "/" + dataSnapshot.getKey();
	}
}