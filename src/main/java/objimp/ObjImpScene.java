package objimp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nate.sumo.display.TextureManager;
import com.obj.Face;
import com.obj.Group;
import com.obj.Material;
import com.obj.TextureCoordinate;
import com.obj.Vertex;
import com.obj.WavefrontObject;

import static org.lwjgl.opengl.GL11.*;

public class ObjImpScene 
{
	private static final Logger logger = LogManager.getLogger();
	
	public ObjImpScene()
	{
		
	  	_callListID = 0;
	    _callListCompiled = false; 
		
        _callListID = glGenLists( 1 ); 

    	
	    _vertexList = new ArrayList<float[]>();
	    _normalList = new ArrayList<float[]>();
	    _texcoordList = new ArrayList<float[]>();

	    _meshList = new ArrayList<ObjImpMesh>();
	    
	    _obj = null;
	} 
	
	public void load( String name, String textureName )
	{
	   load( name, textureName, 1.0f, 1.0f, 1.0f );
	} 
	  
	public void load( String name, String textureName, float sx, float sy, float sz )
	  {
	    _obj = new WavefrontObject( name, sx, sy, sz );  
	  
	    ArrayList<Group> groups = _obj.getGroups();
	    for( int gi=0; gi<groups.size(); gi++ )
	    {
	      Group g = groups.get( gi );
	      Material gm = g.getMaterial();

	      ObjImpMesh mesh = new ObjImpMesh();
	      mesh.setName( g.getName() );

	      if( gm != null )
	      {
	    	  if( gm.getKa() != null ) mesh._material._ambient = new float[]{ gm.getKa().getX(), gm.getKa().getY(), gm.getKa().getZ() };
	    	  else mesh._material._diffuse = new float[]{ 0.0f, 0.0f, 0.0f };
	    	  if( gm.getKd() != null ) mesh._material._diffuse = new float[]{ gm.getKd().getX(), gm.getKd().getY(), gm.getKd().getZ() };
	    	  else mesh._material._diffuse = new float[]{ 0.5f, 0.5f, 0.5f };
	    	  if( gm.getKs() != null ) mesh._material._specular = new float[]{ gm.getKs().getX(), gm.getKs().getY(), gm.getKs().getZ() };
	    	  else mesh._material._specular = new float[]{ 0, 0, 0 };
	    	  mesh._material._shininess = gm.getShininess();
	      	      
		      if( gm.texName != null && gm.texName.length() > 0 )
		      {
		    	  try
		    	  { 
		    		  int texId = TextureManager.getInstance().loadTexture( textureName );
//		    		  Texture _tex = TextureIO.newTexture( new File(_parent.dataPath(gm.texName)), true );
//		    	      _tex.setTexParameteri( GL_TEXTURE_WRAP_S, GL_REPEAT );
//		    	      _tex.setTexParameteri( GL_TEXTURE_WRAP_T, GL_REPEAT ); 
//		    		  
			    	  mesh._material._texId = texId;
			    	  //System.out.println( "mesh._material._texId: " + mesh._material._texId  );
		    	  }
			      catch( IOException e )
			      {
			        logger.error( "(OBJScene) Failed loading texture '" + gm.texName + "' with error: " + e );
			      } catch (URISyntaxException e) {
					
			        logger.error( "(OBJScene) Failed loading texture '" + gm.texName + "' with error: " + e );
				  }
		      }
	      }
	      else
	      {
	    	  // default material
	    	  mesh._material._ambient = new float[]{ 0, 0, 0, 0 };
	    	  mesh._material._diffuse = new float[]{ 0.5f, 0.5f, 0.5f, 1 };
	    	  mesh._material._specular = new float[]{ 1, 1, 1, 1 };
	    	  mesh._material._shininess = 64.0f;
	    	  mesh._material._texId = 0;
	      }


	      for( int fi=0; fi<g.getFaces().size(); fi++ )
	      {
	        Face f = g.getFaces().get( fi );
	        int[] idx = f.vertIndices;
	        int[] nidx = f.normIndices;
	        int[] tidx = f.texIndices;

	        
	        if( f.getType() == Face.GL_TRIANGLES )
	        {
		        ObjImpFace face = new ObjImpFace();
		        face._a = idx[0];
		        face._b = idx[1];
		        face._c = idx[2];
		        face._na = nidx[0];
		        face._nb = nidx[1];
		        face._nc = nidx[2];
		        face._ta = tidx[0];
		        face._tb = tidx[1];
		        face._tc = tidx[2];
		        mesh.addFace( face );
	        }
	        else if( f.getType() == Face.GL_QUADS )
	        {
		        ObjImpFace face = new ObjImpFace();
		        ObjImpFace face2 = new ObjImpFace();
	        	
		        face._a = idx[0];
		        face._b = idx[1];
		        face._c = idx[2];
		        face._na = nidx[0];
		        face._nb = nidx[1];
		        face._nc = nidx[2];
		        face._ta = tidx[0];
		        face._tb = tidx[1];
		        face._tc = tidx[2];	        	

		        face2._a = idx[0];
		        face2._b = idx[2];
		        face2._c = idx[3];
		        face2._na = nidx[0];
		        face2._nb = nidx[2];
		        face2._nc = nidx[3];
		        face2._ta = tidx[0];
		        face2._tb = tidx[2];
		        face2._tc = tidx[3];	        	
		        
		        mesh.addFace( face );
		        mesh.addFace( face2 );
	        }
	      }

	      for( int vi=0; vi<_obj.getVertices().size(); vi++ )
	      {
	        Vertex v = (Vertex)_obj.getVertices().get( vi );
	        _vertexList.add( new float[]{v.getX(), v.getY(), v.getZ()} );
	      }

	      for( int vi=0; vi<_obj.getNormals().size(); vi++ )
	      {
	        Vertex v = (Vertex)_obj.getNormals().get( vi );
	        _normalList.add( new float[]{v.getX(), v.getY(), v.getZ()} );
	      }

	      for( int vi=0; vi<_obj.getTextures().size(); vi++ )
	      {
	        TextureCoordinate tc = (TextureCoordinate)_obj.getTextures().get( vi );
	        _texcoordList.add( new float[]{tc.getU(), tc.getV(), tc.getW()} );
	      }

	/*      for( int vi=0; vi<g.indices.size(); vi++ )
	      {
	        int tc = (int)g.indices.get( vi );
	        mesh.addVertex( new Vector3( tc.getX(), tc.getY(), tc.getZ()) );
	      }

	      for( int vi=0; vi<g.vertices.size(); vi++ )
	      {
	        Vertex tc = (Vertex)g.vertices.get( vi );
	        mesh.addVertex( new Vector3( tc.getX(), tc.getY(), tc.getZ()) );
	      }

	      for( int vi=0; vi<g.normals.size(); vi++ )
	      {
	        Vertex tc = (Vertex)g.normals.get( vi );
	        mesh.addNormal( new Vector3( tc.getX(), tc.getY(), tc.getZ()) );
	      }

	      for( int vi=0; vi<g.texcoords.size(); vi++ )
	      {
	        TextureCoordinate tc = (TextureCoordinate)g.texcoords.get( vi );
	        mesh.addTexCoord( new Vector3( tc.getU(), tc.getV(), tc.getW()) );
	      }*/

	      // Finally add mesh to scene
	      addMesh( mesh );
	    }
	  } 	  
	  
