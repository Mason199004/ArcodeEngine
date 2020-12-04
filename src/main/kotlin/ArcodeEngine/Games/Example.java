package ArcodeEngine.Games;


import ArcodeEngine.Game;
import ArcodeEngine.GameObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
