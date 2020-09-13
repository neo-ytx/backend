package edu.fudan.backend.dao;

import edu.fudan.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByTopicId(Integer topicId);
}
