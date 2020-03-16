package softuni.productshop3.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserSoldProductsDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private List<ProductByUserDto> productsSold;


    public UserSoldProductsDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<ProductByUserDto> getProductByUserDtos() {
        return productsSold;
    }

    public void setProductByUserDtos(List<ProductByUserDto> productByUserDtos) {
        this.productsSold = productByUserDtos;
    }
}
