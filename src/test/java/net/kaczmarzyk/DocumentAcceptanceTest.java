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

    Person editor = new Person("Bart Simpson");
    Person editor2 = new Person("Marge Simpson");
    Person sbElse = new Person("Peter Griffin");
    
    Document draftDocument = document()
            .withEditor(editor)
            .build();
    
    @Test
    public void documentIsAcceptedWhenAllEditorsAcceptIt() {
        Document document = document()
                .withStatus(SUBMITED)
                .withEditors(editor, editor2)
                .build();
        
        document.accept(editor);
        document.accept(editor2);
        
        assertThat(document).hasStatus(ACCEPTED);
    }
    
    @Test
    public void documentIsNotAcceptedUntilAllEditorsAcceptIt() {
        Document document = document()
                .withStatus(SUBMITED)
                .withEditors(editor, editor2)
                .build();
        
        document.accept(editor);
        
        assertThat(document).hasStatus(SUBMITED);
    }
    
    @Test
    public void editorCantAcceptDocumentMoreThanOnce() {
        Document document = document()
                .withStatus(SUBMITED)
                .withEditors(editor)
                .build();
        
        document.accept(editor);
        
        exception.expect(IllegalStateException.class);
        
        document.accept(editor);
    }
    
    @Test
    public void onlyEditorMayAcceptDocument() {
        Document document = document()
                .withStatus(SUBMITED)
                .withEditors(editor)
                .build();
        
        exception.expect(IllegalArgumentException.class);
        
        document.accept(sbElse);
    }

    @Test
    public void editorCantAcceptDocumentThatHasNotBeenSubmited() {
        exception.expect(IllegalStateException.class);
        
        draftDocument.accept(editor);
    }
}
