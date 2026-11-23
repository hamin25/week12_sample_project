package edu.handong.week12.book;

import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@Tag(name="Book", description = "book API")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @Operation(summary = "도서목록 조회", description = "페이지 조회")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> list(
            @PageableDefault(size=5, sort="id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<BookResponse> saved = service.findAll(pageable);
        return ResponseEntity.ok(saved);
    } // TODO: Pageable

    @Operation(summary = "단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable Long id) {
        BookResponse result = service.findById(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "도서 등록")
    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse saved = service.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    } // TODO: @Valid, ResponseEntity 201

    @Operation(summary = "도서 수정")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        BookResponse saved = service.update(id,request);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "도서 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    } // TODO: 204
}
