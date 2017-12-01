package application.bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    private  AccountRepository accountRepository;

    @Autowired
    public AccountRestController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Account> readAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public Account readAccount(@PathVariable String userId) {
        return accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        Account createdAccount = accountRepository.save(new Account(account.username, "password"));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userId}")
                .buildAndExpand(createdAccount.username).toUri();

        return ResponseEntity.created(location).build();
    }
}