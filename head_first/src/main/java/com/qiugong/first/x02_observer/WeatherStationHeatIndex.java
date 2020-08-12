package com.qiugong.first.x02_observer;

import com.qiugong.first.x02_observer.observer.CurrentConditionsDisplay;
import com.qiugong.first.x02_observer.observer.ForecastDisplay;
import com.qiugong.first.x02_observer.observer.HeatIndexDisplay;
import com.qiugong.first.x02_observer.observer.StatisticsDisplay;

public class WeatherStationHeatIndex {

	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
		StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
		HeatIndexDisplay heatIndexDisplay = new HeatIndexDisplay(weatherData);

		// Current conditions: 80.0F degrees and 65.0% humidity
		// Avg/Max/Min temperature = 80.0/80.0/80.0
		// Forecast: Improving weather on the way!
		// Heat index is 82.95535
		weatherData.setMeasurements(80, 65, 30.4f);

		// Current conditions: 82.0F degrees and 70.0% humidity
		// Avg/Max/Min temperature = 81.0/82.0/80.0
		// Forecast: Watch out for cooler, rainy weather
		// Heat index is 86.90124
		weatherData.setMeasurements(82, 70, 29.2f);

		// Current conditions: 78.0F degrees and 90.0% humidity
		// Avg/Max/Min temperature = 80.0/82.0/78.0
		// Forecast: More of the same
		// Heat index is 83.64967
		weatherData.setMeasurements(78, 90, 29.2f);
	}
}
