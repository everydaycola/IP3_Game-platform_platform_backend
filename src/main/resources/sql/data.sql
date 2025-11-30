INSERT INTO genres (id, name, description)
VALUES ('aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
        'Strategy',
        'Games that emphasize planning, tactics, and decision-making to outmaneuver an opponent or solve complex scenarios.')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO genres (id, name, description)
VALUES ('aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
        'Puzzle',
        'Games centered around solving spatial, logical, or pattern-based challenges, often requiring quick thinking or precision.')
    ON CONFLICT (id) DO NOTHING;



INSERT INTO games (id, name, description, price, image, icon, genre_id, url)
VALUES ('11111111-1111-1111-1111-111111111111',
        'Tic Tac Toe',
        'A classic 2-player strategy game.',
        19.98,
        'tictactoe.png',
        'https://www.svgrepo.com/show/143264/tic-tac-toe-game.svg',
        'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
        'http://localhost:5174/')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO games (id, name, description, price, image, icon, genre_id, url)
VALUES ('22222222-2222-2222-2222-222222222222',
        'Go',
        'An ancient abstract strategy board game originating from East Asia.',
        29.99,
        'go.png',
        '',
        'aaaaaaa1-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
        '')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO games (id, name, description, price, image, icon, genre_id, url)
VALUES ('33333333-3333-3333-3333-333333333333',
        'Tetris',
        'A tile-matching puzzle game where players rotate pieces to clear lines.',
        14.99,
        'tetris.png',
        '',
        'aaaaaaa2-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
        '')
    ON CONFLICT (id) DO NOTHING;