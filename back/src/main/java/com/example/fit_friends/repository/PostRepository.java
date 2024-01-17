package com.example.fit_friends.repository;
import com.example.fit_friends.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(String category);

    @Query("select p from Post p join p.tag t where p.category = :category and (:ageType is null or t.ageType = :ageType) and (:levelType is null or t.levelType = :levelType) and (:genderType is null or t.genderType = :genderType)")
    List<Post> findPosts(String category, String levelType,
                         String ageType, String genderType);
}
