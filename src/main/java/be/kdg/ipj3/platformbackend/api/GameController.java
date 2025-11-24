package be.kdg.ipj3.platformbackend.api;

import be.kdg.ipj3.platformbackend.api.dtos.GameListdto;
import be.kdg.ipj3.platformbackend.application.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
