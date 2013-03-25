package com.earthquakeunicorn.puzzlerunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.earthquakeunicorn.puzzlerunner.animateobjects.Player;
import com.earthquakeunicorn.puzzlerunner.blocks.Block;
import com.earthquakeunicorn.puzzlerunner.blocks.BreakableBlock;
import com.earthquakeunicorn.puzzlerunner.blocks.Spike;
import com.earthquakeunicorn.puzzlerunner.blocks.TeleportBlock;
import com.earthquakeunicorn.puzzlerunner.blocks.WinBlock;

public class LevelBuilder 
{	
	public static Level buildLevel(String filePath)
	{
		FileHandle file = Gdx.files.internal(filePath);
		Level level = new Level();
		
		String entireLevel = file.readString();
		
		String[] tokens = entireLevel.split("\n");
		
		for(int i = tokens.length-1; i >= 0;i--)
		{
			String curLine = tokens[i];
			
			for(int j = 0; j < curLine.length(); j++)
			{
				if(curLine.charAt(j) == '#')
					level.addBlock(new Block(new Texture(Gdx.files.internal("textures/block.png")), new Rectangle(j*32, ((tokens.length-1)-i)*32, 32, 32)));
				else if(curLine.charAt(j) == '@')
					level.addPlayer(new Player(new Texture(Gdx.files.internal("textures/player.png")), new Rectangle(j*32, ((tokens.length-1)-i)*32, 32, 32), 0, 0));
				else if(curLine.charAt(j) == '&')
					level.addBlock(new WinBlock(new Texture(Gdx.files.internal("textures/block.png")), new Rectangle(j*32, ((tokens.length-1)-i)*32, 32, 32)));
				else if(curLine.charAt(j) == '^')
					level.addBlock(new Spike(new Texture(Gdx.files.internal("textures/spike.png")), new Rectangle(j*32, ((tokens.length-1)-i)*32, 32, 32)));
				else if(curLine.charAt(j) == '*')
					level.addBlock(new TeleportBlock(new Texture(Gdx.files.internal("textures/spike.png")), new Rectangle(j*32, ((tokens.length-1)-i)*32, 32, 32)));
				else if(curLine.charAt(j) == '|')
					level.addBlock(new BreakableBlock(new Texture(Gdx.files.internal("textures/block.png")), new Rectangle(j*32, ((tokens.length-1)-i)*32, 32, 32)));
			}
		}
		
		return level;
	}
}