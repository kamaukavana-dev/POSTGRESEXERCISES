class Timer{
    private int Hour;
    private int Minute;
    private int Second;

    Timer(int Hour,int Minute,int Second){
        this.Hour = Hour;
        this.Minute = Minute;
        this.Second = Second;
    }

    public void SetHour(int Hour){
        this.Hour = Hour;
    }
    public void SetMinute(int Minute){
        this.Minute = Minute;
    }
    public void SetSecond(int Second){
        this.Second = Second;
    }
    public void getTime(int hour,int minute,int second){
        hour = Hour * 120;
        minute = Minute * 60;
        second = Second;
        System.out.println("Hours:"+Hour +"  ||  Minutes: "+Minute+"  ||  Seconds:"+Second);
        System.out.println("Total Seconds :"+(hour+minute+second)+" seconds");
    }
}
