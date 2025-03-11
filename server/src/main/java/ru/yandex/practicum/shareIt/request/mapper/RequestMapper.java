package ru.yandex.practicum.shareIt.request.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.item.Item;
import ru.yandex.practicum.shareIt.item.repository.ItemRepository;
import ru.yandex.practicum.shareIt.request.ItemResponse;
import ru.yandex.practicum.shareIt.request.Request;
import ru.yandex.practicum.shareIt.request.dto.RequestDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RequestMapper {
    private final ItemRepository repository;

    public RequestDto mapDto(Request request) {
        List<ItemResponse> items = new ArrayList<>();
        if (request.getItemIds() != null && !request.getItemIds().isEmpty()) {
            items = request.getItemIds().stream()
                    .map(id -> {
                        Item item = repository.findById(id).
                                orElseThrow(() -> new NotFoundException("Предмет с id: " + id + " не найден"));
                        return ItemResponse.builder()
                                .itemId(item.getId())
                                .userId(item.getUser().getId())
                                .name(item.getName())
                                .description(item.getDescription())
                                .build();
                    })
                    .toList();
        }

        return RequestDto.builder()
                .id(request.getId())
                .description(request.getDescription())
                .items(items)
                .created(request.getCreationDate())
                .build();
    }

    public Request mapPOJO(RequestDto dto) {
        Set<Long> itemIds = new HashSet<>();
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            itemIds = dto.getItems().stream()
                    .map(ItemResponse::getItemId)
                    .collect(Collectors.toSet());
        }

        return Request.builder()
                .description(dto.getDescription())
                .itemIds(itemIds)
                .build();
    }
}
