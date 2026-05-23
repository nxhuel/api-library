# Lo que aprendí en este proyecto (más allá del CRUD)

## 1. Relaciones entre entidades (JPA)

### @OneToOne con @MapsId
La relación más interesante es entre `UserEntity` y `AuthorEntity`:

```java
// UserEntity
@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
private AuthorEntity author;

// AuthorEntity
@Id
private Long userId;

@OneToOne
@MapsId
@JoinColumn(name = "user_id")
private UserEntity user;
```

**¿Qué hace `@MapsId`?** Toma el `id` del `UserEntity` y lo reusa como `id` del `AuthorEntity`. Es decir, NO hay un `@GeneratedValue` en AuthorEntity. El `userId` del autor es el mismo que el `id` del usuario. Así garantizamos que un autor SIEMPRE tiene un usuario asociado, y no puede existir un autor sin su usuario.

**`cascade = CascadeType.ALL`** en el lado de UserEntity: si creás un usuario con un autor adentro, se guarda todo automático.

### @OneToMany y @ManyToOne (bidireccional)
```java
// BookEntity (dueño de la relación)
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "author_id", nullable = false)
private AuthorEntity author;

// AuthorEntity (lado inverso)
@OneToMany(mappedBy = "author")
private List<BookEntity> books;
```

**`mappedBy = "author"`** le dice a JPA: "la relación ya está mapeada en el campo `author` de BookEntity, no crees otra columna".

**`FetchType.LAZY`**: no trae los libros automáticamente cuando cargás un autor. Solo los trae cuando llamás a `getBooks()`. Esto evita consultas innecesarias (N+1 problem).

### @OneToMany por duplicado en UserEntity
```java
@OneToMany(mappedBy = "uploadedBy")
private List<BookEntity> uploadedBooks;
```

Un usuario puede tener `uploadedBooks` (libros que subió) y opcionalmente un `author`. Son relaciones distintas.

### Autorreferencia (comentarios con replies)
```java
// CommentEntity
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "parent_id")
private CommentEntity parent;
```

Un comentario puede tener un `parent` (otro comentario). Así armamos replies en árbol. En el servicio, cuando pedimos comentarios de un libro, solo traemos los de nivel 1 (`parent IS NULL`) y para cada uno buscamos sus respuestas:

```java
List<CommentEntity> topLevel = commentRepository
    .findByBookIdAndParentIsNullAndDeletedFalse(bookId);
// por cada uno, buscar replies con findByParentIdAndDeletedFalse(parentId)
```

### UniqueConstraint compuesto (favoritos)
```java
@Table(name = "favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "book_id"})
})
```

Un usuario no puede marcar el mismo libro como favorito dos veces. La constraint a nivel BD lo garantiza.

---

## 2. Manejo de errores (GlobalExceptionHandler)

Antes del cambio, si un servicio lanzaba una excepción, Spring devolvía un error 500 genérico y feo. Ahora:

```java
@ControllerAdvice  // intercepta TODOS los controllers
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex) {
        // 404 con mensaje claro
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(...) {
        // 400 con los campos que fallaron y por qué
        // ej: {"name": "must not be blank", "email": "must be a well-formed email address"}
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(...) {
        // 500 para errores inesperados
    }
}
```

Esto sigue el principio **Fail Fast**: si algo está mal, que falle ya y con un mensaje útil, no un error 500 genérico.

**Respuesta consistente**: todos los errores devuelven el mismo formato:
```json
{
  "status": 404,
  "error": "Book not found with id: 99",
  "timestamp": "2026-05-13T00:30:00"
}
```

---

## 3. Soft Delete (borrado lógico)

En lugar de `DELETE FROM books WHERE id = ?`, marcamos un campo:

```java
// BookEntity
@Builder.Default
private Boolean deleted = false;

// En el servicio:
public void deleteBook(Long id) {
    BookEntity entity = bookRepository.findById(id).orElseThrow(...);
    entity.setDeleted(true);  // no se borra, solo se marca
    bookRepository.save(entity);
}
```

**Ventajas:**
- Podemos mostrar un historial de libros eliminados (Admin)
- Podemos restaurar libros después (solo cambiamos `deleted` a `false`)
- No perdemos datos

**Contras**: hay que filtrar siempre en las consultas:
```java
// En getAllBooks:
return bookRepository.findAll().stream()
    .filter(b -> !Boolean.TRUE.equals(b.getDeleted()))
    .map(this::toResponse)
    .toList();
```

Lo mismo aplica para comentarios con `CommentEntity.deleted`.

---

## 4. DTOs (Data Transfer Objects) y por qué no exponer Entities

