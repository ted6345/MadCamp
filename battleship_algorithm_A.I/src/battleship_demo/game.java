package battleship_demo;

public class game {

	static int[][] board_setting = 
	   {{1,1,1,1,0,0,0,0,0,0},
		{0,0,0,0,0,2,2,2,0,0},
		{3,3,3,0,0,0,0,0,0,0},
		{0,0,0,0,4,4,0,0,5,5},
		{6,6,0,0,0,0,0,0,0,0},
		{0,0,0,7,0,8,0,9,0,10},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0}};
	
	static int[][] board_setting2 = 
	   {{1,1,1,1,0,0,0,0,0,0},
		{0,0,0,0,0,2,2,2,0,0},
		{3,3,3,0,0,0,0,0,0,0},
		{0,0,0,0,4,4,0,0,5,5},
		{6,6,0,0,0,0,0,0,0,0},
		{0,0,0,7,0,8,0,9,0,10},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0}};	


	public static void main(String[] args) {

		boolean still_playing = true;
		boolean hitting=true;
		int turn = 0;
		board player = new board();
		AI_board AI = new AI_board();
		
		player.setting(board_setting);
		AI_board.setting(board_setting2);
		
		while (still_playing) {
			int[] coordinate = new int[2];
			switch (turn%2) {
			case 0 :
				hitting= true;
				while(hitting){
				System.out.println("player turn");
				coordinate = player.turn();
				AI.opponent_turn(coordinate);
				still_playing=AI.getstill_board();
				hitting=AI.gethit();
				}
				break;
			
			case 1:
				hitting = true;
				while(hitting){
				System.out.println("AI turn");
				coordinate = AI.turn();
				player.opponent_turn(coordinate);
				still_playing=player.getstill_board();
				hitting=player.gethit();
				}break;
			}
			turn++;
		}
          
	}
}
