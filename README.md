22500354 성하민

① Source Code Analysis 

Project Structure & JPA 
1. BookController, BookService, BookRepository의 역할과 요청 처리 흐름을 설명하세요.
: Controller는 HTTP를 받는 입구로 URL과 메서드를 매핑해 각 요청에 맞는 service의 메서드를 부르고 Service는 비즈니스 로직을 담당해 repository에서 CRUD 기능을 부르고 객체 형태로 controller에 전달한다. 
    repositor는 실제 DB에 접근하는 역할로 이번 프로젝트는 JpaRepository를 확장해서 사용해 따로 선언하지 않아도 기본 기능을 사용할 수 있다.
2. JpaRepository<Book, Long>만 선언해도 findAll(), save()를 사용할 수 있는 이유를 설명하세요.
: JpaRepository에 이미 save, findById와 같은 메서드가 인터페이스에 정의되어 있기 때문이다. 따라서 구현 클래스 정의하지 않아도 기본적인 CRUD 기능은 사용할 수 있다.

DTO & HTTP Response
3. 기존 API가 Book Entity를 직접 요청·응답에 사용할 때 발생할 수 있는 문제를 설명하세요.
: ID 서버 관리 필드 노출의 위험성으로 보안에도 문제가 발생한다. 또 연관관계로 인한 순환 참조 문제인 직렬화도 발생하기 때문이다. 
4. Request DTO와 Response DTO를 분리하는 이유와 ResponseEntity가 필요한 상황을 설명하세요.
: id의 유무차이로 요청에는 id가 없어도 되지만 응답에는 id값이 필요하기 때문이다. ResponseEntity는 해당 entity의 상태코드와 헤더 바디를 직접 작성할 수 있어서 상태코드 헤더를 직접 정해야 할 때 사용한다.


Validation & Exception Handling
5. @Valid, @NotBlank, @Min은 각각 어느 시점에 어떤 동작을 수행하나요?
: @Valid - 검증 실행하는 것으로 기본 적이 틀에 대한 validation이 진행된다. @NotBlank은 해당 문자열이 공백인지 @Min은 값의 최소 길이를 정해주는 역할이다.
6. @RestControllerAdvice와 @ExceptionHandler를 사용하면 예외 처리 흐름이 어떻게 바뀌나요?
: 사용 전에는 try-catch를 사용해 예외 처리를 한다. 하지만 이 어노테이션을 사용하면 타입에 맞는 @ExceptionHandler을 찾아 실행해 공통 에러 JSON이 응답된다. 

Pagination & OpenAPI
7. Pageable과 Page<T>는 각각 어떤 역할을 하며 sort=price,desc는 어떻게 처리되나요?
: pageable은 요청정보로 어떤 페이지를 몇개씩 어떤 정렬로 가져올지에 대한 정보를, Page<T>는 응답정보로 데이터 목록과 메타데이터를 담는다. 해당 정렬은 가격을 내림차로 정렬하는 기능을 수행한다.
8. /v3/api-docs와 /swagger-ui.html의 역할을 구분해서 설명하세요.
: /v3/api-docs는 OpenAPI 명세 JSON이고 /swagger-ui.html은 해당 JSON을 읽어 사람이 읽기 쉽게 해주는 웹 UI이다. 둘의 차이는 데이터 명세인 것과 데이터를 보여 주는 화면이라는 점에서 차이가 있다.


STEP 3
변경 전/후 API 요청과 응답 차이를 README에 기록합니다.
: 어떤 요청을 해도 상태코드가 200이었는데 POST, DELETE의 경우 201, 204로 변경되었다. 전에는 모든 return을 Entity로 하였는데, 지금은 BookResponse로 반환한다. 

