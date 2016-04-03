package util;

import db.ChinaWeatherDB;
import model.City;
import model.County;
import model.Province;
import android.R.string;
import android.text.TextUtils;

public class Utility {
	/**
	 * 解析和处理服务器返回的省级数据
	 */
	public synchronized static boolean handleProvinceResponse(ChinaWeatherDB chinaWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");//分析规则先按逗号分隔
			if (allProvinces != null && allProvinces.length > 0) {
				for (String p : allProvinces) {
					String[] array = p.split("\\|");//在按单竖线分隔
					//将解析出来的数据设置到实体类
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					//将解析出来的数据存储到Province表中
					chinaWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * 解析和处理服务器返回的市级数据
	 */
	public static boolean handlerCitiesReaponse(ChinaWeatherDB chinaWeatherDB, String response,int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");//分析规则先按逗号分隔
			if (allCities != null && allCities.length > 0) {
				for (String p : allCities) {
					String[] array = p.split("\\|");//在按单竖线分隔
					//将解析出来的数据设置到实体类
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					//将解析出来的数据存储到Province表中
					chinaWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * 解析和处理服务器返回的县级数据
	 */
	public static boolean handlerCountiesReaponse(ChinaWeatherDB chinaWeatherDB, String response,int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");//分析规则先按逗号分隔
			if (allCounties != null && allCounties.length > 0) {
				for (String p : allCounties) {
					String[] array = p.split("\\|");//在按单竖线分隔
					//将解析出来的数据设置到实体类
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					//将解析出来的数据存储到Province表中
					chinaWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
}
