package com.library.services;

import com.library.entities.dto.BookDto;
import com.library.entities.model.Book;
import com.library.exceptions.ObjectNotFoundException;
import com.library.modelMapper.MyModelMapper;
import com.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDto> findAll() {
        var books = bookRepository.findAll();
        return MyModelMapper.convertList(books, BookDto.class);
    }

    public BookDto findById(String id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book Not Found"));
        return MyModelMapper.convertValue(book, BookDto.class);
    }

    public BookDto save(BookDto bookDto) {
        var entity = MyModelMapper.convertValue(bookDto, Book.class);
        return MyModelMapper.convertValue(bookRepository.save(entity), BookDto.class);
    }

    public BookDto update(BookDto bookDto) {
        var entity = bookRepository.findById(bookDto.getId()).orElseThrow(() -> new ObjectNotFoundException("Book Not Found"));

        entity.setTitle(bookDto.getTitle());
        entity.setAuthor(bookDto.getAuthor());
        entity.setQuantity(bookDto.getQuantity());

        return MyModelMapper.convertValue(bookRepository.save(entity), BookDto.class);
    }

    public void delete(String id) {
        var entity = bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Book Not Found"));
        bookRepository.delete(entity);
    }
}
