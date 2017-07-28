/*****************************************************
CS 4365.001
Colleen Cousins
Matthew Villarreal
******************************************************/
package ctf.agent;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.lang.Integer;
import java.lang.System;
import java.util.ArrayList;
import java.lang.Math;
import ctf.common.AgentAction;
import ctf.common.AgentEnvironment;
import ctf.agent.Agent;

public class miv140130Agent extends Agent {

	private class AStar {

		// class variables
        public ArrayList<Cell> cellQueue = new ArrayList<Cell>();								// Queue for each cell on the grid
        public ArrayList<Cell> visited = new ArrayList<Cell>();									// Keep track of visited cells
        public ArrayList<ArrayList<Cell>> goodPaths = new ArrayList<ArrayList<Cell>>();			// Keeps a list of each good path
        public ArrayList<Position> goodList = new ArrayList<Position>();					// Keeps track of a good square/cell
        public ArrayList<Position> badList = new ArrayList<Position>();						// Keeps track of a bad square/cell
        
        // ****************** CONSTRUCTOR *************************
        AStar() {}
    }// end AStar class

	private class Cell implements Comparator<Cell> {
    	
		// class variables
    	Cell parent;
        Position location;
        Integer g;
        Integer h;
        Integer cost;
    	
    	// ****************** CONSTRUCTOR *************************
        public Cell(){
            parent = null;
            location = new Position();
            g = -1;
            h = -1;
            cost = -1;
        }// end Cell(0)
        public Cell(Cell old){
            parent = old.parent;
            location = old.location;
            g = old.g;
            h = old.h;
            cost = old.cost;
        }// end Cell(1)

     // ****************** METHOD *************************

        public int compare(Cell a, Cell b){
            if(a.cost < b.cost)
                return -1;
            else if(a.cost == b.cost)
                return 0;
            else
                return 1;
        }// end compare Method
    }// end Cell Class

	private class PathCompare implements Comparator<ArrayList<Cell>>{
        public int compare(ArrayList<Cell> a, ArrayList<Cell> b){
            if(a.size() < b.size())
                return -1;
            else if (a.size() == b.size())
                return 0;
            else
                return 1;
        }// end compare Method
    }// end PathCompare Class

	// Retrieves an explored location that is adjacent 
    public ArrayList<Position> getExplored(Position location){
        ArrayList<Position> posList = new ArrayList<Position>();
        Position moveNorthOne = location.moveNorthOne();
        Position moveEastOne = location.moveEastOne();
        Position moveSouthOne = location.moveSouthOne();
        Position moveWestOne = location.moveWestOne();

        if(listContains(aSSearch.goodList, moveNorthOne))
            posList.add(moveNorthOne);
        if(listContains(aSSearch.goodList, moveEastOne))
            posList.add(moveEastOne);
        if(listContains(aSSearch.goodList, moveSouthOne))
            posList.add(moveSouthOne);
        if(listContains(aSSearch.goodList, moveWestOne))
            posList.add(moveWestOne);
        return posList;
    }// end getExplored

    // Retrieves possible solutions in surrounding area
    public ArrayList<Position> getPossibleLocation(){
        ArrayList<Position> cList = new ArrayList<Position>();
        Position moveNorthOne = pawn.location.moveNorthOne();
        Position moveEastOne = pawn.location.moveEastOne();
        Position moveSouthOne = pawn.location.moveSouthOne();
        Position moveWestOne = pawn.location.moveWestOne();

        if(isNorth())
            cList.add(moveNorthOne);
        if(isEast())
            cList.add(moveEastOne);
        if(isSouth())
            cList.add(moveSouthOne);
        if(isWest())
            cList.add(moveWestOne);
        return cList;
    }// end getPossibleLocation

    public void addList(ArrayList<Cell> cList){
        for(int i = 0; i < cList.size(); i++)
            aSSearch.cellQueue.add(new Cell(cList.get(i)));
    }// end addList

    public ArrayList<Position> shortestPath(Position source, Position target){

        Cell current = getFirstCell(source, target);

        // Base visibility for AStar search
        if(!listContains(aSSearch.goodList, teamBase.location))
            aSSearch.goodList.add(teamBase.location);
        if(!listContains(aSSearch.goodList, enemyBase.location))
            aSSearch.goodList.add(enemyBase.location);

        aSSearch.visited.add(current);
        createAstarMap();

        for(int i = 0; ; i++){

            //Find goal location
            if(current.location.isSamePositions(target))
                aSSearch.goodPaths.add(addCell(current));	//add to list 
            
            addChildCell(current, target);

            if(aSSearch.cellQueue.size() != 0){
                Collections.sort(aSSearch.cellQueue, new Cell());
                current = aSSearch.cellQueue.get(0);
                aSSearch.cellQueue.remove(0);
                aSSearchmap[current.location.y][current.location.x] = '?';
                //printAStarMap();
            }// end if 
            else
                break;		 //break when empty
        }// end for
        
        // Found the solution
        if(aSSearch.goodPaths.size() != 0){
            ArrayList<Position> shortestPath = getSolution();
            return shortestPath;
        }// end if
        else
            return new ArrayList<Position>();
    }// end shortestPath

    public ArrayList<Position> getSolution(){

        Collections.sort(aSSearch.goodPaths, new PathCompare());
        ArrayList<Position> bestPath = new ArrayList<Position>();
        
        for(int i = 0; i < aSSearch.goodPaths.get(0).size(); i++)
            bestPath.add(new Position(aSSearch.goodPaths.get(0).get(i).location));
        return bestPath;
    }// end getSolution

    public ArrayList<Cell> addCell(Cell square){
    	
        ArrayList<Cell> solList = new ArrayList<Cell>();
        solList.add(square);
        
        while(true){
            square = square.parent;
            if(square == null)
                break;
            else
                solList.add(square);
        }// end while
        return solList;
    }// end addCell

    public boolean isCell(ArrayList<Cell> cList, Cell square){
        for(int i = 0; i < cList.size(); i++){
            if(square.location.isSamePositions(cList.get(i).location))
                return true;
        }// end for
        return false;
    }// end cList

    // Creates a child and adds to list if not there
    public void addChildCell(Cell currentCell, Position target){

        ArrayList<Cell> cList = getNextMove(currentCell, target);

        for(int i = 0; i < cList.size(); i++){
            if(!isCell(aSSearch.visited, cList.get(i))){
                aSSearch.visited.add(cList.get(i));
                aSSearch.cellQueue.add(cList.get(i));
            }// end if
        }// end for
    }// end addChildCell

    // Retrieves next moveInThatDirection
    public ArrayList<Cell> getNextMove(Cell currentCell, Position target){
        ArrayList<Position> surroundingPos = getExplored(currentCell.location);
        ArrayList<Cell> cList = getSurroundingMoves(currentCell, surroundingPos, target);
        return cList;
    }// end getNextMove

    // Create a copy of the First cell
    public Cell getFirstCell(Position pos, Position target){
        Cell newCell = new Cell();
        newCell.location = pos;
        newCell.g = 0;
        newCell.h = getDistance(pos, target);
        newCell.cost = newCell.g + newCell.h;
        return newCell;
    }// end getFirstCell

    // Finds surrounding moves that are valid (no obstList)
    public ArrayList<Cell> getSurroundingMoves(Cell parent, ArrayList<Position> posList, Position target){
        ArrayList<Cell> cList = new ArrayList<Cell>();
        for(int i = 0; i < posList.size(); i++){
            Cell newCell = new Cell();
            newCell.parent = parent;
            newCell.location = posList.get(i);
            newCell.g = parent.g + 1;
            newCell.h = getDistance(newCell.location, target);
            newCell.cost = newCell.g + newCell.h;
            cList.add(newCell);
        }
        return cList;
    }

    // Assign a Coordinate to each cell in list
    public ArrayList<Position> addPosition(ArrayList<Cell> cList){
        ArrayList<Position> posList = new ArrayList<Position>();
        
        for(int i = 0; i < cList.size(); i++){
            Position newPosition = new Position(cList.get(i).location);
            posList.add(newPosition);
        }// end for
        return posList;
    }// end addPosition

    // Compare cell costs with each other
    public int costCompare(Cell a, Cell b){
        if(a.cost > b.cost)
            return -1;
        else if(a.cost == b.cost)
            return 0;
        else
            return 1;
    }// end costCompare

    // Adds any good moveInThatDirection/cell to a list
    public AStar addGoodCell(Position location){
        if(!aSSearch.goodList.contains(location))
            aSSearch.goodList.add(location);
        
        return aSSearch;
    }// end addGoodCell

    // Adds any bad moveInThatDirection/cell to a list
    public AStar addBadCell(Position location){
        if(!aSSearch.badList.contains(location))
            aSSearch.badList.add(location);
   
        return aSSearch;
    }// end addBadCell

    // enum for ease of remembering obstacle locations relative to pawn
    enum obstacle {NONE, N_WALL, E_WALL, S_WALL, W_WALL}

 // modified Agent class to allow more control of Agent and ease of resetting when maps are changed
    private class Agent {
    	
