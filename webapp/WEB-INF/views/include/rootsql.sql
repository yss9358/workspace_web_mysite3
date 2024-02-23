-- phone 계정만들기
create user 'phone'@'%' identified by 'phone';

-- phone 권한부여
grant all privileges on phone_db.* to 'phone'@'%';

flush privileges; -- 권한 바로 부여
-- 데이터베이스 만들기
create database phone_db
	default character set utf8mb4
    collate utf8mb4_general_ci
    default encryption='n'
;

-- guestbook 
create user 'guestbook'@'%' identified by 'guestbook';
grant all privileges on guestbook_db.* to 'guestbook'@'%';
flush privileges;
create database guestbook_db
	default character set utf8mb4
    collate utf8mb4_general_ci
    default encryption='n'
;

-- --------------------------------------------------------------------
-- web 계정만들기
create user 'web'@'%' identified by 'web';
-- web 계정 권한부여
grant all privileges on web_db.* to 'web'@'%';
flush privileges;
-- web_db 만들기
create database web_db
	default character set utf8mb4
    collate utf8mb4_general_ci
    default encryption='n'
;

show databases;