package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.httpclient.HttpStatus;
import org.endeavourhealth.cim.IRegistry;
import org.endeavourhealth.cim.Registry;
import org.endeavourhealth.cim.exceptions.SessionException;

public class SecurityProcessor implements Processor {
	private IRegistry _registry = new Registry();

    public void process(Exchange exchange) throws Exception {
        String publicKey = (String)exchange.getIn().getHeader("api_key");
        String inboundHash = (String)exchange.getIn().getHeader("hash");
        String method = exchange.getFromEndpoint().getEndpointConfiguration().getParameter("path");
        String body = exchange.getIn().getBody(String.class);

        if (!_registry.validateMessage(publicKey, method, body, inboundHash)) {
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.SC_UNAUTHORIZED);
            exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");
            exchange.getIn().setBody("Invalid Session");
            throw new SessionException("Invalid session");
        }
    }
}
