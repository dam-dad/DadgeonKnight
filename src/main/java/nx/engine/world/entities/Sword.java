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
		Player player = getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
		.map(entity -> (Player) entity).findAny().get();
		
		List<Optional<MobEntity>> nearMobs = getWorld().getEntities().stream()
				.filter(entity -> entity instanceof MobEntity)
				.filter(e -> e.getDistanceToEntity(player) < 200)
				.map(e -> Optional.of((MobEntity) e))
				.toList();

		player.setAttacking(true);
		switch (player.getDirection()) {
		case WEST:
			setPosition(player.getPosX() + Game.tileSize, player.getPosY());
			nearMobs.forEach(e -> {
				if(e.isPresent() && this.checkCollision(e.get())) {
					e.get().getAttacked(SwordDamage);
				}
			});
			break;
		case EAST:
			setPosition(player.getPosX() - Game.tileSize, player.getPosY());
			nearMobs.forEach(e -> {
				if(e.isPresent() && this.checkCollision(e.get())) {
					e.get().getAttacked(SwordDamage);
				}
			});
			break;
		case NORTH:
			setPosition(player.getPosX(), player.getPosY() - Game.tileSize);
			nearMobs.forEach(e -> {
				if(e.isPresent() && this.checkCollision(e.get())) {
					e.get().getAttacked(SwordDamage);
				}
			});
			break;
		case SOUTH:
			setPosition(player.getPosX(), player.getPosY() + Game.tileSize);
			nearMobs.forEach(e -> {
				if(e.isPresent() && this.checkCollision(e.get())) {
					e.get().getAttacked(SwordDamage);
				}
			});
			break;
		default:
			break;
		}
	}
}
