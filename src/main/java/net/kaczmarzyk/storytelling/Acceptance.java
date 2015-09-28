package net.kaczmarzyk.storytelling;

import java.time.LocalDateTime;
import java.util.Objects;


public class Acceptance {

    private Person acceptingEditor;
    private LocalDateTime date;
    
    public Acceptance(Person editor) {
        this.acceptingEditor = editor;
        this.date = LocalDateTime.now();
    }

    boolean isMadeBy(Person editor) {
        return Objects.equals(acceptingEditor, editor);
    }

}
