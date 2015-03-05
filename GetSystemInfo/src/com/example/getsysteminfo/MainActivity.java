package com.example.getsysteminfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private TextView tvAvailMem;
	private Button btProcessInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvAvailMem = (TextView) findViewById(R.id.tvAvailMemory);
		btProcessInfo = (Button) findViewById(R.id.btProcessInfo);
		// 跳转到显示进程信息界面
		btProcessInfo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent intent = new
				 * Intent(MainActivity.this,BrowseProcessInfoActivity.class);
				 * startActivity(intent);
				 */
				AlarmReadInfo();
			}
		});
		SystemMemory systemMemory = getSystemAvailableMemorySize();
		String availMemStr = formateFileSize(systemMemory.getMemorySize());
		tvAvailMem.setText(availMemStr);
	}

	/**
	 * @Title: AlarmReadInfo
	 * @Description: 定时读取系统信息
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	public void AlarmReadInfo() {
		final Handler myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					Intent intent = new Intent(MainActivity.this,
							BrowseProcessInfoActivity.class);
					startActivity(intent);
				}
			}
		};
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				myHandler.sendEmptyMessage(0x123);
			}
		}, 0, 1200);
	}

/*	public void AlarmGetInfo() {
		new Timer().schedule(new MyTimerTask(), 0, 1200);
	}

	class MyTimerTask extends TimerTask {
		private SystemMemory systemMemory = new SystemMemory();
		private List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();

		@Override
		public void run() {
			systemMemory = getSystemAvailableMemorySize();
			processInfoList = getProcessInfo();
		}
	}
*/
	/**
	 * @ClassName: SystemMemory
	 * @Description: 系统内存信息的封装，包括系统当前可用内存大小、内存阀值、是否低于阀值
	 * @author 
	 * @date 2015-3-4 下午10:22:01
	 * 
	 */
	class SystemMemory {
		private long memorySize;
		private long thresholdSize;
		private boolean lowMemoryFlag;

		public long getMemorySize() {
			return memorySize;
		}

		public void setMemorySize(long memorySize) {
			this.memorySize = memorySize;
		}

		public long getThresholdSize() {
			return thresholdSize;
		}

		public void setThresholdSize(long thresholdSize) {
			this.thresholdSize = thresholdSize;
		}

		public boolean isLowMemoryFlag() {
			return lowMemoryFlag;
		}

		public void setLowMemoryFlag(boolean lowMemoryFlag) {
			this.lowMemoryFlag = lowMemoryFlag;
		}

	}

	/**
	 * @Title: getSystemAvailableMemorySize
	 * @Description: 获取系统当前可用内存、内存的阀值以及当前可用内存是否低于阀值
	 * @param aManager
	 * @return String
	 * @throws
	 */
	public SystemMemory getSystemAvailableMemorySize() {
		ActivityManager aManager;
		MemoryInfo memoryInfo = new MemoryInfo();
		SystemMemory systemMemory = new SystemMemory();
		aManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		aManager.getMemoryInfo(memoryInfo);
		systemMemory.setMemorySize(memoryInfo.availMem);
		systemMemory.setThresholdSize(memoryInfo.threshold);
		systemMemory.setLowMemoryFlag(memoryInfo.lowMemory);
		return systemMemory;
	}

	// 调用系统函数，字符串转换long->String KB/MB
	private String formateFileSize(long size) {
		return Formatter.formatFileSize(MainActivity.this, size);
	}
	/**
	 * @Title: getProcessInfo
	 * @Description: 获取进程的ID,UID,占用内存大小，进程名，进程内运行包名
	 * @param @return
	 * @return List<ProcessInfo> 返回类型
	 * @throws
	 */
	public List<ProcessInfo> getProcessInfo() {
		ActivityManager aManager;
		aManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcessList = aManager
				.getRunningAppProcesses();
		List<ProcessInfo> processInfoList = new ArrayList<ProcessInfo>();
		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
			int pid = appProcessInfo.pid;
			int uid = appProcessInfo.uid;
			String processName = appProcessInfo.processName;
			int[] myPid = new int[] { pid };
			Debug.MemoryInfo[] memoryInfo = aManager
					.getProcessMemoryInfo(myPid);
			int memSize = memoryInfo[0].dalvikPrivateDirty;
			String[] packageName = appProcessInfo.pkgList;
			ProcessInfo processInfo = new ProcessInfo();
			processInfo.setPid(pid);
			processInfo.setUid(uid);
			processInfo.setProcessName(processName);
			processInfo.setMemSize(memSize);
			List<String> packageList = new ArrayList<String>();
			for (int i = 0; i < packageName.length; i++) {
				packageList.add(packageName[i]);
			}
			processInfo.setPackageNameList(packageList);
			processInfoList.add(processInfo);
		}
		return processInfoList;
	}

}
