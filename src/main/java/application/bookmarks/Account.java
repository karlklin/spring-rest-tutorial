package application.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    String password;

    @Column(unique = true)
    String username;

    public Account(String name, String password) {
        this.username = name;
        this.password = password;
    }

    Account() { // jpa only
    }

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bookmark> bookmarks = new HashSet<>();

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBookmarks(Set<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }



}
