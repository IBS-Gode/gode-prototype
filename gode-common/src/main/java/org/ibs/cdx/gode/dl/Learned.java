package org.ibs.cdx.gode.dl;

public interface Learned<T,Character> extends LearningRequired<T,Character>{

    void setCharacter(Character character);
    Character getCharacter();

}
