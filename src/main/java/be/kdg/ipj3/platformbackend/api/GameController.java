package be.kdg.ipj3.platformbackend.api;

import be.kdg.ipj3.platformbackend.api.dtos.FullGameDto;
import be.kdg.ipj3.platformbackend.api.dtos.GameListdto;
import be.kdg.ipj3.platformbackend.application.GameService;
import be.kdg.ipj3.platformbackend.domain.Game;
import be.kdg.ipj3.platformbackend.domain.GameId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService games;

    public GameController(GameService games) {
        this.games = games;
    }

    @GetMapping
    public ResponseEntity<List<GameListdto>> findAll(){
        List<GameListdto> dtos = games.findAll().stream().map(GameListdto::from).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullGameDto> findGame(@PathVariable final UUID id){
        Game game = games.find(new GameId(id));
        return ResponseEntity.ok(FullGameDto.from(game));
    }
}
