package hello;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = CategoryImpl.class)
public interface Category extends Entity {

  Text getLabel();

  void setLabel(Text label);

}
