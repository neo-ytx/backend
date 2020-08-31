package edu.fudan.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "RelationEdge")
@Getter
@Setter
public class RelationNode {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private EntityNode startNode;

    private String name;

    @EndNode
    private EntityNode endNode;

    RelationNode(EntityNode startNode, String name, EntityNode endNode) {
        this.startNode = startNode;
        this.name = name;
        this.endNode = endNode;
    }
}
