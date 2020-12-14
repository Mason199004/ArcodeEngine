package ArcodeEngine.Games;


import ArcodeEngine.Engine.GameState;
import ArcodeEngine.Engine.GameObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Example extends GameState
{
	ArrayList<GameObject> Entities = new ArrayList<GameObject>();

	public Example(@NotNull String Name) {
		super(Name);
	}


	@Override
	public void Tick() {

	}

	@Override
	public void Render() {

	}

	@Override
	public void Init() {

	}
}
