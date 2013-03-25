package com.earthquakeunicorn.puzzlerunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.earthquakeunicorn.puzzlerunner.animateobjects.Player;
import com.earthquakeunicorn.puzzlerunner.blocks.Block;

public class Level 
{
	public Array<Block> blocks;
	//public ArrayList<Enemy> enemies;
	
	public Player player;
	
	public Level()
	{
		blocks = new Array<Block>();
	}
	
	public void addBlock(Block block)
	{
		blocks.add(block);
	}
	
	public void addPlayer(Player p)
	{
		player = p;
	}
	
	public void update(float delta, Camera camera)
	{
		for(Block cur : blocks)
			cur.update(delta);
		player.update(Gdx.graphics.getDeltaTime(), camera);
	}
	
	public void draw(SpriteBatch batch)
	{
		for(Block cur : blocks)
			cur.draw(batch);		
		
		player.draw(batch);
	}
	
	public void reset()
	{
		player.reset();
		for(Block cur : blocks)
			cur.reset();
	}
}
