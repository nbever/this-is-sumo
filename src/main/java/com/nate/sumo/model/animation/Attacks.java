package com.nate.sumo.model.animation;

public class Attacks {

	private interface AnimEnumIf{
		public String getFilename();
	}
	
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
}
