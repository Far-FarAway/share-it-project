package ru.yandex.practicum.shareIt.request;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.shareIt.user.User;

import java.time.Instant;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "requests")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    @Column
    String description;
    @ElementCollection
    @CollectionTable(name = "requests_items", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "item_id")
    Set<Long> itemIds;
    @Builder.Default
    @Column(name = "creation_date")
    Instant creationDate = Instant.now();
}
