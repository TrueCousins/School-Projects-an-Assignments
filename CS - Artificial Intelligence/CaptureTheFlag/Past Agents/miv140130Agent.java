package ctf.agent;

import ctf.common.AgentEnvironment;
import ctf.agent.Agent;
import ctf.common.AgentAction;

import java.util.Random;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class miv140130Agent extends Agent {
    public int getMove(AgentEnvironment inEnvironment) {
        boolean firstTurn = true;
        boolean mapSize;

        int whoAmI = 1; //1 is north pawn, 2 is south pawn
        int turn = 0;
        int rowIdx;
        int colIdx;
        
        char gameMap[][];
        
        Stack<Integer> pawn1Moves = new Stack<>();


        if(firstTurn) { 

            if(inEnvironment.isAgentNorth(inEnvironment.OUR_TEAM, false))
                whoAmI = 2;
            else 
                whoAmI = 1;
            firstTurn = false;
        }

        if (whoAmI == 1) {
            whoAmI = 2; //change player for next turn
            return AgentAction.MOVE_NORTH;
        }
        else {
            whoAmI = 1;
            if(inEnvironment.isFlagEast(inEnvironment.ENEMY_TEAM, false))
                return AgentAction.MOVE_EAST;
            else
                return AgentAction.MOVE_WEST;
        }
    } // end getMove
} // end miv140130Agent