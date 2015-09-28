package net.kaczmarzyk;

import java.util.Arrays;

import net.kaczmarzyk.storytelling.Document;
import net.kaczmarzyk.storytelling.RevisionStatus;
import net.kaczmarzyk.storytelling.Person;


public class DocumentBuilder {

    private String content = "dummy content";
    private Person author = new Person("Author");
    private Person editor = new Person("Editor");
    private Person reviewer = new Person("Reviewer");
    private RevisionStatus status;
    
    private DocumentBuilder() {
    }
    
    public DocumentBuilder authoredBy(Person author) {
        this.author = author;
        return this;
    }
    
    public DocumentBuilder withStatus(RevisionStatus status) {
        this.status = status;
        return this;
    }
    
    public DocumentBuilder withEditor(Person editor) {
        this.editor = editor;
        return this;
    }
    
    public Document build() {
        Document doc = new Document(content, author, editor, reviewer);
        if (Arrays.asList(RevisionStatus.SUBMITED,
                RevisionStatus.REJECTED,
                RevisionStatus.ACCEPTED).contains(status)) {
            
            doc.submit(author);
        }
        if (status == RevisionStatus.REJECTED) {
            doc.reject(editor);
        }
        return doc;
    }
    
    public static DocumentBuilder document() {
        return new DocumentBuilder();
    }
}
