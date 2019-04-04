package cn.edu.ncepu.sa.activemq;

public class Cos extends C2Component{
	public Cos(String host, int port, String user, String pwd) {
		super(host, port, user, pwd,"Cos");
		// TODO Auto-generated constructor stub
	}

	@Override
	String getResult(String msg) {
		// TODO Auto-generated method stub
		String[] tmp = msg.split(",");
		if (tmp.length >= 2) {
			if (tmp[0].equals("cos")) {
				double x = Double.parseDouble(tmp[1]);
				double a = Math.toRadians(x);
				return String.valueOf(String.format("%.2f",  Math.cos(a)));
			}
		}
		return null;
	}

}