enum Calendar{
     Month, Week, Day, Hours;

}
enum Days {
    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
}enum Months {
    January, February, March, April, May, June, July, August, September, October, November, December;
}enum Hours {
    One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve;
}

public  class Learn {
    public static void main(String[] args) {
        Calendar calendar = Calendar.valueOf("Month");
        System.out.println("Calendar: " + calendar);

}