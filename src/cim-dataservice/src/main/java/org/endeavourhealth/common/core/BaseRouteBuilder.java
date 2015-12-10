package org.endeavourhealth.common.core;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.endeavourhealth.common.camel.QueueReader;
import org.endeavourhealth.core.repository.common.data.RepositoryException;
import org.endeavourhealth.core.repository.rabbit.RabbitConfig;
import org.endeavourhealth.core.repository.rabbit.ConfigRepository;
import org.endeavourhealth.core.serializer.DeserializationException;

public abstract class BaseRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .to(direct(ComponentRouteName.EXCEPTION_HANDLER))
                .handled(true);

        configureRoute();
    }

	protected static String direct(String routeName) {
		return "direct:"+routeName;
	}

	protected String queued(String routeName) throws RepositoryException, DeserializationException {
		String inboundQueueRoute = routeName+"Queue";

		from(direct(inboundQueueRoute))
			.routeId(inboundQueueRoute)
			.setHeader("rabbitmq.ROUTING_KEY",
					xpath("/f:Bundle/f:entry/f:resource/f:MessageHeader/f:source/f:extension[@url=\"http://endeavour-health.org/fhir/StructureDefinition/endeavour-identifier-extension\"]/f:valueString/@value", String.class)
							.namespace("f", "http://hl7.org/fhir"))
			.setHeader(QueueReader.DESTINATION_ROUTE, constant(direct(routeName)))
			.to(rabbitQueueWriter());

		return direct(inboundQueueRoute);
	}

	public RouteDefinition buildWrappedRoute(String coreName, String routeName) {
		String wrappedRoute = routeName + "Internal";

		from(direct(routeName))
				.routeId(routeName)
				.setProperty(HeaderKey.WrappedRouteCallback, constant(direct(wrappedRoute)))
				.to(direct(coreName));

		return from(direct(wrappedRoute))
				.routeId(wrappedRoute);
	}

    public abstract void configureRoute() throws Exception;

	protected String rabbitQueueWriter() throws RepositoryException, DeserializationException {
		RabbitConfig rabbitConfig = ConfigRepository.getInstance().getConfigByName("rabbit", RabbitConfig.class);
		final String RMQ_EXCHANGE = rabbitConfig.getUri() + "/" + rabbitConfig.getExchange();
		final String RMQ_OPTIONS = "?autoAck=false&autoDelete=false&automaticRecoveryEnabled=true&durable=true&"+rabbitConfig.getUsernamePassword();
		final String RMQ_ROUTING = "&queue=DeadLetter&routingKey=DeadLetter";

		return "rabbitmq://" + RMQ_EXCHANGE + RMQ_OPTIONS + RMQ_ROUTING;
	}

}
