package softuni.productshop3.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.productshop3.domain.dtos.ProductInRangeDto;
import softuni.productshop3.domain.dtos.ProductSeedDto;
import softuni.productshop3.domain.entities.Category;
import softuni.productshop3.domain.entities.Product;
import softuni.productshop3.domain.entities.User;
import softuni.productshop3.repository.CategoryRepository;
import softuni.productshop3.repository.ProductRepository;
import softuni.productshop3.repository.UserRepository;
import softuni.productshop3.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, UserRepository userRepository, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedProducts(ProductSeedDto[] productSeedDtos) {
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            if(!this.validationUtil.isValid(productSeedDto)){
                this.validationUtil.violations(productSeedDto).
                        forEach(v-> System.out.println(v.getMessage()));
                continue;
            }

            Product product = this.modelMapper.map(productSeedDto, Product.class);
            product.setSeller(getRandomUser());
            Random random = new Random();
            if(random.nextInt() % 13 != 0){
                product.setBuyer(getRandomUser());
            }
            product.setCategories(getRandomCategories());

            this.productRepository.saveAndFlush(product);

        }
    }

    @Override
    public List<ProductInRangeDto> productsInRange() {
        List<Product> products =
                this.productRepository.
                        findAllByPriceIsBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal.valueOf(500),BigDecimal.valueOf(1000));
        List<ProductInRangeDto> productInRangeDtos = new ArrayList<>();
        for (Product product : products) {
            ProductInRangeDto productInRangeDto = this.modelMapper.map(product, ProductInRangeDto.class);
            productInRangeDto.setSeller(String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName()));
            productInRangeDtos.add(productInRangeDto);
        }
        return productInRangeDtos;
    }

    private User getRandomUser(){
        Random random = new Random();
        long randomId = random.nextInt((int) this.userRepository.count())+1;
        return this.userRepository.getOne(randomId);
    }
    private List<Category> getRandomCategories(){
        Random random = new Random();
        List<Category> categories = new ArrayList<>();
        long randomId = random.nextInt((int) this.categoryRepository.count())+1;

        for (int i = 0; i < random.nextInt(3)+1; i++) {
            categories.add(this.categoryRepository.getOne(randomId));
        }
        return categories;
    }
}
