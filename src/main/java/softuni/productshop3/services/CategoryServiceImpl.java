package softuni.productshop3.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.productshop3.domain.dtos.CategoryByProductCountDto;
import softuni.productshop3.domain.dtos.CategorySeedDto;
import softuni.productshop3.domain.entities.Category;
import softuni.productshop3.domain.entities.Product;
import softuni.productshop3.repository.CategoryRepository;
import softuni.productshop3.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto categorySeedDto : categorySeedDtos) {
            if(!this.validationUtil.isValid(categorySeedDto)){
                this.validationUtil.violations(categorySeedDto).
                        forEach(v-> System.out.println(v.getMessage()));
                continue;
            }

            Category category = this.modelMapper.map(categorySeedDto, Category.class);
            this.categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public List<CategoryByProductCountDto> getCategoriesByProductCount() {
        List<Category> categories = this.categoryRepository.getCategoriesByProductCount();
        List<CategoryByProductCountDto> categoryByProductCountDtos = new ArrayList<>();
        for (Category category : categories) {
            CategoryByProductCountDto categoryByProductCountDto =
                    this.modelMapper.map(category, CategoryByProductCountDto.class);
            categoryByProductCountDto.setProductCount(category.getProducts().size());
            categoryByProductCountDto.setTotalRevenue(
                    category.getProducts().stream().map(Product::getPrice).mapToDouble(BigDecimal::doubleValue).sum());
            categoryByProductCountDto.setAveragePrice(
                    category.getProducts().stream().map(Product::getPrice).
                            mapToDouble(BigDecimal::doubleValue).sum()/category.getProducts().size());
            categoryByProductCountDtos.add(categoryByProductCountDto);
        }
        return categoryByProductCountDtos;
    }

}
