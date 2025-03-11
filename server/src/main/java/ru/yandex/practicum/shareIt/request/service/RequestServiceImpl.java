package ru.yandex.practicum.shareIt.request.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.shareIt.exception.NotFoundException;
import ru.yandex.practicum.shareIt.request.Request;
import ru.yandex.practicum.shareIt.request.dto.RequestDto;
import ru.yandex.practicum.shareIt.request.mapper.RequestMapper;
import ru.yandex.practicum.shareIt.request.repository.RequestRepository;
import ru.yandex.practicum.shareIt.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestServiceImpl implements RequestService {
    RequestRepository repository;
    UserRepository userRepository;
    RequestMapper mapper;

    @Override
    public RequestDto postRequest(RequestDto dto, long userId) {
        Request request = mapper.mapPOJO(dto);
        request.setUser(userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Пользователь с id: " + userId + " не найден")));

        return mapper.mapDto(repository.save(request));
    }

    @Override
    public List<RequestDto> getUserRequests(long userId) {
        return repository.findByUserIdOrderByCreationDateDesc(userId).stream()
                .map(mapper::mapDto)
                .toList();
    }

    @Override
    public List<RequestDto> getAllRequests(long userId) {
        return repository.findByUserIdNotOrderByCreationDateDesc(userId).stream()
                .map(mapper::mapDto)
                .toList();
    }

    @Override
    public RequestDto getRequestById(long requestId) {
        return mapper.mapDto(repository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Предмет с id: " + requestId + " не найден")));
    }
}
