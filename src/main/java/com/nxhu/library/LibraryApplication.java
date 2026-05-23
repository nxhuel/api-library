package com.nxhu.library;

import com.nxhu.library.persistence.entity.*;
import com.nxhu.library.persistence.entity.enums.Role;
import com.nxhu.library.persistence.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               AuthorRepository authorRepository,
                               BookRepository bookRepository,
                               CommentRepository commentRepository,
                               FavoriteRepository favoriteRepository,
                               NotificationRepository notificationRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() > 0) return;

            var pwd = passwordEncoder.encode("pass123");

            // ───────────────────── USERS ─────────────────────
            var u1 = userRepository.save(UserEntity.builder().email("john@email.com").name("John Doe").password(pwd).role(Role.USER).build());
            var u2 = userRepository.save(UserEntity.builder().email("jane@email.com").name("Jane Smith").password(pwd).role(Role.AUTHOR).build());
            var u3 = userRepository.save(UserEntity.builder().email("bob@email.com").name("Bob Johnson").password(pwd).role(Role.ADMIN).build());
            var u4 = userRepository.save(UserEntity.builder().email("alice@email.com").name("Alice Wonder").password(pwd).role(Role.AUTHOR).build());
            var u5 = userRepository.save(UserEntity.builder().email("charlie@email.com").name("Charlie Brown").password(pwd).role(Role.USER).build());
            var u6 = userRepository.save(UserEntity.builder().email("diana@email.com").name("Diana Prince").password(pwd).role(Role.AUTHOR).build());
            var u7 = userRepository.save(UserEntity.builder().email("edward@email.com").name("Edward Norton").password(pwd).role(Role.USER).build());
            var u8 = userRepository.save(UserEntity.builder().email("fiona@email.com").name("Fiona Apple").password(pwd).role(Role.AUTHOR).build());
            var u9 = userRepository.save(UserEntity.builder().email("george@email.com").name("George Lucas").password(pwd).role(Role.AUTHOR).build());
            var u10 = userRepository.save(UserEntity.builder().email("helen@email.com").name("Helen Mirren").password(pwd).role(Role.USER).build());

            // ───────────────────── AUTHORS ─────────────────────
            var a1 = authorRepository.save(AuthorEntity.builder().user(u2).nationality("American").bio("Bestselling author of fantasy novels and epic adventures").build());
            var a2 = authorRepository.save(AuthorEntity.builder().user(u4).nationality("British").bio("Award-winning science fiction writer exploring time and consciousness").build());
            var a3 = authorRepository.save(AuthorEntity.builder().user(u6).nationality("Brazilian").bio("Master of magical realism inspired by Latin American folklore").build());
            var a4 = authorRepository.save(AuthorEntity.builder().user(u8).nationality("Canadian").bio("Poet and novelist known for lyrical prose and deep character studies").build());
            var a5 = authorRepository.save(AuthorEntity.builder().user(u9).nationality("American").bio("Epic space opera creator and pioneer of modern sci-fi").build());

            // ───────────────────── BOOKS ─────────────────────
            var b1 = bookRepository.save(BookEntity.builder().title("The Fantasy Realm").author(a1).uploadedBy(u2).description("An epic fantasy adventure through enchanted lands").gender("Fantasy").numPages(350).coverImage("https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400").build());
            var b2 = bookRepository.save(BookEntity.builder().title("Dragons of Dawn").author(a1).uploadedBy(u3).description("A tale of dragons, magic and ancient prophecies").gender("Fantasy").numPages(420).coverImage("https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400").build());
            var b3 = bookRepository.save(BookEntity.builder().title("Elves of Everwood").author(a1).uploadedBy(u2).description("The hidden world of immortal elves facing their final war").gender("Fantasy").numPages(390).coverImage("https://images.unsplash.com/photo-1474552226712-ac0f0961a954?w=400").build());
            var b4 = bookRepository.save(BookEntity.builder().title("Quantum Stars").author(a2).uploadedBy(u4).description("Space exploration saga across parallel universes").gender("Science Fiction").numPages(280).coverImage("https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=400").build());
            var b5 = bookRepository.save(BookEntity.builder().title("The Time Paradox").author(a2).uploadedBy(u3).description("A mind-bending journey through time and causality").gender("Science Fiction").numPages(310).coverImage("https://images.unsplash.com/photo-1501139083538-0139583c060f?w=400").build());
            var b6 = bookRepository.save(BookEntity.builder().title("Neural Dawn").author(a2).uploadedBy(u4).description("When artificial intelligence awakens to self-awareness").gender("Science Fiction").numPages(340).coverImage("https://images.unsplash.com/photo-1485827404703-89b55fcc595e?w=400").build());
            var b7 = bookRepository.save(BookEntity.builder().title("The Enchanted River").author(a3).uploadedBy(u6).description("A mystical river that grants wishes to those who find it").gender("Magical Realism").numPages(290).coverImage("https://images.unsplash.com/photo-1504198322253-cfa87a0ff25f?w=400").build());
            var b8 = bookRepository.save(BookEntity.builder().title("Butterflies of Gold").author(a3).uploadedBy(u3).description("Legends of golden butterflies that carry souls to the afterlife").gender("Magical Realism").numPages(260).coverImage("https://images.unsplash.com/photo-1504214208698-ea1916a2195a?w=400").build());
            var b9 = bookRepository.save(BookEntity.builder().title("Whispers in the Wind").author(a4).uploadedBy(u8).description("A collection of hauntingly beautiful poems about love and loss").gender("Poetry").numPages(120).coverImage("https://images.unsplash.com/photo-1474932430478-367dbb6832c1?w=400").build());
            var b10 = bookRepository.save(BookEntity.builder().title("The Northern Lights").author(a4).uploadedBy(u8).description("A coming-of-age story set in the remote Canadian wilderness").gender("Fiction").numPages(220).coverImage("https://images.unsplash.com/photo-1503264116251-35a269479413?w=400").build());
            var b11 = bookRepository.save(BookEntity.builder().title("Star Horizon").author(a5).uploadedBy(u9).description("Humanity's first interstellar colony ship faces the unknown").gender("Science Fiction").numPages(500).coverImage("https://images.unsplash.com/photo-1462331940025-496dfbfc7564?w=400").build());
            var b12 = bookRepository.save(BookEntity.builder().title("Galactic Empire").author(a5).uploadedBy(u3).description("The rise and fall of the greatest empire in the galaxy").gender("Science Fiction").numPages(480).coverImage("https://images.unsplash.com/photo-1545156521-77bd85671d30?w=400").build());

            // ───────────────────── COMMENTS ─────────────────────
            commentRepository.saveAll(List.of(
                    CommentEntity.builder().content("Absolutely loved this book! Could not put it down.").user(u1).book(b1).build(),
                    CommentEntity.builder().content("The world-building is incredible. Highly recommended.").user(u5).book(b1).build(),
                    CommentEntity.builder().content("A masterpiece of fantasy literature.").user(u7).book(b1).build(),
                    CommentEntity.builder().content("Dragons have never been this real. Amazing read!").user(u1).book(b2).build(),
                    CommentEntity.builder().content("The plot twists kept me guessing until the end.").user(u10).book(b2).build(),
                    CommentEntity.builder().content("Beautifully written. The elven culture is so rich.").user(u5).book(b3).build(),
                    CommentEntity.builder().content("A bit slow in the middle but the ending is worth it.").user(u7).book(b3).build(),
                    CommentEntity.builder().content("Mind-blowing concepts about parallel universes.").user(u1).book(b4).build(),
                    CommentEntity.builder().content("This book changed how I see reality.").user(u3).book(b4).build(),
                    CommentEntity.builder().content("Hard sci-fi at its finest.").user(u10).book(b4).build(),
                    CommentEntity.builder().content("Time travel done right. Very clever plot.").user(u5).book(b5).build(),
                    CommentEntity.builder().content("Reminds me of classic Asimov but with a fresh twist.").user(u7).book(b5).build(),
                    CommentEntity.builder().content("Scarily plausible vision of AI awakening.").user(u1).book(b6).build(),
                    CommentEntity.builder().content("The ethical questions raised are profound.").user(u10).book(b6).build(),
                    CommentEntity.builder().content("Magical realism at its best. Reminds me of Garcia Marquez.").user(u2).book(b7).build(),
                    CommentEntity.builder().content("A beautiful story that stays with you.").user(u4).book(b7).build(),
                    CommentEntity.builder().content("The symbolism of the butterflies is breathtaking.").user(u5).book(b8).build(),
                    CommentEntity.builder().content("Short but powerful. Cried at the ending.").user(u8).book(b8).build(),
                    CommentEntity.builder().content("Every poem feels like a personal letter to the reader.").user(u7).book(b9).build(),
                    CommentEntity.builder().content("Lyrically stunning. I've read it three times already.").user(u1).book(b9).build(),
                    CommentEntity.builder().content("Captures the beauty and harshness of nature perfectly.").user(u10).book(b10).build(),
                    CommentEntity.builder().content("A heartfelt story about finding yourself in the wilderness.").user(u5).book(b10).build(),
                    CommentEntity.builder().content("The most ambitious space opera I've ever read.").user(u2).book(b11).build(),
                    CommentEntity.builder().content("The ship design and science are incredibly detailed.").user(u4).book(b11).build(),
                    CommentEntity.builder().content("Can't wait for the sequel!").user(u6).book(b11).build(),
                    CommentEntity.builder().content("A sprawling epic that spans millennia. Incredible scope.").user(u1).book(b12).build(),
                    CommentEntity.builder().content("Political intrigue on a galactic scale. Brilliant.").user(u3).book(b12).build()
            ));

            // ───────────────────── FAVORITES ─────────────────────
            favoriteRepository.saveAll(List.of(
                    FavoriteEntity.builder().user(u1).book(b1).build(),
                    FavoriteEntity.builder().user(u1).book(b4).build(),
                    FavoriteEntity.builder().user(u1).book(b11).build(),
                    FavoriteEntity.builder().user(u5).book(b2).build(),
                    FavoriteEntity.builder().user(u5).book(b7).build(),
                    FavoriteEntity.builder().user(u5).book(b9).build(),
                    FavoriteEntity.builder().user(u7).book(b3).build(),
                    FavoriteEntity.builder().user(u7).book(b6).build(),
                    FavoriteEntity.builder().user(u10).book(b1).build(),
                    FavoriteEntity.builder().user(u10).book(b5).build(),
                    FavoriteEntity.builder().user(u10).book(b8).build(),
                    FavoriteEntity.builder().user(u10).book(b12).build()
            ));

            // ───────────────────── NOTIFICATIONS ─────────────────────
            notificationRepository.saveAll(List.of(
                    NotificationEntity.builder().user(u5).message("Jane Smith published a new book: 'The Fantasy Realm'").build(),
                    NotificationEntity.builder().user(u3).message("Alice Wonder published a new book: 'Quantum Stars'").build(),
                    NotificationEntity.builder().user(u1).message("George Lucas published a new book: 'Star Horizon'").build()
            ));
        };
    }
}
