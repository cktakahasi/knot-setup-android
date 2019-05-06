package br.org.cesar.knot_setup_app.persistence.mysqlDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "KNoT_Devices" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table KNoT_Devices " + "(id integer primary key, name text,Channel text,NetName text, PanID text,XpanID text, Masterkey text, IPV6 text)");
        db.execSQL("create table KNoT_Things " + "(id integer primary key, name text,Channel text,NetName text, PanID text,XpanID text, Masterkey text, IPV6 text)");
        db.execSQL("create table gateway_Devices " + "(id_Gateway integer, id_Thing primary key)" );
        db.execSQL("create table account " + "(login text primary key, token text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS devices");
        onCreate(db);
    }

    public boolean insertDevice (Integer id,String name, String channel, String netName, String panID,String xpanID, String masterkey, String ipv6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("Channel", channel);
        contentValues.put("NetName", netName);
        contentValues.put("PanID", panID);
        contentValues.put("XpanID", xpanID);
        contentValues.put("MasterKey", masterkey);
        contentValues.put("IPV6", ipv6);
        db.insert("KNoT_Devices", null, contentValues);
        return true;
    }

    public boolean insertThing (Integer id,String name, String channel, String netName, String panID,String xpanID, String masterkey, String ipv6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("Channel", channel);
        contentValues.put("NetName", netName);
        contentValues.put("PanID", panID);
        contentValues.put("XpanID", xpanID);
        contentValues.put("MasterKey", masterkey);
        contentValues.put("IPV6", ipv6);
        db.insert("KNoT_Things", null, contentValues);
        return true;
    }

    public boolean insertAccount(String login, String token){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login",login);
        contentValues.put("token",token);
        db.replace("account", null,contentValues);
        return true;
    }



    public boolean insertGatewayThing(Integer gatewayID, Integer thingID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_Gateway",gatewayID);
        contentValues.put("id_Thing",thingID);
        db.insert("gateway_Devices", null, contentValues);
        return true;
    }

    public Cursor getData(String column, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from KNoT_Devices where " + column + "=" + id +"", null );
        res.moveToFirst();
        return res;
    }

    public String getData(String column, String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from KNoT_Devices where " + column+"=" + "'" + id + "'" , null );
        res.moveToFirst();
        return res.getString(res.getColumnIndex("id"));
    }

    public boolean updateKNoTDevice (Integer id,String name, String channel, String netName, String panID,String xpanID, String masterkey, String ipv6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("Channel", channel);
        contentValues.put("NetName", netName);
        contentValues.put("PanID", panID);
        contentValues.put("XpanID", xpanID);
        contentValues.put("MasterKey", masterkey);
        contentValues.put("IPV6", ipv6);
        db.update("KNoT_Devices", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteKNoTDevice (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("KNoT_Devices",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }



    public ArrayList<String> getAllKNoTDevices() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from KNoT_Devices", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("name")));
            res.moveToNext();
        }
        return array_list;
    }


    public ArrayList<String> getAllGatewayThings() {
        ArrayList<String> array_list_ID = new ArrayList<String>();
        ArrayList<String> array_list_Names = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from gateway_Devices", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list_ID.add(res.getString(res.getColumnIndex("id_Thing")));
            res.moveToNext();
        }

        for (String things : array_list_ID){
            res =  db.rawQuery( "select * from KNoT_Things where " + "id" + "="  + things , null );
            res.moveToFirst();
            array_list_Names.add(res.getString(res.getColumnIndex("name")));
        }
        return array_list_Names;
    }

    public Cursor getAccount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from account", null );
        return res;
    }


}
