package productsShop.controllers;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import productsShop.models.dtos.*;
import productsShop.services.CategoryService;
import productsShop.services.ProductService;
import productsShop.services.UserService;
import productsShop.utils.FileUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static productsShop.constants.GlobalConstants.*;

@Component
public class AppController implements CommandLineRunner {

    private final Gson gson;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final FileUtil fileUtil;

    @Autowired
    public AppController(Gson gson, CategoryService categoryService, UserService userService, ProductService productService, FileUtil fileUtil) {
        this.gson = gson;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.fileUtil = fileUtil;
    }

    @Override
    public void run(String... args) throws Exception {


//        this.seedCategories();
//        this.seedUsers();
//        this.seedProducts();

//        query 1
        this.writeProductAndSeller();

//        query 2
        this.writeUsersSoldProducts();

//        query 3
        this.writeCategoriesByProductCount();

//        query 4
        this.writeUsersAndProducts();
    }

    private void writeUsersAndProducts() throws IOException {

        UsersAndProductsDto dtos = this.userService.getUsersAndProducts();

        String usersAndProductsJson = this.gson.toJson(dtos);
        this.fileUtil.write(usersAndProductsJson, EX_4_OUTPUT);
    }

    private void writeCategoriesByProductCount() throws IOException {

        List<CategoriesByCountDto> dtos = this.categoryService.findCategoriesWithProductCount();
        String categoriesJson = this.gson.toJson(dtos);
        this.fileUtil.write(categoriesJson, EX_3_OUTPUT);
    }

    private void writeUsersSoldProducts() throws IOException {

        List<UserWithSoldProductDto> users = this.userService.findUsersWithSoldProducts();

        String usersJson = this.gson.toJson(users);
        this.fileUtil.write(usersJson, EX_2_OUTPUT);
    }

    private void writeProductAndSeller() throws IOException {

        List<ProductAndSellerDto> dtos = this.productService.getAllProductsAndSellers();

        String json = this.gson.toJson(dtos);

        this.fileUtil.write(json, EX_1_OUTPUT);
    }

    private void seedProducts() throws FileNotFoundException {
        ProductSeedDto[] dtos = this.gson
                .fromJson(new FileReader(PRODUCTS_FILE_PATH),
                        ProductSeedDto[].class);

        this.productService.seedProducts(dtos);

    }

    private void seedUsers() throws FileNotFoundException {

        UserSeedDto[] dtos = this.gson
                .fromJson(new FileReader(USERS_FILE_PATH),
                        UserSeedDto[].class);

        this.userService.seedUsers(dtos);
    }

    private void seedCategories() throws FileNotFoundException {

        CategorySeedDto[] dtos = this.gson
                .fromJson(new FileReader(CATEGORIES_FILE_PATH),
                        CategorySeedDto[].class);

        this.categoryService.seedCategories(dtos);

    }
}
