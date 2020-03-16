package softuni.productshop3.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.productshop3.domain.dtos.*;
import softuni.productshop3.services.CategoryService;
import softuni.productshop3.services.ProductService;
import softuni.productshop3.services.UserService;
import softuni.productshop3.util.FileIOUtil;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {
    private static final String USER_FILE_PATH =
            "C:\\Users\\user l\\Desktop\\productshop3\\src\\main\\resources\\files\\users.json";
    private static final String CATEGORY_FILE_PATH =
            "C:\\Users\\user l\\Desktop\\productshop3\\src\\main\\resources\\files\\categories.json";
    private static final String PRODUCT_FILE_PATH =
            "C:\\Users\\user l\\Desktop\\productshop3\\src\\main\\resources\\files\\products.json";
    private static final String OUTPUT1_PATH =
            "C:\\Users\\user l\\Desktop\\productshop3\\src\\main\\resources\\files\\output\\products-in-range.json";
    private static final String OUTPUT2_PATH =
            "C:\\Users\\user l\\Desktop\\productshop3\\src\\main\\resources\\files\\output\\categories-by-product-count.json";
    private static final String OUTPUT3_PATH =
            "C:\\Users\\user l\\Desktop\\productshop3\\src\\main\\resources\\files\\output\\users-sold-products.json";
    private final FileIOUtil fileIOUtil;
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final Gson gson;
    @Autowired
    public ProductShopController(FileIOUtil fileIOUtil, UserService userService, ProductService productService, CategoryService categoryService, Gson gson) {
        this.fileIOUtil = fileIOUtil;
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.seedUsers();
//        this.seedCategories();
//        this.seedProducts();
        
       // this.productsInRange();
        //this.categoriesByProductCount();
        this.successfullySoldProducts();

        
    }

    private void successfullySoldProducts() throws IOException {
        List<UserSoldProductsDto>userSoldProductsDtos = this.userService.getUsersWithAtLeastOneProductSold();
        String json = this.gson.toJson(userSoldProductsDtos);
        this.fileIOUtil.writeFile(json,OUTPUT3_PATH);

    }

    private void categoriesByProductCount() throws IOException {
        List<CategoryByProductCountDto> categoryByProductCountDtos = this.categoryService.getCategoriesByProductCount();
        String json = this.gson.toJson(categoryByProductCountDtos);
        this.fileIOUtil.writeFile(json,OUTPUT2_PATH);
    }

    private void productsInRange() throws IOException {
        List<ProductInRangeDto> productInRangeDtos = this.productService.productsInRange();
        String json = this.gson.toJson(productInRangeDtos);
        this.fileIOUtil.writeFile(json,OUTPUT1_PATH);
    }


    private void seedProducts() throws IOException {
        String json = this.fileIOUtil.readFile(PRODUCT_FILE_PATH);
        ProductSeedDto[] productSeedDtos = this.gson.fromJson(json,ProductSeedDto[].class);
        this.productService.seedProducts(productSeedDtos);
    }

    private void seedCategories() throws IOException {
        String json = this.fileIOUtil.readFile(CATEGORY_FILE_PATH);
        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(json,CategorySeedDto[].class);
        this.categoryService.seedCategories(categorySeedDtos);
    }

    private void seedUsers() throws IOException {
        String json = this.fileIOUtil.readFile(USER_FILE_PATH);
        UserSeedDto[] userSeedDtos = this.gson.fromJson(json, UserSeedDto[].class);
        this.userService.seedUsers(userSeedDtos);
    }
}
