<!-- SQL문을 작성하기 전에 정상작동 하는지 확인하는 페이지 -->
<!-- STORE(맛집) 데이터 베이스 -->
SELECT * FROM STORE;            <!-- 테이블 안의 데이터 모두 출력 -->

DROP TABLE STORE;               <!-- 테이블 삭제 -->

DELETE FROM STORE WHERE id = 2; <!-- 테이블안의 2번째 행 삭제 -->

ALTER TABLE STORE
    ADD (tag varchar(30));          <!-- 테이블에 새로운 열 추가-->

alter table store add storefilename varchar2(250);      <!-- 열 추가 -->

alter table store drop column storefilename;        <!-- 열 삭제 -->

alter table store rename column 변경할 컬럼명 to 변경될 이름;  <!-- 열 이름 바꾸기 -->

ALTER TABLE place ADD FOREIGN KEY (account_id) REFERENCES account (id);   <!-- FK 선언 -->

TRUNCATE TABLE 테이블명; <!-- 테이블 안 데이터 모두 삭제 -->

  <!-- 맛집 테이블 생성 -->
CREATE TABLE STORE
(
    storeid NUMBER(5) GENERATED BY DEFAULT AS IDENTITY,
    storename VARCHAR2(10),
    storelocation VARCHAR2(30),
    storemenu VARCHAR2(10),
    storeprice VARCHAR2(10),
    storescore VARCHAR2(20),
    storetag VARCHAR2(30),
    storefilename VARCHAR2(250),
    storefilepath VARCHAR2(250),
    storeviewcount int not null default 0,
    storedate DATE,
    PRIMARY KEY (storeid)

    );

<!-- 가볼만한 곳 테이블 생성 -->
CREATE TABLE PLACE
(
    placeid NUMBER(5) GENERATED BY DEFAULT AS IDENTITY,
    placelocation VARCHAR2(30),
    placename VARCHAR2(20),
    placeweather VARCHAR2(20),
    placescore VARCHAR2(20),
    placememo text,
    placetag VARCHAR2(20),
    placefilename VARCHAR2(250),
    placefilepath VARCHAR2(250),
    placeviewcount int not null default 0,
    placedate DATE,
    PRIMARY KEY (placeid)

    );

<!-- 숙소 테이블 생성 -->
CREATE TABLE LODGING
(
    lodgingid NUMBER(5) GENERATED BY DEFAULT AS IDENTITY,
    lodgingname VARCHAR2(20),
    lodginglocation VARCHAR2(30),
    lodgingprice int,
    lodgingscore VARCHAR2(20),
    lodgingmemo text,
    lodgingtag VARCHAR2(20),
    lodgingfilename VARCHAR2(250),
    lodgingfilepath VARCHAR2(250),
    lodgingviewcount int not null default 0,
    lodgingviewcount int,
    lodgingdate DATE,
    PRIMARY KEY (lodgingid)

    );

<!-- 좋아요 테이블 -->
CREATE TABLE LIKED
(
    idx NUMBER(5) GENERATED BY DEFAULT AS IDENTITY,
    regdate DATE,
    totalid NUMBER(10),
    username VARCHAR2(100),
    point VARCHAR2(10),
    PRIMARY KEY (idx)
);

<-- 북마크 테이블 -->
CREATE TABLE BOOKMARK
(
    idx NUMBER(5) GENERATED BY DEFAULT AS IDENTITY,
    regdate DATE,
    totalid NUMBER(10),
    username VARCHAR2(100),
    bookmark VARCHAR2(10),
    PRIMARY KEY (idx)
);

<!-- 게시물 데이터 테이블 -->
CREATE TABLE total
(
    idx NUMBER(5) GENERATED BY DEFAULT AS IDENTITY,
    title VARCHAR2(10),
    location VARCHAR2(30),
    memo VARCHAR2(500),
    tag_list VARCHAR2(100),
--     filename VARCHAR2(250),
    score VARCHAR2(10),
    date VARCHAR2(30),
    liked VARCHAR2(100),
    createdate DATE,
    username VARCHAR2(100),
    useremail VARCHAR2(100),
    image_url  VARCHAR2(200),
    profileimage VARCHAR2(100),
    category VARCHAR2(30),
    weather VARCHAR2(30),
    menu VARCHAR2(30),
    viewcount int,
    price int,
    PRIMARY KEY (idx)
);

<-- 회원 테이블 -->
CREATE TABLE ACCOUNT
(
    idx Long GENERATED BY DEFAULT AS IDENTITY,
    username VARCHAR2(30) not null unique,
    password VARCHAR2(30),
    email VARCHAR2(30),
    emailVerified


    regdate DATE,
    totalid NUMBER(10),
    username VARCHAR2(100),
    bookmark VARCHAR2(10),
    PRIMARY KEY (idx)
);