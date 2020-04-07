package softuni.exam.models.dtos;


import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedTicketDto {


    @XmlElement(name = "register-number")
    private String regNumber;

    public PlaneSeedTicketDto() {
    }


    @Column(unique = true)
    @Length(min = 5)
    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }
}
