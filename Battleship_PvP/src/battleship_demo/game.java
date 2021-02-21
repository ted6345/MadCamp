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
	// data 통신을 통해서 상대방의 배열을 받아야되서
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
	
	static int[] Tocoordinate = new int[2];
	// 상대방한테 보내는 좌표 !! 
	static int[] Fromcoordinate = new int[2];
	// 상대방한테 받는 좌표
	static boolean still_playing = true;
	// game이 끝난는지 안끝났는지 알려주는 boolean
	static boolean hitting=true;
	// hitting
	public void setFromcoordinate(int [] t)
	{
		Fromcoordinate = t;
	}
	public int[] getTocoordinate()
	{
		return Fromcoordinate;
	}
	public boolean gethitting()
	{
		return hitting;
	}
	public boolean getstill_playing()
	{
		return still_playing;
	}
	public void setboard_setting(int[][] t) {
		board_setting2 = t;
	}

	public int[][] getboard_setting() {
		return board_setting;
	}

	public static void main(String[] args) {

		int turn = 0;
		board player1 = new board();
		board player2 = new board();
		
		player1.setting(board_setting);
		player2.setting(board_setting2);
		
		while (still_playing) {
			
			switch (turn%2) {
			case 0 :
				hitting= true;
				
				while(hitting&&still_playing){
					
				// opponent 로부터 준비가 됬는지를 확인해야 할것 같음
				System.out.println("player1 turn");
				
				Tocoordinate = player1.turn();
				//서버를 통해서 상대방한테도 따로 보내줘야되고				
				//짜야될것
				
				// 자기가 화면에 표시하기위해서 내부적으로 계산하는 것
				player2.opponent_turn(Tocoordinate);
				
				still_playing=player2.getstill_board();
				hitting=player2.gethit();
				
				}
				break;
			case 1:
				hitting = true;
				while(hitting&&still_playing){
				System.out.println("player2 turn");
				Fromcoordinate[0]=100;
				while(true)
				{
					if(Fromcoordinate[0]!=100)
						break;
				}
				player1.opponent_turn(Fromcoordinate);
				still_playing=player1.getstill_board();
				System.out.println(still_playing);
				hitting=player1.gethit();
				}break;
			}
			turn++;			
		}
		
		if(player1.getstill_board())	System.out.println("player1 Win");
		else if(player2.getstill_board())	System.out.println("player2 Win");
		else System.out.println("Error");
			
	
	}
}
