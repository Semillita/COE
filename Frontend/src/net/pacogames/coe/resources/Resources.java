package net.pacogames.coe.resources;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**Utility class to import assets*/
public class Resources {

	public Texture playerTexture;
	
	public Texture arenaBorder;
	
	private static Map<String, Texture> textures = new HashMap<>();
	
	/**Returns the cached {@link Texture} instance mapped to a file path. If no texture exists
	 * originating from that specific file path, a new one is created and configured
	 * 
	 * @param path the file path to the {@link Texture}
	 * @return the cached/created texture*/
	public static Texture getTexture(String path) {
		path = "textures/" + path;
		return (textures.containsKey(path)) ? textures.get(path) : createTexture(path);
	}
	
	/**Creates a {@link Texture} and configures it with the right properties for size filtering
	 * 
	 * @param path the file path to the texture
	 * @return the created texture*/
	private static Texture createTexture(String path) {
		Texture texture = new Texture(Gdx.files.internal(path), true);
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		textures.put(path, texture);
		return texture;
	}
	
}
