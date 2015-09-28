package net.kaczmarzyk;

import static net.kaczmarzyk.DocumentAssert.assertThat;
import static net.kaczmarzyk.DocumentBuilder.document;
import static net.kaczmarzyk.storytelling.RevisionStatus.*;
import static org.junit.Assert.assertTrue;
import net.kaczmarzyk.storytelling.Document;
import net.kaczmarzyk.storytelling.Person;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class DocumentTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    Person homer = new Person("Homer Simpson");
    Person bart = new Person("Bart Simpson");
    Person peter = new Person("Peter Griffin");

    @Test
    public void newDocumentHasStatusDraft() {
        Document document = document().build();
        assertTrue(document.isDraft());
    }

    @Test
    public void authorCanAmendContentOfADraft() {
        Document document = document()
                .authoredBy(homer)
                .build();

        document.amend("new content", homer);

        assertThat(document).hasContent("new content");
    }

    @Test
    public void authorCanAmendTheContentOfADraftWithoutCreatingNewRevision() {
        Document document = document()
                .authoredBy(homer)
                .build();

        document.amend("new content", homer);

        assertThat(document).hasRevisionNumber(1);
    }

    @Test
    public void documentCanBeAmendedOnlyByAuthor() {
        Document document = document()
                .authoredBy(homer)
                .build();

        exception.expect(IllegalArgumentException.class);

        document.amend("new content", bart);
    }

    @Test
    public void newRevisionIsCreatedWhenAuthorEditsARejectedDocument() {
        Document document = document()
                .authoredBy(homer)
                .withStatus(REJECTED)
                .build();

        document.amend("new content", homer);

        assertThat(document).hasRevisionNumber(2);
    }

    @Test
    public void authorCanAmendContentOfARejectedDocument() {
        Document document = document()
                .authoredBy(homer)
                .withStatus(REJECTED)
                .build();

        document.amend("new content", homer);

        assertThat(document).hasContent("new content");
    }
    
    @Test
    public void editorMayRejectSubmitedDocument() {
        Document document = document()
                .withStatus(SUBMITED)
                .authoredBy(homer)
                .withEditor(bart)
                .build();
        
        document.reject(bart);
        
        assertThat(document).hasStatus(REJECTED);
    }
    
    @Test
    public void editorCantRejectDocumentThatHasNotBeenSubmited() {
        Document document = document()
                .withStatus(DRAFT)
                .authoredBy(homer)
                .withEditor(bart)
                .build();
        
        exception.expect(IllegalStateException.class);
        
        document.reject(bart);
    }
    
    @Test
    public void onlyEditorsMayRejectDocument() {
        Document document = document()
                .withStatus(SUBMITED)
                .authoredBy(homer)
                .withEditor(bart)
                .build();
        
        exception.expect(IllegalArgumentException.class);
        
        document.reject(peter);
    }
    
    @Test
    public void editorMayAcceptSubmitedDocument() {
        Document document = document()
                .withStatus(SUBMITED)
                .authoredBy(homer)
                .withEditor(bart)
                .build();
        
        document.accept(bart);
        
        assertThat(document).hasStatus(ACCEPTED);
    }
    
    @Test
    public void onlyEditorMayAcceptDocument() {
        Document document = document()
                .withStatus(SUBMITED)
                .authoredBy(homer)
                .withEditor(bart)
                .build();
        
        exception.expect(IllegalArgumentException.class);
        
        document.accept(peter);
    }
    
    @Test
    public void editorCantAcceptDocumentThatHasNotBeenSubmited() {
        Document document = document()
                .withStatus(DRAFT)
                .authoredBy(homer)
                .withEditor(bart)
                .build();
        
        exception.expect(IllegalStateException.class);
        
        document.accept(bart);
    }
}
