package ru.yandex.practicum.shareIt.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.shareIt.item.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByItemId(long itemId);
}
