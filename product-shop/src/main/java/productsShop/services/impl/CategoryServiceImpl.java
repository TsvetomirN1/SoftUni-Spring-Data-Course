package productsShop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.models.dtos.CategoriesByCountDto;
import productsShop.models.dtos.CategorySeedDto;
import productsShop.models.entities.Category;
import productsShop.repositories.CategoryRepository;
import productsShop.services.CategoryService;
import productsShop.utils.ValidationUtil;

import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {

        if (this.categoryRepository.count() != 0) {
            return;
        }

        Arrays.stream(categorySeedDtos)
                .forEach(categorySeedDto -> {
                    if (this.validationUtil.isValid(categorySeedDto)) {
                        Category category = this.modelMapper
                                .map(categorySeedDto, Category.class);

                        this.categoryRepository.saveAndFlush(category);
                    } else {
                        this.validationUtil.getViolations(categorySeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public List<Category> getRandomCategories() {

        Random random = new Random();
        List<Category> resultList = new ArrayList<>();
        int randomCount = random.nextInt(3) + 1;
        for (int i = 0; i < randomCount; i++) {

            long randomId = random
                    .nextInt((int) this.categoryRepository.count()) + 1;
            resultList.add(this.categoryRepository.getOne(randomId));

        }

        return resultList;
    }

    @Override
    public List<CategoriesByCountDto> findCategoriesWithProductCount() {

        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoriesByCountDto(c.getName(), c.getProducts().size(),
                        c.getProducts().size() == 0 ? 0 :
                                c.getProducts().stream()
                                        .mapToDouble(p -> p.getPrice().doubleValue()).average().getAsDouble(),
                        c.getProducts().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum()))
                .sorted(Comparator.comparingInt(x -> -x.getProductsCount()))
                .collect(Collectors.toList());
    }
}
