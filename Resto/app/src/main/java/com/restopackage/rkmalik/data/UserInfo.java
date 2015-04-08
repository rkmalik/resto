package com.restopackage.rkmalik.data;

/**
 * Created by vicks on 11/03/15.
 */
public class UserInfo {
    private boolean isLocationTracking = false;
    private String language = null;
    private String cityName = null;

    public boolean isLocationTracking() {
        return isLocationTracking;
    }

    public void setLocationTracking(boolean isLocationTracking) {
        this.isLocationTracking = isLocationTracking;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
