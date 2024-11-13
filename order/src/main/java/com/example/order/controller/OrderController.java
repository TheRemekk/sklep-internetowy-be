package com.example.order.controller;


import com.example.order.entity.OrderDTO;
import com.example.order.entity.Response;
import com.example.order.entity.notify.Notify;
import com.example.order.entity.notify.Order;
import com.example.order.exception.*;
import com.example.order.mediator.OrderMediator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMediator orderMediator;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO order, HttpServletRequest request, HttpServletResponse response){
        return orderMediator.createOrder(order,request,response);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/notification")
    public ResponseEntity<Response> notifyOrder(@RequestBody Notify notify, HttpServletRequest request){
            return orderMediator.handleNotify(notify,request);
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestParam(required = false) String uuid,HttpServletRequest request){
        return orderMediator.getOrder(uuid,request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyBasketException.class)
    public Response handleValidationExceptions(EmptyBasketException ex){
        return new Response("Basket is empty");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BasketDontExistException.class)
    public Response handleValidationExceptions(BasketDontExistException ex){
        return new Response("Basket dont exist for this session");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PayUException.class)
    public Response handleValidationExceptions(PayUException ex){
        return new Response("Server error contact with administrator");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UknowDeliverTypException.class)
    public Response handleValidationExceptions(UknowDeliverTypException ex){
        return new Response("Deliver don't exist with this uuid");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserDontLoginException.class)
    public Response handleValidationExceptions(UserDontLoginException ex){
        return new Response("User is not logged in");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderDontExistException.class)
    public Response handleValidationExceptions(OrderDontExistException ex){
        return new Response("Order don't exist");
    }
}
