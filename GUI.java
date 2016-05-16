import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final int width = 500;
	private static final int height = 300;
	private JTabbedPane tabbedPane;
	private JTextField textField, nameTextField, eventNameTextField, eventTypeTextField, 
					   eventDateTextField, eventLocationTextField, eventPriceTextField;
	private JButton priceQuery, locationQuery, dateQuery, insert, enter, update;
	private DBconnector connect;
	private JLabel customerMessage, adminMessage;
	private boolean passFlag = false;
	private JPasswordField passwordTextField;
	private JPanel searchPanel;
	private JMenuItem menuM, menuF, menuB, menuV, menuC, menuT;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton Delete;
	private StringOperations op;
	
	public GUI() 
	{
		setBackground(new Color(139, 0, 0));
		getContentPane().setBackground(new Color(25, 25, 112));
		mainFrame();
		customerPane();
		searchPane();
		adminPane();
		eventHandlers();
		eventHandlersGeneralSearch();
		connect = new DBconnector();
	}
	
	private void mainFrame()
	{
		setTitle("Bilet Sepeti");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setVisible(true);
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(245, 245, 245));
		getContentPane().add(tabbedPane);
	}
	
	private void customerPane()
	{		
		JPanel customerPanel = new JPanel();
		customerPanel.setBackground(new Color(144, 238, 144));
		customerPanel.setToolTipText("");
		tabbedPane.addTab("Özel Arama", null, customerPanel, null);
		customerPanel.setLayout(null);
		
		JLabel label = new JLabel("Organizasyon ad\u0131 giriniz:");
		label.setBounds(0, 0, 489, 27);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		label.setBackground(Color.BLACK);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		customerPanel.add(label);
		
		customerMessage = new JLabel("Bilet Sepetine ho\u015F geldiniz.");
		customerMessage.setVerticalAlignment(SwingConstants.TOP);
		customerMessage.setBounds(0, 216, 489, 35);
		customerMessage.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		customerMessage.setHorizontalAlignment(SwingConstants.CENTER);
		customerPanel.add(customerMessage);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(144, 238, 144));
		panel_2.setBounds(0, 27, 489, 189);
		customerPanel.add(panel_2);
		panel_2.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField.setBackground(new Color(245, 255, 250));
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(0, 0, 489, 30);
		panel_2.add(textField);
		textField.setColumns(10);
		textField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		priceQuery = new JButton("Fiyat");
		priceQuery.setForeground(new Color(240, 248, 255));
		priceQuery.setBackground(new Color(25, 25, 112));
		priceQuery.setBounds(40, 60, 90, 90);
		priceQuery.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		priceQuery.setFont(new Font("Times New Roman", Font.BOLD, 26));
		panel_2.add(priceQuery);
		
		locationQuery = new JButton("Mekan");
		locationQuery.setForeground(new Color(240, 248, 255));
		locationQuery.setBackground(new Color(25, 25, 112));
		locationQuery.setBounds(350, 60, 90, 90);
		locationQuery.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		locationQuery.setFont(new Font("Times New Roman", Font.BOLD, 26));
		panel_2.add(locationQuery);
		
		dateQuery = new JButton("Tarih");
		dateQuery.setForeground(new Color(240, 248, 255));
		dateQuery.setBackground(new Color(25, 25, 112));
		dateQuery.setBounds(200, 60, 90, 90);
		dateQuery.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		dateQuery.setFont(new Font("Times New Roman", Font.BOLD, 26));
		panel_2.add(dateQuery);
	}
	
	private void adminPane()
	{
		JPanel adminPanel = new JPanel();
		adminPanel.setBackground(new Color(127, 255, 212));
		tabbedPane.addTab("Admin", null, adminPanel, null);
		adminPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(127, 255, 212));
		adminPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_3 = new JLabel("Kullan\u0131c\u0131 ad\u0131:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel.add(lblNewLabel_3);
		
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		nameTextField.setBackground(new Color(245, 255, 250));
		panel.add(nameTextField);
		nameTextField.setColumns(10);
		nameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		JLabel lblNewLabel_2 = new JLabel("     Parola:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel.add(lblNewLabel_2);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		passwordTextField.setBackground(new Color(245, 255, 250));
		panel.add(passwordTextField);
		passwordTextField.setColumns(10);
		passwordTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		enter = new JButton("Giri\u015F\r\n");
		enter.setForeground(new Color(245, 255, 250));
		enter.setBackground(new Color(25, 25, 112));
		enter.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		enter.setFont(new Font("Times New Roman", Font.BOLD, 15));
		panel.add(enter);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(127, 255, 212));
		adminPanel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Etkinlik ad\u0131:\r\n");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(75, 10, 80, 14);
		panel_2.add(lblNewLabel_4);
		
		eventNameTextField = new JTextField();
		eventNameTextField.setBackground(new Color(245, 255, 250));
		eventNameTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		eventNameTextField.setBounds(216, 10, 130, 20);
		panel_2.add(eventNameTextField);
		eventNameTextField.setColumns(10);
		eventNameTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		JLabel lblNewLabel_5 = new JLabel("Etkinlik t\u00FCr\u00FC:");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(75, 42, 80, 14);
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Etkinlik tarihi:");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(75, 74, 100, 14);
		panel_2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Etkinlik mekan\u0131:");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_7.setBounds(75, 106, 120, 14);
		panel_2.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Etkinlik fiyat\u0131:");
		lblNewLabel_8.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_8.setBounds(75, 138, 120, 20);
		panel_2.add(lblNewLabel_8);
		
		eventTypeTextField = new JTextField();
		eventTypeTextField.setBackground(new Color(245, 255, 250));
		eventTypeTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		eventTypeTextField.setBounds(216, 42, 130, 20);
		panel_2.add(eventTypeTextField);
		eventTypeTextField.setColumns(10);
		eventTypeTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		eventDateTextField = new JTextField();
		eventDateTextField.setBackground(new Color(245, 255, 250));
		eventDateTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		eventDateTextField.setBounds(216, 74, 130, 20);
		eventDateTextField.setText("2016-05-11 19:00:00");
		panel_2.add(eventDateTextField);
		eventDateTextField.setColumns(10);
		eventDateTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		eventLocationTextField = new JTextField();
		eventLocationTextField.setBackground(new Color(245, 255, 250));
		eventLocationTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		eventLocationTextField.setBounds(216, 106, 130, 20);
		panel_2.add(eventLocationTextField);
		eventLocationTextField.setColumns(10);
		eventLocationTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		eventPriceTextField = new JTextField();
		eventPriceTextField.setBackground(new Color(245, 255, 250));
		eventPriceTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		eventPriceTextField.setBounds(216, 138, 130, 20);
		panel_2.add(eventPriceTextField);
		eventPriceTextField.setColumns(10);
		eventPriceTextField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		
		insert = new JButton("Ekle");
		insert.setBounds(380, 10, 76, 40);
		panel_2.add(insert);
		insert.setForeground(new Color(245, 255, 250));
		insert.setBackground(new Color(25, 25, 112));
		insert.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		insert.setFont(new Font("Times New Roman", Font.BOLD, 22));
		
		update = new JButton("De\u011Fi\u015Ftir");
		update.setForeground(new Color(245, 255, 250));
		update.setFont(new Font("Times New Roman", Font.BOLD, 20));
		update.setBackground(new Color(25, 25, 112));
		update.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		update.setBounds(379, 67, 76, 40);
		panel_2.add(update);
		
		Delete = new JButton("Sil");
		Delete.setForeground(new Color(245, 255, 250));
		Delete.setFont(new Font("Times New Roman", Font.BOLD, 22));
		Delete.setBackground(new Color(25, 25, 112));
		Delete.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		Delete.setBounds(379, 121, 76, 40);
		panel_2.add(Delete);
		
		adminMessage = new JLabel("Etkinlik deðiþimi i\u00E7in giri\u015F yap\u0131n\u0131z.");
		adminMessage.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		adminMessage.setHorizontalAlignment(SwingConstants.CENTER);
		adminPanel.add(adminMessage, BorderLayout.SOUTH);
	}
	
	private void searchPane()
	{
		searchPanel = new JPanel();
		searchPanel.setBackground(new Color(176, 196, 222));
		tabbedPane.addTab("Genel Arama", null, searchPanel, null);
		searchPanel.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		menuBar.setForeground(new Color(25, 25, 112));
		menuBar.setBackground(new Color(245, 245, 245));
		menuBar.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 0, 0)));
		menuBar.setBounds(18, 10, 80, 30);
		searchPanel.add(menuBar);
		
		JMenu menu = new JMenu("Tür Seçiniz");
		menu.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		menu.setBorder(new MatteBorder(2, 2, 1, 2, (Color) new Color(25, 25, 112)));
		menu.setBackground(new Color(255, 235, 205));
		menuBar.add(menu);
		
		JMenu menuSpor = new JMenu("Spor");
		menuSpor.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		menuSpor.setBorder(new MatteBorder(2, 2, 1, 2, (Color) new Color(25, 25, 112)));
		menu.add(menuSpor);
		
		menuF = new JMenuItem("Futbol");
		menuF.setBackground(new Color(245, 245, 245));
		menuF.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		menuF.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(25, 25, 112)));
		menuSpor.add(menuF);
		
		menuB = new JMenuItem("Basketbol");
		menuB.setBackground(new Color(245, 245, 245));
		menuB.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		menuB.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(25, 25, 112)));
		menuSpor.add(menuB);
		
		menuV = new JMenuItem("Voleybol");
		menuV.setBackground(new Color(245, 245, 245));
		menuV.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		menuV.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(25, 25, 112)));
		menuSpor.add(menuV);
		
		JMenu menuPerformance = new JMenu("Sahne Sanatlarý");
		menuPerformance.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		menuPerformance.setBorder(new MatteBorder(1, 2, 1, 2, (Color) new Color(25, 25, 112)));
		menu.add(menuPerformance);
		
		menuT = new JMenuItem("Tiyatro");
		menuT.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		menuT.setBackground(new Color(245, 245, 245));
		menuT.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(25, 25, 112)));
		menuPerformance.add(menuT);
		
		menuC = new JMenuItem("Sinema");
		menuC.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		menuC.setBackground(new Color(245, 245, 245));
		menuC.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(25, 25, 112)));
		menuPerformance.add(menuC);
		
		menuM = new JMenuItem("Muzik");
		menuM.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		menuM.setBorder(new MatteBorder(1, 2, 2, 2, (Color) new Color(25, 25, 112)));
		menu.add(menuM);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(130, 10, 340, 207);
		scrollPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(25, 25, 112)));
		searchPanel.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPane.setViewportView(textArea);
	}
	
	private void eventHandlersGeneralSearch()
	{
		op = new StringOperations(); 
		
		menuM.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String s = "ÝSÝM                          FÝYAT     "
						+ "YER                        TARÝH                      TÜR\n";
				
				ArrayList<String> block = op.parseMusic(connect.searchmusic());
				textArea.setText(s);
				for(int i = 0; i< block.size(); i++)
				{
					textArea.insert(block.get(i), textArea.getCaretPosition());
				}		
			}
		});
		
		menuC.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String s = "ÝSÝM                          FÝYAT     "
						+ "BAÞROL                     TARÝH                      TÜR\n";
				
				
				ArrayList<String> block = op.parseMusic(connect.searchperforming(4));
				textArea.setText(s);
				for(int i = 0; i< block.size(); i++)
				{
					textArea.insert(block.get(i), textArea.getCaretPosition());
				}		
			}
		});
		
		menuT.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String s = "ÝSÝM                          FÝYAT     "
						+ "YÖNETMEN                   TARÝH                      TÜR\n";
				
				ArrayList<String> block = op.parseMusic(connect.searchperforming(5));
				textArea.setText(s);
				for(int i = 0; i< block.size(); i++)
				{
					textArea.insert(block.get(i), textArea.getCaretPosition());
				}	
			}
		});
		
		menuV.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String s = "ÝSÝM                          FÝYAT     "
						+ "YER                        TARÝH                      TÜR\n";
				
				ArrayList<String> block = op.parseMusic(connect.searchsports(3));
				textArea.setText(s);
				for(int i = 0; i< block.size(); i++)
				{
					textArea.insert(block.get(i), textArea.getCaretPosition());
				}	
			}
		});
		
		menuB.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String s = "ÝSÝM                          FÝYAT     "
						+ "YER                        TARÝH                      TÜR\n";
				
				ArrayList<String> block = op.parseMusic(connect.searchsports(2));
				textArea.setText(s);
				for(int i = 0; i< block.size(); i++)
				{
					textArea.insert(block.get(i), textArea.getCaretPosition());
				}	
			}
		});
		
		menuF.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String s = "ÝSÝM                          FÝYAT     "
						+ "YER                        TARÝH                      EV SAHÝBÝ\n";
				
				ArrayList<String> block = op.parseMusic(connect.searchsports(1));
				textArea.setText(s);
				for(int i = 0; i< block.size(); i++)
				{
					textArea.insert(block.get(i), textArea.getCaretPosition());
				}	
			}
		});
	}
	
	private boolean sqlInjectionCheck(String name)
	{
		if (name.contains(";")) return true;
		if (name.contains("*")) return true;		
		if (name.contains("Drop")) return true;
		if (name.contains("Insert")) return true;
		if (name.contains("Select")) return true;
		if (name.contains("From")) return true;
		else return false;
	}
	
	private void eventHandlers()
	{
		Delete.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (passFlag)
				{
					String eventName = eventNameTextField.getText();
					boolean passed1 = sqlInjectionCheck(eventName);
					
					if (passed1) adminMessage.setText("Bu uygulama sql saldýrýlarýna karþý korunmaktadýr.");
					else
					{			
						connect.deleteEvent(eventName);
						adminMessage.setText("Etkinlik silinmiþtir.");
					}
				}
				else adminMessage.setText("Etkinlik silmek için giriþ yapýnýz.");	
			}
		});
		
		update.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (passFlag)
				{
					String eventName = eventNameTextField.getText();
					boolean passed1 = sqlInjectionCheck(eventName);
					
					String eventType = eventTypeTextField.getText();
					boolean passed2 = sqlInjectionCheck(eventType);
					
					String eventDate = eventDateTextField.getText();
					boolean passed3 = sqlInjectionCheck(eventDate);
					
					String eventLocation = eventLocationTextField.getText();
					boolean passed4 = sqlInjectionCheck(eventLocation);
					
					String eP = eventPriceTextField.getText();
					boolean passed5 = sqlInjectionCheck(eP);
					
					if (passed1 || passed2 || passed3 || passed4 || passed5) adminMessage.setText("Bu uygulama sql saldýrýlarýna karþý korunmaktadýr.");
					else
					{
						int eventPrice = Integer.parseInt(eP);
						if(eventType.equals("Sinema") || eventType.equals("Tiyatro")){
							connect.updateEvent(eventName, eventPrice, eventType, eventLocation, eventDate,2);
						}
						else if(eventType.equals("Konser") || eventType.equals("Müzik")){
							connect.updateEvent(eventName, eventPrice, eventType, eventLocation, eventDate,3);
						}
						else if(eventType.equals("Futbol") || eventType.equals("Basketbol") || eventType.equals("Voleybol")){
							connect.updateEvent(eventName, eventPrice, eventType, eventLocation, eventDate,1);
						}
						else{
							connect.updateEvent(eventName, eventPrice, eventType, eventLocation, eventDate);
						}
						adminMessage.setText("Etkinlik deðiþtirilmiþtir.");
					}
				}
				else adminMessage.setText("Etkinlik deðiþtirmek için giriþ yapýnýz.");
			}
		});
		
		priceQuery.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				String eventName = textField.getText();
				
				if (sqlInjectionCheck(eventName)) customerMessage.setText("Bu uygulama sql saldýrýlarýna karþý korunmaktadýr.");
				else
				{
					Event event = connect.searchEvent(eventName);
				
					if (event.getPrice() == -1) customerMessage.setText("Etkinlik bulunamadý.");
					else if (eventName.equals("")) customerMessage.setText("Etkinlik adý giriniz.");
					else customerMessage.setText("" + event.getPrice() + " TL");
				}
			}
		});
		
		dateQuery.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String eventName = textField.getText();
				
				if (sqlInjectionCheck(eventName)) customerMessage.setText("Bu uygulama sql saldýrýlarýna karþý korunmaktadýr.");
				else
				{
					Event event = connect.searchEvent(eventName);	
				
					if (eventName.equals("")) customerMessage.setText("Etkinlik adý giriniz.");
					else customerMessage.setText("" + event.getDate());
				}
			}
		});
		
		locationQuery.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String eventName = textField.getText();
				
				if (sqlInjectionCheck(eventName)) customerMessage.setText("Bu uygulama sql saldýrýlarýna karþý korunmaktadýr.");
				else
				{
					Event event = connect.searchEvent(eventName);		
				
					if (eventName.equals("")) customerMessage.setText("Etkinlik adý giriniz.");
					else customerMessage.setText("" + event.getLocationname());
				}
			}
		});
		
		insert.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (passFlag)
				{
					String eventName = eventNameTextField.getText();
					boolean passed1 = sqlInjectionCheck(eventName);
					
					String eventType = eventTypeTextField.getText();
					boolean passed2 = sqlInjectionCheck(eventType);
					
					String eventDate = eventDateTextField.getText();
					boolean passed3 = sqlInjectionCheck(eventDate);
					
					String eventLocation = eventLocationTextField.getText();
					boolean passed4 = sqlInjectionCheck(eventLocation);
					
					String eP = eventPriceTextField.getText();
					boolean passed5 = sqlInjectionCheck(eP);
					
					if (passed1 || passed2 || passed3 || passed4 || passed5) adminMessage.setText("Bu uygulama sql saldýrýlarýna karþý korunmaktadýr.");
					else
					{
						int eventPrice = Integer.parseInt(eP);
						if(eventType.equals("Sinema") || eventType.equals("Tiyatro")){
							connect.addPerforming(eventName, eventPrice, eventType, eventLocation, eventDate);
						}
						else if(eventType.equals("Konser") || eventType.equals("Müzik")){
							connect.addMusic(eventName, eventPrice, eventType, eventLocation, eventDate);
						}
						else if(eventType.equals("Futbol") || eventType.equals("Basketbol") || eventType.equals("Voleybol")){
							connect.addSport(eventName, eventPrice, eventType, eventLocation, eventDate);
						}
						else{
							connect.addEvent(eventName, eventPrice, eventType, eventLocation, eventDate);
						}
						adminMessage.setText("Etkinlik eklenmiþtir.");
					}
				}
				else adminMessage.setText("Etkinlik eklemek için giriþ yapýnýz.");
			}
		});
		
		enter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String name = nameTextField.getText();
				boolean passed1 = sqlInjectionCheck(name);
				
				char[] p = passwordTextField.getPassword();
				String password = new String(p);
				boolean passed2 = sqlInjectionCheck(password);
				
				if (passed1 || passed2) adminMessage.setText("Bu uygulama sql saldýrýlarýna karþý korunmaktadýr.");
				else
				{
					if(connect.searchAdmin(name, password)){
						adminMessage.setText("Giriþ baþarýlý. Etkinlik ekleyebilirsiniz.");
						passFlag = true;
					}
					else{
						adminMessage.setText("Giriþ baþarýsýz.");
						passFlag = false;
					}
				}
			}
		});
	}
}
