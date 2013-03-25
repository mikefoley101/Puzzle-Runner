package com.earthquakeunicorn.puzzlerunner.animateobjects;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.earthquakeunicorn.puzzlerunner.InputHandler;
import com.earthquakeunicorn.puzzlerunner.blocks.Block;
import com.earthquakeunicorn.puzzlerunner.blocks.BreakableBlock;
import com.earthquakeunicorn.puzzlerunner.blocks.Spike;
import com.earthquakeunicorn.puzzlerunner.blocks.TeleportBlock;
import com.earthquakeunicorn.puzzlerunner.blocks.WinBlock;
import com.earthquakeunicorn.puzzlerunner.screens.GameScreen;

public class Player extends AnimateObject 
{	
	private int speed = 3;
	private double gravity = 0.2;
	private int jumpSpeed = 6;
	private int maxFallSpeed = -5;
	
	private Vector2 initialPosition;
	
	private float lastBulletFire = 0;
	
	private boolean cameraPushing = false;
	public boolean isAlive = true;
	public boolean hasWon = false;
	
	private ArrayList<Bullet> bullets;
	
	private double momentum = 0;
	
	private Texture bulletTex;
	
	public Player(Texture t, Rectangle r, int col, int rows) 
	{
		super(t, r);
		bullets = new ArrayList<Bullet>();
		bulletTex = new Texture(Gdx.files.internal("textures/Bullet.png"));
		initialPosition = new Vector2(r.x, r.y);
	}
	
	@Override
	public void update(float delta, Camera camera)
	{
		super.update(delta);
		
		if(rect.y < -200)
			isAlive = false;
		
		if(rect.x + 400 <= camera.position.x)
		{
			cameraPushing = true;
		}
		
		else
			cameraPushing = false;
		
		tryMove(new Vector2(0, (int)momentum));
		
		if(InputHandler.right)
			tryMove(new Vector2(speed,0));
		
		else if(InputHandler.left && !cameraPushing)
			tryMove(new Vector2(-speed,0));
		
		else if(cameraPushing)
			tryMove(new Vector2(1, 0));
		
		if((InputHandler.coords.x != 0 && InputHandler.coords.y != 0) && TimeUtils.nanoTime() - lastBulletFire > 200000000f)
		{
			Bullet temp = new Bullet(bulletTex, new Rectangle(rect.x + rect.width/2, rect.y + rect.height/2, 8, 8));
			temp.fire(new Vector2(temp.rect.x, temp.rect.y), 
					  new Vector2(GameScreen.camera.position.x - GameScreen.camera.viewportWidth / 2 + InputHandler.coords.x, GameScreen.camera.position.y + GameScreen.camera.viewportHeight/2 - InputHandler.coords.y));
			bullets.add(temp);
			lastBulletFire = TimeUtils.nanoTime();
		}
		
		if(InputHandler.jump)
		{
			if(momentum == 0)
			{
				momentum = jumpSpeed;
			}
		}
		
		if(momentum > maxFallSpeed)
		{
			momentum -= gravity;
		}
		
		//Bullet Collision checking
		Iterator<Bullet> iter = bullets.iterator();
		while(iter.hasNext()) 
		{
		   	Bullet bullet = iter.next();
		   	
		   	bullet.update(delta);
			
			if(bullet.rect.x > GameScreen.camera.position.x + GameScreen.camera.viewportWidth / 2
					|| bullet.rect.x < GameScreen.camera.position.x - GameScreen.camera.viewportWidth / 2)
			{
				iter.remove();
			}
			
			else if(bullet.rect.y > GameScreen.camera.position.y + GameScreen.camera.viewportHeight / 2 
					|| bullet.rect.y < GameScreen.camera.position.y - GameScreen.camera.viewportHeight / 2)
			{
				iter.remove();
			}
		   	
			else
			{
				Iterator<Block> iter2 = GameScreen.level.blocks.iterator();
				while(iter2.hasNext()) 
				{
					Object cur = iter2.next();
					
					if(bullet.rect.overlaps(((Block)cur).rect) && ((Block)cur).active)
					{
						//Teleport!!
						if(cur.getClass() == TeleportBlock.class)
						{
							move(new Vector2(((Block)cur).rect.x, ((Block)cur).rect.y + ((Block)cur).rect.height));
						}
						
						//BreakableBlock Hit
						else if(cur.getClass() == BreakableBlock.class)
						{
							((BreakableBlock)cur).health--;
							if(((BreakableBlock)cur).health == 0)
							{
								((BreakableBlock)cur).active = false;
							}
						}
							
						
						iter.remove();
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		for(Bullet cur : bullets)
			cur.draw(batch);
		super.draw(batch);
	}
	
	public void tryMove(Vector2 vec)
	{
		float prevX = rect.x;
		float prevY = rect.y;
		
		move(new Vector2(rect.x + vec.x, rect.y + vec.y));
		
		for(Block cur : GameScreen.level.blocks)
		{
			if(vec.x > 0)
			{
				if(this.isOnLeftOf(cur))
				{
					if(cur.getClass() == Spike.class)
					{
						isAlive = false;
						break;
					}
					else if(cur.getClass() == WinBlock.class)
					{
						hasWon = true;
						break;
					}
					else if(cameraPushing)
					{
						isAlive = false;
						break;
					}
					else
					{
						move(new Vector2(prevX,prevY));
						break;
					}
				}
			}
			
			else if(vec.x < 0)
			{
				if(this.isOnRightOf(cur))
				{
					if(cur.getClass() == Spike.class)
					{
						isAlive = false;
						break;
					}
					else if(cur.getClass() == WinBlock.class)
					{
						hasWon = true;
						break;
					}
					else
					{
						move(new Vector2(prevX,prevY));
						break;
					}
				}
			}
			
			if(vec.y > 0)
			{
				if(this.isOnBottomOf(cur))
				{
					if(cur.getClass() == Spike.class)
					{
						isAlive = false;
						break;
					}
					else if(cur.getClass() == WinBlock.class)
					{
						hasWon = true;
						break;
					}
					else
					{
						move(new Vector2(prevX,prevY));
						break;
					}
				}
			}
			
			else if(vec.y < 0)
			{
				if(this.isOnTopOf(cur))
				{
					if(cur.getClass() == Spike.class)
					{
						isAlive = false;
						break;
					}
					else if(cur.getClass() == WinBlock.class)
					{
						hasWon = true;
						break;
					}
					else
					{
						move(new Vector2(prevX,prevY));
						momentum = 0;
						break;
					}
				}
			}
		}
	}

	public void reset()
	{
		move(initialPosition);
		isAlive = true;
		hasWon = false;
		momentum = 0;
		bullets.clear();
	}
}