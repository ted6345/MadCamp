package battleship_AI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class AIboard {

	boolean something;
	boolean hit = false;
	private boolean still_board = true;
	int[][] board = new int[10][10];
	int[] targetcoordinate = new int[2];
	
	int life = 10;

	ArrayList<ship> setofship = new ArrayList<ship>();

	public boolean getstill_board() {
		return still_board;
	}

	public boolean gethit() {
		return hit;
	}

	public void settargetcoordinate(int[] t) {
		targetcoordinate = t;
	}

	public void setting(int setting[][]) {		
		board = setting;		// �ڵ���ġ�ϱ� �����ؾ���!! 
		ship ship1 = new ship();
		ship ship2 = new ship();
		ship ship3 = new ship();
		ship ship4 = new ship();
		ship ship5 = new ship();
		ship ship6 = new ship();
		ship ship7 = new ship();
		ship ship8 = new ship();
		ship ship9 = new ship();
		ship ship10 = new ship();
		setofship.add(0, null);
		setofship.add(1, ship1);
		setofship.add(2, ship2);
		setofship.add(3, ship3);
		setofship.add(4, ship4);
		setofship.add(5, ship5);
		setofship.add(6, ship6);
		setofship.add(7, ship7);
		setofship.add(8, ship8);
		setofship.add(9, ship9);
		setofship.add(10, ship10);
		for (int i = 1; i <= 10; i++){
			setofship.get(i).setcoordinate(board, i);
		}
	}

	public int[] turn(int opponentboard[][]) {
		do{
		do{
			targetcoordinate[0]=(int) 	(Math.random()*10);
			targetcoordinate[1]=(int)	(Math.random()*10);			
		}while(targetcoordinate[0]==10||targetcoordinate[1]==10);
		}while(opponentboard[targetcoordinate[0]][targetcoordinate[1]]>10);
		System.out.println("AI�� �ش���ǥ�� �����Ͽ����ϴ�!!");
		System.out.println(targetcoordinate[0] + " , " + targetcoordinate[1]);
		return targetcoordinate;
	}

	public void opponent_turn(int[] attack) {
		hit = false;
		int[][] tmp = new int[2][11];
		int i = 0;
		switch (board[attack[1]][attack[0]]) {
		case 0:
			board[attack[1]][attack[0]] = 12;
			System.out.println("no hit");
			hit = false;			
			break;
		default:
			if (setofship.get(board[attack[1]][attack[0]]).hit()) {
				tmp = setofship.get(board[attack[1]][attack[0]]).make_kill(board);			
				life--;
				if (life == 0)
					still_board = false;
			}
			board[attack[1]][attack[0]] = 11;
			hit = true;
			break;
		}

	}
}
