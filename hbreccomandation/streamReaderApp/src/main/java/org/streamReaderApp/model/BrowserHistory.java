package org.streamReaderApp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BrowserHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String productid;
	private String userid;
	private LocalDateTime when;

	protected BrowserHistory() {
	}

	public BrowserHistory(String productid, String userid, LocalDateTime when) {
		this.productid = productid;
		this.userid = userid;
		this.when = when;
	}

	public Long getId() {
		return id;
	}

	public String getProductid() {
		return productid;
	}

	public String getUserid() {
		return userid;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%d, firstName='%s', lastName='%s', when='%s']", id, productid, userid, when);
	}

	public LocalDateTime getWhen() {
		return when;
	}

}
