package productsShop.services;

import productsShop.models.dtos.CategoriesByCountDto;
import productsShop.models.dtos.CategorySeedDto;
import productsShop.models.entities.Category;

import java.util.List;

public interface CategoryService {

    void seedCategories(CategorySeedDto[] categorySeedDtos);

    List<Category> getRandomCategories();

    List<CategoriesByCountDto> findCategoriesWithProductCount();
}
