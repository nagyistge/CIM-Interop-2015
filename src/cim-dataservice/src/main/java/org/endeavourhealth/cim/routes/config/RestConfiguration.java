package org.endeavourhealth.cim.routes.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.camel.spi.ManagementAgent;
import org.apache.camel.spi.ManagementStrategy;

@SuppressWarnings("WeakerAccess")
public class RestConfiguration extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        onException(Exception.class)
            .to(Route.direct(CoreRouteName.CIM_CRITICAL_ERROR))
            .stop();

        // enable debug output
        getContext().setTracing(true);
        getContext().setAllowUseOriginalMessage(false);
		ManagementStrategy strat = getContext().getManagementStrategy();
		ManagementAgent agt = strat.getManagementAgent();
		if (agt != null) {
			agt.setRegistryPort(8888);
			agt.setCreateConnector(true);
		}

		/*
        Tracer tracer = new Tracer();
        tracer.getDefaultTraceFormatter().setShowBreadCrumb(false);
        tracer.getDefaultTraceFormatter().setShowExchangePattern(false);
        tracer.getDefaultTraceFormatter().setShowHeaders(false);
        tracer.getDefaultTraceFormatter().setShowBody(false);
        tracer.getDefaultTraceFormatter().setShowBodyType(false);
        tracer.getDefaultTraceFormatter().setShowNode(false);

        getContext().addInterceptStrategy(tracer); */

        restConfiguration().component("servlet")
                .bindingMode(RestBindingMode.off)
                .dataFormatProperty("prettyPrint", "true");
    }
}
