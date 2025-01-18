package ru.yandex.practicum.shareIt.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.shareIt.item.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUserId(long userId);

    List<Item> findByNameContainingOrDescriptionContaining(String text);

    @Query("SELECT i.user.id " +
            "FROM items i" +
            "WHERE i.id = ?1")
    long checkItemOwner(long itemId);
}
