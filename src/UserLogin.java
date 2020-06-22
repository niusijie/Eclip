
import info.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.CPYDDatabase;

public class UserLogin extends JFrame implements ActionListener{
	private JTextField fieldAccount = new JTextField();
	private JPasswordField fieldPassword = new JPasswordField();
	private JButton buttonLogin = new JButton("登   陆");

	public static void main(String[] args) {
		new UserLogin();

	}

	public UserLogin() {
		this.setTitle("用户登陆");
		this.setLocation(400, 300);
		this.setSize(300, 400);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

		// 用户账号
		JLabel labelAccount = new JLabel("账  号");
		labelAccount.setAlignmentY(CENTER_ALIGNMENT);
		labelAccount.setBounds(50, 100, 100, 30);
		fieldAccount.setBounds(150, 100, 100, 30);
		this.getContentPane().add(labelAccount);
		this.getContentPane().add(fieldAccount);

		
		// 用户密码
		JLabel labelPwd = new JLabel("密  码");
		labelPwd.setAlignmentY(CENTER_ALIGNMENT);
		labelPwd.setBounds(50, 150, 100, 30);
		fieldPassword.setBounds(150, 150, 100, 30);
		this.getContentPane().add(labelPwd);
		this.getContentPane().add(fieldPassword, BorderLayout.CENTER);
		
		// 第6行为登陆按钮
		buttonLogin.setBounds(75, 250, 150, 30);
		this.getContentPane().add(buttonLogin);
		buttonLogin.addActionListener(this);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonLogin){
			String account = fieldAccount.getText();
			String password = new String(fieldPassword.getPassword());
			
			User user = verifyAccount(account, password);
			if( user != null){
				// 弹出车票预订窗口
				new Msnake();
				
				// 关闭当前窗口
				this.dispose();
			}
			else{
				JOptionPane.showMessageDialog(this, "账号或密码错误");
				fieldPassword.setText(null);
			}
		}		
	}

	/**	
	 * @param account
	 * @param password
	 * @return user, 为null表示用户名或者密码错误，否则验证通过
	 */
	private User verifyAccount(String account, String password) {
		// 这里固定用户名和密码为 10001/10001
		User user = CPYDDatabase.userQquery(account);
		if (user == null){
			System.out.println("用户不存在");
		} else if (! user.verifyPwd(password)) {
			System.out.println("密码错误");
			user = null;
		}
		// else, 验证通过
		
		return user;
	}
}
