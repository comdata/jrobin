/* ============================================================
 * JRobin : Pure java implementation of RRDTool's functionality
 * ============================================================
 *
 * Project Info:  http://www.jrobin.org
 * Project Lead:  Sasa Markovic (saxon@jrobin.org)
 * 
 * Developers:    Sasa Markovic (saxon@jrobin.org)
 *                Arne Vandamme (cobralord@jrobin.org)
 *
 * (C) Copyright 2003, by Sasa Markovic.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 */
package jrobin.graph2;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics2D;

/**
 * <p>description</p>
 * 
 * @author Arne Vandamme (arne.vandamme@jrobin.org)
 */
public class ChartGraphics 
{
	Graphics2D g;
	
	private int width, height;
	private long xStart, xEnd;
	private double yStart, yEnd;

	double widthDelta = 1.0d, heightDelta = 3.0d;

	ChartGraphics( Graphics2D graphics )
	{
		g = graphics;
	}

	void drawLine(int x1, int y1, int x2, int y2)
	{
		g.drawLine( x1, -y1, x2, -y2 );
	}

	// Contrary to Graphics2D fillRect, this method uses boundary points
	void fillRect(int x1, int y1, int x2, int y2)
	{
		g.fillRect( x1, -y2, x2 - x1, y2 - y1 );
	}
	
	void setColor( Color c )
	{
		g.setColor( c );
	}

	void setMeasurements( int width, int height )
	{
		this.width  = width;
		this.height	= height;
	}

	void setXRange( long start, long end )
	{
		xStart 	= start;
		xEnd	= end; 
	
		if ( xEnd != xStart )
			widthDelta = width * 1.0d / (( xEnd - xStart) * 1.0d);
		else
			widthDelta = 1.0d;
	}

	void setYRange( double lower, double upper )
	{
		yStart 	= lower;
		yEnd	= upper; 
	
		if ( yEnd != yStart )
			heightDelta = height * 1.0d / (( yEnd - yStart) * 1.0d);
		else
			heightDelta = 1.0d;
	}

	int getX( long timestamp )
	{
		return new Double((timestamp - xStart) * widthDelta).intValue();
	}

	int getY( double value )
	{
		if ( Double.isNaN(value) ) return Integer.MIN_VALUE;
	
		int tmp = new Double( (value - ( yStart < 0 ? 0 : Math.abs(yStart) ) ) * heightDelta).intValue();
	
		return ( tmp > value * heightDelta ? tmp - 1 : tmp ); 
	}
	
	void setStroke( Stroke s )
	{
		g.setStroke( s );
	}
}
