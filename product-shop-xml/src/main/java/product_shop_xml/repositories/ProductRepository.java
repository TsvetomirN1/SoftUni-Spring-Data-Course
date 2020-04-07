package product_shop_xml.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product_shop_xml.models.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByNameAndPrice(String name, BigDecimal price);

    List<Product> findAllByPriceBetweenAndBuyerIsNull(BigDecimal lower, BigDecimal higher);
}
