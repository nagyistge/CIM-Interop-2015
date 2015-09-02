package org.endeavourhealth.cim.routes.routeBuilders.endpoints;

import org.endeavourhealth.cim.common.HeaderKey;
import org.endeavourhealth.cim.common.HttpVerb;
import org.endeavourhealth.cim.processor.administrative.GetOrganisationProcessor;
import org.endeavourhealth.cim.routes.common.CIMRouteBuilder;
import org.endeavourhealth.cim.routes.common.Route;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationEndpoint extends CIMRouteBuilder {

    @Override
    public void configureRoute() throws Exception {

        final String BASE_ROUTE = "/{odsCode}";
        final String ID_ROUTE = "";

        final String ID_PROCESSOR_ROUTE = "GetOrganisation";

        rest(BASE_ROUTE)

        .get(ID_ROUTE)
            .route()
            .routeId(HttpVerb.GET + BASE_ROUTE + ID_ROUTE)
            .setHeader(HeaderKey.MessageRouterCallback, constant(Route.direct(ID_PROCESSOR_ROUTE)))
            .to(Route.CIM_CORE)
        .endRest();

        from(Route.direct(ID_PROCESSOR_ROUTE))
            .routeId(ID_PROCESSOR_ROUTE)
            .process(new GetOrganisationProcessor());
    }
}
