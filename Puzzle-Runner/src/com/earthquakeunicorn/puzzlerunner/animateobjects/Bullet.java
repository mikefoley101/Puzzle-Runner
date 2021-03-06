package com.earthquakeunicorn.puzzlerunner.animateobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.earthquakeunicorn.puzzlerunner.Entity;

public class Bullet extends Entity
{
	Vector2 speed;
	
	public Bullet(TextureRegion t, Rectangle r) 
	{
		super(t, r);
	}
	
	public void fire(Vector2 source, Vector2 target)
	{		
		double speed = 15f;
        Vector2 mousePosition = new Vector2(1, (target.y - source.y) / (target.x - source.x));
        double angle = Math.atan(mousePosition.y / mousePosition.x);


        Vector2 finalSpeed = new Vector2((float)(speed*Math.cos(angle)), (float)(speed*Math.sin(angle)));
        if (target.x < source.x)
        {
            finalSpeed.mul(-1);
        }
        this.speed = finalSpeed;
	}

	@Override
	public void update(float delta) 
	{
		 move(new Vector2(rect.x + speed.x, rect.y + speed.y));
	}

	@Override
	public void draw(SpriteBatch batch) 
	{
		batch.draw(text, rect.x, rect.y);
	}
	
}
