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
	private static final String TYPE = "type";
	private static final String SETTING = "settings";
	private static final String SURVEY = "survey";		
	private HashMap<String, > dataholder = new HashMap<String, ArrayList>();
	private HashMap<String, String> selectedans = new HashMap<String, String>();
	private HashMap<String, Integer> selectedans2 = new HashMap<String, Integer>();
	private JFrame frame;
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
            String type = (String)surv.get(i).get(TYPE);
            
            ArrayList<Integer> setting = (ArrayList<Integer>)surv.get(i).get(SETTING);
            // storing question and button group of answers in dataholder
            this.dataholder.put(quest, ans);
            this.dataholder2.put(quest, type);
            this.dataholder3.put(quest, setting);

            
		}
	}
	
	
	public void run(){
		//set up features/attribute of jframe
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(600,300);
		this.frame.setLocationRelativeTo(null);
		this.frame.setLayout(new BorderLayout());
		this.frame.setVisible(true);
		
		
		// SURVEY
		JPanel panelmain = new JPanel();
		// iterate through dataholder
		Iterator it = this.dataholder.entrySet().iterator();
		Iterator it2 = this.dataholder2.entrySet().iterator();
		Iterator it3 = this.dataholder3.entrySet().iterator();
		while(it.hasNext()){
			// get question and answer data
			
			Map.Entry<String, ArrayList<String>> q = (Map.Entry<String, ArrayList<String>>)it.next();
			ArrayList<String> a = q.getValue();
			Map.Entry<String, String> q2 = (Map.Entry<String, String>)it2.next();
			String t = q2.getValue();
			Map.Entry<String, ArrayList<Integer>> q3 = (Map.Entry<String, ArrayList<Integer>>)it3.next();
			ArrayList<Integer> s = (ArrayList<Integer>)q3.getValue();
			Integer test = 
			System.out.println(s);
			
			// create question label
			JLabel label = new JLabel(q.getKey());
			JPanel panel = new JPanel();
			panel.add(label,0);
			// create answer buttons
			for(int i=0; i < a.size(); i++){
				if(t.equals("multi")){

					JButton btn = new JButton(a.get(i));
					btn.addActionListener(new SelectAction());
					
					panel.add(btn);
					
				}
				else if(t.equals("single")){
					JRadioButton btn = new JRadioButton(a.get(i));
					btn.addActionListener(new SelectRadioAction());
					
					panel.add(btn);
					
					
				}
				else if(t.equals("check")){
					JCheckBox btn = new JCheckBox(a.get(i));
					btn.addActionListener(new SelectCheckAction());
					
					panel.add(btn);
					
					
				}
				
				
			}
			if(t.equals("slide")){
				JSlider slide = new JSlider(JSlider.HORIZONTAL, q3.get(0), q3.getValue().get(1), q3.getValue().get(2));
				panel.add(slide);
				slide.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e){
						JSlider slide = (JSlider)e.getSource();
						JPanel panel = (JPanel)slide.getParent();
				        String q = ((JLabel)panel.getComponent(0)).getText();
				        selectedans2.put(q,slide.getValue()); 
				        System.out.println(selectedans2);
					}
					
					
				});
			}
			// add all to main panel
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
	        selectedans.put(q,a); 
	        System.out.println(selectedans);
		}
	}
	
	public class SelectRadioAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JRadioButton btn= (JRadioButton)e.getSource();
	        String a = btn.getText();
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        selectedans.put(q,a); 
	        System.out.println(selectedans);
		}
	}
	public class SelectCheckAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JCheckBox btn= (JCheckBox)e.getSource();
	        String a = btn.getText();
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        selectedans.put(q,a); 
	        System.out.println(selectedans);
		}
	}

	}







