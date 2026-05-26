package com.ignorehighlighter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Ignore;
import net.runelite.api.NameableContainer;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class IgnoreHighlighterOverlay extends Overlay
{
	private static final int MINIMAP_DOT_RADIUS = 4;
	private static final Font NAME_FONT = new Font("Arial", Font.BOLD, 12);

	private final Client client;
	private final IgnoreHighlighterConfig config;

	@Inject
	public IgnoreHighlighterOverlay(Client client, IgnoreHighlighterConfig config)
	{
		this.client = client;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Set<String> ignoredNames = buildIgnoreSet();
		if (ignoredNames.isEmpty())
		{
			return null;
		}

		Color color = config.highlightColor();

		List<Player> players = client.getPlayers();
		for (Player player : players)
		{
			if (player == null || player == client.getLocalPlayer())
			{
				continue;
			}

			String name = player.getName();
			if (name == null || !ignoredNames.contains(name.toLowerCase()))
			{
				continue;
			}

			if (config.highlightTile())
			{
				renderTile(graphics, player, color);
			}

			if (config.highlightHull())
			{
				renderHull(graphics, player, color);
			}

			if (config.showName())
			{
				renderName(graphics, player, color);
			}

			if (config.showMinimapDot())
			{
				renderMinimapDot(graphics, player, color);
			}
		}

		return null;
	}

	private void renderTile(Graphics2D graphics, Player player, Color color)
	{
		Polygon poly = player.getCanvasTilePoly();
		if (poly == null)
		{
			return;
		}

		Color fill = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50);
		graphics.setColor(fill);
		graphics.fillPolygon(poly);

		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(1));
		graphics.drawPolygon(poly);
	}

	private void renderHull(Graphics2D graphics, Player player, Color color)
	{
		Shape hull = player.getConvexHull();
		if (hull == null)
		{
			return;
		}

		Color fill = new Color(color.getRed(), color.getGreen(), color.getBlue(), 40);
		graphics.setColor(fill);
		graphics.fill(hull);

		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(2));
		graphics.draw(hull);
	}

	private void renderName(Graphics2D graphics, Player player, Color color)
	{
		String name = player.getName();
		if (name == null)
		{
			return;
		}

		Point textLocation = player.getCanvasTextLocation(graphics, name, player.getLogicalHeight() + 40);
		if (textLocation == null)
		{
			return;
		}

		graphics.setFont(NAME_FONT);

		// Black drop shadow for readability
		graphics.setColor(Color.BLACK);
		graphics.drawString(name, textLocation.getX() + 1, textLocation.getY() + 1);

		graphics.setColor(color);
		graphics.drawString(name, textLocation.getX(), textLocation.getY());
	}

	private void renderMinimapDot(Graphics2D graphics, Player player, Color color)
	{
		Point minimapLocation = player.getMinimapLocation();
		if (minimapLocation == null)
		{
			return;
		}

		graphics.setColor(color);
		graphics.fillOval(
			minimapLocation.getX() - MINIMAP_DOT_RADIUS,
			minimapLocation.getY() - MINIMAP_DOT_RADIUS,
			MINIMAP_DOT_RADIUS * 2,
			MINIMAP_DOT_RADIUS * 2
		);
	}

	private Set<String> buildIgnoreSet()
	{
		Set<String> names = new HashSet<>();
		NameableContainer<Ignore> container = client.getIgnoreContainer();
		if (container == null)
		{
			return names;
		}

		Ignore[] ignores = container.getMembers();
		if (ignores == null)
		{
			return names;
		}

		for (Ignore ignore : ignores)
		{
			if (ignore != null && ignore.getName() != null)
			{
				names.add(ignore.getName().toLowerCase());
			}
		}

		return names;
	}
}
