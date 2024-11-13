package com.example.basket.controller;

import com.example.basket.entity.BasketItemAddDTO;
import com.example.basket.entity.BasketItemDTO;
import com.example.basket.entity.BasketItems;
import com.example.basket.entity.Response;
import com.example.basket.exceptions.BasketItemDontExistException;
import com.example.basket.exceptions.NoBasketInfoException;
import com.example.basket.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@RequestBody BasketItemAddDTO basketItemAddDTO, HttpServletRequest request, HttpServletResponse response){
        return basketService.add(basketItemAddDTO,request,response);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Response> delete(@RequestParam String uuid,HttpServletRequest request){
        return basketService.delete(uuid,request);
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getItems(HttpServletRequest request){
        return basketService.getItems(request);
    }

    @ExceptionHandler(BasketItemDontExistException.class)
    private ResponseEntity<Response> handleException(BasketItemDontExistException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response(exception.getMessage()));
    }

    @ExceptionHandler(NoBasketInfoException.class)
    private ResponseEntity<Response> handleException(NoBasketInfoException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response(exception.getMessage()));
    }
}
