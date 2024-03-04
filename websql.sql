use web_db;
-- 테이블 만들기 
create table users(
	no int auto_increment primary key, -- 회원식별번호
    id varchar(20) unique not null, -- 아이디
    password varchar(20) not null, -- 패스워드
    name varchar(20), -- 이름
    gender varchar(10) -- 성별
);
-- 전체 불러오기
select	no,
		id,
        password,
        name,
        gender
from users
;
-- 한명 불러오기
select	no,
		id,
        password,
        name,
        gender
from users
where no = 7
;
-- 추가
insert into users
value(null, 'yss', '1234', '유승수', 'male')
;
insert into users
value(null, 'id', 'password', 'name', 'female')
;



update users
set gender = 'male'
where no = 1
;

delete from users
where no = 21
;

select	id,
		password
from users
where id = 'aaa'
and password = '123'
;





-- 수정
update users
set password = '123',
	name = '수정중',
    gender = 'male'
where no = 7
;


-- guestbook table 만들기
create table guestbook(
	no int auto_increment primary key, -- 식별번호
    name varchar(80), -- 이름
    password varchar(20), -- 패스워드
    content varchar(2000), -- 본문
    reg_date datetime -- 등록일
);
-- guestbook 전체 불러오기
select	no,
		name,
        password,
        content,
        reg_date regDate
from guestbook
;
-- 추가
insert into guestbook
value(null, 'name', 'password','content', date_format(now(),'%Y-%m-%d %H:%i:%s'))
;

-- guestbook 삭제
delete from guestbook

where no = 1
;
-- 한명 불러오기
select	no,
        name
from users
where id = 'aaa'
and password = '123'
;

select	no,
		name,
        password,
        content,
        reg_date regDate
from guestbook
order by no desc
;



show tables;

