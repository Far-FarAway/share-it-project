package ru.yandex.practicum.shareIt.item.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.yandex.practicum.shareIt.booking.repository.BookingRepository;
import ru.yandex.practicum.shareIt.item.Comment;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.dto.CommentDto;
import ru.yandex.practicum.shareIt.item.dto.ItemDto;
import ru.yandex.practicum.shareIt.item.mapper.CommentMapper;
import ru.yandex.practicum.shareIt.item.mapper.ItemMapper;
import ru.yandex.practicum.shareIt.item.repository.CommentRepository;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.request.repository.RequestRepository;
import ru.yandex.practicum.shareIt.user.User;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ItemServiceUnitTest {
    @Mock
    ItemRepository itemRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    BookingRepository bookingRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    RequestRepository requestRepository;
    ItemService service;

    User user;
    Item item;
    ItemDto itemDto;

    @BeforeEach
    void before() {
        user = User.builder()
                .id(3L)
                .name("Shrek")
                .email("shrekIsLove@gmail.com")
                .build();

        item = Item.builder()
                .id(3L)
                .user(user)
                .name("Shrexy pants")
                .description("No words are needed")
                .available(true)
                .build();

        itemDto = ItemDto.builder()
                .id(3L)
                .name("carbonara")
                .description("Yummy")
                .available(true)
                .build();

        service = new ItemServiceImpl(itemRepository, userRepository, bookingRepository, commentRepository,
                requestRepository, new CommentMapper(), new ItemMapper());

    }

    @Test
    void testDelete() {
        Mockito.when(itemRepository.checkItemOwner(Mockito.anyLong()))
                .thenReturn(item.getUser().getId());

        service.deleteItem(user.getId(), item.getId());

        Mockito.verify(itemRepository, Mockito.times(1))
                .deleteById(item.getId());
    }

    @Test
    void testUpdate() {
        Mockito.when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(item));
        Mockito.when(itemRepository.checkItemOwner(Mockito.anyLong()))
                .thenReturn(itemDto.getId());
        Mockito.when(itemRepository.save(Mockito.any(Item.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ItemDto updateDto = ItemDto.builder().name("Ultra shrexy Shrek").build();

        ItemDto resp = service.updateItem(item.getUser().getId(), item.getId(), updateDto);

        assertThat(resp.getName(), is(updateDto.getName()));
    }

    @Test
    void testPostComment() {
        Mockito.when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(user));
        Mockito.when(itemRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(item));
        Mockito.when(bookingRepository.existsValidBookingForAddComment(Mockito.anyLong()))
                .thenReturn(true);
        Mockito.when(commentRepository.save(Mockito.any(Comment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        CommentDto comment = CommentDto.builder().text("Danila it's amazing").build();

        CommentDto resp = service.addComment(item.getUser().getId(), item.getId(), comment);

        assertThat(resp.getText(), is(comment.getText()));
        assertThat(resp.getAuthorName(), is(user.getName()));
    }
}