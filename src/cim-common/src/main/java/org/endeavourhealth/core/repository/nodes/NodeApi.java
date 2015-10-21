package org.endeavourhealth.core.repository.nodes;

import org.endeavourhealth.core.repository.nodes.data.NodeRepository;
import org.endeavourhealth.core.repository.nodes.data.ServiceNodeLinkRepository;
import org.endeavourhealth.core.repository.nodes.models.InboundQueueNode;
import org.endeavourhealth.core.repository.nodes.models.Node;
import org.endeavourhealth.core.repository.nodes.models.NodePurpose;
import org.endeavourhealth.core.repository.nodes.models.ServiceNodeLinks;
import org.endeavourhealth.core.repository.common.data.RepositoryException;

import java.util.UUID;

public class NodeApi {

    public static InboundQueueNode GetServicesInboundQueueConfig(UUID serviceId) throws InternalProcessingException {

        try {

            ServiceNodeLinkRepository serviceNodeLinkRepository = new ServiceNodeLinkRepository();
            ServiceNodeLinks links = serviceNodeLinkRepository.getNodesByServiceId(serviceId);

            if (links == null)
                return null;

            ServiceNodeLinks.ServiceNodeLink nodeLink = links.find(NodePurpose.InboundQueue);

            NodeRepository nodeRepository = new NodeRepository();
            Node rawNode = nodeRepository.getById(nodeLink.getNodePurpose(), nodeLink.getNodeId());

            if (rawNode == null)
                throw new RepositoryException("Node not found.  NodePurpose: " + nodeLink.getNodePurpose().toString() + " NodeId: " + nodeLink.getNodeId().toString());

            return (InboundQueueNode) rawNode;

        } catch (Exception e) {
            throw new InternalProcessingException("Exception thrown during processing service ID: " + serviceId.toString(), e);
        }
    }
}
