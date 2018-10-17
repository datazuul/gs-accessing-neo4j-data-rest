package hello;

import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(Neo4jDataAutoConfiguration.class)
public class CmsTests {

  private static final String JSON_TEST_CATEGORY = "{\"label\":"
          + " {\"translations\":"
          + "[{\"lang\":\"de\", \"text\":\"category label\"}]"
          + "}}";

  private static final String JSON_TEST_UPDATE_CATEGORY = "{\"label\":"
          + " {\"translations\":"
          + "[{\"lang\":\"de\", \"text\":\"updated category label\"}]"
          + "}}";

  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private MockMvc mockMvc;

  @Before
  public void deleteAllBeforeTests() throws Exception {
    categoryRepository.deleteAll();
  }

  @Test
  @Ignore
  public void shouldCreateAndFindEntity() throws Exception {

    mockMvc.perform(post("/category").content(JSON_TEST_CATEGORY)).andExpect(
            status().isCreated());

    mockMvc.perform(
            get("/category/search/findByLastName?name={name}", "Baggins")).andExpect(
            status().isOk()).andExpect(
            jsonPath("$._embedded.people[0].firstName").value(
                    "Frodo"));
  }

  @Test
  public void shouldCreateEntity() throws Exception {

    mockMvc.perform(post("/category").content(JSON_TEST_CATEGORY)).andExpect(
            status().isCreated()).andExpect(
            header().string("Location", containsString("category/")));
  }

  @Test
  public void shouldDeleteEntity() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/category")
            .content(JSON_TEST_CATEGORY)).andExpect(status().isCreated()).andReturn();

    String location = mvcResult.getResponse().getHeader("Location");

    mockMvc.perform(delete(location)).andExpect(status().isNoContent());
    mockMvc.perform(get(location)).andExpect(status().isNotFound());
  }

  @Test
  @Ignore
  public void shouldPartiallyUpdateEntity() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/people").content(
            "{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")).andExpect(
                    status().isCreated()).andReturn();

    String location = mvcResult.getResponse().getHeader("Location");

    mockMvc.perform(
            patch(location).content("{\"firstName\": \"Bilbo Jr.\"}")).andExpect(
            status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
            jsonPath("$.firstName").value("Bilbo Jr.")).andExpect(
            jsonPath("$.lastName").value("Baggins"));
  }

  @Test
  public void shouldRetrieveEntity() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/category")
            .content(JSON_TEST_CATEGORY)).andExpect(status().isCreated()).andReturn();

    String location = mvcResult.getResponse().getHeader("Location");

    mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
            jsonPath("$.label.translations[0].text").value("category label"));
  }

  @Test
  public void shouldReturnRepositoryIndex() throws Exception {

    mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
            jsonPath("$._links.category").exists());
  }

  @Test
  public void shouldUpdateEntity() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/category")
            .content(JSON_TEST_CATEGORY)).andExpect(status().isCreated()).andReturn();

    String location = mvcResult.getResponse().getHeader("Location");

    mockMvc.perform(put(location)
            .content(JSON_TEST_UPDATE_CATEGORY)).andExpect(status().isNoContent());

    mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
            jsonPath("$.label.translations[0].text").value("updated category label"));
  }

}
