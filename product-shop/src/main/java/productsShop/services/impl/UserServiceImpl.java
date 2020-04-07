package productsShop.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productsShop.models.dtos.*;
import productsShop.models.entities.User;
import productsShop.repositories.UserRepository;
import productsShop.services.UserService;
import productsShop.utils.ValidationUtil;

import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {

        if (this.userRepository.count() != 0) {
            return;
        }

        Arrays.stream(userSeedDtos)
                .forEach(userSeedDto -> {
                    if (this.validationUtil.isValid(userSeedDto)) {
                        User user = this.modelMapper.map(userSeedDto, User.class);
                        this.userRepository.saveAndFlush(user);

                    } else {
                        this.validationUtil
                                .getViolations(userSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);

                    }
                });
    }

    @Override
    public User getRandomUser() {

        Random random = new Random();
        long randomId = random
                .nextInt((int) this.userRepository.count()) + 1;

        return this.userRepository.getOne(randomId);
    }

    @Override
    public List<UserWithSoldProductDto> findUsersWithSoldProducts() {

        return this.userRepository.findUsersWithSoldProducts()
                .stream()
                .map(u -> new UserWithSoldProductDto(u.getFirstName(), u.getLastName(),
                        u.getSold()
                                .stream()
                                .filter(p -> p.getBuyer() != null)
                                .map(p -> new SoldProductsDto(p.getName(), p.getPrice(),
                                        p.getBuyer().getFirstName(), p.getBuyer().getLastName()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public UsersAndProductsDto getUsersAndProducts() {

        Set<User> usersWithSoldProducts = userRepository.findUsersWithSoldProducts();

        return new UsersAndProductsDto(usersWithSoldProducts.size(),
                usersWithSoldProducts
                        .stream()
                        .map(u -> new UserSoldWithAgeDto(u.getFirstName(), u.getLastName(), u.getAge(),
                                new ProductsDtoCount(
                                        u.getSold().stream().filter(x -> x.getBuyer() != null).count(),
                                        u.getSold().stream()
                                                .filter(x -> x.getBuyer() != null)
                                                .map(p -> new ProductNameAndPriceDto(p.getName(), p.getPrice()))
                                                .collect(Collectors.toList()))))
                        .sorted(Comparator.comparing((UserSoldWithAgeDto x) -> -x.getSoldProducts().getProducts().size())
                                .thenComparing(UserSoldWithAgeDto::getLastName))
                        .collect(Collectors.toList()));
    }

    }

