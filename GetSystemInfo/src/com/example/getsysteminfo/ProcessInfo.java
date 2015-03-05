/**   
* @Title: ProcessInfo.java 
* @Package com.example.getsysteminfo 
* @Description: TODO
* @author tgx
* @date 2015-3-5 ����2:19:23 
* @version V1.0   
*/ 
package com.example.getsysteminfo;

import java.util.List;

/**
 * @ClassName: ProcessInfo
 * @Description: ������Ϣ�ķ�װ,���̵�ID,UID,ռ���ڴ��С�������������������а���
 * @author tgx
 * @date 2015-3-4 ����10:17:25
 * 
 */
public class ProcessInfo {
	private int pid;
	private int uid;
	private String processName;
	private int memSize;
	private List<String> packageNameList;

	public List<String> getPackageNameList() {
		return packageNameList;
	}

	public void setPackageNameList(List<String> packageNameList) {
		this.packageNameList = packageNameList;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public int getMemSize() {
		return memSize;
	}

	public void setMemSize(int memSize) {
		this.memSize = memSize;
	}

}
