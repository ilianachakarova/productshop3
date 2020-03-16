package softuni.productshop3.services;

import softuni.productshop3.domain.dtos.UserSeedDto;
import softuni.productshop3.domain.dtos.UserSoldProductsDto;

import java.util.List;

public interface UserService {
    void seedUsers(UserSeedDto[] userSeedDtos);
    List<UserSoldProductsDto> getUsersWithAtLeastOneProductSold();
}
