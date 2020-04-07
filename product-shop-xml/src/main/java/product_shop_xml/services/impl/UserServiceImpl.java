package product_shop_xml.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product_shop_xml.models.dtos.seedDtos.UserSeedDto;
import product_shop_xml.models.dtos.viewDtos.query2.SoldProductDto;
import product_shop_xml.models.dtos.viewDtos.query2.UserViewDto;
import product_shop_xml.models.dtos.viewDtos.query2.UserViewRootDto;
import product_shop_xml.models.entities.User;
import product_shop_xml.repositories.UserRepository;
import product_shop_xml.services.UserService;
import product_shop_xml.utils.ValidationUtil;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Random random;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           ValidationUtil validationUtil, Random random) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.random = random;
    }


    @Override
    public void seedUsers(List<UserSeedDto> userSeedDtos) {

        userSeedDtos
                .forEach(userSeedDto -> {
                    if (this.validationUtil.isValid(userSeedDto)) {
                        if (this.userRepository
                                .findByFirstNameAndLastName(
                                        userSeedDto.getFirstName(), userSeedDto.getLastName()) == null) {

                            User user = this.modelMapper.map(userSeedDto, User.class);

                            this.userRepository.saveAndFlush(user);

                        } else {
                            System.out.println("Already in database");
                        }
                    } else {
                        this.validationUtil.getViolations(userSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public User getRandomUser() {

        long randomId = this.random.nextInt((int) userRepository.count()) + 1;

        return this.userRepository.getOne(randomId);

    }

    @Override
    public UserViewRootDto getUsersSoldProduct() {


        List<UserViewDto> usersDto = this.userRepository.findUsersWithSoldProducts()
                .stream()
                .map(user -> this.modelMapper
                        .map(user, UserViewDto.class))
                .collect(Collectors.toList());
        UserViewRootDto userViewRootDto = new UserViewRootDto(usersDto);
        return userViewRootDto;

    }
}
