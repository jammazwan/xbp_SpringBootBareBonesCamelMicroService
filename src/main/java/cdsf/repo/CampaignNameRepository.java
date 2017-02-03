package cdsf.repo;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import cdsf.domain.Campaign;
import cdsf.domain.CampaignName;
import cdsf.util.Db;

public class CampaignNameRepository {
	DatabaseReference campaignNameRef;
	String path;
	Map<String, CampaignName> campaignNameMap = new HashMap<String, CampaignName>();

	public CampaignNameRepository(String path) {
		campaignNameRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		campaignNameRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                CampaignName campaignName = dataSnapshot.getValue(CampaignName.class);
                campaignNameMap.put(path+"/"+dataSnapshot.getKey(), campaignName);
                System.err.println(campaignName);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				CampaignName campaignName = dataSnapshot.getValue(CampaignName.class);
                campaignNameMap.put(path+"/"+dataSnapshot.getKey(), campaignName);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				campaignNameMap.remove(path+"/"+dataSnapshot.getKey());
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
}