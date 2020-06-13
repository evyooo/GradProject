create table STATS(
	roomindex int,
	userid text,
	year text,
	month text,
	weekofMonth text,
	content text,
	score text
);

ALTER TABLE STATS CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
