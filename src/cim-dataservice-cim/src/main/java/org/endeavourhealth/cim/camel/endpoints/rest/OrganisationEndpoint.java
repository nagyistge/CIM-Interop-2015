package org.endeavourhealth.cim.camel.endpoints.rest;

import org.endeavourhealth.cim.camel.routes.OrganisationRoutes;
import org.endeavourhealth.common.core.HttpVerb;
import org.endeavourhealth.common.core.BaseRouteBuilder;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrganisationEndpoint extends BaseRouteBuilder {

    @Override
    public void configureRoute() throws Exception {
        final String BASE_PATH = "/{odsCode}";

        rest(BASE_PATH)

        .get()
            .route()
            .routeId(HttpVerb.GET + BASE_PATH)
            .to(BaseRouteBuilder.direct(OrganisationRoutes.GET_ORGANISATION_ROUTE))
        .endRest();
    }
}