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


public class DocumentAmendingTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    Person author = new Person("Homer Simpson");
    Person editor = new Person("Bart Simpson");
    Person sbElse = new Person("Peter Griffin");

    Document submitedDocument = document()
            .withStatus(SUBMITED)
            .authoredBy(author)
            .withEditor(editor)
            .build();
    
    Document rejectedDocument = document()
            .authoredBy(author)
            .withStatus(REJECTED)
            .build();

    Document draftDocument = document()
            .authoredBy(author)
            .withEditor(editor)
            .build();
    
    @Test
    public void authorCanAmendContentOfADraft() {
        draftDocument.amend("new content", author);

        assertThat(draftDocument).hasContent("new content");
    }

    @Test
    public void authorCanAmendTheContentOfADraftWithoutCreatingNewRevision() {
        draftDocument.amend("new content", author);

        assertThat(draftDocument).hasRevisionNumber(1);
    }

    @Test
    public void documentCanBeAmendedOnlyByAuthor() {
        exception.expect(IllegalArgumentException.class);

        submitedDocument.amend("new content", editor);
    }

    @Test
    public void newRevisionIsCreatedWhenAuthorEditsARejectedDocument() {
        rejectedDocument.amend("new content", author);

        assertThat(rejectedDocument).hasRevisionNumber(2);
    }

    @Test
    public void authorCanAmendContentOfARejectedDocument() {
        rejectedDocument.amend("new content", author);

        assertThat(rejectedDocument).hasContent("new content");
    }
}
