package cdsf.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import cdsf.domain.Campaign;

@Service
public class WriteCampaignToProps {

	public WriteCampaignToProps() {
		super();
	}

	public synchronized void go(String key, Campaign campaign) {

		boolean hasNecessaryStuff = false;
		boolean isGeneratingType = false;
		if (isField(campaign.getCuriousHeadline()) && isField(campaign.getCuriousPost())
				&& isField(campaign.getCuriousPre()) && isField(campaign.getCuriousVideo())
				&& isField(campaign.getEngagedHeadline()) && isField(campaign.getEngagedPre())
				&& isField(campaign.getEngagedPost()) && isField(campaign.getEngagedVideo())) {
			hasNecessaryStuff = true;
		}
		String type = campaign.getCampaignType();
		if (type.contains("OTG") || type.contains("Kuchel") || type.contains("London")) {
			isGeneratingType = true;
		}
		if (isGeneratingType && hasNecessaryStuff) {
			writePropertiesFile(key, campaign);
		} else if (isGeneratingType && !hasNecessaryStuff) {
			printProblemCampaign(campaign);
		}
	}

	private void writePropertiesFile(String key, Campaign campaign) {
		Properties properties = parseCampaignIntoProperties(campaign);
		writeFile(properties, key);
	}

	void writeFile(Properties properties, String key) {
		try {
			File file = new File("propertyFiles/" +key + ".properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, key + " campaign");
			fileOut.close();
			System.err.println("WROTE PROPERTIES FILE " + file.getAbsolutePath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	Properties parseCampaignIntoProperties(Campaign campaign) {
		Properties properties = new Properties();
		properties.put("campaignType", campaign.getCampaignType());
		properties.put("name", campaign.getName());
		properties.put("category", campaign.getCategory());
		properties.put("timeStamp", ""+campaign.getTimestamp());
		properties.put("curiousHeadline", campaign.getCuriousHeadline());
		properties.put("curiousPre", campaign.getCuriousPre());
		properties.put("curiousVideo", campaign.getCuriousVideo());
		properties.put("curiousPost", campaign.getCuriousPost());
		properties.put("engagedHeadline", campaign.getEngagedHeadline());
		properties.put("engagedPre", campaign.getEngagedPre());
		properties.put("engagedVideo", campaign.getEngagedVideo());
		properties.put("engagedPost", campaign.getEngagedPost());
		return properties;
	}

	String cleanFieldOfVelocityCharachters(String string) {
		string = string.replaceAll(Pattern.quote("${"), "-[");
		string = string.replaceAll(Pattern.quote("{"), "[");
		string = string.replaceAll(Pattern.quote("}"), "]");
		return string;
	}

	private boolean isField(String string) {
		if (string != null && string.length() > 1) {
			return true;
		} else {
			return false;
		}
	}

	private void printProblemCampaign(Campaign campaign) {
		System.err.println("LOOK HERE FOR SOMETHING NULL THAT SHOULD NOT BE");
		System.err.println("1" + campaign.getCuriousHeadline());
		System.err.println("2" + campaign.getCuriousPre());
		System.err.println("3" + campaign.getCuriousVideo());
		System.err.println("4" + campaign.getCuriousPost());
		System.err.println("5" + campaign.getEngagedHeadline());
		System.err.println("6" + campaign.getEngagedPre());
		System.err.println("7" + campaign.getEngagedVideo());
		System.err.println("8" + campaign.getEngagedPost());
	}
}
