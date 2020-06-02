package org.ibs.cdx.gode.dl;

import java.io.Serializable;
import java.util.List;

public interface LearningRequired<T,Character> extends Serializable {

    List<Character> characters();
    List<String> analysedFields();
}
