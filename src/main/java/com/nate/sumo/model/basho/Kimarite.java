package com.nate.sumo.model.basho;

import com.nate.sumo.model.common.Name;

public enum Kimarite {

	ABISETAOSHI( new Name( "Abisetaoshi", "浴せ倒し"), Type.YOTSU ),
	OSHIDASHI( new Name( "Oshidashi", "押し出し"), Type.OSHI ),
	YORIKIRI( new Name( "Yorikiri", "寄り切り"), Type.YOTSU ),
	OSHITAOSHI( new Name( "Oshitaoshi", "押し倒し"), Type.OSHI ),
	YORITAOSHI( new Name( "Yoritaoshi", "寄り倒し"), Type.YOTSU ),
	UWATENAGE( new Name( "Uwatenage", "上手投げ"), Type.NAGE ),
	SHITATENAGE( new Name( "Shitatenage", "下手投げ"), Type.NAGE ),
	SUKUINAGE( new Name( "Sukuinage", "掬い投げ"), Type.NAGE ),
	HATAKIKOMI( new Name( "Hatakikomi", "叩き込み"), Type.HIKU ),
	HANSOKU( new Name( "Hansoku", "反則"), Type.NON_LOSING ),
	FUSENSHO( new Name( "Fusensho", ""), Type.NON_LOSING ),
	FUSEN( new Name( "Fusen", ""), Type.NON_LOSING ),
	SOTOGAKE( new Name( "Sotogake", "外掛け"), Type.GAKE ),
	UCHIGAKE( new Name( "Uchigake", "内掛け"), Type.GAKE ),
	TSUKIOTOSHI( new Name( "Tsukiotoshi", "突き落とし"), Type.TSUKI ),
	TSUKITAOSHI( new Name( "Tsukitaoshi", "突き倒し"), Type.TSUKI ),
	OKURIDASHI( new Name( "Okuridashi", "送り出し"), Type.OSHI ),
	UWATEDASHINAGE( new Name( "Uwatedashinage", "上手出し投げ"), Type.YOTSU ),
	SHITATEDASHINAGE( new Name( "Shitatedashinage", "下手出し投げ"), Type.YOTSU ),
	KUBINAGE( new Name( "Kubinage", "首投げ"), Type.NAGE ),
	UTCHARI( new Name( "Utchari", "うっちゃり"), Type.DEFENSE ),
	WATASHIKOMI( new Name( "Watashikomi", "渡し込み" ), Type.YOTSU ),
	TSUKIDASHI( new Name( "Tsukidashi", "突き出し"), Type.TSUKI ),
	IPPONZEOI( new Name( "Ipponzeoi", "本背負い"), Type.NAGE ),
	KAKENAGE( new Name( "Kakenage", "掛け投げ"), Type.NAGE ),
	KOSHINAGE( new Name( "Koshinage", "腰投げ"), Type.NAGE ),
	KOTENAGE( new Name( "Kotenage", "小手投げ"), Type.NAGE ),
	NICHONAGE( new Name( "Nichonage", "二丁投げ"), Type.GAKE ),
	TSUKAMINAGE( new Name( "Tsukaminage", "つかみ投げ"), Type.NAGE ),
	YAGURANAGE( new Name( "Yaguranage", "櫓投げ"), Type.NAGE ),
	ASHITORI( new Name ("Ashitori", "足取り"), Type.HIKU ),
	CHONGAKE( new Name( "Chongake", "ちょん掛け"), Type.SPECIAL ),
	KAWAZUNAGE( new Name( "Kawazunage", "河津掛け"), Type.DEFENSE ),
	KEKAESHI( new Name( "Kekaeshi", "蹴返し"), Type.HIKU ),
	KETAGURI( new Name( "Ketaguri", "蹴手繰り"), Type.GAKE ),
	KIRIKAESHI( new Name( "Kirikaeshi", "切り返し"), Type.GAKE ),
	KOMATASUKUI( new Name( "Komatasukui", "小股掬い"), Type.DEFENSE ),
	KOZUMATORI( new Name( "Kozumatori", "小褄取り"), Type.SPECIAL ),
	MITOKOROZEME( new Name( "Mitokorozeme", "三所攻め"), Type.DEFENSE ),
	NIMAIGERI( new Name( "Nimaigeri", "二枚蹴り"), Type.SPECIAL ),
	OMATA( new Name( "Omata", "大股"), Type.DEFENSE ),
	SOTOKOMATA( new Name( "Sotokomata", "外小股"), Type.DEFENSE ),
	SUSOHARAI( new Name( "Susoharai", "裾払い"), Type.DEFENSE),
	SUSOTORI( new Name( "Susotori", "裾取り"), Type.DEFENSE ),
	TSUMATORI( new Name( "Tsumatori", "褄取り"), Type.SPECIAL ),
	AMIUCHI( new Name( "Amiuchi", "網打ち"), Type.HIKU ),
	GASSHOHINERI( new Name( "Gasshohineri", "合掌捻り"), Type.YOTSU ),
	HARIMANAGE( new Name( "Harimanage", "波離間投げ"), Type.HIKU ),
	KAINAHINERI( new Name( "Kainahineri", "腕捻り"), Type.YOTSU ),
	KATASUKASHI( new Name( "Katasukashi", "肩透かし"), Type.YOTSU ),
	KOTEHINERI( new Name( "Kotohineri", "小手捻り"), Type.YOTSU ),
	KUBIHINERI( new Name( "Kubihineri", "首捻り"), Type.YOTSU ),
	MAKIOTOSHI( new Name( "Makiotoshi", "巻き落とし"), Type.DEFENSE ),
	OSAKATE( new Name( "Osakate", "大逆手"), Type.YOTSU ),
	SABAORI( new Name( "Sabaori", "鯖折り"), Type.HIKU ),
	SAKATOTTARI( new Name( "Sakatottari", "鯖折り"), Type.YOTSU ),
	SHITATEHINERI( new Name( "Shitatehineri", "下手捻り"), Type.YOTSU ),
	SOTOMUSO( new Name( "Sotomuso", "外無双"), Type.YOTSU ),
	TOKKURINAGE( new Name( "Tokkurinage", "徳利投げ"), Type.OSHI ),
	TOTTARI( new Name( "Tottari", "とったり"), Type.SPECIAL ),
	UCHIMUSO( new Name( "Uchimuso", "内無双"), Type.SPECIAL ),
	UWATEHINERI( new Name( "Uwatehineri", "上手捻り"), Type.YOTSU ),
	ZUBUNERI( new Name( "Zubuneri", "ずぶねり"), Type.OSHI ),
	IZORI( new Name( "Izori", "居反り"), Type.OSHI ),
	KAKEZORI( new Name( "Kakezori", "掛け反り"), Type.OSHI ),
	SHUMOKUZORI( new Name( "Shumokuzori", "撞木反り"), Type.SPECIAL ),
	SOTOTASUKIZORI( new Name( "Sototasukizori", "外たすき反り"), Type.YOTSU ),
	TASUKIZORI( new Name( "Tasukizori", "たすき反り"), Type.YOTSU ),
	TSUTAEZORI( new Name( "Tsutaezori", "伝え反り"), Type.YOTSU ),
	HIKIOTOSHI( new Name( "Hikiotoshi", "引き落とし"), Type.HIKU ),
	HIKKAKE( new Name( "Hikkake", "引っ掛け"), Type.HIKU ),
	KIMEDASHI( new Name( "Kimedashi", "極め出し"), Type.OSHI ),
	KIMETAOSHI( new Name( "Kimetaoshi", "極め倒し"), Type.OSHI ),
	OKURIGAKE( new Name( "Okurigake", "送り掛け"), Type.GAKE ),
	OKURIHIKIOTOSHI( new Name( "Okurihikiotoshi", "送り引き落とし"), Type.HIKU ),
	OKURINAGE( new Name( "Okurinage", "送り投げ"), Type.YOTSU ),
	OKURITAOSHI( new Name( "Okuritaoshi", "送り倒し"), Type.OSHI ),
	OKURITSURIDASHI( new Name( "Okuritsuridashi", "送り吊り出し"), Type.YOTSU ),
	OKURITSURIOTOSHI( new Name( "Okuritsuriotoshi", "送り吊り落とし"), Type.YOTSU ),
	SOKUBIOTOSHI( new Name( "Sokubiotoshi", "素首落とし"), Type.TSUKI ),
	TSURIDASHI( new Name( "Tsuridashi", "吊り出し"), Type.YOTSU ),
	TSURIOTOSHI( new Name( "Tsuriotoshi", "吊り落とし"), Type.YOTSU ),
	USHIROMOTARE( new Name( "Ushiromotare", "後ろもたれ"), Type.OSHI ),
	WARIDASHI( new Name( "Waridashi", "割り出し"), Type.SPECIAL ),
	YOBIMODOSHI( new Name( "Yobimodoshi", "呼び戻し"), Type.HIKU ),
	FUMIDASHI( new Name( "Fumidashi", "踏み出し"), Type.NON_LOSING),
	ISAMIASHI( new Name( "Isamiashi", "勇み足"), Type.NON_LOSING),
	KOSHIKUDAKE( new Name( "Koshikudake", "腰砕け"), Type.NON_LOSING),
	TSUKIHIZA( new Name( "Tsukihiza", "つきひざ"), Type.NON_LOSING),
	TSUKITE( new Name( "Tsukite", "つき手"), Type.NON_LOSING);
	
	public enum Type {
		YOTSU,
		OSHI,
		TSUKI,
		NAGE,
		GAKE,
		HIKU,
		SPECIAL,
		DEFENSE,
		NON_LOSING
	};
	
	private Name name;
	private Type type;
	
	private Kimarite( Name aName, Kimarite.Type type ){
		this.name = aName;
		this.type = type;
	}
	
	public Name getName(){
		return name;
	}
	
	public Type getType(){
		return type;
	}
}
