package htxsystems.simpleid.javasample;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame implements SimpleIDEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tbAccountId;
	private JTextField tbPersonId;
	private JList<String> lbLog;
	DefaultListModel<String> dlm = new DefaultListModel<String>();
	JLabel pbSearchFingerprint;
	JLabel pbRegisterFingerprint;
	JPanel gbFingers;
	JButton btCancelRegister;
	JButton btSubmitRegister;
	JLabel pbRegisterFace;
	JButton btRegisterFaceOnly;
	BufferedImage lastFaceSearchImage;
	BufferedImage lastFaceRegisterImage;
	
	 private SimpleID simpleId;
     private OperationType _currentOperation;
     private JTextField tbCustom1;
     private JTextField tbCustom2;
     private JTextField tbCustom3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				simpleId.Disconnect();
			}
		});
		
		simpleId = new SimpleID(this);
		simpleId.Connect();
		
		setTitle("SimpleID Java Sample");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		
		JLabel lblNewLabel = new JLabel("Account Id:");
		
		tbAccountId = new JTextField();
		tbAccountId.setColumns(10);
		
		JLabel lblPersonId = new JLabel("Person Id:");
		
		tbPersonId = new JTextField();
		tbPersonId.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		lbLog = new JList<>(dlm);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbLog, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
						.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbAccountId, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
							.addGap(35)
							.addComponent(lblPersonId, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(tbPersonId, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPersonId))
						.addComponent(tbPersonId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel)
							.addComponent(tbAccountId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(30)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 491, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbLog, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel tabRegister = new JPanel();
		tabbedPane.addTab("Register", null, tabRegister, null);
		
		pbRegisterFace = new JLabel("");
		
		JButton btLoadImageRegisterFace = new JButton("Load Image");
		btLoadImageRegisterFace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg"));
				int result = fileChooser.showOpenDialog(Main.this);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    try {
				    	lastFaceRegisterImage = ImageIO.read(selectedFile);
						ImageIcon icon = new ImageIcon(lastFaceRegisterImage.getScaledInstance(pbRegisterFace.getWidth(), pbRegisterFace.getHeight(), Image.SCALE_SMOOTH));
					    pbRegisterFace.setIcon(icon);
					    btRegisterFaceOnly.setEnabled(true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		JButton btClearRegister = new JButton("Clear");
		btClearRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClearRegisterForm();
			}
		});
		
		JButton btStartRegister = new JButton("Start Register");
		btStartRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String base64Image = null;
				if(pbRegisterFace.getIcon() != null)
	            {
					base64Image = encodeToBase64Jpeg(lastFaceRegisterImage);
	            }
	           
            	_currentOperation = OperationType.REGISTER;
			  	String accountId = ReadAccountId();
	            String personId = ReadPersonId();
	            String custom1 = ReadCustom1();
	            String custom2 = ReadCustom2();
	            String custom3 = ReadCustom3();

                simpleId.StartRegister(accountId, personId, custom1, custom2, custom3, base64Image, false);
	        }
		});
		
		JLabel lblCustom = new JLabel("Custom 1:");
		
		tbCustom1 = new JTextField();
		tbCustom1.setColumns(10);
		
		tbCustom2 = new JTextField();
		tbCustom2.setColumns(10);
		
		JLabel lblCustom_1 = new JLabel("Custom 2:");
		
		tbCustom3 = new JTextField();
		tbCustom3.setColumns(10);
		
		JLabel lblCustom_2 = new JLabel("Custom 3:");
		
		pbRegisterFingerprint = new JLabel("");
		
		gbFingers = new JPanel();
		gbFingers.setBorder(new TitledBorder(null, "Fingers", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		
		btRegisterFaceOnly = new JButton("Register Face Only");
		btRegisterFaceOnly.setEnabled(false);
		btRegisterFaceOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pbRegisterFace.getIcon() == null)
	            {
			 		JOptionPane.showMessageDialog(null,"Please load an image");
	            }
	            else
	            {
	            	_currentOperation = OperationType.REGISTER;
				  	String accountId = ReadAccountId();
		            String personId = ReadPersonId();
		            String custom1 = ReadCustom1();
		            String custom2 = ReadCustom2();
		            String custom3 = ReadCustom3();
		            
		            String base64Image = encodeToBase64Jpeg(lastFaceRegisterImage);

	                simpleId.StartRegister(accountId, personId, custom1, custom2, custom3, base64Image, true);
	            }
			}
		});
		
		btCancelRegister = new JButton("Cancel");
		btCancelRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simpleId.CancelRegister();
	            ClearRegisterForm();
			}
		});
		
		btSubmitRegister = new JButton("Submit");
		btSubmitRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simpleId.SubmitRegister();
			}
		});
		btSubmitRegister.setEnabled(false);
		GroupLayout gl_tabRegister = new GroupLayout(tabRegister);
		gl_tabRegister.setHorizontalGroup(
			gl_tabRegister.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tabRegister.createSequentialGroup()
					.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
								.addComponent(pbRegisterFace, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
								.addGroup(gl_tabRegister.createSequentialGroup()
									.addComponent(btClearRegister, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
									.addGap(113)))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
								.addComponent(btStartRegister, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
								.addComponent(btLoadImageRegisterFace, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
							.addGap(67)))
					.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_tabRegister.createSequentialGroup()
									.addComponent(lblCustom_2, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(tbCustom3, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_tabRegister.createSequentialGroup()
									.addComponent(lblCustom_1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(tbCustom2, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_tabRegister.createSequentialGroup()
									.addComponent(lblCustom, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(tbCustom1, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)))
							.addGap(210)
							.addComponent(pbRegisterFingerprint, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addGap(104)
							.addComponent(btRegisterFaceOnly, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
							.addGap(65)
							.addComponent(btCancelRegister, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btSubmitRegister, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE))
						.addComponent(gbFingers, GroupLayout.PREFERRED_SIZE, 681, GroupLayout.PREFERRED_SIZE))
					.addGap(39))
		);
		gl_tabRegister.setVerticalGroup(
			gl_tabRegister.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabRegister.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCustom))
						.addComponent(tbCustom1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCustom_1))
						.addComponent(tbCustom2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCustom_2))
						.addComponent(tbCustom3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(334, Short.MAX_VALUE))
				.addGroup(gl_tabRegister.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addComponent(btClearRegister, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pbRegisterFace, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE))
						.addComponent(pbRegisterFingerprint, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_tabRegister.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addComponent(btLoadImageRegisterFace, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
							.addComponent(btStartRegister, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addGap(45))
						.addGroup(gl_tabRegister.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(gbFingers, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_tabRegister.createParallelGroup(Alignment.TRAILING)
								.addComponent(btRegisterFaceOnly, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addComponent(btCancelRegister, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(btSubmitRegister, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
							.addGap(24))))
		);
		
		JButton btRightThumb = new JButton("Right Thumb");
		btRightThumb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.RIGHT_THUMB);
			}
		});
		
		JButton btnLeftThumb = new JButton("Left Thumb");
		btnLeftThumb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.LEFT_THUMB);
			}
		});
		
		JButton btnRightIndex = new JButton("Right Index");
		btnRightIndex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.RIGHT_INDEX);
			}
		});
		
		JButton btnRightMiddle = new JButton("Right Middle");
		btnRightMiddle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.RIGHT_MIDDLE);
			}
		});
		
		JButton btnRightRing = new JButton("Right Ring");
		btnRightRing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.RIGHT_RING);
			}
		});
		
		JButton btnRightLittle = new JButton("Right Little");
		btnRightLittle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.RIGHT_LITTLE);
			}
		});
		
		JButton btnLeftThumb_1 = new JButton("Left Index");
		btnLeftThumb_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.LEFT_INDEX);
			}
		});
		
		JButton btnLeftThumb_2 = new JButton("Left Middle");
		btnLeftThumb_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.LEFT_MIDDLE);
			}
		});
		
		JButton btnLeftRing = new JButton("Left Ring");
		btnLeftRing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.LEFT_RING);
			}
		});
		
		JButton btnLeftLittle = new JButton("Left Little");
		btnLeftLittle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFingerCapture(FINGERID.LEFT_LITTLE);
			}
		});
		GroupLayout gl_gbFingers = new GroupLayout(gbFingers);
		gl_gbFingers.setHorizontalGroup(
			gl_gbFingers.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbFingers.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_gbFingers.createParallelGroup(Alignment.LEADING)
						.addComponent(btRightThumb)
						.addComponent(btnLeftThumb, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(gl_gbFingers.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRightIndex, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLeftThumb_1, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(gl_gbFingers.createParallelGroup(Alignment.LEADING)
						.addComponent(btnRightMiddle, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLeftThumb_2, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(gl_gbFingers.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_gbFingers.createSequentialGroup()
							.addComponent(btnRightRing, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addComponent(btnRightLittle, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_gbFingers.createSequentialGroup()
							.addComponent(btnLeftRing, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addComponent(btnLeftLittle, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_gbFingers.setVerticalGroup(
			gl_gbFingers.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbFingers.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_gbFingers.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_gbFingers.createSequentialGroup()
							.addComponent(btnRightMiddle, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_gbFingers.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnLeftThumb_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLeftThumb_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnRightIndex, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_gbFingers.createSequentialGroup()
							.addComponent(btRightThumb, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnLeftThumb, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_gbFingers.createSequentialGroup()
							.addGroup(gl_gbFingers.createParallelGroup(Alignment.LEADING)
								.addComponent(btnRightRing, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRightLittle, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_gbFingers.createParallelGroup(Alignment.LEADING)
								.addComponent(btnLeftLittle, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLeftRing, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gbFingers.setLayout(gl_gbFingers);
		tabRegister.setLayout(gl_tabRegister);
		
		JPanel tabSearchFingerprint = new JPanel();
		tabbedPane.addTab("Search Fingerprint", null, tabSearchFingerprint, null);
		
		JButton btSearchFingerprint = new JButton("Search Fingerprint");
		btSearchFingerprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  	_currentOperation = OperationType.SEARCH;
				  	String accountId = ReadAccountId();
		            String personId = ReadPersonId();

		            simpleId.SearhFingerprint(accountId, personId);
		            pbSearchFingerprint.setIcon(null);
			}
		});
		
		JButton btCancelSearchFingerprint = new JButton("Cancel Search");
		btCancelSearchFingerprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				simpleId.CancelFingerprintSearch();
			}
		});
		
	    pbSearchFingerprint = new JLabel("");
		GroupLayout gl_tabSearchFingerprint = new GroupLayout(tabSearchFingerprint);
		gl_tabSearchFingerprint.setHorizontalGroup(
			gl_tabSearchFingerprint.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabSearchFingerprint.createSequentialGroup()
					.addContainerGap()
					.addComponent(pbSearchFingerprint, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
					.addGap(167)
					.addGroup(gl_tabSearchFingerprint.createParallelGroup(Alignment.LEADING)
						.addComponent(btCancelSearchFingerprint, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
						.addComponent(btSearchFingerprint, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(384, Short.MAX_VALUE))
		);
		gl_tabSearchFingerprint.setVerticalGroup(
			gl_tabSearchFingerprint.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabSearchFingerprint.createSequentialGroup()
					.addGroup(gl_tabSearchFingerprint.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabSearchFingerprint.createSequentialGroup()
							.addGap(199)
							.addComponent(btSearchFingerprint, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addGap(59)
							.addComponent(btCancelSearchFingerprint, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_tabSearchFingerprint.createSequentialGroup()
							.addGap(27)
							.addComponent(pbSearchFingerprint, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(97, Short.MAX_VALUE))
		);
		tabSearchFingerprint.setLayout(gl_tabSearchFingerprint);
		
		JPanel tabSearchFace = new JPanel();
		tabbedPane.addTab("SearchFace", null, tabSearchFace, null);
		
		JLabel pbSearchFace = new JLabel("");
		
		JButton btLoadImageSearchFace = new JButton("Load Image");
		btLoadImageSearchFace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg"));
				int result = fileChooser.showOpenDialog(Main.this);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    try {
				    	lastFaceSearchImage = ImageIO.read(selectedFile);
						ImageIcon icon = new ImageIcon(lastFaceSearchImage.getScaledInstance(pbSearchFace.getWidth(), pbSearchFace.getHeight(), Image.SCALE_SMOOTH));
					    pbSearchFace.setIcon(icon);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		JButton btSearchFace = new JButton("Search Face");
		btSearchFace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 	if(pbSearchFace.getIcon() == null)
		            {
				 		JOptionPane.showMessageDialog(null,"Please load an image");
		            }
		            else
		            {
		               
		                _currentOperation = OperationType.SEARCH_FACE;
					  	String accountId = ReadAccountId();
			            String personId = ReadPersonId();
			            
			            String base64Image = encodeToBase64Jpeg(lastFaceSearchImage);

		                simpleId.SearchFace(accountId, personId, base64Image);
		            }
			}
		});
		GroupLayout gl_tabSearchFace = new GroupLayout(tabSearchFace);
		gl_tabSearchFace.setHorizontalGroup(
			gl_tabSearchFace.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabSearchFace.createSequentialGroup()
					.addGroup(gl_tabSearchFace.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tabSearchFace.createSequentialGroup()
							.addGap(286)
							.addComponent(pbSearchFace, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_tabSearchFace.createSequentialGroup()
							.addGap(348)
							.addComponent(btLoadImageSearchFace, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(249, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_tabSearchFace.createSequentialGroup()
					.addContainerGap(723, Short.MAX_VALUE)
					.addComponent(btSearchFace, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_tabSearchFace.setVerticalGroup(
			gl_tabSearchFace.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabSearchFace.createSequentialGroup()
					.addGap(27)
					.addComponent(pbSearchFace, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addComponent(btLoadImageSearchFace, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
					.addComponent(btSearchFace, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		tabSearchFace.setLayout(gl_tabSearchFace);
		
		JPanel tabDelete = new JPanel();
		tabbedPane.addTab("Delete", null, tabDelete, null);
		
		JButton btDeletePerson = new JButton("Delete Person");
		btDeletePerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String accountId = ReadAccountId();
	            String personId = ReadPersonId();

	            simpleId.Delete(accountId, personId);
				
			}
		});
		GroupLayout gl_tabDelete = new GroupLayout(tabDelete);
		gl_tabDelete.setHorizontalGroup(
			gl_tabDelete.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tabDelete.createSequentialGroup()
					.addGap(365)
					.addComponent(btDeletePerson, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(370, Short.MAX_VALUE))
		);
		gl_tabDelete.setVerticalGroup(
			gl_tabDelete.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_tabDelete.createSequentialGroup()
					.addContainerGap(217, Short.MAX_VALUE)
					.addComponent(btDeletePerson, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addGap(191))
		);
		tabDelete.setLayout(gl_tabDelete);
		getContentPane().setLayout(groupLayout);
	}

	private String ReadAccountId() {
		String accountId = tbAccountId.getText().trim();
		if(accountId.isEmpty())
			return null;
		return accountId;
	}
	
	private String ReadPersonId() {
		String personId = tbPersonId.getText().trim();
		if(personId.isEmpty())
			return null;
		return personId;
	}
	
	private String ReadCustom1() {
		String custom1 = tbCustom1.getText().trim();
		if(custom1.isEmpty())
			return null;
		return custom1;
	}
	
	private String ReadCustom2() {
		String custom2 = tbCustom2.getText().trim();
		if(custom2.isEmpty())
			return null;
		return custom2;
	}
	
	private String ReadCustom3() {
		String custom3 = tbCustom3.getText().trim();
		if(custom3.isEmpty())
			return null;
		return custom3;
	}
	
	private void ProcessSimpleIDResponse(SimpleIDResponse response) {
		 WriteLog(response);
         ResponseType responseType = response.getTransactionInformation().getReason();
         
         if (responseType == ResponseType.FINGER_CAPTURE_STATUS)
         {
             String base64Image = response.getTransactionInformation().getPersons().get(0).getFingerprintImage().getImage();
             if (_currentOperation == OperationType.SEARCH)
             {
            	 SetFingerprintSearchImage(base64Image);
             }
             else if (_currentOperation == OperationType.REGISTER)
             {
            	 SetFingerprintRegisterImage(base64Image);
                 if(response.getTransactionInformation().getPersons().get(0).getFingerprintImage().getFinished())
                 {
                	 btSubmitRegister.setEnabled(true);
                 }
             }
         }

         if (responseType == ResponseType.PROCESSING_STATUS)
         {
             if(response.getTransactionInformation().getProcessingStatus() == ProcessingStatus.REGISTRATION_READY)
             {
            	 gbFingers.setEnabled(true);
            	 btCancelRegister.setEnabled(true);
             }
         }
	}
	
	private void WriteLog(SimpleIDResponse response) {
		
		 ReturnCode returnCode = response.getTransactionInformation().getReturnCode();
         ResponseType responseType = response.getTransactionInformation().getReason();
         

        StringBuilder sb = new StringBuilder();
        sb.append(responseType.toString() + ": ");
        if (responseType == ResponseType.PROCESSING_STATUS)
        {
            sb.append(response.getTransactionInformation().getProcessingStatus().toString() + " ");
        }
        else
        {
            sb.append(returnCode.toString() + " ");
        }
        if (returnCode == ReturnCode.HIT_CONFIRMED || returnCode == ReturnCode.ALREADY_ENROLLED)
        {
            sb.append(" - " +  response.getTransactionInformation().getPersons().get(0).getPersonID());
        }
       
        if (lbLog.getModel().getSize() > 5)
        	((DefaultListModel) lbLog.getModel()).remove(0);
        dlm.addElement(sb.toString());
        lbLog.setSelectedIndex(lbLog.getModel().getSize() - 1);
        lbLog.clearSelection();
        
	}
	
	 private BufferedImage toBufferedImage(Image img)
	    {
	        if (img instanceof BufferedImage)
	        {
	            return (BufferedImage) img;
	        }

	        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	      
	        Graphics2D bGr = bimage.createGraphics();
	        bGr.drawImage(img, 0, 0, null);
	        bGr.dispose();
	        
	        return bimage;
	    }
	    
	    private void SetFingerprintSearchImage(String base64Image)
	    {
	    	byte[] btDataFile =  Base64.decodeBase64(base64Image);
	    	try {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(btDataFile));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(pbSearchFingerprint.getWidth(), pbSearchFingerprint.getHeight(), Image.SCALE_SMOOTH));
			    pbSearchFingerprint.setIcon(icon);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    private void SetFingerprintRegisterImage(String base64Image)
	    {
	    	byte[] btDataFile =  Base64.decodeBase64(base64Image);
	    	try {
				BufferedImage image = ImageIO.read(new ByteArrayInputStream(btDataFile));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(pbRegisterFingerprint.getWidth(), pbRegisterFingerprint.getHeight(), Image.SCALE_SMOOTH));
				pbRegisterFingerprint.setIcon(icon);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    public String encodeToBase64Jpeg(BufferedImage image) {
	        String imageString = null;
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();

	        try {
	            ImageIO.write(image, "jpg", bos);
	            byte[] imageBytes = bos.toByteArray();

	            imageString = Base64.encodeBase64String(imageBytes);

	            bos.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return imageString;
	    }
	    
	    private void ClearRegisterForm()
        {
            tbCustom1.setText(""); 
            tbCustom2.setText(""); 
            tbCustom3.setText("");
            pbRegisterFingerprint.setIcon(null);
            gbFingers.setEnabled(false);
            btCancelRegister.setEnabled(false);
            pbRegisterFace.setIcon(null);
            btRegisterFaceOnly.setEnabled(false);
        }
	
	    //region FINGERS_CLICK
	    
	    private void StartFingerCapture(FINGERID fingerId)
        {
            simpleId.StartFingerCapture(fingerId);
        }
	    
	    //endregion
	    
	@Override
	public void OnSimpleIDMessage(SimpleIDResponse response) {
		ProcessSimpleIDResponse(response);
		
	}
}
