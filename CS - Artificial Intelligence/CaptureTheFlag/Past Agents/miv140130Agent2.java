package ctf.agent;

import ctf.common.AgentEnvironment;
import jdk.nashorn.internal.ir.ReturnNode;
import ctf.agent.Agent;
import ctf.common.AgentAction;

import java.util.Random;
import java.util.Stack;
import java.util.List;
import java.net.NoRouteToHostException;
import java.util.ArrayList;

public class miv140130Agent extends Agent {
    static boolean firstTurn = true;
    static boolean mapSizeFound = false;
    static boolean baseOnEast = true;

    static int mapSize;
    static int whoAmI; //1 is north pawn, 2 is south pawn
    static int pawn2Turn = 0;

    static int pawn1Row;
    static int pawn1Col;
    static int pawn2Row;
    static int pawn2Col;

    
    static char gameMap[][];

    List<Character> pawn1Moves = new ArrayList<>();
        
    //Stack<pathObj> pawn1Moves = new Stack<>();
    //Stack<pathObj> pawn2Moves = new Stack<>();
    public int getMove(AgentEnvironment inEnvironment) {
        int makeMove;
       
        if(firstTurn) { 

            if(inEnvironment.isAgentNorth(inEnvironment.OUR_TEAM, false)){
            	whoAmI = 2;
            } // end if
            else {
            	whoAmI = 1;
            } // send else
             
            if(inEnvironment.isAgentEast(inEnvironment.ENEMY_TEAM, false))
            	baseOnEast = false;
            else
            	baseOnEast = true;
            
            firstTurn = false;
        } //end if
        
        if(!mapSizeFound) {
            if(whoAmI == 1) {
                whoAmI = 2;
                return AgentAction.MOVE_SOUTH;
            } // end if
            else { // whoAmI == 2
                
                if(inEnvironment.isFlagNorth(inEnvironment.OUR_TEAM, true)) {
                    mapSize = 2 * (pawn2Turn + 2);
                    mapSizeFound = true;
                    System.out.println("pawn2Turn = " + pawn2Turn);
                    System.out.println("mapSize = " + mapSize);
                    gameMap = new char[mapSize][mapSize];

                    // fill map with "unexplored"" symbols
                    for(int i = 0; i < mapSize; i++) {
                        for(int j = 0; j < mapSize; j++) {
                            gameMap[i][j] = '.';
                        } // end nested for
                    } //end nested for

                    // initialize game map
                    for(int i = 0; i < mapSize; i++) {
                        if(i == pawn2Turn + 2) {
                            gameMap[i][0] = 'i';
                            gameMap[i][mapSize - 1] = 'i';
                        } // end if
                        else {
                            gameMap[i][0] = 'o';
                            gameMap[i][mapSize -1] = 'o';
                        } // end else
                    } //end for

                    pawn1Row = (mapSize / 2) - 2;
                    pawn2Row = (mapSize / 2) + 1;

                    if(inEnvironment.isAgentEast(inEnvironment.ENEMY_TEAM, false)) {
                        pawn1Col = 0;
                        pawn2Col = 0;
                    } //end if
                    else {
                        pawn1Col = (mapSize - 1);
                        pawn2Col = (mapSize - 1);
                    } //end else

                    printMap(gameMap);
                } // end nested if
                else {
                    whoAmI = 1;
                    pawn2Turn++;
                    return AgentAction.MOVE_NORTH;
                } // end nested else
            } // end else
        } // end if

        /*********************************BEHAVIORS*********************************************/

        boolean northBlocked = false;
        boolean southBlocked = false;
        boolean eastBlocked = false;
        boolean westBlocked = false;
        
        boolean northBlockByTeam = false;
        boolean southBlockByTeam = false;
        boolean eastBlockByTeam = false;
        boolean westBlockByTeam = false;
        
        int pawnRow;
        int pawnCol;

        // reset check
        if(baseOnEast) {
            if(whoAmI == 1 && inEnvironment.isObstacleEastImmediate() && inEnvironment.isObstacleNorthImmediate()) {
                if(inEnvironment.isBaseSouth(inEnvironment.OUR_TEAM, false) && !inEnvironment.isBaseEast(inEnvironment.OUR_TEAM, false)) {
                    // reset pawn 1's location
                    pawn1Row = 0;
                    pawn1Col = 0;
                } // end if(2)
            } // end if(1)
            if(whoAmI == 2 && inEnvironment.isObstacleEastImmediate() && inEnvironment.isObstacleSouthImmediate()) {
                if(inEnvironment.isBaseNorth(inEnvironment.OUR_TEAM, false) && !inEnvironment.isBaseEast(inEnvironment.OUR_TEAM, false)) {
                    // reset pawn 2's location
                    pawn2Row = mapSize - 1;
                    pawn2Col = 0;
                } // end if(2)
            } // end if(1)
        } //end if
        else {
            if(whoAmI == 1 && inEnvironment.isObstacleWestImmediate() && inEnvironment.isObstacleNorthImmediate()) {
                if(inEnvironment.isBaseSouth(inEnvironment.OUR_TEAM, false) && !inEnvironment.isBaseWest(inEnvironment.OUR_TEAM, false)) {
                    // reset pawn 1's location
                    pawn1Row = 0;
                    pawn1Col = mapSize - 1;
                } // end if(2)
            } // end if(1)
            if(whoAmI == 2 && inEnvironment.isObstacleEastImmediate() && inEnvironment.isObstacleSouthImmediate()) {
                if(inEnvironment.isBaseNorth(inEnvironment.OUR_TEAM, false) && !inEnvironment.isBaseWest(inEnvironment.OUR_TEAM, false)) {
                    // reset pawn 2's location
                    pawn2Row = mapSize - 1;
                    pawn2Col = mapSize - 1;
                } // end if(2)
            } // end if(1)
        } //end else

        if(whoAmI == 1) {
            pawnRow = pawn1Row;
            pawnCol = pawn1Col;
        } // end if
        else {
            pawnRow = pawn2Row;
            pawnCol = pawn2Col;
        } //end else

        // north obstacle check
        if(inEnvironment.isObstacleNorthImmediate()) {
            northBlocked = true;

            if(inEnvironment.isAgentNorth(inEnvironment.OUR_TEAM, true) || inEnvironment.isBaseNorth(inEnvironment.OUR_TEAM, true)) 
                northBlockByTeam = true;
                
            if(pawnRow != 0) {
                if(!northBlockByTeam) 
                    gameMap[pawnRow - 1][pawnCol] = 'x';
                else 
                    gameMap[pawnRow - 1][pawnCol] = 'o';
            } // end nested if
        } // end if

        // south obstacle check
        if(inEnvironment.isObstacleSouthImmediate()) {
            southBlocked = true;

            if(inEnvironment.isAgentSouth(inEnvironment.OUR_TEAM, true) || inEnvironment.isBaseSouth(inEnvironment.OUR_TEAM, true)) 
                southBlockByTeam = true;
                
            if(pawnRow != (mapSize - 1)) {
                if(!southBlockByTeam)
                    gameMap[pawnRow + 1][pawnCol] = 'x';
                else 
                    gameMap[pawnRow + 1][pawnCol] = 'o';
            } // end nested if
        } // end if

        // east obstacle check
        if(inEnvironment.isObstacleEastImmediate()) {
            eastBlocked = true;

            if(inEnvironment.isAgentEast(inEnvironment.OUR_TEAM, true) || inEnvironment.isBaseEast(inEnvironment.OUR_TEAM, true)) 
                eastBlockByTeam = true;
                
            if(pawnCol != (mapSize - 1)) {
                if(!eastBlockByTeam)
                    gameMap[pawnRow][pawnCol + 1] = 'x';
                else 
                 gameMap[pawnRow][pawnCol + 1] = 'o';
            } // end nested if
        } // end if

        // west obstacle check
        if(inEnvironment.isObstacleWestImmediate()) {
            westBlocked = true;

            if(inEnvironment.isAgentWest(inEnvironment.OUR_TEAM, true) || inEnvironment.isBaseWest(inEnvironment.OUR_TEAM, true)) 
                westBlockByTeam = true;
                
            if(pawnCol != 0) {
                if(!westBlockByTeam)
                    gameMap[pawnRow][pawnCol - 1] = 'x';
                else 
                    gameMap[pawnRow][pawnCol - 1] = 'o';
            } //end if
        } // end if
        
        if (whoAmI == 1) {
            whoAmI = 2; // change player for next turn
            System.out.println("Pawn 1 is attacking");
            return getFlag(inEnvironment, baseOnEast, northBlocked, southBlocked, eastBlocked, westBlocked);
             
        } // end else
        else {
            whoAmI = 1;  // change player for next turn
            System.out.println("Defending base");
            return defendBase(inEnvironment, baseOnEast);
        } // end else
    } // end getMove

