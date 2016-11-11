package com.nao20010128nao.UUIDgen;

import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.security.*;

public class MainActivity extends AppCompatActivity {
	Button generate;
	TextView result;
	EditText count;
	ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		generate=(Button)findViewById(R.id.generate);
		result=(TextView)findViewById(R.id.result);
		count=(EditText)findViewById(R.id.count);
		progress=(ProgressBar)findViewById(R.id.progress);
		
		generate.setOnClickListener(new View.OnClickListener(){
			public void onClick(View b){
				progress.setProgress(0);
				progress.setMax(new Integer(count.getText().toString()));
				new AsyncTask<Integer,Integer,List<UUID>>(){
					public List<UUID> doInBackground(Integer... a){
						int count=a[0];
						List<UUID> uuids=new ArrayList<>();
						SecureRandom sr=new SecureRandom();
						byte[] buf=new byte[10000];
						for(int i=0;i<count;i++){
							sr.nextBytes(buf);
							uuids.add(UUID.nameUUIDFromBytes(buf));
							publishProgress(i+1);
						}
						return uuids;
					}
					
					public void onProgressUpdate(Integer... a){
						int prog=a[0];
						progress.setProgress(prog);
					}
					
					public void onPostExecute(List<UUID> uuids){
						StringBuilder sb=new StringBuilder();
						for(UUID uuid:uuids)
							sb.append(uuid).append('\n');
						result.setText(sb);
					}
				}.execute(new Integer(count.getText().toString()));
			}
		});
    }
}
