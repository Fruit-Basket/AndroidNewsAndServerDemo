package com.example.mynews;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONManager {
	
	public JSONManager(){};
	
	/**
     * ��JSON�ַ����е�ָ��������ֵ����Ϊ��Ӧ���ַ�����
     * jsonString : JSON��ʽ���ַ�����json��ÿ�����б������ָ���ļ���
     * valueName��ָ���ļ���
     * @throws JSONException 
     */
    protected static String[] jsonToStrings(String jsonString,String valueName) 
    		throws JSONException{
    	JSONArray jsonArray=new JSONArray(jsonString);
    	StringBuilder stringBuilder=new StringBuilder();
    	JSONObject jsonObject;
    	
    	for(int i=0;i<jsonArray.length();i++){
    		jsonObject=jsonArray.getJSONObject(i);
    		stringBuilder.append(jsonObject.getString(valueName));
    		stringBuilder.append(",");    //��","Ϊ�ָ���
    	}
    	stringBuilder.deleteCharAt(stringBuilder.length()-1);
		return stringBuilder.toString().split(",");
    }
	
    /**
     * ��ָ���ķ������˿ڴ�����ȡ�ַ����������ȡ����JSON��ʽ���ַ���
     * @param hostAddress Ŀ��������ַ������ʹ��ip��ַ
     * @param port
     * @return
     */
    protected static String readJSONString (String hostAddress,int port)
    		throws UnknownHostException,ConnectException,IOException{
    	StringBuilder stringBuilder=new StringBuilder();
    	
    	Socket socket=Connection.connectToServer(hostAddress,port);
    	Scanner input=new Scanner(socket.getInputStream());
    	
    	while(input.hasNext()==true){
    			stringBuilder.append(input.nextLine());	//�����������������еĻ��з�
    	}
		return stringBuilder.toString();
    }
}
