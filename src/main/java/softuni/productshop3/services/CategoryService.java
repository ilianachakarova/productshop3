package softuni.productshop3.services;

import softuni.productshop3.domain.dtos.CategoryByProductCountDto;
import softuni.productshop3.domain.dtos.CategorySeedDto;

import java.util.List;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);
    List<CategoryByProductCountDto>getCategoriesByProductCount();
}
