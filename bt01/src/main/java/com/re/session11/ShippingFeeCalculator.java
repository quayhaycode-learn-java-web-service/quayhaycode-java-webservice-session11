package com.re.session11;

public class ShippingFeeCalculator {

    public double calculateFee(double weightKg, double distanceKm) {
        if (weightKg <= 0 || distanceKm <= 0) {
            throw new IllegalArgumentException("Weight and distance must be positive");
        }

        double weightFee = 0;
        if (weightKg <= 1) {
            weightFee = 50000;
        } else {
            weightFee = 50000 + (Math.floor(weightKg - 1) * 10000);
        }

        double distanceFee = 0;
        if (distanceKm < 10) {
            distanceFee = 0;
        } else if (distanceKm < 50) {
            distanceFee = distanceKm * 5000;
        } else {
            distanceFee = distanceKm * 4000;
        }

        return weightFee + distanceFee;
    }

    public double calculateFeeFix(double weightKg, double distanceKm) {
        if (weightKg <= 0 || distanceKm <= 0) {
            throw new IllegalArgumentException("Weight and distance must be positive");
        }

        // Sửa logic cân nặng: Sử dụng Math.ceil cho phần vượt quá 1kg
        double weightFee = 50000;
        if (weightKg > 1) {
            weightFee += Math.ceil(weightKg - 1) * 10000;
        }

        // Sửa logic khoảng cách: Đảm bảo không bị nhảy bậc sai lệch
        double distanceFee = 0;
        if (distanceKm >= 50) {
            distanceFee = distanceKm * 4000;
        } else if (distanceKm >= 10) {
            distanceFee = distanceKm * 5000;
        } else {
            distanceFee = 0;
        }

        return weightFee + distanceFee;
    }
}