	public void addMesh( ObjImpMesh mesh )
	  {
	    _meshList.add( mesh );
	  } 

	
	public void drawWithExternalTextureMap()
	{
		
	    // If the list is compiled and everything is ok, render
	    if( _callListID > 0 && _callListCompiled )
	    {
	    	glCallList( _callListID );
	    	
	    	return;
	    } 

	    if( _callListID > 0 && !_callListCompiled )
	    {
	        //_callListID = glGenLists( 1 ); 
	        glNewList( _callListID, GL_COMPILE );  
	    }
		
		
	    // Render all scene
	    for( int i=0; i<_meshList.size(); i++ )
	    {
	    	ObjImpMesh m = _meshList.get( i );
	      
	      // render triangles.. this is too basic. should be optimized
	      glBegin( GL_TRIANGLES );
	      
	      //if( m._material._texId > 0 )
	    	  //glColor4f( 1, 1, 1, 1 );
	      //else
//	      if( m._material != null )glColor4f( m._material._diffuse[0], m._material._diffuse[1], m._material._diffuse[2], 1.0f );
	      for( int fi=0; fi<m._faceList.size(); fi++ )
//	      for( int fi = m._faceList.size()-1; fi >= 0; fi-- )
	      {
	        ObjImpFace f = (ObjImpFace)m._faceList.get( fi );

	        glNormal3f( _normalList.get(f._na)[0], _normalList.get(f._na)[1], _normalList.get(f._na)[2] );
	        glTexCoord2f( _texcoordList.get(f._ta)[0], _texcoordList.get(f._ta)[1] );
	        glVertex3f( _vertexList.get(f._a)[0], _vertexList.get(f._a)[1], _vertexList.get(f._a)[2] );

	        glNormal3f( _normalList.get(f._nb)[0], _normalList.get(f._nb)[1], _normalList.get(f._nb)[2] );
	        glTexCoord2f( _texcoordList.get(f._tb)[0], _texcoordList.get(f._tb)[1] );
	        glVertex3f( _vertexList.get(f._b)[0], _vertexList.get(f._b)[1], _vertexList.get(f._b)[2] );
	        
	        glNormal3f( _normalList.get(f._nc)[0], _normalList.get(f._nc)[1], _normalList.get(f._nc)[2] );
	        glTexCoord2f( _texcoordList.get(f._tc)[0], _texcoordList.get(f._tc)[1] );
	        glVertex3f( _vertexList.get(f._c)[0], _vertexList.get(f._c)[1], _vertexList.get(f._c)[2] );
	      }
	      
	      glEnd();
	    }

		if( _callListID > 0 && !_callListCompiled )
		{
		  glEndList();
		  _callListCompiled = true;
		}     	    
	}	
	  
