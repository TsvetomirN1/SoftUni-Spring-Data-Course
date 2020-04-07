package entities.hospital;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "visit_comment")
public class VisitComment extends Comment {

    private Visit visit;
    public VisitComment(){

    }

    public VisitComment(String text) {
        super(text);
    }

    @ManyToOne
    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }
}
