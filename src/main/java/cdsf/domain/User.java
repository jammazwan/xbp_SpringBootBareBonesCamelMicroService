package cdsf.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
//	private HashMap<String, Object> campaign;
//	private HashMap<String, Object> campaignName;
//	private HashMap<String, Object> emailContent;
//	private HashMap<String, Object> emailLookup;

    public String campaign;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String campaign) {
        this.campaign = campaign;
    }

	@Override
	public String toString() {
		return "User [campaign=" + campaign + "]";
	}

}