package me.mhadidg.homework.labforward;

import me.mhadidg.homework.labforward.notebook.FrequencyDTO;
import me.mhadidg.homework.labforward.notebook.NotebookController;
import me.mhadidg.homework.labforward.notebook.NotebookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = NotebookController.class)
class NotebookControllerTests {

  public static final String FREQUENCY_ENDPOINT = "/notebooks/word/frequency";

  @Autowired
  MockMvc mockMvc;

  @MockBean
  NotebookService service;

  @Test
  public void shouldReturnAFrequencyDTOOnValidRequest() throws Exception {
    FrequencyDTO expectedModel = FrequencyDTO.of(1L, Set.of("word", "Word"));

    given(service.calculateWordFrequency(anyList(), any()))
        .willReturn(expectedModel);

    Object[] expectedWords = expectedModel.getSimilarWords().toArray();

    mockMvc.perform(post(FREQUENCY_ENDPOINT)
            .param("notebook", "word Word")
            .param("word", "word"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.frequency").value(1))
        .andExpect(jsonPath("$.similarWords", containsInAnyOrder(expectedWords[0], expectedWords[1])));

  }

  @Test
  public void shouldFailOnMissingRequiredParameters() throws Exception {
    given(service.calculateWordFrequency(anyList(), any()))
        .willThrow(RuntimeException.class);

    mockMvc.perform(post(FREQUENCY_ENDPOINT)
            .param("notebook", "word word"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsStringIgnoringCase("required")));

    mockMvc.perform(post(FREQUENCY_ENDPOINT)
            .param("word", "word"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsStringIgnoringCase("required")));
  }

  @Test
  public void shouldFailOnBlankRequiredParameters() throws Exception {
    given(service.calculateWordFrequency(anyList(), any()))
        .willThrow(RuntimeException.class);

    mockMvc.perform(post(FREQUENCY_ENDPOINT)
            .param("notebook", "word Word")
            .param("word", ""))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsStringIgnoringCase("invalid")));

    mockMvc.perform(post(FREQUENCY_ENDPOINT)
            .param("notebook", "")
            .param("word", "word"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsStringIgnoringCase("invalid")));
  }

}