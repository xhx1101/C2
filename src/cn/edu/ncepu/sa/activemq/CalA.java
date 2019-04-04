package cn.edu.ncepu.sa.activemq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class CalA extends C2Component {
	


	private String calAString;
	public CalA(String host, int port, String user, String pwd) {
		super(host, port, user, pwd, "CalA");
		// TODO Auto-generated constructor stub
	}
	
	public CalA(String host, int port, String user, String pwd,String calAString) {
		super(host, port, user, pwd, "CalA");
		this.calAString = calAString;
		// TODO Auto-generated constructor stub
	}
	
	
	private void sendASubTask(String msg) {
		String tmp[] = msg.split(",");
		switch (tmp[0]) {
		case "+":
			sender.SendAMsg("Add", msg, queue);
			break;
		case "-":
			sender.SendAMsg("Sub", msg, queue);
			break;
		case "*":
			sender.SendAMsg("Mul", msg, queue);
			break;
		case "/":
			sender.SendAMsg("Div", msg, queue);
			break;
		case "sin":
			sender.SendAMsg("Sin", msg, queue);
			break;
		case "cos":
			sender.SendAMsg("Cos", msg, queue);
			break;

		default:
			break;
		}

	}

	/**
	 * 开始工作
	 */
	@Override
	public void StartWork() {
		super.StartWork();

	}

	@Override
	public void run() {
		String returnResult = "100";
		Analysis calculate = new Analysis();
		ArrayList<String> result = calculate.getStringList(calAString);//String转换为List
		result = calculate.getPostOrder(result);   //中缀变后缀
		System.out.println(result);
		Stack stack = new Stack();
		int i = 0;
		boolean flag = true;
		while (toWork) {
			if(flag) {
				for ( ;i < result.size(); i++) {
					String next="";
					if(Character.isDigit(result.get(i).charAt(0))){
						stack.push(Double.parseDouble(result.get(i)));
					}else if((result.get(i))=="sin"||result.get(i)=="cos") {
						
						Integer cur = (Integer) stack.pop();
						next = result.get(i)+","+cur;
						System.out.println(next);
						sendASubTask(next);
						
					}else{			
						flag = false;
						Double back = (Double)stack.pop();
						Double front = (Double)stack.pop();
						switch (result.get(i).charAt(0)) {
							case '+':
								next = "+,"+front+","+back;
								break;
							case '-':
								next = "-,"+front+","+back;					
								break;
							case '*':
								next = "*,"+front+","+back;
								break;
							case '/':
								next = "/,"+front+","+back;
								break;
						}
						System.out.println(next);
						sendASubTask(next);
						i++;
						break;
					}
				}
			}
			
			
			if (null != reciever) {
				// 接收器对了中是否消息系
//				System.out.println(reciever.msgList.size());
				if (reciever.msgList.size() > 0) {
					if(i >= result.size()&&reciever.msgList.size()==1) {
						TextMessage msg1 = reciever.msgList.peek();
						try {
							returnResult = msg1.getText();
						} catch (JMSException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Work over:" + returnResult);
						StopWork();						
					}else {
					// 取出一个消息
					TextMessage msg = reciever.msgList.poll();
					try {
						returnResult = msg.getText();
						stack.push(Double.parseDouble(returnResult));
						for ( ;i < result.size(); i++) {
							String next="";
							if(Character.isDigit(result.get(i).charAt(0))){
								stack.push(Double.parseDouble(result.get(i)));
							}else if((result.get(i))=="sin"||result.get(i)=="cos") {
								Double cur = (Double) stack.pop();
								next = result.get(i)+","+cur;
								System.out.println(next);
								sendASubTask(next);	
								i=i+1;
								break;
							}else{			
								Double back = (Double)stack.pop();
								Double front = (Double)stack.pop();								
								switch (result.get(i).charAt(0)) {
									case '+':
										next = "+,"+front+","+back;
										break;
									case '-':
										next = "-,"+front+","+back;					
										break;
									case '*':
										next = "*,"+front+","+back;
										break;
									case '/':
										next = "/,"+front+","+back;
										break;
								}
								System.out.println(next);
								sendASubTask(next);
								i++;
								break;
							}
						}

					} catch (JMSException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						returnResult = null;
					}
				}
				}
			}
			
			
			

			// 休眠0.1秒
			try {
				sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	@Override
	String getResult(String msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
