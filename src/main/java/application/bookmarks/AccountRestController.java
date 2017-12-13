package application.bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    @Autowired
    private AccountRepository accountRepository;

//    public AccountRestController(AccountRepository accountRepository) {
//        this.accountRepository = accountRepository;
//    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Account> readAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    public Account readAccount(@PathVariable String userId) {
        return accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    // TODO check differences of POST and PUT implementation. POST should be idempotent and it's not here.

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(@RequestBody Account account) {

        // return 200 OK if present otherwise 201 CREATED
        return accountRepository.findByUsername(account.username)
                .map(foundAccount -> ResponseEntity.ok(foundAccount))
                .orElseGet(() -> {
                    accountRepository.save(new Account(account.username, "password"));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{userId}")
                            .buildAndExpand(account.username).toUri();

                    return ResponseEntity.created(location).build();
                });
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
    public ResponseEntity<?> createOrUpdateAccount(@PathVariable String userId, @RequestBody Account account) {
        Optional<Account> foundAccount = accountRepository.findByUsername(userId);

        // return 200 if modified or 201 if created
        return accountRepository.findByUsername(userId)
                .map(fAccount -> {
                    accountRepository.save(foundAccount.get());

                    return ResponseEntity.ok(foundAccount);})
                .orElseGet(() -> {
                    accountRepository.save(new Account(account.username, "password"));

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{userId}")
                            .buildAndExpand(account.username).toUri();

                    return ResponseEntity.created(location).build();
                });
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable String userId) {
        Account accountToDelete = accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));

        accountRepository.delete(accountToDelete);

        // other possibility to return 204 status / no content
        // ResponseEntity.noContent().build();
        return ResponseEntity.ok(accountToDelete);
    }
}
