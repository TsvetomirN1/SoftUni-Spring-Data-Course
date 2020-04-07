package product_shop_xml.services;

import product_shop_xml.models.dtos.seedDtos.UserSeedDto;
import product_shop_xml.models.dtos.viewDtos.query2.UserViewRootDto;
import product_shop_xml.models.entities.User;

import java.util.List;

public interface UserService {

    void seedUsers(List<UserSeedDto> userSeedDtos);

    User getRandomUser();

    UserViewRootDto getUsersSoldProduct();
}
