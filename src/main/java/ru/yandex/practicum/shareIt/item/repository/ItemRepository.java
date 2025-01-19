package ru.yandex.practicum.shareIt.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.shareIt.item.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUserId(long userId);

    @Query("SELECT i " +
            "FROM Item i " +
            "WHERE (LOWER(i.name) LIKE LOWER(%?1%) OR LOWER(i.description) LIKE LOWER(%?1%)) AND " +
            "i.available = TRUE")
    List<Item> findByNameContainingOrDescriptionContaining(String text);

    @Query("SELECT i.user.id " +
            "FROM Item i " +
            "WHERE i.id = ?1")
    long checkItemOwner(long itemId);
}
