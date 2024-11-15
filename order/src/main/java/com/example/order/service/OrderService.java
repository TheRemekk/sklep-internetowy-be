package com.example.order.service;


import com.example.order.entity.*;
import com.example.order.exception.BasketDontExistException;
import com.example.order.exception.EmptyBasketException;
import com.example.order.exception.OrderDontExistException;
import com.example.order.exception.UknowDeliverTypException;
import com.example.order.repository.DeliverRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.translators.BasketItemDTOToOrderItems;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliverRepository deliverRepository;
    private final BasketService basketService;
    private final ItemService itemService;
    private final PayuService payuService;
    private final BasketItemDTOToOrderItems basketItemDTOToItems;
    private final EmailService emailService;
    private final AuthService authService;


    private Order save(Order order) {
        Deliver deliver = deliverRepository.findByUuid(order.getDeliver().getUuid()).orElseThrow(UknowDeliverTypException::new);
        StringBuilder stringBuilder = new StringBuilder("ORDER/")
                .append(orderRepository.count())
                .append("/")
                .append(LocalDate.now().getMonthValue())
                .append("/")
                .append(LocalDate.now().getYear());

        order.setUuid(UUID.randomUUID().toString());
        order.setStatus(Status.PENDING);
        order.setOrders(stringBuilder.toString());
        order.setDeliver(deliver);
        return orderRepository.saveAndFlush(order);
    }

    @Transactional
    public String createOrder(Order order, HttpServletRequest request, HttpServletResponse response) {
        List<Cookie> cookies = Arrays.stream(request.getCookies()).filter(value->
                        value.getName().equals("Authorization") || value.getName().equals("refresh"))
                .toList();

        UserRegisterDTO userRegisterDTO = authService.getUserDetails(cookies);
        if (userRegisterDTO != null) {
            order.setClient(userRegisterDTO.getLogin());
        }
        Order finalOrder = save(order);
        AtomicReference<String> result = new AtomicReference<>();
        Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("basket")).findFirst().ifPresentOrElse(value -> {
            ListBasketItemDTO basket = basketService.getBasket(value);
            if (basket.getBasketProducts().isEmpty()) throw new EmptyBasketException();
            List<OrderItems> items = new ArrayList<>();
            basket.getBasketProducts().forEach(item -> {
                OrderItems orderItems = basketItemDTOToItems.toOrderItems(item);
                orderItems.setOrder(finalOrder);
                orderItems.setUuid(UUID.randomUUID().toString());
                items.add(itemService.save(orderItems));
                basketService.removeBasket(value,item.getUuid());
            });
            result.set(payuService.createOrder(finalOrder, items));
            value.setMaxAge(0);
            response.addCookie(value);
            emailService.sendActivation(order.getEmail(),order.getUuid());
        }, () -> {
            throw new BasketDontExistException();
        });
        return result.get();
    }

    public void completeOrder(com.example.order.entity.notify.Notify notify)throws OrderDontExistException{
        orderRepository.findOrderByOrders(notify.getOrder().getExtOrderId()).ifPresentOrElse(value->{
            value.setStatus(notify.getOrder().getStatus());
            orderRepository.save(value);
        },()->{
         throw new OrderDontExistException();
        });
    }

    public Order getOrderByUuid(String uuid) {
       return orderRepository.findOrderByUuid(uuid).orElseThrow(OrderDontExistException::new);
    }

    public List<Order> getOrdersByClient(String login) {
        return orderRepository.findOrderByClient(login);
    }
}
