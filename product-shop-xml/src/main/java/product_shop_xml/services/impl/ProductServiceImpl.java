package product_shop_xml.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import product_shop_xml.models.dtos.seedDtos.ProductSeedDto;
import product_shop_xml.models.dtos.viewDtos.query1.ProductViewDto;
import product_shop_xml.models.dtos.viewDtos.query1.ProductViewRootDto;
import product_shop_xml.models.entities.Product;
import product_shop_xml.repositories.ProductRepository;
import product_shop_xml.services.CategoryService;
import product_shop_xml.services.ProductService;
import product_shop_xml.services.UserService;
import product_shop_xml.utils.ValidationUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
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
    private final Random random;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Random random, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.random = random;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedProducts(List<ProductSeedDto> productSeedDtos) {

        productSeedDtos
                .forEach(productSeedDto -> {
                    if (this.validationUtil.isValid(productSeedDto)) {
                        if (this.productRepository.findByNameAndPrice(productSeedDto.getName(),
                                productSeedDto.getPrice()) == null) {

                            Product product = this.modelMapper.map(productSeedDto, Product.class);

                            product.setSeller(this.userService.getRandomUser());

                            int randomNum = random.nextInt(2);

                            if (randomNum == 1) {
                                product.setBuyer(this.userService.getRandomUser());
                            }
                            product.setCategories(new HashSet<>(this.categoryService.getRandomCategories()));

                            this.productRepository.saveAndFlush(product);

                        } else {
                            System.out.println("Already in database");
                        }

                    } else {
                        this.validationUtil
                                .getViolations(productSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }

                });
    }

    @Override
    public ProductViewRootDto getAllProductAndSellers() {

        ProductViewRootDto productViewRootDto = new ProductViewRootDto();

        List<ProductViewDto> productViewDtos = this.productRepository
                .findAllByPriceBetweenAndBuyerIsNull(BigDecimal.valueOf(500), BigDecimal.valueOf(1000))
                .stream().map(product -> {
                    ProductViewDto productViewDto = this.modelMapper.map(product, ProductViewDto.class);
                    productViewDto.setName(product.getName());
                    productViewDto.setPrice(product.getPrice());

                    productViewDto.setSeller(String.format("%s %s ",
                            product.getSeller().getFirstName(), product.getSeller().getLastName()));
                    return productViewDto;
                })
                .collect(Collectors.toList());

        productViewRootDto.setProducts(productViewDtos);

        return productViewRootDto;

    }

}
