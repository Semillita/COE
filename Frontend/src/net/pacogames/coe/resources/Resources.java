package net.pacogames.coe.resources;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Resources {

	public Texture playerTexture;
	
	public Texture arenaBorder;
	
	private static Map<String, Texture> textures = new HashMap<>();
	
	public static Texture getTexture(String path) {
		return (textures.containsKey(path)) ? textures.get(path) : createTexture(path);
	}
	
	private static Texture createTexture(String path) {
		Texture texture = new Texture(Gdx.files.internal(path), true);
		texture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.MipMapLinearLinear);
		textures.put(path, texture);
		return texture;
	}
	
}
