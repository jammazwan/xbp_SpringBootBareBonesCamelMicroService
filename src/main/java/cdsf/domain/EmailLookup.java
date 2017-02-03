package cdsf.domain;

public class EmailLookup {
	public String campaignKey;
	public String campaignName;
	public String campaignType;
	public String extract;
	public String productCategory;

	public EmailLookup() {
		super();
	}

	@Override
	public String toString() {
		return "EmailLookup [campaignKey=" + campaignKey + ", campaignName=" + campaignName + ", campaignType="
				+ campaignType + ", extract=" + extract + ", productCategory=" + productCategory + "]";
	}

}