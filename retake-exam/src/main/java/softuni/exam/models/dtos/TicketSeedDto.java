package softuni.exam.models.dtos;


import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {


    @XmlElement(name = "serial-number")
    private String serialNumber;
    @XmlElement
    private BigDecimal price;
    @XmlElement(name = "take-off")
    private String takeoff;
    @XmlElement(name = "from-town")
    private FromTownSeedDto fromTown;
    @XmlElement(name = "to-town")
    private ToTownSeedDto toTown;
    @XmlElement(name = "passenger")
    private PassengerSeedTicketDto passenger;
    @XmlElement(name = "plane")
    private PlaneSeedTicketDto plane;

    public TicketSeedDto() {
    }


    @Length(min = 2)
    @Column(unique = true)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    @NotNull
    @Min(value = 0)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    public String getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }


    @NotNull
    public FromTownSeedDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(FromTownSeedDto fromTown) {
        this.fromTown = fromTown;
    }

    @NotNull
    public ToTownSeedDto getToTown() {
        return toTown;
    }

    public void setToTown(ToTownSeedDto toTown) {
        this.toTown = toTown;
    }

    @NotNull
    public PassengerSeedTicketDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerSeedTicketDto passenger) {
        this.passenger = passenger;
    }


    @NotNull
    public PlaneSeedTicketDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneSeedTicketDto plane) {
        this.plane = plane;
    }
}
