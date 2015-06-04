package org.endeavourhealth.cim.processor.subscription;

import org.apache.camel.Exchange;
import org.endeavourhealth.cim.adapter.AdapterFactory;
import org.endeavourhealth.cim.adapter.IDataAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class PollerProcessor implements org.apache.camel.Processor {
    private static PollerProcessor _instance = null;

    public static PollerProcessor getInstance() {
        if (_instance == null)
            _instance = new PollerProcessor();

        return _instance;
    }

    private ArrayList<String> _services = new ArrayList<>();
    private HashMap<UUID, PollingSubscription> _subscriptions = new HashMap<>();

    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<String> subscriberCallbacks = new ArrayList<>();
        subscriberCallbacks.add("log:SubscriberCallback1");
        subscriberCallbacks.add("log:SubscriberCallback2");
        subscriberCallbacks.add("log:SubscriberCallback3");

        exchange.getIn().setHeader("Callbacks", subscriberCallbacks);
    }

    public void addSubscription(UUID subscriptionId, String odsCode, String message) {
        PollingSubscription subscription = new PollingSubscription(subscriptionId, odsCode, message);
        _subscriptions.put(subscriptionId, subscription);

        if (_services.contains(odsCode) == false)
            _services.add(odsCode);
    }
}
