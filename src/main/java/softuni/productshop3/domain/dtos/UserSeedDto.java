package softuni.productshop3.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserSeedDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;

    public UserSeedDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @NotNull(message = "Last name cannot be null")
    @Size(min = 3,message = "Invalid last name")
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
}
