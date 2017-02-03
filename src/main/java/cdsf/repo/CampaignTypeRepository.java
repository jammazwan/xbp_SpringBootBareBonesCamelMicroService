package cdsf.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import cdsf.domain.CampaignType;
import cdsf.domain.CampaignTypeFaq;
import cdsf.util.Db;

public class CampaignTypeRepository {
	DatabaseReference campaignTypeRef = Db.coRef("campaignType");
	DatabaseReference campaignTypeFaqRef = Db.coRef("campaignTypeFaq");
	Map<String, CampaignType> map = new HashMap<String, CampaignType>();

	public CampaignTypeRepository() {
		init();
	}

	private void init() {
		campaignTypeRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				CampaignType campaignType = dataSnapshot.getValue(CampaignType.class);
				map.put(dataSnapshot.getKey(), campaignType);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				CampaignType campaignType = dataSnapshot.getValue(CampaignType.class);
				map.put(dataSnapshot.getKey(), campaignType);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				map.remove(dataSnapshot.getKey());
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

	public void add(String name, String faq, boolean hasLeadCapturePage, boolean hasSalesPage,
			boolean hasOptionalVslPage) {
		DatabaseReference newCampaignRef = campaignTypeRef.push();
		newCampaignRef.setValue(new CampaignType(name, hasLeadCapturePage, hasSalesPage, hasOptionalVslPage));
		campaignTypeFaqRef.child(name).setValue(new CampaignTypeFaq(faq));
	}

	public List<CampaignType> getList() {
		return (List<CampaignType>) map.values();
	}

	public Map<String, CampaignType> getMap() {
		return map;
	}

	public void update(String key, CampaignType campaignType) {
		campaignTypeRef.child(key).setPriority(campaignType);
	}

	public void delete(String key) {
		campaignTypeRef.child(key).removeValue();
	}

}

// ref.addValueEventListener(new ValueEventListener() {
// @Override
// public void onDataChange(DataSnapshot dataSnapshot) {
// CampaignType campaignType = dataSnapshot.getValue(CampaignType.class);
// list.add(campaignType);
// }
//
// @Override
// public void onCancelled(DatabaseError databaseError) {
// System.err.println("The read failed: " + databaseError.getCode());
// }
// });
