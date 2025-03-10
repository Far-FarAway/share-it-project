package ru.yandex.practicum.shareIt.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.shareIt.request.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query
    List<Request> findByUserIdOrderByCreationDateDesc(long userId);

    @Query
    List<Request> findByUserIdNotOrderByCreationDateDesc(long userId);
}
