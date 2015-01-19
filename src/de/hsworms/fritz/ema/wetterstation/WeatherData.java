package de.hsworms.fritz.ema.wetterstation;

/**
 * Created by fritz on 28/11/14.
 */
public class WeatherData {

    private String timestamp;
    private String baroTendency;
    private String airPressure;
    private String temperature;
    private String windSpeed;
    private String windAverageSpeed;
    private String windDirectionDeg;
    private String windDirectionString;
    private String humidity;
    private String rainRate;
    private String isRaining;
    private String uvLevel;
    private String sunImpact;
    private String rainToday;
    private String rainMonth;
    private String rainYear;
    private String evapotranspirationToday;
    private String evapotranspirationMonth;

    public WeatherData(){

    }

    public WeatherData(String timestamp, String baroTendency, String airPressure, String temperature, String windSpeed, String windAverageSpeed, String windDirectionDeg, String windDirectionString, String humidity, String rainRate, String isRaining, String uvLevel, String sunImpact, String rainToday, String rainMonth, String rainYear, String evapotranspirationToday, String evapotranspirationMonth) {
        this.timestamp = timestamp;
        this.baroTendency = baroTendency;
        this.airPressure = airPressure;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windAverageSpeed = windAverageSpeed;
        this.windDirectionDeg = windDirectionDeg;
        this.windDirectionString = windDirectionString;
        this.humidity = humidity;
        this.rainRate = rainRate;
        this.isRaining = isRaining;
        this.uvLevel = uvLevel;
        this.sunImpact = sunImpact;
        this.rainToday = rainToday;
        this.rainMonth = rainMonth;
        this.rainYear = rainYear;
        this.evapotranspirationToday = evapotranspirationToday;
        this.evapotranspirationMonth = evapotranspirationMonth;
    }

    public void parseCsv(String csv){
        String[] data = csv.split(";");
        if (data.length == 18) {
            this.timestamp = data[0];
            this.baroTendency = data[1];
            this.airPressure = data[2];
            this.temperature = data[3];
            this.windSpeed = data[4];
            this.windAverageSpeed = data[5];
            this.windDirectionDeg = data[6];
            this.windDirectionString = data[7];
            this.humidity = data[8];
            this.rainRate = data[9];
            this.isRaining = data[10];
            this.uvLevel = data[11];
            this.sunImpact = data[12];
            this.rainToday = data[13];
            this.rainMonth = data[14];
            this.rainYear = data[15];
            this.evapotranspirationToday = data[16];
            this.evapotranspirationMonth = data[17];
        } else {
        	this.timestamp = "Error fetching data";
        }
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBaroTendency() {
        return baroTendency;
    }

    public void setBaroTendency(String baroTendency) {
        this.baroTendency = baroTendency;
    }

    public String getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(String airPressure) {
        this.airPressure = airPressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindAverageSpeed() {
        return windAverageSpeed;
    }

    public void setWindAverageSpeed(String windAverageSpeed) {
        this.windAverageSpeed = windAverageSpeed;
    }

    public String getWindDirectionDeg() {
        return windDirectionDeg;
    }

    public void setWindDirectionDeg(String windDirectionDeg) {
        this.windDirectionDeg = windDirectionDeg;
    }

    public String getWindDirectionString() {
        return windDirectionString;
    }

    public void setWindDirectionString(String windDirectionString) {
        this.windDirectionString = windDirectionString;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getRainRate() {
        return rainRate;
    }

    public void setRainRate(String rainRate) {
        this.rainRate = rainRate;
    }

    public String getIsRaining() {
        return isRaining;
    }

    public void setIsRaining(String isRaining) {
        this.isRaining = isRaining;
    }

    public String getUvLevel() {
        return uvLevel;
    }

    public void setUvLevel(String uvLevel) {
        this.uvLevel = uvLevel;
    }

    public String getSunImpact() {
        return sunImpact;
    }

    public void setSunImpact(String sunImpact) {
        this.sunImpact = sunImpact;
    }

    public String getRainToday() {
        return rainToday;
    }

    public void setRainToday(String rainToday) {
        this.rainToday = rainToday;
    }

    public String getRainMonth() {
        return rainMonth;
    }

    public void setRainMonth(String rainMonth) {
        this.rainMonth = rainMonth;
    }

    public String getRainYear() {
        return rainYear;
    }

    public void setRainYear(String rainYear) {
        this.rainYear = rainYear;
    }

    public String getEvapotranspirationToday() {
        return evapotranspirationToday;
    }

    public void setEvapotranspirationToday(String evapotranspirationToday) {
        this.evapotranspirationToday = evapotranspirationToday;
    }

    public String getEvapotranspirationMonth() {
        return evapotranspirationMonth;
    }

    public void setEvapotranspirationMonth(String evapotranspirationMonth) {
        this.evapotranspirationMonth = evapotranspirationMonth;
    }
}
