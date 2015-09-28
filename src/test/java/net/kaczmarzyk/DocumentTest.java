package net.kaczmarzyk;

import static net.kaczmarzyk.DocumentBuilder.document;
import static org.junit.Assert.assertTrue;
import net.kaczmarzyk.storytelling.Document;

import org.junit.Test;


public class DocumentTest {

    @Test
    public void newDocumentHasStatusDraft() {
        Document document = document().build();
        assertTrue(document.isDraft());
    }
}
