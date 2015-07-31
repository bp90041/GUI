import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import net.sourceforge.yamlbeans.YamlException;
import net.sourceforge.yamlbeans.YamlReader;
import net.sourceforge.yamlbeans.YamlWriter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class GUI {
	private static final String ANSWER = "answer";
	private static final String QUESTION = "question";
	private static final String TYPES = "type";
	private static final String SURVEY = "survey";		
	private ButtonGroup bgroup = new ButtonGroup();
	private ArrayList<Entry> data = new ArrayList<Entry>();
	private HashMap<String, ArrayList<String>> enteredans3 = new HashMap<String, ArrayList<String>>();
	private HashMap<String, Integer> enteredans2 = new HashMap<String, Integer>();
	private HashMap<String, String> enteredans = new HashMap<String, String>();
	private ArrayList<String> list1 = new ArrayList<String>();
	private JFrame frame;
	private Map yaml;
	public GUI(String yamlfile) throws FileNotFoundException, YamlException{
		// create frame for class
		this.frame = new JFrame("Annotation Framework");
		
		// read in yaml file
		YamlReader reader = new YamlReader(new FileReader(yamlfile));
		Object object = reader.read();
	    this.yaml = (Map)object;
	    
	    // parse yaml data and storeArrayList<String> list = nArrayList<String> list = new ArrayList<String>();ew ArrayList<String>();
		Object s = this.yaml.get(SURVEY);
		ArrayList<Map<String, Object>> surv = (ArrayList<Map<String, Object>>)s;
		// iterate through questions in survey
		for (int i = 0; i < surv.size(); i++) {
            ArrayList<String> ans = (ArrayList<String>)surv.get(i).get(ANSWER);
            String quest = (String)surv.get(i).get(QUESTION);
            String style = (String)surv.get(i).get(TYPES);
            
            
            
            Entry someQuestion = new Entry(quest, ans, style);
            
            this.data.add(someQuestion);
     
		}
	}
	
	
	public void run(){
		//set up features/attribute of jframe
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(600,300);
		this.frame.setLocationRelativeTo(null);
		this.frame.setLayout(new BorderLayout());

		
		JPanel panelmain = new JPanel();
		
		System.out.println(enteredans);
		for(Entry aEntry : data) {
			// create question label
			JLabel label = new JLabel(aEntry.getQuestion());
			JPanel panel = new JPanel();
			panel.add(label,0);
			
			// TODO make this a switch statement
			//Create buttons
			//Slider?1
			if (aEntry.getType().equals(Type.SLIDER)){
				JSlider slide = new JSlider(JSlider.HORIZONTAL, 0, (aEntry.getAnswers().size()-1), 1);
		        slide.setMajorTickSpacing(1);
				slide.setPaintTicks(true);
		        
		        Hashtable<Integer, JLabel> labels =
		                new Hashtable<Integer, JLabel>();
		        for(int i=0; i<aEntry.getAnswers().size(); i++){
		        	 labels.put(i, new JLabel(aEntry.getAnswers().get(i)));
				      
		        }
		       
		        slide.setLabelTable(labels);
		        // TODO make this dynamic
		        slide.setPreferredSize(new Dimension(550, 60));		 
		        slide.setPaintLabels(true);
		        panel.add(slide);
		        slide.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e){
						JSlider slide = (JSlider)e.getSource();
						JPanel panel = (JPanel)slide.getParent();
						String q = ((JLabel)panel.getComponent(0)).getText();
						// TODO query arraylist data by question and get answer by index
						enteredans2.put(q,slide.getValue());
						System.out.println(enteredans2);
						}
				});
			} else { // Not a slider
				for(Object anAnswer : aEntry.getAnswers()){ 
					// anAnswer is technically an int or string. Only time it is an integer is when the Entry is a slider
					if(aEntry.getType().equals(Type.BUTTON)){
						JButton btn = new JButton((String)anAnswer);
						btn.addActionListener(new SelectAction());
						panel.add(btn);
					} else if(aEntry.getType().equals(Type.CHECK)) {
							JCheckBox btn = new JCheckBox((String)anAnswer);
						btn.addActionListener(new SelectCheckAction());
						panel.add(btn);
					} else if(aEntry.getType().equals(Type.RADIO)) {
						JRadioButton btn = new JRadioButton((String)anAnswer);
						bgroup.add(btn);
						btn.addActionListener(new SelectRadioAction());
						panel.add(btn);
						
					} else {
						System.out.println("Rut Row - we have an error... The entry is not a slider," +
								" but it is also not a button, check, or radio...");
					
					}
				
				}
			}
			
			panelmain.add(panel);
		}
		
		
		this.frame.add(panelmain);
		panelmain.setLayout(new BoxLayout(panelmain, BoxLayout.Y_AXIS));
        
	    	
    	//SUBMIT
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitAction());
		panelmain.add(submit);
		
		this.frame.setVisible(true);
	}
	public class SubmitAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have submitted your answer");
	        JButton Submit = (JButton)(e.getSource());
		
	        Submit.setBackground(Color.GREEN);
	        // TODO check every question is answers by checking new selected attribute of Entry class is not empty
	        if(enteredans.size()+enteredans2.size()+enteredans3.size()==data.size()){
	        	System.out.println("and congrats you answered everything");
	        	JFrame frame2 = new JFrame("Answer Submitted");
		        JLabel label = new JLabel("Finished");
		        
		        frame2.setVisible(true);
		        frame2.add(label);
		        label.setLocation(100, 50);
		        frame2.setLocationRelativeTo(null);
		        frame2.pack();
		        System.out.println(enteredans);
		        System.out.println(enteredans3);
		        System.out.println(enteredans2);
		        
		        YamlWriter writer;
				try {
					writer = new YamlWriter(new FileWriter("output.yml"));
			        writer.write(data);
			        writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (YamlException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		         
	        }
	        else{
	        	System.out.println("but slow down you didn't finish dis");	
	        }
	     

		

		}
	}

	
	public class SelectAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        
	        JButton btn= (JButton)e.getSource();
	        String a = btn.getText();
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        enteredans.put(q,a);
	        System.out.println(enteredans);
	        
	       
		}                                                                                                                                                                                                                                                                                                                                                                                                                  
	}
	
	public class SelectRadioAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JRadioButton btn= (JRadioButton)e.getSource();
			String a = btn.getText();
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        enteredans.put(q,a);
	        System.out.println(enteredans);
	        
		}
	}
	public class SelectCheckAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	  	    System.out.println("You have selected ");
	        JCheckBox btn= (JCheckBox)e.getSource();
	        String a = btn.getText();
	        list1.add(a);
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        if(btn.isSelected() == false){
	        	list1.remove(a);
	        	list1.remove(a);
	        	enteredans3.put(q, list1); 
	        	}
	        else if(btn.isSelected() == true){
	        	enteredans3.put(q,list1);
	        }
	        System.out.println(enteredans3);
	        
	       
		}
	}
	/*
	public class mediaPlayer extends JFrame
    {
        public mediaPlayer()
        {
            setLayout(new BorderLayout());

            //file you want to play
            URL mediaURL = "Hi.mov;//Whatever
            //create the media player with the media url
            Player mediaPlayer = Manager.createRealizedPlayer(mediaURL);
            //get components for video and playback controls
            Component video = mediaPlayer.getVisualComponent();
            Component controls = mediaPlayer.getControlPanelComponent();
            add(video,BorderLayout.CENTER);
            add(controls,BorderLayout.SOUTH);
        }
    }
	*/
}