    public void printMap(char gameMap[][]) {
        for(int i = 0; i < mapSize; i++) {
            for(int j = 0; j < mapSize; j++) 
                System.out.print(gameMap[i][j]);
            System.out.print("\n");
        } // end for
    } // end printMap

    public int getFlag(AgentEnvironment inEnv, boolean baseOnEast, boolean northBlocked, boolean southBlocked, boolean eastBlocked, boolean westBlocked) {

        AgentAction doThis = new AgentAction();

        StringBuilder str = new StringBuilder(200);

        // these check to see if the pawn has gotten stuck in a back-and-forth pattern
        boolean SN = false;
        boolean NS = false;
        boolean EW = false;
        boolean WE = false;

        if(!pawn1Moves.isEmpty()) {
            for(char s : pawn1Moves) 
                str.append(s);
            
            String check = str.toString();
            str = new StringBuilder(200);

            if(check.contains("NSNS")) // stuck moving north and south
                NS = true;
            else if(check.contains("SNSN")) // stuck moving south and north
                SN = true;
            else if(check.contains("EWEW")) // stuck moving east and west
                EW = true;
            else if(check.contains("WEWE")) //stuck moving west and east
                WE = true;
            else { } // do nothing
        } // end if

        if(NS) {
            gameMap[pawn1Row - 1][pawn1Col] = '#';
            gameMap[pawn1Row][pawn1Col] = '#';

            for(int i = 0; i < 4; i++) // remove last 4 moves from list as we are undoing the loop
                pawn1Moves.remove(pawn1Moves.size() - 1);
            
            pawn1Row++;
            pawn1Moves.add('S');
            return doThis.MOVE_SOUTH;
        } // end if

        if(SN) {
            gameMap[pawn1Row + 1][pawn1Col] = '#';
            gameMap[pawn1Row][pawn1Col] = '#';

            for(int i = 0; i < 4; i++) // remove last 4 moves from list as we are undoing the loop
                pawn1Moves.remove(pawn1Moves.size() - 1);
            
            pawn1Row--;
            pawn1Moves.add('N');
            return doThis.MOVE_NORTH;
        } // end if    

        if(EW) {
            gameMap[pawn1Row][pawn1Col + 1] = '#';
            gameMap[pawn1Row][pawn1Col] = '#';

            for(int i = 0; i < 4; i++) // remove last 4 moves from list as we are undoing the loop
                pawn1Moves.remove(pawn1Moves.size() - 1);

            pawn1Col--;
            pawn1Moves.add('W');
            return doThis.MOVE_WEST;
        } // end if

        if(WE) {
            gameMap[pawn1Row][pawn1Col - 1] = '#';
            gameMap[pawn1Row][pawn1Col] = '#';

            for(int i = 0; i < 4; i++) // remove last 4 moves from list as we are undoing the loop
                pawn1Moves.remove(pawn1Moves.size() - 1);
            
            pawn1Col++;
            pawn1Moves.add('E');
            return doThis.MOVE_EAST;
        } // end if

         // check to see if on a dead end path
         if(!northBlocked) {
             if(southBlocked && eastBlocked && gameMap[pawn1Row][pawn1Col -1] == '#') {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Row--;
                 pawn1Moves.add('N');
                 return doThis.MOVE_NORTH; // going west is a dead end- back out
             } //end if
             if(southBlocked && gameMap[pawn1Row][pawn1Col +1] == '#' && westBlocked) {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Row--;
                 pawn1Moves.add('N');
                 return doThis.MOVE_NORTH; // going east is a dead end- back out
             } //end if
             if(gameMap[pawn1Row +1][pawn1Col] == '#' && eastBlocked && westBlocked) {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Row--;
                 pawn1Moves.add('N');
                 return doThis.MOVE_NORTH; // going south is a dead end- back out
             } //end if
         } // end if

         if(!southBlocked) {
             if(northBlocked && eastBlocked && gameMap[pawn1Row][pawn1Col -1] == '#') {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Row++;
                 pawn1Moves.add('S');
                 return doThis.MOVE_SOUTH; // going west is a dead end- back out
             } //end if
             if(northBlocked && gameMap[pawn1Row][pawn1Col +1] == '#' && westBlocked) {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Row++;
                 pawn1Moves.add('S');
                 return doThis.MOVE_SOUTH; // going east is a dead end- back out
             } //end if
             if(gameMap[pawn1Row -1][pawn1Col] == '#' && eastBlocked && westBlocked) {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Row++;
                 pawn1Moves.add('S');
                 return doThis.MOVE_SOUTH; // going north is a dead end- back out
             } //end if
         } // end if

         if(!eastBlocked) {
             if(northBlocked && southBlocked && gameMap[pawn1Row][pawn1Col -1] == '#') {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Col++;
                 pawn1Moves.add('E');
                 return doThis.MOVE_EAST; // going west is a dead end- back out
             } //end if
             if(northBlocked && gameMap[pawn1Row +1][pawn1Col] == '#' && westBlocked) {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Col++;
                 pawn1Moves.add('E');
                 return doThis.MOVE_EAST; // going south is a dead end- back out
             } //end if
             if(gameMap[pawn1Row -1][pawn1Col] == '#' && eastBlocked && westBlocked) {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Col++;
                 pawn1Moves.add('E');
                 return doThis.MOVE_EAST; // going north is a dead end- back out
             } //end if
         } // end if

         if(!westBlocked) {
             if(northBlocked && southBlocked && gameMap[pawn1Row][pawn1Col +1] == '#') {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Col--;
                 pawn1Moves.add('W');
                 return doThis.MOVE_WEST; // going east is a dead end- back out
             } //end if
             if(northBlocked && gameMap[pawn1Row +1][pawn1Col] == '#' && eastBlocked) {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Col--;
                 pawn1Moves.add('W');
                 return doThis.MOVE_WEST; // going south is a dead end- back out
             } //end if
             if(gameMap[pawn1Row -1][pawn1Col] == '#' && eastBlocked && eastBlocked) {
                 gameMap[pawn1Row][pawn1Col] = '#';

                 pawn1Col--;
                 pawn1Moves.add('W');
                 return doThis.MOVE_WEST; // going north is a dead end- back out
             } //end if
         } // end if

        //  movement if pawn has the flag
        if(inEnv.hasFlag()) {
            if(baseOnEast) {
                if(pawn1Col == mapSize -1) { // pawn in on his home side
                    if(inEnv.isBaseNorth(inEnv.OUR_TEAM, false)) {
                        pawn1Row--;
                        pawn1Moves.add('N');
                        return doThis.MOVE_NORTH;
                    } // end if(3)
                    else {
                        pawn1Row++;
                        pawn1Moves.add('S');
                        return doThis.MOVE_SOUTH;
                    } // end else
                } //end if(2)

                // pawn is somewhere between goals 
                if(!eastBlocked && !inEnv.isAgentEast(inEnv.ENEMY_TEAM, true)) { // move east
                    pawn1Col++;
                    pawn1Moves.add('E');
                    return doThis.MOVE_EAST;
                } // end if(2)
                if(!southBlocked && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, true)) { // move north
                    pawn1Row++;
                    pawn1Moves.add('S');
                    return doThis.MOVE_SOUTH;
                } // end if(2)
                if(!northBlocked && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, true)) { // move south
                    pawn1Row--;
                    pawn1Moves.add('N');
                    return doThis.MOVE_NORTH;
                }
                if(!westBlocked && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, true)) { // move west as last resort 
                    pawn1Col--;
                    pawn1Moves.add('W');
                    return doThis.MOVE_WEST;
                } // end if(1)

