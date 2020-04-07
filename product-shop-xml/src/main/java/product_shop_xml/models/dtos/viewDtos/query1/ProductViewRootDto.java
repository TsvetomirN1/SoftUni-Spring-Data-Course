package product_shop_xml.models.dtos.viewDtos.query1;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductViewRootDto {

    @XmlElement(name = "product")
    private List<ProductViewDto> products;

    public ProductViewRootDto() {
    }

    public List<ProductViewDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductViewDto> products) {
        this.products = products;
    }
}
