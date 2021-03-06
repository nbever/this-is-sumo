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

INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 0, 'Arashio', '荒汐', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 1, 'Asakayama', '浅香山', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 2, 'Azumazeki', '東関', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 3, 'Chiganoura', '千賀の裏', -1, 'Masu', 'ます', '舛', 70 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 4, 'Dewanoumi', '出羽海', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 5, 'Fujishima', '藤島', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 6, 'Hakkaku', '八角', -1, 'Hokuto', 'ほくと', '北勝', 90 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 7, 'Irumagawa', '入間川', -1, NULL, NULL, NULL, 0 );	
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 8, 'Isegahama', '伊勢ヶ浜', -1, NULL, NULL, NULL, 0 );	
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 9, 'Isenoumi', '伊勢ノ海', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 10, 'Izutsu', '井筒', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 11, 'Kagamiyama', '鏡山', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 12, 'Kasugano', '春日野', -1, 'Tochi', 'とち', '栃', 95 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 13, 'Kasugayama', '春日山', -1, 'Kasuga', 'かすが', '春日', 80 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 14, 'Kataonami', '片男波', -1, 'Tama', 'たま', '玉', 100 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 15, 'Kise', '木瀬', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 16, 'Kitanoumi', '北の湖', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 17, 'Kokonoe', '九重', -1, 'Chiyo', 'ちよ', '千代', 99 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 18, 'Michinoku', '陸奥', -1, 'Kiri', 'きり', '霧', 60 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 19, 'Minato', '湊', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 20, 'Minezaki', '峰崎', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 21, 'Miyagino', '宮城野', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 22, 'Musashigawa', '武蔵川', -1, 'Musashi', 'むさし', '武蔵', 70 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 23, 'Nishikido', '錦戸', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 24, 'Nishonoseki', '二所ノ関', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 25, 'Oguruma', '尾車', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 26, 'Oitekaze', '追手風', -1, 'Daisho', 'だいしょう', '大翔', 90 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 27, 'Onoe', '尾上', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 28, 'Onomatsu', '阿武松', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 29, 'Otake', '大嶽', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 30, 'Sadogatake', '佐渡ヶ嶽', -1, 'Koto', 'こと', '琴', 100 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 31, 'Sakaigawa', '境川', -1, 'Sada', 'さだ', '佐田', 45 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 32, 'Shibatayama', '芝田山', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 33, 'Shikihide', '式秀', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 35, 'Shikoroyama', '錣山', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 36, 'Tagonoura', '田子ノ浦', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 37, 'Takadagawa', '高田川', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 38, 'Takanohana', '貴乃花', -1, 'Taka', 'たか', '貴', 90 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 39, 'Takasago', '髙砂', -1, 'Asa', 'あさ', '朝', 80 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 40, 'Tamanoi', '玉ノ井', -1, 'Azuma', 'あずま', '東', 30 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 41, 'Tatsunami', '立浪', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 42, 'Tokitsukaze', '時津風', -1, NULL, NULL, NULL, 0 );
INSERT INTO APP.HEYA ( ID, EN_NAME, JP_NAME, LOCATION, EN_COMMON_PREFIX, JP_COMMON_PREFIX, KJ_COMMON_PREFIX, STRICTNESS )
	VALUES ( 43, 'Tomozuna', '友綱', -1, 'Kai', 'かい', '魁', 70 );
	