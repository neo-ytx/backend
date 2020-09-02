package edu.fudan.backend.dao;

import edu.fudan.backend.model.EntityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Set;

public interface EntityRepository extends Neo4jRepository<EntityNode, Long> {
    List<EntityNode> findAllByNameAndLabels(String name, Set<String> Labels);
}