        public Integer idNum;
        public char name;
        public ArrayList<objective> objList;
        public ArrayList<direction> directionList;
        public ArrayList<obstacle> obstList;
        
        public Position spawnPoint;
        public Position location;
        public ArrayList<Position> visitedCellList;
        public ArrayList<Integer> movesList;
        public boolean hasFlag;

        // *************** CONSTURCTOR *******************
        public Agent() {
            idNum = -1;
            objList = new ArrayList<objective>();
            addObjective(objective.INITIALIZE);
            directionList = new ArrayList<direction>();
            obstList = new ArrayList<obstacle>();
            
            spawnPoint = new Position();
            location = new Position();
            visitedCellList = new ArrayList<Position>();
            movesList = new ArrayList<Integer>();
            hasFlag = false;
        }// Agent()
        // *************** METHODS *******************


        // Retrieves string equivalent of objective
        public String getStringObjective(){

            String oString = "";

            switch(getObjective()){
                case INITIALIZE:
                    oString = "INITIALIZE";
                    break;
                case FIND_ENEMY_BASE:
                    oString = "FIND ENEMY BASE";
                    break;
                case FIND_OUR_BASE:
                    oString = "FIND OUR BASE";
                    break;
                case GET_FRIENDLY_FLAG:
                    oString = "FIND FRIENDLY FLAG";
                    break;
                case DEFEND_OUR_BASE:
                    oString = "DEFEND OUR BASE";
                    break;
                case MOVE_PAST_OBST:
                    oString = "MOVE AROUND OBSTACLE";
                    break;
                default:
                	break;
            }// end switch
            return oString;
        }// end getStringObjective

        // Returns current objective
        public objective getObjective() {
            return objList.get(objList.size() - 1);
        }// end getObjective

        public Agent setNewBase(objective obj){
            objList.set(0, obj);
            return this;
        }// end setNewBase

        public objective getBaseObjective(){
            return objList.get(0);
        }// end getBaseObjective

        public Agent addObjective(objective obj) {
            objList.add(obj);
            return this;
        }// addObjective

        public Agent removeObjective() {
            objList.remove(objList.size() - 1);
            return this;
        }// end removeObjective


     // returns last direction in directionList
        public direction getDirection() {
            return directionList.get(directionList.size() - 1);
        }

        public Agent addDirection(direction d) {
            directionList.add(d);
            return this;
        }// addDirection

        public Agent removeDirection() {
            directionList.remove(directionList.size() - 1);
            return this;
        }// end removeDirection

        public Position moveIfValid(direction assignedDirection){
            switch(assignedDirection){
                case NORTH:
                    return checkNorthAgentPosition();
                case EAST:
                    return checkEastAgentPosition();
                case SOUTH:
                    return checkSouthAgentPosition();
                case WEST:
                    return checkWestAgentPosition();
                default:
                    return this.location;
            }// end switch
        }// end moveIfValid

        public Position checkNorthAgentPosition() {
            return location.checkNorthPosition();
        }

        public Position checkEastAgentPosition() {
            return location.checkEastPosition();
        }

        public Position checkSouthAgentPosition() {
            return location.checkSouthPosition();
        }

        public Position checkWestAgentPosition() {
            return location.checkWestPosition();
        }
    }// end Agent class

    private class Landmark {
        String name;
        direction assignedDirection;
        Position location;

        Landmark(String name) {
            this.name = name;
            location = new Position();
        }// end Landmark(1)
    }

    // Keeps track of coordinates
    private class Position{
    	
        public Integer x;
        public Integer y;

        // **************** CONSTRUCTOR ******************
        public Position() {}
        
        public Position(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }// end Position(2)

        public Position(Position pos){
            this.x = pos.x;
            this.y = pos.y;
        }// end Position (1)

     // **************** METHODS ******************
        public void setXY(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }// end setXY

        public void setNewPosition(Position otherPosition) {
            this.x = otherPosition.x;
            this.y = otherPosition.y;
        }// end setNewPosition

        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        public boolean isSamePositions(Position otherPosition){
            return ((this.x == otherPosition.x) && (this.y == otherPosition.y));
        }


        public Position moveNorthOne(){
            return new Position(this.x, this.y - 1);
        }// end moveNorthOne

        public Position moveSouthOne(){
            return new Position(x, y + 1);
        }// end moveSouthOne

        public Position moveEastOne(){
            return new Position(x + 1, y);
        }// end moveEastOne

        public Position moveWestOne(){
            return new Position(x - 1, y);
        }// end moveWestOne

        public Position checkNorthPosition(){
            if(this.y == 0)
                return new Position(this.x, this.y);
            else
                return new Position(this.x, this.y - 1);
            
        }// end checkNorthPosition

        public Position checkSouthPosition(){
            if(this.y == mapSize)
                return new Position(x, y);
            else
                return new Position(x, y + 1);
            
        }// end checkSouthPosition

        public Position checkEastPosition(){
            if(this.x == mapSize)
                return new Position(x, y);
            else
                return new Position(x + 1, y);
            
        }// end checkEastPosition

        public Position checkWestPosition(){
            if(this.x == 0)
                return new Position(x, y);
            else
                return new Position(x - 1, y);
        }// end checkWestPosition
    }// end Position Class

    //Agent variables
    private final static int MAX_AGENTS = 2;
    private static ArrayList<Agent> agents = new ArrayList<Agent>();
    private Agent pawn;

    // Map Variables
    private static char[][] gameMap;
    private static int[][] sumVisitedMap;
    private int mapSize = 10;         //it is known that mapSize == mapLength
    private Landmark teamBase;
    private Landmark enemyBase;
    
    private Landmark findPath;
    private ArrayList<Landmark> goalLandmarkList;
    private ArrayList<Position> minesList = new ArrayList<Position>();

    private enum direction {NORTH, EAST, SOUTH, WEST, SOUTHWEST, SOUTHEAST, NORTHEAST, NORTHWEST, DONOTHING}
    private enum objective {INITIALIZE, FIND_ENEMY_BASE, FIND_ENEMY_BASE_GIVEN_PATH,  FIND_OUR_BASE, FIND_OUR_BASE_GIVEN_PATH, GET_FRIENDLY_FLAG, DEFEND_OUR_BASE, MOVE_PAST_OBST}

    // 2D map that aStar uses
    private static char[][] aSSearchmap;

    //Environment Variables
    private boolean obstacleNorth;
    private boolean obstacleSouth;
    private boolean obstacleEast;
    private boolean obstacleWest;
    
    private boolean obstacleNorthEast;
    private boolean obstacleSouthEast;
    private boolean obstacleNorthWest;
    private boolean obstacleSouthWest;
    
    private boolean obstacleNorthEastSouth;
    private boolean obstacleEastSouthWest;
    private boolean obstacleSouthWestNorth;
    private boolean obstacleWestNorthEast;
    private boolean obstacleEveryDirection;

    private boolean teamBaseNorth;
    private boolean teamBaseSouth;
    private boolean teamBaseWest;
    private boolean teamBaseEast;

    private boolean teamBaseNorthImmediate;
    private boolean teamBaseSouthImmediate;
    private boolean teamBaseWestImmediate;
    private boolean teamBaseEastImmediate;

    private boolean enemyBaseNorth;
    private boolean enemyBaseSouth;
    private boolean enemyBaseEast;
    private boolean enemyBaseWest;

    private boolean enemyBaseNorthImmediate;
    private boolean enemyBaseSouthImmediate;
    private boolean enemyBaseEastImmediate;
    private boolean enemyBaseWestImmediate;

    private boolean enemyNorthImmediate;
    private boolean enemyEastImmediate;
    private boolean enemySouthImmediate;
    private boolean enemyWestImmediate;

    private boolean enemyHasFlag;

    private boolean teammateNorthImmediate;
    private boolean teammateEastImmediate;
    private boolean teammateSouthImmediate;
    private boolean teammateWestImmediate;

 // variables to represent the 6 actions to return
    private int moveNorth = AgentAction.MOVE_NORTH;
    private int moveEast = AgentAction.MOVE_EAST;
    private int moveSouth = AgentAction.MOVE_SOUTH;
    private int moveWest = AgentAction.MOVE_WEST;
    private int doNothing = AgentAction.DO_NOTHING;

    private static boolean locatedEnemyBase = false;
    private static boolean locatedTeamBase = false;
    
    private static ArrayList<Position> shortestPathList = new ArrayList<Position>();
    private static AStar aSSearch;

    Random numRandom;

    // **************** CONSTRUCTOR ******************************
    public miv140130Agent() {

    	// Creating two agents for 
        if (agents.size() == MAX_AGENTS) {
            agents.clear();

            locatedEnemyBase = false;
            locatedTeamBase = false;

            shortestPathList.clear();
            aSSearch = new AStar();
        }// end if
        pawn = new Agent();
        agents.add(pawn);
        pawn.idNum = agents.size() - 1;
        pawn.name = (char) ('a' + pawn.idNum);
        

        // Creating game map
        createMap();
        makeVisitedMap();
        
        teamBase = new Landmark("teamBase");
        enemyBase = new Landmark("enemyBase");

        goalLandmarkList = new ArrayList<Landmark>();
        goalLandmarkList.add(teamBase);
        goalLandmarkList.add(enemyBase);

        aSSearch = new AStar();
    }// end miv140130Agent

