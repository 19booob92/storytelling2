package net.kaczmarzyk;

import static net.kaczmarzyk.DocumentAssert.assertThat;
import static net.kaczmarzyk.DocumentBuilder.document;
import static net.kaczmarzyk.storytelling.RevisionStatus.REJECTED;
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
}
