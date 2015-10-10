import java.util.*;
import java.lang.*;
import java.io.*;
 
/* Name of the class has to be "Main" only if the class is public. */
public class PlayGame
{   
	public static void name(int x){ //輸出撲克牌
		String y,z;
		if (x>1) {
			if (x%4==0) y="H";
			else if (x%4==1) y="S";
			else if (x%4==2) y="C";
			else y="D";
			if (x<38) z=Integer.toString((x+6)/4);
		    else if (x<42) z="J";
		    else if (x<46) z="Q";
	    	else if (x<50) z="K";
	    	else z="A";
		}
		else if (x==1) {y="B"; z="0";} //黑色鬼牌
		else {y="R"; z="0";} //紅色鬼牌
 
		System.out.printf( "%3s ",(y+z)); 
	}
 
	public static int dropCards(int x[], int xl, int kx){ //配對＋丟牌＋顯示
		int i,j,m,n;
	    for (i=0; i<xl-1; i++){
			m=(x[i]+2)/4;
			n=(x[i+1]+2)/4;
			if (m==n && m!=0) {
				xl-=2;
				for (j=i; j<xl; j++) 
					x[j]=x[j+2];
				i--;
			}
		}
	    System.out.printf("\nPlayer%d:",kx);
		for (i=0; i<xl; i++) name(x[i]);
		return xl;
	}
 
	public static int[] drawCard(int x[], int xl, int kx, int y[], int yl, int ky){ //抽牌＋顯示
		int i,j,k;
		Random ran = new Random();
		j=ran.nextInt(yl);
		System.out.printf("\nPlayer%d  draws a card from Player%d ",kx,ky);
		name(y[j]);
 
		x[xl]=y[j];
		xl+=1;
		yl-=1;
		Arrays.sort(x,0,xl);
		for (k=j; k<yl; k++) y[k]=y[k+1];
 
		xl = dropCards(x,xl,kx);
		yl = dropCards(y,yl,ky);
        int draw[] = {xl,yl};
        return draw;
	}
    
    public static int[] winner(int players, int player[][], int playerl[], int playerk[], int j, int k){
    	int i, round=0;
    	if (playerl[j]==0 && playerl[k]==0) { //若有兩人同時獲勝
            players -= 2;
	        System.out.printf("\nPlayer%d and Player%d win\n",playerk[j],playerk[k]);
	     	for (i=j; i<players; i++){
	         	player [i] = player [i+2];
                playerl[i] = playerl[i+2];
                playerk[i] = playerk[i+2];
     	    }
     	}
	    else { //若只有其中一人獲勝
	     	players -= 1;
	       	System.out.print("\nPlayer");
	        if (playerl[j]==0){
		    	System.out.printf("%d",playerk[j]); 
	    		for (i=j; i<players; i++){
	    	        player [i] = player [i+1];
                    playerl[i] = playerl[i+1];
                    playerk[i] = playerk[i+1];
                    round = j;
	    		}
	    	}
	    	else if (playerl[k]==0){
	    		System.out.printf("%d",playerk[k]); 
    	    	for (i=k; i<players; i++){
	        		player [i] = player [i+1];
                    playerl[i] = playerl[i+1];
                    playerk[i] = playerk[i+1];
                    round = k;
	    		}
	        }
	        System.out.println(" wins");
	    }	
	    int win[] = {players, round};
	    return win;
    } 
            	
    // continue之後的下一個玩家，檢查作業要求，改Main檔名
	public static void main (String[] args) throws java.lang.Exception
	{
		int i,j,k;
		int al=14, bl=14, cl=13, dl=13, total=54; //牌數
	    int s[] = new int[total]; //total poker cards
		int x[] = new int[total]; //次序-亂數陣列
		int x0[]= new int[total]; //次序-原始陣列
	    int a[] = new int[al]; //player0
	    int b[] = new int[bl]; //player1
	    int c[] = new int[cl]; //player2
	    int d[] = new int[dl]; //player3
        int players   = 4; //玩家數
        int player[][]= {a,b,c,d}; //將玩家丟進一個陣列
        int playerl[] = {al,bl,cl,dl}; //每個玩家的牌數
        int playerk[] = {0,1,2,3}; //玩家次序
        int round = 0, first = 0; //抽牌變數
        int draw[] = new int [2]; //回傳drawCard函式的值
        int win[]  = new int [2]; //回傳winner函式的值
        Random ran = new Random(); //宣告Random

	    //建立亂數排列的撲克牌
	    for (i=0; i<total; i++){
	        x0[i] = i; 
	        s[i] = i;
	    } 
	    for (i=0; i<total; i++){
	    	j = ran.nextInt(total-i);
	        x[i] = x0[j];
	       	for (k=j; k<x0.length-1; k++) 
	       		x0[k] = x0[k+1];
	    } 
 
	    //發牌
		System.out.print("Deal cards"); 
		for (i=0; i<players; i++){
		    System.out.printf("\nPlayer%d:", playerk[i]);	
		    for (j=0; j<playerl[i]; j++){
		    	if (i==0) player[i][j] = s[x[j]];
		    	else if (i==1) player[i][j] = s[x[j+al]];
		    	else if (i==2) player[i][j] = s[x[j+2*al]];
		    	else player[i][j] = s[x[j+2*al+cl]];
		    }
		    Arrays.sort(player[i]);
	        for (j=0; j<playerl[i]; j++) name(player[i][j]);
		}

	    //丟牌
	    System.out.print("\nDrop cards"); 
	    for (i=0; i<players; i++){
	    	playerl[i] = dropCards(player[i],playerl[i],playerk[i]);
	    }
 
		//抽牌，遊戲開始
		System.out.print("\nGame start");

		if (playerl[0]==0 || playerl[1]==0) { //判斷比賽一開始是否有人獲勝
        	win = winner(players, player, playerl, playerk, 0, 1);
            players = win[0];
        }

        //開始抽牌		
        while (players>1){
        	j = round%players; //指定抽牌的玩家為j
        	k = (round+1)%players; //指定被抽牌的玩家為k
 
	    	draw = drawCard(player[j],playerl[j],playerk[j],player[k],playerl[k],playerk[k]); //抽牌
	    	playerl[j] = draw[0];
	    	playerl[k] = draw[1];
	    	round+=1; //下一次抽牌

            //若有人獲勝
            if (playerl[j]==0 || playerl[k]==0) {
                win = winner(players, player, playerl, playerk, j, k);
                players = win[0];
                round = win[1];
                if (first==0){ //若是basic game
	    	        System.out.print("Basic game over\n\nContinue");
	    	    first=1;
	            }
            }
	    }
	    //遊戲結束
	    System.out.println("Bonus game over");
	    //finish line*/
	}
}