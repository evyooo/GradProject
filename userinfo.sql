create table USERINFO(
	name text, 
    userid text,
    userpw text,
    birthday text);

ALTER TABLE USERINFO CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

ALTER database Android default CHARACTER SET utf8;
SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;
commit;