package softuni.productshop3.services;

import softuni.productshop3.domain.dtos.ProductInRangeDto;
import softuni.productshop3.domain.dtos.ProductSeedDto;

import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDto[] productSeedDtos);
    List<ProductInRangeDto> productsInRange();
}
