package friends.aidelivery.order.application;

import friends.aidelivery.common.infrastructure.security.UserDetailsImpl;
import friends.aidelivery.order.application.dto.response.OrderHistoryListResponse;
import friends.aidelivery.order.application.dto.response.OrderHistoryResponse;
import friends.aidelivery.order.domain.Order;
import friends.aidelivery.order.domain.OrderHistory;
import friends.aidelivery.order.domain.repository.OrderHistoryRepository;
import friends.aidelivery.order.exception.OrderHistoryForbiddenException;
import friends.aidelivery.order.exception.OrderHistoryNotFoundException;
import friends.aidelivery.store.domain.Store;
import friends.aidelivery.store.domain.repository.StoreRepository;
import friends.aidelivery.store.exception.StoreNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void saveOrderHistory(final Order order) {
        OrderHistory orderHistory = OrderHistory.create(order);
        orderHistoryRepository.save(orderHistory);
    }

    public OrderHistoryResponse getOrderHistoryForCustomer(final UserDetailsImpl userDetails,
        final UUID orderHistoryId) {
        final Long userId = userDetails.getUserId();
        final OrderHistory orderHistory = getOrderHistoryOrElseThrow(orderHistoryId);
        orderHistory.validateCustomer(userId);
        return OrderHistoryResponse.of(orderHistory);
    }

    public OrderHistoryListResponse searchOrderHistory(
        final UserDetailsImpl userDetails, final int page, final int size, final String sort,
        final String query) {
        final Long userId = userDetails.getUserId();
        final Pageable pageable = createPageable(page, size, sort);
        Page<OrderHistory> orderHistories = orderHistoryRepository.search(userId, pageable, query);
        return OrderHistoryListResponse.of(orderHistories);
    }

    public OrderHistory getOrderHistoryOrElseThrow(UUID orderHistoryId) {
        return orderHistoryRepository.findById(orderHistoryId)
            .orElseThrow(() -> new OrderHistoryNotFoundException(orderHistoryId));
    }

    public OrderHistoryListResponse getOrderHistoryListForOwner(final UserDetailsImpl userDetails,
        final UUID storeId, final int page, final int size, final String sort) {
        validateOwner(userDetails, storeId);
        final Pageable pageable = createPageable(page, size, sort);
        Page<OrderHistory> orderHistoryList = orderHistoryRepository.findAllByStoreId(storeId,
            pageable);
        return OrderHistoryListResponse.of(orderHistoryList);
    }

    private void validateOwner(UserDetailsImpl userDetails, UUID storeId) {
        final Long userId = userDetails.getUserId();
        final Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new StoreNotFoundException(storeId));
        if (!Objects.equals(store.getUser().getId(), userId)) {
            throw new OrderHistoryForbiddenException();
        }
    }

    private Pageable createPageable(final int page, final int size, final String sort) {

        String[] sortParams = sort.split(",");
        List<Sort.Order> orders = new ArrayList<>();

        for (String param : sortParams) {
            String[] fieldAndDirection = param.trim().split("-");
            if (fieldAndDirection.length != 2) {
                throw new IllegalArgumentException(
                    "Invalid sort parameter format. Expected 'field direction'.");
            }

            String field = fieldAndDirection[0];
            String direction = fieldAndDirection[1].toUpperCase();
            Direction dir = direction.equals("ASC") ? Direction.ASC : Direction.DESC;
            orders.add(new Sort.Order(dir, field));
        }

        Sort sortObj = Sort.by(orders);

        return PageRequest.of(page, size, sortObj);
    }
}
