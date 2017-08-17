package com.example.mynews;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView=this.getListView();
        
        new ReadJSONStringTask().execute();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	
    	//向ActionBar添加一个刷新按钮
    	MenuItem refreshItem=menu.add(0,0,0,"刷新");
        refreshItem.setIcon(R.drawable.titlebar_button_refresh);
        refreshItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    	
		return true;
    }
    
    /**
     * 列表项单击处理函数
     * position：被单击的列表项的位置
     * id：被单击的列表项的id
     */
    public void onListItemClick(ListView parent,View v,int position,long id){
    	//Toast.makeText(this, "newsTitles[position]="+newsTitles[position]+" , id="+id, Toast.LENGTH_SHORT).show();
    }
    
 
    private class ReadJSONStringTask extends AsyncTask<Void,String,String>{
    	/**
    	 * 直接从指定的主机和端口（由Connection类定义）上读取数据
    	 */
		@Override
		protected String doInBackground(Void... params) {
			String newsTitlesJSON="";
			try {
				publishProgress("从服务器读取数据...");
				//直接从指定的主机和端口上读取数据
	        	newsTitlesJSON=JSONManager.readJSONString(Connection.SERVER_IP,Connection.SERVER_PORT);
	        	publishProgress("数据读取完毕");
			}
	        catch(UnknownHostException uhe){
				uhe.printStackTrace();
				publishProgress("服务器IP地址设置错误，请重新设置IP");
	        }
	        catch(ConnectException ce){
				ce.printStackTrace();
				publishProgress("服务器未启动，请开启服务器后，重启本程序");
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				publishProgress("请求服务器数据出错");
			}
	        catch(Exception e){
	        	e.printStackTrace();
	        	publishProgress("客户端出错");
	        }
			return newsTitlesJSON; 
		}
		
		@Override
		protected void onProgressUpdate(String...details){
			Toast.makeText(getBaseContext(), details[0], Toast.LENGTH_SHORT).show();
		}
		
		/**
		 * 设置前台页面中ListView的适配器
		 */
		@Override
		protected void onPostExecute(String newsTitlesJSON) {
			String[] newsTitles=null;
			try {
				newsTitles=JSONManager.jsonToStrings(newsTitlesJSON, "newsTitle");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			//设置前台页面中ListView的适配器
			final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,newsTitles);
            listView.post(new Runnable(){

				@Override
				public void run() {
					listView.setAdapter(arrayAdapter);
				}
            	
            });
		}
    }//End Class
    
}