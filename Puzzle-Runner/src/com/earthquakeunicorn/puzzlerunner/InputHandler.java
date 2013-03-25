package com.earthquakeunicorn.puzzlerunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class InputHandler 
{	
	public static boolean right, left, jump, reset, back, menu;
	public static Vector2 coords;
	private static float lastBackRead = 0;
	private static float lastMenuRead = 0;
	
	
	public static void handleGameInput()
	{
		right = false;
		left = false;
		jump = false;
		reset = false;
		back = false;
		menu = false;
		
		coords = new Vector2();
		
		if(Gdx.input.isKeyPressed(Keys.D))
			right = true;
		
		if(Gdx.input.isKeyPressed(Keys.A))
			left = true;
		
		if(Gdx.input.isKeyPressed(Keys.SPACE))
			jump = true;
		
		if(Gdx.input.isKeyPressed(Keys.R))
			reset = true;
		
		if(Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE) && TimeUtils.nanoTime() - lastBackRead > 200000000f)
		{
			back = true;
			lastBackRead = TimeUtils.nanoTime();
		}
		
		if(Gdx.input.isKeyPressed(Keys.TAB) || Gdx.input.isKeyPressed(Keys.MENU) && TimeUtils.nanoTime() - lastMenuRead > 200000000f)
		{
			menu = true;
			lastMenuRead = TimeUtils.nanoTime();
		}
		
		for(int i = 0; i < 4; i++)
		{
			if(Gdx.input.isTouched(i))
			{
				int x = Gdx.input.getX(i);
				int y = Gdx.input.getY(i);
				
				if(x > 150 && y > 400 && x < 300)
					right = true;
				
				else if(x < 150 && y > 400)
					left = true;
				
				else if(x > 650 && y > 400)
					jump = true;
				
				else if(x < 80 && y < 80)
					reset = true;
				
				else 
				{
					coords.x = x;
					coords.y = y;
				}
			}
		}
	}
	
	public static void handleMenuInput()
	{
		coords = new Vector2();
		back = false;
		left = false;
		right = false;
		
		if(Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE) && TimeUtils.nanoTime() - lastBackRead > 300000000f)
		{
			back = true;
			lastBackRead = TimeUtils.nanoTime();
		}
		
		if(Gdx.input.isKeyPressed(Keys.D))
			right = true;
		
		if(Gdx.input.isKeyPressed(Keys.A))
			left = true;
		
		if(Gdx.input.isTouched())
		{
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();

			coords.x = x;
			coords.y = y;
		}
	}
}
