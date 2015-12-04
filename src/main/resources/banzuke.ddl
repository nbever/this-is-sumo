CREATE TABLE APP.BANZUKE (
	RIKISHI_ID BIGINT NOT NULL,
	EN_FIRST_SHIKONA VARCHAR(24),
	EN_LAST_SHIKONA VARCHAR(24),
	JP_FIRST_SHIKONA VARCHAR(24),
	JP_LAST_SHIKONA VARCHAR(24),
	RANK INTEGER,
	DIRECTION INTEGER,
	NUMBER INTEGER,
	OSHI INTEGER,
	YOTSU INTEGER,
	NAGE INTEGER,
	TSUKI INTEGER,
	HATAKIKOMI INTEGER,
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
	PREFERRED_GRIP INTEGER,
	FOCUS INTEGER,
	ANGER INTEGER,
	DRIVE INTEGER,
	IQ INTEGER,
	TENDENCY_MAP BLOB,
	MAWASHI_COLOR VARCHAR(8)
);
