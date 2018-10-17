package hello;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = TranslationImpl.class)
public interface Translation extends Entity {

  public String getText();

  public void setText(String text);

  public String getLang();

  public void setLang(String lang);

  public boolean has(String lang);

}
