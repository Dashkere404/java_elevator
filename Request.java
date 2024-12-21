class Request {
    public int floorFrom;
    public int floorTo;
    public Request(int floorFrom, int floorTo){
        this.floorFrom=floorFrom;
        this.floorTo=floorTo;
    }
    public int getFloorFrom(){
        return this.floorFrom;
    }
    public int getFloorTo(){
        return this.floorTo;
    }
}
