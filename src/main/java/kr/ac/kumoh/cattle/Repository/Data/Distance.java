package kr.ac.kumoh.cattle.Repository.Data;

public class Distance {
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static double getDistance(double accident_latitude, double accident_longitude, double user_latitude, double user_longitude){
        double theta = accident_longitude - user_longitude;
        double dist = Math.sin(deg2rad(accident_latitude)) * Math.sin(deg2rad(user_latitude))
                + Math.cos(deg2rad(accident_latitude)) * Math.cos(deg2rad(user_latitude))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; //km
        //dist = dist * 1609.344; //m

        return dist;
    }

}
