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

--This is the ID for your own id.
INSERT INTO platform_user (id)
VALUES('11111111-1111-1111-1234-111111111111');
-- this is a random other player ID.
INSERT INTO platform_user (id)
VALUES('11111111-1111-1111-aaaa-111111111111');

-- this is a player that we auto-accept friends with.
INSERT INTO platform_user (id)
VALUES('11111111-1111-1111-aabb-111111111111');