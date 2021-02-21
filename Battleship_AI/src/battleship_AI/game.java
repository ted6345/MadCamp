package battleship_AI;

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
		AIboard player2 = new AIboard();
		
		player1.setting(board_setting);
		player2.setting(board_setting2); // android의 초기 배열을 random 하게 설정해주어야 한다.!!
		
		while (still_playing) {
			int[] coordinate = new int[2];
			switch (turn % 2) {
			case 0:
				hitting = true;
				while (hitting && still_playing) {
					System.out.println("player1 turn");
					System.out.println("player2 board before");  //배의 위차빼고 출력되어야 한다!!
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							if(board_setting2[i][j]>10)
								{
								System.out.printf("%5d", board_setting2[i][j]);
								}
								else
									{
									System.out.printf("%5d", 0);
									}
							
						}
						System.out.println("\n");
					}
					coordinate = player1.turn();
					player2.opponent_turn(coordinate);
					still_playing = player2.getstill_board();
					hitting = player2.gethit();
				
					System.out.println("player2 board After");  //배의 위차빼고 출력되어야 한다!!
					
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							if(board_setting2[i][j]>10)
								System.out.printf("%5d", board_setting2[i][j]);
							else System.out.printf("%5d", 0);
							
						}
						System.out.println("\n");
					}
					System.out.println("hitting boolean   ="+hitting);  //배의 위차빼고 출력되어야 한다!!
					
				}
				
				
				break;

			case 1:
				hitting = true;
				while (hitting && still_playing) {
					System.out.println("Android turn");
					System.out.println("player1 board Before");   //배의 위차빼고 출력되어야 한다!!
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							if(board_setting[i][j]>10)
							System.out.printf("%5d", board_setting[i][j]);
							else System.out.printf("%5d", 0);
						}
						System.out.println("\n");
					}
					coordinate = player2.turn(board_setting);      //랜덤하게 죄표를 찍어준다!!
					player1.opponent_turn(coordinate);
					still_playing = player1.getstill_board();
					hitting = player1.gethit();
					System.out.println("player1 board After");   //배의 위차빼고 출력되어야 한다!!
					for (int i = 0; i < 10; i++) {
						for (int j = 0; j < 10; j++) {
							if(board_setting[i][j]>10)
							System.out.printf("%5d", board_setting[i][j]);
							else System.out.printf("%5d", 0);
						}
						System.out.println("\n");
					}
			}
			
			break;
		}//End of Switch
			turn++;
		}
		if(player1.getstill_board())	System.out.println("player1 Win");
		else if(player2.getstill_board())	System.out.println("Android Win");
		else System.out.println("Error");
		
	}
}
	
