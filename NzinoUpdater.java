package nzinosUpdate;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NzinoUpdater {
	private static boolean clock = false;
	
	public void standardProcedure() {
	
	JOptionPane.showMessageDialog(null, "Nzino's patcher updater - you're welcome", "InfoBox: " + "Titolo", JOptionPane.INFORMATION_MESSAGE);
	File f = new File(""); //
	String path = f.getAbsolutePath(); //
	File dir = new File(path); //
	dir.getClass();

	File modFolder = new File(path+"\\mods");

	//lista mod
	File[] modList = modFolder.listFiles();
	
	String[] modAndGitList = modFolder.list();

	//list nomi delle mod
	String[] modNameList = new String [modList.length];

	for (int i=0; i<modList.length;i++) {
		modNameList[i] = modList[i].getName();
	}
	
	boolean isGit=false;
	for(int i=0; i< modAndGitList.length;i++) {
		if(modAndGitList[i].equals(".git"))
			isGit=true;
	}

	ConsolinaFake cf = new ConsolinaFake();
	
	
	ProcessBuilder pb=null;

	if(modList.length==0) {
		JOptionPane.showMessageDialog(null, "First path - cloning incoming...", "InfoBox: " + "Titolo", JOptionPane.INFORMATION_MESSAGE);
		pb = new ProcessBuilder("git", "clone", "https://github.com/khamdra1/SweetHood.git", ".");
		pb.directory(modFolder);
		
		Process p;
		try {
			p = pb.start();
			addListener(p,cf);
			checkProcess(p,cf);
			errorFromGit(p,cf);
		} catch (Exception e) {
			String error = e.getStackTrace().toString();
			JOptionPane.showMessageDialog(null, error, "FAILED TO SAVE", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}else if(isGit){
		JOptionPane.showMessageDialog(null, "Second path pull incoming...", "InfoBox: " + "Titolo", JOptionPane.INFORMATION_MESSAGE);
		pb = new ProcessBuilder("git", "pull" , "origin", "main");
		pb.directory(modFolder);
		try {
		Process p = pb.start();
		addListener(p,cf);
		checkProcess(p,cf);
		errorFromGit(p,cf);
		}catch (Exception e ) {
			String error = e.getStackTrace().toString();
			JOptionPane.showMessageDialog(null, error, "FAILED TO SAVE", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}else {
		
		JOptionPane.showMessageDialog(null, "Third path - setup incoming... ok?", "InfoBox: " + "Titolo", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, "OK", "OK", JOptionPane.INFORMATION_MESSAGE);
		ProcessBuilder pb2= new ProcessBuilder("git", "init");
		ProcessBuilder pb3= new ProcessBuilder("git", "checkout", "-b", "main");
		ProcessBuilder pb4= new ProcessBuilder("git", "remote", "add", "origin", "https://github.com/khamdra1/SweetHood.git");
		ProcessBuilder pb5 = new ProcessBuilder("git", "fetch", "--all");
		ProcessBuilder pb6 = new ProcessBuilder("git", "reset", "--hard", "origin/main");
		pb2.directory(modFolder);
		pb3.directory(modFolder);
		pb4.directory(modFolder);
		pb5.directory(modFolder);
		pb6.directory(modFolder);
	
		try {
		
		Process p2 = pb2.start();
		addListener(p2,cf);
		checkProcess(p2,cf);
		listErrorFromGit(p2,cf);
		Process p3 = pb3.start();
		addListener(p3,cf);
		checkProcess(p3,cf);
		listErrorFromGit(p3,cf);
		Process p4 = pb4.start();
		addListener(p4,cf);
		checkProcess(p4,cf);
		listErrorFromGit(p4,cf);
		Process p5 = pb5.start();
		addListener(p5,cf);
		checkProcess(p5,cf);
		listErrorFromGit(p5,cf);
		Process p6 = pb6.start();
		addListener(p6,cf);
		checkProcess(p6,cf);
		listErrorFromGit(p6,cf);
	
	}catch(Exception e ) {
		String error = e.getStackTrace().toString();
		JOptionPane.showMessageDialog(null, error, "FAILED TO SAVE", JOptionPane.INFORMATION_MESSAGE);
	}
	}

	modAndGitList = modFolder.list();
	
	if(modAndGitList.length>1) {
		JOptionPane.showMessageDialog(null, "OK", "OK", JOptionPane.INFORMATION_MESSAGE);
	}else {
		JOptionPane.showMessageDialog(null, "Error Occurred (?)", "InfoBox: " + "Titolo", JOptionPane.INFORMATION_MESSAGE);
	}
	
	cf.dismiss();

}
	
	protected static class ConsolinaFake {
		
		JPanel panelConsolina;
		JFrame frameConsolina;
		JTextArea areaConsolina;
		
		 protected JPanel createPanel() {
		        panelConsolina = new JPanel(new GridLayout());
		        //panel.setBorder(BorderFactory.createTitledBorder("Description"));
		        areaConsolina = new JTextArea(30, 60);
		        panelConsolina.add(new JScrollPane(areaConsolina));
		        areaConsolina.setLineWrap(true);
		        areaConsolina.setWrapStyleWord(true);
		        areaConsolina.setText("Welcome!");
		        return panelConsolina;
		    }

		    protected void display() {
		        frameConsolina = new JFrame("Frame Consolina");
		        frameConsolina.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frameConsolina.setContentPane(createPanel());
		        frameConsolina.pack();
		        frameConsolina.setLocationRelativeTo(null);
		        frameConsolina.setVisible(true);
		    }
		    
		    protected void dismiss() {
		    	frameConsolina.setVisible(false); //you can't see me!
		    	frameConsolina.dispose();
		    }
		
		protected ConsolinaFake() {
			display();
		}
		
		protected void updateConsolina(String line) {
			String oldText = areaConsolina.getText();
			areaConsolina.setText(oldText+"\n"+line);
		}
		
		protected void inLineUpdateConsolina(String line) {
			String oldText = areaConsolina.getText();
			areaConsolina.setText(oldText+line);
		}
	}
	
	public void checkProcess(Process p, ConsolinaFake consolina) throws InterruptedException {
		
		consolina.updateConsolina("Executing");
		
		while(p.isAlive()) {
			Thread.sleep(1000);
			if(clock) {
			consolina.inLineUpdateConsolina(".");
			clock=false;
			}
			else {
				consolina.inLineUpdateConsolina(".");
				clock=true;
			}
		}
		consolina.updateConsolina("Done");
	}
	
	public void errorFromGit(Process p, ConsolinaFake consolina) throws InterruptedException, IOException {
		
		BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
		StringBuffer s = new StringBuffer("");
		String line = "";
		while ((line = bfr.readLine()) != null) {
			s.append(line);
		}
//		JOptionPane.showMessageDialog(null, s.toString(), "Results from Git:", JOptionPane.INFORMATION_MESSAGE);
		consolina.updateConsolina(s.toString());
		
	}
	
	public void listErrorFromGit(Process p, ConsolinaFake consolina) throws InterruptedException, IOException {	
	BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
	List<String> bufferone= new ArrayList<String>();
	String line = "";
	while ((line = bfr.readLine()) != null) {
		if(line.length()>4)
		bufferone.add(line);
	}
//	JOptionPane.showMessageDialog(null, bufferone.get(0), "Results from Git:", JOptionPane.INFORMATION_MESSAGE);
	for (int i=0;i<bufferone.size();i++) {
	consolina.updateConsolina(bufferone.get(i));
	}
	bfr.close();
	}
	
	public void addListener(Process p, ConsolinaFake consolina) {
		consolina.frameConsolina.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	Iterator<ProcessHandle> it = p.descendants().iterator();
            	while(it.hasNext()) {
            		ProcessHandle ph = it.next();
            		ph.destroyForcibly();
            	}
                p.destroyForcibly();
                consolina.dismiss();
                try {
					p.waitFor();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
            }
        });
	}

	
	
		
	}