package edu.cmu.cs.dennisc.scenegraph.builder;

public class ModelBuilder {
	private ModelPart root;
	private java.util.Set< edu.cmu.cs.dennisc.scenegraph.IndexedTriangleArray > geometries;
	private java.util.Set< edu.cmu.cs.dennisc.texture.BufferedImageTexture > textures;
	
	private static final String MAIN_ENTRY_PATH = "main.bin";
	private static final String INDEXED_TRIANGLE_ARRAY_PREFIX = "indexedTriangleArrays/";
	private static final String INDEXED_TRIANGLE_ARRAY_POSTFIX = ".bin";
	private static final String BUFFERED_IMAGE_TEXTURE_PREFIX = "bufferedImageTextures/";
	private static final String BUFFERED_IMAGE_TEXTURE_POSTFIX = ".png";
	private ModelBuilder() {
	}

	private static java.util.Map< java.io.File, ModelBuilder > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	//private static java.util.Map< java.io.File, TreeBuilder > map = edu.cmu.cs.dennisc.java.util.Collections.newWeakHashMap();

	public static ModelBuilder getInstance( java.io.File file ) throws java.io.IOException {
		ModelBuilder rv = map.get( file );
		if( rv != null ) {
			//pass
		} else {
			rv = new ModelBuilder();
			try {
				java.util.Map< Integer, edu.cmu.cs.dennisc.scenegraph.IndexedTriangleArray > mapIdToGeometry = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
				java.util.Map< Integer, edu.cmu.cs.dennisc.texture.BufferedImageTexture > mapIdToTexture = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();

				java.io.FileInputStream fis = new java.io.FileInputStream( file );
				java.util.Map< String, byte[] > map = edu.cmu.cs.dennisc.zip.ZipUtilities.extract( fis );

				java.io.InputStream isMainEntry = null;
				for( String entryPath : map.keySet() ) {
					java.io.InputStream is = new java.io.ByteArrayInputStream( map.get( entryPath ) );
					if( entryPath.startsWith( INDEXED_TRIANGLE_ARRAY_PREFIX ) ) {
						String s = entryPath.substring( INDEXED_TRIANGLE_ARRAY_PREFIX.length(), entryPath.length()-INDEXED_TRIANGLE_ARRAY_POSTFIX.length() );
						int id = Integer.parseInt( s );
						edu.cmu.cs.dennisc.codec.BinaryDecoder decoder = new edu.cmu.cs.dennisc.codec.InputStreamBinaryDecoder( is );
						edu.cmu.cs.dennisc.scenegraph.IndexedTriangleArray ita = new edu.cmu.cs.dennisc.scenegraph.IndexedTriangleArray();
						ita.vertices.setValue( decoder.decodeBinaryEncodableAndDecodableArray( edu.cmu.cs.dennisc.scenegraph.Vertex.class ) );
						ita.polygonData.setValue( decoder.decodeIntArray() );
						mapIdToGeometry.put( id, ita );
						
					} else if( entryPath.startsWith( BUFFERED_IMAGE_TEXTURE_PREFIX ) ) {
						String s = entryPath.substring( BUFFERED_IMAGE_TEXTURE_PREFIX.length(), entryPath.length()-BUFFERED_IMAGE_TEXTURE_POSTFIX.length() );
						
						boolean isPotentiallyAlphaBlended = s.charAt( 0 ) == 't'; 
						int id = Integer.parseInt( s.substring( 1 ) );

						java.awt.image.BufferedImage bufferedImage = edu.cmu.cs.dennisc.image.ImageUtilities.read( edu.cmu.cs.dennisc.image.ImageUtilities.PNG_CODEC_NAME, is );
						edu.cmu.cs.dennisc.texture.BufferedImageTexture texture = new edu.cmu.cs.dennisc.texture.BufferedImageTexture();
						texture.setBufferedImage( bufferedImage );
						texture.setPotentiallyAlphaBlended( isPotentiallyAlphaBlended );
						mapIdToTexture.put( id, texture );
					} else {
						assert entryPath.equals( MAIN_ENTRY_PATH ) : entryPath;
						isMainEntry = is;
					}
				}
				edu.cmu.cs.dennisc.codec.BinaryDecoder decoder = new edu.cmu.cs.dennisc.codec.InputStreamBinaryDecoder( isMainEntry );
				rv.root = decoder.decodeBinaryEncodableAndDecodable( ModelPart.class );
				rv.root.resolve( mapIdToGeometry, mapIdToTexture );

				rv.geometries = edu.cmu.cs.dennisc.java.util.Collections.newHashSet( mapIdToGeometry.values() );
				rv.textures = edu.cmu.cs.dennisc.java.util.Collections.newHashSet( mapIdToTexture.values() );
			} catch( java.io.IOException ioe ) {
				throw new RuntimeException( file.toString(), ioe );
			}
			map.put( file, rv );
		}
		return rv;
	}
	public static ModelBuilder newInstance( edu.cmu.cs.dennisc.scenegraph.Transformable transformable ) {
		ModelBuilder rv = new ModelBuilder();
		rv.geometries = edu.cmu.cs.dennisc.java.util.Collections.newHashSet();
		rv.textures = edu.cmu.cs.dennisc.java.util.Collections.newHashSet();
		rv.root = ModelPart.newInstance( transformable, rv.geometries, rv.textures );
		return rv;
	}
	public edu.cmu.cs.dennisc.scenegraph.Transformable buildTransformable() {
		edu.cmu.cs.dennisc.scenegraph.Transformable rv = this.root.build();
		return rv;
	}
	
