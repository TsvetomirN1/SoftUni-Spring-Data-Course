package product_shop_xml.models.dtos.viewDtos.query2;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductRootDto {

    @XmlElement(name = "product")
    private Set<SoldProductDto> soldProducts;

    public SoldProductRootDto() {
    }

    public Set<SoldProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<SoldProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
