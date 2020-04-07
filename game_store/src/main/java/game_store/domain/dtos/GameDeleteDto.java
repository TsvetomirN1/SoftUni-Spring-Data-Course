package game_store.domain.dtos;

public class GameDeleteDto {

    private long id;

    public GameDeleteDto(Long idToDelete) {
    }

    public long getId(Long idToDelete) {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
