package me.mhadidg.homework.labforward.notebook;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author Mustapha Hadid
 */
@Data
@AllArgsConstructor(staticName = "of")
public class FrequencyDTO {

  private Long frequency;
  private Set<String> similarWords;

}
