package softuni.productshop3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.productshop3.domain.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByPriceIsBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal priceMin, BigDecimal priceMax);
}
