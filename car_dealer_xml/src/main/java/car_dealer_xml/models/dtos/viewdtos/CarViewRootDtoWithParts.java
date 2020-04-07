package car_dealer_xml.models.dtos.viewdtos;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarViewRootDtoWithParts {


    @XmlElement(name = "car")
    List<CarViewDtoWithParts> cars;

    public CarViewRootDtoWithParts() {
    }

    public List<CarViewDtoWithParts> getCars() {
        return cars;
    }

    public void setCars(List<CarViewDtoWithParts> cars) {
        this.cars = cars;
    }
}
