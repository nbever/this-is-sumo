CREATE TABLE APP.BANZUKE (
	RIKISHI_ID BIGINT NOT NULL,
	EN_FIRST_NAME VARCHAR(24),
	EN_LAST_NAME VARCHAR(24),
	KJ_FIRST_NAME VARCHAR(24),
	KJ_LAST_NAME VARCHAR(24),
	EN_FIRST_SHIKONA VARCHAR(24),
	EN_LAST_SHIKONA VARCHAR(24),
	JP_FIRST_SHIKONA VARCHAR(24),
	JP_LAST_SHIKONA VARCHAR(24),
	KJ_FIRST_SHIKONA VARCHAR(24),
	KJ_LAST_SHIKONA VARCHAR(24),
	EN_UNIVERSITY VARCHAR(48),
	KJ_UNIVERSITY VARCHAR(24),
	HEYA_ID BIGINT,
	HOMETOWN BIGINT,
	HEIGHT INTEGER,
	WEIGHT INTEGER,
	BIRTHDAY BIGINT,
	HATSU_BASHO BIGINT,
	CURRENT_RANK VARCHAR(16),
	CURRENT_SIDE VARCHAR(8),
	CURRENT_NUMBER INTEGER,
	HIGHEST_RANK VARCHAR(16),
	HIGHEST_SIDE VARCHAR(8),
	HIGHEST_NUMBER INTEGER,
	MAKUUCHI_YUSHO INTEGER,
	MAKUUCHI_JUN_YUSHO INTEGER,
	GINO_SHO INTEGER,
	SHUKUN_SHO INTEGER,
	KANTO_SHO INTEGER,
	JURYO_YUSHO INTEGER,
	JURYO_JUN_YUSHO INTEGER,
	MAKUSHITA_YUSHO INTEGER,
	MAKUSHITA_JUN_YUSHO INTEGER,
	SANDANME_YUSHO INTEGER,
	SANDANME_JUN_YUSHO INTEGER,
	JONIDAN_YUSHO INTEGER,
	JONIDAN_JUN_YUSHO INTEGER,
	JONOKUCHI_YUSHO INTEGER,
	JONOKUCHI_JUN_YUSHO INTEGER,
	MAE_ZUMO_YUSHO INTEGER,
	CAREER_WINS INTEGER,
	CAREER_LOSES INTEGER,
	CAREER_FORFEITS INTEGER,
	OSHI INTEGER,
	GAKE INTEGER,
	YOTSU INTEGER,
	NAGE INTEGER,
	TSUKI INTEGER,
	HIKU INTEGER,
	DEFENSE INTEGER,
	OVERALL_SKILL INTEGER,
	TACHIAI INTEGER,
	LEFT_ARM INTEGER,
	RIGHT_ARM INTEGER,
	UPPER_BODY INTEGER,
	LOWER_BODY INTEGER,
	RIGHT_LEG INTEGER,
	LEFT_LEG INTEGER,
	QUICKNESS INTEGER,
	EDGE INTEGER,
	GRIP_BREAK INTEGER,
	GRIP_STRENGTH INTEGER,
	SECONDARY_GRIP_STRENGTH INTEGER,
	PREFERRED_GRIP VARCHAR(16),
	BALANCE_CONTROL INTEGER,
	RECOVERY INTEGER,
	POTENTIAL INTEGER,
	FOCUS INTEGER,
	ANGER INTEGER,
	DRIVE INTEGER,
	IQ INTEGER,
	EMOTIONS INTEGER,
	INJURIES LONG VARCHAR
);

CREATE TABLE APP.DNA(
	RIKISHI_ID BIGINT NOT NULL,
	TACHIAI VARCHAR(64),
	WINNING VARCHAR(64),
	LOSING VARCHAR(64),
	EDGE_WINNING VARCHAR(64),
	EDGE_LOSING VARCHAR(64)
);

CREATE TABLE APP.LOOK(
	RIKISHI_ID BIGINT NOT NULL,
	MAWASHI_COLOR VARCHAR(16),
	MAWASHI_TXT VARCHAR(24),
	MAWASHI_MODEL VARCHAR(24),
	BODY_MODEL VARCHAR(24),
	HEAD_MODEL VARCHAR(24),
	BODY_TXT VARCHAR(24),
	HEAD_TXT VARCHAR(24),
	HAIR_COLOR VARCHAR(16),
	HAIR_TXT VARCHAR(24),
	HAIR_MODEL VARCHAR(24),
	KESHO_MODEL VARCHAR(24),
	KESHO_TXT VARCHAR( 24 ),
	PORTRAIT VARCHAR(24),
	SKIN_TONE VARCHAR(16)
);

CREATE TABLE APP.ANIMATIONS(
	RIKISHI_ID BIGINT NOT NULL,
	ANIMATIONS LONG VARCHAR
);