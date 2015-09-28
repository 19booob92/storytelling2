package net.kaczmarzyk.storytelling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Document {

    private List<Revision> revisions = new ArrayList<>();
    
    private Person author;
    private Collection<Person> editors;
    private Person reviewer;
    
    
    public Document(String content, Person author, Collection<Person> editors, Person reviewer) {
        this.author = author;
        this.editors = new ArrayList<Person>(editors);
        this.reviewer = reviewer;
        this.revisions.add(new Revision(content));
    }
    
    public void amend(String newContent, Person amendmentAuthor) {
        if (!Objects.equals(author, amendmentAuthor)) {
            throw new IllegalArgumentException("only authors may make amendments");
        }
        if (isDraft()) {
            revisions.remove(revisions.size() - 1);
        }
        revisions.add(new Revision(newContent));
    }

    public boolean isDraft() {
        return getLastRevision().getStatus() == RevisionStatus.DRAFT;
    }
    
    public void submit(Person submitter) {
        getLastRevision().submit();
    }
    
    public void reject(Person rejectingEditor) {
        if (!editors.contains(rejectingEditor)) {
            throw new IllegalArgumentException("only editor may reject document");
        }
        getLastRevision().reject();
    }
    
    public void accept(Person acceptingEditor) {
        if (!editors.contains(acceptingEditor)) {
            throw new IllegalArgumentException("only editor may reject document");
        }
        Revision lastRevision = getLastRevision();
        lastRevision.accept(acceptingEditor);
        lastRevision.markAcceptedIfReady(editors);
    }

    public List<Revision> getRevisions() {
        return Collections.unmodifiableList(revisions);
    }

    public Revision getLastRevision() {
        return revisions.get(revisions.size() - 1);
    }
}
