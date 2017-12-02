package application.bookmarks;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    // finding bookmarks by user name
    // feature 1
    Collection<Bookmark> findByAccountUsername(String username);
}