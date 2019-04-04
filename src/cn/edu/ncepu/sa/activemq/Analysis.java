package cn.edu.ncepu.sa.activemq;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
 
/**
 *   ���ʽ�Ĵ���
 * @author xhx
 *
 */
public class Analysis {
	
	/**
	 * ���ַ���ת����List
	 * @param str
	 * @return
	 */
	public ArrayList<String> getStringList(String str){
		ArrayList<String> result = new ArrayList<String>();
		String num = "";
		for (int i = 0; i < str.length(); i++) {
			if(Character.isDigit(str.charAt(i))||str.charAt(i)=='.'){
				num = num + str.charAt(i);
			}else if(str.charAt(i)=='s'||str.charAt(i)=='c') {
				num=str.charAt(i)+"";
				i=i+2;
				result.add(num);
				num="";
			}else{
				if(num != ""){
					result.add(num);
				}
				result.add(str.charAt(i) + "");
				num = "";
			}
		}
		if(num != ""){
			result.add(num);
		}
		return result;
	}
	
	/**
	 * ����׺���ʽת��Ϊ��׺���ʽ
	 * @param inOrderList
	 * @return
	 */
	public ArrayList<String> getPostOrder(ArrayList<String> inOrderList){
		
		ArrayList<String> result = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < inOrderList.size(); i++) {
			if(Character.isDigit(inOrderList.get(i).charAt(0))){
				result.add(inOrderList.get(i));
			}else{
				switch (inOrderList.get(i).charAt(0)) {
				case 's':
					stack.push("sin");
					break;
				case 'c':
					stack.push("cos");
					break;
				case '(':
					stack.push(inOrderList.get(i));
					break;
				case ')':
					while (!stack.peek().equals("(")) {
						result.add(stack.pop());
					}
					stack.pop();
					if(stack.peek().equals("sin")||stack.peek().equals("cos")) {
						result.add(stack.pop());
					}
					break;
				default:
					while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))){
						result.add(stack.pop());
					}
					stack.push(inOrderList.get(i));
					break;
				}
			}
		}
		while(!stack.isEmpty()){
			result.add(stack.pop());
		}
		return result;
	}
	
	
	/**
	 * �Ƚ�������ȼ�
	 * @param peek
	 * @param cur
	 * @return
	 */
	public static boolean compare(String peek, String cur){
		if("*".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
			return true;
		}else if("/".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
			return true;
		}else if("+".equals(peek) && ("+".equals(cur) || "-".equals(cur))){
			return true;
		}else if("-".equals(peek) && ("+".equals(cur) || "-".equals(cur))){
			return true;
		}
		return false;
	}
	
	
}
