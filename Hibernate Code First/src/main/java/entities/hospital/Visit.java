package entities.hospital;


import entities.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

    private Patient patient;
    private Diagnose diagnose;
    private Medicament medicament;
    private LocalDateTime visitDate;
    private List<VisitComment> comments;

    public Visit(){

    }

    public Visit(Patient patient, Diagnose diagnoses, Medicament medicament){
        this.patient = patient;
        this.diagnose = diagnoses;
        this.medicament = medicament;
        this.comments = new ArrayList<VisitComment>();
    }


    @ManyToOne(targetEntity = Patient.class, cascade = CascadeType.ALL)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }


    @OneToMany(mappedBy = "visit")
    public List<VisitComment> getComments() {
        return comments;
    }

    public void setComments(List<VisitComment> comments) {
        this.comments = comments;
    }

    @Column(name = "visit_date")
    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
        this.visitDate = visitDate;
    }
}
