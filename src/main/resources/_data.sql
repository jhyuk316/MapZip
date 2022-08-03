INSERT INTO "PUBLIC"."CATEGORY" VALUES
(1, STRINGDECODE('\ud55c\uc2dd')),
(2, STRINGDECODE('\uae40\uce58\ucc0c\uac1c')),
(3, STRINGDECODE('\uc911\uc2dd')),
(4, STRINGDECODE('\ud0d5\uc218\uc721')),
(5, STRINGDECODE('\uae40\ubc25')),
(6, STRINGDECODE('\ub3c8\uae4c\uc2a4'));

INSERT INTO "PUBLIC"."RESTAURANT" VALUES
(1, STRINGDECODE('\uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uad00\uc545\uad6c \ub0a8\ubd80\uc21c\ud658\ub85c 1621'), NULL, 37.4847149, 126.9308361, STRINGDECODE('\uc2dc\ud5d8\uc2dd\ub2f92'), 0.0),
(2, STRINGDECODE('\uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uad00\uc545\uad6c \ub0a8\ubd80\uc21c\ud658\ub85c 1667'), NULL, 37.4850492, 126.9359222, STRINGDECODE('\uc2dc\ud5d8\uc2dd\ub2f93'), 0.0),
(3, STRINGDECODE('\uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uad00\uc545\uad6c \uc740\ucc9c\ub85c 1'), NULL, 37.4865763, 126.9354702, STRINGDECODE('\uc2dc\ud5d8\uc2dd\ub2f91'), 0.0);

INSERT INTO "PUBLIC"."YOUTUBER" VALUES
(1, 'ChanA', STRINGDECODE('\uc720\ud29c\ubc84A')),
(2, 'ChanB', STRINGDECODE('\uc720\ud29c\ubc84B')),
(3, 'ChanC', STRINGDECODE('\uc720\ud29c\ubc84C'));

-- 외래키 때문에 나중에 조인 테이블을 입력해야 함. 당연한건데 참 헷깔리네.

INSERT INTO "PUBLIC"."RESTAURANT_CATEGORY" VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 2),
(4, 4, 2),
(5, 1, 3),
(6, 5, 3),
(7, 6, 3);

INSERT INTO "PUBLIC"."RESTAURANT_YOUTUBER_ENTITY" VALUES
(1, 'VideoA2', 1, 1),
(2, 'VideoA1', 3, 1),
(3, 'VideoB1', 3, 2);