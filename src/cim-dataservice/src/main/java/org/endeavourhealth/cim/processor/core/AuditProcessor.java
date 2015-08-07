package org.endeavourhealth.cim.processor.core;

import org.apache.camel.Exchange;
import org.apache.commons.io.IOUtils;
import org.endeavourhealth.cim.repository.audit.AuditRepository;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

public class AuditProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        String message = "";
        Object body = exchange.getIn().getBody();
        if (body instanceof String)
            message = (String)exchange.getIn().getBody();
        if (body instanceof InputStream)
            message = IOUtils.toString((InputStream)body);

        Date date = new Date();

        AuditRepository auditRepository = new AuditRepository();
        auditRepository.add(date, message);

        exchange.getIn().setBody(Arrays.asList(date, message));
    }
}