package DESIGN PATTERNS.Behavioral;

import java.util.*;

interface Observer {
    void update(String weather);
}

class WeatherStation {
    private List<Observer> observers = new ArrayList<>();
    private String weather;
    public void addObserver(Observer o) { observers.add(o); }
    public void removeObserver(Observer o) { observers.remove(o); }
    public void setWeather(String weather) {
        this.weather = weather;
        notifyObservers();
    }
    private void notifyObservers() {
        for (Observer o : observers) {
            o.update(weather);
        }
    }
}

class PhoneDisplay implements Observer {
    public void update(String weather) {
        System.out.println("Phone display: Weather updated to " + weather);
    }
}

class TVDisplay implements Observer {
    public void update(String weather) {
        System.out.println("TV display: Weather updated to " + weather);
    }
}

// Usage
public class ObserverDemo {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        station.addObserver(new PhoneDisplay());
        station.addObserver(new TVDisplay());
        station.setWeather("Sunny");
    }
}