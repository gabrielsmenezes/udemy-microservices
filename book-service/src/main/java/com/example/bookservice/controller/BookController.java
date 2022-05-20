package com.example.bookservice.controller;

import com.example.bookservice.model.Book;
import com.example.bookservice.proxy.CambioProxy;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.response.Cambio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CambioProxy proxy;

    @GetMapping("/{id}/{currency}")
    public Book findBook(@PathVariable Long id, @PathVariable String currency) {
        String port = this.environment.getProperty("local.server.port");

        Book book = this.bookRepository.findById(id).orElseThrow(RuntimeException::new);
        Cambio cambio = proxy.getCambio(book.getPrice(), "USD", currency);

        book.setEnvironment(port);
        book.setPrice(cambio.getConvertedValue());
        return book;
    }

}