                return doThis.DO_NOTHING; //blocked- wait for a pawn to move
            } // end if(1)
            else {
                if(pawn1Col == 0) { // pawn on home column
                    if(inEnv.isBaseNorth(inEnv.OUR_TEAM, false)) {
                        pawn1Row--;
                        pawn1Moves.add('N');
                        return doThis.MOVE_NORTH;
                    } // end if(3)
                    else {
                        pawn1Row++;
                        pawn1Moves.add('S');
                        return doThis.MOVE_SOUTH;
                    } // end else
                } //end if(2)

                // pawn is somewhere between goals
                if(!westBlocked && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, true)) { // move west
                    pawn1Col--;
                    pawn1Moves.add('W');
                    return doThis.MOVE_WEST;
                } // end if(1)
                if(!southBlocked && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, true)) { // move north
                    pawn1Row++;
                    pawn1Moves.add('S');
                    return doThis.MOVE_SOUTH;
                } // end if(2)
                if(!northBlocked && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, true)) { // move south
                    pawn1Row--;
                    pawn1Moves.add('N');
                    return doThis.MOVE_NORTH;
                } //end if(2)
                if(!eastBlocked && !inEnv.isAgentEast(inEnv.ENEMY_TEAM, true)) { // move east as last resort
                    pawn1Col++;
                    pawn1Moves.add('E');
                    return doThis.MOVE_EAST;
                } // end if(2)

                return doThis.DO_NOTHING; //blocked- wait for a pawn to move
            } //end else

        } // end if

        // movement if on opponents side
        

        // determine default movement
        boolean safeMoveNorth = false;
        boolean safeMoveSouth = false;
        boolean safeMoveEast = false;
        boolean safeMoveWest = false;

        if(!northBlocked && gameMap[pawn1Row -1][pawn1Col] != '#') 
            safeMoveNorth = true;
        if(!southBlocked && gameMap[pawn1Row +1][pawn1Col] != '#')
            safeMoveSouth = true;
        if(!eastBlocked && gameMap[pawn1Row][pawn1Col + 1] != '#')
            safeMoveEast = true;
        if(!westBlocked && gameMap[pawn1Row][pawn1Col - 1] != '#')
            safeMoveWest = true;

        // randomly choose movement(while preferring to move towards opponent goal)
        double northChance = 0;
        double southChance = 0;
        double eastChance = 0;
        double westChance = 0;

        if(baseOnEast && safeMoveEast && inEnv.isAgentEast(inEnv.OUR_TEAM, true))
            eastChance = .5;
        if(!baseOnEast && safeMoveWest && inEnv.isAgentWest(inEnv.OUR_TEAM, true))
            westChance = .5;
            

            return 1; //needed so the compiler doesn't get mad
    } // end getFlag
    
     public int defendBase(AgentEnvironment inEnv, boolean baseOnEast) {
		
		AgentAction doThis = new AgentAction();
		int move = 0;
		
		// If they have the flag 
		if(inEnv.hasFlag(inEnv.ENEMY_TEAM)){
			
			// If the enemy is in front of the defender then attack them
			if(inEnv.isAgentNorth(inEnv.ENEMY_TEAM, true)){
				return doThis.MOVE_NORTH;
			}
			if(inEnv.isAgentSouth(inEnv.ENEMY_TEAM, true))
				return doThis.MOVE_SOUTH;
			if(inEnv.isAgentWest(inEnv.ENEMY_TEAM, true))
				return doThis.MOVE_WEST;
			if(inEnv.isAgentEast(inEnv.ENEMY_TEAM, true))
				return doThis.MOVE_EAST;
			
			
			// If the enemy is directly west or east in front of us then chase them with no obstacles
			if(baseOnEast && inEnv.isAgentWest(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleWestImmediate())
				return doThis.MOVE_WEST;
			if(!baseOnEast && inEnv.isAgentWest(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleEastImmediate())
				return doThis.MOVE_EAST;
			
			// If the enemy is directly north or south in front of us then chase them with no obstacles
			if(baseOnEast && inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentEast(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleNorthImmediate())
				return doThis.MOVE_NORTH;
			if(!baseOnEast && inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentEast(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleSouthImmediate())
				return doThis.MOVE_SOUTH;
			
			// If they are directly in front of us and we are blocked from West and (North or South), then move north or south
			if (baseOnEast && inEnv.isAgentWest(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && inEnv.isObstacleWestImmediate() && (inEnv.isObstacleNorthImmediate() || inEnv.isBaseNorth(inEnv.OUR_TEAM, true)))
				return doThis.MOVE_SOUTH;
			if (baseOnEast && inEnv.isAgentWest(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && inEnv.isObstacleWestImmediate() && (inEnv.isObstacleSouthImmediate() || inEnv.isBaseSouth(inEnv.OUR_TEAM, true)))
				return doThis.MOVE_NORTH;
			
			// If they are directly in front of us and  we are blocked from East and North or South
			if (!baseOnEast && inEnv.isAgentEast(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && inEnv.isObstacleWestImmediate() && (inEnv.isObstacleNorthImmediate() || inEnv.isBaseNorth(inEnv.OUR_TEAM, true)))
				return doThis.MOVE_SOUTH;
			if (!baseOnEast && inEnv.isAgentEast(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && inEnv.isObstacleWestImmediate() && (inEnv.isObstacleSouthImmediate() || inEnv.isBaseSouth(inEnv.OUR_TEAM, true)))
				return doThis.MOVE_NORTH;
			
			
			/*
			if(baseOnEast && inEnv.isAgentWest(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleNorthImmediate())
				return doThis.MOVE_NORTH;
			else if(baseOnEast && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isObstacleSouthImmediate())
				return doThis.MOVE_SOUTH;
			else if (!baseOnEast && inEnv.isAgentEast(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false)&& !inEnv.isObstacleNorthImmediate())
				return doThis.MOVE_NORTH;
			else if (!baseOnEast && !inEnv.isAgentEast(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isObstacleSouthImmediate())
				return doThis.MOVE_SOUTH;
			else {};*/
		}
		
		//************************** ENEMY HAS NO FLAG ************************************************
		
		/* PRIORITY #1: If an enemy is IMMEDIATELY in front of me
		 * GOAL: 		Attack them
		 */
		if(inEnv.isAgentNorth(inEnv.ENEMY_TEAM, true)) {
			//gameMap[pawnRow + 1][pawnCol] = 'x';
			return doThis.MOVE_NORTH;
		}
		if(inEnv.isAgentSouth(inEnv.ENEMY_TEAM, true))
			return doThis.MOVE_SOUTH;
		if(inEnv.isAgentWest(inEnv.ENEMY_TEAM, true))
			return doThis.MOVE_WEST;
		if(inEnv.isAgentEast(inEnv.ENEMY_TEAM, true))
			return doThis.MOVE_EAST;
		
		/* PRIORITY #2: If an enemy is DIRECTLY NORTH or SOUTH of me 
		 * 						- There are NO obstacles
		 * 
		 * Goal: 		If there is an enemy, North or South of me, then get them and attack. Get rid of them
		 */
		if(inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentEast(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleNorthImmediate())
			return doThis.MOVE_NORTH;
		if(inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentEast(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleSouthImmediate())
			return doThis.MOVE_SOUTH;
		
		/* PRIORITY #2.2: If an enemy is DIRECTLY NORTH or SOUTH of me 
		 * 						- YES, obstacles
		 * 
		 * Goal: 		If there is an enemy, North or South of me, then get them and attack. Get rid of them
		 */
		if(inEnv.isObstacleNorthImmediate() || inEnv.isAgentNorth(inEnv.OUR_TEAM, true) || inEnv.isBaseNorth(inEnv.OUR_TEAM, true)){
			if(inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentEast(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleNorthImmediate()){
				if(!inEnv.isObstacleEastImmediate() || !inEnv.isAgentEast(inEnv.OUR_TEAM, true) || !inEnv.isBaseEast(inEnv.OUR_TEAM, true))
					return doThis.MOVE_EAST;
				if(!inEnv.isObstacleWestImmediate() || !inEnv.isAgentWest(inEnv.OUR_TEAM, true) || !inEnv.isBaseWest(inEnv.OUR_TEAM, true))
					return doThis.MOVE_WEST;
			} // end if North only
		}// end if obstacles North
		if(inEnv.isObstacleSouthImmediate() || inEnv.isAgentSouth(inEnv.OUR_TEAM, true) || inEnv.isBaseSouth(inEnv.OUR_TEAM, true)){
			if(inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentWest(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentEast(inEnv.ENEMY_TEAM, false) && !inEnv.isObstacleNorthImmediate()){
				if(!inEnv.isObstacleEastImmediate() || !inEnv.isAgentEast(inEnv.OUR_TEAM, true) || !inEnv.isBaseEast(inEnv.OUR_TEAM, true))
					return doThis.MOVE_EAST;
				if(!inEnv.isObstacleWestImmediate() || !inEnv.isAgentWest(inEnv.OUR_TEAM, true) || !inEnv.isBaseWest(inEnv.OUR_TEAM, true))
					return doThis.MOVE_WEST;
			} // end South only
		} // end if obstacles South
		
		/* PRIORITY #3: If an enemy is NW or NE of me
		 * 					- NO obstacles NORTH of me, then go North
		 * 					- Obstacles = teammates, actual obstacles, or our base
		 * 
		 * GOAL:		Get closer to the enemy where you are directly in front of them. If enemy is North of you, then go North
		 */
		if(!inEnv.isObstacleNorthImmediate() && !inEnv.isAgentNorth(inEnv.OUR_TEAM, true) && !inEnv.isBaseNorth(inEnv.OUR_TEAM, true)){
			if(baseOnEast && inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentWest(inEnv.ENEMY_TEAM, false))
				return doThis.MOVE_NORTH;
			if(!baseOnEast && inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentEast(inEnv.ENEMY_TEAM, false))
				return doThis.MOVE_NORTH;
		}
		
		/*
		 * PRIORITY #3.2: If an enemy is NW or NE of me
		 * 					- YES, there is an obstacle North of me
		 * 
		 * GOAL:		Get around that obstacle and line up with enemy
		 */
		if(inEnv.isObstacleNorthImmediate() || inEnv.isAgentNorth(inEnv.OUR_TEAM, true) || inEnv.isBaseNorth(inEnv.OUR_TEAM, true)){		// If there are obstacles north
			if(!inEnv.isObstacleWestImmediate() || !inEnv.isAgentWest(inEnv.OUR_TEAM, true) || !inEnv.isBaseWest(inEnv.OUR_TEAM, true)){	// If there are no obstacles west
				if(baseOnEast && inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentWest(inEnv.ENEMY_TEAM, false))					// If enemy is NW of me
					return doThis.MOVE_WEST;
			}
			if(!inEnv.isObstacleEastImmediate() || !inEnv.isAgentEast(inEnv.OUR_TEAM, true) || !inEnv.isBaseEast(inEnv.OUR_TEAM, true)){	// If thre are no obstacles EAST
				if(!baseOnEast && inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentEast(inEnv.ENEMY_TEAM, false))					// If enemy is NE of me
					return doThis.MOVE_EAST;
			}
		}
		
		/* PRIORITY #4: If an enemy is SW or SE of me
		 * 						- NO obstacles SOUTH of me, then go SOUTH
		 * 						- Obstacles = teammates, actual obstacles, or our base
		 * 
		 * GOAL:			Get closer to the enemy where you are directly in front of them. If enemy is SOUTH of me, then go SOUTH
		 */
		if(!inEnv.isObstacleSouthImmediate() && !inEnv.isAgentSouth(inEnv.OUR_TEAM, true) && !inEnv.isBaseSouth(inEnv.OUR_TEAM,  true)){
			if(baseOnEast && inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentWest(inEnv.ENEMY_TEAM, false))
				return doThis.MOVE_SOUTH;
			if(!baseOnEast && inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentEast(inEnv.ENEMY_TEAM, false))
				return doThis.MOVE_SOUTH;
		}
		
		/* PRIORITY #4.2: If an enemy is SW or SE of me
		 * 					- YES there is an obstacle Sorth of me
		 * 
		 * GOAL:		Get around that obstacle and line up with enemy
		 */
		if(inEnv.isObstacleSouthImmediate() || inEnv.isAgentSouth(inEnv.OUR_TEAM, true) || inEnv.isBaseSouth(inEnv.OUR_TEAM, true)){		// If there are obstacles SOUTH
			if(!inEnv.isObstacleWestImmediate() || !inEnv.isAgentWest(inEnv.OUR_TEAM, true) || !inEnv.isBaseWest(inEnv.OUR_TEAM, true)){	// If there are no obstacles WEST
				if(baseOnEast && inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentWest(inEnv.ENEMY_TEAM, false))					// If enemy is SW of me
					return doThis.MOVE_WEST;
			}
			if(!inEnv.isObstacleEastImmediate() || !inEnv.isAgentEast(inEnv.OUR_TEAM, true) || !inEnv.isBaseEast(inEnv.OUR_TEAM, true)){	// If thre are no obstacles EAST
				if(!baseOnEast && inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false)&& inEnv.isAgentEast(inEnv.ENEMY_TEAM, false))					// If enemy is SE of me
					return doThis.MOVE_EAST;
			}
		}
		
		/* PRIORITY #5:		If an enemy is DIRECTLY WEST or EAST of me
		 * 
		 * GOAL:			Do nothing. We want to line up directly with an enemy. Hold your ground
		 * 
		 */
		if(baseOnEast && inEnv.isAgentWest(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false))
			return doThis.DO_NOTHING;
		if(!baseOnEast && inEnv.isAgentWest(inEnv.ENEMY_TEAM, false) && !inEnv.isAgentSouth(inEnv.ENEMY_TEAM, false)&& !inEnv.isAgentNorth(inEnv.ENEMY_TEAM, false))
			return doThis.DO_NOTHING;
		
		// LAST PRIORITY: do nothing
		return doThis.DO_NOTHING;
	} // end defend
} // end miv140130Agent 

class Path 
{
	int row;
	int col;
	Path previous;
	
	// *************** CONSTRUCTORS ***************
	public Path(int argRow, int argCol) {
		this.row = argRow;
		this.col = argCol;
	}
	
	// *************** GETTERS ***************
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public Path getPrevious(){
		return previous;
	}
	// *************** SETTERS ***************
	public void setRow(int argX){
		this.row = argX;
	}
	public void setCol(int argY){
		this.col = argY;
	}
	public void setPrevious(Path argP){
		this.previous = argP;
	} 
	
	// *************** METHODS ***************
	
	public boolean isEqual(Path current){
		return (this.row == current.row) && (this.col == current.col);
	}

} // end Path class
