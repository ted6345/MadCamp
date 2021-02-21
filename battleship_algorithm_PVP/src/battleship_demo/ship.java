package battleship_demo;

public class ship {

	int[][] kill = new int[2][2];
	int[][] coordinate;

	
	int size;
	int count;
	
	
	int min = 99, max = 0, parallel, parallel_value;

	public void setcoordinate(int[][] _Board, int id) {
		switch(id)
		{
		case 1: 
			size=4;
			count=4;
			break;
		case 2 :
		case 3 :
			size=3;
			count=3;
			break;
		case 4 :
		case 5 :
		case 6 :
			size=2;
			count=2;
			break;
		case 7 :
		case 8:
		case 9:
		case 10:
			size=1;
			count=1;
			break;
		}
		int[][] coordinate= new int[2][size];
		int i, j, k = 0;
		for (i = 0; i < 10; i++) {
			for (j = 0; j < 10; j++) {
				if (_Board[i][j] == id) {
					coordinate[0][k] = j;
					coordinate[1][k] = i;
				}
			}
		}
		if(size==1)
		{
			parallel=0;
			parallel_value=coordinate[0][0];
			min=coordinate[1][0];
			max=coordinate[1][0];
			
		}
		else if(coordinate[0][0]==coordinate[0][1])
		{
			parallel=0;
			parallel_value=coordinate[0][0];
			for(i=0;i<size;i++)
			{
				if(coordinate[1][i]>max)
					max=coordinate[1][i];
				if(coordinate[1][i]<min)
					min=coordinate[1][i];
			}
		}
		else{
			parallel=1;
			parallel_value=coordinate[1][0];
			for(i=0;i<size;i++)
			{
				if(coordinate[0][i]>max)
					max=coordinate[0][i];
				if(coordinate[0][i]<min)
					min=coordinate[0][i];
			}
		}
	}

	public int[][] make_kill(int[][] board) {
		int i;
		System.out.println("kill!!");
		if (parallel == 0) {
			
			kill[0][0] = parallel_value - 1;
			kill[1][0] = min - 1;
			kill[0][1] = parallel_value + 1;
			kill[1][1] = max + 1;
			
		} else {
			
			kill[0][0] = min - 1;
			kill[1][0] = parallel_value - 1;
			kill[0][1] = max + 1;
			kill[1][1] = parallel_value + 1;
		
		}
		
		i = kill[0][0];
		if (kill[0][0] < 0)
			i = 0;
		if (kill[1][0] >= 0) {
			for (; i < kill[0][1]; i++)
				board[kill[1][0]][i] = 11;
		}
		i = kill[1][0];
		if (kill[1][0] < 0)
			i = 0;
		if (kill[0][1] <= 9) {
			for (; i < kill[1][1]; i++)
				board[i][kill[0][1]] = 11;
		}
		i = kill[0][1];
		if (kill[0][1] > 9)
			i = 9;
		if (kill[1][1] <= 9) {
			for (; i > kill[0][0]; i--) {
				board[kill[1][1]][i] = 11;
			}
		}
		i = kill[1][1];
		if (kill[1][1] > 9)
			i = 9;
		if (kill[0][0] >= 0) {
			for (; i > kill[1][0]; i--)
				board[i][kill[0][0]] = 11;
		}
		
		
		return board;
	}

	public boolean hit() {
		System.out.println("hit!!");
		count--;
		if (count == 0)
			return true;
		else
			return false;
	}

}
