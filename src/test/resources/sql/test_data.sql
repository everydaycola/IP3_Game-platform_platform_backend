INSERT INTO genres(id,description,name)
VALUES(
          'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
       'Games that emphasize planning, tactics and decision making.',
       'Strategy'
      );

INSERT INTO games(id,name,description,price,genre_id,icon,image,url)
VALUES(
        '11111111-1111-1111-1111-111111111111',
       'Tic Tac Toe',
       'Tic Tac Toe is een game waarin X en O geplaatst wordt om een rij van 3 te vormen.',
       19.99,
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
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

--this is a test user that has an open FR to own user.
INSERT INTO platform_user (id)
VALUES ('11111111-1111-1111-aaab-111111111111');

INSERT INTO platform_user_friend (confirmed_at, is_confirmed, requested_at, friend_id, user_id)
VALUES (NULL, false, NOW(), '11111111-1111-1111-1234-111111111111','11111111-1111-1111-aaab-111111111111');


--this is a test user that already is a friend with main user
INSERT INTO platform_user (id)
VALUES ('11111111-1111-1111-aaac-111111111111');

INSERT INTO platform_user_friend (confirmed_at, is_confirmed, requested_at, friend_id, user_id)
VALUES (now(), true, NOW(), '11111111-1111-1111-1234-111111111111','11111111-1111-1111-aaac-111111111111');