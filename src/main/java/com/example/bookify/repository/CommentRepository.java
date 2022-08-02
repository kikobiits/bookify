package com.example.bookify.repository;

import com.example.bookify.model.entity.Comment;
import com.example.bookify.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findAllByOffer(Offer offer);
}
