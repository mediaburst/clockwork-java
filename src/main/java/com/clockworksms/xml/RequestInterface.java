package com.clockworksms.xml;

public interface RequestInterface {

	Class<? extends XmlRequest> getClassType();
	Class<? extends XmlResponse> getResponseClassType();
}
