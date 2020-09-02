package edu.fudan.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NodeEntity
public class EntityNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Labels
    private Set<String> labels;

    @Relationship(type = "RelationEdge")
    private Set<RelationNode> sets = new HashSet<>();

    public EntityNode(String name, String label) {
        this.name = name;
        this.labels = new HashSet<>();
        this.labels.add(label);
    }

    public void addRelation(EntityNode endEntity, String name) {
        RelationNode relationNode = new RelationNode(this, name, endEntity);
        sets.add(relationNode);
        endEntity.
                getSets().
                add(relationNode);
    }
}
