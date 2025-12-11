INSERT INTO genres(description,name)
VALUES(
       'Games that emphasize planning, tactics and decision making.',
       'Strategy'
      );

INSERT INTO games(id,name,description,price,genre_name,icon,image,url)
VALUES(
        '11111111-1111-1111-1111-111111111111',
       'Tic Tac Toe',
       'Tic Tac Toe is een game waarin X en O geplaatst wordt om een rij van 3 te vormen.',
       19.99,
        'Strategy',
       'myicon.png',
       'myImage.png',
       'localhost:8080'
      );


INSERT INTO platform_user (id, user_name, biography)
VALUES('11111111-1111-1111-1234-111111111111', 'test-host','');
INSERT INTO platform_user (id, user_name, biography)
VALUES('11111111-1111-1111-aaaa-111111111111','test-user-1','');
INSERT INTO platform_user (id, user_name, biography)
VALUES('11111111-1111-1111-aabb-111111111111','test-user-2','');
INSERT INTO platform_user (id, user_name,biography)
VALUES('11111111-1111-1111-aacc-111111111111','test-user-3','');

INSERT INTO achievements (id, name, description, game_id)
VALUES
    (
        'f47ac10b-58cc-4372-a567-0e02b2c3d479',
        'King of the Hill',
        'Win a game from starting in the middle',
        '11111111-1111-1111-1111-111111111111'
    )

