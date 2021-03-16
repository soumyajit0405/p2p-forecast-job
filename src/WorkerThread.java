
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

class WorkerThread implements Runnable {
	public  int userId;
	public String usn;
	
	public WorkerThread(int userId, String usn) {
		this.userId = userId;
		this.usn = usn;
	}

	public void run() {
		try {
			getForecastDataFromAgent();
						
	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		finally {
			if (ScheduleDAO.con != null) {
//				try {
//			//		ScheduleDAO.con.close();  Close later
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
				}
		System.out.println(Thread.currentThread().getName() + " (End)");// prints thread name
	}

	private void processmessage() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void getForecastDataFromAgent() throws ClassNotFoundException, SQLException,IOException {
		HttpConnectorHelper httpconnectorhelper= new HttpConnectorHelper();
		int response = httpconnectorhelper
				.sendPostWithToken("http://localhost:8087/rest/getForecastFromDevice/"+usn);
		
		
	}
}