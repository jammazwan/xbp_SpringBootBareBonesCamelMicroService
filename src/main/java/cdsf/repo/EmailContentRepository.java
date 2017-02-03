package cdsf.repo;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import cdsf.domain.EmailContent;
import cdsf.util.Db;

public class EmailContentRepository {

	DatabaseReference emailContentRef;
	String path;
	Map<String, EmailContent> emailContentMap = new HashMap<String, EmailContent>();

	public EmailContentRepository(String path) {
		emailContentRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		emailContentRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                EmailContent emailContent = dataSnapshot.getValue(EmailContent.class);
                emailContentMap.put(path+"/"+dataSnapshot.getKey(), emailContent);
//                System.err.println(emailContent);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                EmailContent emailContent = dataSnapshot.getValue(EmailContent.class);
                emailContentMap.put(path+"/"+dataSnapshot.getKey(), emailContent);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				emailContentMap.remove(path+"/"+dataSnapshot.getKey());
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
