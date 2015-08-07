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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
	
	private ButtonGroup bgroup = new ButtonGroup(); //radio button groups
	private JFrame frame; //main frame
	private Map yaml; //yaml map
	
	public EntrySet data = new EntrySet(); //holder of all questions, answers, types

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
            
            //after all yaml objects are parsed, read through and place them within data through Entry           
            Entry someQuestion = new Entry(quest, ans, style);
            this.data.add(someQuestion);
     
		}
	}

	
	public void run() {
		JPanel panelmain = new JPanel(); // create main panel

		for (Entry aEntry : this.data.getEntries()) { // for all entries in data
			// create question label
			JLabel label = new JLabel(aEntry.getQuestion());
			JPanel panel = new JPanel();
			panel.add(label, 0);

			// special slider type
			if (aEntry.getType().equals(Type.SLIDER)) {
				JSlider slide = new JSlider(JSlider.HORIZONTAL, 0, (aEntry
						.getAnswers().size() - 1), 1);

				// labels of slider ticks
				slide.setMajorTickSpacing(1);
				slide.setPaintTicks(true);
				Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
				for (int i = 0; i < aEntry.getAnswers().size(); i++) {
					labels.put(i, new JLabel(aEntry.getAnswers().get(i)));

				}
				slide.setLabelTable(labels);
				// TODO set the dimensions dynamically
				slide.setPreferredSize(new Dimension(500, 100));
				slide.setPaintLabels(true);

				panel.add(slide);
				slide.addChangeListener(new ChangeSlideAction());
			} else {
				for (Object anAnswer : aEntry.getAnswers()) { // for all answers
					switch (aEntry.getType()) { // switch through all non-slider types
					case BUTTON: // if type is button...
						JButton btn = new JButton((String) anAnswer);
						btn.addActionListener(new SelectAction());
						panel.add(btn);
						break;
					case CHECK: // if type is check
						JCheckBox check = new JCheckBox((String) anAnswer);
						check.addActionListener(new SelectCheckAction());
						panel.add(check);
						break;
					case RADIO: // if type is radio
						JRadioButton radio = new JRadioButton((String) anAnswer);
						bgroup.add(radio);
						radio.addActionListener(new SelectRadioAction());
						panel.add(radio);
						break;
					case TEXT: // if type is textfield
						JTextField text = new JTextField((String) anAnswer, 50);
						text.addActionListener(new SelectTextAction());
						panel.add(text);
						break;
					}
				}
			}
			panelmain.add(panel);
		}
		// SUBMIT
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitAction());
		panelmain.add(submit);
		
		// layout main panel
		panelmain.setLayout(new BoxLayout(panelmain, BoxLayout.Y_AXIS));
		this.frame.add(panelmain);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		this.frame.pack();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
	}
	
	public class SubmitAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        JButton Submit = (JButton)(e.getSource());
	        Submit.setBackground(Color.GREEN);
	        
	        for (Entry question : data.getEntries()){
	        	if (question.getSelected().isEmpty()){
	        		System.out.println("but you never answered everything.");
	        		break;
	        	}
	        	else{       		
					/* TODO write out to yaml file
					 * try {
						writer = new YamlWriter(new FileWriter("output.yml"));
				        writer.write(data);
				        writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (YamlException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}*/
	        	}
	        }
	     }
		
	}
	
	public class ChangeSlideAction implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			JSlider slide = (JSlider)e.getSource();
			JPanel panel = (JPanel)slide.getParent();
			String q = ((JLabel)panel.getComponent(0)).getText();
			data.get(q).select(slide.getValue());
			System.out.println(data.get(q).getSelected());
		}
	}
	
	public class SelectAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JButton btn= (JButton)e.getSource();
	        String a = btn.getText();
	        JPanel panel = (JPanel)btn.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        data.get(q).select(a);
			System.out.println(data.get(q).getSelected());
		}                                                                                                                                                                                                                                                                                                                                                                                                                  
	}
	
	public class SelectRadioAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JRadioButton radio= (JRadioButton)e.getSource();
			String a = radio.getText();
	        JPanel panel = (JPanel)radio.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        data.get(q).select(a);
			System.out.println(data.get(q).getSelected());
	        
		}
	}
	public class SelectTextAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	        System.out.println("You have selected ");
	        JTextField text= (JTextField)e.getSource();
			String a = text.getText();
	        JPanel panel = (JPanel)text.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        Entry question = data.get(q);
	        question.addAnswer(a);
	        question.select(a);
			System.out.println(data.get(q).getSelected());
	        
		}
	}
	public class SelectCheckAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
	  	    System.out.println("You have selected ");
	        JCheckBox check= (JCheckBox)e.getSource();
	        String a = check.getText();
	        JPanel panel = (JPanel)check.getParent();
	        String q = ((JLabel)panel.getComponent(0)).getText();
	        
	        Entry question = data.get(q);
	        if(question.isSelected(a)){
	        	question.deselect(a);
	        }
	        else{
	        	question.select(a);
	        }

			System.out.println(data.get(q).getSelected());       
		}
	}
	/*
	 * TODO add video file players
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