    public int getMove(AgentEnvironment inEnvironment) {

        int task = doNothing;

        // Initializing all variables for easy typing throughout program
        initializeWithEnvironment(inEnvironment);

        // Initialize the pawns and base points
        if (pawn.getObjective() == objective.INITIALIZE) {
            setSpawnPoints();
            setBasePoints();
        }// end if

        isSpawnPoint();
        addImmediateObstacles(inEnvironment);
        checkBase();

        if (pawn.hasFlag) 
            pawn.setNewBase(objective.FIND_OUR_BASE);
        else
            pawn.setNewBase(objective.FIND_ENEMY_BASE);

        // shortest path found, get on shortest path and follow it
        if(locatedEnemyBase && locatedTeamBase){

            shortestPathList = shortestPath(getLandmarkName("teamBase").location, getLandmarkName("enemyBase").location);

            if(shortestPathList.size() != 0){
                findPath = new Landmark("findPath");
                findPath.location = getSmallestPosition(pawn.location, shortestPathList);
                findPath.assignedDirection = getDirectionGivenPosition(findPath.location);
            }// end if
            if(pawn.hasFlag && shortestPathList.size() != 0){
                if(isOnShortestPath()){
                    pawn.objList.clear();
                    pawn.addObjective(objective.FIND_OUR_BASE_GIVEN_PATH);
                }// end inner if
                else
                    pawn.setNewBase(objective.FIND_OUR_BASE_GIVEN_PATH);
            }
            else if(!pawn.hasFlag && shortestPathList.size() != 0){
                if(isOnShortestPath()){
                    pawn.objList.clear();
                    pawn.addObjective(objective.FIND_ENEMY_BASE_GIVEN_PATH);
                }// end inner if
                else
                    pawn.setNewBase(objective.FIND_ENEMY_BASE_GIVEN_PATH);
            }// end else if
        }// end if

        switch (pawn.getObjective()) {
            case FIND_ENEMY_BASE:
                task = getNextTask(getLandmarkName("enemyBase"));
                break;
            case FIND_ENEMY_BASE_GIVEN_PATH:
                if(!isOnShortestPath())
                    task = getNextTask(findPath);
                else{
                    if(pawn.location.isSamePositions(enemyBase.location))
                        return doNothing; //we are already at the enemy base
                    int index = getPointFromList(shortestPathList, pawn.location);
                    Position whereToGo = shortestPathList.get(index - 1);
                    direction directionOfPointRelativeTome = getDirectionGivenPosition(whereToGo);
                    if(checkValidMove(directionOfPointRelativeTome))
                        task = moveInThatDirection(getDirectionGivenPosition(whereToGo));
                    else
                        task = getNextTask(getLandmarkName("enemyBase"));
                }// end else
                break;
            case FIND_OUR_BASE:
                task = getNextTask(getLandmarkName("teamBase"));
                break;
            case FIND_OUR_BASE_GIVEN_PATH:
                if(!isOnShortestPath())
                    task = getNextTask(findPath);
                else{
                    if(pawn.location.isSamePositions(teamBase.location)){
                        return doNothing;//we are already at our base
                    }
                    int index = getPointFromList(shortestPathList, pawn.location);
                    Position whereToGo = shortestPathList.get(index + 1);
                    direction directionOfPointRelativeTome = getDirectionGivenPosition(whereToGo);
                    if(checkValidMove(directionOfPointRelativeTome))
                        task = moveInThatDirection(getDirectionGivenPosition(whereToGo));
                    else
                        task = getNextTask(getLandmarkName("teamBase")); 
                }// end else
                break;
           // case DEFEND_OUR_BASE:
             //   task = defend(teamBase);
               // break;
            case MOVE_PAST_OBST:
                if(pawn.getBaseObjective() == objective.FIND_OUR_BASE || pawn.getBaseObjective() == objective.FIND_OUR_BASE_GIVEN_PATH){
                    task = movePastObstacles(teamBase);
                }else if(pawn.getBaseObjective() == objective.FIND_ENEMY_BASE || pawn.getBaseObjective() == objective.FIND_ENEMY_BASE_GIVEN_PATH){
                    task = movePastObstacles(enemyBase);
                }
            default:
        }// end switch
        return task;
    }// end getMove

    public int getNextTask(Landmark place) {

        int task = doNothing;

        // check if blocked by obstacles for 3 directions
        if (threeObstacles()) {
            task = moveInThatDirection(findOpenDir());
            return task;
        }// end if
        
      //check if simply blocked in 3 directions
        if(isBlockedThreeWays()){
            task = moveInThatDirection(getNotBlockedDirection());
            return task;
        }// end if

        direction nextDirection = place.assignedDirection;

        switch (nextDirection) {
            case NORTH:
                if (isNorth()) {
                    task = moveNorth();
                }// end if
                else{
                    pawn.addObjective(objective.MOVE_PAST_OBST);
                    task = movePastObstacles(place);
                }// end else
                break;
            case NORTHEAST:
                if(isNorth() && isEast())              
                    task = getRandomDirectionAndMove(direction.NORTH, direction.EAST);
                else {
                    pawn.addObjective(objective.MOVE_PAST_OBST);
                    task = movePastObstacles(place);
                }// end else
                break;
            case NORTHWEST:
                if(isNorth() && isWest())
                    task = getRandomDirectionAndMove(direction.NORTH, direction.WEST);
                else{
                    pawn.addObjective(objective.MOVE_PAST_OBST);
                    task = movePastObstacles(place);
                }// end else
                break;
            case SOUTH:
                if(isSouth())
                    task = moveSouth();
                else{
                    pawn.addObjective(objective.MOVE_PAST_OBST);
                    task = movePastObstacles(place);
                }// end else
                break;
            case SOUTHEAST:
                if(isSouth() && isEast())
                    task = getRandomDirectionAndMove(direction.SOUTH, direction.EAST);
                else{
                    pawn.addObjective(objective.MOVE_PAST_OBST);
                    task = movePastObstacles(place);
                }// end else
                break;
            case SOUTHWEST:
                if(isSouth() && isWest())
                    task = getRandomDirectionAndMove(direction.SOUTH, direction.WEST);
                else{
                    pawn.addObjective(objective.MOVE_PAST_OBST);
                    task = movePastObstacles(place);
                } // end else
                break;
            case EAST:
                if(isEast())
                    task = moveEast();
                else{
                    pawn.addObjective(objective.MOVE_PAST_OBST);
                    task = movePastObstacles(place);
                }// end else
                break;
            case WEST:
                if(isWest())
                    task = moveWest();
                else{
                    pawn.addObjective(objective.MOVE_PAST_OBST);
                    task = movePastObstacles(place);
                }// end else
                break;
            default:
            	break;
        }// end switch
        
        return task;
    }// getNextTask

    public Landmark getLandmarkName(String name){
        for(int i = 0; i < goalLandmarkList.size(); i++){
            Landmark current = goalLandmarkList.get(i);
            if(current.name == name)
                return current;
        }// end for
        return new Landmark("None were found.");
    }// end getLandmarkName

    public boolean isOnShortestPath(){
        return (listContains(shortestPathList, pawn.location));
    }// end isOnShortestPath

    public void checkBase(){
        if(teamBaseNorthImmediate || teamBaseEastImmediate || teamBaseSouthImmediate || teamBaseWestImmediate)
            locatedTeamBase = true;
        if(enemyBaseNorthImmediate || enemyBaseEastImmediate || enemyBaseSouthImmediate || enemyBaseWestImmediate)
            locatedEnemyBase = true;
    }// end checkBase

