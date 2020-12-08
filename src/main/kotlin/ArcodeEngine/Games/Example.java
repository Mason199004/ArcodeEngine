package ArcodeEngine.Games;


import ArcodeEngine.Engine.Game;
import ArcodeEngine.Engine.GameObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Example extends Game
{
	ArrayList<GameObject> Entities = new ArrayList<GameObject>();

	public Example(@NotNull String Name) {
		super(Name);
	}

	@NotNull
	@Override
	public ArrayList<GameObject> getEntities() {
		return Entities;
	}
}
