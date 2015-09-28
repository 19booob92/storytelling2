package net.kaczmarzyk;

import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.storytelling.Document;
import net.kaczmarzyk.storytelling.RevisionStatus;
import net.kaczmarzyk.storytelling.Person;


public class DocumentBuilder {

    private String content = "dummy content";
    private Person author = new Person("Author");
    private List<Person> editors = Arrays.asList(new Person("Editor"));
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
        return withEditors(editor);
    }
    
    public DocumentBuilder withEditors(Person... editors) {
        this.editors = Arrays.asList(editors);
        return this;
    }
    
    public Document build() {
        Document doc = new Document(content, author, editors, reviewer);
        if (Arrays.asList(RevisionStatus.SUBMITED,
                RevisionStatus.REJECTED,
                RevisionStatus.ACCEPTED).contains(status)) {
            
            doc.submit(author);
        }
        if (status == RevisionStatus.REJECTED) {
            doc.reject(editors.get(0));
        }
        return doc;
    }
    
    public static DocumentBuilder document() {
        return new DocumentBuilder();
    }
}
