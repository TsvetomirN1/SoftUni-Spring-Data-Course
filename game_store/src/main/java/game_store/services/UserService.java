package game_store.services;

import game_store.domain.dtos.UserLoginDto;
import game_store.domain.dtos.UserRegisterDto;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logOut();

    boolean isLoggedUserIsAdmin();
}
