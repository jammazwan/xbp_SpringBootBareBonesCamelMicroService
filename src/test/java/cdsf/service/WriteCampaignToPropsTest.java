package cdsf.service;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

import cdsf.domain.Campaign;
import cdsf.service.WriteCampaignToProps;

public class WriteCampaignToPropsTest {

	@Test
	public void testCleanFieldOfVelocityCharachters() {
		String string = "My good buddy has ${lots of help} geting implemented";
		String returnValue = new WriteCampaignToProps().cleanFieldOfVelocityCharachters(string);
		assertEquals("My good buddy has -[lots of help] geting implemented", returnValue);
		string = "My good buddy has {more help} geting tested";
		returnValue = new WriteCampaignToProps().cleanFieldOfVelocityCharachters(string);
		assertEquals("My good buddy has [more help] geting tested", returnValue);
	}

	@Test
	public void testParseCampaignIntoProperties() {
		Campaign campaign = new Campaign("campaignType", "name", "category", 1234567890, "curiousHeadline",
				"curiousPre", "curiousVideo", "curiousPost", "engagedHeadline", "engagedPre", "engagedVideo",
				"engagedPost");
	    Properties properties = new WriteCampaignToProps().parseCampaignIntoProperties(campaign);
	    assertEquals(properties.getProperty("curiousPre"), "curiousPre");
	}

}
