package nx.engine.world.entities;


import java.util.List;
import java.util.Optional;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.engine.world.MobEntity;

public class Sword extends PickableEntity {
	
	private static final double ANIMATION_SPEED = 0.15;
	
	private static int SwordDamage = 4;
	
	public Sword(TileSet tileset, double x, double y, double width, double height) {
		super(TileSetManager.loadImageFromTileSet(tileset, 91, 16, 16), x, y, width, height);
	}
	
	@Override
	public Shape getCollisionShape() {
		return new Rectangle(getPosX(),getPosY(),Game.tileSize,Game.tileSize);
	}
	
	@Override
	public void useItem() {
		
		List<Optional<MobEntity>> nearMobs = Player.get().getWorld().getEntities().stream()
				.filter(entity -> entity instanceof MobEntity)
				.filter(e -> e.getDistanceToEntity(Player.get()) < 200)
				.map(e -> Optional.of((MobEntity) e))
				.toList();

		Player.get().setAttacking(true);
		switch (Player.get().getDirection()) {
		case WEST:
			setPosition(Player.get().getPosX() + Game.tileSize, Player.get().getPosY());
			nearMobs.forEach(e -> {
				if(e.isPresent() && this.checkCollision(e.get())) {
					e.get().getAttacked(SwordDamage);
				}
			});
			break;
		case EAST:
			setPosition(Player.get().getPosX() - Game.tileSize, Player.get().getPosY());
			nearMobs.forEach(e -> {
				if(e.isPresent() && this.checkCollision(e.get())) {
					e.get().getAttacked(SwordDamage);
				}
			});
			break;
		case NORTH:
			setPosition(Player.get().getPosX(), Player.get().getPosY() - Game.tileSize);
			nearMobs.forEach(e -> {
				if(e.isPresent() && this.checkCollision(e.get())) {
					e.get().getAttacked(SwordDamage);
				}
			});
			break;
		case SOUTH:
			setPosition(Player.get().getPosX(), Player.get().getPosY() + Game.tileSize);
			nearMobs.forEach(e -> {
				if(e.isPresent() && this.checkCollision(e.get())) {
					e.get().getAttacked(SwordDamage);
				}
			});
			break;
		default:
			break;
		}
		Game.inputHandler.ClearActiveKeys();
	}
}
