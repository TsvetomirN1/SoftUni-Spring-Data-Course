package productsShop.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.models.dtos.ProductAndSellerDto;
import productsShop.models.dtos.ProductSeedDto;
import productsShop.models.entities.Category;
import productsShop.models.entities.Product;
import productsShop.repositories.ProductRepository;
import productsShop.services.CategoryService;
import productsShop.services.ProductService;
import productsShop.services.UserService;
import productsShop.utils.ValidationUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {

        if (this.productRepository.count() != 0) {
            return;
        }
        Arrays.stream(productSeedDtos)
                .forEach(productSeedDto -> {
                    if (this.validationUtil.isValid(productSeedDto)) {
                        Product product = this.modelMapper
                                .map(productSeedDto, Product.class);

                        product.setSeller(this.userService.getRandomUser());

                        Random random = new Random();
                        int randomNum = random.nextInt(2);

                        if (randomNum == 1) {
                            product.setBuyer(this.userService.getRandomUser());
                        }

                        product.setCategories(new HashSet<>(this.categoryService.getRandomCategories()));

                        this.productRepository.saveAndFlush(product);

                    } else {
                        this.validationUtil.getViolations(productSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public List<ProductAndSellerDto> getAllProductsAndSellers() {


        return this.productRepository.findAllByPriceBetweenAndBuyerIsNull
                (BigDecimal.valueOf(500), BigDecimal.valueOf(1000))
                .stream()
                .map(p -> {
                    ProductAndSellerDto productAndSellerDto = this.modelMapper.map(p, ProductAndSellerDto.class);

                    productAndSellerDto.setSeller(String.format("%s %s ",
                            p.getSeller().getFirstName(), p.getSeller().getLastName()));

                    return productAndSellerDto;
                }).collect(Collectors.toList());
    }
}
