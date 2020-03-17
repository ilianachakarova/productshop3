package softuni.productshop3.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.productshop3.domain.dtos.*;
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
    @Transactional
    @Override
    public CountAndUsersDto getUsersCountAndInfo() {
        List<User> users = this.userRepository.getUsersByProductAtLeastOne();
        CountAndUsersDto countAndUsersDto = new CountAndUsersDto();
        countAndUsersDto.setCount(users.size());
        countAndUsersDto.setUsers(this.setUsersAndProducts(users));
        System.out.println();
        return countAndUsersDto;
    }

    private List<UserAndProductsDto> setUsersAndProducts(List<User> users) {
        List<UserAndProductsDto> userAndProductsDtos = new ArrayList<>();
        for (User user : users) {
            UserAndProductsDto userAndProductsDto = modelMapper.map(user,UserAndProductsDto.class);
            userAndProductsDto.setSoldProducts(this.setSoldProductsByUser(user));
            userAndProductsDtos.add(userAndProductsDto);
        }
        return userAndProductsDtos;
    }

    private List<CountAndProductDto> setSoldProductsByUser(User user) {
        Set<Product> soldProducts = user.getProductsSold();
        CountAndProductDto countAndProductDto = new CountAndProductDto();
        countAndProductDto.setCount(soldProducts.size());
        countAndProductDto.setProducts(this.setProductAndPrice(user));
        return List.of(countAndProductDto);
    }

    private List<ProductDto> setProductAndPrice(User user) {
        Set<Product> productsSold = user.getProductsSold();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : productsSold) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDtos.add(productDto);
        }
        return productDtos;
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
