INSERT INTO genres (name, description)
VALUES ('Strategy',
        'Games that emphasize planning, tactics, and decision-making to outmaneuver an opponent or solve complex scenarios.')
    ON CONFLICT (name) DO NOTHING;

INSERT INTO genres (name, description)
VALUES (
        'Puzzle',
        'Games centered around solving spatial, logical, or pattern-based challenges, often requiring quick thinking or precision.')
    ON CONFLICT (name) DO NOTHING;



INSERT INTO games (id, name, description, price, image, icon, genre_name, url)
VALUES ('11111111-1111-1111-1111-111111111111',
        'Tic Tac Toe',
        'A classic 2-player strategy game.',
        19.98,
        'tictactoe.png',
        'https://www.svgrepo.com/show/143264/tic-tac-toe-game.svg',
        'Strategy',
        'http://localhost:5174/')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO games (id, name, description, price, image, icon, genre_name, url)
VALUES ('22222222-2222-2222-2222-222222222222',
        'Go',
        'An ancient abstract strategy board game originating from East Asia.',
        29.99,
        'go.png',
        '',
        'Strategy',
        '')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO games (id, name, description, price, image, icon, genre_name, url)
VALUES ('33333333-3333-3333-3333-333333333333',
        'Tetris',
        'A tile-matching puzzle game where players rotate pieces to clear lines.',
        14.99,
        'tetris.png',
        '',
        'Puzzle',
        '')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO achievements (id, name, description, game_id)
VALUES
    (
        'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        'First Capture',
        'Capture your first opponent stone',
        '22222222-2222-2222-2222-222222222222'
    ),
    (
        '9b2c7e4a-3f1d-4a82-9c3b-2b8a6d4f1e57',
        'Territory Master',
        'Secure 30 points of territory in a single match',
        '22222222-2222-2222-2222-222222222222'
    ),
    (
        '6d8f94c3-2a41-4b9e-b5c2-7e1d8f3a92be',
        'Perfect Endgame',
        'Win a match without losing any stones',
        '22222222-2222-2222-2222-222222222222'
    );