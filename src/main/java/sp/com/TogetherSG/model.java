package sp.com.TogetherSG;

public class model {
    private String name, date, time, venue, cost;

    private model() {}

    private model(String name,String date,String time,String venue,String cost){
        this.name = name;
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
