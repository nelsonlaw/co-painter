import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CoPainter {
	JTextField hostText;
	JTextField portText;
	JFrame frame;
	public static void main( String[] args) {
    	CoPainter cp = new CoPainter();
    	cp.go();	
    }

    public void go() {
    	frame = new JFrame();
    	frame.setTitle("Collaborative Painter");
    	frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
     	JPanel p = new JPanel();
     	p.setLayout(new GridLayout( 3, 2, 0, 2));
     	JLabel host = new JLabel("Host:");
     	hostText =  new JTextField(20);
        hostText.setText("192.168.0.100");
     	JLabel port = new JLabel("Port:");
     	portText =  new JTextField(20);
        portText.setText("5000");
     	JButton startHost = new JButton("Start as a host");
     	startHost.setActionCommand("host");
        startHost.addActionListener(new buttonListener());
     	JButton connectHost = new JButton("Connect to a host");
     	connectHost.setActionCommand("guest");
        connectHost.addActionListener(new buttonListener());
     	p.add(host);
     	p.add(hostText);
     	p.add(port);
     	p.add(portText);
     	p.add(startHost);
     	p.add(connectHost);
        //JPanel buttons = new JPanel();
        frame.getContentPane().add(p);
        frame.setSize( 300, 110);
    	frame.setVisible( true);
    }
    public class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
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
            if(action == "host") {
            	DrawPath dp = new DrawPath(0,hostText.getText(),portText.getText());
            	dp.go();
            	frame.setVisible( false);
                frame.dispose();
            };
            if(action == "guest") {
            	DrawPath dp = new DrawPath(1,hostText.getText(),portText.getText());
            	dp.go();
            	frame.setVisible( false);
                frame.dispose();
            };
        }
    }
}