package game_store.services;

import game_store.domain.dtos.GameAddDto;
import game_store.domain.dtos.GameDeleteDto;
import game_store.domain.dtos.GameEditDto;
import game_store.domain.entities.BaseEntity;

public interface GameService {

    void addGame(GameAddDto gameAddDto);
    void editGame(GameEditDto gameEditDto);
    void deleteGame(GameDeleteDto gameDeleteDto);

    GameEditDto getEditGameDtoById(Long id);
}
