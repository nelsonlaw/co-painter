import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;
import java.util.*;


class Path implements Serializable{
    Color penColor;
    int penSize;
    ArrayList<Point> points = new ArrayList<Point>();
    public void addPoints(Point point){
        points.add(point);

    }
    public ArrayList<Point> getPoints(){
        return points;
    }
    public void penSetter(Color nowColor, int nowSize){
        penColor = nowColor;
        penSize = nowSize;
    }
    public int penSizeGetter(){
        return penSize;
    }
    public Color penColorGetter(){
        return penColor;
    }
}

public class DrawPath {
    //ArrayList<Point> points = new ArrayList<Point>();
    //awt.List paths = Collections.synchronizedList(new ArrayList<Path>());
    ArrayList<Path> paths = new ArrayList<Path>();
    Path tempPath = new Path();
    int state;
    String host;
    int port;
    Color nowColor = Color.black;
    int nowSize = 20;
    MyPanel p;
    JFrame frame;
    ClientConnecter cc;
    ClientSync cs;
    ClientBroadcaster cb;

    DrawPath(int stateNum, String hostName, String portNum){
        state = stateNum;
        host = hostName;
        port = Integer.parseInt(portNum);
        //DrawPath dp = new DrawPath(state,host,port);
        //dp.go();

    }
    public static void main( String[] args) {
    	//DrawPath dp = new DrawPath(state,host,port);
    		
    }

    public void go() {

    	frame = new JFrame();
    	frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
     	p = new MyPanel();
        JPanel buttons = new JPanel();
        frame.getContentPane().add(p);

        JMenuBar MenuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Action");
        JMenuItem menuitem1 = new JMenuItem("Clear");
        JMenuItem menuitem2 = new JMenuItem("Save");
        JMenuItem menuitem3 = new JMenuItem("Load");
        JMenuItem menuitem4 = new JMenuItem("Exit");
        frame.setJMenuBar(MenuBar);
        MenuBar.add(menu1);
        menu1.setMnemonic(KeyEvent.VK_A);
        menu1.add(menuitem1);
        menuitem1.setMnemonic(KeyEvent.VK_C);
        menuitem1.setActionCommand("clear");
        menuitem1.addActionListener(new PenListener());
        menu1.add(menuitem2);
        menuitem2.setMnemonic(KeyEvent.VK_S);
        menuitem2.setActionCommand("save");
        menuitem2.addActionListener(new PenListener());
        menu1.add(menuitem3);
        menuitem3.setMnemonic(KeyEvent.VK_L);
        menuitem3.setActionCommand("load");
        menuitem3.addActionListener(new PenListener());        
        menu1.add(menuitem4);
        menuitem4.setMnemonic(KeyEvent.VK_E);
        menuitem4.setActionCommand("exit");
        menuitem4.addActionListener(new PenListener());
        if(state == 1) {
            menuitem1.setEnabled(false);
            menuitem3.setEnabled(false);
        }


        buttons.setLayout(new GridLayout( 1, 9, 0, 10)); 
        frame.getContentPane().add(BorderLayout.SOUTH,buttons);

        JButton buttonBlack = new JButton();
        buttonBlack.setBackground(Color.BLACK);
        buttonBlack.setPreferredSize(new Dimension(40, 40));
        buttonBlack.setActionCommand("black");
        buttonBlack.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonBlack);

