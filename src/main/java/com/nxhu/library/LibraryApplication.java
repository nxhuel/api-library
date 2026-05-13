package com.nxhu.library;

import com.nxhu.library.persistence.entity.AuthorEntity;
import com.nxhu.library.persistence.entity.BookEntity;
import com.nxhu.library.persistence.entity.UserEntity;
import com.nxhu.library.persistence.entity.enums.Role;
import com.nxhu.library.persistence.repository.AuthorRepository;
import com.nxhu.library.persistence.repository.BookRepository;
import com.nxhu.library.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               AuthorRepository authorRepository,
                               BookRepository bookRepository) {
        return args -> {
            if (userRepository.count() > 0) return;

            UserEntity user1 = UserEntity.builder()
                    .email("john@email.com").name("John Doe")
                    .password("pass123").role(Role.USER).build();
            UserEntity user2 = UserEntity.builder()
                    .email("jane@email.com").name("Jane Smith")
                    .password("pass123").role(Role.AUTHOR).build();
            UserEntity user3 = UserEntity.builder()
                    .email("bob@email.com").name("Bob Johnson")
                    .password("pass123").role(Role.ADMIN).build();
            UserEntity user4 = UserEntity.builder()
                    .email("alice@email.com").name("Alice Wonder")
                    .password("pass123").role(Role.AUTHOR).build();

            userRepository.saveAll(List.of(user1, user2, user3, user4));

            AuthorEntity author1 = AuthorEntity.builder()
                    .user(user2).nationality("American")
                    .bio("Bestselling author of fantasy novels").build();
            AuthorEntity author2 = AuthorEntity.builder()
                    .user(user4).nationality("British")
                    .bio("Award-winning science fiction writer").build();

            authorRepository.saveAll(List.of(author1, author2));

            BookEntity book1 = BookEntity.builder()
                    .title("The Fantasy Realm").author(author1).uploadedBy(user2)
                    .description("An epic fantasy adventure")
                    .gender("Fantasy").numPages(350).build();
            BookEntity book2 = BookEntity.builder()
                    .title("Dragons of Dawn").author(author1).uploadedBy(user3)
                    .description("A tale of dragons and magic")
                    .gender("Fantasy").numPages(420).build();
            BookEntity book3 = BookEntity.builder()
                    .title("Quantum Stars").author(author2).uploadedBy(user4)
                    .description("Space exploration saga")
                    .gender("Science Fiction").numPages(280).build();
            BookEntity book4 = BookEntity.builder()
                    .title("The Time Paradox").author(author2).uploadedBy(user3)
                    .description("A mind-bending time travel story")
                    .gender("Science Fiction").numPages(310).build();

            bookRepository.saveAll(List.of(book1, book2, book3, book4));
        };
    }
}
