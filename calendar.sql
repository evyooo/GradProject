create table CALENDAR(
	calendarindex int auto_increment primary key,
	roomindex int,
	color text,
	title text,
	startdate text,
	enddate text,
	remind text,
	memo text
);

ALTER TABLE CALENDAR CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