Nunca devolvemos `BookEntity` directamente al frontend. Creamos un `BookResponseDTO`:

```java
public class BookResponseDTO {
    private Long id;
    private String title;
    private String authorName;  // datos de otras tablas
    // ...
}
```

**¿Por qué?**
- **Seguridad**: no exponemos campos internos como `password`
- **Desacoplamiento**: podemos cambiar la BD sin cambiar la API
- **Personalización**: podemos combinar datos de varias entidades en una sola respuesta (ej: el nombre del autor dentro del libro)
- **Rendimiento**: solo enviamos lo que el frontend necesita

---

## 5. Servicios con interfaces

Cada servicio tiene `interface` + `impl`:

```java
// BookService.java (contrato)
public interface BookService {
    BookResponseDTO createBook(BookRequestDTO request);
    List<BookResponseDTO> getAllBooks();
    // ...
}

// BookServiceImpl.java (implementación)
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    // ...
}
```

**¿Para qué?**
- **Fácil de testear**: podés mockear la interfaz
- **Flexibilidad**: podés cambiar la implementación sin tocar el controller
- **Claridad**: la interfaz dice QUÉ hace, la implementación dice CÓMO

---

## 6. Logging con SLF4J

```java
private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
log.info("createBook title={}", request.getTitle());
log.info("book_created id={}", saved.getId());
```

**Formato con `{}`**: mejor que concatenar strings (`"createBook " + title`) porque solo arma el mensaje si el nivel de log está habilitado.

---

## 7. Constructor Injection con @RequiredArgsConstructor

```java
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;  // final = obligatorio
    private final AuthorRepository authorRepository;
    // ...
}
```

Lombok genera automáticamente el constructor con todos los `final`. Spring inyecta las dependencias por constructor. **Ventajas**: las dependencias son explícitas, no hay setters, el objeto siempre se crea con todo lo que necesita.

---

## 8. @Transactional (manejo de transacciones)

```java
@Override
@Transactional  // si falla algo, se deshace TODO
public BookResponseDTO createBook(BookRequestDTO request) {
    // varias operaciones en BD
    bookRepository.save(entity);
    // si algo falla acá, el save se deshace automáticamente
}
```

- `@Transactional(readOnly = true)` en consultas: optimiza la conexión a la BD
- Si un método llama a otro método con `@Transactional`, las transacciones se fusionan

---

## 9. Cálculo de estadísticas (AuthorStats)

```java
public AuthorStatsResponseDTO getAuthorStats(Long authorId) {
    List<BookEntity> books = bookRepository.findByAuthorUserId(authorId);
    long totalBooks = books.size();
    long totalDownloads = books.stream()
        .mapToLong(b -> b.getDownloadsCount())
        .sum();
    long totalFavorites = books.stream()
        .mapToLong(b -> favoriteRepository.countByBookId(b.getId()))
        .sum();
    double averageRating = books.stream()
        .filter(b -> b.getRating() != null)
        .mapToDouble(BookEntity::getRating)
        .average()
        .orElse(0.0);
    // ...
}
```

**`stream().mapToLong().sum()`**: recorre la lista de libros, suma los valores de cada uno. Mejor que un for loop imperativo.

---

## 10. Manejo de archivos PDF

```java
public BookResponseDTO uploadPdf(Long id, MultipartFile file) {
    // 1. Crear directorio si no existe
    Files.createDirectories(dir);

    // 2. Nombre único con UUID para evitar colisiones
    String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

    // 3. Copiar el archivo
    Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

    // 4. Guardar la ruta en la BD
    entity.setPdfPath(target.toString());
}
```

Para descargar, usamos `UrlResource` que Spring sirve como `Resource`.

---

## Resumen: patrones que usamos

| Patrón | Dónde | Para qué |
|--------|-------|----------|
| `@MapsId` | AuthorEntity | Compartir PK entre tablas |
| `@ManyToOne(fetch = LAZY)` | Book, Comment, Favorite | No cargar datos innecesarios |
| `@ControllerAdvice` | GlobalExceptionHandler | Errores consistentes |
| Soft Delete | Book, Comment | No perder datos, historial |
| DTOs | dto/ | No exponer entidades |
| Interface + Impl | service/ | Testear y cambiar implementaciones |
| Constructor Injection | Todos los servicios | Dependencias explícitas |
| SLF4J | Todos los servicios | Logging profesional |
| `@Transactional` | Servicios | Transacciones seguras |

Esto es lo que diferencia un proyecto "CRUD nomás" de uno bien armado. Cada patrón tiene un propósito: hacer el código más mantenible, seguro y fácil de entender.
