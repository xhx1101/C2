package cn.edu.ncepu.sa.activemq;

public class Add extends C2Component {

	
	public Add(String host, int port, String user, String pwd) {
		super(host, port, user, pwd,"Add");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	String getResult(String msg) {
		String[] tmp = msg.split(",");
		if (tmp.length >= 3) {
			if (tmp[0].equals("+")) {
				double x = Double.parseDouble(tmp[1]);
				double y = Double.parseDouble(tmp[2]);
				return String.valueOf((x + y));
			}
		}
		
		
		
		return null;
	}

}
