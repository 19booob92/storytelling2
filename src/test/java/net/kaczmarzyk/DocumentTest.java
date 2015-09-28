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

    Document submitedDocumentAuthoredByHomerWithEditorBart = document()
            .withStatus(SUBMITED)
            .authoredBy(homer)
            .withEditor(bart)
            .build();
    
    Document rejectedDocumentAuthoredByHomer = document()
            .authoredBy(homer)
            .withStatus(REJECTED)
            .build();

    Document draftDocumentAuthoredByHomerWithEditorBart = document()
            .authoredBy(homer)
            .withEditor(bart)
            .build();
    
    @Test
    public void newDocumentHasStatusDraft() {
        Document document = document().build();
        assertTrue(document.isDraft());
    }

    @Test
    public void authorCanAmendContentOfADraft() {
        draftDocumentAuthoredByHomerWithEditorBart.amend("new content", homer);

        assertThat(draftDocumentAuthoredByHomerWithEditorBart).hasContent("new content");
    }

    @Test
    public void authorCanAmendTheContentOfADraftWithoutCreatingNewRevision() {
        draftDocumentAuthoredByHomerWithEditorBart.amend("new content", homer);

        assertThat(draftDocumentAuthoredByHomerWithEditorBart).hasRevisionNumber(1);
    }

    @Test
    public void documentCanBeAmendedOnlyByAuthor() {
        exception.expect(IllegalArgumentException.class);

        submitedDocumentAuthoredByHomerWithEditorBart.amend("new content", bart);
    }

    @Test
    public void newRevisionIsCreatedWhenAuthorEditsARejectedDocument() {
        rejectedDocumentAuthoredByHomer.amend("new content", homer);

        assertThat(rejectedDocumentAuthoredByHomer).hasRevisionNumber(2);
    }

    @Test
    public void authorCanAmendContentOfARejectedDocument() {
        rejectedDocumentAuthoredByHomer.amend("new content", homer);

        assertThat(rejectedDocumentAuthoredByHomer).hasContent("new content");
    }
    
    @Test
    public void editorMayRejectSubmitedDocument() {
        submitedDocumentAuthoredByHomerWithEditorBart.reject(bart);
        
        assertThat(submitedDocumentAuthoredByHomerWithEditorBart).hasStatus(REJECTED);
    }
    
    @Test
    public void editorCantRejectDocumentThatHasNotBeenSubmited() {
        exception.expect(IllegalStateException.class);
        
        draftDocumentAuthoredByHomerWithEditorBart.reject(bart);
    }
    
    @Test
    public void onlyEditorsMayRejectDocument() {
        exception.expect(IllegalArgumentException.class);
        
        submitedDocumentAuthoredByHomerWithEditorBart.reject(peter);
    }
    
    @Test
    public void editorMayAcceptSubmitedDocument() {
        submitedDocumentAuthoredByHomerWithEditorBart.accept(bart);
        
        assertThat(submitedDocumentAuthoredByHomerWithEditorBart).hasStatus(ACCEPTED);
    }
    
    @Test
    public void onlyEditorMayAcceptDocument() {
        exception.expect(IllegalArgumentException.class);
        
        submitedDocumentAuthoredByHomerWithEditorBart.accept(peter);
    }

    @Test
    public void editorCantAcceptDocumentThatHasNotBeenSubmited() {
        exception.expect(IllegalStateException.class);
        
        draftDocumentAuthoredByHomerWithEditorBart.accept(bart);
    }
}
