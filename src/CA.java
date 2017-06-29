import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
public class CA extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	//����
	//north
	JMenuBar bar = new JMenuBar();
	JMenu m1 = new JMenu("���ӷ�����");
	JMenu m2 = new JMenu("����");
	JMenuItem conServer = new JMenuItem("���ӷ�����");
	JMenuItem disServer = new JMenuItem("�Ͽ�������");
    JMenuItem exit = new JMenuItem("�˳�");
    JMenuItem about = new JMenuItem("���ڱ�����");
	ImageIcon img = new ImageIcon(Client.class.getResource("top.jpg"));
    JLabel top = new JLabel(img);
    JPanel north = new JPanel();
    //west
    JButton Cert = new JButton("����֤��");
    
    JPanel west = new JPanel();
    ImageIcon img2 = new ImageIcon(Client.class.getResource("left.jpg"));
    JLabel left = new JLabel(img2);
    DefaultListModel dlm = new DefaultListModel();
    JList userList = new JList(dlm);
    JScrollPane listPane = new JScrollPane(userList);
    //center
    JPanel center = new JPanel();
    JTextArea showMsg = new JTextArea(10,20);
    JScrollPane showPane = new JScrollPane(showMsg);
    JPanel operPane = new JPanel();
    JLabel input = new JLabel("������:");
    JTextField msgInput = new JTextField(24);
    JButton send = new JButton("send");
    //net
    static Socket socket;
    static String name;
    static ObjectOutputStream oos;
    static List<PersonalFrame> personalFrames = new ArrayList<PersonalFrame>();
    static String requester;
	
    public CA() {
		init();
		addEvent();
		initFrame();
	}
  //�ַ���ת���ɶ�����
    private String StrToBinstr(String str){
    	char[] strChar = str.toCharArray();
    	String result ="";
    	for(int i=0;i<strChar.length;i++){
    		result+=Integer.toBinaryString(strChar[i])+" ";
    	}
    	return result;
    }
    //Ѱ��֤��
    public static String FindCert(String requester)throws IOException{
    	File file = new File("F:\\֤��.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String cert="";
    	String StdID="";
    	if(requester=="01"){ 
    		String StuID = "20141003501";
    		
    	}
    	if(requester=="02"){
    		String StuID = "20141003502";
    		
    	}
    	if(requester=="03"){
    		String StuID = "20141003503";
    	}
    	while((cert=br.readLine())!=null){
    		Pattern p = Pattern.compile(StdID);
    		Matcher m = p.matcher(cert);
    		if(m.find())
    			return cert;
    	}
		return cert;
    }
    
    //��ʼ��
	public void init(){
		//north
		m1.add(conServer);
		m1.add(disServer);
		m1.addSeparator();
		m1.add(exit);
		m2.add(about);
		bar.add(m1);
		bar.add(m2);
		BorderLayout bl = new BorderLayout();
		north.setLayout(bl);
		north.add(bar,BorderLayout.NORTH);
		//north.add(top,BorderLayout.SOUTH);
		add(north,BorderLayout.NORTH);
		//west
		Dimension dim = new Dimension(100,150);
		west.setPreferredSize(dim);
		Dimension dim2 = new Dimension(100,300);
		listPane.setPreferredSize(dim2);
		BorderLayout bl2 = new BorderLayout();
		west.setLayout(bl2);
		//west.add(left,BorderLayout.NORTH);
		west.add(listPane,BorderLayout.CENTER);
		west.add(Cert,BorderLayout.SOUTH);
		add(west,BorderLayout.EAST);
		userList.setFont(new Font("����",Font.BOLD,18));
		//center
		//showMsg.setFont(new Font("����",Font.BOLD,28));
		msgInput.setActionCommand("enterKey");
		showMsg.setEditable(false);
		BorderLayout bl3 = new BorderLayout();
		center.setLayout(bl3);
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		operPane.setLayout(fl);
		operPane.add(input);
		operPane.add(msgInput);
		operPane.add(send);
		center.add(showPane,BorderLayout.CENTER);
		center.add(operPane,BorderLayout.SOUTH);
		add(center,BorderLayout.CENTER);
		
	}
	//���Ӽ���
	public void addEvent(){
		//�¼��İ�
		Cert.addActionListener(this);
		send.addActionListener(this);
		msgInput.addActionListener(this);
		conServer.addActionListener(this);
		disServer.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);
		SymMouse   picker   =   new   SymMouse();
		userList.addMouseListener(picker);
	}
	//��ʼ������
	public void initFrame(){
		setResizable(false);
		setTitle("CA");
		setSize(500,400);
		setLocation(200,50);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	@Override
	public void dispose(){
		try {
			ExitProgram();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//������Ϣ
	public void SendMessageToRequester()throws Exception{
		//�ж��Ƿ������˷�����
		if(socket == null){
			JOptionPane.showMessageDialog(this, "û�����ӷ��������Ѿ��Ͽ�������");
			return;
		}
		/*
		 * ������Ϣ
		 * CA���͵�����Ӧ���������ߵ�֤�飬����˽��
		 * ��ʽ  CA��֤����Ϣ����Ϣ��Chat����������Personal��֤��������
		 *
		 */
		//String tempcontent = msgInput.getText();
		//String content = StrToBinstr(tempcontent);
		String content = FindCert(requester);
		if(content == null || content.trim().equals("")){
			JOptionPane.showMessageDialog(this, "���ܷ��Ϳ��ַ���");
		}
		else{
			//����ı������
			msgInput.setText("");
		}
		
		Message msg = new Message("CA", "֤��", MessageType.Chat, ChatState.Personal, requester);
		System.out.println(requester);
			oos.writeObject(msg);
			oos.flush();
	}
	
	
	public void ConnectServer()throws Exception{
		//�����������ʾ�û����������IP��ַ
		int cnt = 0;
		do{
			if(++cnt == 4){
				JOptionPane.showMessageDialog(this,"�Բ������������Ѿ�����3��\n�����Զ��˳���");
				System.exit(0);
			}
			
			String ip = JOptionPane.showInputDialog("�������������IP:");
			String portStr = JOptionPane.showInputDialog("�������������PORT:");
			
			System.out.println("������յ�����Ϣ�У�"+ip+" "+portStr);
			
		/* */	socket=new Socket(ip,Integer.parseInt(portStr));
		//���̶ֹ�ΪCA
			name = "CA";
			
			OutputStream os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			Message msg = new Message(name,null,MessageType.Login);
			oos.writeObject(msg);
			oos.flush();
		    
		}while(socket == null);
//		�ɹ��������Ӻ�����һ�����߳̽�����Ϣ
		new Thread(){
			@Override
			public void run() {
				try {
					boolean flag=true;
					while(flag){
						//����������
						InputStream is = socket.getInputStream();
						//��ȡ���������
						ObjectInputStream ois = new ObjectInputStream(is);
						
						Message msg = (Message)(ois.readObject());
						
						MessageType type = msg.getMsgType();
						
				//��Ϣ����Ϊ��¼�������û��б���
						if(type.equals(MessageType.Login)){
							//TextArea��ʾ���յ�����Ϣ
							String content = msg.getContent();
							String old = showMsg.getText();
							
							if(old == null || old.trim().equals("")){
								showMsg.setText(content);
							}else{
								String temp = old+"\n"+content;
								showMsg.setText(temp);
							}							
							//�������������¶�
							showMsg.setCaretPosition(showMsg.getText().length());
							
							//���������û�����
							if(dlm.size() == 0){
								List<String> names = msg.getNames();
								for(String s : names){
									
									if(!s.equals(name)){
										//����name���б���
										dlm.addElement(s);
										System.out.println(s+"���ӳɹ�");
									}
								}
							}else{
								//����б���û�У�����name
								if(!msg.getFrom().equals(name))
									dlm.addElement(msg.getFrom());
							}
						}						
				//��Ϣ����Ϊ����
						else if(type.equals(MessageType.Chat)){
							//�����˽��
							if(msg.getChatState().equals(ChatState.Personal)){
								boolean FindIt=false;
								for(PersonalFrame pf:personalFrames){
									if(pf.getName().equals(msg.getFrom())||pf.getName().equals(msg.getTo())){	
										//��ʾ��Ϣ
										//pf.showMsg(msg);
										showMsg.setText(pf.getName());
										//JOptionPane.showMessageDialog(null, "�յ�֤��");
										 requester = msg.getFrom();
										 //�յ�˽�ĸ��ݷ����� ����֤����Ϣ��
										 SendMessageToRequester();
										System.out.println("�ɹ��ҵ���");
										FindIt=true;
										break;
									}									
								}
								//���û�ҵ� �½�һ��˽��
								/*if(FindIt==false){
									PersonalFrame myFrame=new PersonalFrame(name, msg.getFrom());
									personalFrames.add(myFrame);
									System.out.println("�ɹ�������");
									myFrame.showMsg(msg);
								}*/
							}
							//����������
							else{
									String content = msg.getContent();
									String old = showMsg.getText();
									if(old == null || old.trim().equals("")){
										showMsg.setText(content);
									}else{
										String temp = old+"\n"+content;
										showMsg.setText(temp);
									}
									showMsg.setCaretPosition(showMsg.getText().length());
							}
							
						}
						
						//�ǳ�״̬
						else if(type.equals(MessageType.Logout)){
							String content = msg.getContent();
							String old = showMsg.getText();
							if(old == null || old.trim().equals("")){
								showMsg.setText(content);
							}else{
								String temp = old+"\n"+content;
								showMsg.setText(temp);
							}
							showMsg.setCaretPosition(showMsg.getText().length());
							if(!msg.getFrom().equals(name))
								dlm.removeElement(msg.getFrom());
							if(msg.getFrom().equals(name))
								flag=false;
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}.start();
		setTitle("CA");
	}
	//�Ͽ�����
	public void BreakServer()throws Exception{
		if(socket!=null){
			Message msg=new Message(name, null, MessageType.Logout);
			oos.writeObject(msg);
			oos.flush();
			dlm.removeAllElements();
			socket=null;
			setTitle("CA����");
		}
	}
	//ֱ���˳�
	public void ExitProgram()throws Exception{
		int flag;
		if(socket!=null){
			flag = JOptionPane.showConfirmDialog(this, "��û��ע����ֱ���˳����Զ�ע�����ӣ�\nȷ��Ҫ�˳���");
		}else{
			flag=0;
		}
		//System.out.println("flag : "+flag);
		if(flag == 0){
			if(socket!=null){
				Message msg=new Message(name, null, MessageType.Logout);
				oos.writeObject(msg);
				oos.flush();
				socket=null;
				dlm.removeAllElements();
			}
			System.exit(0);
		}
	}
	//Button��Ӧ�¼�
	public void actionPerformed(ActionEvent e) {
		try{
		String comm = e.getActionCommand();
		System.out.println(comm);
		if(comm.equals("send") || comm.equals("enterKey")||comm.equals("sendPersonalMessage")){
			SendMessageToRequester();
		}else if(comm.equals("���ӷ�����")){
			ConnectServer();
		}else if(comm.equals("�Ͽ�������")){
			BreakServer();
		}else if(comm.equals("�˳�")){
			ExitProgram();
		}else if(comm.equals("���ڱ�����")){
			JOptionPane.showMessageDialog(this, "֤�鷢�Ż���");
		}else if(comm.equals("����֤��")){
			
			String content = "����֤��";
			
		}
		else{
			System.out.println("��ʶ����¼�");
		}
		}catch(Exception e1){
			//��������
		}
	}
	//�ڲ��࣬������Ӧ˫��JList��item���¼�
	class   SymMouse   extends   java.awt.event.MouseAdapter 
    { 
        public   void   mouseClicked(java.awt.event.MouseEvent   e) 
        { 
            Object   object   =   e.getSource(); 
            if(object==userList)
				try {
					userList_mouseClicked(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
        } 
    } 
	 
  void   userList_mouseClicked(java.awt.event.MouseEvent   event) throws IOException 
    { 
        if(event.getModifiers()==MouseEvent.BUTTON1_MASK&&event.getClickCount()==2) 
        { 
        	//JOptionPane.showMessageDialog(this, userList.getSelectedValue().toString(), "���� ",JOptionPane.WARNING_MESSAGE);
        	if(userList.getSelectedValue().toString()=="01"){ 
        		/*String StuID = "20141003501";
        		String cert = FindCert(StuID);
        		showMsg.setText(cert);*/
        		
        	}
        	if(userList.getSelectedValue().toString()=="02"){
        		String StuID = "20141003501";
        		String cert = FindCert(StuID);
        		showMsg.setText(cert);
        	}
        	if(userList.getSelectedValue().toString()=="03"){
        		String StuID = "20141003501";
        		String cert = FindCert(StuID);
        		showMsg.setText(cert);
        		
        	}
        	
        } 
    } 
	public static void main(String[] args) {
		new CA();
	}
}
