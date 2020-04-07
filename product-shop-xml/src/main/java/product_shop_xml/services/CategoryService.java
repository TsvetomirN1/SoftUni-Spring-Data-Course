package product_shop_xml.services;

import product_shop_xml.models.dtos.seedDtos.CategorySeedDto;
import product_shop_xml.models.entities.Category;

import java.util.List;

public interface CategoryService {

    void seedCategories(List<CategorySeedDto> categorySeedDtos);

    List<Category> getRandomCategories();


}
