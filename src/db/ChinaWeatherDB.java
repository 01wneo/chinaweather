package db;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.County;
import model.Province;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ChinaWeatherDB {// ChinaWeatherDB是一个单例类，将它的构造方法私有化
	/**
	 * 数据库名字
	 */
	public static final String DB_NAME = "cool_weather";
	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;

	public static ChinaWeatherDB chinaWeatherDB;

	private SQLiteDatabase db;

	/**
	 * 将构造方法私有化**ChinaWeatherDB是一个单例类，将它的构造方法私有化
	 */

	private ChinaWeatherDB(Context context) {
		ChinaWeatherOpenHelper dbHelper = new ChinaWeatherOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 获取ChinaWeatherDB的实例 为了保证全局范围内只会有一个ChinaWeatherDB的实例
	 */
	public synchronized static ChinaWeatherDB getInstance(Context context) {
		if (chinaWeatherDB == null) {// 通过getInstance()来获取ChinaWeatherDB的实例
			chinaWeatherDB = new ChinaWeatherDB(context);
		}
		return chinaWeatherDB;
	}

	/*
	 * 提供6组方法， 
	 * saveProvince()==存储省份数据 
	 * loadProvince()==读取所有省份数据
	 * saveCity()==存储城市数据 
	 * loadCities()==读取某省内所有城市数据
	 * saveCounty()==存储县数据
	 * loadCounties()==读取某市内所有县的数据
	 */
	/**
	 * 将Province实例存储到数据库
	 */
	public void saveProvince(Province province) {
		// TODO Auto-generated method stub
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * 从数据库中读取全国的所有的省份信息
	 */
	public List<Province> loadProvince() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
		// TODO Auto-generated method stub

	}

	/**
	 * 将City实例存储到数据库
	 */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	/**
	 * 从数据库读取某省下所有的城市信息
	 */
	public List<City> loadCities(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?",
				new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

	/**
	 * 将County实例存储到数据库
	 */
	public void saveCounty(County county) {
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}

	/**
	 * 从数据库读取某城市下所有的具体信息
	 */
	public List<County> loadCounties(int cityId) {
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor
						.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getColumnName(cursor
						.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			} while (cursor.moveToNext());
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}
}
