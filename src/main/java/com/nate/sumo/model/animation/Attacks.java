package com.nate.sumo.model.animation;

public class Attacks {
	
	public static enum TSUKI_ATTACK implements AnimEnumIf{
		DEFAULT( "tsuki_default.md5anim" );
		
		private String filename;
		
		private TSUKI_ATTACK( String file ){
			filename = file;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum OSHI_ATTACK implements AnimEnumIf{
		DEFAULT( "oshi_default.md5anim" );
		
		private String filename;
		
		private OSHI_ATTACK( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_UWATE_NAGE implements AnimEnumIf{
		MIGI_UWATE_NAGE_DEFAULT( "migi_uwate_default.md5anim" );
		
		private String filename;
		
		private MIGI_UWATE_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_UWATE_NAGE implements AnimEnumIf{
		DEFAULT( "hidari_uwate_default.md5anim" );
		
		private String filename;
		
		private HIDARI_UWATE_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_ONLY_UWATE_NAGE implements AnimEnumIf{
		MIGI_ONLY_UWATE_NAGE_DEFAULT( "migi_only_uwate_default.md5anim" );
		
		private String filename;
		
		private MIGI_ONLY_UWATE_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_ONLY_UWATE_NAGE implements AnimEnumIf{
		DEFAULT( "hidari_only_uwate_default.md5anim" );
		
		private String filename;
		
		private HIDARI_ONLY_UWATE_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_SHITATE_NAGE implements AnimEnumIf{
		DEFAULT( "migi_shitate_default.md5anim" );
		
		private String filename;
		
		private MIGI_SHITATE_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_SHITATE_NAGE implements AnimEnumIf{
		DEFAULT( "hidari_shitate_default.md5anim" );
		
		private String filename;
		
		private HIDARI_SHITATE_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_ONLY_SHITATE_NAGE implements AnimEnumIf{
		DEFAULT( "migi_only_shitate_default.md5anim" );
		
		private String filename;
		
		private MIGI_ONLY_SHITATE_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_ONLY_SHITATE_NAGE implements AnimEnumIf{
		DEFAULT( "hidari_only_shitate_default.md5anim" );
		
		private String filename;
		
		private HIDARI_ONLY_SHITATE_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_SUKUI_NAGE implements AnimEnumIf{
		DEFAULT( "hidari_sukui_default.md5anim" );
		
		private String filename;
		
		private HIDARI_SUKUI_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_SUKUI_NAGE implements AnimEnumIf{
		DEFAULT( "migi_sukui_default.md5anim" );
		
		private String filename;
		
		private MIGI_SUKUI_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_NO_GRIP_SUKUI_NAGE implements AnimEnumIf{
		DEFAULT( "hidari_no_grip_sukui_default.md5anim" );
		
		private String filename;
		
		private HIDARI_NO_GRIP_SUKUI_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_NO_GRIP_SUKUI_NAGE implements AnimEnumIf{
		DEFAULT( "migi_no_grip_sukui_default.md5anim" );
		
		private String filename;
		
		private MIGI_NO_GRIP_SUKUI_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_KUBI_NAGE implements AnimEnumIf{
		DEFAULT( "migi_kubi_default.md5anim" );
		
		private String filename;
		
		private MIGI_KUBI_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_KUBI_NAGE implements AnimEnumIf{
		DEFAULT( "hidari_kubi_default.md5anim" );
		
		private String filename;
		
		private HIDARI_KUBI_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_KIMEDASHI implements AnimEnumIf{
		DEFAULT( "migi_kimedashi_default.md5anim" );
		
		private String filename;
		
		private MIGI_KIMEDASHI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_KIMEDASHI implements AnimEnumIf{
		DEFAULT( "hidari_kimedashi_default.md5anim" );
		
		private String filename;
		
		private HIDARI_KIMEDASHI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_SHITATE_DASHI_NAGE implements AnimEnumIf{
		DEFAULT( "migi_shitate_dashi_nage_default.md5anim" );
		
		private String filename;
		
		private MIGI_SHITATE_DASHI_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_SHITATE_DASHI_NAGE implements AnimEnumIf{
		DEFAULT( "hidari_shitate_dashi_nage_default.md5anim" );
		
		private String filename;
		
		private HIDARI_SHITATE_DASHI_NAGE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_UTCHARI implements AnimEnumIf{
		DEFAULT( "migi_utchari_default.md5anim" );
		
		private String filename;
		
		private MIGI_UTCHARI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_UTCHARI implements AnimEnumIf{
		DEFAULT( "hidari_utchari_default.md5anim" );
		
		private String filename;
		
		private HIDARI_UTCHARI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_SOTO_GAKE implements AnimEnumIf{
		DEFAULT( "migi_soto_gake_default.md5anim" );
		
		private String filename;
		
		private MIGI_SOTO_GAKE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_SOTO_GAKE implements AnimEnumIf{
		DEFAULT( "hidari_soto_gake_default.md5anim" );
		
		private String filename;
		
		private HIDARI_SOTO_GAKE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};	
	
	public static enum MIGI_UCHI_GAKE implements AnimEnumIf{
		DEFAULT( "migi_uchi_gake_default.md5anim" );
		
		private String filename;
		
		private MIGI_UCHI_GAKE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_UCHI_GAKE implements AnimEnumIf{
		DEFAULT( "hidari_uchi_gake_default.md5anim" );
		
		private String filename;
		
		private HIDARI_UCHI_GAKE( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_YORIKIRI implements AnimEnumIf{
		DEFAULT( "migi_yorikiri_default.md5anim" );
		
		private String filename;
		
		private MIGI_YORIKIRI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_YORIKIRI implements AnimEnumIf{
		DEFAULT( "hidari_yorikiri_default.md5anim" );
		
		private String filename;
		
		private HIDARI_YORIKIRI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_ONLY_YORIKIRI implements AnimEnumIf{
		DEFAULT( "migi_only_yorikiri_default.md5anim" );
		
		private String filename;
		
		private MIGI_ONLY_YORIKIRI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_ONLY_YORIKIRI implements AnimEnumIf{
		DEFAULT( "hidari_only_yorikiri_default.md5anim" );
		
		private String filename;
		
		private HIDARI_ONLY_YORIKIRI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_NO_GRIP_YORIKIRI implements AnimEnumIf{
		DEFAULT( "migi_no_grip_yorikiri_default.md5anim" );
		
		private String filename;
		
		private MIGI_NO_GRIP_YORIKIRI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_NO_GRIP_YORIKIRI implements AnimEnumIf{
		DEFAULT( "hidari_no_grip_yorikiri_default.md5anim" );
		
		private String filename;
		
		private HIDARI_NO_GRIP_YORIKIRI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MORO_ZASHII_YORIKIRI implements AnimEnumIf{
		DEFAULT( "moro_zashii_yorikiri_default.md5anim" );
		
		private String filename;
		
		private MORO_ZASHII_YORIKIRI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_TOTTARI implements AnimEnumIf{
		DEFAULT( "migi_tottari_default.md5anim" );
		
		private String filename;
		
		private MIGI_TOTTARI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_TOTTARI implements AnimEnumIf{
		DEFAULT( "hidari_tottari_default.md5anim" );
		
		private String filename;
		
		private HIDARI_TOTTARI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_HATAKIKOMI implements AnimEnumIf{
		DEFAULT( "migi_hatakikomi_default.md5anim" );
		
		private String filename;
		
		private MIGI_HATAKIKOMI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_HATAKIKOMI implements AnimEnumIf{
		DEFAULT( "hidari_hatakikomi_default.md5anim" );
		
		private String filename;
		
		private HIDARI_HATAKIKOMI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIKIOTOSHI implements AnimEnumIf{
		DEFAULT( "hikiotoshi_default.md5anim" );
		
		private String filename;
		
		private HIKIOTOSHI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MIGI_TSURI implements AnimEnumIf{
		DEFAULT( "migi_tsuri_default.md5anim" );
		
		private String filename;
		
		private MIGI_TSURI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum HIDARI_TSURI implements AnimEnumIf{
		DEFAULT( "hidari_tsuri_default.md5anim" );
		
		private String filename;
		
		private HIDARI_TSURI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	};
	
	public static enum MORO_TSURI implements AnimEnumIf{
		DEFAULT( "moro_tsuri_default.md5anim" );
		
		private String filename;
		
		private MORO_TSURI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MORO_OUT_TSURI implements AnimEnumIf{
		DEFAULT( "moro_out_tsuri_default.md5anim" );
		
		private String filename;
		
		private MORO_OUT_TSURI( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MIGI_BREAK_LEFT implements AnimEnumIf{
		DEFAULT( "migi_break_left_default.md5anim" );
		
		private String filename;
		
		private MIGI_BREAK_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MIGI_BREAK_RIGHT implements AnimEnumIf{
		DEFAULT( "migi_break_right_default.md5anim" );
		
		private String filename;
		
		private MIGI_BREAK_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum HIDARI_BREAK_LEFT implements AnimEnumIf{
		DEFAULT( "hidari_break_left_default.md5anim" );
		
		private String filename;
		
		private HIDARI_BREAK_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}	
	
	public static enum HIDARI_BREAK_RIGHT implements AnimEnumIf{
		DEFAULT( "hidari_break_right_default.md5anim" );
		
		private String filename;
		
		private HIDARI_BREAK_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MORO_BREAK_LEFT implements AnimEnumIf{
		DEFAULT( "moro_break_left_default.md5anim" );
		
		private String filename;
		
		private MORO_BREAK_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}	
	
	public static enum MORO_BREAK_RIGHT implements AnimEnumIf{
		DEFAULT( "moro_break_right_default.md5anim" );
		
		private String filename;
		
		private MORO_BREAK_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MORO_OUT_BREAK_LEFT implements AnimEnumIf{
		DEFAULT( "moro_out_break_left_default.md5anim" );
		
		private String filename;
		
		private MORO_OUT_BREAK_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MORO_OUT_BREAK_RIGHT implements AnimEnumIf{
		DEFAULT( "moro_out_break_right_default.md5anim" );
		
		private String filename;
		
		private MORO_OUT_BREAK_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MIGI_MOVE_LEFT implements AnimEnumIf{
		DEFAULT( "migi_move_left_default.md5anim" );
		
		private String filename;
		
		private MIGI_MOVE_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}	
	
	public static enum MIGI_MOVE_RIGHT implements AnimEnumIf{
		DEFAULT( "migi_move_right_default.md5anim" );
		
		private String filename;
		
		private MIGI_MOVE_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}	
	
	public static enum HIDARI_MOVE_LEFT implements AnimEnumIf{
		DEFAULT( "hidari_move_left_default.md5anim" );
		
		private String filename;
		
		private HIDARI_MOVE_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum HIDARI_MOVE_RIGHT implements AnimEnumIf{
		DEFAULT( "hidari_move_right_default.md5anim" );
		
		private String filename;
		
		private HIDARI_MOVE_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}	
	
	public static enum MORO_MOVE_LEFT implements AnimEnumIf{
		DEFAULT( "moro_move_left_default.md5anim" );
		
		private String filename;
		
		private MORO_MOVE_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MORO_MOVE_RIGHT implements AnimEnumIf{
		DEFAULT( "moro_move_right_default.md5anim" );
		
		private String filename;
		
		private MORO_MOVE_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MORO_OUT_MOVE_LEFT implements AnimEnumIf{
		DEFAULT( "moro_out_move_left_default.md5anim" );
		
		private String filename;
		
		private MORO_OUT_MOVE_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MORO_OUT_MOVE_RIGHT implements AnimEnumIf{
		DEFAULT( "moro_out_move_right_default.md5anim" );
		
		private String filename;
		
		private MORO_OUT_MOVE_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}	
	
	public static enum MOVE_LEFT implements AnimEnumIf{
		DEFAULT( "move_left_default.md5anim" );
		
		private String filename;
		
		private MOVE_LEFT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MOVE_RIGHT implements AnimEnumIf{
		DEFAULT( "move_right_default.md5anim" );
		
		private String filename;
		
		private MOVE_RIGHT( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MOVE_FORWARD implements AnimEnumIf{
		DEFAULT( "move_forward_default.md5anim" );
		
		private String filename;
		
		private MOVE_FORWARD( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
	
	public static enum MOVE_BACKWARD implements AnimEnumIf{
		DEFAULT( "move_backward_default.md5anim" );
		
		private String filename;
		
		private MOVE_BACKWARD( String aFile ){
			filename = aFile;
		}
		
		public String getFilename(){
			return filename;
		}
	}
}
