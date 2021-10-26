package me.mhadidg.homework.labforward.notebook;

import org.apache.commons.lang3.Validate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mustapha Hadid
 */
@RestController
@RequestMapping("notebooks")
public class NotebookController {

  private final NotebookService service;

  public NotebookController(NotebookService service) {
    this.service = service;
  }

  @PostMapping("/word/frequency")
  public FrequencyDTO calculateWordFrequency(@RequestParam String notebook, @RequestParam String word) {
    Validate.notBlank(notebook, "Required request parameter 'notebook' cannot be empty or blank!");
    Validate.notBlank(word, "Required request parameter 'word' cannot be empty or blank!");

    List<String> wordList = Arrays.asList(notebook.split("\\s+"));
    return service.calculateWordFrequency(wordList, word);
  }

}
