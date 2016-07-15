package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class CoolWeatherDB {

	/*数据库名*/
	public static final String DB_NAME = "cool_weather";
	
	/*数据库版本*/
	public static final int VERTION=1;
	private static CoolWeatherDB coolWeatherDB;
	private static SQLiteDatabase db;
	
	/*将构造方法私有化*/
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERTION);
		db = dbHelper.getReadableDatabase();
	}
	
	/*获取CoolWeatherDB实例*/
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/*将Province实例存储到数据库*/
	public static void saveProvince(Province province) {
		if(province != null){
		ContentValues value = new ContentValues();
		value.put("province_name", province.getProvinceName());
		value.put("province_code", province.getProvinceCode());
		db.insert("Province", null, value);
		}
	}
	
	/*从数据库读取全国所有省份信息*/
	public static List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null,  null,  null,  null,  null,  null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
	
	/*将City实例存储到数据库*/
	public static void saveCity(City city){
		if(city != null){
			ContentValues value = new ContentValues();
			value.put("city_name", city.getCityName());
			value.put("city_code", city.getCityCode());
			value.put("province_id", city.getProvinceId());
			db.insert("City", null, value);
		}
	}
	
	/*从数据库读取某省下所有城市信息*/
	public static List<City> loadCity(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null,  "province_id=?",  new String[]{String.valueOf(provinceId)},  null,  null,  null);
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
	
	/*将County实例存储到数据库*/
	public static void saveCounty(County county){
		if(county != null){
			ContentValues value = new ContentValues();
			value.put("county_name", county.getCountyName());
			value.put("county_code", county.getCountyCode());
			value.put("city_id", county.getCityId());
			db.insert("County", null, value);
		}
	}
	
	/*从数据库读取某城市下所有的县信息*/
	public static List<County> loadCounty(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null,  "city_id=?",  new String[]{String.valueOf(cityId)},  null,  null,  null);
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			}while(cursor.moveToNext());
		}
		if(cursor != null){
			cursor.close();
		}
		return list;
	}
}
