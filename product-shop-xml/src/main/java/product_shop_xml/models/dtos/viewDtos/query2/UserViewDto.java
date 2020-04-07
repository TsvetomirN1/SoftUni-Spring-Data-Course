package product_shop_xml.models.dtos.viewDtos.query2;


import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElement(name = "sold-products")
    private SoldProductRootDto soldProducts;

    public UserViewDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SoldProductRootDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductRootDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
