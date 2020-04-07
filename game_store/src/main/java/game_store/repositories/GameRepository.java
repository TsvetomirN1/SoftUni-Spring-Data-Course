package game_store.repositories;

import game_store.domain.dtos.GameEditDto;
import game_store.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    GameEditDto getById(Long id);
}
