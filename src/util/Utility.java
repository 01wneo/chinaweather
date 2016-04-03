package util;

import db.ChinaWeatherDB;
import model.City;
import model.County;
import model.Province;
import android.R.string;
import android.text.TextUtils;

public class Utility {
	/**
	 * �����ʹ�����������ص�ʡ������
	 */
	public synchronized static boolean handleProvinceResponse(ChinaWeatherDB chinaWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");//���������Ȱ����ŷָ�
			if (allProvinces != null && allProvinces.length > 0) {
				for (String p : allProvinces) {
					String[] array = p.split("\\|");//�ڰ������߷ָ�
					//�������������������õ�ʵ����
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					//���������������ݴ洢��Province����
					chinaWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * �����ʹ�����������ص��м�����
	 */
	public static boolean handlerCitiesReaponse(ChinaWeatherDB chinaWeatherDB, String response,int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");//���������Ȱ����ŷָ�
			if (allCities != null && allCities.length > 0) {
				for (String p : allCities) {
					String[] array = p.split("\\|");//�ڰ������߷ָ�
					//�������������������õ�ʵ����
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					//���������������ݴ洢��Province����
					chinaWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * �����ʹ�����������ص��ؼ�����
	 */
	public static boolean handlerCountiesReaponse(ChinaWeatherDB chinaWeatherDB, String response,int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");//���������Ȱ����ŷָ�
			if (allCounties != null && allCounties.length > 0) {
				for (String p : allCounties) {
					String[] array = p.split("\\|");//�ڰ������߷ָ�
					//�������������������õ�ʵ����
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					//���������������ݴ洢��Province����
					chinaWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
}
