package application.bookmarks.unittest;

import application.bookmarks.Account;
import application.bookmarks.AccountRepository;
import application.bookmarks.AccountRestController;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountRestControllerUnitTest {

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountRestController controller;

    @Test
    public void readSingleAccount() throws Exception {
        Account expectedAccount = new Account("oanacka", "password");
        when(repository.findByUsername("oanacka")).thenReturn(Optional.of(expectedAccount));

        Account actualAccount = controller.readAccount("oanacka");

        assertThat(actualAccount).isEqualTo(expectedAccount);
    }

    @Test
    public void readAllAccounts() throws Exception {
        List<Account> expectedAccounts = Arrays.asList(
                new Account("oanacka", "password"),
                new Account("kpawelski", "password"));
        when(repository.findAll()).thenReturn(expectedAccounts);

        Collection<Account> actualAccounts = controller.readAccounts();

        assertThat(actualAccounts).containsExactlyElementsOf(expectedAccounts);
    }

    @Test
    @Ignore
    // createAccount contains some dependencies on web container and cannot be run without whole environment
    public void createAccount() throws Exception {
        Account account = new Account("oanacka", "password");
        when(repository.findByUsername(account.getUsername())).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.createAccount(account);

        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok());
    }
}
