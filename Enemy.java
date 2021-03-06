class Enemy extends MoveChara {

    Enemy(int startX, int startY, MapData mapData){
        super(startX,startY,mapData);
    }


    @Override
    public boolean canMove(int dx, int dy){
        switch(mapData.getMap(posX+dx, posY+dy)){
            case MapData.TYPE_WALL:
                return false;
            case MapData.TYPE_NONE:
                return true;
            case MapData.TYPE_ITEM:
                return true;
            case MapData.TYPE_STEP:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void setCharaDir(int cd){
      charaDir = cd;
    }

}
