package product_shop_xml.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import product_shop_xml.constants.GlobalConstants;
import product_shop_xml.models.dtos.seedDtos.CategorySeedRootDto;
import product_shop_xml.models.dtos.seedDtos.ProductSeedRootDto;
import product_shop_xml.models.dtos.seedDtos.UserSeedRootDto;
import product_shop_xml.models.dtos.viewDtos.query1.ProductViewRootDto;
import product_shop_xml.models.dtos.viewDtos.query2.UserViewRootDto;
import product_shop_xml.services.CategoryService;
import product_shop_xml.services.ProductService;
import product_shop_xml.services.UserService;
import product_shop_xml.utils.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

import static product_shop_xml.constants.GlobalConstants.USERS_FILE_PATH;

@Component
public class AppController implements CommandLineRunner {

    private final XMLParser xmlParser;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final CategoryService categoryService;


    @Autowired
    public AppController(XMLParser xmlParser, UserService userService, ModelMapper modelMapper, ProductService productService, CategoryService categoryService) {
        this.xmlParser = xmlParser;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {


//        this.seedUsers();
//        this.seedCategories();
//        this.seedProducts();

//        this.writeProductsInRange();
        this.writeSoldProducts();
    }

    private void writeSoldProducts() throws JAXBException {

        UserViewRootDto userViewRootDto = this.userService.getUsersSoldProduct();
        this.xmlParser
                .marshalToFile(GlobalConstants.EX_2_OUTPUT,userViewRootDto);

    }

    private void writeProductsInRange() throws JAXBException {

        ProductViewRootDto productViewRootDto = this.productService.getAllProductAndSellers();
        this.xmlParser
                .marshalToFile(GlobalConstants.EX_1_OUTPUT,productViewRootDto);

    }

    private void seedCategories() throws JAXBException, FileNotFoundException {

        CategorySeedRootDto categorySeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.CATEGORIES_FILE_PATH,CategorySeedRootDto.class);

        this.categoryService.seedCategories(categorySeedRootDto.getCategories());
    }

    private void seedProducts() throws JAXBException, FileNotFoundException {

        ProductSeedRootDto productSeedRootDto = this.xmlParser
                .unmarshalFromFile(GlobalConstants.PRODUCTS_FILE_PATH, ProductSeedRootDto.class);

        this.productService.seedProducts(productSeedRootDto.getProducts());
    }

    private void seedUsers() throws JAXBException, FileNotFoundException {

        UserSeedRootDto userSeedRootDto = this.xmlParser
                .unmarshalFromFile(USERS_FILE_PATH, UserSeedRootDto.class);

        this.userService.seedUsers(userSeedRootDto.getUsers());
    }
}
