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
        this.status = RevisionStatus.REJECTED;
    }
    
    public String getContent() {
        return content;
    }
    
    public RevisionStatus getStatus() {
        return status;
    }
}
