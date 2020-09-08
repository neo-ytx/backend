package edu.fudan.backend.dao;

import edu.fudan.backend.model.EntityNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class EntityRepositoryTest {
    @Autowired
    private EntityRepository entityRepository;

    @Test
    void addNode() {
        EntityNode node1 = new EntityNode("中国", "Country");
        EntityNode node2 = new EntityNode("哈尔滨", "City");
        EntityNode node3 = new EntityNode("上海", "City");
        EntityNode node4 = new EntityNode("美国", "Country");
        EntityNode node5 = new EntityNode("纽约", "City");
        EntityNode node6 = new EntityNode("洛杉矶", "City");
        node1.addRelation(node2, "包含");
        node1.addRelation(node3, "包含");
        node4.addRelation(node5, "包含");
        node4.addRelation(node6, "包含");
        node1.addRelation(node4, "合作");
        List<EntityNode> list = new ArrayList<>();
        list.add(node1);
        list.add(node2);
        list.add(node3);
        list.add(node4);
        list.add(node5);
        list.add(node6);
        entityRepository.saveAll(list);
    }

    @Test
    void search() {
        Set<String> labels = new HashSet<>();
        labels.add("Country");
        List<EntityNode> list = entityRepository.findAllByNameAndLabels("中国", labels);
        for (EntityNode entityNode : list) {
            System.out.println(entityNode.getName());
        }
    }

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static void main(String[] args) {
    }
}