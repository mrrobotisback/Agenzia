package model.mo;

public class Travel {

  private Long id;
  private Long categoryId;
  private Travel travel;
  private Double price;
  private String name;
  private Double discount;
  private String startDate;
  private Long means;
  private String description;
  private String startPlace;
  private String startHour;
  private String duration;
  private int seatsAvailable;
  private int seatsTotal;
  private String destination;
  private boolean deleted;
  private boolean hide;

  public Travel getTravel(){
    return travel;
  }

  public void setTravel(Travel travel){
    this.travel = travel;
  }

  public Long getTravelId() {
    return id;
  }

  public void setTravelId(Long id) {
    this.id = id;
  }

  public Long getCategoryId(){
    return categoryId;
  }

  public void setCategoryId(Long categoryId){
    this.categoryId = categoryId;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public Long getMeans() {
    return means;
  }

  public void setMeans(Long means) {
    this.means = means;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStartPlace() {
    return startPlace;
  }

  public void setStartPlace(String startPlace) {
    this.startPlace = startPlace;
  }

  public String getStartHour() {
    return startHour;
  }

  public void setStartHour(String startHour) {
    this.startHour = startHour;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public int getSeatsAvailable() {
    return seatsAvailable;
  }

  public void setSeatsAvailable(int seatsAvailable) {
    this.seatsAvailable = seatsAvailable;
  }

  public int getSeatsTotal() {
    return seatsTotal;
  }

  public void setSeatsTotal(int seatsTotal) {
    this.seatsTotal = seatsTotal;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean getVisible() {
    return hide;
  }

  public void setVisible(boolean hide) {
    this.hide = hide;
  }


}
