package productsShop.services;

import productsShop.models.dtos.ProductAndSellerDto;
import productsShop.models.dtos.ProductSeedDto;

import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);

    List<ProductAndSellerDto> getAllProductsAndSellers();
}
