package product_shop_xml.services;


import product_shop_xml.models.dtos.seedDtos.ProductSeedDto;
import product_shop_xml.models.dtos.viewDtos.query1.ProductViewRootDto;


import java.util.List;

public interface ProductService {

    void seedProducts(List<ProductSeedDto> productSeedDtos);

    ProductViewRootDto getAllProductAndSellers();
}
