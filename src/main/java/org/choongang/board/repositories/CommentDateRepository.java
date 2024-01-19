package org.choongang.board.repositories;

import org.choongang.board.entities.CommentDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentDateRepository extends JpaRepository<CommentDate, Long>, QuerydslPredicateExecutor<CommentDate> {
}
