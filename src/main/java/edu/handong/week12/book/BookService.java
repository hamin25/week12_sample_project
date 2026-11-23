package edu.handong.week12.book;

import edu.handong.week12.book.dto.BookRequest;
import edu.handong.week12.book.dto.BookResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BookService {
  private final BookRepository repository;

  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  public Page<BookResponse> findAll(Pageable pageable) { //JpaRepository가 제공하는 기본 페이징 조회
    return repository.findAll(pageable)// 각 Book Entity를 DTO로 변환
            .map(BookResponse::from);

  } // TODO STEP 6: Pagination/Sorting


  public BookResponse findById(Long id) { // id로 도서 조회해 Response DTO로 반환하겠다.
    Book book = repository.findById(id).orElseThrow(()-> new BookNotFoundException((id))); // 값이 있다면 Book을 가져오고 없으면 예외 발생시키기, GlobalExceptionHandler의 상태코드 404로 응답
    return BookResponse.from(book); // DTO로 변환해 controller에 반환
  } // TODO STEP 5: 404


  public BookResponse create(BookRequest request) {

    Book book = new Book(request.title(),request.author(),request.price());
    Book saved = repository.save(book);
    return BookResponse.from(saved);

  } // TODO STEP 3: DTO

  public BookResponse update(Long id, BookRequest request) {
    Book book = repository.findById(id).orElseThrow(()->new BookNotFoundException((id)));
    book.update(request.title(), request.author(), request.price());
    return BookResponse.from(repository.save(book));
  }

  public void delete(Long id) {
    if(!repository.existsById(id)){
      throw new BookNotFoundException(id);
    }
    repository.deleteById(id);
  } // TODO: 404 if absent
}
