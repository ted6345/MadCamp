package com.example.graphic_practice;

public interface Game_interface {
	public Input getInput();
	public FileIO getFileIO();
	public Graphics getGrahphics();
	public Audio getAudio();
	public void setScreen(Screen screen);
	public Screen getCurrentScreen();
	public Screen GetStartScreen();

}


public abstract class Screen
{
	protected final Game game;
	public Screen(Game game)
	{
		this.game = game;
	}
	
	public abstract void update(float deltaTime);
	public abstract void present(float deltaTime);
	public abstract void pause();
	public abstract void resume();
	public abstract void dispose();
}