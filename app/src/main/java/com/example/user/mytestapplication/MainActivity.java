package com.example.user.mytestapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView myList;

    String[] actions = {"Open Contacts", "Open Camera", "Send Mail", "Call", "Send Message", "Open Music", "Open Browser"};
    String[] subTitles = {"See List of Contacts", "Take Photo", "Send Mail", "Make Call", "Send Message", "Lisen To Music", "Browse Information"};
    int[] actionIcons = {R.drawable.contacts, R.drawable.camera, R.drawable.mail, R.drawable.call, R.drawable.message, R.drawable.music, R.drawable.browser};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myList = (ListView) findViewById(R.id.listView);
        BaseAdapter adapter = new myTestAdapter(this, actions, subTitles, actionIcons);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Intent contact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivity(contact);
            //Toast.makeText(this,"You have clicked --------------------------",Toast.LENGTH_SHORT).show();
        } else if (position == 1) {
            Intent camera = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(camera);

        } else if (position == 2) {
            Intent mail = new Intent(Intent.ACTION_SEND);
            mail.setData(Uri.parse("mailto:"));
            mail.setType("message/rfc822");
            startActivity(mail);
        } else if (position == 3) {
            Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:8749007297"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                startActivity(call);
            }

            startActivity(call);

        }
        else if(position == 4)
        {
            Intent msg = new Intent(Intent.ACTION_VIEW);
            msg.putExtra("sms_body","Hi this is test message from my app");
            msg.setType("vnd.android-dir/mms-sms");
            startActivity(msg);
        }
        else if(position == 5)
        {
            Intent music = new Intent("android.intent.action.MUSIC_PLAYER");
            startActivity(music);
        }
        else if(position == 6)
        {
            Intent browser = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
            startActivity(browser);
        }
        else
        {
            TextView txt = (TextView) view.findViewById(R.id.textView);
            Toast.makeText(this,"You have clicked "+ txt.getText(),Toast.LENGTH_SHORT).show();
        }

    }
}

class SingleRow
{
    String action;
    String subTitle;
    int actionIcon;
    SingleRow( String action, String subTitle, int actionIcon){
        this.action = action;
        this.subTitle = subTitle;
        this.actionIcon = actionIcon;
    }

}
class myTestAdapter extends BaseAdapter
{

    Context context;
    ArrayList<SingleRow> list = new ArrayList<>();
    String[] actions;
    String[] subTitles;
    int[] actionIcons;
    myTestAdapter(Context context, String[] actions, String[] subTitles, int[] actionIcons){
        this.context = context;
        this.actions = actions;
        this.subTitles = subTitles;
        this.actionIcons = actionIcons;
        for(int i= 0;i<this.actions.length;i++)
        {
            SingleRow item = new SingleRow(this.actions[i],this.subTitles[i],this.actionIcons[i]);
            list.add(item);
        }


    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_item,parent,false);

        TextView actionText = (TextView) row.findViewById(R.id.textView);
        TextView subTitleText = (TextView) row.findViewById(R.id.textView2);
        ImageView iconImage = (ImageView) row.findViewById(R.id.imageView);

        SingleRow temp = list.get(position);
        actionText.setText(temp.action);
        subTitleText.setText(temp.subTitle);
        iconImage.setImageResource(temp.actionIcon);
        return row;
    }
}