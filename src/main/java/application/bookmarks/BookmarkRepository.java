package application.bookmarks;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    // finding bookmarks by user name
    // Fix 1
    // Fix 1.1
    // feature 1
    // feature 1.1
    // finding bookmarks by user
    // feature 2
    // feature 2.2
    // feature 3
    Collection<Bookmark> findByAccountUsername(String username);
}