create table ROONINFO(
	roomindex int auto_increment primary key,
    roomtitle text,
    roomdes text,
	user1 text, 
    user2 text,
    user3 text,
    user4 text);

ALTER TABLE ROONINFO CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
