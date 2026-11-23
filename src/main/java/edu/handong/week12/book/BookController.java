package edu.handong.week12.book;

import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> list() {
        return service.findAll();
    } // TODO: Pageable

    @GetMapping("/{id}")
    public Book get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public BookResponse create(@RequestBody BookRequest request) {
        Book book = new Book();

        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setPrice(request.price());

        Book saved = service.create(book);

        return BookResponse.from(saved);
    } // TODO: @Valid, ResponseEntity 201

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book input) {
        return service.update(id, input);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    } // TODO: 204
}
