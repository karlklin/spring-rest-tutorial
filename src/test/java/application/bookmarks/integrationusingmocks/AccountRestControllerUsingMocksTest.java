package application.bookmarks.integrationusingmocks;

import application.Application;
import application.bookmarks.Account;
import application.bookmarks.AccountRepository;
import application.bookmarks.BookmarkRepository;
import application.bookmarks.integration.MvcTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static application.bookmarks.integration.MvcTestHelper.CONTENT_TYPE;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AccountRestControllerUsingMocksTest {

    public static final String OANACKA = "oanacka";
    public static final String KPAWELSKI = "kpawelski";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MvcTestHelper helper;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setup() throws Exception {
        this.bookmarkRepository.deleteAllInBatch();
        this.accountRepository.deleteAllInBatch();

        accountRepository.save(new Account(OANACKA, "password"));
        accountRepository.save(new Account(KPAWELSKI, "password"));
    }

    @Test
    public void readSingleAccount() throws Exception {
        mockMvc.perform(get(helper.url("/" + OANACKA)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.username", is(OANACKA)));
    }

    @Test
    public void readAccounts() throws Exception {
        mockMvc.perform(get(helper.url()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is(OANACKA)))
                .andExpect(jsonPath("$[1].username", is(KPAWELSKI)));
    }

    @Test
    public void createAccountPost() throws Exception {
        final String newAccount = "tpawelski";
        String accountJson = helper.json(new Account(newAccount, "password"));

        this.mockMvc.perform(post(helper.url())
                .contentType(CONTENT_TYPE)
                .content(accountJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateAccountPost() throws Exception {
        String accountJson = helper.json(new Account(OANACKA, "password"));

        this.mockMvc.perform(post(helper.url())
                .contentType(CONTENT_TYPE)
                .content(accountJson))
                .andExpect(status().isOk());
    }

    @Test
    public void createAccountPut() throws Exception {
        final String newAccount = "tpawelski";
        String accountJson = helper.json(new Account(newAccount, "password"));

        this.mockMvc.perform(put(helper.url("/" + newAccount))
                .contentType(CONTENT_TYPE)
                .content(accountJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateAccountPut() throws Exception {
        String accountJson = helper.json(new Account(OANACKA, "password"));

        this.mockMvc.perform(put(helper.url("/" + OANACKA))
                .contentType(CONTENT_TYPE)
                .content(accountJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAccount() throws Exception {
        this.mockMvc.perform(delete(helper.url("/" + OANACKA))
                .contentType(CONTENT_TYPE))
                .andExpect(status().isOk());
    }

}
