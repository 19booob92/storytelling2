package net.kaczmarzyk.storytelling;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Document {

    private List<Revision> revisions = new ArrayList<>();
    
    private DocumentStatus status;
    
    private Person author;
    private Person editor;
    private Person reviewer;
    
    
    public Document(String content, Person author, Person editor, Person reviewer) {
        this.status = DocumentStatus.NEW;
        this.author = author;
        this.editor = editor;
        this.reviewer = reviewer;
    }
}
