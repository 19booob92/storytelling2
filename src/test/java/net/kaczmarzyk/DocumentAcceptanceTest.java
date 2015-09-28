package net.kaczmarzyk;

import static net.kaczmarzyk.DocumentAssert.assertThat;
import static net.kaczmarzyk.DocumentBuilder.document;
import static net.kaczmarzyk.storytelling.RevisionStatus.ACCEPTED;
import static net.kaczmarzyk.storytelling.RevisionStatus.SUBMITED;
import net.kaczmarzyk.storytelling.Document;
import net.kaczmarzyk.storytelling.Person;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class DocumentAcceptanceTest {
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    Person author = new Person("Homer Simpson");
    Person editor = new Person("Bart Simpson");
    Person editor2 = new Person("Marge Simpson");
    Person sbElse = new Person("Peter Griffin");
    
    Document submitedDocument = document()
            .withStatus(SUBMITED)
            .authoredBy(author)
            .withEditors(editor, editor2)
            .build();
    
    Document draftDocument = document()
            .authoredBy(author)
            .withEditor(editor)
            .build();
    
    @Test
    public void documentIsAcceptedWhenAllEditorsAcceptIt() {
        submitedDocument.accept(editor);
        submitedDocument.accept(editor2);
        
        assertThat(submitedDocument).hasStatus(ACCEPTED);
    }
    
    @Test
    public void documentIsNotAcceptedUntilAllEditorsAcceptIt() {
        submitedDocument.accept(editor);
        
        assertThat(submitedDocument).hasStatus(SUBMITED);
    }
    
    @Test
    public void editorCantAcceptDocumentMoreThanOnce() {
        submitedDocument.accept(editor);
        
        exception.expect(IllegalStateException.class);
        
        submitedDocument.accept(editor);
    }
    
    @Test
    public void onlyEditorMayAcceptDocument() {
        exception.expect(IllegalArgumentException.class);
        
        submitedDocument.accept(sbElse);
    }

    @Test
    public void editorCantAcceptDocumentThatHasNotBeenSubmited() {
        exception.expect(IllegalStateException.class);
        
        draftDocument.accept(editor);
    }
}
