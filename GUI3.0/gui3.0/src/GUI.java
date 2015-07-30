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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class GUI {
	private static final String ANSWER = "answer";
	private static final String QUESTION = "question";
	private static final String TYPES = "type";
	private static final String SURVEY = "survey";		
	//private HashMap<String, String> selectedans = new HashMap<String, String>();
	//private HashMap<String, Integer> selectedans2 = new HashMap<String, Integer>();
	private ArrayList<Entry> data = new ArrayList<Entry>();
	private JFrame frame;
	private ArrayList<?> list;
	private Map yaml;
	public GUI(String yamlfile) throws FileNotFoundException, YamlException{
		// create frame for class
		this.frame = new JFrame("Annotation Framework");
		
		// read in yaml file
		YamlReader reader = new YamlReader(new FileReader(yamlfile));
		Object object = reader.read();
	    this.yaml = (Map)object;
	    
	    // parse yaml data and store
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
		this.frame.setVisible(true);
		
		JPanel panelmain = new JPanel();
		
	
		for(Entry aEntry : data) {
			// create question label
			JLabel label = new JLabel(aEntry.getQuestion());
			JPanel panel = new JPanel();
			panel.add(label,0);
			
			//Create buttons
			//Slider?
			if (aEntry.getType().equals(Type.SLIDER)){
				System.out.println(aEntry.getAnswers().get(2));
				int start = Integer.parseInt((String)aEntry.getAnswers().get(0));
				int stop = Integer.parseInt((String)aEntry.getAnswers().get(1));
				int step = Integer.parseInt((String)aEntry.getAnswers().get(2));
				
				JSlider slide = new JSlider(JSlider.HORIZONTAL, start, stop, step);
				panel.add(slide);
				slide.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e){
						JSlider slide = (JSlider)e.getSource();
						JPanel panel = (JPanel)slide.getParent();
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
						JCheckBox btn = new JCheckBox((String)anAnswer);
						btn.addActionListener(new SelectCheckAction());
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
        
		/*
		YamlWriter writer = new YamlWriter(new FileWriter("output.yml"));
    	writer.write(selectedans);
    	writer.close();
		*/
    	
    	//SUBMIT
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitAction());
		panelmain.add(submit);
	}
	public class SubmitAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have submitted your answer");
	        JButton Submit = (JButton)(e.getSource());
	        //Submit.setBackground(Color.GREEN);
	        /*
	        if(selectedans.size()+selectedans2.size()==dataholder.size()){
	        	System.out.println("and congrats you answered everything");
	        	JFrame frame2 = new JFrame("Answer Submitted");
		        JLabel label = new JLabel("Finished");
		        frame2.setVisible(true);
		        frame2.add(label);
		        label.setLocation(100, 50);
		        frame2.setLocationRelativeTo(null);
		        frame2.setSize(200,100);

	        }
	        else{
	        	System.out.println("but slow down you didn't finish dis");	
	        }*/
	     


		}
	}

	
	public class SelectAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JButton btn= (JButton)e.getSource();
	        String a = btn.getText();
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        //btn.setBackground(Color.GREEN);
	        //selectedans.put(q,a); 
	        //System.out.println(selectedans);
		}
	}
	
	public class SelectRadioAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JRadioButton btn= (JRadioButton)e.getSource();
	        String a = btn.getText();
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        //btn.setBackground(Color.GREEN);
	        //selectedans.put(q,a); 
	        //System.out.println(selectedans);
		}
	}
	public class SelectCheckAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JCheckBox btn= (JCheckBox)e.getSource();
	        String a = btn.getText();
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	       //btn.setBackground(Color.GREEN);
	        //selectedans.put(q,a); 
	        //System.out.println(selectedans);
		}
	}

	}







