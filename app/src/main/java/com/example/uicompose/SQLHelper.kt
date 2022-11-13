package com.example.uinativexml

import OrderModel
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class SQLHelper(context:Context) :SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    companion object{
        private const val DATABASE_VERSION=2
        private const val DATABASE_NAME="orders.db"
        private const val TBL_USERS="tbl_users"
        private const val TBL_ORDERS="tbl_orders"
        private const val UID="uid"
        private const val OID="oid"
        private const val NAME="name"
        private const val PRODUCTS="products"
        private const val DELADRESS="deladress"
        private const val PHONE="phone"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTabel=("CREATE TABLE "+ TBL_USERS + "("
                    + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " TEXT UNIQUE" +")"
                )
        db?.execSQL(createUserTabel)
        val createOrderTabel=("CREATE TABLE " + TBL_ORDERS + "("
                + OID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UID + " INTEGER REFERENCES "+ TBL_USERS+ "("+UID+"),"
                + PRODUCTS + " TEXT, "
                + DELADRESS + " TEXT, "
                + PHONE + " TEXT )"
                )
        db?.execSQL(createOrderTabel)

        val values = ContentValues()
        values.put(NAME,"username")
        db?.insert(TBL_USERS,null,values)
        val values2 = ContentValues()
        values2.put(NAME,"username2")
        db?.insert(TBL_USERS,null,values2)
        val values4 = ContentValues()
        values4.put(NAME,"username3")
        db?.insert(TBL_USERS,null,values4)
        val values3 = ContentValues()
        values3.put(UID,1)
        values3.put(PRODUCTS,"Ceva1")
        values3.put(DELADRESS,"Ceva2")
        values3.put(PHONE,"Ceva3")
        println("Cacat cu perje")
        println(db?.insert(TBL_ORDERS,null,values3))
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_ORDERS")
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USERS")
        onCreate(db)
    }

    fun insertOrder(order: OrderModel): Long{
        val db=this.writableDatabase

        val ContentValues=ContentValues()
        ContentValues.put(PRODUCTS,order.products)
        ContentValues.put(DELADRESS,order.deladress)
        ContentValues.put(PHONE,order.phone)
        ContentValues.put(UID,order.id)

        val success=db.insert(TBL_ORDERS,null,ContentValues)
        db.close()

        return success
    }

    fun deleteOrder(OrderId:Int){
        val db=this.writableDatabase
        db.delete(TBL_ORDERS,"$OID=$OrderId",null)
    }
    fun updateOrder(Order:OrderModel){
        val db=this.writableDatabase

        val ContentValues=ContentValues()
        ContentValues.put(PRODUCTS,Order.products)
        ContentValues.put(DELADRESS,Order.deladress)
        ContentValues.put(PHONE,Order.phone)
        ContentValues.put(UID,Order.uid)
        ContentValues.put(OID,Order.id)
        db.update(TBL_ORDERS,ContentValues,"$OID=?", arrayOf(Order.id.toString()))
    }
    @SuppressLint("Range")
    fun getOrder(OrderId: Int):OrderModel{
        val db=this.readableDatabase
        val selectQuery= "SELECT * FROM $TBL_ORDERS WHERE $OID=$OrderId"

        val cursor: Cursor?

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return OrderModel(-1,-1,"","","")
        }
        var products:String
        var phone:String
        var deladress:String
        if(cursor.moveToFirst()){
            products=cursor.getString(cursor.getColumnIndex("products"))
            phone=cursor.getString(cursor.getColumnIndex("phone"))
            deladress=cursor.getString(cursor.getColumnIndex("deladress"))
            val order=OrderModel(uid=0,id=0,products=products,phone=phone,deladress=deladress)
            return order
        }
        return OrderModel(-1,-1,"","","")
    }

    @SuppressLint("Range")
    fun getAllOrders(userID:Int):ArrayList<OrderModel>{
        val orderList:ArrayList<OrderModel> = ArrayList()
        val selectQuery= "SELECT * FROM $TBL_ORDERS WHERE $UID=$userID"
        val db=this.readableDatabase
        val cursor: Cursor?

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id:Int
        var uid:Int
        var products:String
        var phone:String
        var deladress:String
        if(cursor.moveToFirst()){
            do{
                uid=cursor.getInt(cursor.getColumnIndex("uid"))
                id=cursor.getInt(cursor.getColumnIndex("oid"))
                products=cursor.getString(cursor.getColumnIndex("products"))
                phone=cursor.getString(cursor.getColumnIndex("phone"))
                deladress=cursor.getString(cursor.getColumnIndex("deladress"))
                val order=OrderModel(uid=uid,id=id,products=products,phone=phone,deladress=deladress)
                orderList.add(order)

            }
                while (cursor.moveToNext())
        }
        return orderList
    }

    @SuppressLint("Range")
    fun checkUser(username: String):Int{
        val selectQuery= "SELECT * FROM $TBL_USERS WHERE $NAME = ?"
        val db=this.readableDatabase
        var id :Int
        val cursor: Cursor?
        try{
            cursor=db.rawQuery(selectQuery, arrayOf(username))
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return 0
        }
        if(cursor.moveToFirst()){
            id=cursor.getInt(cursor.getColumnIndex(UID))
            return id
        }
        return 0
    }

}