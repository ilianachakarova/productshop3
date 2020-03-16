package softuni.productshop3.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.productshop3.domain.dtos.ProductByUserDto;
import softuni.productshop3.domain.dtos.UserSeedDto;
import softuni.productshop3.domain.dtos.UserSoldProductsDto;
import softuni.productshop3.domain.entities.Product;
import softuni.productshop3.domain.entities.User;
import softuni.productshop3.repository.UserRepository;
import softuni.productshop3.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if(!this.validationUtil.isValid(userSeedDto)){
                this.validationUtil.violations(userSeedDto).
                        forEach(v-> System.out.println(v.getMessage()));
                continue;
            }

            User user = this.modelMapper.map(userSeedDto, User.class);
            this.userRepository.saveAndFlush(user);
        }
    }
    @Transactional
    @Override
    public List<UserSoldProductsDto> getUsersWithAtLeastOneProductSold() {
        List<User>users = this.userRepository.getUsersByProductAtLeastOne();
        List<UserSoldProductsDto>userSoldProductsDtos = new ArrayList<>();
        for (User user : users) {
            UserSoldProductsDto userSoldProductsDto = this.modelMapper.map(user, UserSoldProductsDto.class);
            userSoldProductsDto.setProductByUserDtos(this.setProductsSold(user.getId()));
            System.out.println();
            userSoldProductsDtos.add(userSoldProductsDto);
        }

        return userSoldProductsDtos;
    }

    private List<ProductByUserDto> setProductsSold(long id) {
        User user = this.userRepository.getOne(id);
        List<ProductByUserDto>productByUserDtos = new ArrayList<>();
        Set<Product> userProducts = user.getProductsSold();
        for (Product userProduct : userProducts) {
            ProductByUserDto productByUserDto = this.modelMapper.map(userProduct,ProductByUserDto.class);
            if(userProduct.getBuyer().getFirstName() != null){
                productByUserDto.setBuyerFirstName(userProduct.getSeller().getFirstName());
            }else {
                productByUserDto.setBuyerFirstName("");
            }

          productByUserDto.setBuyerLastName(userProduct.getSeller().getLastName());
          productByUserDtos.add(productByUserDto);
        }
//        user.getProductsBought().stream().forEach(product -> {
//            ProductByUserDto productByUserDto = this.modelMapper.map(product,ProductByUserDto.class);
//           productByUserDto.setBuyerFirstName(product.getBuyer().getFirstName());
//            productByUserDto.setBuyerLastName(product.getBuyer().getLastName());
//            productByUserDtos.add(productByUserDto);
//        });
        return productByUserDtos;
    }
}
