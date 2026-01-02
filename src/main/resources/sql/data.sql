INSERT INTO genres (name, description)
VALUES ('Strategy',
        'Games that emphasize planning, tactics, and decision-making to outmaneuver an opponent or solve complex scenarios.')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO genres (name, description)
VALUES (
        'Puzzle',
        'Games centered around solving spatial, logical, or pattern-based challenges, often requiring quick thinking or precision.')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO games (id, name, description, price, image, icon, genre_name, url, ai_game_start_endpoint, game_start_endpoint, game_settings)
VALUES ('11111111-1111-1111-1111-111111111111',
        'Tic Tac Toe',
        'A classic 2-player strategy game.',
        19.98,
        'tictactoe.png',
        'https://www.svgrepo.com/show/143264/tic-tac-toe-game.svg',
        'Strategy',
        'http://localhost:5174/',
        'http://localhost:8081/tic-tac-toe/api/matches/ai',
        'http://localhost:8081/tic-tac-toe/api/matches',
        '{}'
       )
    ON CONFLICT (id) DO NOTHING;

INSERT INTO games (id, name, description, price, image, icon, genre_name, url, game_settings)
VALUES ('33333333-3333-3333-3333-333333333333',
        'Tetris',
        'A tile-matching puzzle game where players rotate pieces to clear lines.',
        14.99,
        'tetris.png',
        '',
        'Puzzle',
        '',
        '{}'
       )
    ON CONFLICT (id) DO NOTHING;