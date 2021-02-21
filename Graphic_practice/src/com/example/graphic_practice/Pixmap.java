package com.example.graphic_practice;

import com.example.graphic_practice.Graphics.PixmapFormat;

public interface Pixmap{
	public int getWidth();
	public int getHeight();
	public PixmapFormat getFormat();
	public void dispose();
}
