package com.earthquakeunicorn.puzzlerunner.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.earthquakeunicorn.puzzlerunner.InputHandler;
import com.earthquakeunicorn.puzzlerunner.LevelFrame;
import com.earthquakeunicorn.puzzlerunner.PuzzleRunner;

public class LevelSelectScreen implements Screen 
{
	OrthographicCamera camera;
	SpriteBatch batch;
	PuzzleRunner game;
	
	Array<Page> pages;
	
	int curPage = 0;
	
	Texture frameTex;
	
	public LevelSelectScreen(PuzzleRunner game)
	{
		this.game = game;
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		pages = new Array<Page>();
		
		frameTex = new Texture(Gdx.files.internal("textures/levelFrame.png"));
		
		FileHandle dirHandle;
		if (Gdx.app.getType() == ApplicationType.Android) 
		{
		   dirHandle = Gdx.files.internal("maps/");
		} 
		
		else 
		{
		  dirHandle = Gdx.files.internal("./bin/maps");
		}
		
		
		FileHandle[] dirList = dirHandle.list();
		int totalLevels = dirList.length;
		int totalPagesInt = totalLevels/28;
		double totalPagesDouble = totalLevels/28.0;
		if(totalPagesDouble>totalPagesInt)
			totalPagesInt++;
		
		int lastIndex = 0;
		int gotoIndex = 28;
		
		for(int i = 0; i < totalPagesInt; i++)
		{
			pages.add(new Page());
			if(gotoIndex > dirList.length)
				gotoIndex = dirList.length;
			
			int rows = 3;
			int columns = 0;
			for (int j = lastIndex; j < gotoIndex; j++) 
			{
			   LevelFrame frame = new LevelFrame(frameTex, new Rectangle(columns * 110 + 25, rows * 110 + 25, 100, 100), dirList[j].path(), dirList[j].name());
			   pages.get(i).levelList.add(frame);
			   rows--;
			   if(rows < 0)
			   {
				   rows = 3;
				   columns++;
			   }
			}
			
			lastIndex = gotoIndex;
			gotoIndex+=20;
		}
	}
	
	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		InputHandler.handleMenuInput();
		
		if(InputHandler.back)
			game.returnToMainMenu();
		if(InputHandler.right)
			curPage = 1;
		if(InputHandler.left)
			curPage = 0;
		
		batch.begin();
		
		
		for(LevelFrame frame : pages.get(curPage).levelList)
		{
			frame.update(delta);
			if(frame.isTouched(InputHandler.coords))
				game.selectLevel(frame.getFilePath());
			frame.draw(batch);
		}
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() 
	{
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

class Page
{
	public Array<LevelFrame> levelList;
	
	public Page()
	{
		levelList = new Array<LevelFrame>();
	}
}