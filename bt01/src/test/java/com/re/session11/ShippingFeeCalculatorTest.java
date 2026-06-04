package com.re.session11;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ShippingFeeCalculatorTest {

    private final ShippingFeeCalculator calculator = new ShippingFeeCalculator();

    @Test
    void testStandardCases() {
        // 1. Cân nặng <= 1kg, khoảng cách < 10km (50.000 + 0 = 50.000)
        assertThat(calculator.calculateFee(0.5, 5)).isEqualTo(50000);

        // 2. Cân nặng > 1kg (số nguyên), khoảng cách 10-50km (2kg, 20km)
        // Phí: 50.000 + (1*10.000) + (20*5.000) = 160.000
        assertThat(calculator.calculateFee(2.0, 20)).isEqualTo(160000);

        // 3. Cân nặng lẻ, khoảng cách > 50km (1.5kg, 60km)
        // Phí: 50.000 + (1*10.000) + (60*4.000) = 300.000
        assertThat(calculator.calculateFee(1.5, 60)).isEqualTo(300000);

        // 4. Biên khoảng cách (10km và 50km)
        assertThat(calculator.calculateFee(1.0, 10)).isEqualTo(100000); // 50k + 10*5k
        assertThat(calculator.calculateFee(1.0, 50)).isEqualTo(250000); // 50k + 50*4k

        // 5. Đầu vào không hợp lệ
        assertThatThrownBy(() -> calculator.calculateFee(0, 10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testStandardCasesFix() {
        // 1. Cân nặng <= 1kg, khoảng cách < 10km (50.000 + 0 = 50.000)
        assertThat(calculator.calculateFeeFix(0.5, 5)).isEqualTo(50000);

        // 2. Cân nặng > 1kg (số nguyên), khoảng cách 10-50km (2kg, 20km)
        // Phí: 50.000 + (1*10.000) + (20*5.000) = 160.000
        assertThat(calculator.calculateFeeFix(2.0, 20)).isEqualTo(160000);

        // 3. Cân nặng lẻ, khoảng cách > 50km (1.5kg, 60km)
        // Phí: 50.000 + (1*10.000) + (60*4.000) = 300.000
        assertThat(calculator.calculateFeeFix(1.5, 60)).isEqualTo(300000);

        // 4. Biên khoảng cách (10km và 50km)
        assertThat(calculator.calculateFeeFix(1.0, 10)).isEqualTo(100000); // 50k + 10*5k
        assertThat(calculator.calculateFeeFix(1.0, 50)).isEqualTo(250000); // 50k + 50*4k

        // 5. Đầu vào không hợp lệ
        assertThatThrownBy(() -> calculator.calculateFeeFix(0, 10))
                .isInstanceOf(IllegalArgumentException.class);
    }
}