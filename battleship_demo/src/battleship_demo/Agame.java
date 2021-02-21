package battleship_demo;

public class Agame {

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

	//	public void setboard_setting(int[][] t) {
//		board_setting = t;
//	}
//
//	public int[][] getboard_setting() {
//		return board_setting;
//	}

	public static void main(String[] args) {

		boolean still_playing = true;
		boolean hitting=true;
		int turn = 0;
		board player1 = new board();
		board player2 = new board();
		
		player1.setting(board_setting);
		player2.setting(board_setting2);
		
		while (still_playing) {
			int[] coordinate = new int[2];
			switch (turn%2) {
			case 0 :
				hitting= true;
				while(hitting){
				System.out.println("player1 turn");
				coordinate = player1.turn();
				player2.opponent_turn(coordinate);
				still_playing=player2.getstill_board();
				hitting=player2.gethit();
				System.out.println("Plyaer1 Board");
				for(int i=0;i<10;i++){
					for(int j=0;j<10;j++)
					{
						System.out.printf("%5d",board_setting[i][j]);
					}
					System.out.println("\n");
				}
				
				System.out.println("Plyaer2 Board");
				for(int i=0;i<10;i++){
					for(int j=0;j<10;j++)
					{
						System.out.printf("%5d",board_setting2[i][j]);
					}
					System.out.println("\n");
				}
				}
				
				break;
			
			case 1:
				hitting = true;
				while(hitting){
				System.out.println("player2 turn");
				coordinate = player2.turn();
				player1.opponent_turn(coordinate);
				still_playing=player1.getstill_board();
				hitting=player1.gethit();
				System.out.println("Plyaer1 Board");
				for(int i=0;i<10;i++){
					for(int j=0;j<10;j++)
					{
						System.out.printf("%5d",board_setting[i][j]);
					}
					System.out.println("\n");
				}
				
				System.out.println("Plyaer2 Board");
				for(int i=0;i<10;i++){
					for(int j=0;j<10;j++)
					{
						System.out.printf("%5d",board_setting2[i][j]);
					}
					System.out.println("\n");
				}
				}break;
			}
			turn++;
		}
          
	}
}
