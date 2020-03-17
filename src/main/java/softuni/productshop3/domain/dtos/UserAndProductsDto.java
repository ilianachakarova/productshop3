package softuni.productshop3.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class UserAndProductsDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private List<CountAndProductDto> soldProducts;

    public UserAndProductsDto() {
        this.soldProducts = new ArrayList<>();
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

    public List<CountAndProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<CountAndProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
