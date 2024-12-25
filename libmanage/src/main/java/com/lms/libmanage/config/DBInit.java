package com.lms.libmanage.config;

import com.lms.libmanage.entity.Author;
import com.lms.libmanage.entity.Book;
import com.lms.libmanage.entity.Genre;
import com.lms.libmanage.entity.user.RegisterRequest;
import com.lms.libmanage.entity.user.User;
import com.lms.libmanage.repository.*;
import com.lms.libmanage.service.AuthenticationService;
import com.lms.libmanage.service.BorrowHistoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBInit {

    private final AuthenticationService authenticationService;
    private final BorrowHistoryService borrowHistoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public DBInit(AuthenticationService authenticationService, PasswordEncoder passwordEncoder, BorrowHistoryService borrowHistoryService) {
        this.authenticationService = authenticationService;
        this.borrowHistoryService = borrowHistoryService;
    }

    @PostConstruct
    public void initData() {
        createUsers();
        createGenres();
        createAuthors();
        createBooks();
        createBorrowHistory();
    }

    private void createUsers() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            RegisterRequest registerRequest = new RegisterRequest(
                    "Admin", "User", "admin", "admin@example.com", "password123", "1234567890", "admin address"
            );
            String roleName = "ADMIN";
            authenticationService.register(registerRequest, roleName);
        }

        if (userRepository.findByUsername("member").isEmpty()) {
            RegisterRequest registerRequest = new RegisterRequest(
                    "Member", "User", "member","member@example.com", "password123", "0987654321", "member address"
                    );
            String roleName = "LIBRARY_MEMBER";
            authenticationService.register(registerRequest, roleName);
        }

        if (userRepository.findByUsername("staff").isEmpty()) {
            RegisterRequest registerRequest = new RegisterRequest(
                    "Staff", "User", "staff","staff@example.com", "password123", "0987654321", "staff address"
            );
            String roleName = "LIBRARY_STAFF";
            authenticationService.register(registerRequest, roleName);
        }
    }

    private void createBooks() {
        if (bookRepository.count() == 0) {
            Author scottFitzgerald = authorRepository.findByFirstNameAndLastName("F. Scott", "Fitzgerald");
            Author hermanMelville = authorRepository.findByFirstNameAndLastName("Herman", "Melville");
            Author janeAusten = authorRepository.findByFirstNameAndLastName("Jane", "Austen");
            Author leoTolstoy = authorRepository.findByFirstNameAndLastName("Leo", "Tolstoy");
            Author georgeOrwell = authorRepository.findByFirstNameAndLastName("George", "Orwell");
            Author charlesDickens = authorRepository.findByFirstNameAndLastName("Charles", "Dickens");
            Author markTwain = authorRepository.findByFirstNameAndLastName("Mark", "Twain");
            Author ernestHemingway = authorRepository.findByFirstNameAndLastName("Ernest", "Hemingway");
            Author jkRowling = authorRepository.findByFirstNameAndLastName("J.K.", "Rowling");
            Author jrrTolkien = authorRepository.findByFirstNameAndLastName("J.R.R.", "Tolkien");
            Author gabrielGarciaMarquez = authorRepository.findByFirstNameAndLastName("Gabriel", "García Márquez");
            Author franzKafka = authorRepository.findByFirstNameAndLastName("Franz", "Kafka");
            Author williamShakespeare = authorRepository.findByFirstNameAndLastName("William", "Shakespeare");
            Author miguelDeCervantes = authorRepository.findByFirstNameAndLastName("Miguel", "de Cervantes");
            Author virginiaWoolf = authorRepository.findByFirstNameAndLastName("Virginia", "Woolf");
            Author danteAlighieri = authorRepository.findByFirstNameAndLastName("Dante", "Alighieri");
            Author edgarAllanPoe = authorRepository.findByFirstNameAndLastName("Edgar", "Allan Poe");
            Author maryShelley = authorRepository.findByFirstNameAndLastName("Mary", "Shelley");
            Author bramStoker = authorRepository.findByFirstNameAndLastName("Bram", "Stoker");
            Author julesVerne = authorRepository.findByFirstNameAndLastName("Jules", "Verne");


            Genre drama = genreRepository.findByGenreName("Drama");
            Genre romance = genreRepository.findByGenreName("Romance");
            Genre adventure = genreRepository.findByGenreName("Adventure");
            Genre historicalFiction = genreRepository.findByGenreName("Historical Fiction");
            Genre dystopian = genreRepository.findByGenreName("Dystopian");
            Genre satire = genreRepository.findByGenreName("Satire");
            Genre fantasy = genreRepository.findByGenreName("Fantasy");
            Genre mystery = genreRepository.findByGenreName("Mystery");
            Genre horror = genreRepository.findByGenreName("Horror");
            Genre scienceFiction = genreRepository.findByGenreName("Science Fiction");

            if (drama == null || romance == null || dystopian == null || historicalFiction == null ||
                    adventure == null || fantasy == null || satire == null || mystery == null || horror == null ||
                    scienceFiction == null) {
                System.out.println("Genre(s) not found!");
                return;
            }


            List<Book> books = List.of(
                    new Book("The Great Gatsby", scottFitzgerald, drama, 5),
                    new Book("Pride and Prejudice", janeAusten, romance, 4),
                    new Book("War and Peace", leoTolstoy, historicalFiction, 6),
                    new Book("1984", georgeOrwell, satire, 7),
                    new Book("Oliver Twist", charlesDickens, mystery, 4),
                    new Book("The Adventures of Tom Sawyer", markTwain, adventure, 5),
                    new Book("The Old Man and the Sea", ernestHemingway, adventure, 5),
                    new Book("Harry Potter and the Philosopher's Stone", jkRowling, fantasy, 10),
                    new Book("The Hobbit", jrrTolkien, fantasy, 8),
                    new Book("One Hundred Years of Solitude", gabrielGarciaMarquez, fantasy, 7),
                    new Book("The Metamorphosis", franzKafka, satire, 6),
                    new Book("Hamlet", williamShakespeare, drama, 5),
                    new Book("Don Quixote", miguelDeCervantes, adventure, 6),
                    new Book("Mrs. Dalloway", virginiaWoolf, drama, 5),
                    new Book("The Divine Comedy", danteAlighieri, historicalFiction, 4),
                    new Book("The Raven", edgarAllanPoe, mystery, 3),
                    new Book("Frankenstein", maryShelley, scienceFiction, 6),
                    new Book("Dracula", bramStoker, mystery, 5),
                    new Book("Around the World in Eighty Days", julesVerne, adventure, 7)
            );

            bookRepository.saveAll(books);
            bookRepository.flush();
        }
    }



    private void createGenres() {
        if (genreRepository.count() == 0) {
            genreRepository.save(new Genre("Drama"));
            genreRepository.save(new Genre("Romance"));
            genreRepository.save(new Genre("Historical Fiction"));
            genreRepository.save(new Genre("Satire"));
            genreRepository.save(new Genre("Epic Novel"));
            genreRepository.save(new Genre("Philosophical Fiction"));
            genreRepository.save(new Genre("Dystopian"));
            genreRepository.save(new Genre("Modernist Fiction"));
            genreRepository.save(new Genre("Fantasy"));
            genreRepository.save(new Genre("Literary Fiction"));
            genreRepository.save(new Genre("Magical Realism"));
            genreRepository.save(new Genre("Absurdist Fiction"));
            genreRepository.save(new Genre("Epic Poetry"));
            genreRepository.save(new Genre("Horror"));
            genreRepository.save(new Genre("Adventure"));
            genreRepository.save(new Genre("Jazz Age Fiction"));
            genreRepository.save(new Genre("Southern Gothic"));
            genreRepository.save(new Genre("Gothic Fiction"));
            genreRepository.save(new Genre("Science Fiction"));
            genreRepository.save(new Genre("Mystery"));
        }
    }


    private void createAuthors() {
        if (authorRepository.count() == 0) {
            authorRepository.save(new Author("F. Scott", "Fitzgerald"));
            authorRepository.save(new Author("Herman", "Melville"));
            authorRepository.save(new Author("Jane", "Austen"));
            authorRepository.save(new Author("Leo", "Tolstoy"));
            authorRepository.save(new Author("George", "Orwell"));
            authorRepository.save(new Author("Charles", "Dickens"));
            authorRepository.save(new Author("Mark", "Twain"));
            authorRepository.save(new Author("Ernest", "Hemingway"));
            authorRepository.save(new Author("J.K.", "Rowling"));
            authorRepository.save(new Author("J.R.R.", "Tolkien"));
            authorRepository.save(new Author("Gabriel", "García Márquez"));
            authorRepository.save(new Author("Franz", "Kafka"));
            authorRepository.save(new Author("William", "Shakespeare"));
            authorRepository.save(new Author("Miguel", "de Cervantes"));
            authorRepository.save(new Author("Virginia", "Woolf"));
            authorRepository.save(new Author("Dante", "Alighieri"));
            authorRepository.save(new Author("Edgar", "Allan Poe"));
            authorRepository.save(new Author("Mary", "Shelley"));
            authorRepository.save(new Author("Bram", "Stoker"));
            authorRepository.save(new Author("Jules", "Verne"));
        }
    }


    private void createBorrowHistory() {
        if (borrowHistoryRepository.count() == 0) {
            User member = userRepository.findById(2)
                    .orElseThrow(() -> new IllegalStateException("User with ID 1 does not exist"));

            List<Book> books = bookRepository.findAll();

            if (books.isEmpty()) {
                throw new IllegalStateException("No books available in the repository");
            }

            borrowHistoryService.borrowBook(books.get(0).getBookId(), member.getId());
            borrowHistoryService.borrowBook(books.get(1).getBookId(), member.getId());
            borrowHistoryService.borrowBook(books.get(2).getBookId(), member.getId());
            borrowHistoryService.borrowBook(books.get(3).getBookId(), member.getId());
            borrowHistoryService.borrowBook(books.get(4).getBookId(), member.getId());
        }
    }


}
