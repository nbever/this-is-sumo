package com.nate.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PicScraper {

	public static void main(String[] args) {

		String baseUrl = "http://www.sumo.or.jp/sumo_data/rikishi/profile?id=";
		File imageDir = new File( "src/main/resources/images/portraits/" );
		
		if ( !imageDir.exists() ){
			imageDir.mkdirs();
		}
		
		boolean keepGoing = true;
		int index = 0;
		
		while( keepGoing == true ){
			
			try {
				URL url = new URL( baseUrl + index );
				InputStream inStream = null;
				System.out.println( "Trying: " + url.toString() );
				
				try {
					inStream = url.openStream();
				}
				catch( FileNotFoundException fe ){
					System.out.println( "Could not find file." );
					System.out.println( "Moving to the next one." );
					index++;
					continue;
				}
				
				System.out.println( "Found!" );
				
				BufferedReader reader = new BufferedReader( new InputStreamReader( inStream ) );

				String line;
				String imgUrl = null;
				boolean startName = false;
				boolean nameFinished = false;
				String nameBlock = "";
				
				while( (line = reader.readLine()) != null || ( imgUrl == null && nameFinished == false ) ){
					
					int imgIndex = line.indexOf( "/img/sumo_data/rikishi" );
					if ( imgIndex != -1 ){
						int endIndex = line.indexOf( "\"", imgIndex+1 );
						
						String subPath = line.substring( imgIndex, endIndex );
						imgUrl = "http://www.sumo.or.jp" + subPath;
					}
					
					if ( line.indexOf( "<td colspan=\"2\" class=\"fntXL\">" ) != -1 ){
						startName = true;
					}
					
					if ( startName == true ){
						
						if ( line.indexOf(  "<br" ) != -1 ){
							startName = false;
							nameFinished = true;
							nameBlock += line;
						}
					}
				}// while
				
				// now we have the name to parse and the image url to copy from
				String name = nameBlock.trim();
				name = name.substring( 0, name.indexOf(  "<" ) );
				File newFile = new File( imageDir.getPath() + File.separatorChar + name.replaceAll( " ", "_" ) + ".jpg" );
				
				if ( !newFile.exists() ){
					System.out.println( "Trying to download: " + imgUrl.toString() );
					InputStream imgStream = new URL( imgUrl ).openStream();
					System.out.println( "Trying to write: " + newFile.getAbsolutePath() );
					Files.copy( imgStream, newFile.toPath(), StandardCopyOption.REPLACE_EXISTING );
					imgStream.close();
				}
				else {
					System.out.println( "File exists: " + newFile.getAbsolutePath() );
				}
				
				inStream.close();
				reader.close();
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			index++;
			
			if ( index > 3663 ){
				keepGoing = false;
			}
		}
	}

}
