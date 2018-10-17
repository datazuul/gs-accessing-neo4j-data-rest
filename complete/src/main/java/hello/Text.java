package hello;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Collection;
import java.util.Set;

@JsonDeserialize(as = TextImpl.class)
public interface Text extends Entity {

  public static final String DEFAULT_LANG = "de";

  /**
   * @return text with default lang ("de")
   */
  String getText();

  /**
   * @param lang the desired language.
   * @return text with the given lang
   */
  String getText(String lang);

  /**
   * sets (means: "add" or "replace") a text with a given locale, which is calculated out of the lang string
   *
   * @param lang the language of <code>text</code>
   * @param text the text content
   */
  void setText(String lang, String text);

  /**
   * sets (means: "add" or "replace") a text with the default locale ("de")
   *
   * @param text the text for default locale.
   */
  void setText(String text);

  /**
   *
   * @return all languages for which translated texts are available.
   */
  Collection<String> getLanguages();

  Set<Translation> getTranslations();

  void setTranslations(Set<Translation> translations);
}