	private static String getEntryPath( edu.cmu.cs.dennisc.scenegraph.Geometry geometry ) {
		if( geometry instanceof edu.cmu.cs.dennisc.scenegraph.IndexedTriangleArray ) {
			return INDEXED_TRIANGLE_ARRAY_PREFIX + geometry.hashCode() + ".bin";
		} else {
			return null;
		}
	}
	private static String getEntryPath( edu.cmu.cs.dennisc.texture.Texture texture ) {
		if( texture instanceof edu.cmu.cs.dennisc.texture.BufferedImageTexture ) {
			edu.cmu.cs.dennisc.texture.BufferedImageTexture bufferedImageTexture = (edu.cmu.cs.dennisc.texture.BufferedImageTexture)texture;
			char c;
			if( bufferedImageTexture.isPotentiallyAlphaBlended() ) {
				c = 't';
			} else {
				c = 'f';
			}
			return BUFFERED_IMAGE_TEXTURE_PREFIX + c + texture.hashCode() + ".png";
		} else {
			return null;
		}
	}
	
	public void encode( java.io.File file ) throws java.io.IOException {
		java.io.FileOutputStream fos = new java.io.FileOutputStream( file );
		java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream( fos );
		for( final edu.cmu.cs.dennisc.scenegraph.IndexedTriangleArray geometry : this.geometries ) {
			edu.cmu.cs.dennisc.zip.ZipUtilities.write( zos, new edu.cmu.cs.dennisc.zip.DataSource() {
				public String getName() {
					return getEntryPath( geometry );
				}
				public void write( java.io.OutputStream os ) throws java.io.IOException {
					edu.cmu.cs.dennisc.codec.BinaryEncoder encoder = new edu.cmu.cs.dennisc.codec.OutputStreamBinaryEncoder( os );
					encoder.encode( geometry.vertices.getValue() );
					encoder.encode( geometry.polygonData.getValue() );
					encoder.flush();
				}
			} );
		}
		for( final edu.cmu.cs.dennisc.texture.BufferedImageTexture texture : this.textures ) {
			edu.cmu.cs.dennisc.zip.ZipUtilities.write( zos, new edu.cmu.cs.dennisc.zip.DataSource() {
				public String getName() {
					return getEntryPath( texture );
				}
				public void write( java.io.OutputStream os ) throws java.io.IOException {
					edu.cmu.cs.dennisc.image.ImageUtilities.write( edu.cmu.cs.dennisc.image.ImageUtilities.PNG_CODEC_NAME, os, texture.getBufferedImage() );
				}
			} );
		}
		edu.cmu.cs.dennisc.zip.ZipUtilities.write( zos, new edu.cmu.cs.dennisc.zip.DataSource() {
			public String getName() {
				return MAIN_ENTRY_PATH;
			}
			public void write( java.io.OutputStream os ) throws java.io.IOException {
				edu.cmu.cs.dennisc.codec.BinaryEncoder encoder = new edu.cmu.cs.dennisc.codec.OutputStreamBinaryEncoder( os );
				encoder.encode( root );
				encoder.flush();
			}
		} );
		zos.flush();
		zos.close();
	}
}