        JButton buttonRed = new JButton();
        buttonRed.setBackground(Color.RED);
        buttonRed.setPreferredSize(new Dimension(40, 40));
        buttonRed.setActionCommand("red");
        buttonRed.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonRed);

        JButton buttonGreen = new JButton();
        buttonGreen.setBackground(Color.GREEN);
        buttonGreen.setPreferredSize(new Dimension(40, 40));
        buttonGreen.setActionCommand("green");
        buttonGreen.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonGreen);

        JButton buttonBlue = new JButton();
        buttonBlue.setBackground(Color.BLUE);
        buttonBlue.setPreferredSize(new Dimension(40, 40));
        buttonBlue.setActionCommand("blue");
        buttonBlue.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonBlue);

        JButton buttonWhite = new JButton();
        buttonWhite.setBackground(Color.WHITE);
        buttonWhite.setPreferredSize(new Dimension(40, 40));
        buttonWhite.setActionCommand("white");
        buttonWhite.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonWhite);

        TinyButton buttonTiny = new TinyButton();
        buttonTiny.setBackground(Color.WHITE);
        buttonTiny.setPreferredSize(new Dimension(40, 40));
        buttonTiny.setActionCommand("tiny");
        buttonTiny.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonTiny);

        SmallButton buttonSmall = new SmallButton();
        buttonSmall.setBackground(Color.WHITE);
        buttonSmall.setPreferredSize(new Dimension(40, 40));
        buttonSmall.setActionCommand("small");
        buttonSmall.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonSmall);

        MediumButton buttonMedium = new MediumButton();
        buttonMedium.setBackground(Color.WHITE);
        buttonMedium.setPreferredSize(new Dimension(40, 40));
        buttonMedium.setActionCommand("medium");
        buttonMedium.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonMedium);

        HugeButton buttonHuge = new HugeButton();
        buttonHuge.setBackground(Color.WHITE);
        buttonHuge.setPreferredSize(new Dimension(40, 40));
        buttonHuge.setActionCommand("huge");
        buttonHuge.addActionListener(new PenListener());
        buttons.add(BorderLayout.SOUTH,buttonHuge);
	
        p.addMouseListener(p);
        p.addMouseMotionListener(p);
    	frame.setSize( 600, 600);
    	frame.setVisible( true);
        if(state == 0){
            Thread clienthandler = new Thread(new ClientHandler());
            clienthandler.start();
        }else if(state == 1){
            try{
                Socket sock;
                sock = new Socket(host, port);
                cc = new ClientConnecter(sock);
                Thread t = new Thread(cc);
                t.start();
                cs = new ClientSync(sock);
                Thread t2 = new Thread(cs);
                t2.start();
                System.out.println("Networking established");
            } catch (IOException e) {
                //e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Unable to connect to host!","Fail to start",JOptionPane.ERROR_MESSAGE);
                CoPainter cp = new CoPainter();
                cp.go();
                frame.setVisible(false);
                frame.dispose();

            }
        }
    }
    
    class WriterList extends ArrayList<ObjectOutputStream> {

        public synchronized boolean add(ObjectOutputStream w) {  return super.add(w);  }

        public synchronized boolean remove(ObjectOutputStream w) {  return super.remove(w);  }
    }

    public synchronized void pathModification(ArrayList<Path> toPaths){
        paths = toPaths;
    }

    public class ClientHandler implements Runnable {

        public ClientHandler(){

        }

        public void run() {

            System.out.println( "Server starts ...");
            try {
                ServerSocket ss = new ServerSocket(port);
                cb = new ClientBroadcaster();

                while (true) {
                    Socket s = ss.accept();
                    
                    cb.setupNewGuest(s);
                    Thread tl = new Thread(new ClientListener(s));
                    tl.start();
                }
            } catch (Exception e) { 
                JOptionPane.showMessageDialog(null, "Unable to listen to port "+port,"Fail to start",JOptionPane.ERROR_MESSAGE);                     
                CoPainter cp = new CoPainter();
                cp.go();
                frame.setVisible(false);
                frame.dispose();
                }
        }
    }
    public class ClientListener implements Runnable {
        ObjectInputStream reader;
        Socket client;
        public ClientListener(Socket sock){
            client = sock;

        }
        public void run() {
        	try {
                reader = new ObjectInputStream(client.getInputStream());
            } catch (Exception e) { System.out.println( "Something is wrong about ObjectInputStream in ClientListener"); }
            try {
                while (true) {
                    //message=reader.readLine();
                    /*
                    if (message.equals("logout") ) {
                        clientOutputStreams.remove(w);
                        System.out.println( "No. of remaining clients: " +
                                clientOutputStreams.size());
                        return; 

                    }
                    else {  
                        System.out.println("Received: " + message);
                        tellEveryone( clientID + ": " + message);
                    }
                    */
                    pathModification((ArrayList<Path>) reader.readObject());
                    System.out.println(paths);
                    cb.tellEveryone(paths);
                    p.repaint();
                    //Thread.sleep(1000);
                }
            } catch (Exception e) { System.out.println( "Stop listening on that guest.");}
        }
    }

    

    public class ClientBroadcaster {
        WriterList clientOutputStreams;  

        //PrintWriter w;    //used in removal as an ID

        Socket client;

        public ClientBroadcaster() {
        }
        public void setupNewGuest(Socket sock) {
        	client = sock;
            //String message;
            clientOutputStreams = new WriterList();
            //System.out.println( "Server starts ...");
                    //System.out.println( "ChatServer starts ...");
            try {
                ObjectOutputStream ww= new ObjectOutputStream(client.getOutputStream());
                clientOutputStreams.add(ww);
             } catch (Exception e) { System.out.println( "Something is wrong about ObjectOutputStream in ClientBroadcaster"); }   

            //Thread t = new Thread(new ClientHandler());
            //t.start();

            //Random rand = new Random();

            System.out.println("New guest enters ...");
            /*
            try {
                while (true) {
                    //message=reader.readLine();
                    /*
                    if (message.equals("logout") ) {
                        clientOutputStreams.remove(w);
                        System.out.println( "No. of remaining clients: " +
                                clientOutputStreams.size());
                        return; 

                    }
                    else {  
                        System.out.println("Received: " + message);
                        tellEveryone( clientID + ": " + message);
                    }
                    /
                    tellEveryone(paths);
                    //Thread.sleep(1000);
                    
                }
            } catch (Exception e) { System.out.println( "A guest leaved.");
            */
        }

        

        public void tellEveryone(ArrayList<Path> paths) {

            for ( ObjectOutputStream w: clientOutputStreams) {
            //for (Iterator<ObjectOutputStream> iw = clientOutputStreams.iterator(); iw.hasNext();){
                try {
                    //ObjectOutputStream w = iw.next();
                    ArrayList<Path> avoidCrashPaths = new ArrayList<Path>(paths);
                    w.writeObject(avoidCrashPaths);
                    w.flush();
                    w.reset();
                    //System.out.println("Uploaded");
                } catch (ConcurrentModificationException e) { System.out.println( "You are editing too fast.");}
                catch (Exception e) { clientOutputStreams.remove(w);}
                //System.out.println( message);
            }


        }
    }
    

    public class ClientConnecter implements Runnable {
        //ObjectInputStream reader;    //Used in this version
        ObjectOutputStream writer;
        Socket sock;
        ArrayList<Path> serverPath;

        public ClientConnecter(Socket client) {
            sock = client;
            try{
                //reader = new ObjectInputStream(sock.getInputStream());
                writer = new ObjectOutputStream(sock.getOutputStream());
                
            }catch (Exception e){ System.out.println( "Something is wrong about ObjectOutputStream or ObjectInputStream in ClientConnecter");}
        }
        public void run() {
            
        }
        public void updateToServer(){
            try{
            	//System.out.println("uploading");
                ArrayList<Path> avoidCrashPaths = new ArrayList<Path>(paths);
                writer.writeObject(avoidCrashPaths);                    
                writer.flush();
                writer.reset();
                //System.out.println("uploaded");
            }catch(Exception error){System.out.println( "Updating to server failed.");}
        }
        
    }

    public class ClientSync implements Runnable {
        ObjectInputStream reader;    //Used in this version
        //ObjectOutputStream writer;
        Socket sock;
        ArrayList<Path> serverPath;

        public ClientSync(Socket client) {
            sock = client;
            try{
                reader = new ObjectInputStream(sock.getInputStream());
                //writer = new ObjectOutputStream(sock.getOutputStream());
                
            }catch (Exception e){ System.out.println( "Something is wrong about ObjectOutputStream or ObjectInputStream in ClientSync");}
        }
        public void run() {
            //sock = new Socket(host, port);
        	
            try {
                
                while (true) { 
                    /*
                    message = reader.readLine();
                    System.out.println("Read: " + message);
                    incoming.append( message + "\n");
                    */
                    ArrayList<Path> avoidCrashPaths = new ArrayList<Path>((ArrayList<Path>)reader.readObject());
                    pathModification((ArrayList<Path>) avoidCrashPaths);
                    //System.out.println("Object received = " + serverPath);
                    //paths = serverPath;
                    p.repaint();
                    //System.out.println("Downloaded");
                    //System.out.println(paths);
                    //Thread.sleep(100);
                    /*
                    try{
		        		sleepWaitingForUpload(5000);
		        	}catch(Exception e){
		        		System.out.println("Thread is busy.");
		        	}
		        	*/
                }
               
            } catch (Exception e) { JOptionPane.showMessageDialog(null, "Host is gone!","Connection dropped",JOptionPane.ERROR_MESSAGE);System.exit(0); }
            
        }
        /*
        public void sleepWaitingForUpload(int millisecond){
        	try{
        		t2.sleep(millisecond);
        	}catch(Exception e){
        		System.out.println("Thread is busy.");
        	}
        }
        */
    }

    class MyPanel extends JPanel implements MouseListener, MouseMotionListener {   //An inner class
    	public void paintComponent( Graphics g) {
        	g.setColor(Color.white);   //Erase the previous figures
        	g.fillRect(0, 0, getWidth(), getHeight());
    		g.setColor(Color.black);
            //g.setColor(NowColor);
    		//if(g instanceof Graphics2D) {
    			Graphics2D g2D = (Graphics2D) g;
    			g2D.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    		//}
    		Point prevPoint = null;
            for (Path pt: paths) {
                g.setColor(pt.penColorGetter());
                g2D.setStroke(new BasicStroke(pt.penSizeGetter(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        		for (Point p: pt.getPoints()) {
                    /*
                    if(p.x == -1 && p.y == -2) {
                        g.setColor(Color.black);
                        continue;
                    }
                    if(p.x == -1 && p.y == -3) {
                        g.setColor(Color.red);
                        continue;
                    }
                    if(p.x == -1 && p.y == -4) {
                        g.setColor(Color.green);
                        continue;
                    }
                    if(p.x == -1 && p.y == -5) {
                        g.setColor(Color.blue);
                        continue;
                    }
                    if(p.x == -1 && p.y == -6) {
                        g.setColor(Color.white);
                        continue;
                    }
                    
                    if(p.x == -1 && p.y == -7) {
                        //JOptionPane.showMessageDialog(null,"haha");
                        g2D.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        continue;
                    }
                    if(p.x == -1 && p.y == -8) {
                        g2D.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        continue;
                    }
                    if(p.x == -1 && p.y == -9) {
                        g2D.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        continue;
                    }
                    if(p.x == -1 && p.y == -10) {
                        g2D.setStroke(new BasicStroke(30, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        continue;
                    }
                    */
                    if(p.x == -1 && p.y == -1) {
                        prevPoint = null;
                        continue;
                    }
                    
            			if (prevPoint != null) {
            				g.drawLine(prevPoint.x, prevPoint.y, p.x, p.y);
            			}
            			prevPoint = p;
  		        }
            }
            for (Point p: tempPath.getPoints()) {
                g.setColor(nowColor);
                g2D.setStroke(new BasicStroke(nowSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                if(p.x == -1 && p.y == -1) {
                    prevPoint = null;
                    continue;
                }
                
                    if (prevPoint != null) {
                        g.drawLine(prevPoint.x, prevPoint.y, p.x, p.y);
                    }
                    prevPoint = p;
            }
    	}
    	@Override
    	public void mouseDragged(MouseEvent event) {
    		tempPath.addPoints(event.getPoint());
    		repaint();
    	}
    	@Override
    	public void mouseMoved(MouseEvent event) {
    	}
    	@Override
    	public void mouseClicked(MouseEvent event) {
    	}
    	@Override
    	public void mouseEntered(MouseEvent event) {
    	}
    	@Override
    	public void mouseExited(MouseEvent event) {
    	}
    	@Override
    	public void mousePressed(MouseEvent event) {

            tempPath.penSetter(nowColor,nowSize);
    		tempPath.addPoints(event.getPoint());
    		repaint();
    	}
    	@Override
    	public void mouseReleased(MouseEvent event) {
    		//if(state == 1) cc.manualSync();
    		
            tempPath.addPoints(new Point(-1,-1));
            paths.add(tempPath);
            repaint();
            if(state == 1) {
            	cc.updateToServer();
            	//cs.sleepWaitingForUpload(5000);
            }else if(state == 0){
            	cb.tellEveryone(paths);
            }
            tempPath = new Path();
           

    	}
    }
    public class PenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            if(action == "black") nowColor = Color.BLACK;
            if(action == "red") nowColor = Color.RED;
            if(action == "green") nowColor = Color.GREEN;
            if(action == "blue") nowColor = Color.BLUE;
            if(action == "white") nowColor = Color.WHITE;
            if(action == "tiny") nowSize = 5;
            if(action == "small") nowSize = 10;
            if(action == "medium") nowSize = 20;
            if(action == "huge") nowSize = 30;
            if(action == "clear"){paths = new ArrayList<Path>(); p.repaint();}
            if(action == "save") {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showSaveDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try{
                        FileOutputStream f = new FileOutputStream(chooser.getSelectedFile());
                        ObjectOutputStream os = new ObjectOutputStream(f);
                        os.writeObject(paths);
                        os.close();
                    }catch(Exception error){
                        System.out.println( "Saving failed.");
                    }
                   
                }
            };
            if(action == "load") {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showSaveDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try{
                        FileInputStream f = new FileInputStream(chooser.getSelectedFile());
                        ObjectInputStream os = new ObjectInputStream(f);
                        Object tp = os.readObject();
                        //paths = new ArrayList<Path>((ArrayList<Path>) tp);
                        paths = (ArrayList<Path>) tp;
                        p.repaint();
                        os.close();
                    }catch(Exception error){
                        System.out.println( "Loading failed.");
                    }
                   
                }
            };
            if(action == "exit") System.exit(0);
            /*
            if(action == "black") points.add(new Point(-1,-2));
            if(action == "red") points.add(new Point(-1,-3));
            if(action == "green") points.add(new Point(-1,-4));
            if(action == "blue") points.add(new Point(-1,-5));
            if(action == "white") points.add(new Point(-1,-6));
            if(action == "tiny") points.add(new Point(-1,-7));
            if(action == "small") points.add(new Point(-1,-8));
            if(action == "medium") points.add(new Point(-1,-9));
            if(action == "huge") points.add(new Point(-1,-10));
            */

            
        }
    }
    class TinyButton extends JButton {
        public void paintComponent(Graphics g) {
                g.setColor(Color.black);
                g.fillOval(getWidth()/2-2, getHeight()/2-2, 5, 5);
            }
    }
    class SmallButton extends JButton {
        public void paintComponent(Graphics g) {
                g.setColor(Color.black);
                g.fillOval(getWidth()/2-5, getHeight()/2-5, 10, 10);
            }
    }
    class MediumButton extends JButton {
        public void paintComponent(Graphics g) {
                g.setColor(Color.black);
                g.fillOval(getWidth()/2-10, getHeight()/2-10, 20, 20);
            }
    }
    class HugeButton extends JButton {
        public void paintComponent(Graphics g) {
                g.setColor(Color.black);
                g.fillOval(getWidth()/2-15, getHeight()/2-15, 30, 30);
            }
    }

}