	public void draw()
	{
		
	    // If the list is compiled and everything is ok, render
	    if( _callListID > 0 && _callListCompiled )
	    {
	    	glCallList( _callListID );
	    	
	    	return;
	    } 

	    if( _callListID > 0 && !_callListCompiled )
	    {
	        //_callListID = glGenLists( 1 ); 
	        glNewList( _callListID, GL_COMPILE );  
	    }
		
		
	    // Render all scene
	    for( int i=0; i<_meshList.size(); i++ )
	    {
	    	ObjImpMesh m = _meshList.get( i );

	      // If current material has texture, bind it
	    //System.out.println( m._name + "  -- texid: " + m._material._texId );
	      if( m._material._texId > 0 )
	      {
	        glEnable( GL_TEXTURE_2D );
	        glBindTexture( GL_TEXTURE_2D, m._material._texId );
	      }
	      else
	      {
	        glBindTexture( GL_TEXTURE_2D, 0 );
	        glDisable( GL_TEXTURE_2D );
	      }

	      if( m._material != null )
	      {
//	    	  glMaterialfv( GL_FRONT_AND_BACK, GL_AMBIENT, new float[]{m._material._ambient[0], m._material._ambient[1], m._material._ambient[2], 1.0f}, 0 ); 
//	    	  glMaterialfv( GL_FRONT_AND_BACK, GL_DIFFUSE, new float[]{m._material._diffuse[0], m._material._diffuse[1], m._material._diffuse[2], 1.0f}, 0 ); 
//	    	  glMaterialfv( GL_FRONT_AND_BACK, GL_SPECULAR, new float[]{m._material._specular[0], m._material._specular[1], m._material._specular[2], 1.0f}, 0 ); 
//	    	  glMaterialf( GL_FRONT_AND_BACK, GL_SHININESS, m._material._shininess );
	    	  //glEnable( GL_COLOR_MATERIAL );
	      }
	      
	      // render triangles.. this is too basic. should be optimized
	      glBegin( GL_TRIANGLES );
	      
	      //if( m._material._texId > 0 )
	    	  //glColor4f( 1, 1, 1, 1 );
	      //else
	      if( m._material != null )glColor4f( m._material._diffuse[0], m._material._diffuse[1], m._material._diffuse[2], 1.0f );
	      for( int fi=0; fi<m._faceList.size(); fi++ )
	      {
	        ObjImpFace f = (ObjImpFace)m._faceList.get( fi );

	        glNormal3f( _normalList.get(f._na)[0], _normalList.get(f._na)[1], _normalList.get(f._na)[2] );
	        if( m._material._texId > 0 ) glTexCoord2f( _texcoordList.get(f._ta)[0], _texcoordList.get(f._ta)[1] );
	        glVertex3f( _vertexList.get(f._a)[0], _vertexList.get(f._a)[1], _vertexList.get(f._a)[2] );

	        glNormal3f( _normalList.get(f._nb)[0], _normalList.get(f._nb)[1], _normalList.get(f._nb)[2] );
	        if( m._material._texId > 0 ) glTexCoord2f( _texcoordList.get(f._tb)[0], _texcoordList.get(f._tb)[1] );
	        glVertex3f( _vertexList.get(f._b)[0], _vertexList.get(f._b)[1], _vertexList.get(f._b)[2] );
	        
	        glNormal3f( _normalList.get(f._nc)[0], _normalList.get(f._nc)[1], _normalList.get(f._nc)[2] );
	        if( m._material._texId > 0 ) glTexCoord2f( _texcoordList.get(f._tc)[0], _texcoordList.get(f._tc)[1] );
	        glVertex3f( _vertexList.get(f._c)[0], _vertexList.get(f._c)[1], _vertexList.get(f._c)[2] );
	      }
	      
	      glEnd();
	    }

		if( _callListID > 0 && !_callListCompiled )
		{
		  glEndList();
		  _callListCompiled = true;
		}     	    
	}	  
	
	
	public ObjImpMesh getMeshByIdx( int i )
	{
		return _meshList.get( i );
	}

	
	
	public ObjImpMesh getMeshByName( String name )
	{
	    for( int i=0; i<_meshList.size(); i++ )
	    {
	    	ObjImpMesh m = _meshList.get( i );
	    	if( m._name.equals(name) )
	    		return _meshList.get( i );
	    }

	    return null;
	}

	  
	//
	// Members
	//
	
	// compile a list for the scene. this is just for starters.
	int _callListID;
	boolean _callListCompiled;   

	ArrayList<float[]> _vertexList;
	ArrayList<float[]> _normalList;
	ArrayList<float[]> _texcoordList;
	ArrayList<ObjImpMesh> _meshList;
	  
	private WavefrontObject _obj;
}
