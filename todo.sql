create table TODO(
	todoindex int auto_increment primary key,
	roomindex int,
	title text,
	duedate text,
	score text,
	remind text,
	userid text,
	donedate text
);

ALTER TABLE TODO CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
