package softuni.productshop3.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class CountAndUsersDto {
    @Expose
    private int count;
    @Expose
    private List<UserAndProductsDto> users;

    public CountAndUsersDto() {
        this.users = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserAndProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserAndProductsDto> users) {
        this.users = users;
    }
}
