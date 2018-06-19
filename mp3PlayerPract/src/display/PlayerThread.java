package display;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class PlayerThread implements Runnable{

	/**
	 * @param args
	 */
	Player player;
	public PlayerThread(Player player){
		this.player = player;
	}
	
	public synchronized void run(){
		try {
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
