import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.sourceforge.yamlbeans.YamlException;
import net.sourceforge.yamlbeans.YamlReader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	private static final String SURVEY = "survey";		
	private HashMap<String, ArrayList<String>> dataholder = new HashMap<String, ArrayList<String>>();
	private HashMap<Map.Entry<String, ArrayList<String>>, JButton> selectedans = new HashMap<Map.Entry<String, ArrayList<String>>, JButton>();
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
            // storing question and button group of answers in dataholder
            this.dataholder.put(quest, ans);
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
		while(it.hasNext()){
			// get question and answer data
			Map.Entry<String, ArrayList<String>> q = (Map.Entry<String, ArrayList<String>>)it.next();
			ArrayList<String> a = q.getValue();
			// create question label
			JLabel label = new JLabel(q.getKey());
			JPanel panel = new JPanel();
			panel.add(label);
			// create answer buttons
			for(int i=0; i < a.size(); i++){
				JButton btn = new JButton(a.get(i));
				btn.addActionListener(new SelectAction());
				if(btn.isSelected()){
			        selectedans.put(q,btn);
			        System.out.println(selectedans);
				}
				else{
					System.out.println("Test Marker");
				}
				panel.add(btn);
			}
			// add all to main panel
			panelmain.add(panel);
		}
		this.frame.add(panelmain);
		// SUBMIT
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitAction());
		panelmain.add(submit);
	}
	public class SubmitAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have submitted your answer");
		}
	}
	
	public class SelectAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected");
	        

	        
		}
	}
}







