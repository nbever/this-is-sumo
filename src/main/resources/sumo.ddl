CREATE TABLE APP.RIKISHI_INFO (
	ID BIGINT NOT NULL,
	EN_FIRST_NAME VARCHAR(24),
	EN_LAST_NAME VARCHAR(24),
	JP_FIRST_NAME VARCHAR(24),
	JP_LAST_NAME VARCHAR(24),
	EN_FIRST_SHIKONA VARCHAR(24),
	EN_LAST_SHIKONA VARCHAR(24),
	JP_FIRST_SHIKONA VARCHAR(24),
	JP_LAST_SHIKONA VARCHAR(24),
	KJ_FIRST_NAME VARCHAR(24),
	KJ_LAST_NAME VARCHAR(24),
	KJ_SHIKONA_FIRST VARCHAR(24),
	KJ_SHIKONA_LAST VARCHAR(24),
	BIRTHDAY BIGINT,
	HEIGHT INTEGER,
	WEIGHT INTEGER,
	SHUSSHIN BIGINT,
	HATSU_BASHO BIGINT,
	INTAI BIGINT,
	CONSTRAINT RIKISHI_INFO_PK PRIMARY KEY (ID)
);

CREATE TABLE APP.ICHIMON (
    ID BIGINT NOT NULL UNIQUE,
    EN_NAME VARCHAR(24),
    JP_NAME VARCHAR(24)
);

CREATE TABLE APP.HEYA (
    ID BIGINT NOT NULL UNIQUE,
    EN_NAME VARCHAR(24),
    JP_NAME VARCHAR(24),
    LOCATION BIGINT,
    EN_COMMON_PREFIX VARCHAR (12),
    JP_COMMON_PREFIX VARCHAR(12),
    KJ_COMMON_PREFIX VARCHAR(12),
    STRICTNESS INTEGER
);

CREATE TABLE APP.LOCATIONS (
    ID BIGINT NOT NULL UNIQUE,
    EN_COUNTRY VARCHAR(32),
    JP_COUNTRY VARCHAR(32),
    EN_AREA VARCHAR(32),
    JP_AREA VARCHAR(32),ß
    LONGITUDE FLOAT,
    LATITUDE FLOAT
);