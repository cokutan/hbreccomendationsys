package org.streamReaderApp.viewproductmessageconsumer;


import java.time.LocalDateTime;

public class ViewProductMessage {
	String event;
	String   messageid;
	String userid;
	LocalDateTime when;
	Properties	 properties;
	Context context;
	
	public ViewProductMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public String getEvent() {
		return event;
	}
	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	
	
		 
	
	class Properties {
		private String productid;

		public Properties () {
			
		}
		public String getProductid() {
			return productid;
		}

		public void setProductid(String productid) {
			this.productid = productid;
		}
	}
	private class Context {
		private String source;
		
		public Context() {
			
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}
	}
}