    public void avoidObstacles(Landmark landGoal){

        pawn.obstList = new ArrayList<obstacle>(getImmediateObstacles());

        // Check to see if pawns are in a tunnel
        if(!isEast() && !isWest()){
            direction notWestAndNotEast;

            notWestAndNotEast = getRandomDirection(direction.SOUTH, direction.NORTH);

            if(landGoal.assignedDirection == direction.NORTHEAST) {
                pawn.addDirection(direction.NORTH);
                return;
            }// end if

            if(landGoal.assignedDirection == direction.SOUTHEAST){
                pawn.addDirection(direction.SOUTH);
                return;
            }// end if

            if(landGoal.assignedDirection == direction.EAST){
                pawn.addDirection(notWestAndNotEast);
                return;
            }// end if

            if(landGoal.assignedDirection == direction.WEST){
                pawn.addDirection(notWestAndNotEast);
                return;
            }// end if

            if(landGoal.assignedDirection == direction.NORTHWEST){
                pawn.addDirection(direction.NORTH);
                return;
            }// end if

            if(landGoal.assignedDirection == direction.SOUTHWEST){
                pawn.addDirection(direction.SOUTH);
                return;
            }// end if

            if(landGoal.assignedDirection == direction.SOUTH){
                pawn.addDirection(direction.SOUTH);
                return;
            }// end if

            if(landGoal.assignedDirection == direction.NORTH){
                pawn.addDirection(direction.NORTH);
                return;
            }// end if
        }// end outer if

        if(!isNorth() && !isSouth()){

            direction notNorthAndNotSouth;
            notNorthAndNotSouth = getRandomDirection(direction.EAST, direction.WEST);
            
            if(landGoal.assignedDirection == direction.NORTHEAST) {
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTHEAST){
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.EAST){
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.WEST){
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.NORTHWEST){
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTHWEST){
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTH){
                pawn.addDirection(notNorthAndNotSouth);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.NORTH){
                pawn.addDirection(notNorthAndNotSouth);
                return;
            }// end inner if
        }// end outer if


        // pawn is blocked by a southeast corner
        if(!isEast() && !isNorth()){
            direction notNorthAndNotEast;
            notNorthAndNotEast = getRandomDirection(direction.SOUTH, direction.WEST);
            
            if(landGoal.assignedDirection == direction.NORTHEAST) {
                pawn.addDirection(notNorthAndNotEast);
                return;
            }// end inner if
            if(landGoal.assignedDirection == direction.SOUTHEAST){
                pawn.addDirection(direction.SOUTH);
                return;
            }// end inner if
            if(landGoal.assignedDirection == direction.EAST){
                pawn.addDirection(notNorthAndNotEast);
                return;
            }// end inner if
            if(landGoal.assignedDirection == direction.WEST){
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if
            if(landGoal.assignedDirection == direction.NORTHWEST){
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if
            if(landGoal.assignedDirection == direction.SOUTHWEST){
                pawn.addDirection(notNorthAndNotEast);
                return;
            }// end inner if
            if(landGoal.assignedDirection == direction.SOUTH){
                pawn.addDirection(direction.SOUTH);
                return;
            }// end inner if
            if(landGoal.assignedDirection == direction.NORTH){
                pawn.addDirection(notNorthAndNotEast);
                return;
            }// end inner if
        }// end outer if

        // pawn is blocked by a southwest corner
        if(!isEast() && !isSouth()) {
            direction notSouthAndNotEast;
            notSouthAndNotEast = getRandomDirection(direction.NORTH, direction.WEST);

            if (landGoal.assignedDirection == direction.NORTHEAST) {
                pawn.addDirection(notSouthAndNotEast);
                return;
            } // end inner if

            if (landGoal.assignedDirection == direction.SOUTHEAST) {
                pawn.addDirection(notSouthAndNotEast);
                return;
            }// end inner if

            if (landGoal.assignedDirection == direction.EAST) {
                pawn.addDirection(notSouthAndNotEast);
                return;
            }// end inner if

            if (landGoal.assignedDirection == direction.WEST) {
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if

            if (landGoal.assignedDirection == direction.NORTHWEST) {
                pawn.addDirection(notSouthAndNotEast);
                return;
            }// end inner if

            if (landGoal.assignedDirection == direction.SOUTHWEST) {
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if

            if (landGoal.assignedDirection == direction.NORTH) {
                pawn.addDirection(direction.NORTH);
                return;
            }// end inner if

            if (landGoal.assignedDirection == direction.SOUTH) {
                pawn.addDirection(notSouthAndNotEast);
                return;
            }// end inner if
        }// end outer if

     // pawn is blocked by a northwest corner
        if(!isWest() && !isSouth()){
            direction notSouthAndNotWest;

            notSouthAndNotWest = getRandomDirection(direction.NORTH, direction.EAST);

            if(landGoal.assignedDirection == direction.NORTHEAST) {
                pawn.addDirection(notSouthAndNotWest);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTHEAST){
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.EAST){
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.WEST){
                pawn.addDirection(notSouthAndNotWest);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.NORTHWEST){
                pawn.addDirection(direction.NORTH);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTHWEST){
                pawn.addDirection(notSouthAndNotWest);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.NORTH){
                pawn.addDirection(direction.NORTH);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTH){
                pawn.addDirection(notSouthAndNotWest);
                return;
            }// end inner if
        }// end outer if

     // pawn is blocked by a northwest corner
        if(!isWest() && !isNorth()){
            direction notNorthAndNotWest;
            notNorthAndNotWest = getRandomDirection(direction.SOUTH, direction.EAST);

            if(landGoal.assignedDirection == direction.NORTHEAST) {
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTHEAST){
                pawn.addDirection(notNorthAndNotWest);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.EAST){
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.WEST){
                pawn.addDirection(notNorthAndNotWest);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.NORTHWEST){
                pawn.addDirection(notNorthAndNotWest);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTHWEST){
                pawn.addDirection(direction.SOUTH);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.SOUTH){
                pawn.addDirection(direction.SOUTH);
                return;
            }// end inner if

            if(landGoal.assignedDirection == direction.NORTH){
                pawn.addDirection(notNorthAndNotWest);
                return;
            }// end inner if
        }// end outer if

        if(!isEast() && (landGoal.assignedDirection == direction.SOUTH || landGoal.assignedDirection == direction.SOUTHEAST)){
            pawn.addDirection(direction.SOUTH);
            return;
        }// end if

     // obstacle in the east and current objective is north or northeast
        if(!isEast() && (landGoal.assignedDirection == direction.NORTH || landGoal.assignedDirection == direction.NORTHEAST)){
            pawn.addDirection(direction.NORTH);
            return;
        }// end if

     // obstacle in the east and current objective is east or west
        if(!isEast() && (landGoal.assignedDirection == direction.EAST || landGoal.assignedDirection == direction.WEST || landGoal.assignedDirection == direction.SOUTHWEST || landGoal.assignedDirection == direction.NORTHWEST)){

            direction notEast;
            if(pawn.hasFlag)
                notEast = getRandomDirection(direction.SOUTH, direction.NORTH, direction.WEST);
            else // prioritize less visited spaces
                notEast = getLowestVisitedDirection(direction.SOUTH, direction.NORTH, direction.WEST);//if I don't have the flag, prefer less visited squares

            // keep moving north
            if(notEast == direction.NORTH){
                pawn.addDirection(direction.NORTH);
                return;
            }// end inner if

            // keep moving south
            if(notEast == direction.SOUTH) {
                pawn.addDirection(direction.SOUTH);
                return;
            }// end inner if

         // keep moving west
            if(notEast == direction.WEST) {
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if
        }// end outer if

        if(!isWest() && (landGoal.assignedDirection == direction.SOUTH || landGoal.assignedDirection == direction.SOUTHWEST)){
            pawn.addDirection(direction.SOUTH);
            return;
        }// end if

        // obstacle in the west and currect objective is north or northwest    
        if(!isWest() && (landGoal.assignedDirection == direction.NORTH || landGoal.assignedDirection == direction.NORTHWEST)){
            pawn.addDirection(direction.NORTH);
            return;
        }// end if

        
        // obstacle in the west and currect objective is west or east
        if(!isWest() && (landGoal.assignedDirection == direction.WEST || landGoal.assignedDirection == direction.EAST || landGoal.assignedDirection == direction.SOUTHEAST || landGoal.assignedDirection == direction.NORTHEAST)){

            direction notWest;
            if(pawn.hasFlag) // prioritize commonly visited spaces
                notWest = getRandomDirection(direction.SOUTH, direction.NORTH, direction.EAST);
            else // prioritize less visited spaces 
                notWest = getLowestVisitedDirection(direction.SOUTH, direction.NORTH, direction.EAST); //if I don't have the flag, prefer less visited squares
            
            //keep moving north
            if(notWest == direction.NORTH) {
                pawn.addDirection(direction.NORTH);
                return;
            }// end inner if

         // keep moving south
            if(notWest == direction.SOUTH) {
                System.out.println("agent " + pawn.name + pawn.idNum + " decided to go " + getDirectionName(direction.SOUTH) + " around west wall");
                pawn.addDirection(direction.SOUTH);
                return;
            }// end inner if

         // keep moving east
            if(notWest == direction.EAST) {
                System.out.println("agent " + pawn.name + pawn.idNum + " decided to go " + getDirectionName(direction.EAST) + " around west wall");
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if
        }// end outer if


        if(!isNorth() && (landGoal.assignedDirection == direction.EAST || landGoal.assignedDirection == direction.NORTHEAST)){
            pawn.addDirection(direction.EAST);
            return;
        }// end if

        //if obstNorth and objective is west or northwest, keep going west until obstWest or !obstNorth
        if(!isNorth() && (landGoal.assignedDirection == direction.WEST || landGoal.assignedDirection == direction.NORTHWEST)){
            pawn.addDirection(direction.WEST);
            return;
        }// end if

        // obstacle in the east and currect objective is west or northwest
        if(!isNorth() && (landGoal.assignedDirection == direction.NORTH || landGoal.assignedDirection == direction.SOUTH || landGoal.assignedDirection == direction.SOUTHWEST || landGoal.assignedDirection == direction.SOUTHEAST)){

            direction notNorth;
            if(pawn.hasFlag) // prioritize commonly visited spaces
                notNorth = getRandomDirection(direction.EAST, direction.WEST, direction.SOUTH);
            else // prioritize less visited spaces
                notNorth = getLowestVisitedDirection(direction.EAST, direction.WEST, direction.SOUTH);	//if I don't have the flag, prefer less visited squares
            
            // keep moving east
            if(notNorth == direction.EAST) {
                pawn.addDirection(direction.EAST);
                return;
            } // end inner if

            // keep moving west
            if(notNorth == direction.WEST) {
                pawn.addDirection(direction.WEST);
                return;
            } // end inner if

            // keep moving south
            if(notNorth == direction.SOUTH) {
                pawn.addDirection(direction.SOUTH);
                return;
            } // end inner if
        } // end outer if

        // obstacle in the south and currect objective is east or southeast
        if(!isSouth() && (landGoal.assignedDirection == direction.EAST || landGoal.assignedDirection == direction.SOUTHEAST)){
            pawn.addDirection(direction.EAST);
            return;
        }// end if

        // obstacle in the south and currect objective is west or southwest
        if(!isSouth() && (landGoal.assignedDirection == direction.WEST || landGoal.assignedDirection == direction.SOUTHWEST)){
            pawn.addDirection(direction.WEST);
            return;
        }// end if

        // obstacle in the south and currect objective is northeast or northwest
        if(!isSouth() && (landGoal.assignedDirection == direction.SOUTH || landGoal.assignedDirection == direction.NORTH || landGoal.assignedDirection == direction.NORTHEAST || landGoal.assignedDirection == direction.NORTHWEST)){
            direction notSouth;
            if(pawn.hasFlag)
                notSouth = getRandomDirection(direction.EAST, direction.WEST, direction.NORTH);
            else
                notSouth = getLowestVisitedDirection(direction.EAST, direction.WEST, direction.NORTH); //if I don't have the flag, prefer less visited squares
            

            // keep moving east
            if(notSouth == direction.EAST) {
                pawn.addDirection(direction.EAST);
                return;
            }// end inner if

            // keep moving west
            if(notSouth == direction.WEST) {
                pawn.addDirection(direction.WEST);
                return;
            }// end inner if

            // keep moving north
            if(notSouth == direction.NORTH) {
                pawn.addDirection(direction.NORTH);
                return;
            }// end inner if
        }// end outer if
    }// end avoidObstacles

    public ArrayList<obstacle> getImmediateObstacles(){
        ArrayList<obstacle> obstList = new ArrayList<obstacle>();
        if(!checkValidMove(direction.NORTH))
            obstList.add(obstacle.N_WALL);
        if(!checkValidMove(direction.EAST))
            obstList.add(obstacle.E_WALL);
        if (!checkValidMove(direction.SOUTH))
            obstList.add(obstacle.S_WALL);
        if(!checkValidMove(direction.WEST)) 
            obstList.add(obstacle.W_WALL);
        return obstList;
    }// end getImmediateObstacles

    public int movePastObstacles(Landmark landGoal){

        if(isSpawnPoint()){
            if(pawn.directionList.size() != 0)
                pawn.directionList.clear();
            pawn.removeObjective();
            return getNextTask(landGoal);
        }// end if

     // move in the only unblocked direction
        if(isBlockedThreeWays()){
            pawn.removeObjective();
            pawn.removeDirection();
            return moveInThatDirection(getNotBlockedDirection());
        }// end if

        // first time at specific obstacle
        if(pawn.directionList.size() == 0)
            avoidObstacles(landGoal);
        
        
        direction currentDir = pawn.getDirection();

        if(checkForObstacles())
            removeObstacles();
        
        // no obstacles, call getNextTask
        if(pawn.obstList.size() == 0){
            pawn.removeDirection();
            pawn.removeObjective();
            return getNextTask(landGoal);
        }// end if
        if(checkValidMove(currentDir))
            return moveInThatDirection(currentDir);
        else{
            pawn.removeDirection();
            pawn.removeObjective();
            return getNextTask(landGoal);
        } // end else
    }// end movePastObstacles

    public boolean checkForObstacles(){
        for(int i = 0; i < pawn.obstList.size(); i++){
            if(checkValidMove(getDirectionGivenObstacle(pawn.obstList.get(i))))
                return true;
        }// end for
        return false;
    }// end checkForObstacles

    public void removeObstacles(){
        ArrayList<Integer> obstListToRemove = new ArrayList<Integer>();
        for(int i = 0; i < pawn.obstList.size(); i++){
            if(checkValidMove(getDirectionGivenObstacle(pawn.obstList.get(i))))
                obstListToRemove.add(i); 
        }// end for
        for(int j = 0; j < obstListToRemove.size(); j++)
            pawn.obstList.remove(obstListToRemove.get(j));
    }// end for

    //********************** MAP *********************************/
    public ArrayList<direction> getLowestVistiedImmediateCells(){
        ArrayList<direction> visitedList = new ArrayList<direction>();		// Keeps track of the least visited 

        int minNumberOfMoves = 200;
        int visitedSumNorth = (isNorth())? (getVisitedSum(pawn.location.moveNorthOne())) : (200);
        int visitedSumEast = (isEast()) ? (getVisitedSum(pawn.location.moveEastOne())) : (200);
        int visitedSumSouth = (isSouth()) ? (getVisitedSum(pawn.location.moveSouthOne())) : (200);
        int visitedSumWest = (isWest()) ? (getVisitedSum(pawn.location.moveWestOne())) : (200);

        if(visitedSumNorth < minNumberOfMoves)
            minNumberOfMoves = visitedSumNorth;
        if(visitedSumEast < minNumberOfMoves)
            minNumberOfMoves = visitedSumEast;
        if(visitedSumSouth < minNumberOfMoves)
            minNumberOfMoves = visitedSumSouth;
        if(visitedSumWest < minNumberOfMoves)
            minNumberOfMoves = visitedSumWest;      
        if (visitedSumNorth == minNumberOfMoves) 
            visitedList.add(direction.NORTH);     
        if(visitedSumEast == minNumberOfMoves) 
            visitedList.add(direction.EAST);  
        if(visitedSumSouth == minNumberOfMoves)
            visitedList.add(direction.SOUTH);  
        if(visitedSumWest == minNumberOfMoves)
            visitedList.add(direction.WEST);     
        return visitedList;
    }// end getLowestImmediateVisited

    public direction getLowestVisitedDirection(direction aDir, direction bDir){
        int minNumberOfMoves = 200;

        int a = (checkValidMove(aDir))?(getVisitedSum(pawn.moveIfValid(aDir))):(200);
        int b = (checkValidMove(bDir))?(getVisitedSum(pawn.moveIfValid(bDir))):(200);

        if(a < minNumberOfMoves)
            minNumberOfMoves = a;
        if(b < minNumberOfMoves)
            minNumberOfMoves = b;
        if(a == minNumberOfMoves)
            return aDir;
        else
            return bDir;
    }// end getLowestVisitedDirection(2)

    // Overloaded method
    public direction getLowestVisitedDirection(direction aDir, direction bDir, direction cDir){
        int minNumberOfMoves = 200;

        int a = (checkValidMove(aDir))?(getVisitedSum(pawn.moveIfValid(aDir))):(200);
        int b = (checkValidMove(bDir))?(getVisitedSum(pawn.moveIfValid(bDir))):(200);
        int c = (checkValidMove(cDir))?(getVisitedSum(pawn.moveIfValid(cDir))):(200);

        if(a < minNumberOfMoves)
            minNumberOfMoves = a;
        if(b < minNumberOfMoves)
            minNumberOfMoves = b;
        if(c < minNumberOfMoves)
            minNumberOfMoves = c;
        
        if(a == minNumberOfMoves)
            return aDir;   
        else if(b == minNumberOfMoves)
            return bDir;     
        else
            return cDir;
        
    }// end getLowestVisitedDirection(3)

    public int getVisitedSum(Position pos){
        return sumVisitedMap[pos.x][pos.y];
    }// end getVisitedSum

    public int checkEnemyForFlag(){
        int task = doNothing;
        if(enemyNorthImmediate && enemyHasFlag)
            task = moveNorth;
        else if(enemyEastImmediate && enemyHasFlag)
            task = moveEast;
        
        else if(enemySouthImmediate && enemyHasFlag)
            task = moveSouth;
        else if(enemyWestImmediate && enemyHasFlag) 
            task = moveWest;
        
        return task;
    }// end checkEnemyForFlag

    public int moveRandomly(){
        ArrayList<direction> openDir = addOpenDirections();
        direction assignedDirection = direction.DONOTHING;

        int x = getRandomNumber(1, openDir.size());
        for(int i = 0; i < openDir.size(); i++){
            if(x == i){
                assignedDirection = openDir.get(i);
                break;
            }// end if
        }// end for
        return moveInThatDirection(assignedDirection);
    }// end moveRandomly

    public int moveRandomOpenDirection(direction a, direction b){
        direction assignedDirection = getRandomDirection(a, b);

        if(checkValidMove(assignedDirection))
            return moveInThatDirection(assignedDirection);
        else if(assignedDirection == a && checkValidMove(b))
            return moveInThatDirection(b);
        else
            return doNothing; 
    }// end moveRandomOpenDirection(2)

    public int moveRandomOpenDirection(direction a, direction b, direction c){
        boolean aBad = false;
        boolean bBad = false;
        boolean cBad = false;

        while(true){
            direction assignedDirection = getRandomDirection(a, b, c);
            if(checkValidMove(assignedDirection))
                return moveInThatDirection(assignedDirection);
            
            if(assignedDirection == a)
                aBad = true;
            if(assignedDirection == b)
                bBad = true; 
            if(assignedDirection == c)
                cBad = true;
            if(aBad && bBad && cBad)
                return doNothing;
        }// end while
    }// end moveRandomOpenDirection (3)

    public int moveRandomOpenDirection(direction a, direction b, direction c, direction d){

        boolean aBad = false;
        boolean bBad = false;
        boolean cBad = false;
        boolean dBad = false;

        while(true){
            direction assignedDirection = getRandomDirection(a, b, c, d);
            if(checkValidMove(assignedDirection))
                return moveInThatDirection(assignedDirection);          
            if(assignedDirection == a)
                aBad = true;      
            if(assignedDirection == b)
                bBad = true;
            if(assignedDirection == c)
                cBad = true;   
            if(assignedDirection == d)
                dBad = true;   
            if(aBad && bBad && cBad && dBad)
                return doNothing;
        }// end while
    }// end moveRandomOpenDirection(4)

    public direction getRandomDirection(direction a, direction b){
        int x = getRandomNumber(1, 2);
        return (x == 1) ? (a) : (b);
    }// end getRandomDirection(2)

    public direction getRandomDirection(direction a, direction b, direction c){
        direction assignedDirection = direction.DONOTHING;
        int x = getRandomNumber(1, 3);
        
        switch(x){
            case 1:
                return a;
            case 2:
                return b;
            case 3:
                return c;
        }// end switch
        return assignedDirection;
    }// getRandomDirection (3)

    public direction getRandomDirection(direction a, direction b, direction c, direction d) {
        direction assignedDirection = direction.DONOTHING;

        int randomNum = getRandomNumber(1, 4);

        switch(randomNum){
            case 1:
                return a;
            case 2:
                return b;
            case 3:
                return c;
            case 4:
                return d;
        }
        return assignedDirection;
    }// getRandomDirection(4)

    public int getRandomDirectionAndMove(direction a, direction b, direction c, direction d) {
        direction assignedDirection = getRandomDirection(a, b, c, d);
        return moveInThatDirection(assignedDirection);
    }// end getRandomDirectionAndMove(4)

    public int getRandomDirectionAndMove(direction a, direction b){
        direction assignedDirection = getRandomDirection(a, b);
        return moveInThatDirection(assignedDirection);
    }// getRandomDirectionAndMove(2)

    public int getRandomNumber(int min, int max){
        numRandom = new Random();
        return numRandom.nextInt((max - min) + 1) + min;
    }// end getRandomNumber

    public direction getRandomDirection(ArrayList<direction> directions){
    	
        direction assignedDirection = direction.DONOTHING;
        int x = getRandomNumber(1, directions.size());
        
        for(int i = 0; i < directions.size(); i++){
            if(x == i){
                assignedDirection = directions.get(i);
                break;
            }// end if
        }// end for
        return assignedDirection;
    }// end getRandomDirection

    public int getRandomDirectionAndMove(ArrayList<direction> directions){
        return moveInThatDirection(getRandomDirection(directions));
    }// getRandomDirection

    public int moveLeastVisitedCell(){
        int task = getRandomDirectionAndMove(getLowestVistiedImmediateCells());
        return task;
    }// end moveLeastVistedCell

    public direction getRandomVisitedCell(){
        return getRandomDirection(getLowestVistiedImmediateCells());
    } //getRandomVisitedCell

    public ArrayList<direction> addOpenDirections(){
        ArrayList<direction> openDir = new ArrayList<direction>();
        if(isNorth())
            openDir.add(direction.NORTH);
        if(isEast()) 
            openDir.add(direction.EAST);  
        if(isSouth()) 
            openDir.add(direction.SOUTH); 
        if(isWest())
            openDir.add(direction.WEST);  
        return openDir;
    }// end addOpenDirection

    public int getEndMove(){
        return pawn.movesList.get(pawn.movesList.size() - 2);
    }// end getEndMove


    public int moveNorth(){
        Position newPosition = new Position(pawn.location.x, pawn.location.y - 1);
        updatePosition(pawn.location, newPosition);
        pawn.movesList.add(moveNorth);
        return moveNorth;
    }// end moveNorth

    public int moveEast(){
        Position newPosition = new Position(pawn.location.x + 1, pawn.location.y);
        updatePosition(pawn.location, newPosition);
        pawn.movesList.add(moveEast);
        return moveEast;
    }// end moveEast

    public int moveSouth(){
        Position newPosition = new Position(pawn.location.x, pawn.location.y + 1);
        updatePosition(pawn.location, newPosition);
        pawn.movesList.add(moveSouth);
        return moveSouth;
    }// end moveSouth

    public int moveWest(){
        Position newPosition = new Position(pawn.location.x - 1, pawn.location.y);
        updatePosition(pawn.location, newPosition);
        pawn.movesList.add(moveWest);
        return moveWest;
    }// moveWest

    public boolean checkValidMove(ArrayList<direction> directions){
        boolean checkAllDirections = true;
        for(int i = 0; i < directions.size(); i++){
            if(!checkValidMove(directions.get(i))){
                checkAllDirections = false;
                break;
            }// end if
        }// end for
        return checkAllDirections;
    }// end checkValidMove

    public boolean checkValidMove(direction assignedDirection){
        switch(assignedDirection){
            case NORTH:
                return isNorth();
            case EAST:
                return isEast();
            case SOUTH:
                return isSouth();
            case WEST:
                return isWest();
            default:
            	break;
        }// end switch
        return false;
    }// end checkValidMove

    int moveInThatDirection(direction assignedDirection){
        switch(assignedDirection){
            case NORTH:
                return moveNorth();
            case EAST:
                return moveEast();
            case SOUTH:
                return moveSouth();
            case WEST:
                return moveWest();
            default:
            	break;
        }// end switch
        return doNothing;
    }// end moveInThatDirection

    public void addImmediateObstacles(AgentEnvironment inEnvironment) {

    	obstacleNorthEastSouth = (obstacleNorth && obstacleEast && obstacleSouth);
        obstacleEastSouthWest = (obstacleEast && obstacleSouth && obstacleWest);
        obstacleSouthWestNorth = (obstacleSouth && obstacleWest && obstacleNorth);
        obstacleWestNorthEast = (obstacleWest && obstacleNorth && obstacleEast);
        obstacleEveryDirection = (obstacleWest && obstacleNorth && obstacleEast && obstacleSouth);
        
        if(pawn.location.y == 0)
            obstacleNorth = inEnvironment.isObstacleNorthImmediate();
        else
            obstacleNorth = (inEnvironment.isObstacleNorthImmediate() || (gameMap[pawn.location.y - 1][pawn.location.x] == 'X'));
        
        if(pawn.location.y == mapSize - 1)
            obstacleSouth = inEnvironment.isObstacleSouthImmediate();
        else
            obstacleSouth = (inEnvironment.isObstacleSouthImmediate() || (gameMap[pawn.location.y + 1][pawn.location.x] == 'X'));

        if(pawn.location.x == mapSize -1)
            obstacleEast  = inEnvironment.isObstacleEastImmediate();
        else
            obstacleEast  = (inEnvironment.isObstacleEastImmediate() || (gameMap[pawn.location.y][pawn.location.x + 1] == 'X'));

        if(pawn.location.x == 0)
            obstacleWest  = inEnvironment.isObstacleWestImmediate();
        else
            obstacleWest  = (inEnvironment.isObstacleWestImmediate() || (gameMap[pawn.location.y][pawn.location.x - 1] == 'X'));

        if (obstacleNorth && pawn.location.y != 0) {
            gameMap[pawn.location.y - 1][pawn.location.x] = 'X';
            addBadCell(pawn.location.moveNorthOne());
        }// end if
        if (obstacleEast && pawn.location.x != mapSize - 1) {
            gameMap[pawn.location.y][pawn.location.x + 1] = 'X';
            addBadCell(pawn.location.moveEastOne());
        }// end if
        if (obstacleSouth && pawn.location.y != mapSize - 1) {
            gameMap[pawn.location.y + 1][pawn.location.x] = 'X';
            addBadCell(pawn.location.moveSouthOne());
        }// end if
        if (obstacleWest && pawn.location.x != 0) {
            gameMap[pawn.location.y][pawn.location.x - 1] = 'X';
            addBadCell(pawn.location.moveWestOne());
        }// end if
    }// end addImmediateObject

    public void setSpawnPoints() {

        if (enemyBaseEast && teamBaseSouth) 
            pawn.spawnPoint.setXY(0, 0);
        else if (enemyBaseEast && teamBaseNorth) 
            pawn.spawnPoint.setXY(0, mapSize - 1);
        else if (enemyBaseWest && teamBaseNorth) 
            pawn.spawnPoint.setXY(mapSize - 1, mapSize - 1);
        else if (enemyBaseWest && teamBaseSouth) 
            pawn.spawnPoint.setXY(mapSize - 1, 0);
        
        pawn.location.setNewPosition(pawn.spawnPoint);
    }// end setSpawnPoints

    public Boolean isSpawnPoint() {

        if ((teamBaseWest == false) && (teamBaseEast == false)) {
            if ((teamBaseSouth == true) && (obstacleNorth == true)) {
                if ((enemyBaseWest == true) && (pawn.spawnPoint.x == mapSize - 1) && (pawn.spawnPoint.y == 0)) {
                    
                    updatePosition(pawn.location, pawn.spawnPoint);
                    makeVisitedMap();
                    return true;
                }// end inner if
                if ((enemyBaseWest == false) && (pawn.spawnPoint.x == 0) && (pawn.spawnPoint.y == 0)) {
                    updatePosition(pawn.location, pawn.spawnPoint);
                    makeVisitedMap();
                    return true;
                }// end inner if
            }// end mid if 
            else if ((teamBaseNorth == true) && (obstacleSouth == true)) {
                if ((enemyBaseWest == true) && (pawn.spawnPoint.x == mapSize - 1) && (pawn.spawnPoint.y == mapSize - 1)) {  
                    updatePosition(pawn.location, pawn.spawnPoint);
                    makeVisitedMap();
                    return true;
                }// end if
                if ((enemyBaseWest == false) && (pawn.spawnPoint.x == 0) && (pawn.spawnPoint.y == mapSize - 1)) {
                    updatePosition(pawn.location, pawn.spawnPoint);
                    makeVisitedMap();
                    return true;
                }// end if
            }//end else if 
            else 
                return false;
        }// end outer if
        else 
            return false;
        return false;
    }// end isSpawnPoint

    public void setBasePoints() {
        if (enemyBaseWest) {
            teamBase.location.setXY(9, 5);
            enemyBase.location.setXY(0, 5);
        } // end if 
        else if (enemyBaseEast) {
            teamBase.location.setXY(0, 5);
            enemyBase.location.setXY(9, 5);
        }// end else if
    } // setBasePoints

    public void setMapCell(Position pos, char c) {
        gameMap[pos.y][pos.x] = c;
    }// end setMapCell

    public void movePawnOnMap(Position source, Position target) {
        if(threeObstacles()){
            setMapCell(source, 'X');
            addBadCell(source);
        } // end if
        else {
            setMapCell(source, ' ');
            addGoodCell(target);
        }// end else
        setMapCell(target, pawn.name);
    }// end setMapCell

    public void updateVisitedMap(Position pos){
        sumVisitedMap[pos.y][pos.x]++;
    }

    public void updatePosition(Position source, Position target) {
        pawn.visitedCellList.add(new Position(target));
        updateVisitedMap(target);
        movePawnOnMap(source, target);
        pawn.location.setNewPosition(target);
    }

    public void createMap() {
        gameMap = new char[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                gameMap[i][j] = '.';
            }// end inner loop
        }// end outer loop
    }// end createMap

    public void createAstarMap() {
        aSSearchmap = new char[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if(listContains(aSSearch.goodList, new Position(j, i)))
                    aSSearchmap[i][j] = ' ';
                else
                    aSSearchmap[i][j] = '.';
            }// end inner for
        }// end outer for
    }// end createAstarMap

    public void makeVisitedMap(){
        sumVisitedMap = new int[mapSize][mapSize];
        for(int i = 0; i < mapSize; i++){
            for(int j = 0; j < mapSize; j++){
                sumVisitedMap[i][j] = 0;
            }// end for
        }// end for
    }// end makeVisitedMap

    public boolean listContains(ArrayList<Position> list, Position coordinate){
        for(int i = 0; i < list.size(); i++){
            if(coordinate.isSamePositions(list.get(i))){
                return true;
            }// end if
        }// end for
        return false;
    }// end listContains

    public int getPointFromList(ArrayList<Position> list, Position point){
        for(int i = 0; i < list.size(); i++){
            if(point.isSamePositions(list.get(i))){
                return i;
            }// end if
        }// end for
        return -1;
    }// end getPointFromList

    public boolean isNorth() {
        Position moveNorthOne = new Position(pawn.location.x, pawn.location.y - 1);
        boolean isMine = minesList.contains(moveNorthOne);
        return ((pawn.hasFlag && enemyNorthImmediate) || teammateNorthImmediate || obstacleNorth || (teamBaseNorthImmediate && !pawn.hasFlag) || isMine) ? (false) : (true);
    }// end isNorth

    public boolean isEast() {
        Position moveEastOne = new Position(pawn.location.x + 1, pawn.location.y);
        boolean isMine = minesList.contains(moveEastOne);
        return ((pawn.hasFlag && enemyEastImmediate) || teammateEastImmediate || obstacleEast || (teamBaseEastImmediate && !pawn.hasFlag) || isMine) ? (false) : (true);
    }// end isEast

    public boolean isWest() {
        Position moveWestOne = new Position(pawn.location.x - 1, pawn.location.y);
        boolean isMine = minesList.contains(moveWestOne);
        return ((pawn.hasFlag && enemyWestImmediate)|| teammateWestImmediate || obstacleWest || (teamBaseWestImmediate && !pawn.hasFlag) || isMine) ? (false) : (true);
    }// end isWest

    public boolean isSouth() {
        Position moveSouthOne = new Position(pawn.location.x, pawn.location.y + 1);
        boolean isMine = minesList.contains(moveSouthOne);
        return ((pawn.hasFlag && enemySouthImmediate) || teammateSouthImmediate || obstacleSouth || (teamBaseSouthImmediate && !pawn.hasFlag) || isMine) ? (false) : (true);
    }// end isSouth

    public void initializeWithEnvironment(AgentEnvironment inEnvironment) {

        pawn.hasFlag = inEnvironment.hasFlag();
        enemyHasFlag = inEnvironment.hasFlag(inEnvironment.ENEMY_TEAM);

        teamBaseWest = inEnvironment.isBaseWest(inEnvironment.OUR_TEAM, false);
        teamBaseEast = inEnvironment.isBaseEast(inEnvironment.OUR_TEAM, false);
        teamBaseSouth = inEnvironment.isBaseSouth(inEnvironment.OUR_TEAM, false);
        teamBaseNorth = inEnvironment.isBaseNorth(inEnvironment.OUR_TEAM, false);

        teamBaseWestImmediate = inEnvironment.isBaseWest(inEnvironment.OUR_TEAM, true);
        teamBaseEastImmediate = inEnvironment.isBaseEast(inEnvironment.OUR_TEAM, true);
        teamBaseSouthImmediate = inEnvironment.isBaseSouth(inEnvironment.OUR_TEAM, true);
        teamBaseNorthImmediate = inEnvironment.isBaseNorth(inEnvironment.OUR_TEAM, true);

        enemyBaseEast = inEnvironment.isBaseEast(inEnvironment.ENEMY_TEAM, false);
        enemyBaseWest = inEnvironment.isBaseWest(inEnvironment.ENEMY_TEAM, false);
        enemyBaseNorth = inEnvironment.isBaseNorth(inEnvironment.ENEMY_TEAM, false);
        enemyBaseSouth = inEnvironment.isBaseSouth(inEnvironment.ENEMY_TEAM, false);

        enemyBaseEastImmediate = inEnvironment.isBaseEast(inEnvironment.ENEMY_TEAM, true);
        enemyBaseWestImmediate = inEnvironment.isBaseWest(inEnvironment.ENEMY_TEAM, true);
        enemyBaseNorthImmediate = inEnvironment.isBaseNorth(inEnvironment.ENEMY_TEAM, true);
        enemyBaseSouthImmediate = inEnvironment.isBaseNorth(inEnvironment.ENEMY_TEAM, true);

        obstacleNorth = inEnvironment.isObstacleNorthImmediate();
        obstacleSouth = inEnvironment.isObstacleSouthImmediate();
        obstacleEast  = inEnvironment.isObstacleEastImmediate();
        obstacleWest  = inEnvironment.isObstacleWestImmediate();

        obstacleNorthEast = (obstacleNorth && obstacleEast);
        obstacleSouthEast = (obstacleSouth && obstacleEast);
        obstacleNorthWest = (obstacleNorth && obstacleWest);
        obstacleSouthWest = (obstacleSouth && obstacleWest);

        enemyNorthImmediate = inEnvironment.isAgentNorth(inEnvironment.ENEMY_TEAM, true);
        enemyEastImmediate = inEnvironment.isAgentEast(inEnvironment.ENEMY_TEAM, true);
        enemySouthImmediate = inEnvironment.isAgentSouth(inEnvironment.ENEMY_TEAM, true);
        enemyWestImmediate = inEnvironment.isAgentWest(inEnvironment.ENEMY_TEAM, true);

        teammateNorthImmediate = inEnvironment.isAgentNorth(inEnvironment.OUR_TEAM, true);
        teammateEastImmediate = inEnvironment.isAgentEast(inEnvironment.OUR_TEAM, true);
        teammateSouthImmediate = inEnvironment.isAgentSouth(inEnvironment.OUR_TEAM, true);
        teammateWestImmediate = inEnvironment.isAgentWest(inEnvironment.OUR_TEAM, true);

        for (int i = 0; i < goalLandmarkList.size(); i++) {
            if (goalLandmarkList.get(i).name == "teamBase") {
                if (teamBaseNorth && teamBaseEast) 
                    goalLandmarkList.get(i).assignedDirection = direction.NORTHEAST;
                 else if (teamBaseEast && teamBaseSouth) 
                    goalLandmarkList.get(i).assignedDirection = direction.SOUTHEAST;
                 else if (teamBaseWest && teamBaseSouth) 
                    goalLandmarkList.get(i).assignedDirection = direction.SOUTHWEST;
                 else if (teamBaseWest && teamBaseNorth) 
                    goalLandmarkList.get(i).assignedDirection = direction.NORTHWEST;
                 else if (teamBaseNorth) 
                    goalLandmarkList.get(i).assignedDirection = direction.NORTH;
                 else if (teamBaseEast) 
                    goalLandmarkList.get(i).assignedDirection = direction.EAST;
                 else if (teamBaseWest) 
                    goalLandmarkList.get(i).assignedDirection = direction.WEST;
                 else if (teamBaseSouth) 
                    goalLandmarkList.get(i).assignedDirection = direction.SOUTH;
            }// end outer if
            else if (goalLandmarkList.get(i).name == "enemyBase") {
                if (enemyBaseNorth && enemyBaseEast) 
                    goalLandmarkList.get(i).assignedDirection = direction.NORTHEAST;
                 else if (enemyBaseEast && enemyBaseSouth) 
                    goalLandmarkList.get(i).assignedDirection = direction.SOUTHEAST;
                 else if (enemyBaseWest && enemyBaseSouth) 
                    goalLandmarkList.get(i).assignedDirection = direction.SOUTHWEST;
                 else if (enemyBaseWest && enemyBaseNorth) 
                    goalLandmarkList.get(i).assignedDirection = direction.NORTHWEST;
                 else if (enemyBaseNorth) 
                    goalLandmarkList.get(i).assignedDirection = direction.NORTH;
                 else if (enemyBaseEast) 
                    goalLandmarkList.get(i).assignedDirection = direction.EAST;
                 else if (enemyBaseWest) 
                    goalLandmarkList.get(i).assignedDirection = direction.WEST;
                 else if (enemyBaseSouth) 
                    goalLandmarkList.get(i).assignedDirection = direction.SOUTH;
            }// end outer else if
        }// end for
    }// end initializeWithEnvironment

    public direction getDirectionGivenPosition(Position point){
        if(locationIsNorth(point) && locationIsEast(point))
            return direction.NORTHEAST;
        else if(locationIsEast(point) && locationIsSouth(point))
            return direction.SOUTHEAST;
        else if(locationIsSouth(point) && locationIsWest(point))
            return direction.SOUTHWEST;
        else if(locationIsNorth(point) && locationIsWest(point))
            return direction.NORTHWEST;
        else if(locationIsNorth(point))
            return direction.NORTH;
        else if(locationIsEast(point))
            return direction.EAST;
        else if (locationIsSouth(point))
            return direction.SOUTH;
        else
            return direction.WEST;
        
    }// end getDirectionGivenPosition

    public int getDistance(Position point1, Position point2){
        int xDistance = Math.abs(point1.x - point2.x);
        int yDistance = Math.abs(point1.y - point2.y);

        return (xDistance + yDistance) - 1;
    }// end getDistance

    public Position getSmallestPosition(Position location, ArrayList<Position> pList){
        int minDistance = 10000;
        int currDistance = 10000;
        for(int i = 0; i < pList.size(); i++){
            currDistance = getDistance(pList.get(i), location);
            if(currDistance < minDistance)
                minDistance = currDistance;
            
        }// end for

        Position closestPoint = new Position();
        for(int i = 0; i < pList.size(); i++){
            currDistance = getDistance(pList.get(i), location);
            if(currDistance == minDistance)
                closestPoint = pList.get(i);
            
        }// end for
        return closestPoint;
    }// end getSmallestPosition

    public boolean locationIsNorth(Position pos){
        return (pawn.location.y > pos.y);
    }// end locationIsNorth

    public boolean locationIsSouth(Position pos){
        return (pawn.location.y < pos.y);
    }// end locationIsSouth

    public boolean locationIsEast(Position pos){
        return (pawn.location.x < pos.x);
    }// end locationIsEast

    public boolean locationIsWest(Position pos){
        return (pawn.location.x > pos.x);
    }// end locationIsWest

    public boolean locationIsNorthEast(Position pos){
        return (locationIsNorth(pos) && locationIsEast(pos));
    }// end locationIsNorthEast

    public boolean locationIsSouthEast(Position pos){
        return (locationIsSouth(pos) && locationIsEast(pos));
    }// end locationIsSouthEast

    public boolean locationIsNorthWest(Position pos) {
        return (locationIsNorth(pos) && locationIsWest(pos));
    }// end locationIsNorthWest

    public boolean locationIsSouthWest(Position pos){
        return (locationIsSouth(pos) && locationIsWest(pos));
    }// end locationIsSouthWest

    public boolean threeObstacles() {
        return (obstacleNorthEastSouth || obstacleEastSouthWest || obstacleSouthWestNorth || obstacleWestNorthEast);
    }// threeObstacles

    public boolean isBlockedThreeWays(){
        return (blockedEastSouthWest() || blockedNorthEastSouth() || blockedSouthWestNorth() || blockedSouthWestNorth());
    }// end isBlockedThreeWays

    public boolean blockedNorthEastSouth(){
        return (!isNorth() && !isEast() && !isSouth());
    }// end blockedNorthEastSouth

    public boolean blockedEastSouthWest(){
        return (!isEast() && !isSouth() && !isWest());
    }// end blockedEastSouthWest

    public boolean blockedSouthWestNorth(){
        return (!isSouth() && !isWest() && !isNorth());
    }// end blockedSouthWestNorth

    public boolean blockedWestNorthEast(){
        return (!isWest() && !isNorth() && !isEast());
    }// end blockedWestNorthEast

    public direction findOpenDir() {
        direction openDir = direction.EAST;
        if (obstacleNorthEastSouth) 
            openDir = direction.WEST;
        else if (obstacleEastSouthWest) 
            openDir = direction.NORTH;
        else if (obstacleSouthWestNorth) 
            openDir = direction.EAST;
        else if (obstacleWestNorthEast) 
            openDir = direction.SOUTH;
        
        return openDir;
    }// end findOpenDir

    public direction getNotBlockedDirection() {
        direction openDir = direction.DONOTHING;
        if (blockedNorthEastSouth()) 
            openDir = direction.WEST;
         else if (blockedEastSouthWest()) 
            openDir = direction.NORTH;
         else if (blockedSouthWestNorth()) 
            openDir = direction.EAST;
         else if (blockedWestNorthEast()) 
            openDir = direction.SOUTH;
        
        return openDir;
    }// end getNotBlockedDirection

    public String getAgentName(int action) {
        String name = "does nothing";

        switch (action) {
            case AgentAction.MOVE_NORTH:
                name = "moves north";
                break;
            case AgentAction.MOVE_EAST:
                name = "moves east";
                break;
            case AgentAction.MOVE_SOUTH:
                name = "moves south";
                break;
            case AgentAction.MOVE_WEST:
                name = "moves west";
                break;
            case AgentAction.PLANT_HYPERDEADLY_PROXIMITY_MINE:
                name = "plants a mine";
                break;
            default:
            	break;
        }// end switch
        return name;
    }// end getAgentName

    public String getDirectionName(direction assignedDirection) {
    	
        switch (assignedDirection) {
            case NORTH:
                return "North";
            case NORTHEAST:
                return "NorthEast";
            case NORTHWEST:
                return "NorthWest";
            case SOUTH:
                return "South";
            case SOUTHWEST:
                return "SouthWest";
            case SOUTHEAST:
                return "SouthEast";
            case EAST:
                return "East";
            case WEST:
                return "West";
            default:
            	break;
        }// end switch
        return "ERROR: Could not find corresponding direction.";
    }// end getDirectionName

    public direction getDirectionGivenObstacle(obstacle obst){
        direction assignedDirection = direction.DONOTHING;

        switch(obst){
            case N_WALL:
                assignedDirection = direction.NORTH;
                break;
            case E_WALL:
                assignedDirection = direction.EAST;
                break;
            case S_WALL:
                assignedDirection = direction.SOUTH;
                break;
            case W_WALL:
                assignedDirection = direction.WEST;
                break;
            default:
            	break;
        }// end switch
        return assignedDirection;
    }// end getDirectionGivenObstacle  
}// end miv140130Agent class
