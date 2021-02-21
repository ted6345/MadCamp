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
	// data ����� ���ؼ� ������ �迭�� �޾ƾߵǼ�
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
	// �������� ������ ��ǥ !! 
	static int[] Fromcoordinate = new int[2];
	// �������� �޴� ��ǥ
	static boolean still_playing = true;
	// game�� �������� �ȳ������� �˷��ִ� boolean
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
					
				// opponent �κ��� �غ� ������� Ȯ���ؾ� �Ұ� ����
				System.out.println("player1 turn");
				
				Tocoordinate = player1.turn();
				//������ ���ؼ� �������׵� ���� ������ߵǰ�				
				//¥�ߵɰ�
				
				// �ڱⰡ ȭ�鿡 ǥ���ϱ����ؼ� ���������� ����ϴ� ��
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
