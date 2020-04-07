package productsShop.models.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UsersAndProductsDto {

    @Expose
    private Integer usersCount;
    @Expose
    private List<UserSoldWithAgeDto> users;

    public UsersAndProductsDto() {
    }

    public UsersAndProductsDto(Integer usersCount, List<UserSoldWithAgeDto> users) {
        this.usersCount = usersCount;
        this.users = users;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserSoldWithAgeDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldWithAgeDto> users) {
        this.users = users;
    }
}
