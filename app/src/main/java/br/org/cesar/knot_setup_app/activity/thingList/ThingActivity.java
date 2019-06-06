package br.org.cesar.knot_setup_app.activity.thingList;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.org.cesar.knot_setup_app.R;
import br.org.cesar.knot_setup_app.activity.scan.ScanActivity;
import br.org.cesar.knot_setup_app.domain.adapter.DeviceAdapter;
import br.org.cesar.knot_setup_app.activity.thingList.ThingContract.Presenter;
import br.org.cesar.knot_setup_app.activity.thingList.ThingContract.ViewModel;
import br.org.cesar.knot_setup_app.model.Gateway;

public class ThingActivity extends AppCompatActivity implements  ViewModel{
    private DeviceAdapter adapter;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things_list);


        TextView headerTitle = (TextView)findViewById(R.id.list_title);
        headerTitle.setText("THINGs");
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        image.setImageResource(R.drawable.knot);

        final int gatewayID = getIntent().getIntExtra("gatewayID",0);
        mPresenter = new ThingPresenter(this,gatewayID,this);

        FloatingActionButton addButton = findViewById(R.id.add_thing);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThingActivity.this, ScanActivity.class );
                i.putExtra("operation",true);
                i.putExtra("gatewayID",gatewayID);
                startActivity(i);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getDeviceList();
    }


    /**
     * Setup current devices list with smart devices saved on database
     * @param deviceList
     */

    @Override
    public void callbackOnDeviceList(List<Gateway> deviceList){

        adapter = new DeviceAdapter(ThingActivity.this, R.layout.item_gateway, deviceList);

        ListView deviceListView = findViewById(R.id.thing_list);

        deviceListView.setAdapter(adapter);

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}