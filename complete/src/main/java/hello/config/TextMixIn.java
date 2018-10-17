package hello.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hello.TextImpl;

@JsonDeserialize(as = TextImpl.class)
public abstract class TextMixIn {

}
