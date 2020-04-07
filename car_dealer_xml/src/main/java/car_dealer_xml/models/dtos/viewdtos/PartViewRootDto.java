package car_dealer_xml.models.dtos.viewdtos;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartViewRootDto {

    @XmlElement(name = "part")
    private List<PartViewDto> parts;

    public PartViewRootDto() {
    }

    public List<PartViewDto> getParts() {
        return parts;
    }

    public void setParts(List<PartViewDto> parts) {
        this.parts = parts;
    }
}
