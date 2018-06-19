package display;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import javazoom.jl.player.Player;
import java.awt.Font;
import java.awt.Color;

public class PlayerGui {

	private JFrame frame;
	private File mp3File;
	private Player player;
	private Thread pThread;
	private String playState;
	private JLabel nowPlayingLab;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					PlayerGui window = new PlayerGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayerGui() {
		initialize();
	}
	
	synchronized void playerPlay(){
		if (playState.equalsIgnoreCase("playing")){
			try{
				player = new Player(new FileInputStream(mp3File));
				nowPlayingLab.setText("Now playing: "+mp3File.getName());
			}catch(Exception exe){
				
			}
			pThread = new Thread(new PlayerThread(player));
			pThread.start();
		}else if (playState.equalsIgnoreCase("paused")){
			pThread.resume();
			playState = "playing";
		}
	}
	
	synchronized void pause(){
		try {
			if (playState.equalsIgnoreCase("playing")){
				pThread.suspend();
				playState = "paused";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		playState = "playing";
		frame = new JFrame();
		frame.setForeground(new Color(64, 64, 64));
		frame.setBounds(100, 100, 318, 310);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		JButton playBtn = new JButton("Play");
		playBtn.setFont(new Font("Papyrus", Font.BOLD, 12));
		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerPlay();
			}
		});
		playBtn.setBounds(206, 164, 86, 96);
		frame.getContentPane().add(playBtn);
		
		JButton stopBtn = new JButton("Stop");
		stopBtn.setFont(new Font("Papyrus", Font.BOLD, 12));
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pThread.stop();
			}
		});
		stopBtn.setBounds(106, 164, 90, 96);
		frame.getContentPane().add(stopBtn);
		
		JButton pauseBtn = new JButton("Pause");
		pauseBtn.setFont(new Font("Papyrus", Font.BOLD, 12));
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pause();
			}
		});
		pauseBtn.setBounds(10, 164, 86, 96);
		frame.getContentPane().add(pauseBtn);
		
		nowPlayingLab = new JLabel("Now playing:");
		nowPlayingLab.setFont(new Font("Papyrus", Font.BOLD, 12));
		nowPlayingLab.setHorizontalAlignment(SwingConstants.CENTER);
		nowPlayingLab.setBounds(10, 90, 282, 63);
		frame.getContentPane().add(nowPlayingLab);
		
		JButton chooseSong = new JButton("Choose next song");
		chooseSong.setFont(new Font("Papyrus", Font.BOLD, 12));
		chooseSong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					JFileChooser fileChooser = new JFileChooser();
					FileFilter filter = new FileFilter(){

						public boolean accept(File file) {
							if (file.isDirectory()){
								return true;
							}else if (fileExtension(file).equalsIgnoreCase("mp3")){
								return true;
							}else{
								return false;
							}
						}

						public String getDescription() {
							// TODO Auto-generated method stub
							return "mp3 files only";
						} 
						
						public String fileExtension(File file){
							String fName = file.getName();
							if (fName.lastIndexOf(".") >0 && fName.lastIndexOf(".") < fName.length()-1){
								return fName.substring(fName.lastIndexOf(".")+1);
							}else{
								return "";
							}
						}					
					};
					fileChooser.addChoosableFileFilter(filter);
					fileChooser.setDialogTitle("Choose mp3 file to play");
					fileChooser.showOpenDialog(null);
					mp3File = fileChooser.getSelectedFile();
				} catch (Exception excep){
					
				}
			}
		});
		chooseSong.setBounds(10, 11, 282, 68);
		frame.getContentPane().add(chooseSong);
	}
}
