import javafx.scene.image.Image;

public class MoveChara {
    public static final int TYPE_DOWN  = 0;
    public static final int TYPE_LEFT  = 1;
    public static final int TYPE_RIGHT = 2;
    public static final int TYPE_UP    = 3;

    protected int posX;
    protected int posY;

    protected MapData mapData;

    private Image[] charaImage;
    private int count   = 0;
    private int diffx   = 1;
    private int charaDir;
    private int itemCount;

    MoveChara(int startX, int startY, MapData mapData){
        this.mapData = mapData;
        charaImage = new Image[12];
        charaImage[0 * 3 + 0] = new Image("pic/nekod1.png");
        charaImage[0 * 3 + 1] = new Image("pic/nekod2.png");
        charaImage[0 * 3 + 2] = new Image("pic/nekod3.png");
        charaImage[1 * 3 + 0] = new Image("pic/nekol1.png");
        charaImage[1 * 3 + 1] = new Image("pic/nekol2.png");
        charaImage[1 * 3 + 2] = new Image("pic/nekol3.png");
        charaImage[2 * 3 + 0] = new Image("pic/nekor1.png");
        charaImage[2 * 3 + 1] = new Image("pic/nekor2.png");
        charaImage[2 * 3 + 2] = new Image("pic/nekor3.png");
        charaImage[3 * 3 + 0] = new Image("pic/nekou1.png");
        charaImage[3 * 3 + 1] = new Image("pic/nekou2.png");
        charaImage[3 * 3 + 2] = new Image("pic/nekou3.png");

        posX = startX;
        posY = startY;

        charaDir = TYPE_DOWN;
    }

    public int getIndex(){
        return charaDir * 3 + count;
    }
    public int getCharaDir(){
        return charaDir;
    }

    public void changeCount(){
        count = count + diffx;
        if (count > 2) {
            count = 1;
            diffx = -1;
        } else if (count < 0){
            count = 1;
            diffx = 1;
        }
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    public void setCharaDir(int cd){
        charaDir = cd;
    }

    public boolean canMove(int dx, int dy){
        switch(mapData.getMap(posX+dx, posY+dy)){
            case MapData.TYPE_WALL:
                return false;
            case MapData.TYPE_NONE:
                return true;
            case MapData.TYPE_ITEM:
                //アイテムとぶつかった時の条件文
                mapData.getItem(posX+dx,posY+dy);
                itemCount++;
                return true;
            case MapData.TYPE_STEP:
                return true;
            default:
                return false;
        }
    }

    public boolean move(int dx, int dy){
        if (canMove(dx,dy)){
            posX += dx;
            posY += dy;
            return true;
        }
        return false;
    }

    public Image getImage(){
        changeCount();
        return charaImage[getIndex()];
    }

    public int getItemCount(){ return itemCount; }
}


