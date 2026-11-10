package com.openApi.Apis;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(
        name = "Libros",
        description = "Gestión de los libros de la biblioteca"
)
public class BookController {

    private final List<Book> books = new ArrayList<>(
            List.of(
                    new Book(1L, "El Quijote", "Miguel de Cervantes"),
                    new Book(2L, "La Celestina", "Fernando de Rojas")
            )
    );

    // GET: Obtener todos los libros
    @Operation(
            summary = "Obtener todos los libros",
            description = "Devuelve una lista de todos los libros registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida correctamente"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(books);
    }

    // GET: Obtener un libro por ID
    @Operation(
            summary = "Buscar libro por ID",
            description = "Busca un libro utilizando su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Libro encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @Parameter(
                    description = "Identificador del libro",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Crear un libro
    @Operation(
            summary = "Crear un libro",
            description = "Registra un nuevo libro en la biblioteca"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Libro creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                        "id": 3,
                        "title": "El Principito",
                        "author": "Antoine de Saint-Exupéry"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos incorrectos"
            )
    })
    @PostMapping
    public ResponseEntity<Book> createBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del libro que se quiere registrar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Book.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                        "id": 3,
                        "title": "El Principito",
                        "author": "Antoine de Saint-Exupéry"
                    }
                    """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody Book book
    ) {
        Book newBook = new Book(
                books.stream()
                        .mapToLong(Book::getId)
                        .max()
                        .orElse(0L) + 1,
                book.getTitle(),
                book.getAuthor()
        );

        books.add(newBook);

        return ResponseEntity.status(201).body(newBook);
    }

    // PUT: Actualizar un libro
    @Operation(
            summary = "Actualizar un libro",
            description = "Modifica los datos de un libro existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Libro actualizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID del libro", example = "1")
            @PathVariable Long id,

            @RequestBody Book updatedBook
    ) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {

                Book book = new Book(
                        id,
                        updatedBook.getTitle(),
                        updatedBook.getAuthor()
                );

                books.set(i, book);

                return ResponseEntity.ok(book);
            }
        }

        return ResponseEntity.notFound().build();
    }

    // DELETE: Eliminar un libro
    @Operation(
            summary = "Eliminar un libro",
            description = "Elimina un libro mediante su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Libro eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Libro no encontrado"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID del libro", example = "1")
            @PathVariable Long id
    ) {
        boolean deleted = books.removeIf(
                book -> book.getId().equals(id)
        );

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    // Modelo utilizado para documentar los libros
    @Schema(description = "Información de un libro")
    public static class Book {

        @Schema(
                description = "Identificador del libro",
                example = "1"
        )
        private Long id;

        @Schema(
                description = "Título del libro",
                example = "El Quijote",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        private String title;

        @Schema(
                description = "Autor del libro",
                example = "Miguel de Cervantes",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        private String author;

        public Book() {}

        public Book(Long id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
