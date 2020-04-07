package game_store.controllers;

import game_store.domain.dtos.*;
import game_store.domain.entities.Game;
import game_store.services.GameService;
import game_store.services.UserService;
import game_store.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class AppController implements CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final GameService gameService;


    @Autowired
    public AppController(BufferedReader bufferedReader, ValidationUtil validationUtil, UserService userService, GameService gameService) {
        this.bufferedReader = bufferedReader;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.gameService = gameService;
    }


    @Override
    public void run(String... args) throws Exception {


        while (true) {
            System.out.println("Enter command:");
            String[] input = this.bufferedReader.readLine().split("\\|");

            switch (input[0]) {
                case "RegisterUser":

                    if (!input[2].equals(input[3])) {
                        System.out.println("Password not match");
                        break;
                    }

                    UserRegisterDto userRegisterDto = new UserRegisterDto(input[1], input[2], input[4]);

                    if (this.validationUtil.isValid(userRegisterDto)) {
                        this.userService.registerUser(userRegisterDto);
                        System.out.printf("%s was registered%n", input[4]);
                    } else {
                        this.validationUtil.getViolations(userRegisterDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                    break;

                case "LoginUser":
                    UserLoginDto userLoginDto = new UserLoginDto(input[1], input[2]);

                    this.userService.loginUser(userLoginDto);

                    break;

                case "Logout":
                    this.userService.logOut();
                    break;

                case "AddGame":

                    GameAddDto gameAddDto = new GameAddDto(
                            input[1], new BigDecimal(input[2]),
                            Double.parseDouble(input[3]), input[4],
                            input[5], input[6],
                            LocalDate.parse(input[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    );

                    if (this.validationUtil.isValid(gameAddDto)) {
                        this.gameService.addGame(gameAddDto);
                        System.out.println("Game added");
                    } else {
                        this.validationUtil.getViolations(gameAddDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                    break;

                case "EditGame":

                    Long id = Long.parseLong(input[1]);
                    GameEditDto gameEditDto = this.gameService.getEditGameDtoById(id);
                    for (int i = 2; i < input.length; i++) {
                        String[] data = input[i].split("=");
                        String fieldName = data[0];
                        switch (fieldName) {
                            case "price":
                                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(data[1]));
                                gameEditDto.setPrice(price);
                                break;
                            case "size":
                                Double value = Double.parseDouble(data[1]);
                                gameEditDto.setSize(value);
                                break;
                            case "releaseDate":
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
                                LocalDate dateNew = LocalDate.parse(data[1], formatter);
                                gameEditDto.setReleaseDate(dateNew);
                                break;
                            default:
                                Field field = GameEditDto.class.getDeclaredField(data[0]);
                                field.setAccessible(true);
                                field.set(gameEditDto, data[1]);
                                break;
                        }
                        if (validationUtil.isValid(gameEditDto)) {
                            gameService.editGame(gameEditDto);
                            System.out.printf("%s has been edited!%n", gameEditDto.getTitle());
                        } else {
                            validationUtil.getViolations(gameEditDto)
                                    .stream()
                                    .map(ConstraintViolation::getMessage)
                                    .forEach(System.out::println);
                        }
                    }

                    break;

                case "DeleteGame":

                    Long idToDelete = Long.parseLong(input[1]);
                    GameDeleteDto gameDeleteDto = new GameDeleteDto(idToDelete);
                    this.gameService.deleteGame(gameDeleteDto);

                    break;
            }
        }
    }
}

