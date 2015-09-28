package net.kaczmarzyk.storytelling;


public class Revision {

    private String content;
    private RevisionStatus status;
    
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
    
    void accept() {
        if (status != RevisionStatus.SUBMITED) {
            throw new IllegalStateException("only submited document may be accepted");
        }
        this.status = RevisionStatus.ACCEPTED;
    }
    
    public String getContent() {
        return content;
    }
    
    public RevisionStatus getStatus() {
        return status;
    }
}
