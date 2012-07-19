package com.clockworksms.xml;

public abstract class XmlRequest implements RequestInterface {
	
	public Class<? extends XmlRequest> getClassType() {
		return this.getClass();
	}
}
