create table BOARD(
	roomindex int, 
    content text,
    userid text,
    postdate text,
    fixed int,
    coordinate text);

ALTER TABLE BOARD CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
