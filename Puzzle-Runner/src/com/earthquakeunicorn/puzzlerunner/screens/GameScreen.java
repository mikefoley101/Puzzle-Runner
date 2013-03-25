package com.earthquakeunicorn.puzzlerunner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.earthquakeunicorn.puzzlerunner.HUD;
import com.earthquakeunicorn.puzzlerunner.InputHandler;
import com.earthquakeunicorn.puzzlerunner.Level;
import com.earthquakeunicorn.puzzlerunner.LevelBuilder;
import com.earthquakeunicorn.puzzlerunner.PuzzleRunner;

public class GameScreen implements Screen 
{
	public static Level level;
	public static OrthographicCamera camera;
	
	SpriteBatch batch;
	PuzzleRunner game;
	HUD hud;
	
	public static enum State
	{
		win,
		lose,
		pause,
		playing
	}
	
	public static State state;
	
	public GameScreen(PuzzleRunner game, String levelPath)
	{
		level = LevelBuilder.buildLevel(levelPath);
		this.game = game;
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		hud = new HUD();
		
		state = State.playing;
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		InputHandler.handleGameInput();
		
		if(InputHandler.reset)
			reset();
		
		switch(state)
		{
		
			case playing:
				
				if(InputHandler.menu)
					state = State.pause;
				
				if(InputHandler.back)
					game.returnToLevelSelect();
				
				camera.position.x++;
				camera.position.y = level.player.rect.y + 50;
				camera.update();
				
				level.update(delta, camera);
				
				if(!level.player.isAlive)
					state = State.lose;
				else if(level.player.hasWon)
					state = State.win;
				
				hud.update(camera);
				
				batch.setProjectionMatrix(camera.combined);
				
				batch.begin();
				level.draw(batch);
				hud.draw(batch, state);
				batch.end();
				
				break;
				
			case win:
				
				batch.setProjectionMatrix(camera.combined);
				
				batch.begin();
				level.draw(batch);
				hud.draw(batch, state);
				batch.end();
				
				break;
				
			case lose:
				
				batch.setProjectionMatrix(camera.combined);
				
				batch.begin();
				level.draw(batch);
				hud.draw(batch, state);
				batch.end();
				
				break;
				
			case pause:
				
				if(InputHandler.back)
					state = State.playing;
				
				batch.setProjectionMatrix(camera.combined);
				
				batch.begin();
				level.draw(batch);
				hud.draw(batch, state);
				batch.end();
				
				break;
		}
		
	}
	
	public void reset()
	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		level.reset();
		camera.position.y = level.player.rect.y + 100;
		state = State.playing;
	}

	@Override
	public void resize(int width, int height) 
	{
		
	}

	@Override
	public void show() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
		
	}

}
