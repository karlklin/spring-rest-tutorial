package application.bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Collection<Account> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }
}
