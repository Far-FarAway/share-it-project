package ru.yandex.practicum.shareIt.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.shareIt.user.User;

@Data
@Builder
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "item_name")
    private String name;
    @Column
    private String description;
    @Column
    private Boolean available;
    @Column(name = "book_count")
    private Integer bookCount;
}
