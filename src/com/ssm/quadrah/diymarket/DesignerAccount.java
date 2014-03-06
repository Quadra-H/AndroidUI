package com.ssm.quadrah.diymarket;

import android.app.Application;

public class DesignerAccount extends Application{
	private String account;
	public String getAccount()
	{
		return account;		
	}
	public void setAccount(String account)
	{
		this.account =account;
	}
}
