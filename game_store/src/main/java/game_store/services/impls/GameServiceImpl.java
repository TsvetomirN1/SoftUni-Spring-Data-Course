package game_store.services.impls;

import game_store.domain.dtos.GameAddDto;
import game_store.domain.dtos.GameDeleteDto;
import game_store.domain.dtos.GameEditDto;
import game_store.domain.entities.BaseEntity;
import game_store.domain.entities.Game;
import game_store.repositories.GameRepository;
import game_store.services.GameService;
import game_store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {

        if (!this.userService.isLoggedUserIsAdmin()) {
            System.out.println("Logged user is not admin");
            return;
        }

        Game game = this.modelMapper
                .map(gameAddDto, Game.class);

        this.gameRepository.saveAndFlush(game);
    }

    @Override
    public void editGame(GameEditDto gameEditDto) {

        if (!this.userService.isLoggedUserIsAdmin()) {
            System.out.println("Logged user is not admin");
            return;

        }
        Game game = this.modelMapper.map(gameEditDto, Game.class);
        this.gameRepository.saveAndFlush(game);
    }


    @Override
    public void deleteGame(GameDeleteDto gameDeleteDto) {

        if (!this.userService.isLoggedUserIsAdmin()) {
            System.out.println("Logged user is not admin");
            return;
        }

        Game game = this.modelMapper
                .map(gameDeleteDto, Game.class);

        this.gameRepository.delete(game);
    }

    @Override
    public GameEditDto getEditGameDtoById(Long id) {
        return this.gameRepository.getById(id);
    }


}
