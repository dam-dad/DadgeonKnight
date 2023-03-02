package nx.engine.UI;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Game;
import nx.engine.world.entities.Entity;
import nx.engine.world.entities.PickableEntity;
import nx.engine.world.entities.Player;

public class Inventory implements UI {
	
	private List<Entity> items = new ArrayList<>();
	private int selectionInventory = 0;
	
	PickableEntity entitySelected;
	
	@Override
	public void update(double delta) {
		entitySelected = (PickableEntity) getItemSelected();
		
		items.stream().filter(item -> ((PickableEntity) item).hasEffect()).forEach(e -> {
			switch (((PickableEntity) e).getEffect()) {
			case SPEED:
				if(Player.SPEED != Player.INITIAL_SPEED * 2)
					Player.SPEED = Player.INITIAL_SPEED * 2;
				break;
			case ARMOR:
				if(Player.TOTAL_PLAYER_HEALTH < 20) {
					Player.TOTAL_PLAYER_HEALTH = Player.INITIAL_MAX_HEALTH * 2;
					Player.HEALTH = Player.TOTAL_PLAYER_HEALTH;
				}
				break;
			default:
				break;
			}
		});
	}
	@Override
	public void draw(GraphicsContext gc) {
		entitySelected.drawUI(gc);
	}
	public void nextItem() {
		if((selectionInventory + 1) >= getInventory().size())
			return;
		selectionInventory++;
		Game.inputHandler.ClearActiveKeys();
	}
	public void previousItem() {
		if(selectionInventory <=  0)
			return;
		selectionInventory--;
		Game.inputHandler.ClearActiveKeys();
	}
	public Entity getItemSelected() {
		return getInventory().size() > 0 ? this.getInventory().get(selectionInventory) : new PickableEntity();
	}
	public void useSelectedItem() {
		PickableEntity p = (PickableEntity) getItemSelected();
		p.useItem();
	}
	public List<Entity> getInventory() {
		return items;
	}
	public void addEntityToInventory(PickableEntity e) {
		getInventory().add(e);
	}
	
	public boolean isEmpty() {
		return this.items.size() <= 0;
	}

	


}
