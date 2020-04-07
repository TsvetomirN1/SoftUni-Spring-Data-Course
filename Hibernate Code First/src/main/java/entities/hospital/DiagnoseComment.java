package entities.hospital;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "diagnose_comment")
public class DiagnoseComment extends Comment {

    private Diagnose diagnose;

    public DiagnoseComment() {

    }

    public DiagnoseComment(String text) {
        super(text);
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }
}
