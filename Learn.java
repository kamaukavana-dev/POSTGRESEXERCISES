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

}
