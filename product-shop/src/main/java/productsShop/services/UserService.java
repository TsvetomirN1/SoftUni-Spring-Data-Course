package productsShop.services;

import productsShop.models.dtos.UserSeedDto;
import productsShop.models.dtos.UserWithSoldProductDto;
import productsShop.models.dtos.UsersAndProductsDto;
import productsShop.models.entities.User;

import java.util.List;

public interface UserService {

    void seedUsers(UserSeedDto[] userSeedDtos);

    User getRandomUser();

    List<UserWithSoldProductDto> findUsersWithSoldProducts();

    UsersAndProductsDto getUsersAndProducts();
}
