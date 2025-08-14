package com.linkup.dao;




import com.linkup.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    Long countByPostId(Long postId);

    List<Like> findByPostId(Long postId);

    void deleteByUserIdAndPostId(Long userId, Long postId);
}




//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.linkup.model.Like;
//
//public interface LikeRepository extends JpaRepository<Like, Long> {
//    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);
//    int countByPostId(Long postId);
//	boolean existsByUserIdAndPostId(Long userId, Long postId);
//}
