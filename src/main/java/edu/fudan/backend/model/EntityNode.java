package edu.fudan.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NodeEntity(label = "Entity")
public class EntityNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "type")
    private String type;

    @Relationship(type = "RelationEdge")
    private Set<RelationNode> sets = new HashSet<>();

    public EntityNode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void addRelation(EntityNode endEntity, String name) {
        RelationNode relationNode = new RelationNode(this, name, endEntity);
        sets.add(relationNode);
        endEntity.
                getSets().
                add(relationNode);
    }
}
