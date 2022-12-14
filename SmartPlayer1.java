
package mnkgame;

import java.util.Random;

public class SmartPlayer1  implements MNKPlayer {
	private Random rand;
    private int M;
    private int N;
    private int K;
	private MNKBoard B;
	private MNKGameState myWin;
	private MNKGameState yourWin;
	private int TIMEOUT;

	public SmartPlayer1() {
	}


	public void initPlayer(int M, int N, int K, boolean first, int timeout_in_secs) {
		// New random seed for each game
		rand    = new Random(System.currentTimeMillis()); 
		// Save the timeout for testing purposes
        this.M = M;
        this.N = N;
        this.K = K;
		B       = new MNKBoard(M,N,K);
		myWin   = first ? MNKGameState.WINP1 : MNKGameState.WINP2; 
		yourWin = first ? MNKGameState.WINP2 : MNKGameState.WINP1;
		TIMEOUT = timeout_in_secs;
	}

	public MNKCell selectCell(MNKCell[] FC, MNKCell[] MC) {
        
		if(MC.length > 0) {
			MNKCell c = MC[MC.length-1]; 
			B.markCell(c.i,c.j);         
		}
        else if(MC.length == 0) {
            //se è la prima mossa mette il simbolo al centro
            int m = M/2;
            int n = N/2;
            MNKCell s = new MNKCell(m, n);
			B.markCell(s.i,s.j);
            return s;
        }

		if(FC.length == 1) {
            return FC[0];
        }

		//se può vincere lo fa
		for(MNKCell d : FC) {
			if(B.markCell(d.i,d.j) == myWin) {
				return d;  
			} else {
				B.unmarkCell();
			}
		}


        //se l'altro giocatore può vincere alla prossima mossa lo ferma
		int pos1 = rand.nextInt(FC.length); 
		int pos2 = rand.nextInt(FC.length); 
		while(pos1 == pos2) {
			pos2 = rand.nextInt(FC.length); 
		}
		MNKCell c = FC[pos1]; // mossa a caso
		B.markCell(c.i,c.j); 
		for(int k = 0; k < FC.length; k++) {    
			if(k != pos1) {     
			    MNKCell d = FC[k];
			    if(B.markCell(d.i,d.j) == yourWin) {
			    	B.unmarkCell();        
			    	B.unmarkCell();	       
			    	B.markCell(d.i,d.j);   
			    	return d;							 
			    } else {
			    	B.unmarkCell();	       
			    }	
			}
		}
		B.unmarkCell();	
		MNKCell e = FC[pos2]; 
		B.markCell(e.i,e.j); 
		for(int k = 0; k < FC.length; k++) {    
			if(k != pos2) {     
			    MNKCell d = FC[k];
			    if(B.markCell(d.i,d.j) == yourWin) {
			    	B.unmarkCell();        
			    	B.unmarkCell();	       
			    	B.markCell(d.i,d.j);   
			    	return d;							 
			    } else {
			    	B.unmarkCell();	       
			    }	
			}
		}
		B.unmarkCell();	

		MNKCell t = SelectCloseCell(MC[MC.length-1], FC);
		B.markCell(t.i,t.j); 
		return t;
	}

	public String playerName() {
		return "5m4rt";
	}

	private MNKCell SelectCloseCell(MNKCell c, MNKCell FC[]) {
		int m; int n;
		//cerca una cella valida		
		if(c.i < M-1 && c.j < N-1) {
			m = c.i+1; n = c.j+1;
			//controlla che non sia marcata
			MNKCell f = new MNKCell(m, n);
			for(int k=0; k < FC.length; k++) {
				if(m == FC[k].i && n == FC[k].j) {
        			return f;
				}
			}
		}
		if(c.i > 0 && c.j > 0) {
			m = c.i-1; n = c.j-1;
			//controlla che non sia marcata
			MNKCell f = new MNKCell(m, n);
			for(int k=0; k < FC.length; k++) {
				if(m == FC[k].i && n == FC[k].j) {
        			return f;
				}
			}
		}
		if(c.i < M-1 && c.j > 0) {
			m = c.i+1; n = c.j-1;
			//controlla che non sia marcata
			MNKCell f = new MNKCell(m, n);
			for(int k=0; k < FC.length; k++) {
				if(m == FC[k].i && n == FC[k].j) {
        			return f;
				}
			}
		}
		if(c.i > 0 && c.j < N-1) {
			m = c.i-1; n = c.j+1;
			//controlla che non sia marcata
			MNKCell f = new MNKCell(m, n);
			for(int k=0; k < FC.length; k++) {
				if(m == FC[k].i && n == FC[k].j) {
        			return f;
				}
			}
		}


		if(c.i < M-1) {
			m = c.i+1; n = c.j;
			//controlla che non sia marcata
			MNKCell f = new MNKCell(m, n);
			for(int k=0; k < FC.length; k++) {
				if(m == FC[k].i && n == FC[k].j) {
        			return f;
				}
			}
		}
		if(c.i > 0) {
			m = c.i-1; n = c.j;
			//controlla che non sia marcata
			MNKCell f = new MNKCell(m, n);
			for(int k=0; k < FC.length; k++) {
				if(m == FC[k].i && n == FC[k].j) {
        			return f;
				}
			}
		}
		if(c.j > 0) {
			m = c.i; n = c.j-1;
			//controlla che non sia marcata
			MNKCell f = new MNKCell(m, n);
			for(int k=0; k < FC.length; k++) {
				if(m == FC[k].i && n == FC[k].j) {
        			return f;
				}
			}
		}
		if(c.j < N-1) {
			m = c.i; n = c.j+1;
			//controlla che non sia marcata
			MNKCell f = new MNKCell(m, n);
			for(int k=0; k < FC.length; k++) {
				if(m == FC[k].i && n == FC[k].j) {
        			return f;
				}
			}
		}

		return FC[0];		
	}

}

