package product_shop_xml.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product_shop_xml.models.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
