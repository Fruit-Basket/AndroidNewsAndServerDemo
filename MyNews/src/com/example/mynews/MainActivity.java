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
    	
    	//��ActionBar���һ��ˢ�°�ť
    	MenuItem refreshItem=menu.add(0,0,0,"ˢ��");
        refreshItem.setIcon(R.drawable.titlebar_button_refresh);
        refreshItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    	
		return true;
    }
    
    /**
     * �б����������
     * position�����������б����λ��
     * id�����������б����id
     */
    public void onListItemClick(ListView parent,View v,int position,long id){
    	//Toast.makeText(this, "newsTitles[position]="+newsTitles[position]+" , id="+id, Toast.LENGTH_SHORT).show();
    }
    
 
    private class ReadJSONStringTask extends AsyncTask<Void,String,String>{
    	/**
    	 * ֱ�Ӵ�ָ���������Ͷ˿ڣ���Connection�ඨ�壩�϶�ȡ����
    	 */
		@Override
		protected String doInBackground(Void... params) {
			String newsTitlesJSON="";
			try {
				publishProgress("�ӷ�������ȡ����...");
				//ֱ�Ӵ�ָ���������Ͷ˿��϶�ȡ����
	        	newsTitlesJSON=JSONManager.readJSONString(Connection.SERVER_IP,Connection.SERVER_PORT);
	        	publishProgress("���ݶ�ȡ���");
			}
	        catch(UnknownHostException uhe){
				uhe.printStackTrace();
				publishProgress("������IP��ַ���ô�������������IP");
	        }
	        catch(ConnectException ce){
				ce.printStackTrace();
				publishProgress("������δ�������뿪��������������������");
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				publishProgress("������������ݳ���");
			}
	        catch(Exception e){
	        	e.printStackTrace();
	        	publishProgress("�ͻ��˳���");
	        }
			return newsTitlesJSON; 
		}
		
		@Override
		protected void onProgressUpdate(String...details){
			Toast.makeText(getBaseContext(), details[0], Toast.LENGTH_SHORT).show();
		}
		
		/**
		 * ����ǰ̨ҳ����ListView��������
		 */
		@Override
		protected void onPostExecute(String newsTitlesJSON) {
			String[] newsTitles=null;
			try {
				newsTitles=JSONManager.jsonToStrings(newsTitlesJSON, "newsTitle");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			//����ǰ̨ҳ����ListView��������
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