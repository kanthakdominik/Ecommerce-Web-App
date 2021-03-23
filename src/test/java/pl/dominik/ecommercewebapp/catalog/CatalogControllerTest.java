package pl.dominik.ecommercewebapp.catalog;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
public class CatalogControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CatalogService catalogService;

    private WebClient webClient;

    @BeforeEach
    public void setup() {
        webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc).build();
    }

    @Test
    @DisplayName("index page returns the landing page")
    void shouldReturnLandingPage() throws Exception {
        mockMvc.perform(get("http://localhost:8080/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Cake Factory")));
    }

    @Test
    @DisplayName("index page return a list of items from the database")
    void shouldViewListOfItemsFromDatabase() throws Exception {
        String expectedTitle = "Test title";
        BigDecimal expectedPrice = BigDecimal.ONE;
        List<Item> list = new ArrayList<>();
        Item testItem = new Item(expectedTitle, expectedPrice);
        list.add(testItem);
        when(catalogService.getAll()).thenReturn(list);
        HtmlPage page = webClient.getPage("http://localhost:8080/");

        assertThat(page.querySelectorAll(".item-title")).anyMatch(domElement -> domElement.getTextContent().equals(expectedTitle));
    }
}


