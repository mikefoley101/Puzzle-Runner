package com.earthquakeunicorn.puzzlerunner.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BreakableBlock extends Block 
{
	public int health = 3;
	
	public BreakableBlock(Texture text, Rectangle rect) 
	{
		super(text, rect);
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		if(active)
			batch.draw(text, rect.x, rect.y);
	}


	@Override
	public void update(float delta) 
	{
		
	}
	
	@Override
	public void reset()
	{
		health = 3;
		active = true;
	}
}