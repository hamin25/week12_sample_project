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

    @Operation(summary = "도서목록 조회", description = "페이지 조회")//Swagger 문서에 API 설명 추가
    @GetMapping//URL요청 메서드에 연결
    public ResponseEntity<Page<BookResponse>> list( // 현재 페이지 데이터 + 페이징 정보 정의
            @PageableDefault(size=5, sort="id", direction = Sort.Direction.ASC) Pageable pageable){ // 기본값은 5개씩 Id 오름차순으로하겠다
        Page<BookResponse> saved = service.findAll(pageable); // 페이징 정렬 조건을 service에 전달, DTO로 변환된 page 받기
        return ResponseEntity.ok(saved);// page를 Json으로 반환
    } // TODO: Pageable

    @Operation(summary = "단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable Long id) {
        BookResponse result = service.findById(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "도서 등록")// POST 요청을 여기 연결
    @PostMapping//POST 형식
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) { //BookResponse형태로 상태코드, 헤더, 본문 직접 지정해 반환받겠다. 또한 받는 요청 validation 과정 거칠거다.실패 시, 오류발생
        BookResponse saved = service.create(request); //서비스에 request 보내서 DTO형태로 데이터 받아오기
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved); //새 데이터 들어왔을 때, 상태코드 201로 지정, 응답본문 JSON으로 변 //
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
