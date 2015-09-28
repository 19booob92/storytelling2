package net.kaczmarzyk.storytelling;

import java.util.ArrayList;
import java.util.Collection;


public class Revision {

    private String content;
    private RevisionStatus status;
    private Collection<Acceptance> acceptances = new ArrayList<>();
    
    
    public Revision(String content) {
        this.content = content;
        this.status = RevisionStatus.DRAFT;
    }

    void submit() {
        this.status = RevisionStatus.SUBMITED;
    }
    
    void reject() {
        if (status != RevisionStatus.SUBMITED) {
            throw new IllegalStateException("only submited document may be rejected");
        }
        this.status = RevisionStatus.REJECTED;
    }
    
    void accept(Person acceptingEditor) {
        if (status != RevisionStatus.SUBMITED) {
            throw new IllegalStateException("only submited document may be accepted");
        }
        if (hasBeenAcceptedBy(acceptingEditor)) {
            throw new IllegalStateException("the editor already accepted this revision");
        }
        acceptances.add(new Acceptance(acceptingEditor));
    }
    
    void markAcceptedIfReady(Collection<Person> allEditors) {
        if (acceptances.size() == allEditors.size()) {
            this.status = RevisionStatus.ACCEPTED;
        }
    }
    
    private boolean hasBeenAcceptedBy(Person editor) {
        return acceptances.stream()
                .anyMatch(a -> a.isMadeBy(editor));
    }
    
    public String getContent() {
        return content;
    }
    
    public RevisionStatus getStatus() {
        return status;
    }
}
