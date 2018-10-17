package hello.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import hello.TranslationImpl;

@JsonDeserialize(as = TranslationImpl.class)
public abstract class TranslationMixIn {

}
