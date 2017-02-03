package cdsf.repo;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import cdsf.domain.EmailLookup;
import cdsf.util.Db;

public class EmailLookupRepository {


	DatabaseReference emailLookupRef;
	String path;
	Map<String, EmailLookup> emailLookupMap = new HashMap<String, EmailLookup>();

	public EmailLookupRepository(String path) {
		emailLookupRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		emailLookupRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                EmailLookup emailLookup = dataSnapshot.getValue(EmailLookup.class);
                emailLookupMap.put(path+"/"+dataSnapshot.getKey(), emailLookup);
//                System.err.println(emailLookup);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                EmailLookup emailLookup = dataSnapshot.getValue(EmailLookup.class);
                emailLookupMap.put(path+"/"+dataSnapshot.getKey(), emailLookup);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				emailLookupMap.remove(path+"/"+dataSnapshot.getKey());
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
