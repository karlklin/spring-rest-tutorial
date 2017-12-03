package application.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bookmark {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Account account;

    String name;
    String uri;
    String description;

    Bookmark() { // jpa only
    }

    public Bookmark(Account account, String name, String uri, String description) {
        this.name = name;
        this.uri = uri;

        this.description = description;
        this.account = account;
    }


    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() { return description; }

    public String getName() {
        return name;
    }
}
