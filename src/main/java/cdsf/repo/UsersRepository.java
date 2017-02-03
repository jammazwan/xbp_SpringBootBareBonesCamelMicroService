package cdsf.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;

import cdsf.domain.User;
import cdsf.util.Db;

public class UsersRepository {
	DatabaseReference ref = Db.coRef("users");
	Map<String, UserRepository> userRepositoryMap = new HashMap<String, UserRepository>();

	public UsersRepository() {
		init();
	}

	private void init() {
		ref.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				UserRepository userRepository = new UserRepository(dataSnapshot.getKey());
				userRepositoryMap.put(dataSnapshot.getKey(), userRepository);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Map<String,User> users = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, User>>() {});
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				userRepositoryMap.remove(dataSnapshot.getKey());
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
				//don't even know what this would be for?
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				throw new RuntimeException(databaseError.getMessage());
			}
		});
	}
}
