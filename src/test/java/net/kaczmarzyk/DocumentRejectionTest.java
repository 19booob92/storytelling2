package net.kaczmarzyk;

import static net.kaczmarzyk.DocumentAssert.assertThat;
import static net.kaczmarzyk.DocumentBuilder.document;
import static net.kaczmarzyk.storytelling.RevisionStatus.REJECTED;
import static net.kaczmarzyk.storytelling.RevisionStatus.SUBMITED;
import net.kaczmarzyk.storytelling.Document;
import net.kaczmarzyk.storytelling.Person;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class DocumentRejectionTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    Person editor = new Person("Bart Simpson");
    Person sbElse = new Person("Peter Griffin");
    
    Document submitedDocument = document()
            .withStatus(SUBMITED)
            .withEditor(editor)
            .build();
    
    Document draftDocument = document()
            .withEditor(editor)
            .build();
    
    @Test
    public void editorMayRejectSubmitedDocument() {
        submitedDocument.reject(editor);
        
        assertThat(submitedDocument).hasStatus(REJECTED);
    }
    
    @Test
    public void editorCantRejectDocumentThatHasNotBeenSubmited() {
        exception.expect(IllegalStateException.class);
        
        draftDocument.reject(editor);
    }
    
    @Test
    public void onlyEditorsMayRejectDocument() {
        exception.expect(IllegalArgumentException.class);
        
        submitedDocument.reject(sbElse);
    }
}
