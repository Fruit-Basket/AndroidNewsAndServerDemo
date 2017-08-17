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
     * 将JSON字符串中的指定键名的值解析为对应的字符数组
     * jsonString : JSON格式的字符串，json中每个块中必须包含指定的键名
     * valueName：指定的键名
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
    		stringBuilder.append(",");    //以","为分隔符
    	}
    	stringBuilder.deleteCharAt(stringBuilder.length()-1);
		return stringBuilder.toString().split(",");
    }
	
    /**
     * 从指定的服务器端口处，读取字符串，这里读取的是JSON格式的字符串
     * @param hostAddress 目标主机地址，这里使用ip地址
     * @param port
     * @return
     */
    protected static String readJSONString (String hostAddress,int port)
    		throws UnknownHostException,ConnectException,IOException{
    	StringBuilder stringBuilder=new StringBuilder();
    	
    	Socket socket=Connection.connectToServer(hostAddress,port);
    	Scanner input=new Scanner(socket.getInputStream());
    	
    	while(input.hasNext()==true){
    			stringBuilder.append(input.nextLine());	//这里跳过了输入流中的换行符
    	}
		return stringBuilder.toString();
    }
}
