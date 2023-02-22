package nx.engine.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.concurrent.Task;
import nx.engine.Game;
import nx.engine.world.entities.Player;

public class PathfindingManager extends Task<List<Vector2D>> {
	
	final int maxCol;
	final int maxRow;
	
	Tile[][] map;
	
	Tile currentNode,startNode,endNode;
	
	ArrayList<Tile> openList = new ArrayList<>();
	ArrayList<Tile> checkedList = new ArrayList<>();
	
	boolean goalReach = false;
	boolean isSearching = false;
	
	int steps = 0;
	final int maxSteps = 100;
	
	public PathfindingManager(int startX,int startY) {
		map = Player.get().getWorld().getLevel().getCollisionLayer().getTiles();
		
		maxCol = map.length;
		maxRow = map[0].length;
		
		setStart(startX, startY);
		setEnd((int)(Math.round(Player.get().getPosX()/Game.tileSize)), (int)(Math.round(Player.get().getPosY()/Game.tileSize)));
		
		currentNode = startNode;
		
		setCost();
		
		Game.logger.log(Level.INFO,"finding: " + endNode.col + "," + endNode.row);
	}
	
	public PathfindingManager(Vector2D v) {
		this((int)Math.round(v.getX()),(int)Math.round(v.getY()));
	}
	
	@Override
	protected List<Vector2D> call() throws Exception {
		double drawInterval = 1000000000/1000;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		
		while(true) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				search();
				
				steps++;
				Game.logger.log(Level.INFO,"steps: " + steps);
				if(steps > maxSteps) {
					Game.logger.log(Level.INFO,"Player not found");
					return null;
				}
					
				delta--;
			}
			if(goalReach) {
				Game.logger.log(Level.INFO,"Player found");
				return trackThePath();
			}
				
			
			if(timer >= 1000000000)
				timer = 0;
		}
	}

	public void search() 
	{
		if(goalReach == false) 
		{
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.setAsChecked();
			checkedList.add(currentNode);
			openList.remove(currentNode);
			
			Game.logger.log(Level.INFO,"goalReach: " + goalReach + " " + col + " " + row);
			Game.logger.log(Level.INFO,openList.toString());
			Game.logger.log(Level.INFO,checkedList.toString());
			
			// OPEN THE UP NODE
			if(row - 1 >= 0) {
				openNode(map[col][row - 1]);
			}
			// OPEN THE LEFT NODE
			if(col - 1 >= 0) {
				openNode(map[col - 1][row]);
			}
			// OPEN THE DOWN NODE
			if(row + 1 < maxRow) {
				openNode(map[col][row + 1]);
			}
			// OPEN THE RIGHT NODE
			if(col + 1 < maxCol) {
				openNode(map[col + 1][row]);
			}
			
			// FIND THE BEST NODE
			
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for(int i = 0 ; i< openList.size();i++) {
				//Check if this node's f cost is better
				if(openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				//If F cost is equal, check the g cost
				else if(openList.get(i).fCost == bestNodefCost) {
					if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			//After the loop i done, we get the next best node,the next step
			
			currentNode = openList.get(bestNodeIndex);
			
			if(currentNode == endNode) 
			{
				goalReach = true;
				isSearching = false;
			}
			
		}
		
	}
	public void setCost() 
	{
		for(int col = 0; col < map.length; col++) {
			for(int row = 0; row < map[0].length ; row++) {
				getCost(map[col][row]);
			}
		}
	}
	
	public void getCost(Tile tile)
	{
		//gCost
		int xDistance = Math.abs(tile.col - startNode.col);
		int yDistance = Math.abs(tile.row - startNode.row);
		tile.gCost = xDistance + yDistance;
		
		//hCost
		xDistance = Math.abs(tile.col - endNode.col);
		yDistance = Math.abs(tile.row - endNode.row);
		tile.hCost = xDistance + yDistance;
		
		
		//fCost
		
		tile.fCost = tile.gCost + tile.hCost;
		
	}
	private void setStart(int col,int row) 
	{
		map[col][row].setStart();
		startNode = map[col][row];
	}
	private void setEnd(int col,int row) 
	{
		map[col][row].setEnd();
		endNode = map[col][row];
	}
	private void openNode(Tile node) {
		  
		if(!node.open && !node.checked && !node.isSolid()) 
		{
			node.setAsOpen();
			node.parent = currentNode;
			openList.add(node);
		}
	}
	public List<Vector2D> trackThePath() {
		Tile current = endNode;
		
		List<Vector2D> movement = new ArrayList<Vector2D>();
		
		while(current != startNode) {
			current = current.parent;
			
			if(current != startNode) {
				int dx =  current.parent.col - current.col;
				int dy =  current.parent.row - current.row;
				movement.add(new Vector2D(dx,dy));
			}
		}
		
		return  movement;
	}
}
