package entities.hospital;

import entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diagnose")
public class Diagnose extends BaseEntity {


    private String name;

    private List<DiagnoseComment> comments;
    private List<Visit> visits;


    public Diagnose(){

    }

    public Diagnose(String name){
        this.name = name;
        this.visits = new ArrayList<>();
        this.comments = new ArrayList<>();
    }


    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "diagnose")
    public List<DiagnoseComment> getComments() {
        return comments;
    }

    public void setComments(List<DiagnoseComment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "diagnose")
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }


}
