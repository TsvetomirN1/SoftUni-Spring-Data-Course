package product_shop_xml.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product_shop_xml.models.dtos.seedDtos.CategorySeedDto;
import product_shop_xml.models.entities.Category;
import product_shop_xml.repositories.CategoryRepository;
import product_shop_xml.services.CategoryService;
import product_shop_xml.utils.ValidationUtil;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Random random;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Random random) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.random = random;
    }


    @Override
    public void seedCategories(List<CategorySeedDto> categorySeedDtos) {

        categorySeedDtos
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

        List<Category> resultList = new ArrayList<>();
        int randomCount = random.nextInt(3) + 1;
        for (int i = 0; i < randomCount; i++) {

            long randomId = random
                    .nextInt((int) this.categoryRepository.count()) + 1;
            resultList.add(this.categoryRepository.getOne(randomId));
        }

        return resultList;
    }
}
